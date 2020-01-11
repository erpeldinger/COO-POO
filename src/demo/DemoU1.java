package demo;

import java.lang.Object.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.io.IOException;


import user.*;
import communication.*;
import format.DateMsg;
import format.Message;

public class DemoU1 {
    
    public void writeM(OutputStream out, String msg) {
    	try {
        out.write(msg.getBytes());
    	}
    	catch (Exception e) {
    		System.out.println("erreur methode writeM");
    	}

    }

    public void readM(InputStream in, byte[] buff) { 
    	try {
	        while (in.available() <=0 ) {
	        }
	        in.read(buff);
    	}
    	catch (Exception e) {
		System.out.println("erreur methode writeM");
    	}
    }
	
	
    public static void main (String[] args){
    	

        //-----------------Création du serveur TCP------------------------------------- 
        ServerSocket serverSocket = null;
        Socket sockS = null;
        OutputStream outS = null;
        InputStream inS = null;
        try {
            serverSocket = new ServerSocket(1234); 
            sockS = serverSocket.accept();
            System.out.println("Serveur 1 ok : 1234");
        }
        catch (Exception e){
            System.out.println("Erreur création du server1");
        }
        try {
            outS = sockS.getOutputStream();
            inS = sockS.getInputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des inS1 et outS1");
        }

        
        //---------------Creation du client TCP---------------------------------------------------------------
        
        Socket sockC = null;
        OutputStream outC = null;
        InputStream inC = null;
        InetAddress addrC = null;
        try {
            addrC = InetAddress.getLocalHost();
            sockC = new Socket(addrC, 1234); 
            System.out.println("Client 1 ok : 1234");
        }
        catch (Exception e){
            System.out.println("Erreur création du socket client user1");
        }
        try {
            outC = sockC.getOutputStream();
            inC = sockC.getInputStream();
        }
        catch (Exception e){
            System.out.println("Erreur création des inC et outC");
        }


        //-------------------Communication--------------------------------------------------------
        byte[] buff = new byte[200];  
        readM(inS, buff);
        System.out.println("Message lu");
        String m1Lu = new String(buff);
        System.out.println("Le message lu est : " + m1Lu);
        
	        writeM(out_buff[1], m.getContent());
	        //System.out.println("Message envoyé");
	        byte[] buff = new byte[200];
	        readM(in_buff[1], buff);
	        System.out.println("Message lu");
	        String mLu = new String(buff);
	        System.out.println("Le message lu est : " + mLu);
	
	        //fermeture des différents sockets

	        sockC.close();
	        sockS.close();
	    	serverSocket.close();

	    }
}