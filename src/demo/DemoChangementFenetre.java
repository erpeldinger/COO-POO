package demo;
import java.io.IOException;

import inscription.*;

public class DemoChangementFenetre {
    
    public static void main (String[] args) throws IOException{
    	//Connect.createNewDatabase("database.db");
    	
    	//Connect.createNewTableUser("database.db");
    	//Connect.insertUser("database.db", "Toto", "12345678912", 1);
    	
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

    	Inscription pageInscription = new Inscription();
    	//Connexion pageConnexion = new Connexion();
    	//LUC pageLUC = new LUC(new User(1, "Toto", "123456789123"));
    	//Clavardage pageConv = new Clavardage();
    	//Profil monProfil = new Profil(new User(1, "Toto", "12345678912"));
    }
}
