package communication;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.IOException;

import format.DateMsg;
import format.Message;
import requete.Connect;
import clavardage.AlerteMessage;

public class TCPServer extends Thread { 

	//Attributs
	private ServerSocket socket = null;
	private boolean running;
	private int id;
	private int monPort;
	
	//Constructeurs
	public TCPServer(int id,InetAddress localAddr, int port) throws IOException {
		this.id=id;
		this.running = true;
		int monPort=port;
		this.socket = new ServerSocket(port,1,localAddr);
		start();
	}
	
	//getters
	public int getPort() {return this.monPort;}
	public long getId() {return this.id;}
	
	//Methodes	
	public boolean isRunning () {return this.running;}
	
	public void receiveMessage(Socket socket, InputStream in, byte[] buff) { 
    	try {
	        while (in.available() <=0 ) {}
	        in.read(buff);
    	}
    	catch (Exception e) {
		System.out.println("[TCPServer] Erreur readMessage ");
		AlerteMessage error = new AlerteMessage("null", "null", 3);
    	}
    }
	
	//Fermer les ports
	public void stopTCPServer() {
		this.running = false;
		try {
			this.socket.close();
		}
		catch (Exception e) {
			System.out.println("[TCPServer ] Erreur stopTCPServer ");
			AlerteMessage error = new AlerteMessage("null", "null", 3);
		}
	}
	
	public void run() {
		
		while (isRunning()) {	
			try {
				//Reception de la donnee
				Socket server = this.socket.accept();
				byte[] buff = new byte[Byte.MAX_VALUE];
				InputStream in = server.getInputStream();
				OutputStream out = server.getOutputStream();
				receiveMessage(server,in,buff);	
	    		Message m = Message.readMessage(buff);
	    		
	    		//Stockage dans la bd
	    		Connect.createNewDatabase("database.db");
	        	Connect.createNewTableConv("database.db");
	        	
	        	//ajout du nom de l'exp�diteur du message dans la BD
	        	String pseudoExpediteur = Connect.queryUserPseudo("database.db", m.getId(), Connect.queryUserPseudo("database.db", this.id));
	    		Connect.insertConversation("database.db",m.getId(), this.id, Connect.queryUserPseudo("database.db", m.getId(), Connect.queryUserPseudo("database.db", this.id)) + " : " + m.getContent(),DateMsg.toString(m.getDate()));
	    		AlerteMessage monAlerte = new AlerteMessage(pseudoExpediteur, "", 0);
			}			
			catch (Exception e) {
				System.out.println("[TCPServer ] Erreur run " + e);
				AlerteMessage error = new AlerteMessage("null", "null", 3);
			}
		}			
	} 
}