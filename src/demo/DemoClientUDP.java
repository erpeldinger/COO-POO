package demo;

import java.net.*;
import user.User;
import communication.*;

public class DemoClientUDP {
    
    public static void main (String[] args) throws SocketException, Exception {
    	
    	//Client
    	
    	byte[] buff = new byte[256];
    	DatagramPacket p = new DatagramPacket(buff, buff.length);
        InetAddress addrbr = BroadcastingClient.getBroadcastAddress();
        User u2 = new User(88,"UserEnvoieBroadcast","mdp",1246);
        System.out.println("Serveur 2 ok");
        u2.allowBroadcast(new BroadcastingClient(u2.getListener().getDatagramSocket(),1288, u2));
        System.out.println("AllowBroadcastok");
	}                     
}
