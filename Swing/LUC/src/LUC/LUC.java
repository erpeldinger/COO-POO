
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
import profil.Profil;
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
    		Connect.createNewTableLUC("database.db");
    		System.out.println("[LUC] print debconv.gettext() :" + debutConv.getText());
    		String userPseudo = debutConv.getText();
        	int userId = Connect.queryUserIdLUC("database.db", debutConv.getText());
        	System.out.println("[LUC] id recupere : " + userId);
        	if (userId != -1) {
    		//ouvrir la page de conv
        		try {
					Clavardage pageClavardage = new Clavardage(user,userId, userPseudo);
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
    	else if (e.getActionCommand().equals("Raffraichir")) {
    		try {
    			//test de raffraichissement numero 2
    			ArrayList <String> users = Connect.queryAllUserLUC("database.db");
    			ListUser.setText("");
    			for (String courant : users) {
    				if (!courant.contains("end")) {
    					System.out.println(courant + " ");
    					ListUser.setText(ListUser.getText() + "\n" + courant);
    				}
    			}
    			
    			/*
    			//rafraichir la lite des user connectes
    			String luc = ListUser.getText();
    			String[] lucSplit = luc.split("\n");
    			System.out.println("[LUC] texte : " + luc);
    			//System.out.println(lucSplit[0] + " et " + lucSplit[1] + " et " + lucSplit[2]);
    			ArrayList <String> newUsers = Connect.queryNewUser("database.db", lucSplit);
    			ArrayList <String> oldUsers = Connect.queryOldUser("database.db", lucSplit);
    			ArrayList <String> disconnectedUsers = Connect.queryDisconnectedUser("database.db", lucSplit);
    			
				System.out.println("new users : ");
    			for (String courant : newUsers) {
    				System.out.println(courant + " \n");
    			}
				System.out.println("old users : ");
    			for (String courant : oldUsers) {
    				System.out.println(courant + " \n");
    			}
				System.out.println("disconnected users : ");
    			for (String courant : disconnectedUsers) {
    				System.out.println(courant + " \n");
    			}
    			
    			//affichage de la nouvelle liste
    			ListUser.setText("");
    			for (String courant : newUsers) {
    				if (!courant.contains("end")) {
    					ListUser.setText(ListUser.getText() + "\n" + courant);
    				}
    			}
				ListUser.setText(ListUser.getText() + "\n");
    			for (String courant : oldUsers) {
    				if (!courant.contains("end")) {
    					ListUser.setText(ListUser.getText() + "\n" + courant);
    				}
    			}
				ListUser.setText(ListUser.getText() + "\n");
				
				//ouvrir TCP pour les news

				//fermeture des connections TCP des users deconnectes
    			manager.stopCommunication(disconnectedUsers); //!!!!!!!!!!!!!!!!!A CODER ---> retrouver port � partir du pseudo :
    			*/
    			
			} catch (Exception e1) {
				System.out.println("[ERROR LUC]refresh " + e1);
			}
    		//frame.setVisible(false);
    	}
    	else if (e.getActionCommand().equals("Liste des utilisateurs connectes")) {
    		try {
				LUC maLUC = new LUC(this.user);
			} catch (IOException e1) {
				System.out.println("[ERROR LUC] Creation de la page LUC impossible : " + e);
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
    		System.out.println("debut ");
    		Connect.deleteAllUserLUC("database.db");
    		System.out.println("apres delete all User LUC");
    		ArrayList<String> users = Connect.queryAllUserLUC("database.db");
    		System.out.println("contenu de users : ");
    		for (String courant : users) {
    			System.out.println(courant + " - ");
    		}
    		
    		//
    		Connect.deleteAllUserLUC("database.db");
    		Connect.deleteTable("database.db", "ListUserConnected");
    		frame.setVisible(false);
    		System.out.println("fermeture de l'application \n");
            System.exit(0);
    	}
    	else if (e.getActionCommand().equals("Profil")) {
    		Profil monProfil = new Profil(this.user);
    		frame.setVisible(false);
    	}
    }

    public void windowClosing(WindowEvent e) {
		System.out.println("debut ");
    	Connect.createNewTableLUC("database.db");
		System.out.println("1 ");
		Connect.deleteUserLUC("database.db", this.user.getId());
		Connect.deleteUserLUC("database.db", 1);
		System.out.println("2 ");
		Connect.deleteUserLUC("database.db", 2);
		System.out.println("3 ");
		Connect.deleteUserLUC("database.db", 3);
		//TODO envoie Broadcast pour deconnexion

		System.out.println("avant delete table ");
		Connect.deleteTable("database.db", "ListUserConnected");
		System.out.println("apres delete table");
		Connect.deleteAllUserLUC("database.db");
		System.out.println("apres delete all");
		ArrayList<String> users = Connect.queryAllUserLUC("database.db");
		for (String courant : users) {
			System.out.println(courant + " - ");
		}
		
		System.out.println("fermeture de l'application \n");
        System.exit(0);
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
        
        //Set the look and feel.
        initLookAndFeel();
        
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        this.frame = new JFrame("Utilisateurs connectés");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // MODIFIER ---> VIDER LES USERS DE LA LUC !!!
        
        ListUser.setEditable(false);
        //Connect.deleteTable("database.db", "ListUserConnected"); // PROBLEME LORS DU RETOUR
        
        // afficher les User connectes
        Connect.createNewTableLUC("database.db");
        /* Verification du delete (OK)
    	ArrayList <String> UsersNull = Connect.queryAllUserLUC("database.db");
		System.out.println("USERS NULL \n");
    	for (String courant : UsersNull) {
    		System.out.println(courant + "\n");
    	}
        */
        
        /* ------- TEST INSERTION DE USERS / CONV -------
    	Connect.insertUser("database.db", "Tata", "titi123456790" , 19999);
    	Connect.insertUser("database.db", "Tutu", "titi123456790" , 29999);
    	Connect.insertUserLUC("database.db", 19999, "2.3.4.5");
    	Connect.insertUserLUC("database.db", 29999, "1.2.3.4");*/
        
    	Connect.createNewTableConv("database.db");
    	/*
    	Connect.insertConversation("database.db", 1, 29999, "Bonjour", "1:1:1:1");
    	Connect.insertConversation("database.db", 29999, 1, "Bonjour toi !", "1:2:1:1");
    	Connect.insertConversation("database.db", 1, 29999, "Comment va ?", "1:3:1:1");
    	Connect.insertConversation("database.db", 29999, 1, "Bien et toi ? !", "1:4:1:1");
    	*/
        
    	ArrayList <String> Users = Connect.queryAllUserLUC("database.db");
    	//on reset l'espace de texte
		ListUser.setText("");

		System.out.println("[LUC] affichage da la LUC \n");
    	try {
	    	this.manager = new ChatManager();
	    	//System.out.println("USERS \n");
	    	for (String courant : Users) {
    			System.out.println("[LUC]" + courant + "\n");
	    		// Ouverture d'un Thread TCP Server par utilisateur connectes
	    		if(courant.contains("end")) {
	    			//System.out.println("[LUC] if equals \"end\" ");
	    		}
	    		else{
		    		ListUser.setText(ListUser.getText() + "\n" + courant );
		    		//System.out.println("[LUC] Constructeur -> apres set text");
	    			manager.addTCPServer(user.getId(), BroadcastingClient.getIpAddress());
	    			//System.out.println("[LUC] Constructeur -> addTCPServer, id : "+user.getId()+", ip : "+BroadcastingClient.getIpAddress());
	    		}
	    	}
    	}
		catch (Exception e) {
			System.out.println("[LUC] ERREUR Creation TCP Server ou ChatManager impossible");
		}
    	
        //a modifier pour le main
        //LUC app = new LUC();
        Component contents = createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        System.out.println("fin constructeur LUC");
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