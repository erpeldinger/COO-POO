
package connexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import requete.Connect;
import LUC.*;
import clavardage.AlerteMessage;
import communication.BroadcastingClient;
import user.*;
public class Connexion implements ActionListener {
    
    // Tous les labels
	private JFrame frame;
    private static String pseudo = "Pseudo : ";
    private String password = "Mot de passe : ";
    private String connect = "Se connecter";
    private String incorrectPseudo = "Pseudo incorrect";
    private String incorrectPassword = "Mot de passe incorrect";
    private String incorrectUser = "Pseudo/Mot de passe incorrect";
    private String correctUser = "Connexion reussie";
    private static String errorPrefix= "Connexion impossible : ";
    private static String connected = "Connexion réussie";
    private static String errorConnect = "Une ereure s'est produite, la connexion a échouée";
    final JLabel labelConnect = new JLabel(connect);
    final JLabel labelPseudo = new JLabel(pseudo);
    final JLabel labelPassword = new JLabel(password);
    final JLabel labelSuccess = new JLabel("");
    final JLabel labelError = new JLabel("");
	
    Color myGreen = new Color(11, 102, 35);
    
    // Les champs de textes
    JTextField pseudoField = new JTextField(2);
    JTextField passwordField = new JTextField(2);
    
    final static String LOOKANDFEEL = null;
    
    //creation du button d'envoie de message
    public Component createComponents() {
        JButton button = new JButton("Se connecter");
        button.setMnemonic(KeyEvent.VK_I);
        button.addActionListener(this);
        labelConnect.setLabelFor(button);

        JPanel pane = new JPanel(new GridLayout(0, 1));
        pane.add(labelError);
        pane.add(labelPseudo);
        pane.add(pseudoField);
        pane.add(labelPassword);
        pane.add(passwordField);
        pane.add(button);
        pane.add(labelSuccess);
        pane.setBorder(BorderFactory.createEmptyBorder(
                30, //top
                30, //left
                10, //bottom
                30) //right
                );
        
        labelError.setForeground(Color.red);
        labelSuccess.setForeground(myGreen);
        return pane;
    }
    
    public void actionPerformed(ActionEvent e) {
        labelError.setText("");
        //Si le message est vide on ne l'envoie pas , n affiche l'erreur
        if (pseudoField.getText().equals("")) {
            labelError.setText(incorrectPseudo);
        }
        else if(passwordField.getText().equals("")) {
            labelError.setText(incorrectPassword);
        }
        //Sinon on se connecte
        else {
        	Connect.createNewDatabase("database.db");
        	Connect.createNewTableUser("database.db");
       
        	if (Connect.checkIsUser("database.db", pseudoField.getText(), passwordField.getText())) {
        		// connexion ok
        		try {
	            User user = new User( Connect.queryUser("database.db", pseudoField.getText(), passwordField.getText()),pseudoField.getText(), passwordField.getText(), 1234);

	            //lancement du broadcast
	            user.allowBroadcast(new BroadcastingClient(user.getListener().getDatagramSocket(),1234, user));
	            LUC pageLUC = new LUC(user);
        		}
        		catch (Exception j) {
        			System.out.println("[CONNEXION] ERROR Creation user impossible " + j);
					AlerteMessage error = new AlerteMessage("null", "null", 3);
        		}

	            labelError.setText("");
	            pseudoField.setText("");
	            passwordField.setText("");
	            labelSuccess.setText(connected);
	            
	    		frame.setVisible(false);
        	}
        	else {
        		//mauvais pseudo/mot de passe
                labelError.setText(incorrectUser);
        	}
            try {
            }
            catch (Exception ex) {
                labelError.setText(errorConnect);
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
     */

    public Connexion() {
        //Set the look and feel.
        initLookAndFeel();
       
        Connect.updateId();
        
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        this.frame = new JFrame("Connexion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Connexion app = new Connexion();
        Component contents = createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}