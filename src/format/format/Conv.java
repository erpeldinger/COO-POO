package format;

import java.lang.Object.*;
import java.util.Stack;

public class Conv {

    //attributs
    private Stack<Message> stackMessage;

    // constructeurs
    public Conv () {
        this.stackMessage = new Stack <Message>();
    }

    //getters
    public Stack <Message> getConv() { return this.stackMessage; }

    //setter
    public void setPConv(Stack<Message> stackNew) { this.stackMessage=stackNew; }

    //methodes
    public void addMessage(Message msg) {this.stackMessage.push(msg);}
    public void delMessage() {this.stackMessage.pop();}
    
}