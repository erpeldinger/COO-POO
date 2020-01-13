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
    	
    	int port = 1234;
    	InetAddress addrLo = InetAddress.getLocalHost();
    	InetAddress addrbr = BroadcastingClient.getBroadcastAddress();
    	
    	User u1 = new User(77,"ServerToto","mdp",1288,addrbr);
        String addrDest = Connect.queryUserLUC("database.db", 88);
        InetAddress ip = InetAddress.getByName(addrDest);
        //System.out.println("IP Server: ip");
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