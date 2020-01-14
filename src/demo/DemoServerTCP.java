package demo;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import user.User;
import communication.*;
import requete.Connect;

public class DemoServerTCP {
    
    public static void main (String[] args) throws SocketException, Exception {
    	
    	//Server
        Connect.createNewDatabase("database.db");
        Connect.createNewTableLUC("database.db");
    	
    	
    	//ATTENDRE 2secondes
    	/*long now = System.currentTimeMillis();   
    	while(now < expectedElapsedTime){
    	    now = System.currentTimeMillis();
    	}*/
    	//OU ALORS ON FAIT CE WHILE SUR LE WHILE(RUNNING) DANS LISTENERUDP
    	//Thread.sleep(2000);
    	
    	int port = 1234;
    	InetAddress addrLo = InetAddress.getLocalHost();
    	InetAddress addrbr = BroadcastingClient.getBroadcastAddress();    	
        System.out.println("avant user");
    	User u1 = new User(77,"ServerToto","mdp",1288,addrbr);    	
        System.out.println("apres user");
        String addrDest = Connect.queryUserLUC("database.db", 88);
        
        //join ?
        Thread.sleep(2000);
        
        System.out.println("addr dest : " + addrDest);
        String[] other = addrDest.split("/");
        System.out.println("apres split : " + other[1]);
        
        //Thread.sleep(2000);
        
        InetAddress ip = InetAddress.getByName("10.1.5.42");
        System.out.println("IP Server: " +ip);
    	TCPServer server = new TCPServer(port,u1.getId(),ip);
    	
    	/*
    	ServerSocket serverSocket = null;
        Socket sockS = null;
        OutputStream outS = null;
        InputStream inS = null;
        System.out.println("init ok");
        try {
            serverSocket = new ServerSocket(1234); 
            System.out.println("server socket ok");
            sockS = serverSocket.accept();
            System.out.println("Serveur 1 ok : 1234");
        }
        catch (Exception e){
            System.out.println("Erreur création du server1");
        }
        try {
            outS = sockS.getOutputStream();
            inS = sockS.getInputStream();
            System.out.println("stream ok");
        }
        catch (Exception e){
            System.out.println("Erreur création des inS1 et outS1");
        }
      //fermeture des différents sockets
        try {
        sockS.close();
        serverSocket.close();
        }
        catch( Exception e) {}
        */
    }
}