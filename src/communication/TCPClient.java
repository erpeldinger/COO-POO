package communication;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import user.User;
import format.Message;
import requete.Connect;
import format.*;

public class TCPClient {

	//Attributs
	private Socket socket;
	private User user;
	private int destId;
	
	//Constructeurs
	public TCPClient(InetAddress srvAddr, int srvPort, User user, int destId) throws Exception {
        System.out.println("TCP constructeur");
		this.socket = new Socket(srvAddr, srvPort);
        System.out.println("TCP constructeur2");
		this.user = user;
		this.destId= destId;
	}
	
	//Ecrit un message
	public void writeM(OutputStream out, String msg) {
    	try {
    		System.out.println("Message a envoyer : " + msg);
    		Message toSend = Message.toSend(this.user.getId(), msg);
    		byte[] buff = Message.readyToSend(toSend);
    		out.write(buff);    		
    		System.out.println("Message envoye : " + msg);

    		//Stockage dans la bd
    		Connect.createNewDatabase("database.db");
        	Connect.createNewTableConv("database.db");
    		Connect.insertConversation("database.db", this.user.getId(), this.destId, toSend.getContent() , DateMsg.toString(toSend.getDate()));
    		System.out.println("message enregistre dans la db \n");
    		
    	}
    	catch (Exception e) {
    		System.out.println("[TCPClient] Erreur writeM");
    	}    			
    }
	
	//Envoie le message
	public void sendMessage(String m) {
		try {
			OutputStream out = this.socket.getOutputStream();
			writeM(out, m);
			out.flush();
			out.close();
			this.socket.close();
		}			
        catch (Exception e){
            System.out.println("[TCPClient ] Erreur sendMessage, write ");
        }		
	}	
}
