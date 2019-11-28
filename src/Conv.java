import java.lang.object.*;
import constantes.java;

public class Conv {

    //attributs
    private Stack <Message> stackMessage;

    // constructeurs
    public void Conv () {
        this.stackMessage = new Stack <Message>();
    }

    //getters
    public Stack <Message> getConv() { return this.stackMessage; }

    //setter
    public void setPConv(String newStack) { this.stackMessage=newStack; }

    //methodes
    public void addMessage(msg) {this.stack.push(msg);}
    public void delMessage() {this.stack.pop();}
    
}