package communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.net.InetAddress;
import java.io.IOException;
import requete.Connect;

public class ChatManager { 
//2user, on lance 2 tcp server
	//gestion attribution port

	//private User u1;
	//private User u2;
	private static ArrayList<Boolean> ports;
	private static ArrayList<TCPServer> servers;
	
	//Constructeurs
	public ChatManager() throws IOException {	
		ports = new ArrayList<Boolean>(Arrays.asList(new Boolean[10]));
		Collections.fill(ports, Boolean.TRUE);
		servers = new ArrayList<TCPServer>();
		/*
		while(!ports.get(indCurrentPort)) {
			indCurrentPort++;
		}
		ports.set(indCurrentPort,Boolean.FALSE);
		ports.set(indCurrentPort+1,Boolean.FALSE);
		currentPort = 2000 + indCurrentPort;
		InetAddress ip1 = InetAddress.getByName("10.1.5.88");
		InetAddress ip2 = InetAddress.getByName("10.1.5.87");
		TCPServer s1 = new TCPServer(currentPort,u1.getId(),ip1);
		TCPServer s2 = new TCPServer(currentPort+1,u1.getId(),ip2);
		*/
	}
	
	
	public static ArrayList<Boolean> getPorts() {return ports;}
	
	public void addTCPServer(int id,InetAddress localAddr) throws IOException {
		int indCurrentPort, port;
		indCurrentPort = 0;
		while(!ports.get(indCurrentPort)) {
			indCurrentPort++;
		}
		ports.set(indCurrentPort,Boolean.FALSE);
		port = 2000 + indCurrentPort;
		System.out.println("avant add -- port : " + port + " id : " + id );
		Connect.updatePortLUC("database.db", id, port);
		servers.add(new TCPServer(id, localAddr, port));
		System.out.println("après add");
	}
	
	//Demo
	// Creer une fois cette classe (ça initialise le tableau des ports)
	// Appeler une methode addServer(....) qui ajouteriait un TCPServer dans l'ArrayList (en appellant le constructeur TCPServer)
	
	
	
}