import java.lang.Object.*;
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
    public ArrayList<Message> messages;

    //constructeur
    public User(int id, String pseudo, String password) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.isActive = true;
        this.listUserConnected = new ArrayList <U1>();
        this.messages= new ArrayList <Message>();
    }

    // les getters
    public int getId() { return this.id; }
    public String getPseudo() { return this.pseudo; }
    public String getPassword() { return this.password; }
    public Boolean getIsActive() { return this.isActive; }
    public ArrayList <U1> getListUserConnected() { return this.listUserConnected; }

    // les methodes
    private void sendM(String msg, Session session) {}

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

}