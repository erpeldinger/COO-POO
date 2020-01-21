package demo;
import java.lang.Object.*;
import java.net.*;
import java.io.IOException;

import java.util.Scanner;

import LUC.*;
import requete.Connect;
import connexion.*;
import clavardage.*;
import profil.*;
import user.User;
import inscription.*;

public class DemoChangementFenetre {
    
    public static void main (String[] args) throws SocketException, UnknownHostException{
    	Connect.createNewDatabase("database.db");
    	Connect.createNewTableUser("database.db");
    	Connect.insertUser("database.db", "Toto", "12345678912", 1);
    	
    	//Inscription pageInscription = new Inscription();
    	/*Principe de la demo
    	 1 - Supprimer la BD (dans database, supprimer le fichier database.db)
    	 2 - lancer cette demo
    	 3 - s'inscrire (mettre un pseudo et un password, cliquer sur s'inscrire)
    	 4 - cliquer sur le bouton de connecter
    	 5 - se connecter (mettre le pseudo et le password, cliquer sur se connecter)
    	 6 - Choisir un utilisateur connecté, et commence une conversation avec lui (tapper son pseudo dans le champ dédié, cliquer sur "Demarrer une conversation")
    	 7 - Envoyer un message (tapper le message dans le cahmp, cliquer sur "Envoyer le message")
    	 8 - retourner voir qui est connecte (cliquer sur le bouton retour)
    	*/
    	
    	//Connexion pageConnexion = new Connexion();
    	//LUC pageLUC = new LUC();
    	//Clavardage pageConv = new Clavardage();
    	Profil monProfil = new Profil(new User(1, "Toto", "12345678912"));
    }
}
