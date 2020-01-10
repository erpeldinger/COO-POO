package format.format;

import java.io.InputStream;
import java.io.OutputStream;
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
    
    public Message (String content, int id) {
        this.content=content;
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
    
    // On suppose qu'on a un message de la forme "8#coucou#17:26:00:00:00:00"
    public static DateMsg toDateMsg(String s) {
    	
    	String[] other = s.split("#"); 
    	String[] parts= null;
        DateMsg date = null;
        int year, month, day, hour, min, sec;
        
    	try {
    		parts = other[2].split(":");
    	}
        catch (Exception e) {
        	System.out.println("[Message.java - toDateMsg()] Erreur split 1");
        }
        try {
        	year = Integer.parseInt(parts[0]);
        	month = Integer.parseInt(parts[1]);
        	day = Integer.parseInt(parts[2]);
        	hour = Integer.parseInt(parts[3]);
        	min = Integer.parseInt(parts[4]);
        	sec = Integer.parseInt(parts[5]);
        	date = new DateMsg(year, month, day, hour, min, sec);
        }
        catch (Exception e) {
        	System.out.println("[Message.java - toDateMsg()] Erreur split 2");
        }        
        return date;
    }

    public static String toString(int id, String content, DateMsg date){
        return new String(Integer.toString(id) + "#" + content.toString() + "#" + DateMsg.toString(date));
    }
    
    public static String toString(int id, String content){
        return new String(Integer.toString(id) + "#" + content.toString());
    }
    
    // On suppose qu'on a un message de la forme "8#coucou#17:26:00:00:00:00"
    public static Message toMessage(String s){
    	String[] parts = s.split("#");
        String c= "",d="",i="";
        Message m = null;
        
        try {
            c = parts[1];
            i = parts[0];
            m = new Message(c,Message.toDateMsg(s),Integer.parseInt(i));
        }
        catch (Exception e) {
        	System.out.println("[Message.java - toMessage()] Erreur ");
        }

        return m;
    }
    
 // On suppose qu'on a un message de la forme "8#coucou"
    public static Message toMessageBdc(String s){
    	String[] parts = s.split("#");
        String c= "",d="",i="";
        Message m = null;
        
        try {
            c = parts[1];
            i = parts[0];
            m = new Message(c,Integer.parseInt(i));
        }
        catch (Exception e) {
        	System.out.println("[Message.java - toMessage()] Erreur ");
        }

        return m;
    }
    
    
    /* Le format d'un byte prêt à être envoyé est :
     * 
     * id#content#date
     * 
     */    
    
    //Renvoie un tableau de bytes prets a etre envoyes en TCP
    public static byte[] readyToSend(int id, String content) {
    	DateMsg date = new DateMsg();
    	String msg = Integer.toString(id) + content + date.toString() ;
    	return msg.getBytes();
    }
    
    
}