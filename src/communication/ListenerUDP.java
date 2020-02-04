package communication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import clavardage.AlerteMessage;

import java.io.IOException;
import java.lang.Exception;
import java.net.UnknownHostException;

import format.Message;
import requete.Connect;

public class ListenerUDP extends Thread {

    //Attributs
    private DatagramSocket socket;
    private boolean running ;
    private static byte[] response = null; //Message comme quoi on est connecté
    private InetAddress addrBroadcast;
	private int userId;
	private ArrayList <Integer> userLUC;
	private String pseudo;
	private ChatManager manager;
    
    //Constructeurs
    public ListenerUDP (int port, String name, int id, ArrayList <Integer> LUC) throws IOException {
    	super(name);
    	this.manager = new ChatManager();
        this.socket = new DatagramSocket(port);
        this.userId = id;
        this.pseudo=name;
        this.userLUC = LUC;
        this.addrBroadcast = BroadcastingClient.getBroadcastAddress();
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
		return msg.contains(s);
	}
	
	private boolean isEndPacket(String msg) {
		CharSequence s =  "DISCONNECTED";
		return msg.contains(s);
	}
	
	private boolean isOKPacket(String msg) {
		CharSequence s =  "YES";
		return msg.contains(s);
	}
	private boolean isNOPPacket(String msg) {
		CharSequence s =  "NOP";
		return msg.contains(s);
	}

	private boolean isMODIFPacket(String msg) {
		CharSequence s =  "PSEUDO";
		return msg.contains(s);
	}
	
	public void stopUDPListener() {
		this.running = false;
		try {
			this.socket.close();
		}
		catch (Exception e) {
			System.out.println("[UDP Listener] Erreur stopUDPListener ");
			AlerteMessage error = new AlerteMessage("null", "null", 3);
		}
	}


    public void run() {
  
		int iter = 0;
		int iterDEBUG = 1;
		this.running = true;
		int portrecup=-1;
		
    	while (running) {
    		iterDEBUG++;
    		try {
				iter++;
    			
		    	byte[] buff = new byte[256];
		    	DatagramPacket inPacket = new DatagramPacket(buff, buff.length);
		    	socket.receive(inPacket);
		    	
		    	//Print message broadcast
		    	String msg = new String(inPacket.getData(), 0, inPacket.getLength());
		    	
		    	// S'il s'agit d'un message broadcast pour récupérer la liste des users connectés : id # pseudo # BROADCAST : Hello, who is there ?
				if (inPacket.getAddress().equals(BroadcastingClient.getIpAddress())) {
					System.out.println("[LISTENER UDP] NOTRE BROADCAST --> on ne fait rien");
				}
				else if (isBroadcastPacket(msg)) {
		    		int recupPort = this.manager.portDispo();
		    		String r = this.userId + "#" + this.pseudo + "#" +  Integer.valueOf(recupPort)+ "#est connecte !"; 
		    		response = r.getBytes();		    				
		    		DatagramPacket outPacket = new DatagramPacket(response,response.length, getAddr(inPacket), getPort(inPacket));
		    		socket.send(outPacket);
		    		
		    		//Ajout de l'adresse IP de user dans la bdd 
		    		Connect.createNewDatabase("database.db");
		        	Connect.createNewTableLUC("database.db");
		        	Connect.insertUser("database.db", Message.toMessageBdc(msg).getPseudo() ,"XXXX", Message.toMessageBdc(msg).getId());
		    		Connect.insertUserLUCbyAllPort("database.db", Message.toMessageBdc(msg).getPseudo(), inPacket.getAddress().toString(), Message.toMessageBdc(msg).getId(), recupPort);
		    		
		    	}
				else if (isMODIFPacket(msg)) { // un utilisateur a changé de pseudonyme
					String ancienPseudo = Message.toMessageBdcPseudo(msg).getPseudo();
					String nouveauPseudo = Message.toMessageBdcPseudo(msg).getNewPseudo();
					
					String ip = Connect.queryUserLUCbyPseudo("database.db", ancienPseudo);
					//maj bdd
					int id = Message.toMessageBdcPseudo(msg).getId();
	    			Connect.updateUser("database.db",nouveauPseudo, "XXXX", id); 
					Connect.updatePseudoLUC("database.db",nouveauPseudo,ip); // apparement fait un delete et non un udpate
					Connect.insertUserLUCbyPseudo("database.db",nouveauPseudo,ip);
					
					//prevenir l'utilisateur
					AlerteMessage alerte = new AlerteMessage(ancienPseudo, nouveauPseudo, 3);
				}
		    	else if (isEndPacket(msg)) { // Message de deconnexion --------- id # pseudo # "DISCONNECTED : Goodbye
		    		//on supprime la personne de la BD + on ferme de TCP Server
		    		int idDisconnected = -1;
		    		idDisconnected = Message.toMessageDisc(msg).getId();
		    		String pseudoDisconnected = Connect.queryUserPseudo("database.db", idDisconnected, this.pseudo);
		    		Connect.deleteUserLUC("database.db", pseudoDisconnected);
		    		//On previens l'utilisateur
		            AlerteMessage alerte = new AlerteMessage(pseudoDisconnected,"" , 2);
		    	}
		    	else if (isOKPacket(msg)) {
		    		//rien à faire
		    		System.out.println("[LISTENER UPD] OK-PACKET");
		    	}
		    	else if (isNOPPacket(msg)) { // MODIFICATION DU PORT
		    		portrecup = Message.toMessageBdcPort(msg).getPort();
		    		if (manager.isDispo(portrecup)) { //DISPO ---> envoi d'un "YES PACKET"
			    		Connect.updatePortLUC("database.db", Message.toMessageBdcPort(msg).getPseudo(), portrecup);
				    	
				    	//envoi d'un OK Packet
				    	String r = this.userId + "#" + this.pseudo + "# YES !"; 
			    		response = r.getBytes();
			    		DatagramPacket outPacket = new DatagramPacket(response,response.length, getAddr(inPacket), getPort(inPacket));
			    		socket.send(outPacket);
		    		}
		    		else { // PAS DISPO ---> envoi d'un "NOP PACKET"
		    			//demande d'un autre numero de port
			    		int recupPort = this.manager.portDispo();
			    		String r = this.userId + "#" + this.pseudo + "#" +  Integer.valueOf(recupPort)+ "# NOP !"; 
			    		response = r.getBytes();		    				
			    		DatagramPacket outPacket = new DatagramPacket(response,response.length, getAddr(inPacket), getPort(inPacket));
			    		socket.send(outPacket);
			    		//update Port dans bdd
			    		Connect.updatePortLUC("database.db", Message.toMessageBdcPort(msg).getPseudo(), recupPort);
		    		}
		    	}
		    	else { //verification du port (libre ?)
		    		portrecup = Message.toMessageBdcPort(msg).getPort();
		    		if (manager.isDispo(portrecup)) { //DISPO ---> envoi d'un "YES PACKET"
		    			Connect.createNewTableUser("database.db");
			    		Connect.createNewTableLUC("database.db");
			        	Connect.insertUser("database.db", Message.toMessageBdcPort(msg).getPseudo() ,"XXXX", Message.toMessageBdcPort(msg).getId());
			    		Connect.insertUserLUCbyAllPort("database.db", Message.toMessageBdcPort(msg).getPseudo(), inPacket.getAddress().toString(), Message.toMessageBdcPort(msg).getId(), Message.toMessageBdcPort(msg).getPort());
				    	
				    	//envoi d'un OK Packet
				    	String r = this.userId + "#" + this.pseudo + "# YES !"; 
			    		response = r.getBytes();
			    		DatagramPacket outPacket = new DatagramPacket(response,response.length, getAddr(inPacket), getPort(inPacket));
			    		socket.send(outPacket);
		    		}
		    		else { // PAS DISPO ---> envoi d'un "NOP PACKET"
		    			//demande d'un autre numero de port
			    		int recupPort = this.manager.portDispo();
			    		String r = this.userId + "#" + this.pseudo + "#" +  Integer.valueOf(recupPort)+ "# NOP !"; 
			    		response = r.getBytes();		    				
			    		DatagramPacket outPacket = new DatagramPacket(response,response.length, getAddr(inPacket), getPort(inPacket));
			    		socket.send(outPacket);
			    		//update Port dans bdd
			    		Connect.updatePortLUC("database.db", Message.toMessageBdcPort(msg).getPseudo(), recupPort);
		    		}
				}	

	    	}
    		catch (Exception e) {
    			System.out.println("[LISTENER UDP] Erreur run " + e);
				AlerteMessage error = new AlerteMessage("null", "null", 3);
    		}
    	}
    }    
}