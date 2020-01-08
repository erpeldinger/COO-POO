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
        System.out.println("send broadcast ok");}
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
    	
    	//On s'ajoute dans la liste des users connect�s
    	user.getListIdUserConnected().add(user.getId());
    	System.out.println("Je me suis ajout� dans ma liste des users connect�s.\n");    	
    	
    	String mBr = "BROADCAST : Hello, who is there ?";
    	Message m = new Message(mBr, user.getId());
    	
    	// Création d'un paquet de format : "id sender | message | date"
    	String msg = Message.toString(m.getId(),mBr); 
    	System.out.println("Message : " + msg);
    	
    	try {
    		//System.out.println("debut send");
	    	socket.setBroadcast(true);
	    	//System.out.println("set broadcast ok");
	    	packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length,addrbr, port);   	
	    	//System.out.println("cr�ation dtg packet");
	    	socket.send(packet);
	    	//System.out.println("socket.send(packet)");
			byte[] buff = new byte[256];
	    	DatagramPacket outPacket = new DatagramPacket(buff, buff.length);
			String rep = new String(outPacket.getData(), 0, outPacket.getLength());

    		System.out.println("[BROADCASTING CLIENT - sendBroadcast");
    		socket.setSoTimeout(2000); //attend une r�ponse pendant 2000 ms
			socket.receive(outPacket);				
			System.out.println(rep);
			
			//????????????? A tester, je l'ai ajoute ?????????????
			socket.close();
			System.out.println("Socket.close()");
    		
    		//Ajoute l'id de la personne qui répond
    		user.getListIdUserConnected().add(Message.toMessage(rep).getId());
        	System.out.println("Ajout users dans liste des users connect�s ok.\n"); 
    	}
    	catch (Exception e) {
    		System.out.println("[BROADCASTING CLIENT - sendBroadcast] Erreur sendBroadcast");
    	}
    }
}