import java.lang.Object.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class Demo {
    
    public static void main (String[] args) {

        //creation de deux user
        User user1 = null;//new User(1, "Toto1", "titi");
        User user2 = null; //new User(2, "Tata2", "tutu");

        //creation d'un message par user1
        Message m1 = user1.createM("Bonjour, je suis Toto1");

        //creation du serveur user1
        ServerSocket serverSocket1 = null;
        Socket sockS1 = null;
        OutputStream outS1 = null;
        InputStream inS1 = null;
        try {
            serverSocket1 = new ServerSocket(1235); 
            sockS1 = serverSocket1.accept();
            System.out.println("Serveur 1 ok : 1235");
        }
        catch (Exception e){
            System.out.println("Erreur création du server1");
        }
        try {
            outS1 = sockS1.getOutputStream();
            inS1 = sockS1.getInputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des inS1 et outS1");
        }

        //creation du serveur user2
        ServerSocket serverSocket2 = null;
        Socket sockS2 = null;
        OutputStream outS2 = null;
        InputStream inS2 = null;
        try {
            serverSocket2 = new ServerSocket(1234); 
            sockS2 = serverSocket2.accept();
            System.out.println("Serveur 2 ok : 1234");
        }
        catch (Exception e){
            System.out.println("Erreur création du server2");
        }
        try {
            outS2 = sockS2.getOutputStream();
            inS2 = sockS2.getInputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des inS2 et outS2");
        }
        //creation du client user1
        /*
        Socket sock1=null;
        OutputStream out1 = null;
        InputStream in1 = null;
        try {
            InetAddress addr1 = InetAddress.getLocalHost();
            sock1 = new Socket(addr1, 1234); //client1 = serveur 2 et inversement
            System.out.println("Client 1 ok : 1234");
        }
        catch (Exception e){
            System.out.println("Erreur création du socket client user1");
        }
        try {
            out1 = sock1.getOutputStream();
            in1 = sock1.getInputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des in1 et out1");
        }
        */
        
        //creation du client user2
        Socket sock2 = null;
        OutputStream out2 = null;
        InputStream in2 = null;
        try {
            InetAddress addr2 = InetAddress.getLocalHost();
            sock2 = new Socket(addr2, 1235); 
            System.out.println("Client 2 ok : 1235");
        }
        catch (Exception e){
            System.out.println("Erreur création du socket client user2");
        }
        try {
            out2 = sock2.getOutputStream();
            in2 = sock2.getInputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des in2 et out2");
        }
        
        //Envoi et réception de messages
        System.out.println("Debut de la communication");
        user1.writeM(out2, m1.getContent());
        System.out.println("Message envoyé");
        byte[] buff = new byte[200];
        user2.readM(inS1, buff);
        System.out.println("Message lu");
        String m1Lu = new String(buff);
        System.out.println("Le message lu est : " + m1Lu);

        //fermeture des différents sockets
        try {
        //sock1.close();
        sock2.close();
        sockS1.close();
        //sockS2.close();
        serverSocket1.close();
        //serverSocket2.close();
        }
        catch (Exception e) {
        	System.out.println("Erreur fermeture de sockets");
        }

    }
    
}