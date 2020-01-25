
package clavardage;


/*
 * SwingApplication.java is a 1.4 example that requires
 * no other files.
 */
import javax.swing.*;

import connexion.Connexion;
import profil.Profil;
import requete.Connect;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import communication.*;
import LUC.*;
import user.*;

public class Clavardage implements ActionListener {
    
    // L'etat de l'envoi du message
	private User user;
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
    
    //Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
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
        button.addActionListener(this);
        labelMessage.setLabelFor(button);
        button.setPreferredSize(new Dimension(10,50));
        
        //button de retour vers la page des LUC
        JButton retour = new JButton("Retour");
        retour.setMnemonic(KeyEvent.VK_I);
        retour.addActionListener(this);
        labelRetour.setLabelFor(retour);
        retour.setPreferredSize(new Dimension(10,50));
        
        /*
         * An easy way to put space between a top-level container
         * and its contents is to put the contents in a JPanel
         * that has an "empty" border.
         */
        JPanel pane = new JPanel(new GridLayout(0, 1));
        pane.add(retour);
        pane.add(Conversation);
        pane.add(labelError);
        pane.add(messageField);
        pane.add(button);
        pane.add(labelMessage);
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
	                
	                //Sauvegarder le message dans la base de donnees
	                
	                //Afficher le message
	                ConvArea.setText(ConvArea.getText() + "\n" + "You : " + messageField.getText());
	
	                //rendre vide le champ messageField
	                messageField.setText("");
	            }
	            catch (Exception ex){
	                labelMessage.setText(labelPrefix + etatEnvoiEnCours);
	            }
	        }
    	}
	    else if (e.getActionCommand().contentEquals("Retour")) {
	    	messageField.setText("");
	    	ConvArea.setText("");
	    	try {
				LUC pageLUC = new LUC(user);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		frame.setVisible(false);
	    }
    	else if (e.getActionCommand().equals("Profil")) {
    		Profil monProfil = new Profil(this.user);
    		frame.setVisible(false);
    	}
    	else if (e.getActionCommand().equals("Liste des utilisateurs connectes")) {
    		try {
				LUC maLUC = new LUC(this.user);
			} catch (IOException e1) {
				System.out.println("[ERROR PROFIL] Creation de la page LUC impossible : " + e);
			}
    		frame.setVisible(false);
    	}
    	else if (e.getActionCommand().equals("Se deconnecter")) {
    		// TODO
    		Connect.deleteUserLUC("database.db", this.user.getId());
    		frame.setVisible(false);
    	}
    }
    
    private static void initLookAndFeel() {
        
        // Swing allows you to specify which look and feel your program uses--Java,
        // GTK+, Windows, and so on as shown below.
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
    public Clavardage(User user, int id2) throws UnknownHostException, Exception {
    	//init user
    	this.user = user;
    	Connect.createNewTableLUC("database.db");
    	//int port = Connect.queryPortLUC("database.db", id2);
    	//System.out.println("port recupere : " + port);
    	String ipS = Connect.queryUserLUC("database.db", id2);
    	System.out.println("[CLAVARDAGE] ip recupere string : " + ipS);
    	
    	// POSSIBILITE : Mettre le port en dur pour tester ---> j'ai pas pu tester parce que j'arrive pas à lancer l'appli depuis le terminal (seulemen depuis eclipse)
    	// int port = 2007;
    	int port = Connect.queryPortLUC("database.db",id2);
    	System.out.println("port recupere : " + port);
    	ArrayList <Integer> Ports = Connect.queryAllPortLUC("database;db");
    	for (Integer courant : Ports) {
    		System.out.println(courant + "\n");
    	}
    	
    	String parts[] = ipS.split("/");
    	System.out.println("[CLAVARDAGE] ip recupere split : " + parts[1]);
    	this.client = new TCPClient(InetAddress.getByName(parts[1]), port, user, id2);
        //Set the look and feel.
        initLookAndFeel();
        
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        this.frame = new JFrame("Clavardage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ConvArea.setEditable(false);
        

        // afficher l'historique des message
        Connect.createNewTableConv("database.db");
        System.out.println("User : " + user.getId() + " user 2 " + id2);
    	ArrayList <String> Users = Connect.queryHistorique("database.db", user.getId(), id2);
    	for ( String courant : Users) {
    		//System.out.println("message recupere : " + courant);
    		if (!courant.contentEquals("end") && !courant.contentEquals("1end")) {
    			ConvArea.setText(ConvArea.getText() + "\n" + courant );
    		}
    	}
        
        //Clavardage app = new Clavardage();
        Component contents = createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    /*
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    */
}