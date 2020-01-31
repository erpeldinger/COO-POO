package demo;

import java.io.IOException;
import java.net.*;
import user.User;
import communication.*;
import requete.*;

public class DemoClientTCP {
    
    public static void main (String[] args) throws SocketException, Exception, IOException, SecurityException, IllegalArgumentException, NullPointerException {
    	
    	
    	Connect.createNewDatabase("database.db");
    	Connect.createNewTableUser("database.db");
    	
    	Connect.insertUserLUCbyAllPort("database.db", "ClientTom", "10.1.5.15",1, 2100 );
    	Connect.insertUserLUCbyAllPort("database.db", "ServerPaul", "10.1.5.20",2, 2100 ); // en dur [celle du serveur]
    	Connect.insertUser("database.db", "ClientTom", "mdp", 1);
    	//Broadcast
    	User u2 = new User(13,"ClientTom","mdp",1234);
        //u2.allowBroadcast(new BroadcastingClient(u2.getListener().getDatagramSocket(),1234, u2)); //port du Server ListenerUDP (1234 pour tous)
        
        //Partie TCP
        InetAddress ip = InetAddress.getByName("10.1.5.20"); // en dur [celle du serveur]
        System.out.println("IP Client : " +ip);
    	int port = 2010;
    	TCPClient client = new TCPClient(ip,2100,u2, 12);
    	client.sendMessage("Coucou c'est client, ca boom ?");
    	
    }
}