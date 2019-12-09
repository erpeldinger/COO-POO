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
    
    //Constructeurs
    public BroadcastingClient(DatagramSocket s, DatagramPacket p, int port) {
    	this.socket = s;
    	this.packet = p;
    	this.port = port;
    	this.msg = "Hello, who is there ?";
    }
    
    //Methodes

    /* Avec cette méthode, il faut expliquer que la configuration du réseau doit être configurée de
     * telle ou telle manière.
     * Pour l'instant, on considère que l'on récupère l'adresse de broadcast sur eth0.
     */
    
    //ATTENTION : RENVOIE L'ADRESSE SOUS AVEC '/' DEVANT...
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
    
    public static void sendBroadcast() throws Exception {
    	
    	String msg = "Hello, who is there ?";
    	
    	try {
    		socket = new DatagramSocket(port);
    	}
    	catch (Exception e) {
    		System.out.println("[BROADCASTING CLIENT - sendBroadcast] Erreur création socket");
    	}

    	socket.setBroadcast(true);
    	InetAddress broadcastAddr = getBroadcastAddress() ;
    	packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length,broadcastAddr,port);   	
    	socket.send(packet);
    	socket.close();
    }
}