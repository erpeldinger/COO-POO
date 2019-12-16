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

    //methodes
    /*public void dateToString() {
        //TODO
    }*/

    public String toString(int id, String content, DateMsg date){
        return new String(Integer.toString(id) + ":" + content.toString() + ":" + date.toString());
    }
    
    public Message toMessage(String s){
        return (new Message(content, date, id));
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
