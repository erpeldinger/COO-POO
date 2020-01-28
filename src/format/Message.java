package format;

public class Message {

    //Attributs
    private String content;
    private DateMsg date;
    private int id;
    private byte[] packet;
    private String pseudo;
    private int port;

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
    
    public Message (String content, String pseudo, int id) {
        this.content=content;
        this.pseudo=pseudo;
        this.id=id;
    }
    
    public Message (String content, String pseudo, int id, int port) {
        this.content=content;
        this.pseudo=pseudo;
        this.id=id;
        this.port = port;
    }
    
    public Message (String content, int id) {
        this.content=content;
        this.id=id;
    }

    //Getters
    public String getContent() {return this.content;}
    public String getPseudo() {return this.pseudo;}
    public DateMsg getDate() {return this.date;}
    public byte[] getPacket() {return this.packet;}
    public int getId() {return this.id;}
    public int getPort() {return this.port;}

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
    public static String toString(int id, String content, String pseudo){
        return new String(Integer.toString(id) + "#" + pseudo + "#" + content.toString());
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
    
 // On suppose qu'on a un message de la forme "8#pseudo#coucou"
    public static Message toMessageBdc(String s){
    	System.out.println("[Message] toMessageBdc, print s : " +s +"\n");
    	String[] parts = s.split("#");
        String c= "",i="", pseudo="";
        Message m = null;        
        
        try {
            c = parts[2];
            i = parts[0];
            pseudo = parts[1];
            m = new Message(c, pseudo ,Integer.parseInt(i));
        }
        catch (Exception e) {
        	System.out.println("[Message] Erreur toMessageBdc ");
        }
        return m;
    }   
    
 // On suppose qu'on a un message de la forme "8#pseudo#coucou" ???????????
    public static Message toMessageDisc(String s){
    	System.out.println("[Message] toMessageDisc, print s : " +s +"\n");
    	String[] parts = s.split("#");
        String c= "",i="", pseudo="";
        Message m = null;        
        
        try {
            c = parts[2];
            i = parts[0];
            pseudo = parts[1];
            m = new Message(c, pseudo ,Integer.parseInt(i));
        }
        catch (Exception e) {
        	System.out.println("[Message] Erreur toMessageBdc ");
        }
        return m;
    }   
    
    public static Message toMessageBdcPort(String s){
    	System.out.println("[Message] toMessageBdcPort, print s : " +s +"\n");       
    	String[] parts = s.split("#");
        String c= "",i="", pseudo="", port="";
        Message m = null;
        
        try {
        	port = parts[2];
            c = parts[3];
            i = parts[0];
            pseudo = parts[1];
            m = new Message(c, pseudo ,Integer.parseInt(i), Integer.parseInt(port));
        }
        catch (Exception e) {
        	System.out.println("[Message] Erreur toMessageBdcPort " +e);
        }
        return m;
    }      

    //Renvoie un Message pret a etre serialize pour etre envoye en TCP
    public static Message toSend(int id, String content) {
	    try { 
	    	DateMsg date = new DateMsg();
	    	Message m = new Message(content,date,id);
	    	return m;
	    }
		catch (Exception e) {
			System.out.println("[Message] Erreur toSend ");
			return null;
		} 
    }
    public static byte[] readyToSend(int id, String content) {
    	try {
    		DateMsg date = new DateMsg();
	    	Message m = new Message(content,date,id);
    		String s = Message.toString(m.getId(),m.getContent(),m.getDate());
    		byte[] buff = s.getBytes();
    		return buff;
    	}
    	catch (Exception e) {
    		System.out.println("[Message] Erreur readyToSend(id,content)");
    		return null;
    	}
    }
    
    public static byte[] readyToSend(Message m) {
    	try {
    		String s = Message.toString(m.getId(),m.getContent(),m.getDate());
    		byte[] buff = s.getBytes();
    		return buff;
    	}
    	catch (Exception e) {
    		System.out.println("[Message] Erreur readyToSend(message)");
    		return null;
    	}
    }
    
    public static Message readMessage(byte[] buff) {
    	try {
	    	String s = new String(buff);
			Message m = Message.toMessage(s);	    	
			System.out.println("Message recu : " + Message.toString(m.getId(),m.getContent(),m.getDate()));
			return m;
    	}
    	catch (Exception e) {
    		System.out.println("[Message] Erreur readMessage ");
    		return null;
    	}
    }
    
    
}