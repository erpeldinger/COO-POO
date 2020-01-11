
package clavardage;

/*
 * SwingApplication.java is a 1.4 example that requires
 * no other files.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class Clavardage implements ActionListener {
    
    // L'etat de l'envoi du message
    private static String labelPrefix = "Etat du message : ";
    private String etatEnCreation = "EnCreation";
    private String etatEnvoye = "Envoyé";
    private String etatEnvoiEnCours = "En cours d'envoi";
    private String etatErreur = "Non envoyé, erreur";
    final JLabel labelMessage = new JLabel(labelPrefix + etatEnCreation);
    final JLabel labelConv = new JLabel("");
    
    // Messages d'erreur
    final JLabel labelError = new JLabel("");
    private static String emptyMessageField = "Envoi impossible : message vide";
    
    // Le champ permettant d'ecrire le message
    JTextField messageField = new JTextField(2);
    
    //Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = null;
    
    //creation du button d'envoie de message
    public Component createComponents() {
        JButton button = new JButton("Envoyer le message");
        button.setMnemonic(KeyEvent.VK_I);
        button.addActionListener(this);
        labelMessage.setLabelFor(button);
        
        /*
         * An easy way to put space between a top-level container
         * and its contents is to put the contents in a JPanel
         * that has an "empty" border.
         */
        JPanel pane = new JPanel(new GridLayout(0, 1));
        pane.add(labelConv);
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
                
                //Sauvegarder le message dans la base de donnes
                
                //Afficher le message
                labelConv.setText(messageField.getText());

                messageField.setText("");
            }
            catch (Exception ex){
                labelMessage.setText(labelPrefix + etatEnvoiEnCours);
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
    private static void createAndShowGUI() {
        //Set the look and feel.
        initLookAndFeel();
        
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        JFrame frame = new JFrame("Clavardage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Clavardage app = new Clavardage();
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}