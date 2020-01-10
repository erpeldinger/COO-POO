package format.format;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// RQ A VOIR : avec la serialization plus besoin du format de message String avec #
// --> plus besoin de passer par des String
// REFAIRE LES toString() etc. avec des try catch

public class Message {

    //Attributs
    private String content;
    private DateMsg date;
    private int id;
    private byte[] packet;

    //Constructeurs
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

    //Getters
    public String getContent() {return this.content;}
    public DateMsg getDate() {return this.date;}
    public byte[] getPacket() {return this.packet;}
    public int getId() {return this.id;}

    //Setter
    public void setDate(DateMsg newDate) { this.date = newDate; }
    private void setContent(String newContent) { this.content=newContent; }
    private void setId(int newId) { this.id=newId; }


    //Methodes
    
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
        	System.out.println("[Message] Erreur toDateMsg -> split 1");
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
        	System.out.println("[Message] Erreur toDateMsg -> split 2");
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
        	System.out.println("[Message] Erreur toMessage ");
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
        	System.out.println("[Message] Erreur toMessageBdc ");
        }

        return m;
    }        
   
    public static byte[] serializeMessage(Object packet) {
    	try {
    	ByteArrayOutputStream outByte = new ByteArrayOutputStream();
    	ObjectOutputStream outPacket = new ObjectOutputStream(outByte);
    	outPacket.writeObject(outPacket);
    	return outByte.toByteArray();
	    }
		catch (Exception e) {
			System.out.println("[Message] Erreur serializeMessage ");
			return null;
		} 
    }
        
    public static Object deserializeMessage(byte[] buff) {
    	try {
    		ByteArrayInputStream byteIn = new ByteArrayInputStream(buff);
    		ObjectInputStream objectIn = new ObjectInputStream(byteIn);
    		return (Object) objectIn.readObject();
    	}
    	catch (Exception e) {
    		System.out.println("[Message] Erreur deserializeMessage ");
    		return null;
    	}    	
    }

    //Renvoie un Message pret a etre serialize pour etre envoye en TCP
    public static Message readyToSend(int id, String content) {
	    try { 
	    	DateMsg date = new DateMsg();
	    	Message m = new Message(content,date,id);
	    	return m;
	    }
		catch (Exception e) {
			System.out.println("[Message] Erreur deserializeMessage ");
			return null;
		} 
    }
    
}