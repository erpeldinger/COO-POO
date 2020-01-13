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
    	Connect.insertUserLUC("database.db", 1, "ip-de-Toto");
    	Connect.insertUserLUC("database.db", 2, "ip-de-Tata");
    	Connect.insertUserLUC("database.db", 3, "ip-de-Tutu");
    	
    	// Test du processus d'insertion de message
    	System.out.println("Bonjour");
    		
    	String ip1 = Connect.queryUserLUC("database.db", 1);
    	String ip2 = Connect.queryUserLUC("database.db", 2);
    	String ip3 = Connect.queryUserLUC("database.db", 3);
    	
    	System.out.println("l'adresse ip de Toto est : " + ip1 + "\n");
    	System.out.println("l'adresse ip de Tata est : " + ip2 + "\n");
    	System.out.println("l'adresse ip de Tutu est : " + ip3 + "\n");
    	
    	

    	Connect.deleteTable("database","User");
    	Connect.deleteTable("database","ListUserConnected");
    	System.out.println("delete ok");
    	
    	}
}
