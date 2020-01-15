package demo;

import java.io.IOException;
import java.net.*;
import user.User;
import communication.*;


public class DemoClientTCP {
    
    public static void main (String[] args) throws SocketException, Exception, IOException, SecurityException, IllegalArgumentException, NullPointerException {
    	
    	

    	//Broadcast
    	User u2 = new User(13,"ClientTom","mdp",1288);
    	Thread.sleep(500);
        u2.allowBroadcast(new BroadcastingClient(u2.getListener().getDatagramSocket(),1288, u2));
        
        
        
        //Partie TCP
        InetAddress ip = InetAddress.getByName("10.1.5.229"); // en dur [celle du serveur]
        System.out.println("IP Client : " +ip);
    	int port = 2010;
    	TCPClient client = new TCPClient(ip,2000,u2, 12);
    	client.sendMessage("Coucou c'est client, ca boom ?");
    	
    	/*
    	//CLIENT 
        Connect.createNewDatabase("database.db");
        Connect.createNewTableLUC("database.db");
    	
    	int port = 1234;
    	InetAddress addrLo = InetAddress.getLocalHost();
    	InetAddress addrbr = BroadcastingClient.getBroadcastAddress();
        System.out.println("addr ok");
    	User u2 = new User(88,"ClientTiti","mdp",1246,addrbr);
        u2.allowBroadcast(new BroadcastingClient(u2.getListener().getDatagramSocket(),1288, u2));

    	//join ?
    	Thread.sleep(2000);
    	
        System.out.println("socket cree");
        String addrDest = Connect.queryUserLUC("database.db", 77);
        InetAddress ip = InetAddress.getByName("10.1.5.43");
        System.out.println("IP Client : " +ip);
    	TCPClient client = new TCPClient(ip,port,u2);
    	
    	client.sendMessage("Coucou c'est client, ca boom ?");
    	*/
    	
    	
    	
    	/*
    	Socket sockC = null;
        OutputStream outC = null;
        InputStream inC = null;
        InetAddress addrC = null;
        try {
            addrC = InetAddress.getLocalHost();
            sockC = new Socket(addrC, 1234); 
            System.out.println("Client 1 ok : 1234");
        /*}
        catch (Exception e){
            System.out.println("Erreur création du socket client user1");
        }
        
            outC = sockC.getOutputStream();
            inC = sockC.getInputStream();
        //fermeture des différents sockets
        try {
        sockC.close();
        }
        catch( Exception e) {}
    	*/
    }
}