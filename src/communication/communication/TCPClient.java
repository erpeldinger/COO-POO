package communication.communication;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import user.user.User;
import format.format.Message;

//AJOUT DU MESSAGE A LA BD A FAIRE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//utiliser serialize

public class TCPClient {

	//Attributs
	private Socket socket;
	private User user;
	
	//Constructeurs
	public TCPClient(Socket socket, User user) {
		this.socket = socket;
		this.user = user;
	}
	
	public void writeM(OutputStream out, String msg) {
    	try {
    		Message m = Message.readyToSend(user.getId(),msg);
    		byte[] buff = Message.serializeMessage(m);
    		out.write(buff);
    	}
    	catch (Exception e) {
    		System.out.println("[TCPClient] Erreur writeM");
    	}    			
    }
	
	public void sendMessage(String m) {
		try {
			OutputStream out = this.socket.getOutputStream();
			writeM(out, m);
			out.flush();
			out.close();
			this.socket.close();
			//AJOUT DU MESSAGE A LA BD A FAIRE ICI !!!!!!!!!!!!!!!!!!!
		}			
        catch (Exception e){
            System.out.println("[TCPClient ] Erreur sendMessage, write ");
        }		
	}	
}
