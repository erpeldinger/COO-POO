package communication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.lang.Exception;
import java.net.SocketException;
import java.net.NetworkInterface;
import java.util.Enumeration;

import clavardage.AlerteMessage;
import user.User;
import format.Message;

public class BroadcastingClient {

    //Attributs
    private static DatagramSocket socket;
    private static DatagramPacket packet;
    private static int port;
    private String msg ="";
    private static User user;
    
    //Constructeurs
    public BroadcastingClient(DatagramSocket s, int port, User user) throws SocketException {
    	this.socket = s;
    	this.packet = null;
    	this.port = port;
    	this.msg = "Hello, who is there ?";
    	this.user = user;
    	System.out.println("[BROADCASTING CLIENT] User : " + user.getPseudo() + " ; Socket BroadcastingClient : " + port + "\n");
    	try {
	        BroadcastingClient.sendBroadcast(BroadcastingClient.getBroadcastAddress());}
    	catch (Exception e) {
			AlerteMessage error = new AlerteMessage("null", "null", 3);
    	}
    }
    
    //getters
    public DatagramSocket getSocket() { return socket; }
    
    //Methodes
    
    public static InetAddress getIpAddress() throws SocketException {
    	InetAddress res = null;
        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        while(ifaces.hasMoreElements()) {
          NetworkInterface iface = ifaces.nextElement();
          Enumeration<InetAddress> addresses = iface.getInetAddresses();

          while(addresses.hasMoreElements()) {
            InetAddress monIp = addresses.nextElement();
            if(monIp instanceof Inet4Address && !monIp.isLoopbackAddress()){
              res = monIp;
            }
          }
        }
        return res;
    }
    
    public static InetAddress getBroadcastAddress()  throws SocketException {
    	InetAddress broadcast = null;
    	Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
    	while (interfaces.hasMoreElements()) {
    	    NetworkInterface networkInterface = interfaces.nextElement();
    		if (networkInterface.getDisplayName().contains("eth4")) {
    			broadcast = networkInterface.getInterfaceAddresses().get(1).getBroadcast();   	    
	        }
    	}
    	return broadcast; 
    }
    
    //Envoie d'un message de deconnexion en Broadcast
    public static void sendDisconnected(InetAddress addrbr) throws Exception { // id # pseudo # message
    	String mBr = "DISCONNECTED : Goodbye";
    	Message m = new Message(mBr, user.getPseudo() ,user.getId());

    	String msg = Integer.valueOf(user.getId()) + "#" + user.getPseudo() + "#" + mBr ;
    	
    	try {    		
	    	packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length,addrbr, port);   	
	    	socket.send(packet);
	    	byte[] buff = new byte[256];
	    	DatagramPacket outPacket = new DatagramPacket(buff, buff.length);	    	
    	}
    	catch (Exception e) {
    		System.out.println("[BROADCASTING CLIENT] Erreur sendDisconnected : " + e );
			AlerteMessage error = new AlerteMessage("null", "null", 3);
    	}
    }
    
    // Envoie d'un message de modification de pseudo en Broadcast
    public static void sendModifPseudo(InetAddress addrbr, String ancien, String nouveau) throws Exception { // id # pseudo # message
    	String mBr = "PSEUDO : modification du pseudo";
    	Message m = new Message(mBr, user.getPseudo() ,user.getId());

    	String msg = Integer.valueOf(user.getId()) + "#" + ancien + "#" + nouveau + "#" + mBr ;
    	
    	try {    		
	    	packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length,addrbr, port);   	
	    	socket.send(packet);
	    	byte[] buff = new byte[256];
	    	DatagramPacket outPacket = new DatagramPacket(buff, buff.length);	    	
    	}
    	catch (Exception e) {
    		System.out.println("[BROADCASTING CLIENT] Erreur sendModifPseudo : " + e );
			AlerteMessage error = new AlerteMessage("null", "null", 3);
    	}
    }
    	
    
    //Envoie un message broadcast pour récupérer une liste des ids des utilisateurs connectés
    public static void sendBroadcast(InetAddress addrbr) throws Exception { // id # pseudo # message
    	  	
    	String mBr = "BROADCAST : Hello, who is there ?";
    	Message m = new Message(mBr, user.getPseudo() ,user.getId());
    	
    	String msg = Integer.valueOf(user.getId()) + "#" + user.getPseudo() + "#" + mBr ;
    	
    	try {
	    	packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length,addrbr, port);   
	    	socket.send(packet);
			byte[] buff = new byte[256];
	    	DatagramPacket outPacket = new DatagramPacket(buff, buff.length);
    	}
    	catch (Exception e) {
    		System.out.println("[BROADCASTING CLIENT - sendBroadcast] Erreur sendBroadcast : " + e );
			AlerteMessage error = new AlerteMessage("null", "null", 3);
    	}
    }
}