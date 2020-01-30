
package LUC;

import javax.swing.*;
import user.*;

import clavardage.Clavardage;
import requete.Connect;
import profil.Profil;
import communication.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

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
    		//check si User bien dans la LUC (pseudo ==> id ==> LUC)
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
					System.out.println("[LUC] Erreur : " + e1);
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
    			//rafraichir la lite des user connectes
    			String luc = ListUser.getText();
    			String[] lucSplit = luc.split("\n");
    			System.out.println("[LUC] texte : " + luc);
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
				
		    	String idDest="";
		    	InetAddress idDestInet;
				//creation des TCPServer pour les nouveaux
				for (String courant : newUsers) {
	    			System.out.println("[LUC]" + courant + "\n");
		    		// Ouverture d'un Thread TCP Server par utilisateur connectes
		    		if(!courant.contains("end") && !courant.contains(this.user.getPseudo())) {
			    		ListUser.setText(ListUser.getText() + "\n" + courant );
		    			//manager.addTCPServer(user.getId(), BroadcastingClient.getIpAddress());
			    		idDest = Connect.queryUserLUCbyPseudo("database.db", courant);
			    		String parts[] = idDest.split("/");
			    		System.out.println("[CONNECT] IP recupere dans la BD : " + parts[1] + "pour le pseudo " + courant);
			    		idDestInet = InetAddress.getByName(parts[1]);
			    		manager.addTCPServer(user.getId(), courant ,idDestInet);
		    		}
		    	}

				//fermeture des TCPServer pour les anciens
    			//manager.stopCommunication(disconnectedUsers);
    			
    			
			} catch (Exception e1) {
				System.out.println("[ERROR LUC]refresh " + e1);
			}
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
    		// Envoi d'un message de deconnexion en Broadcast
    		try {
				BroadcastingClient.sendDisconnected(BroadcastingClient.getBroadcastAddress());
				this.manager.stopCommunication();
			} catch (Exception e1) {
				System.out.println("[ERROR LUC] Broadcast de Deconnexion" + e1);
			}
    		
    		//delete ma LUC
    		System.out.println("debut ");
    		Connect.deleteAllUserLUC("database.db");
    		Connect.deleteTable("database.db", "ListUserConnected");
    		System.out.println("apres delete all User LUC");
    		ArrayList<String> users = Connect.queryAllUserLUC("database.db");
    		System.out.println("contenu de users : ");
    		for (String courant : users) {
    			System.out.println(courant + " - ");
    		}
    		frame.setVisible(false);
    		System.out.println("fermeture de l'application \n");
            System.exit(0);
    	}
    	else if (e.getActionCommand().equals("Profil")) {
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
     * @throws IOException 
     */
    
    public LUC(User user) throws IOException {
        System.out.println("debut constructeur");
        //init le user
        this.user = user;
        //Set the look and feel.
        initLookAndFeel();
        
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        this.frame = new JFrame("Utilisateurs connectés");

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
					}
					
					//delete ma LUC
					System.out.println("debut ");
					Connect.deleteAllUserLUC("database.db");
					Connect.deleteTable("database.db", "ListUserConnected");
					
					System.out.println("fermeture de l'application \n");
					frame.setVisible(false);
			}
			public void windowClosed(WindowEvent e){}
			public void windowIconified(WindowEvent e){}
			public void windowDeiconified(WindowEvent e){}
			public void windowActivated(WindowEvent e){}
			public void windowDeactivated(WindowEvent e){}
		});
        
        ListUser.setEditable(false);
      
        // afficher les User connectes
        Connect.createNewTableLUC("database.db");        
    	Connect.createNewTableConv("database.db");
        
    	ArrayList <String> Users = Connect.queryAllUserLUC("database.db");
    	//on reset l'espace de texte
		ListUser.setText("");

		System.out.println("[LUC] !!!!!!!!!!!!!!!!!!! AFFICHAGE DE LA LUC !!!!!!!!!!!!! \n");
    	try {
	    	this.manager = new ChatManager();
	    	String idDest="";
	    	InetAddress idDestInet;
	    	for (String courant : Users) {
    			System.out.println("[LUC]" + courant + "\n");
	    		// Ouverture d'un Thread TCP Server par utilisateur connectes
	    		if(!courant.contains("end") && !courant.contains(this.user.getPseudo())) {
		    		ListUser.setText(ListUser.getText() + "\n" + courant );
		    		System.out.println("[LUC] IN if");
	    			//manager.addTCPServer(user.getId(), BroadcastingClient.getIpAddress());
		    		idDest = Connect.queryUserLUCbyPseudo("database.db", courant);
		    		idDestInet = InetAddress.getByName(idDest);
		    		System.out.println("[CONNECT] IP recupere dans la BD : " + idDest);
		    		manager.addTCPServer(user.getId(), courant,idDestInet);
	    		}
	    	}
    	}				
		catch (Exception e) {
			System.out.println("[LUC] ERREUR Creation TCP Server ou ChatManager impossible");
		}
    	
        //a modifier pour le main
        Component contents = createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        System.out.println("fin constructeur LUC");
    }    
}