import java.lang.Object.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.lang.Thread;

public class DemoU3 {
    
    public static void main (String[] args) {

      /*  //-----------------Création d'un user et de son message-------------------------------------
        User user = new User(3, "Toto3", "titi");
        Message m = user.createM("Bonjour, je suis Toto3");

        //-----------------Création du serveur------------------------------------- 
        ServerSocket serverSocket = null;
        Socket sockS = null;
        OutputStream outS = null;
        InputStream inS = null;
        try {
            serverSocket = new ServerSocket(1236); 
            sockS = serverSocket.accept();
            System.out.println("Serveur 3 ok : 1236");
        }
        catch (Exception e){
            System.out.println("Erreur création du server3");
        }
        try {
            outS = sockS.getOutputStream();
            inS = sockS.getInputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des inS3 et outS3");
        }

        
        //---------------Creation du client------------------------------------------
        
        Socket sock=null;
        OutputStream out = null;
        InputStream in = null;
        InetAddress addr = null;
        Socket sock_buff[]= null;
        OutputStream out_buff[] = null;
        InputStream in_buff[] = null;
        int i_sock = 0; 
        InetAddress addrC = null;
        
        
      //creation du client 
        Socket sockC = null;
        OutputStream outC = null;
        InputStream inC = null;
        try {
            addrC = InetAddress.getLocalHost();
            sockC = new Socket(addrC, 1236); 
            System.out.println("Client 3 ok : 1236");
        }
        catch (Exception e){
            System.out.println("Erreur création du socket client user3");
        }
        try {
            outC = sockC.getOutputStream();
            inC = sockC.getInputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des inC et outC");
        }
               
        
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
                
                user.writeM(out_buff[1], m.getContent());
                //System.out.println("Message envoyé");
                byte[] buff = new byte[200];
                user.readM(in_buff[1], buff);
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
            	System.out.println("Erreur fermeture de sockets 3");
            }
            */
	}                     
}