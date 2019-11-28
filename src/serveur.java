import java.lang.object.*;
import constantes.java;

public Interface Serveur {

    //attributs
    private ArrayList <Struct U2> userList;
    private Struct Conv conv;

    //getters
    public ArrayList <Struct U2> getUserList();
    public ArrayList <Struct U2> getConv();

    //methodes
    private void addUser(Struct U2);
    private void deleterUser(Struct U2);
    private void updateUser(Struct U2, pseudo, passeword);
    private Boolean UserExist(int id);
    private Boolean ConvExist(int id1, int id2);
    private void addMessage (int id1, int id2, String content, Struct DateMsg date);
    //inutile si on vérifie que la conv existe dans addmessage ?
    private void addConv (int id1, int id2, String content, Struct DateMsg date);

}