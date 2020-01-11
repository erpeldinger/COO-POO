package communication;
import java.io.OutputStream;
import java.net.Socket;
import user.User;
import format.Message;

public class TCPClient {

	//Attributs
	private Socket socket;
	private User user;
	
	//Constructeurs
	public TCPClient(Socket socket, User user) {
		this.socket = socket;
		this.user = user;
	}
	
	//Ecrit un message
	public void writeM(OutputStream out, String msg) {
    	try {
    		System.out.println("Message a envoye : " + msg);
    		byte[] buff = Message.readyToSend(user.getId(),msg);
    		out.write(buff);    		
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
