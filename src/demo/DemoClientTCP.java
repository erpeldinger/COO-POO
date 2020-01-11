package demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import user.User;
import communication.*;

public class DemoClientTCP {
    
    public static void main (String[] args) throws SocketException, Exception, IOException, SecurityException, IllegalArgumentException, NullPointerException {
    	
    	//CLIENT 
    	/*
    	int port = 1234;
    	InetAddress addr = InetAddress.getLocalHost();
        System.out.println("addr ok");
        Socket socket = new Socket(addr,port); 
        System.out.println("socket cree");
    	User u1 = new User(88,"UserEnvoieBroadcast","mdp",1246,addr);
    	TCPClient server = new TCPClient(socket,u1);
    	
    	server.sendMessage("Coucou c'est client, ca boom ?");
    	*/
    	
    	Socket sockC = null;
        OutputStream outC = null;
        InputStream inC = null;
        InetAddress addrC = null;
        //try {
            addrC = InetAddress.getLocalHost();
            sockC = new Socket(addrC, 1234); 
            System.out.println("Client 1 ok : 1234");
        /*}
        catch (Exception e){
            System.out.println("Erreur création du socket client user1");
        }
        */
            outC = sockC.getOutputStream();
            inC = sockC.getInputStream();
        //fermeture des différents sockets
        try {
        sockC.close();
        }
        catch( Exception e) {}
    	
    }
}