package communication;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.BufferedReader;
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
			System.out.println("[TCPServer ] debut ServerTCP ");
			try {
				//Reception de la donnee
				Socket server = this.socket.accept();
				System.out.println("[TCPServer ] sort de Accept");
				byte[] buff = new byte[Byte.MAX_VALUE];
				InputStream in = server.getInputStream();
				OutputStream out = server.getOutputStream();
				receiveMessage(server,in,buff);	    		
	    		Message m = Message.readMessage(buff);

				System.out.println("[TCPServer ] message recu " + m.getContent());
	    		/*
	    		String s="";
	            BufferedReader inB = new BufferedReader(new InputStreamReader(in));
	            PrintStream outP = new PrintStream(out);
	            s = inB.readLine();*/

	            server.close();
	    		
	    		//Stockage dans la bd
	    		Connect.createNewDatabase("database.db");
	        	Connect.createNewTableConv("database.db");
	    		Connect.insertConversation("database.db",this.id, m.getId(),m.getContent(),m.getDate().toString());
	    		System.out.println("message enregistre dans la db \n");
			
			}			
			catch (Exception e) {
				System.out.println("[TCPServer ] Erreur run " + e);
			}
		}			
	} 
}