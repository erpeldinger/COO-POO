package communication;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import user.User;
import format.Message;
import requete.Connect;
import format.*;

public class TCPClient {

	//Attributs
	private Socket socket;
	private User user;
	private int destId;
	private int serverPort;
	
	//Constructeurs
	public TCPClient(InetAddress srvAddr, int srvPort, User user, int destId) throws Exception {
        System.out.println("[TCP Client] constructeur - avant Socket");
		this.socket = new Socket(srvAddr, srvPort);
        System.out.println("[TCP Client] constructeur2 - apres socket");
		this.user = user;
		this.destId= destId;
		this.serverPort=srvPort;
	}
	
	//getter
	public int getServerPort() {return this.serverPort; }
	
	//Ecrit un message
	public void writeM(OutputStream out, String msg) {
    	try {
    		System.out.println("[TCP CLIENT] Message a envoyer : " + msg);
    		Message toSend = Message.toSend(this.user.getId(), msg);
    		byte[] buff = Message.readyToSend(toSend);
    		out.write(buff);    		
    		System.out.println("[TCP CLIENT] Message envoye : " + msg);

    		//Stockage dans la bd
    		Connect.createNewDatabase("database.db");
        	Connect.createNewTableConv("database.db");
        	//ajout du pseudo de l'expediteur du message dans la BD
        	String dateMessage = DateMsg.toString(toSend.getDate());
        	System.out.println("[TCP Client] Message à enregistrer, date : " + dateMessage + " de id : " + this.user.getId());
    		Connect.insertConversation("database.db", this.user.getId(), this.destId, Connect.queryUserPseudo("database.db", this.user.getId()) + " : " + toSend.getContent() , dateMessage);
    		System.out.println("[TCP CLIENT] message enregistre dans la db \n");
    		
    	}
    	catch (Exception e) {
    		System.out.println("[TCPClient] Erreur writeM");
    	}    			
    }
	
	//Envoie le message
	public void sendMessage(String m) {
		try {
			System.out.println("[TCP CLIENT] sendMessage -> debut \n");
			OutputStream out = this.socket.getOutputStream();
			System.out.println("[TCP CLIENT] sendMessage -> avant writeM \n");
			writeM(out, m);
			System.out.println("[TCP CLIENT] sendMessage -> apres writeM \n");
			out.flush();
			out.close();
			this.socket.close();
		}			
        catch (Exception e){
            System.out.println("[TCPClient ] Erreur sendMessage, write ");
        }		
		
	/*
	public void sendFile(String path) {
		        File file = new File (file);							//gets the file corresponding to path
		        String fileString = file.getName();							//retrieves the file name
		        byte [] byte_file  = new byte [(int)file.length()];	//makes a byte that will contain the file's data
		        FileInputStream fis = new FileInputStream(file);		//opens the input stream of the file
		        BufferedInputStream bis = new BufferedInputStream(fis);	//links it to the buffered input stream
		        bis.read(byte_file,0,byte_file.length);					//buffered input stream writes into byte_file
		        bis.close();											//close buffered output stream
		        
		        PacketFile packet = new PacketFile(byte_file,name);		//puts the byte_file + file name into serializable packet
	.	        byte[] serialized_file = serialize(packet);				//serializes it into bytes[]
		        
		        OutputStream os = this.socket.getOutputStream();		//retrieves the output stream of the socket
		        os.write(serialized_file,0,serialized_file.length);		//writes the bytes into the stream
		        os.flush();												//flushes the stream
		        os.close();	        									//closes the stream
		        this.socket.close();									//closes the socket
		    }
	}	
	*/
	}
	
}
