import java.lang.Object.*;
import java.net.*;
import java.io.IOException;

import java.util.Scanner; 

public class DemoConnexion {
    
    public static void main (String[] args){
    	System.out.println("Bonjour");
    	
    	Scanner scPseudo = new Scanner(System.in);
    	System.out.println("Veuillez saisir un pseudo :");
    	String str = scPseudo.nextLine();
    	boolean isValid = Connect.checkPseudo("database.db", str);
    	while (isValid==false) {
        	System.out.println("Désolé, ce pseudo est déjà utilisé");
        	scPseudo = new Scanner(System.in);
        	System.out.println("Veuillez saisir un pseudo :");
        	str = scPseudo.nextLine();
    	}

    	Scanner scPass = new Scanner(System.in);
    	System.out.println("Veuillez saisir un mot de passe [ne contenant pas votre pseudo, faisant au minimum 10 caractères] :");
    	String strPass = scPass.nextLine();
    	while (isValid==false) {
        	System.out.println("Désolé, ce mot de passe n'est pas valide ");
        	scPass = new Scanner(System.in);
        	System.out.println("Veuillez saisir un mot de passe : [ne contenant pas votre pseudo, faisant au minimum 10 caractères] ");
        	str = scPass.nextLine();
    	}
    	
    	
    	
    	
    }
}
