//import java.lang.Object.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;

// On a besoin d'un TCPSend et d'un TCPRecive : 
// Le TCPSend a une methode envoieMessage qui horodate le message, l'envoie, et l'ajoute à la BD [methode]
// Le TCPReceive est toujours en ecoute, s'il recoit qqchose il l'horodate, l'ajoute dans la DB et l'affiche [run]

public class TCPChat implements Runnable {

	//Attributs
	private Socket sock;
	private OutputStream outS;
	private InputStream inS;
	//private User user;
	
	//Constructeurs
	public TCPChat(Socket sock) {
		this.sock = sock;
	}
	
	public void sendMessage(Message m) {
		try {		
			if (!this.sock.isClosed()) {
				//est-ce qu'on envoie bien packet ?
				outS.write(m.getPacket());;
			}
		}			
        catch (Exception e){
            System.out.println("[TCPChat] Erreur sendMessage, write ");
        }		
	}	
	
	public void run() {
		
		try {
			while (!this.sock.isClosed()) {
				 /*On recupere le numero de port donne dans user ?
				  * serverSocket = new ServerSocket(1234); 
		            sockS = serverSocket.accept();
		            System.out.println("Serveur 1 ok : 1234");
		      */      
				//On read quoi ???
				inS.read();
			}
		}
		
		catch (Exception e) {
			
		}		
	} 
}
/*

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