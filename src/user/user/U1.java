package user;

import java.lang.Object.*;

public class U1 {

    //attributs
    String pseudo;
    String password;

    // constructeurs
    public void U1 (String pseudo, String password) {
        this.pseudo= pseudo;
        this.password=password;
    }

    //getters
    public String getPseudo() { return this.pseudo; }
    public String getPassWord() { return this.password; }

    //setter
    public void setPseudo(String p) { this.pseudo=p; }
    private void setPassword(String pwd) { this.password =pwd; }

    //methodes
    
}