package demo;

import java.net.*;

import communication.BroadcastingClient;
import communication.ListenerUDP;
import user.User;

public class DemoServerUDP {
    
    public static void main (String[] args) throws SocketException, Exception {
    	//Serveur
    	User u1 = new User(77,"UserAttendBroadcast","mdp",1288);
    	System.out.println("Mon pseudo est  : ");
    	System.out.println(u1.getPseudo()+ "\n");
    	
    	u1.allowBroadcast(new BroadcastingClient(u1.getListener().getDatagramSocket(),1234, u1)); //port du Server ListenerUDP (1234 pour tous)

    	DatagramSocket d1 = new DatagramSocket(1230);
    	byte[] buff = new byte[256];
    	DatagramPacket p = new DatagramPacket(buff, buff.length);
    	

    }
}