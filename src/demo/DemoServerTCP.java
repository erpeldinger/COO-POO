package demo;

import java.net.*;
import user.User;
import communication.*;
import requete.Connect;

public class DemoServerTCP {
    
    public static void main (String[] args) throws SocketException, Exception {
    	
    	

        Connect.createNewDatabase("database.db");
        Connect.createNewTableLUC("database.db");
        Connect.createNewTableUser("database.db");
        
        Connect.insertUser("database.db", "Paul", "12456789123", 12);
        Connect.insertUser("database.db", "Tom", "12456789123", 13);
        Connect.insertUser("database.db", "Thibault", "12456789123", 14);
        
    	Connect.insertUserLUC("database.db", 12, "10.1.5.88");
    	Connect.insertUserLUC("database.db", 13, "10.1.5.183" );
    	Connect.insertUserLUC("database.db", 14, "addrIP3" );

    	Connect.insertUser("database.db", "ServerPaul", "mdp", 2);
    	
    	Connect.insertUserLUCbyAllPort("database.db", "ClientTom", "10.1.5.15",1, 2100 ); // en dur [celle du client]
    	Connect.insertUserLUCbyAllPort("database.db", "ServerPaul", "10.1.5.20",2, 2100 ); // en dur [celle du serveur]
    	
    	//Broadcast
    	//InetAddress addrBr = BroadcastingClient.getBroadcastAddress();
    	User u1 = new User(12,"ServerPaul","mdp", 1234);   
    	
    	//Partie TCP
        InetAddress ip = BroadcastingClient.getIpAddress();
        System.out.println("IP Server: " +ip);
    	int port = 1234;
		System.out.println("lancement du server tcp ");
		ChatManager manager = new ChatManager();
		manager.addTCPServer(u1.getId(), "ClientTom",ip);
		System.out.println("tcp server lancé ");
		
    }
}