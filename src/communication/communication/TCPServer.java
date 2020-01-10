package communication.communication;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.IOException;

import format.format.Message;
import format.format.DateMsg;

//A VERIFIER : TAILLE DES DONNES AUTORISEES, POUR L'INSTANT : MAX_VALUE

//A FAIRE : Ajout message a la BD

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
		//on ouvre un port par nouvelle conversation
		while (isRunning()) {
			try {
				//On met arrete le timer si on n'a rien recu au bout de 1sec
				this.socket.setSoTimeout(1000);
				Socket client = this.socket.accept();
				
				byte[] buff = new byte[Byte.MAX_VALUE];
				InputStream in = client.getInputStream();
				//On recupere la donnee recue et on la stocke dans buff
				readMessage(client,in,buff);
				
				//On deserialize pour visualiser le contenu de la donnee 
				Object inPacket = Message.deserializeMessage(buff);
		    	Class<? extends Object> c = inPacket.getClass();
		    	
				//On verifie s'il s'agit d'un message
		    	if(c.getSimpleName().equals(Message.class.getSimpleName())) {
		    		//On recupere les infos du paquet
		    		Message m = (Message) inPacket;
		    		String content = m.getContent() ;
		    		int id = m.getId();
		    		DateMsg date = m.getDate();
		    		
					//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					//On stocke le message dans la BD
		    	}
			}
			catch (Exception e) {
				System.out.println("[TCPServer ] Erreur run ");
			}
		}			
	} 
}