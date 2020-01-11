package communication;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.IOException;

import format.Message;
import format.DateMsg;
import requete.Connect;

//A VERIFIER : TAILLE DES DONNES AUTORISEES, POUR L'INSTANT : MAX_VALUE
//A FAIRE : Ajout message a la BD

// On a besoin d'un TCPSend et d'un TCPRecive : 
// Le TCPSend a une methode envoieMessage qui horodate le message, l'envoie, et l'ajoute à la BD [methode]
// Le TCPReceive est toujours en ecoute, s'il recoit qqchose il l'ajoute dans la DB et l'affiche [run]

public class TCPServer extends Thread { 

	//Attributs
	private ServerSocket socket;
	private boolean running;
	private int id;
	
	//Constructeurs
	public TCPServer(int port, int id) throws IOException {
		this.socket = new ServerSocket(port);
		System.out.println("[TCPServer Costructeur] port : " + port);
		this.id=id;
		this.running = true;
		start();
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
		System.out.println("[TCPServer] Erreur readMessage ");
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
		//on ouvre un port par nouvelle conversation
		while (isRunning()) {
			System.out.println("[TCPServer ] avant try ");
			try {
				//On met arrete le timer si on n'a rien recu au bout de 1sec
				//this.socket.setSoTimeout(5000);
				Socket server = this.socket.accept();
				System.out.println("[TCPServer ] accept ok ");
				
				byte[] buff = new byte[Byte.MAX_VALUE];
				InputStream in = server.getInputStream();
				//On recupere la donnee recue et on la stocke dans buff
				readMessage(server,in,buff);
	    		
				
				//On deserialize pour visualiser le contenu de la donnee 
	    		String s = new String(buff);
	    		System.out.println("Message recu : " + s );
	    		Message m = Message.toMessage(s);	    	
	    		String content = m.getContent() ;
	    		int idDest = m.getId();
	    		DateMsg date = m.getDate();
	    		System.out.println("Message recu : " + Message.toString(idDest, content, date));
	    		
	    		//stockage dans la bd
	    		Connect.createNewDatabase("database.db");
	        	Connect.createNewTableConv("database.db");
	    		Connect.insertConversation("database.db",this.id, idDest, content, date.toString());
				
		    	
			}
			catch (Exception e) {
				System.out.println("[TCPServer ] Erreur run " + e);
			}
		}			
	} 
}