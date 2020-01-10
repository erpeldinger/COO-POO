//import java.lang.Object.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;

//AJOUT DU MESSAGE A LA BD A FAIRE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

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
    		byte[] buff = Message.readyToSend(user.getId(),msg);
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
/*
 * 
    public void writeM(Socket socket, OutputStream out, String msg) {
    	try {
        out.write(msg.getBytes());
    	}
    	catch (Exception e) {
    		System.out.println("erreur methode writeM");
    	}
    			
    }

    public void readM(Socket socket, InputStream in, byte[] buff) { 
    	try {
	        while (in.available() <=0 ) {
	        }
	        in.read(buff);
    	}
    	catch (Exception e) {
		System.out.println("erreur methode writeM");
    	}
    }

//-----------------Création du serveur TCP------------------------------------- 
ServerSocket serverSocket = null;
Socket sockS = null;
OutputStream outS = null;
InputStream inS = null;
try {
    serverSocket = new ServerSocket(1234); 
    sockS = serverSocket.accept();
    System.out.println("Serveur 1 ok : 1234");
}
catch (Exception e){
    System.out.println("Erreur création du server1");
}
try {
    outS = sockS.getOutputStream();
    inS = sockS.getInputStream();
}
catch (Exception e){
    System.out.println("Erreur création des inS1 et outS1");
}


//---------------Creation du client TCP---------------------------------------------------------------

Socket sockC = null;
OutputStream outC = null;
InputStream inC = null;
InetAddress addrC = null;
try {
    addrC = InetAddress.getLocalHost();
    sockC = new Socket(addrC, 1234); 
    System.out.println("Client 1 ok : 1234");
}
catch (Exception e){
    System.out.println("Erreur création du socket client user1");
}
try {
    outC = sockC.getOutputStream();
    inC = sockC.getInputStream();
}
catch (Exception e){
    System.out.println("Erreur création des inC et outC");
}
//Communication

user.writeM(out_buff[1], m.getContent());
//System.out.println("Message envoyé");
byte[] buff = new byte[200];
user.readM(in_buff[1], buff);
System.out.println("Message lu");
String mLu = new String(buff);
System.out.println("Le message lu est : " + mLu);

//fermeture des différents sockets
try {
sock.close();
}
}*/ 