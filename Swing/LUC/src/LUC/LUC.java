
package LUC;

/*
 * SwingApplication.java is a 1.4 example that requires
 * no other files.
 */
import javax.swing.*;
import user.*;

import clavardage.Clavardage;
import requete.Connect;
import connexion.*;
import communication.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LUC implements ActionListener {
    
    // Tous les labels
	private User user;
	private JFrame frame;
	private ChatManager manager;
    private String incorrectUser = "Cet utilisateur n'ext pas connecte";
    final JLabel labelError = new JLabel("");
    final JLabel labelButton = new JLabel("");
    final JLabel labelButtonRefresh = new JLabel("");
    Color myGreen = new Color(11, 102, 35);
    
    // Les champs de textes
    JTextField debutConv = new JTextField(2);
    
  //Affichage des différents messages
    private static JTextArea ListUser = new JTextArea(10, 20);
    private static JScrollPane Users = new JScrollPane(ListUser);
    
    //Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = null;
    
    public Component createComponents() {
        //creation du button d'inscription
        JButton button = new JButton("Demarrer une conversation");
        button.setMnemonic(KeyEvent.VK_I);
        button.addActionListener(this);
        labelButton.setLabelFor(button);

        JButton buttonRefresh = new JButton("Raffraichir");
        buttonRefresh.setMnemonic(KeyEvent.VK_I);
        buttonRefresh.addActionListener(this);
        labelButtonRefresh.setLabelFor(buttonRefresh);
        
        /*creation du button de redirection vers la page de connexion
        JButton buttonConnect = new JButton("Se connecter");
        buttonConnect.setMnemonic(KeyEvent.VK_I);
        buttonConnect.addActionListener(this);
        labelConnect.setLabelFor(buttonConnect);
        */
        
        /*
         * An easy way to put space between a top-level container
         * and its contents is to put the contents in a JPanel
         * that has an "empty" border.
         */
        JPanel pane = new JPanel(new GridLayout(0, 1));
        pane.add(Users);
        pane.add(labelError);
        pane.add(debutConv);
        pane.add(button);
        pane.add(buttonRefresh);
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
		labelError.setText("");
    	if (e.getActionCommand().equals("Demarrer une conversation")){
    		//check si User bien dans la LUC (pseuod ==> id ==> LUC)
        	int userId = Connect.queryUserIdLUC("database.db", debutConv.getText());
        	if (userId != -1) {
    		//ouvrir la page de conv
        		try {
					Clavardage pageClavardage = new Clavardage(user,userId);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		System.out.println("connexion");
        		frame.setVisible(false);
        	}
        	else {
        		//erreur de User
        		labelError.setText(incorrectUser);
        	}
    	}
    	else {
    		try {
    			ListUser.setText("");
    			manager.stopCommunication();
				LUC pageLUC = new LUC(user);
			} catch (IOException e1) {
				System.out.println("[ERROR LUC]refresh " + e);
			}
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
     * @throws IOException 
     */
    public LUC(User user) throws IOException {
    //public void createAndShowGUI() {
        System.out.println("debut constructeur");
        
        //init le user
        this.user = user;
        //lancement du broadcast
        user.allowBroadcast(new BroadcastingClient(user.getListener().getDatagramSocket(),user.getMonPort(), user));
        
        //Set the look and feel.
        initLookAndFeel();
        
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        this.frame = new JFrame("LUC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ListUser.setEditable(false);
        // afficher les User connectes
        Connect.createNewTableLUC("database.db");
    	Connect.insertUser("database.db", "Tata", "titi123456790" , 19999);
    	Connect.insertUser("database.db", "Tutu", "titi123456790" , 29999);
    	Connect.insertUserLUC("database.db", 19999, "2.3.4.5");
    	Connect.insertUserLUC("database.db", 29999, "1.2.3.4");
    	ArrayList <String> Users = Connect.queryAllUserLUC("database.db");
    	try {
	    	this.manager = new ChatManager();
	    	for (String courant : Users) {
	    		ListUser.setText(ListUser.getText() + "\n" + courant );
	    		// Ouverture d'un Thread TCP Server par utilisateur connectes
	    			manager.addTCPServer(user.getId(), BroadcastingClient.getIpAddress());
	    	}	
    	}
		catch (Exception e) {
			System.out.println("[ERREUR LUC] Creation TCP Server ou ChatManager impossible");
		}
    	
        //a modifier pour le main
        //LUC app = new LUC();
        Component contents = createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        System.out.println("fin constructeur");
    }
    
    /*
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	LUC pageLUC = new LUC();
                pageLUC.createAndShowGUI();
            }
        });
    }
    */
    
    
}