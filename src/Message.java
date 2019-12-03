import java.lang.Object.*;
import java.net.InetAddress;

public class Message {

    //attributs
    private String content;
    private DateMsg date;
    private byte[] packet ;

    // constructeurs
    public Message(String content) {
        this.content=content;
    }

    public Message (String content, DateMsg date) {
        this.content=content;
        this.date=date;
    }

    //getters
    public String getContent() {return this.content;}
    public DateMsg getDate() {return this.date;}
    public byte[] getPacket() {return this.packet;}

    //setter
    public void setDate(DateMsg newDate) { this.date = newDate; }
    private void setContent(String newContent) { this.content=newContent; }

    //methodes
    public void dateToString() {
        //TODO
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