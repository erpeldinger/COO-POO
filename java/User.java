import java.lang.Object.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.OutputStream;
/*
import Constantes.java; */

public class User {

    //attributs
    private int id;
    private String pseudo;
    private String password;
    private Boolean isActive;
    private ArrayList <U1> listUserConnected;
    private ArrayList <Integer> listIdUserConnected;
    private ArrayList<Message> messages;
    private ListenerUDP listener;

    //constructeur
    public User(int id, String pseudo, String password, int port, InetAddress addrbr) throws SocketException {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.isActive = true;
        this.listUserConnected = new ArrayList <U1>();
        this.messages= new ArrayList <Message>();
        this.listener = new ListenerUDP (port,pseudo,addrbr);
    }

    // les getters
    public int getId() { return this.id; }
    public String getPseudo() { return this.pseudo; }
    public String getPassword() { return this.password; }
    public Boolean getIsActive() { return this.isActive; }
    public ArrayList <U1> getListUserConnected() { return this.listUserConnected; }
    public ArrayList <Integer> getListIdUserConnected() { return this.listIdUserConnected; }

    // les methodes
    //private void sendM(String msg, Session session) {}

    private void recvM() {}

    private void createAccount() {}

    private void deleteAccount() {}

    private void consultListUser() {}

    private void modifPseudo(String pseudo) {}

    private void logIn(String pseudo, String password) {}

    private void logOut() {}

    private void openSession(String pseudo, String password) {}

    public String getIPAddres() { return "todo";}

    //methodes pour le main
    public Message createM(String msg) {
        return new Message(msg);
    }

    public void writeM(OutputStream out, String msg) {
    	try {
        out.write(msg.getBytes());
    	}
    	catch (Exception e) {
    		System.out.println("erreur methode writeM");
    	}
    			
    }

    public void readM(InputStream in, byte[] buff) { 
    	try {
	        while (in.available() <=0 ) {
	        }
	        in.read(buff);
    	}
    	catch (Exception e) {
		System.out.println("erreur methode writeM");
    	}
    }
       
    private ArrayList<U1> getUsers(byte[] buff) {
    	ArrayList <U1> maListe = new ArrayList <U1>();
    	//TODO
    	return maListe;    	
    }
    
    /*
    // fait un broadcast UDP et récupère les utilisateurs connectés et les mets dans la liste
    public void createListUserCo(byte[] msg, int len, InetAddress addrBroadcast, int clientPort) {
    	try {
            // envoi du message
            DatagramSocket dgSocket = new DatagramSocket();
            DatagramPacket outPacket= new DatagramPacket(msg, len,addrBroadcast, clientPort);
            dgSocket.send(outPacket);
            
            // Si on n'a pas de réponse au bout de 4 secondes, on considère qu'on est le seul utilisateur connecté
            dgSocket.setSoTimeout(4000); //à voir, il existe une constante RECEIVING_TIMEOUT

            // reception du message
            byte[] bufferClient = new byte[256];
            DatagramPacket inPacketClient= new DatagramPacket(bufferClient, bufferClient.length);
            dgSocket.receive(inPacketClient);
            
            //recuperation des user connectes et création de la liste
           ArrayList <U1> liste = getUsers(bufferClient);
           this.listUserConnected = liste;
        }
        catch (Exception e) {
        	System.out.println("Erreur récupération de la liste des utilisateurs connectes");
        }
    }
    
    
    public DatagramPacket sendBroadcast(String msg, int port) {
    	
    	DatagramSocket dgSocket = new DatagramSocket(port);
    	
    	
    	
    }
    */

}