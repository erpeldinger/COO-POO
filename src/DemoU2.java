import java.lang.Object.*;
import java.util.Date;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.lang.Thread;

public class DemoU2 {
    
    public static void main (String[] args) throws SocketException, Exception {
    	//Serveur


        /*------------------------ TEST envoi broadcast serveur-------------------------------------------- */
    	User u1 = new User(77,"UserAttendBroadcast","mdp",1288,BroadcastingClient.getBroadcastAddress());
    	
    	//ListenerUDP serveur = new ListenerUDP (1234,"serveur1",BroadcastingClient.getBroadcastAddress());
    	
    	//System.out.println(BroadcastingClient.getBroadcastAddress());
    	//BroadcastingClient.sendBroadcast();
    	//Serveur
    	DatagramSocket d1 = new DatagramSocket(1230);
    	byte[] buff = new byte[256];
    	DatagramPacket p = new DatagramPacket(buff, buff.length);
    	
    	
        //System.out.println("addr br serveur : " + serveur.getAddrBr());


        /*
    	//-----------------Création d'un user et de son message-------------------------------------
    	
        User user1 = new User(1, "Toto1", "titi");
        Message m1 = user1.createM("Bonjour, je suis Toto1. ");
        Message mBroadCast = user1.createM("Broadcast : who is here ?");
        
        User user2 = new User(2, "Toto2", "titi2");
        Message m2 = user2.createM("Bonjour, je suis Toto2");

        //-----------------Création du serveur1 TCP------------------------------------- 
        ServerSocket serverSocket1 = null;
        Socket sockS1 = null;
        OutputStream outS1 = null;
        InputStream inS1 = null;
        try {
            serverSocket1 = new ServerSocket(1234); 
            sockS1 = serverSocket1.accept();
            System.out.println("Serveur 1 ok : 1234");
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

        //-------erveur = ----------Création du serveur2------------------------------------- 
        ServerSocket serverSocket2 = null;
        Socket sockS2 = null;
        OutputStream outS2 = null;
        InputStream inS2 = null;
        try {
            serverSocket2 = new ServerSocket(1235); 
            sockS2 = serverSocket2.accept();
            System.out.println("Serveur 2 ok : 1235");
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


        
        //---------------Creation du client1 TCP---------------------------------------------------------------
        
        Socket sockC1 = null;
        OutputStream outC1 = null;
        InputStream inC1 = null;
        InetAddress addrC1 = null;
        try {
            addrC1 = InetAddress.getLocalHost();
            sockC1 = new Socket(addrC1, 1234); 
            System.out.println("Client 1 ok : 1234");
        }
        catch (Exception e){
            System.out.println("Erreur création du socket client user1");
        }
        try {
            outC1 = sockC1.getOutputStream();
            inC1 = sockC1.getInputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des inC et outC");
        }

        
        //---------------Creation du client2------------------------------------------

        Socket sockC2 = null;
        OutputStream outC2 = null;
        InputStream inC2 = null;
        InetAddress addrC2 = null;
        
        try {
            addrC2 = InetAddress.getLocalHost();
            sockC2 = new Socket(addrC2, 1235); 
            System.out.println("Client 2 ok : 1235");
        }
        catch (Exception e){
            System.out.println("Erreur création du socket client user2");
        }
        try {
            outC2 = sockC2.getOutputStream();
            inC2 = sockC2.getInputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des inC et outC");
        }
        */
        
    	//Création du user 1
		//S'ajoute dans sa liste de users connectés
		//Envoi d'un message pour demander la liste des users connectés en broadcast
		//A la réception d'un certain message, on ajoute son émetteur dans la liste des utilisateurs connectés
	//Création du user 2
		//Réception du message broadcast -> utilisation du listener UDP
		//Ajout du user dans sa table
		//Réponse au message
        
    }
}

        /*
        // Est-ce qu'il ne faudrait pas faire un if (on a reçu le message broadcast du serveur central) on répond au serveur central ??
        
        
        //Réponse au broadcast du serveur central (DemoU1)
        byte[] buff = new byte[200];
        user.readM(inS, buff);
        System.out.println("Message lu");
        String m1Lu = new String(buff);
        System.out.println("Le message lu est : " + m1Lu);
        
        user.writeM(out, m.getContent());
        System.out.println("Message envoyé");
    
       
	}                     
}*/

/* 
 *  
        //---------------------Création du scanner------------------------- 


        for(int port = 1;port<1300;port++){
            try{       
            	sock = new Socket(addrC, port);     
                sock_buff[i_sock] = sock;  
                System.out.println("Création socket d'indice " + i_sock + " sur port : " + port);
                i_sock++;
                try {
                    out_buff[i_sock] = sock_buff[i_sock].getOutputStream();
                    in_buff[i_sock] = sock_buff[i_sock].getInputStream();
                }
                catch (Exception e){
                    System.out.println("Erreur création des in et out");
                }
                
                //Communication
                
                user.writeM(out_buff[0], m.getContent());
                //System.out.println("Message envoyé");
                byte[] buff = new byte[200];
                user.readM(in_buff[0], buff);
                System.out.println("Message lu");
                String mLu = new String(buff);
                System.out.println("Le message lu est : " + mLu);

                //fermeture des différents sockets
                try {
                sock.close();
                }
                catch (Exception e) {
                	System.out.println("Erreur fermeture de sockets");
                }                
            }
            catch(Exception e){
                //System.out.println("Port " + port + " fermé");
            	}
            }
          //fermeture des sockets
            try {
            sockS.close();
            serverSocket.close();
            }
            catch (Exception e) {
            	System.out.println("Erreur fermeture de sockets 2");
            }
  
 * */