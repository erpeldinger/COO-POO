package demo;

import java.net.*;
import user.User;
import communication.*;

public class DemoClientTCP {
    
    public static void main (String[] args) throws SocketException, Exception {
    	
    	//CLIENT 
    	int port = 1234;
    	InetAddress addr = InetAddress.getLocalHost();
        Socket socket = new Socket(addr,port); 
    	User u1 = new User(88,"UserEnvoieBroadcast","mdp",1246,addr);
    	TCPClient server = new TCPClient(socket,u1);
    	
    	server.sendMessage("Coucou c'est client, ca boom ?");
    	
    }
}