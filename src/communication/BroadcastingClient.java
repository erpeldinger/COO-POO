package communication;

import java.lang.Object.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.lang.Exception;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.net.InterfaceAddress;

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
    	System.out.println("User : " + user.getPseudo() + " ; Socket BroadcastingClient : " + port + "\n");
    	try {
	        BroadcastingClient.sendBroadcast(BroadcastingClient.getBroadcastAddress());
	        System.out.println("[BROADCASTING CLIENT] send broadcast ok");}
    	catch (Exception e) {}
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
    	// INTERFACE EN DUR
    	Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
    	while (interfaces.hasMoreElements()) {
    	    NetworkInterface networkInterface = interfaces.nextElement();
    		if (networkInterface.getDisplayName().contains("eth0")) {
    			broadcast = networkInterface.getInterfaceAddresses().get(1).getBroadcast();   	    
	        }
    	}
    	return broadcast; 
    	/*
    	System.out.println("debut");
    	InetAddress monAddr = socket.getInetAddress();
    	System.out.println("get Inet Address");
    	String addrBr = "";
    	String monAddrString = monAddr.toString();
    	String[] parts = monAddrString.split(".");
    	System.out.println("split" + parts[0] + parts[1] + parts[2]);
    	Integer ipClasse = Integer.valueOf(parts[0]);
    	System.out.println("valueOf");
    	if ( ipClasse < 127) {
    		addrBr += parts[0] + ".255.255.255";
    	} 
    	else if ( ipClasse < 192) {
    		addrBr += parts[0] + "." + parts[1] + ".255.255";
    	}
    	else if ( ipClasse < 224 ) {
    		addrBr += parts[0] + "." + parts[1] + "." + parts[2] + ".255";
    	}
    	return InetAddress.getByName(addrBr);
    	*/
    }
    
    
    
    /*
    public static InetAddress getLocalAddr() {
    	return socket.getLocalAddress();
    }*/
    
    //Envoie un message broadcast pour récupérer une liste des ids des utilisateurs connectés
    public static void sendBroadcast(InetAddress addrbr) throws Exception {
    	  	
    	
    	String mBr = "BROADCAST : Hello, who is there ?";
    	Message m = new Message(mBr, user.getId());
    	
    	// Création d'un paquet de format : "id sender | message | date"
    	String msg = Message.toString(m.getId(),mBr); 
    	System.out.println("[BROADCASTING CLIENT] Message : " + msg);
    	
    	try {
    		System.out.println("debut send");
	    	//socket.setBroadcast(true);
    		
	    	//System.out.println("set broadcast ok");
	    	packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length,addrbr, port);   	
	    	System.out.println("creation dtg packet");
	    	socket.send(packet);
    		System.out.println("[BROADCASTING CLIENT] sendBroadcast");
	    	//System.out.println("socket.send(packet)");
			byte[] buff = new byte[256];
	    	DatagramPacket outPacket = new DatagramPacket(buff, buff.length);
	    	
	    	//socket.setSoTimeout(500); //attend une reponse pendant 2000 ms
			/*
	    	String rep = new String(outPacket.geServertData(), 0, outPacket.getLength());

    		//socket.setSoTimeout(500); //attend une reponse pendant 2000 ms
        	System.out.println("[BROADCASTING CLIENT] setsotime out\n"); 
			socket.receive(outPacket);				
        	System.out.println("[BROADCASTING CLIENT] receive\n"); 
			System.out.println(rep);
			
    		
    		//Ajoute l'id de la personne qui répond
    		user.getListIdUserConnected().add(Message.toMessage(rep).getId());
        	System.out.println("[BROADCASTING CLIENT] Ajout users dans liste des users connectes ok.\n"); 
        	*/

			//socket.close();
			//System.out.println("[BROADCASTING CLIENT] Socket.close()");
    	}
    	catch (Exception e) {
    		System.out.println("[BROADCASTING CLIENT - sendBroadcast] Erreur sendBroadcast : " + e );
    	}
    }
}