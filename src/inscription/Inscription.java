package inscription;

import LUC.LUC;
import clavardage.AlerteMessage;
import communication.BroadcastingClient;
import requete.Connect;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Inscription implements ActionListener {
    
    // Tous les labels
	private JFrame frame;
    private static String pseudo = "Pseudo : ";
    private String password = "Mot de passe : ";
    private String connect = "Se connecter";
    private String emptyPseudo = "Veuillez entrer un pseudo";
    private String emptyPassword = "Veuillez entrer un mot de passe";
    private String incorrectPseudo = "Pseudo déjà utilisé ";
    private String incorrectPassword = "Mot de passe incorrect : il faut au un mot de passe ayant minimum 10 caractères et ne contenant pas votre pseudo";
    private static String errorPrefix= "Inscription impossible : ";
    private static String logged = "Inscription réussie";
    private static String errorLog = "Une ereure s'est produite, l'inscription a échoué";
    //concernant la connexion
    private String incorrectUser = "Pseudo/Mot de passe incorrect";
    private String incorrectPseudoCo = "Pseudo incorrect";
    private String incorrectPasswordCo = "Mot de passe incorrect";
    private static String connected = "Connexion réussie";
    //Les labels
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
    
    public Component createComponents() {
        //creation du button d'inscription
        JButton button = new JButton("S'inscrire");
        button.setMnemonic(KeyEvent.VK_I);
        button.addActionListener(this);
        labelConnect.setLabelFor(button);
        
        //creation du button de redirection vers la page de connexion
        JButton buttonConnect = new JButton("Se connecter");
        buttonConnect.setMnemonic(KeyEvent.VK_I);
        buttonConnect.addActionListener(this);
        labelConnect.setLabelFor(buttonConnect);
        
        JPanel pane = new JPanel(new GridLayout(0, 1));
        pane.add(labelError);
        pane.add(labelPseudo);
        pane.add(pseudoField);
        pane.add(labelPassword);
        pane.add(passwordField);
        pane.add(button);
        pane.add(labelSuccess);
        pane.add(buttonConnect);
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
    	if (e.getActionCommand().equals("Se connecter")){
    		if (pseudoField.getText().equals("")) {
                labelError.setText(incorrectPseudoCo);
            }
            else if(passwordField.getText().equals("")) {
                labelError.setText(incorrectPasswordCo);
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
            }
    	}
    	else
	    	if (e.getActionCommand().equals("S'inscrire")) {
		    	
		    	Connect.createNewDatabase("database.db");
		    	Connect.createNewTableUser("database.db");
		        //Si le message est vide on ne l'envoie pas, on affiche l'erreur
		        if (pseudoField.getText().equals("")) {
		            labelError.setText(emptyPseudo);
		        }
		        else if(passwordField.getText().equals("")) {
		            labelError.setText(emptyPassword);
		        }
		        //Sinon on test la validite du pseudo/password
		        else {
		        	if (Connect.checkPseudo("database.db", pseudoField.getText()) && Connect.checkPassword("database.db", pseudoField.getText(), passwordField.getText())) {
		        		// on inscrit la personne
		            	Connect.insertUser("database.db",pseudoField.getText(), passwordField.getText(), Connect.getId());
		                labelError.setText("");
		                pseudoField.setText("");
		                passwordField.setText("");
		                labelSuccess.setText(logged);
		        	}
		        	else {
		        		// probleme pseudo ou mot de passe
		        		if (!Connect.checkPseudo("database.db", pseudoField.getText())) {
		        			// pseudo pas unique
		                    labelError.setText(incorrectPseudo);
		        		}
		        		else {
		        			// mot de passe pas aux normes
		                    labelError.setText(incorrectPassword);
		        		}
		        	}	
            
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
    
    public Inscription() {
        initLookAndFeel();

        //mettre a jour le id de la database
        Connect.createNewDatabase("database.db");
        Connect.createNewTableUser("database.db");
        Connect.updateId();
        
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        this.frame = new JFrame("Inscription");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Inscription app = new Inscription();
        Component contents = createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}