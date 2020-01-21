
package profil;

import user.*;
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

public class ModifPseudo implements ActionListener {
    
    // Tous les labels
	private User user;
	private JFrame frame;
    private static String pseudo = "Pseudo : ";
    private String newPseudo = "Nouveau pseudo : ";
    private String emptyPseudo = "Veuillez entrer un pseudo";
    private String incorrectPseudo = "Pseudo deja� utilise ";
    private static String success = "Modification effectu�";
    //private static String errorLog = "Une ereure s'est produite, l'inscription a échoué";
    final JLabel labelPseudo = new JLabel(pseudo);
    final JLabel labelNewPseudo = new JLabel(newPseudo);
    final JLabel labelSuccess = new JLabel("");
    final JLabel labelError = new JLabel("");
    Color myGreen = new Color(11, 102, 35);
    
    // Les champs de textes
    JTextField pseudoField = new JTextField(2);
    JTextField newPseudoField = new JTextField(2);
    
    //Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = null;
    
    public Component createComponents() {
        //creation du button d'inscription
        JButton button = new JButton("Modifier mon pseudo");
        button.setMnemonic(KeyEvent.VK_I);
        button.addActionListener(this);
        labelPseudo.setLabelFor(button);
        
        //creation du button de redirection vers la page de connexion
        JButton button2 = new JButton("Retour au profil");
        button2.setMnemonic(KeyEvent.VK_I);
        button2.addActionListener(this);
        labelNewPseudo.setLabelFor(button2);
        
        /*
         * An easy way to put space between a top-level container
         * and its contents is to put the contents in a JPanel
         * that has an "empty" border.
         */
        JPanel pane = new JPanel(new GridLayout(0, 1));
        pane.add(labelSuccess);
        pane.add(labelPseudo);
        pane.add(pseudoField);
        pane.add(labelNewPseudo);
        pane.add(newPseudoField);
        pane.add(button);
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
    		//champ vide
    		if (pseudoField.getText().equals("")) {
                labelError.setText(emptyPseudo);
            }
    		//Verif dans la BD
    		else {
	    		if (Connect.checkPseudo("database.db", pseudoField.getText())) {
	    			// pseudo pas unique
	                labelError.setText(incorrectPseudo);
	    		}
	    		else {
	    			Connect.updateUser("database.db",newPseudoField.getText(), this.user.getPassword(), this.user.getId());
	    			this.user.setPseudo(newPseudoField.getText());
	            	System.out.println("Pseudo bien modifie en : USER " + this.user.getPseudo() + " DB : " + Connect.queryUserPseudo("database.db", this.user.getId()));
	            	
	                labelError.setText("");
	                pseudoField.setText(this.user.getPseudo());
	                newPseudoField.setText("");
	                labelSuccess.setText(success);
	    		}
    		}
    	}
    	else {
    		Profil pageProfil = new Profil(this.user);
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
    public ModifPseudo(User user) {
        //Set the look and feel.
        initLookAndFeel();

        this.user = user;
        //mettre a jour le id de la database
        Connect.createNewDatabase("database.db");
        Connect.createNewTableUser("database.db");
        //Connect.insertUser("database.db", "Titou", "1246789123456789",56);
        pseudoField.setText(this.user.getPseudo());
        newPseudoField.setText("");
        
        pseudoField.setEditable(false);
        
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