package communication;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	private static ArrayList<Boolean> ports = new ArrayList<Boolean>(Arrays.asList(new Boolean[10]));
	private int currentPort;
	
	//Constructeurs
	public TCPServer(int id,InetAddress localAddr) throws IOException {
		Collections.fill(ports, Boolean.TRUE); // A CHANGER
		this.id=id;
		this.running = true;
		int indCurrentPort=0;
		// on prend le premier port dispo
		while(!ports.get(indCurrentPort)) {
			indCurrentPort++;
		}
		ports.set(indCurrentPort,Boolean.FALSE);
		currentPort = 2000 + indCurrentPort;
		// AJOUTER LE PORT DANS LA BD ?
		System.out.println(" port : " + currentPort);
		this.socket = new ServerSocket(currentPort,1,localAddr);
		
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

	            //server.close();
	    		
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