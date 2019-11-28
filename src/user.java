import java.lang.object.*;
import constantes.java;

public class User {

    //attributs
    private int id;
    private String pseudo:
    private String passeword;
    private Boolean isActive;
    private ArrayList <Struct U1> listUserConnected;

    //constructeur
    public void User (int id, String pseudo, String passeword) {
        this.id = id;
        this.pseudo = pseudo;
        this.passeword = passeword;
        this.isActive = true;
        this.listUserConnected = new ArrayList();
    }

    // les getters
    public int getId() { return this.id; }
    public String getPseudo() { return this.pseudo; }
    public String getPasseword() { return this.passeword; }
    public Boolean getIsActive()) { return this.isActive; }
    public ArrayList <Struct U1> getListUserConnected() { return this. listUserConnected; }

    // les methodes
    private void sendM(String msg, Session session) {}

    private void recvM() {}

    private void createAccount() {}

    private void deleteAccount() {}

    private void consultListUser() {}

    private void modifPseudo(String pseudo) {}

    private void logIn(String pseudo, String passeword) {}

    private void logOut() {}

    private void openSession(String pseudo, String passeword) {}

    public String getIPAddres() { return "todo";}

}