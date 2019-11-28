import java.lang.object.*;
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
    public void User (int id, String pseudo, String password) {
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
    public ArrayList <U1> getListUserConnected() { return this. listUserConnected; }

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

    public Message createM(String msg) {
        return new Message(msg);
    }

}