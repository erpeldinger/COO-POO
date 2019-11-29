import java.lang.Object.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.lang.Thread;

public class DemoU2 {
    
    public static void main (String[] args) {

        //-----------------Création d'un user et de son message-------------------------------------
        User user = new User(2, "Toto2", "titi");
        Message m = user.createM("Bonjour, je suis Toto2");

        //-----------------Création du serveur------------------------------------- 
        ServerSocket serverSocket = null;
        Socket sockS = null;
        OutputStream outS = null;
        InputStream inS = null;
        try {
            serverSocket = new ServerSocket(1235); 
            sockS = serverSocket.accept();
            System.out.println("Serveur 2 ok : 1235");
        }
        catch (Exception e){
            System.out.println("Erreur création du server2");
        }
        try {
            outS = sockS.getOutputStream();
            inS = sockS.getInputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des inS2 et outS2");
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
            sockC = new Socket(addrC, 1235); 
            System.out.println("Client 2 ok : 1235");
        }
        catch (Exception e){
            System.out.println("Erreur création du socket client user2");
        }
        try {
            outC = sockC.getOutputStream();
            inC = sockC.getInputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des inC et outC");
        }
        
        /* Est-ce qu'il ne faudrait pas faire un if (on a reçu le message broadcast du serveur central) on répond au serveur central ??*/
        
        /* A mettre dans le serveur 
         * while(!demande_reçue){
            while(in.available()<=0){
            }
            in.read(b);
            demande_reçue = true;
            try{
                LocalDateTime now = LocalDateTime.now();  
                out.write((new String (dtf.format(now))).getBytes()); //TO STRING A MODIFIER
            }
            catch(Exception e){
                System.out.println("erreur dans l'envoie de la date");
            }
        }
        
        
        dans client :
        
        try{
            while(in.available()<=0){
            }
            byte[] b = new byte[200];
            in.read(b);
            System.out.println("TIME : " + new String(b));
        }catch (Exception e){
            System.out.println("Erreur read");
        }
         * */
        
        
        //Réponse au broadcast du serveur central (DemoU1)
        byte[] buff = new byte[200];
        user.readM(inS, buff);
        System.out.println("Message lu");
        String m1Lu = new String(buff);
        System.out.println("Le message lu est : " + m1Lu);
        
        user.writeM(out, m.getContent());
        System.out.println("Message envoyé");
    
       
	}                     
}

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