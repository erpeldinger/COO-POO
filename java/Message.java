import java.lang.Object.*;
import java.net.InetAddress;

public class Message {

    //attributs
    private String content;
    private DateMsg date;
    private int id;
    private byte[] packet;

    // constructeurs
    public Message(String content) {
        this.content=content;
    }

    public Message (String content, DateMsg date) {
        this.content=content;
        this.date=date;
    }

    public Message (String content, DateMsg date, int id) {
        this.content=content;
        this.date=date;
        this.id=id;
    }

    //getters
    public String getContent() {return this.content;}
    public DateMsg getDate() {return this.date;}
    public byte[] getPacket() {return this.packet;}
    public int getId() {return this.id;}

    //setter
    public void setDate(DateMsg newDate) { this.date = newDate; }
    private void setContent(String newContent) { this.content=newContent; }
    private void setId(int newId) { this.id=newId; }


    // REFAIRE LES toString() etc. avec des try catch


    //methodes
    public static DateMsg toDateMsg(String s) {
        String[] parts = s.split(":");
        return new DateMsg(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]));
    }

    public static String toString(int id, String content, DateMsg date){
        return new String(Integer.toString(id) + "#" + content.toString() + "#" + date.toString());
    }
    
    public static Message toMessage(String s){
        String[] parts = s.split("#");
        if (parts.length = 3){
            //ok on traite les msg
            String c = parts[1];
            String d = parts[0];
            String i = parts[2];
        }

        else {
            System.out.println("[Message.java - toMessage()] Erreur, pas le bon nombre d'arguments");
        } 

        //trouver fonction qui découpe message avec :
        return (new Message(c,toDateMsg(d),Integer.parseInt(i)));
    }

    /* La forme d'un packet prêt à être envoyé est :
     * 
     * addrSrc | addrDest | portS | portD | msg
     * 
     */
    
    /*
    public void readyToSend(int portS, int portD, InetAddress addrSrc, InetAddress addrDest) {
    	String msg = addrSrc.toString() + addrDest.toString() + Integer.toString(portS) + Integer.toString(portD) ;
    	this.packet = msg.getBytes();
    }
    */
    
}
