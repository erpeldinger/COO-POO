package demo;

import java.io.IOException;
import user.User;
import communication.*;

public class DemoChatManager {
    
    public static void main (String[] args) throws IOException {
    	
    	User u1 = new User(12,"ServerPaul","mdp",1288);  
    	User u2 = new User(13,"ClientTom","mdp",1246);
    	System.out.println("debut \n");
    	ChatManager c = new ChatManager(u1,u2);
    	System.out.println("Chat ok \n");
    	int len = ChatManager.getPorts().size();
    	for (int i = 0; i<len; i++) {
    		System.out.println(ChatManager.getPorts().get(i));
    	}
    	
    }
}