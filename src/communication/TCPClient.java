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
	private int serverPort;
	
	//Constructeurs
	public TCPClient(InetAddress srvAddr, int srvPort, User user, int destId) throws Exception {
        System.out.println("[TCP Client] constructeur - avant Socket");
		this.socket = new Socket(srvAddr, srvPort);
        System.out.println("[TCP Client] constructeur2 - apres socket");
		this.user = user;
		this.destId= destId;
		this.serverPort=srvPort;
	}
	
	//getter
	public int getServerPort() {return this.serverPort; }
	
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
        	String dateMessage = DateMsg.toString(toSend.getDate());
        	System.out.println("[TCP Client] Message à enregistrer, date : " + dateMessage + " de id : " + this.user.getId());
    		Connect.insertConversation("database.db", this.user.getId(), this.destId, Connect.queryUserPseudo("database.db", this.user.getId()) + " : " + toSend.getContent() , dateMessage);
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
