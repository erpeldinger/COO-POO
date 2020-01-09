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
    //private ArrayList <String> messages;
    private static byte[] response = null; //Message comme quoi on est connecté
    private InetAddress addrBroadcast;
	private User user;
    
    //Constructeurs
    public ListenerUDP (int port, String name, InetAddress addrBroadcast) throws SocketException {
    	super(name);
        this.socket = new DatagramSocket(port);
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
		//j'ai change la condition pour que ce soit plus propre, avant : while (connected)
    	while (!this.socket.isClosed()) {
    		try {
    			System.out.println("Nb iteration : " + iter + "\n");
				iter++;
    			
		    	byte[] buff = new byte[256];
		    	DatagramPacket inPacket = new DatagramPacket(buff, buff.length);
		    	System.out.println("[LISTENER UDP] Création inPacket ok");
		    	socket.receive(inPacket);
		    	System.out.println("[LISTENER UDP] Réception inPacket ok");
		    	
		    	//Print message broadcast
		    	String msg = new String(inPacket.getData(), 0, inPacket.getLength());
				System.out.println(msg);	
		    	
		    	// S'il s'agit d'un message broadcast pour récupérer la liste des users connectés
		    	if (isBroadcastPacket(msg)) {
		    		//System.out.println("[LISTENER UDP] Si c'est un msg bdcast");
		    		
		    		String r = new String(user.getPseudo() + " est connecte !");		    		
		    		response = r.getBytes();
		    				
		    		DatagramPacket outPacket = new DatagramPacket(response,response.length, getAddr(inPacket), getPort(inPacket));
		    		socket.send(outPacket);

		    	}
		    	else {
		    		//c'est un message d'un clavardage
		    		System.out.println("[LISTENER UDP] Msg de clavardage -> else");
			    	DatagramPacket inPacket2 = new DatagramPacket(buff, buff.length);
		    		socket.receive(inPacket2);
		    		String msg2 = new String(inPacket2.getData(), 0, inPacket2.getLength());
					System.out.println("Message : " + msg2);		    		
				}		    	
	    		
				//Ajoute l'id de la personne qui envoie le message
	    		user.getListIdUserConnected().add(Message.toMessage(msg).getId());
		    	System.out.println("[LISTENER UDP] Add ok");
    		}
    		
    		catch (Exception e) {
    			System.out.println("[LISTENER UDP] Erreur run");
    		}
    	}
    	socket.close();
    }
    
}