import java.lang.Object.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.lang.Exception;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ListenerUDP extends Thread {

    //Attributs
    private DatagramSocket socket;
    private ArrayList <byte[]> messages;
    private static final byte[] response = null; //Message comme quoi on est connecté
    private InetAddress addrBroadcast;
    
    //Constructeurs
    public ListenerUDP (int port, String name, InetAddress addrBroadcast) throws SocketException {
    	super(name);
        this.socket = new DatagramSocket(port);
        this.addrBroadcast = addrBroadcast;
        start();
    }
    
    //Methodes

    private InetAddress getAddr (DatagramPacket inPacket) throws UnknownHostException {
    	return inPacket.getAddress(); 
    }
    
    private int getPort(DatagramPacket inPacket) {
    	return inPacket.getPort();
    }
    
    public void run() {
  
    	Boolean connected = true;
    	while (connected) {
    		try {
    			
    			//TODO -> s'ajouter dans sa propre liste de users connectés
    			
		    	byte[] buff = new byte[256];
		    	DatagramPacket inPacket = new DatagramPacket(buff, buff.length);
		    	System.out.println("[LISTENER UDP] Création inPacket ok");
		    	socket.receive(inPacket);
		    	System.out.println("[LISTENER UDP] Réception inPacket ok");
		    	messages.add(buff);
		    	System.out.println("[LISTENER UDP] Add ok");
		    	
		    	// S'il s'agit d'un message broadcast pour récupérer la liste des users connectés
		    	if (inPacket.getAddress().getHostAddress().contains(addrBroadcast.toString())) {
		    		System.out.println("[LISTENER UDP] Si c'est un msg bdcast");
		    		
		    		DatagramPacket outPacket = new DatagramPacket(response,response.length, getAddr(inPacket), getPort(inPacket));
		    		socket.send(outPacket);
		    		
		    		//Mettre à jour la liste des user connectés (ajouter la personne qui a envoyé le broadcast)
		    	}
		    	else {
		    		//c'est un message d'un clavardage
		    		//TODO
		    	}
    		}
    		catch (Exception e) {
    			System.out.println("[LISTENER UDP] Erreur run");
    		}
    	}
    	socket.close();
    }
    
}