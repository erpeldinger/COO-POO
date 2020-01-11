package demo;

import java.net.*;
import user.User;
import communication.*;

public class DemoServerTCP {
    
    public static void main (String[] args) throws SocketException, Exception {
    	
    	//Server
    	int port = 1234;
    	InetAddress addr = InetAddress.getLocalHost();
    	User u2 = new User(99,"UserEnvoieBroadcast","mdp",1288,addr);
    	TCPServer server = new TCPServer(port);
    	
    }
}