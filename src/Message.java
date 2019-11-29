import java.lang.Object.*;

public class Message {

    //attributs
    private String content;
    private DateMsg date;

    // constructeurs
    public Message(String content) {
        this.content=content;
    }

    public Message (String content, DateMsg date) {
        this.content=content;
        this.date=date;
    }

    //getters
    public String getContent() { return this.content;}
    public DateMsg getDate() { return this.date; }

    //setter
    public void setDate(DateMsg newDate) { this.date = newDate; }
    private void setContent(String newContent) { this.content=newContent; }

    //methodes
    public void dateToString() {
        //todo
    }
    
}