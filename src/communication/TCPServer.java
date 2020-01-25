package communication;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.IOException;
import format.Message;
import requete.Connect;


public class TCPServer extends Thread { 

	//Attributs
	private ServerSocket socket = null;
	private boolean running;
	private int id;
	//private static ArrayList<Boolean> ports = Collections.fill(new ArrayList<Boolean>(Arrays.asList(new Boolean[10])), Boolean.TRUE);
	private int monPort;
	
	//Constructeurs
	public TCPServer(int id,InetAddress localAddr, int port) throws IOException {
		//System.out.println("Constructeur Server TCP");
		this.id=id;
		this.running = true;
		int monPort=port;
		System.out.println("[TCPServer] constructeur -> port : " + port + " addr : " + localAddr);
		this.socket = new ServerSocket(port,1,localAddr);
		System.out.println("[TCPServer] constructeur -> new ServerSocket ok");
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
	        System.out.println("[TCPServer] buff " + new String(buff) );
    	}
    	catch (Exception e) {
		System.out.println("[TCPServer] Erreur readMessage ");
    	}
    }
	// fermer les ports !
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
			System.out.println("[TCPServer ] debut ServerTCP et id : " + this.id);
			try {
				//Reception de la donnee
				Socket server = this.socket.accept();
				System.out.println("[TCPServer ] sort de Accept");
				byte[] buff = new byte[Byte.MAX_VALUE];
				InputStream in = server.getInputStream();
				System.out.println("[TCPServer ] apres inputstream ");
				OutputStream out = server.getOutputStream();
				System.out.println("[TCPServer ] apres outputstream ");
				receiveMessage(server,in,buff);	 // PROBLEME ICI
				System.out.println("[TCPServer ] apres receive ");
	    		Message m = Message.readMessage(buff);

				System.out.println("[TCPServer ] message recu " + m.getContent());
	    		/*
	    		String s="";
	            BufferedReader inB = new BufferedReader(new InputStreamReader(in));
	            PrintStream outP = new PrintStream(out);
	            s = inB.readLine();*/

	            //server.close();
	    		
	    		//Stockage dans la bd
	    		Connect.createNewDatabase("database.db");
	        	Connect.createNewTableConv("database.db");
	        	//ajout du nom de l'exp�diteur du message dans la BD
	    		Connect.insertConversation("database.db",m.getId(), this.id, Connect.queryUserPseudo("database", m.getId()) + " : " + m.getContent(),m.getDate().toString());
	    		System.out.println("message enregistre dans la db \n");
			
			}			
			catch (Exception e) {
				System.out.println("[TCPServer ] Erreur run " + e);
			}
		}			
	} 
}