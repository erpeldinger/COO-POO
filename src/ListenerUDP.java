import java.lang.Object.*;

public class ListenerUDP extends Thread {

    //attributs
    private DatagramSocket socket;
    private ArrayList <byte[]> messages;
    private static final byte[] response; //Message comme quoi on est connecté

    // constructeurs
    public ListenerUDP (int port, String name) {
    	super(name);
        this.DatagramSocket = new DatagramSocket(port);
        start();
    }

    private Boolean isBroadcast(byte[] buff) {
    	return true; //TODO
    }
    
    private InetAddress getAddress(buff) {
    	return new InetAddress(); //TODO
    }
    
    private int getPort(buff) {
    	return 5555; //TODO
    }
    
    public void run() {
  
    	Boolean connected = true;
	  	//tant que le socket est ouvert
    	while (connected) {
	    	bytr[] buff = new byte[256];
	    	DatagramPacket inPacket = new DatagramPacket(buff, buff.length);
	    	socket.receive(inPacket);
	    	messages.add(buff);
	    	if isBroadcast(buff) {
	    		//répondre que l'on est connecté
	    		DatagramPacket outPacket = new DatagramPacket(response,response.length, getAddress(buff), getPort(buff));
	    		socket.send(outPacket);
	    	}
	    	else {
	    		//c'est un message d'un clavardage
	    		//TODO
	    	}
    	}
    	socket.close();
    }
    
}