package demo;

import java.lang.Object.*;
import java.net.*;
import java.io.IOException;

import format.*;

import java.util.ArrayList;
import java.util.Scanner; 

import requete.Connect;

public class DemoHistorique {
    
    public static void main (String[] args){
    	// Creation de deux utilisateurs
    	Connect.createNewDatabase("database.db");
    	Connect.deleteTable("database.db", "User");
    	Connect.deleteTable("database.db", "Conversation");
    	Connect.createNewTableUser("database.db");
    	Connect.createNewTableConv("database.db");
    	Connect.insertUser("database.db", "Toto", "titi123456789", 1);
    	Connect.insertUser("database.db", "Tata", "tutu123456789", 2);
    	
    	// Test du processus d'insertion de message
    	System.out.println("Bonjour");
    	// Demande du 1er message (1 vers 2)
    	Scanner scMsg = new Scanner(System.in);
    	System.out.println("Veuillez saisir votre message :");
    	String strMsg = scMsg.nextLine();
    	Message msgHorodate = Message.toSend(1, strMsg);
    	//insertion du message dans la bd
    	Connect.insertConversation("database.db",1,2,msgHorodate.getContent(),DateMsg.toString(msgHorodate.getDate()));
    	System.out.println("Message insere : " + msgHorodate.getContent() + " " + DateMsg.toString(msgHorodate.getDate()));
    	
    	// Demande du 2eme message (2 vers 1)
    	Scanner scMsg2 = new Scanner(System.in);
    	System.out.println("Veuillez saisir votre message :");
    	String strMsg2 = scMsg2.nextLine();
    	Message msgHorodate2 = Message.toSend(1, strMsg2);
    	//insertion du message dans la bd
    	Connect.insertConversation("database.db",2,1,msgHorodate2.getContent(),DateMsg.toString(msgHorodate2.getDate()));
    	System.out.println("Message insere : " + msgHorodate2.getContent() + " " + DateMsg.toString(msgHorodate2.getDate()));
    	

    	// Demande du 3eme message (1 vers 2)
    	Scanner scMsg3 = new Scanner(System.in);
    	System.out.println("Veuillez saisir votre message :");
    	String strMsg3 = scMsg3.nextLine();
    	Message msgHorodate3 = Message.toSend(1, strMsg3);
    	//insertion du message dans la bd
    	Connect.insertConversation("database.db",1,2,msgHorodate3.getContent(),DateMsg.toString(msgHorodate3.getDate()));
    	System.out.println("Message insere : " + msgHorodate3.getContent() + " " + DateMsg.toString(msgHorodate3.getDate()));
    	
    	/*
    	//Recuperation des messages
    	ArrayList <String> msg12 = Connect.queryConversation("database.db",1, 2);
    	ArrayList <String> msg21 = Connect.queryConversation("database.db",2, 1);
    	//Affichage des messages
    	System.out.println("les messages de Toto vers Tata : ");
    	for (String courant : msg12) {
        	System.out.println(courant + "\n");
    	}
    	System.out.println("les messages de tata vers Toto : ");
    	for (String courant : msg21) {
        	System.out.println(courant + "\n");
    	}*/
    	
    	//Recuperation d'un historique : 
    	ArrayList <String> msg = Connect.queryConversation("database.db",1, 2);
    	msg.remove(msg.size()-1); // on supprime le "end"
    	System.out.println("Les messages ordonnes : \n");
    	for (String courant : msg) {
        	System.out.println(courant + "\n");
    	}
    	
    }
}
