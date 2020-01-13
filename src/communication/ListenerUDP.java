package communication;

import java.lang.Object.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.lang.Exception;
import java.net.SocketException;
import java.net.UnknownHostException;

import format.Message;
import requete.Connect;

public class ListenerUDP extends Thread {

    //Attributs
    private DatagramSocket socket;
    //private ArrayList <String> messages;
    private static byte[] response = null; //Message comme quoi on est connecté
    private InetAddress addrBroadcast;
	private int userId;
	private ArrayList <Integer> userLUC;
    
    //Constructeurs
    public ListenerUDP (int port, String name, InetAddress addrBroadcast, int id, ArrayList <Integer> LUC) throws SocketException {
    	super(name);
        this.socket = new DatagramSocket(port);
        this.userId = id;
        this.userLUC = LUC;
        System.out.println("User : " + name + " ; Socket ListenerUDP : " + port + "\n");
        this.addrBroadcast = addrBroadcast;
        start();
    }
    //getters 
	public DatagramSocket getDatagramSocket() {return socket;}

	public InetAddress getAddrBr(){
    	return this.addrBroadcast; 
    }

    //Methodes

    private InetAddress getAddr (DatagramPacket inPacket) throws UnknownHostException {
    	return inPacket.getAddress(); 
    }
    
    private int getPort(DatagramPacket inPacket) {
    	return inPacket.getPort();
    }
    
	private boolean isBroadcastPacket(String msg) {
		CharSequence s =  "BROADCAST";
		//System.out.println(msg);
		//System.out.println(msg.contains(s));
		return msg.contains(s);
	}

    public void run() {
  
    	//Boolean connected = true;
		int iter = 0;
		int iterDEBUG = 1;
		//j'ai change la condition pour que ce soit plus propre, avant : while (connected)
    	while (!this.socket.isClosed()) {

    		//System.out.println("[LISTENER UDP] iter while : " + iterDEBUG + "\n");
    		iterDEBUG++;
    		try {
    			//System.out.println("[LISTENER UDP] Nb iteration try : " + iter + "\n");
				iter++;
    			
		    	byte[] buff = new byte[256];
		    	DatagramPacket inPacket = new DatagramPacket(buff, buff.length);
		    	//System.out.println("[LISTENER UDP] Création inPacket ok");
		    	socket.receive(inPacket);
		    	System.out.println("[LISTENER UDP] Réception inPacket ok");
		    	
		    	//Print message broadcast
		    	String msg = new String(inPacket.getData(), 0, inPacket.getLength());
				System.out.println("[LISTENER UDP] print msg "+msg);	
		    	
		    	// S'il s'agit d'un message broadcast pour récupérer la liste des users connectés
		    	if (isBroadcastPacket(msg)) {
		    		//System.out.println("[LISTENER UDP] If -> debut");
		    		String r = this.userId + "#est connecte !"; 
		    		response = r.getBytes();		    				
		    		DatagramPacket outPacket = new DatagramPacket(response,response.length, getAddr(inPacket), getPort(inPacket));
		    		socket.send(outPacket);
		    		//System.out.println("[LISTENER UDP] If -> end");
		    		
		    		//ajout de l'user dans la LUC
		    		this.userLUC.add(Message.toMessageBdc(msg).getId());		    		
		    		//Ajout de l'adresse IP de user dans la bdd 
		    		Connect.createNewDatabase("database.db");
		        	Connect.createNewTableLUC("database.db");
		    		Connect.insertUserLUC("database.db", Message.toMessageBdc(msg).getId(), inPacket.getAddress().toString());
		    		
			    	System.out.println("[LISTENER UDP] Add ok");
			    	//Affiche la liste des utilisateurs connectes
			    	for(int id: this.userLUC) {
			        	 System.out.println("User connecte : \n" +id + " \n");
			        }
		    	}
		    	else {
		    		//c'est un message de réponse de Broadcast
		    		System.out.println("[LISTENER UDP] Else -> debut");
		    		//ajout de l'user dans la LUC
		    		this.userLUC.add(Message.toMessageBdc(msg).getId());
			    	System.out.println("[LISTENER UDP] Add ok");
			    	//Affiche la liste des utilisateurs connectes
			    	for(int id: this.userLUC) {
			        	 System.out.println("User connecte : \n" +id + " \n");
			        }
					
				}		    		
    		}
    		
    		catch (Exception e) {
    			System.out.println("[LISTENER UDP] Erreur run");
    		}
    	}
    	socket.close();
    }
    
}