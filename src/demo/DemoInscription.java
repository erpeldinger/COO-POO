package demo;

import java.lang.Object.*;
import java.net.*;
import java.io.IOException;

import java.util.Scanner; 

import requete.Connect;

public class DemoInscription {
    
    public static void main (String[] args){
    	
    	Scanner scPseudo = new Scanner(System.in);
    	System.out.println("Veuillez saisir un pseudo :");
    	String strPseudo = scPseudo.nextLine();
    	boolean isValidPseudo = Connect.checkPseudo("database.db", strPseudo);
    	while (isValidPseudo==false) {
        	System.out.println("Désolé, ce pseudo est déjà utilisé");
        	scPseudo = new Scanner(System.in);
        	System.out.println("Veuillez saisir un pseudo :");
        	strPseudo = scPseudo.nextLine();
        	isValidPseudo = Connect.checkPseudo("database.db", strPseudo);
    	}

    	Scanner scPass = new Scanner(System.in);
    	System.out.println("Veuillez saisir un mot de passe [ne contenant pas votre pseudo, faisant au minimum 10 caractères] :");
    	String strPass = scPass.nextLine();
    	boolean isValidPass = Connect.checkPassword("database.db", strPseudo, strPass);
    	while (isValidPass==false) {
        	System.out.println("Désolé, ce mot de passe n'est pas valide ");
        	Scanner scPass2 = new Scanner(System.in);
        	System.out.println("Veuillez saisir un mot de passe : [ne contenant pas votre pseudo, faisant au minimum 10 caractères] ");
        	strPass = scPass2.nextLine();
        	isValidPass = Connect.checkPassword("database.db", strPseudo, strPass);
    	}

    	Connect.createNewDatabase("database.db");
    	Connect.deleteTable("database.db", "User");
    	Connect.createNewTableUser("database.db");
    	System.out.println("Bonjour");
    	Connect.insertUser("database.db",strPseudo, strPass, 1);
    	System.out.println("Vous êtes maintenant inscrit !");
    	
    }
}
