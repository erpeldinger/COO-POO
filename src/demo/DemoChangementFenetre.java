package demo;
import java.io.IOException;

import inscription.*;
import requete.Connect;

public class DemoChangementFenetre {
    
    public static void main (String[] args) throws IOException{
    	Connect.createNewDatabase("database.db");
    	Connect.createNewTableUser("database.db");
    	
    	/*Principe de la demo
    	 1 - lancer cette demo
    	 2 - s'inscrire (mettre un pseudo et un password, cliquer sur s'inscrire)
    	 3 - se connecter(mettre le pseudo et le password, cliquer sur se connecter)
    	 4 - Choisir un utilisateur connecté, et commence une conversation avec lui (tapper son pseudo dans le champ dédié, cliquer sur "Demarrer une conversation")
    	 5 - Envoyer un message (tapper le message dans le champ, cliquer sur "Envoyer le message")
    	 6 - retourner voir qui est connecte (cliquer sur le bouton retour)
    	 7 - retourner dans votre conversation voir comment l'historique s'affiche
    	*/

    	Inscription pageInscription = new Inscription();
    	//Connexion pageConnexion = new Connexion();
    	//LUC pageLUC = new LUC(new User(1, "Toto", "123456789123"));
    	//Clavardage pageConv = new Clavardage();
    	//Profil monProfil = new Profil(new User(1, "Toto", "12345678912"));
    }
}
