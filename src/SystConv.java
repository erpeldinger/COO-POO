import java.lang.Object.*;
import java.util.ArrayList;

public class SystConv{
    
    //Attributs
    private ArrayList <String> connectedUsersL;

    //Constructeur
    public void SystConv(ArrayList <String> connectedUsersL){
        this.connectedUsersL = connectedUsersL;
    }

    //Methodes
    private void askPseudo(U2 user){}

    private void askPwd(U2 user){}

    private void createUser(int id, String pwd){}

    private void sendM(String msg, U2 user){}

    private void updateConnectedUL(int id){}

    private Boolean convExists(){ return false;
    }

    private Conv getHistorique(U2 user1, U2 user2){ return new Conv();
    }

    private void updateHistConv(Conv listNewM){}

    private void notifyModifiedPseudo(U2 user){}

    private int findId(U2 user){
        return 5555;
    }

}