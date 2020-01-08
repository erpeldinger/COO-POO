import java.lang.Object.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.lang.Exception;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.net.InterfaceAddress;

public class BroadcastingClient {

    //Attributs
    private static DatagramSocket socket;
    private static DatagramPacket packet;
    private static int port;
    private String msg ="";
    private static User user;
    private ArrayList<Integer> idUsersCo = new ArrayList<Integer>();
    
    //Constructeurs
    public BroadcastingClient(DatagramSocket s, DatagramPacket p, int port, User user) {
    	this.socket = s;
    	this.packet = p;
    	this.port = port;
    	this.msg = "Hello, who is there ?";
    	this.user = user;
    	System.out.println("User : " + user.getPseudo() + " ; Socket BroadcastingClient : " + port + "\n");
    }
    
    //Methodes

    /* Avec cette méthode, il faut expliquer que la configuration du réseau doit être configurée de
     * telle ou telle manière.
     * Pour l'instant, on considère que l'on récupère l'adresse de broadcast sur eth0.
     */
    
    public static InetAddress getBroadcastAddress() throws SocketException {
    	
    	InetAddress broadcast = null;
    	
    	Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
    	while (interfaces.hasMoreElements()) {
    	    NetworkInterface networkInterface = interfaces.nextElement();
    		if (networkInterface.getDisplayName().contains("eth0")) {
    			broadcast = networkInterface.getInterfaceAddresses().get(1).getBroadcast();   	    
	        }
    	}
    	return broadcast;  
    }
    
    //ATTENTION : je crois que c'est redondant avec createListUserCo dans User...
    /* Avec cette méthode, on écrit en dur le n° de port pour le broadcast !!!
     */
    
    //Envoie un message broadcast pour récupérer une liste des ids des utilisateurs connectés
    public static ArrayList <Integer> sendBroadcast(InetAddress addrbr) throws Exception {
    	String mBr = "BROADCAST : Hello, who is there ?";
    	Message m = new Message(mBr,DateMsg.getDate(), user.getId());
    	
    	// Création d'un paquet de format : "id sender | message | date"
    	String msg = Message.toString(m.getId(),mBr,m.getDate()); 
    	// IL FAUT LIER BROADCASTING CLIENT ET LISTENER UDP
    	ArrayList <Integer> idUsersCo = null;
    	
    	try {
	    	socket.setBroadcast(true);
	    	InetAddress broadcastAddr = getBroadcastAddress() ;
			
	    	packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length,addrbr, port);   	
	    	socket.send(packet);
	    	
			byte[] buff = new byte[256];
	    	DatagramPacket outPacket = new DatagramPacket(buff, buff.length);
			String rep = new String(outPacket.getData(), 0, outPacket.getLength());

    		System.out.println("[BROADCASTING CLIENT - sendBroadcast");
    		socket.setSoTimeout(3000); //attend une réponse pendant 3000 ms
			socket.receive(outPacket);				
			System.out.println(rep);
    		
    		//Ajoute l'id de la personne qui répond
    		idUsersCo.add(Message.toMessage(rep).getId());
    	}
    	catch (Exception e) {
    		System.out.println("[BROADCASTING CLIENT - sendBroadcast] Erreur sendBroadcast");
    	}
		
		return idUsersCo;
    }
}