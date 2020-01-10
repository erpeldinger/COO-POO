package demo;

import java.lang.Object.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.io.IOException;


import user.User;
import communication.*;

public class DemoU1 {
    
    public static void main (String[] args){
    	
    	

        //-----------------Création d'un user et de son message-------------------------------------
    	
        /*User user = new User(1, "Toto1", "titi");
        Message m = user.createM("Bonjour, je suis Toto1. ");
        Message mBroadCast = user.createM("Broadcast : qui est là ?");
		*/
    	
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
        
        //----------------------Récupération des utilisateurs connectés -------------------------------
        
        // envoie de message en Broadcast, attente de réception et création de la liste des utlisateurs connectes
        
        
        
        /*
       	//Création du serveur UDP
        DatagramSocket  dgSocket = new DatagramSocket(1234);
        byte[] buffer = new byte[256];
        DatagramPacket inPacket= new DatagramPacket(buffer, buffer.length);
        dgSocket.receive(inPacket);
        InetAddress clientAddress= inPacket.getAddress();
        int clientPort= inPacket.getPort();
        String message = new String(inPacket.getData(), 0, inPacket.getLength());
        DatagramPacket outPacket= new DatagramPacket(response.getBytes(), response.length(),clientAddress, clientPort);
        dgSocket.send(outPacket);
        dgSocket.close();
        
        // Création du client UDP
        InetAddress host = ;
        int port = 3333;
        DatagramSocket dgSocketClient= new DatagramSocket();
        DatagramPacket outPacketClient= new DatagramPacket(message.getBytes(), message.length(),host, port);
        dgSocketClient.send(outPacket);
        byte[] bufferClient = new byte[256];
        DatagramPacket inPacketClient= new DatagramPacket(bufferClient, bufferClient.length);
        dgSocketClient.receive(inPacketClient);
        String response = new String(inPacketClient.getData(), 0, inPacketClient.getLength());
        dgSocketClient.close();
        */
        
        
        /*for(int port = 1;port<1300;port++){
    	    try{           	    	
    	    	//Envoi d'un message en broadcast
    	        user.writeM(outC, mBroadCast.getContent());
    	        //System.out.println("Message envoyé");
    	    }   
    	    catch (Exception e) {
    	    	System.out.println();
    	    }
        }
    	byte[] buff = new byte[200];  
        user.readM(inS, buff);
        System.out.println("Message lu");
        String m1Lu = new String(buff);
        System.out.println("Le message lu est : " + m1Lu);*/
	    
        
	    //------------------ Fermeture des sockets-----------------------------------------------------------
	    try {
	    	sockS.close();
	    	serverSocket.close();
	    }
	    catch (Exception e) {
	    	System.out.println("Erreur fermeture de sockets 1");
	    }        
	}                     
}


/*//---------------------Création du scanner------------------------- 

        Socket sock=null;
        OutputStream out = null;
        InputStream in = null;
        InetAddress addr = null;
        Socket sock_buff[]= null;
        OutputStream out_buff[] = null;
        InputStream in_buff[] = null;
        int i_sock = 0; 
        
	for(int port = 1;port<1300;port++){
	    try{       
	    	sock = new Socket(addrC, port);     
	        sock_buff[i_sock] = sock;
	        System.out.println("Création socket d'indice " + i_sock + " sur port : " + port);
	        i_sock++;
	    }
	    catch (Exception e) {
	    	System.out.println("Port " + port + " fermé");
	    }
	    
	    if (sock!=null) {
	        try {
	            out_buff[i_sock] = sock_buff[i_sock].getOutputStream();
	            in_buff[i_sock] = sock_buff[i_sock].getInputStream();
	        }
	        catch (Exception e){
	            System.out.println("Erreur création des in et out");
	        }
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
	
	    catch(Exception e){
	        //System.out.println("Port " + port + " fermé");
	    	}
	    }

*/