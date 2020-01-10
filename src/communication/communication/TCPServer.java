package communication.communication;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.IOException;

//A VERIFIER : TAILLE DES DONNES AUTORISEES, POUR L'INSTANT : MAX_VALUE


// On a besoin d'un TCPSend et d'un TCPRecive : 
// Le TCPSend a une methode envoieMessage qui horodate le message, l'envoie, et l'ajoute à la BD [methode]
// Le TCPReceive est toujours en ecoute, s'il recoit qqchose il l'ajoute dans la DB et l'affiche [run]

public class TCPServer implements Runnable { //ou Thread

	//Attributs
	private ServerSocket socket;
	private boolean running;
	
	//Constructeurs
	public TCPServer(int port) throws IOException {
		this.socket = new ServerSocket(port);
		this.running = true;
	}
	
	//Methodes	
	public boolean isRunning () {return this.running;}
	
	public void readMessage(Socket socket, InputStream in, byte[] buff) { 
    	try {
	        while (in.available() <=0 ) {
	        }
	        in.read(buff);
    	}
    	catch (Exception e) {
		System.out.println("[TCPClient ] Erreur readMessage ");
    	}
    }
	
	public void stopTCPServer() {
		this.running = false;
		try {
			this.socket.close();
		}
		catch (Exception e) {
			System.out.println("[TCPServer ] Erreur stopTCPServer ");
		}
	}
	
	public void run() {
		
		while (isRunning()) {
			try {
				this.socket.setSoTimeout(1000);
				Socket client = this.socket.accept();
				
				byte[] buff = new byte[Byte.MAX_VALUE];
				InputStream in = client.getInputStream();
				readMessage(client,in,buff);
				
				//On verifie qu'il s'agit bien d'un message
			
				
				
				
				
				
			}
			catch (Exception e) {
				System.out.println("[TCPServer ] Erreur run ");
			}
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