package demo;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import user.User;
import communication.*;

public class DemoServerTCP {
    
    public static void main (String[] args) throws SocketException, Exception {
    	
    	//Server
    	
    	int port = 1234;
    	InetAddress addr = InetAddress.getLocalHost();
    	User u2 = new User(99,"UserEnvoieBroadcast","mdp",1288,addr);
    	TCPServer server = new TCPServer(port, 99);
    	
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