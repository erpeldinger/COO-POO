package user;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import format.Message;
import requete.Connect;
import communication.*;

public class User {

    //attributs
	private static int port = 3001;
	private int monPort;
    private int id;
    private String pseudo;
    private String password;
    private Boolean isActive;
    private ArrayList <Integer> listIdUserConnected;
    private ArrayList<Message> messages;
    private ListenerUDP listener;
    private BroadcastingClient broadcast;
    

    //constructeur
    public User(int id, String pseudo, String password) throws IOException {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.isActive = true;
        this.listIdUserConnected = new ArrayList <Integer>();
        this.messages= new ArrayList <Message>();
        this.monPort = port;
        this.listener = new ListenerUDP (port++,pseudo, id, listIdUserConnected); 
    }

  //constructeur
    public User(int id, String pseudo, String password, int port2) throws IOException {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.isActive = true;
        this.listIdUserConnected = new ArrayList <Integer>();
        this.messages= new ArrayList <Message>();
        this.monPort = port2;
        this.listener = new ListenerUDP (port2,pseudo, id, listIdUserConnected); 
    }
    
    // les getters
    public int getId() { return this.id; }
    public int getMonPort() { return this.monPort; }
    public String getPseudo() { return this.pseudo; }
    public String getPassword() { return this.password; }
    public Boolean getIsActive() { return this.isActive; }
    public ArrayList <Integer> getListIdUserConnected() { return this.listIdUserConnected; }
    public ListenerUDP getListener() { return this.listener;}
    public BroadcastingClient getBroadcast() { return this.broadcast; } 
    
    //les setters
    public void setPseudo(String nPseudo) { this.pseudo=nPseudo; }
    public void setPassword(String nPass) { this.password=nPass; }
    
    
    // les methodes
	public void allowBroadcast (BroadcastingClient c) { 
		this.broadcast = c;
	}	

    //methodes pour le main
    public Message createM(String msg) {
        return new Message(msg);
    }
}