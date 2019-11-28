import java.lang.object.*;
/*
import Constantes.java;
import Affichage.java;
import GestionApp.java;
import Message.java;
import Session.java;
import SystConv.java;
import User.java; */
//import pour client/serveur
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class Demo {
    
    public static void main (String[] args) {

        //creation de deux user
        User user1 = new User(1, "Toto1", "titi");
        User user2 = new User(2, "Tata2", "tutu");

        //creation d'un message par user1
        Message m1 = user1.createM("Bonjour, je suis Toto1");

        //creation du client user1
        try {
            InetAddress addr1 = InetAddress.getLocalHost();
            Socket sock1 = new Socket(addr, 1234);
        }
        catch (Exception e){
            System.out.println("Erreur création du socket client user1");
        }
        try {
            OutputStream out1 = sock1.OutputStream();
            ImputStream in1 = sock1.InputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des in1 et out1");
        }

        //creation du serveur user1
        try {
            ServerSocket serverSocket1 = new Server(1234); //a voir pour les ports
            Socket sockS1 = serverSocket1.accept();
        }
        catch (Exception e){
            System.out.println("Erreur création du server socket user1");
        }
        try {
            OutputStream outS1 = sockS1.OutputStream();
            InputStream inS1 = sockS1.InputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des inS1 et outS1");
        }


        //creation du client user2
        try {
            InetAddress addr2 = InetAddress.getLocalHost();
            Socket sock2 = new Socket(addr, 1235);
        }
        catch (Exception e){
            System.out.println("Erreur création du socket client user2");
        }
        try {
            OutputStream out2 = sock2.OutputStream();
            InputStream in2 = sock2.InputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des in2 et out2");
        }

        //creation du serveur user2
        try {
            ServerSocket serverSocket2 = new Server(1234); //a voir pour les ports
            Socket sockS2 = serverSocket2.accept();
        }
        catch (Exception e){
            System.out.println("Erreur création du server socket user2");
        }
        try {
            OutputStream outS2 = sockS2.OutputStream();
            InputStream inS2 = sockS2.InputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des inS2 et outS2");
        }

        //fermeture des différents sockets
        sock1.close();
        sock2.close();
        sockS1.close();
        sockS2.close();
        serverSocket1.close();
        serverSocket2.close();

    }
    
}