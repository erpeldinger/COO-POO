
package clavardage;
import javax.swing.*;

import profil.Profil;
import requete.Connect;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import communication.*;
import LUC.*;
import user.*;

public class Clavardage implements ActionListener {
    
    // L'etat de l'envoi du message
	private User user;
	private int id2;
	private int port;
	private String ipTCP;
	private JFrame frame;
	private TCPClient client;
    private static String labelPrefix = "Etat du message : ";
    private static String etatEnCreation = "EnCreation";
    private static String etatEnvoye = "Envoyé";
    private static String etatEnvoiEnCours = "En cours d'envoi";
    private static String etatErreur = "Non envoyé, erreur";
    final static JLabel labelMessage = new JLabel(labelPrefix + etatEnCreation);
    final static JLabel labelRetour = new JLabel("");
    
    //Affichage des différents messages
    private static JTextArea ConvArea = new JTextArea(20, 50);
    private static JScrollPane Conversation = new JScrollPane(ConvArea);
    
    // Messages d'erreur
    final static JLabel labelError = new JLabel("");
    private static String emptyMessageField = "Envoi impossible : message vide";
    
    // Le champ permettant d'ecrire le message
    JTextField messageField = new JTextField(2);

    final static String LOOKANDFEEL = null;
    
    
    public Component createComponents() {
    	
    	// creation du menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menu1 = new JMenu("Mon profil");
		menu1.addActionListener(this);
		JMenuItem pageProfil = new JMenuItem("Profil");
		menu1.add(pageProfil);
		pageProfil.addActionListener(this);
		JMenu menu2 = new JMenu("Liste des utilisateurs connectes");
		JMenuItem pageLUC = new JMenuItem("Liste des utilisateurs connectes");
		menu2.add(pageLUC);
		pageLUC.addActionListener(this);
		menu2.addActionListener(this);
		JMenu menu3 = new JMenu("Se deconnecter");
		JMenuItem pageDeco = new JMenuItem("Se deconnecter");
		menu3.add(pageDeco);
		pageDeco.addActionListener(this);
		menu3.addActionListener(this);
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		this.frame.setJMenuBar(menuBar);
		
    	//creation du button d'envoie de message
        JButton button = new JButton("Envoyer le message");
        button.setMnemonic(KeyEvent.VK_I);
        //Specify the look and feel to use.  Valid values:
        //null (use the default), "Metal", "System", "Motif", "GTK+"
        button.addActionListener(this);
        labelMessage.setLabelFor(button);
        button.setPreferredSize(new Dimension(10,50));
        
        //button de retour vers la page des LUC
        JButton retour = new JButton("Retour");
        retour.setMnemonic(KeyEvent.VK_I);
        retour.addActionListener(this);
        labelRetour.setLabelFor(retour);
        retour.setPreferredSize(new Dimension(10,50));

    	//creation du button d'affichage des nouveaux messages
        JButton raffraichir = new JButton("raffraichir");
        raffraichir.setMnemonic(KeyEvent.VK_I);
        raffraichir.addActionListener(this);
        labelMessage.setLabelFor(raffraichir);
        raffraichir.setPreferredSize(new Dimension(10,50));

        JPanel pane = new JPanel(new GridLayout(0, 1));
        pane.add(retour);
        pane.add(Conversation);
        pane.add(labelError);
        pane.add(messageField);
        pane.add(button);
        pane.add(labelMessage);
        pane.add(raffraichir);
        pane.setBorder(BorderFactory.createEmptyBorder(
                30, //top
                30, //left
                10, //bottom
                30) //right
                );
        
        labelError.setForeground(Color.red);
        return pane;
    }
    
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("Envoyer le message") ) {
	        //Si le message est vide on ne l'envoie pas , n affiche l'erreur
	        if (messageField.getText().equals("")) {
	            labelError.setText(emptyMessageField);
	        }
	        //Sinon on l'envoie
	        else {
	            labelError.setText("");
	            labelMessage.setText(labelPrefix + etatEnvoiEnCours);
	            try {
	                //Envoyer le message
	                labelMessage.setText(labelPrefix + etatEnvoye);
	                
	                //Envoie + Sauvegarde du message dans la base de donnees
	                this.client.sendMessage(messageField.getText());
	                
	                //Afficher le message
	                ConvArea.setText(ConvArea.getText() + "\n" + "You : " + messageField.getText());
	
	                //rendre vide le champ messageField
	                messageField.setText("");
	            }
	            catch (Exception ex){
	                labelMessage.setText(labelPrefix + etatEnvoiEnCours);
					AlerteMessage error = new AlerteMessage("null", "null", 3);
	            }
	        }
	        //reset du TCPClient
        	try {
				this.client = new TCPClient(InetAddress.getByName(this.ipTCP), this.port, user, id2);
			} catch (Exception e1) {
				System.out.println("reset du TCPClient");
				AlerteMessage error = new AlerteMessage("null", "null", 3);
			}
    	}
	    else if (e.getActionCommand().contentEquals("Retour")) {
	    	messageField.setText("");
	    	ConvArea.setText("");
	    	try {
				LUC pageLUC = new LUC(user);
				this.client.stopClavardage();
			} catch (IOException e1) {
				System.out.println("[CLAVARDAGE] eRREUR : " + e1);
				AlerteMessage error = new AlerteMessage("null", "null", 3);
			}
    		frame.setVisible(false);
	    }
    	else if (e.getActionCommand().equals("Profil")) {
			this.client.stopClavardage();
    		Profil monProfil = new Profil(this.user);
    		frame.setVisible(false);
    	}
    	else if (e.getActionCommand().equals("Liste des utilisateurs connectes")) {
    		try {
				this.client.stopClavardage();
				LUC maLUC = new LUC(this.user);
			} catch (IOException e1) {
				System.out.println("[ERROR PROFIL] Creation de la page LUC impossible : " + e);
				AlerteMessage error = new AlerteMessage("null", "null", 3);
			}
    		frame.setVisible(false);
    	}
    	else if (e.getActionCommand().equals("Se deconnecter")) {
    		// Envoie d'un message de deconnexion en Broadcast
    		try {
				this.client.stopClavardage();
				BroadcastingClient.sendDisconnected(BroadcastingClient.getBroadcastAddress());
				//this.manager.stopCommunication();
			} catch (Exception e1) {
				System.out.println("[ERROR LUC] Broadcast de Deconnexion" + e1);
				AlerteMessage error = new AlerteMessage("null", "null", 3);
			}
    		
    		//delete ma LUC
    		Connect.deleteAllUserLUC("database.db");
    		Connect.deleteTable("database.db", "ListUserConnected");
    		frame.setVisible(false);
    	}
    	else if (e.getActionCommand().equals("raffraichir")) {
    		// on reset l'affichage
    		ConvArea.setText("");
    		// on va chercher les messages dans la BD
    		ArrayList <String> historiqueRecup = Connect.queryHistorique("database.db", user.getId(), id2);
        	for ( String courant : historiqueRecup) {
        		if (!courant.contains("end")) {
        			ConvArea.setText(ConvArea.getText() + "\n" + courant );
        		}
        	}
        	try {
				this.client = new TCPClient(InetAddress.getByName(this.ipTCP), this.port, user, id2);
			} catch (Exception e1) {
				System.out.println("reset du TCPClient");
				AlerteMessage error = new AlerteMessage("null", "null", 3);
			}
    	}
    }   
    
    private static void initLookAndFeel() {
        
        String lookAndFeel = null;
        if (LOOKANDFEEL != null) {
            if (LOOKANDFEEL.equals("Metal")) {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("System")) {
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("Motif")) {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            } else if (LOOKANDFEEL.equals("GTK+")) { //new in 1.4.2
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            } else {
                System.err.println("Unexpected value of LOOKANDFEEL specified: "
                        + LOOKANDFEEL);
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }
            
            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:"
                        + lookAndFeel);
                System.err.println("Did you include the L&F library in the class path?");
                System.err.println("Using the default look and feel.");
            } catch (UnsupportedLookAndFeelException e) {
                System.err.println("Can't use the specified look and feel ("
                        + lookAndFeel
                        + ") on this platform.");
                System.err.println("Using the default look and feel.");
            } catch (Exception e) {
                System.err.println("Couldn't get specified look and feel ("
                        + lookAndFeel
                        + "), for some reason.");
                System.err.println("Using the default look and feel.");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * @throws Exception 
     * @throws UnknownHostException 
     */
    public Clavardage(User user, int id2, String pseudoDest) throws UnknownHostException, Exception {
    	
    	this.user = user;
    	this.id2=id2;
    	Connect.createNewTableLUC("database.db");
    	String ipS = Connect.queryUserLUCbyPseudo("database.db", pseudoDest);
    	
    	this.port = Connect.queryPortLUC("database.db",id2);
    	
    	String parts[] = ipS.split("/");

    	this.ipTCP = parts[1];
    	this.client = new TCPClient(InetAddress.getByName(parts[1]), this.port, user, id2);
    	
        //Set the look and feel.
        initLookAndFeel();
        
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        this.frame = new JFrame("Clavardage");

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowListener(){
			public void windowOpened(WindowEvent e){}
			public void windowClosing(WindowEvent e){
		    	
		    		System.out.println("[PROFIL] fermeture appli");
					// Envoi d'un message de deconnexion en Broadcast
					try {
						BroadcastingClient.sendDisconnected(BroadcastingClient.getBroadcastAddress());
					} catch (Exception e1) {
						System.out.println("[ERROR LUC] Broadcast de Deconnexion" + e1);
						AlerteMessage error = new AlerteMessage("null", "null", 3);
					}
					
					//delete ma LUC
					Connect.deleteAllUserLUC("database.db");
					Connect.deleteTable("database.db", "ListUserConnected");
					frame.setVisible(false);
			}
			public void windowClosed(WindowEvent e){}
			public void windowIconified(WindowEvent e){}
			public void windowDeiconified(WindowEvent e){}
			public void windowActivated(WindowEvent e){}
			public void windowDeactivated(WindowEvent e){}
		});
        
        ConvArea.setEditable(false);
        
        // afficher l'historique des message
        Connect.createNewTableConv("database.db");
    	ArrayList <String> Users1 = Connect.queryHistorique("database.db", user.getId(), id2);
    	for ( String courant : Users1) {
    		if (!courant.contains("end")) {
    			ConvArea.setText(ConvArea.getText() + "\n" + courant );
    		}
    	}

        Component contents = createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}