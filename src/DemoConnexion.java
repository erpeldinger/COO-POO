import java.lang.Object.*;
import java.net.*;
import java.io.IOException;

import java.util.Scanner; 

public class DemoConnexion {
    
    public static void main (String[] args){
    	// Creation d'un utiisateur [ pseudo : Toto et Mot de passe : titi123456789 ]
    	Connect.createNewDatabase("database.db");
    	Connect.deleteTable("database.db", "User");
    	Connect.createNewTableUser("database.db");
    	Connect.insertUser("database.db", "Toto", "titi123456789", 1);
    	
    	// Test du processus de Connexion
    	System.out.println("Bonjour");
    	// Demande du pseudo
    	Scanner scPseudo = new Scanner(System.in);
    	System.out.println("Veuillez saisir votre pseudo :");
    	String strPseudo = scPseudo.nextLine();
    	// Demande du mot de passe associé
    	Scanner scPass = new Scanner(System.in);
    	System.out.println("Veuillez saisir votre mot de passe :");
    	String strPass = scPass.nextLine();
    	
    	//Verification
    	boolean isValidPass = Connect.checkIsUser("database.db", strPseudo, strPass);
    	while (isValidPass==false) {
        	System.out.println("Désolé, le pseudo/mot de passe n'est pas bon. veuillez réessayer ");
        	// Demande du pseudo
        	scPseudo = new Scanner(System.in);
        	System.out.println("Veuillez saisir votre pseudo :");
        	strPseudo = scPseudo.nextLine();
        	// Demande du mot de passe associé
        	scPass = new Scanner(System.in);
        	System.out.println("Veuillez saisir votre mot de passe :");
        	strPass = scPass.nextLine();
        	//Verification
        	isValidPass = Connect.checkIsUser("database.db", strPseudo, strPass);
    	}
    	System.out.println("Vous êtes maintenant connecté !");
    	
    	
    	
    	
    	
    }
}
