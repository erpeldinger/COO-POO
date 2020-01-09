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
    
    //Constructeurs
    public BroadcastingClient(DatagramSocket s, DatagramPacket p, int port, User user) throws SocketException {
    	this.socket = s;
    	this.packet = p;
    	this.port = port;
    	this.msg = "Hello, who is there ?";
    	this.user = user;
    	System.out.println("User : " + user.getPseudo() + " ; Socket BroadcastingClient : " + port + "\n");
    	try {
        BroadcastingClient.sendBroadcast(BroadcastingClient.getBroadcastAddress());
        System.out.println("[BROADCASTING CLIENT] send broadcast ok");}
    	catch (Exception e) {}
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
    
    //Envoie un message broadcast pour récupérer une liste des ids des utilisateurs connectés
    public static void sendBroadcast(InetAddress addrbr) throws Exception {
    	  	
    	
    	String mBr = "BROADCAST : Hello, who is there ?";
    	Message m = new Message(mBr, user.getId());
    	
    	// Création d'un paquet de format : "id sender | message | date"
    	String msg = Message.toString(m.getId(),mBr); 
    	System.out.println("[BROADCASTING CLIENT] Message : " + msg);
    	
    	try {
    		//System.out.println("debut send");
	    	//socket.setBroadcast(true);
	    	//System.out.println("set broadcast ok");
	    	packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length,addrbr, port);   	
	    	//System.out.println("creation dtg packet");
	    	socket.send(packet);
	    	//System.out.println("socket.send(packet)");
			byte[] buff = new byte[256];
	    	DatagramPacket outPacket = new DatagramPacket(buff, buff.length);
			String rep = new String(outPacket.getData(), 0, outPacket.getLength());

    		System.out.println("[BROADCASTING CLIENT] sendBroadcast");
    		//socket.setSoTimeout(500); //attend une reponse pendant 2000 ms
        	System.out.println("[BROADCASTING CLIENT] setsotime out\n"); 
			socket.receive(outPacket);				
        	System.out.println("[BROADCASTING CLIENT] receive\n"); 
			System.out.println(rep);
			
    		
    		//Ajoute l'id de la personne qui répond
    		user.getListIdUserConnected().add(Message.toMessage(rep).getId());
        	System.out.println("[BROADCASTING CLIENT] Ajout users dans liste des users connectes ok.\n"); 

			//????????????? A tester, je l'ai ajoute ?????????????
			socket.close();
			System.out.println("[BROADCASTING CLIENT] Socket.close()");
    	}
    	catch (Exception e) {
    		System.out.println("[BROADCASTING CLIENT - sendBroadcast] Erreur sendBroadcast");
    	}
    }
}