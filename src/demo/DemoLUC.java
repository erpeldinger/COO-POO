package demo;

import java.lang.Object.*;
import java.net.*;
import java.io.IOException;

import format.*;

import java.util.ArrayList;
import java.util.Scanner; 

import requete.Connect;

public class DemoLUC {
    
    public static void main (String[] args){
    	// Creation de deux utilisateurs
    	Connect.createNewDatabase("database.db");
    	Connect.createNewTableUser("database.db");
    	Connect.createNewTableLUC("database.db");
    	Connect.insertUser("database.db", "Toto", "titi123456789", 1);
    	Connect.insertUser("database.db", "Tata", "tutu123456789", 2);
    	Connect.insertUser("database.db", "Tutu", "tutu123456789", 3);
    	Connect.insertUserLUCbyPseudo("database.db", "Toto", "ip-de-Toto");
    	Connect.insertUserLUCbyPseudo("database.db", "Tata", "ip-de-Tata");
    	Connect.insertUserLUCbyPseudo("database.db", "Tutu", "ip-de-Tutu");
    	
    	// Test du processus d'insertion de message
    	System.out.println("Bonjour");
    		
    	String ip1 = Connect.queryUserLUCbyPseudo("database.db", "Toto");
    	String ip2 = Connect.queryUserLUCbyPseudo("database.db", "Tata");
    	String ip3 = Connect.queryUserLUCbyPseudo("database.db", "Tutu");
    	
    	System.out.println("l'adresse ip de Toto est : " + ip1 + "\n");
    	System.out.println("l'adresse ip de Tata est : " + ip2 + "\n");
    	System.out.println("l'adresse ip de Tutu est : " + ip3 + "\n");
    	

    	Connect.deleteTable("database.db","User");
    	Connect.deleteTable("database.db","ListUserConnected");
    	System.out.println("delete ok");
    	
    	}
}
