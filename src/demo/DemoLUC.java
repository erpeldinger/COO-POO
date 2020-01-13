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
    	Connect.deleteTable("database","User");
    	Connect.createNewTableUser("database.db");
    	Connect.createNewTableLUC("database.db");
    	Connect.insertUser("database.db", "Toto", "titi123456789", 1);
    	Connect.insertUser("database.db", "Tata", "tutu123456789", 2);
    	Connect.insertUserLUC("database.db", 1, "ip-de-Toto");
    	Connect.insertUserLUC("database.db", 2, "ip-de-Tata");
    	
    	// Test du processus d'insertion de message
    	System.out.println("Bonjour");
    	
    	ArrayList <String> ip1 = Connect.queryUserLUC("database.db", 1);
    	ArrayList <String> ip2 = Connect.queryUserLUC("database.db", 2);
    	
    	System.out.println("l'adresse ip de Toto est : " + ip1.get(0) + "\n");
    	System.out.println("l'adresse ip de Tata est : " + ip2.get(0) + "\n");
    	
    	
    	}
}
