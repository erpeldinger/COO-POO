
package profil;

import user.*;
import LUC.LUC;
import clavardage.AlerteMessage;
import communication.BroadcastingClient;
import requete.Connect;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class ModifPassword implements ActionListener {
    
    // Tous les labels
	private User user;
	private JFrame frame;
    private static String password = "Mot de passe: ";
    private String newPassword = "Nouveau mot de passe : ";
    private String emptyPassword = "Veuillez entrer un mot de passe";
    private String incorrectPassword = "Mot de passe incorrect : il faut au un mot de passe ayant minimum 10 caractères et ne contenant pas votre pseudo";
    private static String success = "Modification effectuée";
    final JLabel labelPassword = new JLabel(password);
    final JLabel labelNewPassword = new JLabel(newPassword);
    final JLabel labelSuccess = new JLabel("");
    final JLabel labelError = new JLabel("");
    Color myGreen = new Color(11, 102, 35);
    
    // Les champs de textes
    JTextField passwordField = new JTextField(2);
    JTextField newPasswordField = new JTextField(2);
    
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
        JButton button = new JButton("Modifier mon mot de passe");
        button.setMnemonic(KeyEvent.VK_I);
        button.addActionListener(this);
        labelPassword.setLabelFor(button);
        
        //creation du button de redirection vers la page de connexion
        JButton button2 = new JButton("Retour au profil");
        button2.setMnemonic(KeyEvent.VK_I);
        button2.addActionListener(this);
        labelNewPassword.setLabelFor(button2);
        
        JPanel pane = new JPanel(new GridLayout(0, 1));
        pane.add(labelError);
        pane.add(labelPassword);
        pane.add(passwordField);
        pane.add(labelNewPassword);
        pane.add(newPasswordField);
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
    	if (e.getActionCommand().equals("Modifier mon mot de passe")){
    		//champ vide
    		if (passwordField.getText().equals("")) {
                labelError.setText(emptyPassword);
            }
    		//Verif dans la BD
    		else {
	    		if (!Connect.checkPassword("database.db", Connect.queryUserPseudo("database.db",this.user.getId()) , newPasswordField.getText())) {
	    			// pseudo pas unique
	                labelError.setText(incorrectPassword);
	    		}
	    		else {
	    			Connect.updateUser("database.db", this.user.getPseudo(), newPasswordField.getText(), this.user.getId());
	    			this.user.setPassword(newPasswordField.getText());
	            	
	                labelError.setText("");
	                passwordField.setText(this.user.getPassword());
	                newPasswordField.setText("");
	                labelSuccess.setText(success);
	    		}
    		}
    	}
    	else if (e.getActionCommand().equals("Liste des utilisateurs connectes")) {
    		try {
				LUC maLUC = new LUC(this.user);
			} catch (IOException e1) {
				System.out.println("[ERROR PROFIL] Creation de la page LUC impossible : " + e);
				AlerteMessage error = new AlerteMessage("null", "null", 3);
			}
    		frame.setVisible(false);
    	}
    	else if (e.getActionCommand().equals("Se deconnecter")) {
    		Connect.deleteAllUserLUC("database.db");
    		frame.setVisible(false);
    	}
    	else if (e.getActionCommand().equals("Profil") || e.getActionCommand().equals("Retour au profil")) {
    		Profil monProfil = new Profil(this.user);
    		frame.setVisible(false);
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
   
    public ModifPassword(User user) {
        initLookAndFeel();

        this.user = user;
        //mettre a jour le id de la database
        Connect.createNewDatabase("database.db");
        Connect.createNewTableUser("database.db");
        passwordField.setText(this.user.getPassword());
        newPasswordField.setText("");        
        passwordField.setEditable(false);
        
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        this.frame = new JFrame("Modifier mon mot de passe");

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
        
        Component contents = createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}