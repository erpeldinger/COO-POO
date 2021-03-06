package communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.net.InetAddress;
import java.io.IOException;
import requete.Connect;

public class ChatManager { 
	
	private static ArrayList<Boolean> ports;
	private static ArrayList<TCPServer> servers;
	
	//Constructeurs
	public ChatManager() throws IOException {	
		ports = new ArrayList<Boolean>(Arrays.asList(new Boolean[1000]));
		Collections.fill(ports, Boolean.TRUE);
		servers = new ArrayList<TCPServer>();
	}
	
	
	public static ArrayList<Boolean> getPorts() {return ports;}
	public static ArrayList<TCPServer> getServers() {return servers;}
	
	public void addTCPServer(int id,String pseudo ,InetAddress localAddr) throws IOException {
		int indCurrentPort, port;
		indCurrentPort = 0;
		//recuperation du port dans la BD
		port = Connect.queryPortLUC("database.db", pseudo);
		servers.add(new TCPServer(id, localAddr, port)); 
	}
	
	public void stopCommunication(){
		for (TCPServer s : servers) {
			s.stopTCPServer();
		}
		for (int i = 0; i< ports.size(); i++) {
			ports.set(i,Boolean.TRUE);
		}
	}
	
	public void stopCommunication(ArrayList <String> disconnected){
		//TODO
		// PROCEDURE -> recuperation de l'id a partir du pseudo. Dans les TCP Server on a id et monPort. ---> parcours des TCPServers pour trouver le bon
		/*
		 * String pseudo = "XXXX";
		 * ArrayList <Integer> mesPorts = new ArrayList<Integer>();
		 * int id = Connect.queryUserPseudo("database.db", pseudo);
		 * ArrayList <TCPServer> mesServers= this.manager.getServers();
		 * for (TCPServer courant : mesServers) {
		 * 		if (courant.getId() == id) {
		 * 			mesPorts.add(courant.getPort());
		 * 		// fermeture du Server
		 * 		courant.stopTCPServer()
		 * 		}
		 * }
		 *
		 */
		
		/* PUIS FERMETURE DES PORTS
		 * for ( Integer courant : mesPorts) {
		 * 		ports.set(courant -2000, Boolean.FALSE) // NE PAS OUBLIER LE -2000
		 * }
		 */
	}
	
	public Boolean isDispo(int port) {
		int indPort = port - 2100;
		return ports.get(indPort);
	}
	
	public int portDispo() {
		Boolean isDispo = false;
		int res = -1;
		int i = 0;
		int size = ports.size();
		while (!isDispo) {
			if (ports.get(i) == true) {
				isDispo = true;
				res = i + 2100;
			}
			else if (i>(ports.size())){
				isDispo = true;
			}
			i++;
		}
		return res;
	}
}