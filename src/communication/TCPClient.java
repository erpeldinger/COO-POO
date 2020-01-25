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
        System.out.println("[TCP Client] constructeur - avant Socket");
		this.socket = new Socket(srvAddr, srvPort);
        System.out.println("[TCP Client] constructeur2 - apres socket");
		this.user = user;
		this.destId= destId;
	}
	
	//Ecrit un message
	public void writeM(OutputStream out, String msg) {
    	try {
    		System.out.println("[TCP CLIENT] Message a envoyer : " + msg);
    		Message toSend = Message.toSend(this.user.getId(), msg);
    		byte[] buff = Message.readyToSend(toSend);
    		out.write(buff);    		
    		System.out.println("[TCP CLIENT] Message envoye : " + msg);

    		//Stockage dans la bd
    		Connect.createNewDatabase("database.db");
        	Connect.createNewTableConv("database.db");
        	//ajout du pseudo de l'expediteur du message dans la BD
    		Connect.insertConversation("database.db", this.user.getId(), this.destId, Connect.queryUserPseudo("database.db", this.user.getId()) + " : " + toSend.getContent() , DateMsg.toString(toSend.getDate()));
    		System.out.println("[TCP CLIENT] message enregistre dans la db \n");
    		
    	}
    	catch (Exception e) {
    		System.out.println("[TCPClient] Erreur writeM");
    	}    			
    }
	
	//Envoie le message
	public void sendMessage(String m) {
		try {
			System.out.println("[TCP CLIENT] sendMessage -> debut \n");
			OutputStream out = this.socket.getOutputStream();
			System.out.println("[TCP CLIENT] sendMessage -> avant writeM \n");
			writeM(out, m);
			System.out.println("[TCP CLIENT] sendMessage -> apres writeM \n");
			out.flush();
			out.close();
			this.socket.close();
		}			
        catch (Exception e){
            System.out.println("[TCPClient ] Erreur sendMessage, write ");
        }		
	}	
}
