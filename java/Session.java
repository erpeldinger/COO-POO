import java.lang.Object.*;

public class Session {

    //attributs
    private U1 user1;
    private U1 user2;
    private Boolean exist;
    private Conv historique;
    private Conv listNewMsg;

    //constructeur
    public Session( U1 user1, U1 user2) {
        this.user1=user1;
        this.user2=user2;
        this.exist=true;
    }

    public Session(U1 user1, U1 user2, Conv historique) {
        this.user1=user1;
        this.user2=user2;
        this.exist=true;
        this.historique=historique;
    }

    //getters
    public U1 getUser1() { return this.user1;}
    public U1 getUser2() { return this.user2;}
    public Boolean getExist() { return this.exist;}
    public Conv getHistorique() { return this.historique;}
    public Conv getListNewMsg() { return this.listNewMsg;}

    //setters
    private void setUser1(U1 u1) { this.user1 = u1;}
    private void setUser2(U1 u2) { this.user2 = u2;}
    private void setExist(Boolean e) { this.exist=e;}
    public void setHistorique(Conv h) { this.historique=h;}
    public void setListNewMsg(Conv l) { this.listNewMsg=l;}

    //methodes
    private void updateConvHistorique (Conv New) {
        //todo
    }

}