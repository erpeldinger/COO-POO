import java.lang.Object.*;

public class U2 {

    //attributs
    String pseudo;
    String password;
    int id;

    // constructeurs
    public void U1 (int id, String pseudo, String password) {
        this.pseudo= pseudo;
        this.password=password;
        this.id=id;
    }

    //getters
    public String getPseudo() { return this.pseudo; }
    public String getPassWord() { return this.password; }
    public int getId() { return this.id; }

    //setter
    public void setPseudo(String p) { this.pseudo=p; }
    private void setPassword(String pwd) { this.password = pwd; }
    private void setId(int newId) { this.id=newId; }

    //methodes
    
}