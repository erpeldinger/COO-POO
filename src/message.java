import java.lang.object.*;
import constantes.java;

public class Message {

    //attributs
    private String content;
    private Struct <DateMsg> date;

    // constructeurs
    public void Message (String content) {
        this.content=content;
    }

    public void Message (String content, Struct <DateMsg> date) {
        this.content=content;
        this.date=date;
    }

    //getters
    public String getContent() { return this.content;}
    public Struct <DateMsg> getDate() { return this.date; }

    //setter
    public void setDate(Struct <DateMsg> newDate) { this.date = newDate; }
    private void setContent(String newContent=) { this.content=newContent; }

    //methodes
    public void dateToString() {
        //todo
    }
    
}