
package inscription;

/*
 * SwingApplication.java is a 1.4 example that requires
 * no other files.
 */
import javax.swing.*;

import requete.Connect;
import connexion.*;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

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
    final JLabel labelConnect = new JLabel(connect);
    final JLabel labelPseudo = new JLabel(pseudo);
    final JLabel labelPassword = new JLabel(password);
    final JLabel labelSuccess = new JLabel("");
    final JLabel labelError = new JLabel("");
    Color myGreen = new Color(11, 102, 35);
    
    // Les champs de textes
    JTextField pseudoField = new JTextField(2);
    JTextField passwordField = new JTextField(2);
    
    //Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
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
        
        /*
         * An easy way to put space between a top-level container
         * and its contents is to put the contents in a JPanel
         * that has an "empty" border.
         */
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
    		Connexion pageConnexion = new Connexion();
    		frame.setVisible(false);
    	}
    	else
    	if (e.getActionCommand().equals("S'inscrire")) {
    	
    	Connect.createNewDatabase("database.db");
    	Connect.createNewTableUser("database.db");
        //Si le message est vide on ne l'envoie pas , n affiche l'erreur
        if (pseudoField.getText().equals("")) {
            labelError.setText(emptyPseudo);
        }
        else if(passwordField.getText().equals("")) {
            labelError.setText(emptyPassword);
        }
        //Sinon on test la validite du pseudo/password
        else {
        	if (Connect.checkPseudo("database.db", pseudoField.getText()) && Connect.checkPassword("database.db", pseudoField.getText(), passwordField.getText())) {
        		// on inscris la personne
            	System.out.println("Bonjour");
            	Connect.insertUser("database.db",pseudoField.getText(), passwordField.getText(), Connect.getId());
            	System.out.println("Vous êtes maintenant inscrit !");
            	
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
        	
            // on connecte la personne
            try {
            }
            catch (Exception ex) {
                labelError.setText(errorLog);
            }
            
        }
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
     */
    //private static void createAndShowGUI() {
    public Inscription() {
        //Set the look and feel.
        initLookAndFeel();

        //mettre a jour le id de la database
        Connect.createNewDatabase("database.db");
        Connect.createNewTableUser("database.db");
        //Connect.insertUser("database.db", "Titou", "1246789123456789",56);
        Connect.updateId();
        System.out.println("update de l'id : " + Connect.getId());
        
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
    
    /*public static void main(String[] args) {
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