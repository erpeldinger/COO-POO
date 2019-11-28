import java.lang.object.*;
import constantes.java;

public class Session {

    //attributs
    private Struct U1 user1;
    private Struct U1 user2;
    private Boolean exist;
    private Struct Conv historique;
    private Struct Conv listNewMsg;

    //constructeur
    public void Session(Struct U1 user1, Struct U1 user2) {
        this.user1=user1;
        this.user2=user2;
        this.exist=true;
    }

    public void Session(Struct U1 user1, Struct U1 user2, Struct Conv historique) {
        this.user1=user1;
        this.user2=user2;
        this.exist=true;
        this.historique=historique;
    }

    //getters
    public Struct U1 getUser1() { return this.user1;}
    public Struct U1 getUser2() { return this.user2;}
    public Boolean getExist() { return this.exist;}
    public Struct Conv getHistorique() { return this.historique;}
    public Struct Conv getListNewMsg() { return this.listNewMsg;}

    //setters
    private void setUser1(Struct U1 u1) { this.user1 = u1;}
    private void setUser2(Struct U1 u2) { this.user2 = u2;}
    private void setExist(Boolean e) { return this.exist=e;}
    public void setHistorique(Struct Conv h) { this.historique=h;}
    public void setListNewMsg(Struct Conv l) { this.listNewMsg=l;}

    //methodes
    private void updateConvHistorique (Struct Conv New) {
        //todo
    }

}