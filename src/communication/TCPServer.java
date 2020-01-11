package communication;

import java.io.InputStream;
import java.net.Socket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.IOException;

import format.Message;
import format.DateMsg;
import requete.Connect;


public class TCPServer extends Thread { 

	//Attributs
	private ServerSocket socket = null;
	private boolean running;
	private int id;
	
	//Constructeurs
	public TCPServer(int port, int id,InetAddress localAddr) throws IOException {
		this.socket = new ServerSocket(port,1,localAddr);
		this.id=id;
		this.running = true;
		start();
	}
	
	//Methodes	
	public boolean isRunning () {return this.running;}
	
	public void receiveMessage(Socket socket, InputStream in, byte[] buff) { 
    	try {
	        while (in.available() <=0 ) {}
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
		
		while (isRunning()) {			
			try {
				//Reception de la donnee
				Socket server = this.socket.accept();
				byte[] buff = new byte[Byte.MAX_VALUE];
				InputStream in = server.getInputStream();
				receiveMessage(server,in,buff);	    		
	    		Message m = Message.readMessage(buff);
	    		
	    		//Stockage dans la bd
	    		Connect.createNewDatabase("database.db");
	        	Connect.deleteTable("database.db", "Conversation");
	        	Connect.createNewTableConv("database.db");
	    		Connect.insertConversation("database.db",this.id, m.getId(),m.getContent(),m.getDate().toString());			
			}			
			catch (Exception e) {
				System.out.println("[TCPServer ] Erreur run " + e);
			}
		}			
	} 
}