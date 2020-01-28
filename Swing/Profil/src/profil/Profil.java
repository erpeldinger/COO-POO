
package profil;

import user.*;
/*
 * SwingApplication.java is a 1.4 example that requires
 * no other files.
 */
import javax.swing.*;

import requete.Connect;
import connexion.*;
import LUC.*;
import communication.BroadcastingClient;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Profil implements ActionListener {
    
    // Tous les labels
	private User user;
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
		
        //creation du button d'inscription
        JButton button = new JButton("Modifier mon pseudo");
        button.setMnemonic(KeyEvent.VK_I);
        button.addActionListener(this);
        labelPseudo.setLabelFor(button);
        
        //creation du button de redirection vers la page de connexion
        JButton button2 = new JButton("Modifier mon mot de passe");
        button2.setMnemonic(KeyEvent.VK_I);
        button2.addActionListener(this);
        labelPassword.setLabelFor(button2);
        
        /*
         * An easy way to put space between a top-level container
         * and its contents is to put the contents in a JPanel
         * that has an "empty" border.
         */
        JPanel pane = new JPanel(new GridLayout(0, 1));
        pane.add(labelError);
        pane.add(labelPseudo);
        pane.add(pseudoField);
        pane.add(button);
        pane.add(labelPassword);
        pane.add(passwordField);
        pane.add(button2);
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
    	if (e.getActionCommand().equals("Modifier mon pseudo")){
    		ModifPseudo monPseudo = new ModifPseudo(this.user);
    		frame.setVisible(false);
    	}
    	else if (e.getActionCommand().equals("Modifier mon mot de passe")) {
    		ModifPassword monPass = new ModifPassword(this.user);
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
    		// Envoie d'un message de deconnexion en Broadcast
    		try {
				BroadcastingClient.sendDisconnected(BroadcastingClient.getBroadcastAddress());
			} catch (Exception e1) {
				System.out.println("[ERROR LUC] Broadcast de Deconnexion" + e1);
			}
    		
    		//delete ma LUC
    		Connect.deleteAllUserLUC("database.db");
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
     */
    //private static void createAndShowGUI() {
    public Profil(User user) {
        //Set the look and feel.
        initLookAndFeel();

        this.user = user;
        //mettre a jour le id de la database
        Connect.createNewDatabase("database.db");
        Connect.createNewTableUser("database.db");
        //Connect.insertUser("database.db", "Titou", "1246789123456789",56);
        pseudoField.setText(Connect.queryUserPseudo("database.db", this.user.getId()));
        passwordField.setText(Connect.queryUserPass("database.db", this.user.getId()));
        
        pseudoField.setEditable(false);
        passwordField.setEditable(false);
        
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