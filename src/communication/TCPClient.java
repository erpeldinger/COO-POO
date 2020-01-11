package communication;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import user.User;
import format.Message;

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
    		System.out.println("Message a envoye : " + msg);
    		Message m = Message.readyToSend(user.getId(),msg);
    		String s = Message.toString(m.getId(),m.getContent(),m.getDate());
    		System.out.println("Message a envoye : " + s);
    		byte[] buff = s.getBytes();
    				//Message.serializeMessage(m);
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
