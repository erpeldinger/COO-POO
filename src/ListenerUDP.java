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
    private ArrayList <String> messages;
    private static byte[] response = null; //Message comme quoi on est connecté
    private InetAddress addrBroadcast;
	public BroadcastingClient broadcast;
    
    //Constructeurs
    public ListenerUDP (int port, String name, InetAddress addrBroadcast) throws SocketException {
    	super(name);
        this.socket = new DatagramSocket(port);
        this.addrBroadcast = addrBroadcast;
        start();
    }
    //getters 
	public DatagramSocket getDatagramSocket() {return socket;}

    //Methodes
	public void allowBroadcast (BroadcastingClient c) { 
		broadcast = c;
	}

    private InetAddress getAddr (DatagramPacket inPacket) throws UnknownHostException {
    	return inPacket.getAddress(); 
    }
    
    private int getPort(DatagramPacket inPacket) {
    	return inPacket.getPort();
    }
    
    public void run() {
  
    	Boolean connected = true;
		int iter = 0;
    	while (connected) {
    		try {
    			System.out.println("Nb iteration : " + iter + "\n");
				iter++;
    			//TODO -> s'ajouter dans sa propre liste de users connectés
    			
		    	byte[] buff = new byte[256];
		    	DatagramPacket inPacket = new DatagramPacket(buff, buff.length);
		    	System.out.println("[LISTENER UDP] Création inPacket ok");
		    	socket.receive(inPacket);
		    	System.out.println("[LISTENER UDP] Réception inPacket ok");
		    	
		    	//Print message broadcast
		    
		    	String msg = new String(inPacket.getData(), 0, inPacket.getLength());
				System.out.println(msg);				
		    	
				//Le add ne fonctionne pas
		    	//messages.add(msg);
		    	//System.out.println("[LISTENER UDP] Add ok");
		    	
				//ATTENTION PB IF -> condition NOK
		    	// S'il s'agit d'un message broadcast pour récupérer la liste des users connectés
				System.out.println("addr if : "+inPacket.getAddress());
				System.out.println("addr vieux : "+inPacket.getAddress().getHostAddress());
				System.out.println("addr br"+ this.addrBroadcast);
		    	if (inPacket.getAddress().equals(addrBroadcast)) {
		    		System.out.println("[LISTENER UDP] Si c'est un msg bdcast");
		    		
		    		String r = new String("Je suis connecté !");		    		
		    		response = r.getBytes();
		    				
		    		DatagramPacket outPacket = new DatagramPacket(response,response.length, getAddr(inPacket), getPort(inPacket));
		    		socket.send(outPacket);
		    		
		    		//Mettre à jour la liste des user connectés (ajouter la personne qui a envoyé le broadcast)
		    	}
		    	else {
		    		//c'est un message d'un clavardage
		    		//TODO
		    		System.out.println("[LISTENER UDP] Msg de clavardage -> else");
				}
    		}
    		//}
    		catch (Exception e) {
    			System.out.println("[LISTENER UDP] Erreur run");
    		}
    	}
    	socket.close();
    }
    
}