package GraphicUserInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginWindow implements ActionListener {
	
	public LoginWindow() {
	
	//Create and set up the window.
    JFrame frame = new JFrame("LoginWindow");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.setResizable(false);
    
    Container pane = frame.getContentPane();
	pane.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	
	//Création de la premiere zone : creer un compte
	JLabel creerCompte = new JLabel("Créez un compte");
	c.gridx = 0;
	c.gridy = 0;
	c.insets = new Insets(10,150,10,100);
	pane.add(creerCompte,c);
	
	//Creation zone de saisie
	JTextField nouveauLogin = new JTextField("");
	c.gridx = 0;
	c.gridy = 1;
	c.ipadx = 200;
	c.insets = new Insets(0,45,50,0);
	pane.add(nouveauLogin,c);
	
	//Creation bouton envoi
	JButton buttonVerif = new JButton("Verifier");
	buttonVerif.setSize(new Dimension (100,20));
	buttonVerif.addActionListener(this);
	c.gridx = 0;
	c.gridy = 3;
	pane.add(buttonVerif,c);
	
	//Création de la deuxieme zone : se connecter
	JLabel seConnecter = new JLabel("Connectez vous");
	c.gridx = 1;
	c.gridy = 0;
	c.insets = new Insets(10,190,10,0);
	pane.add(seConnecter,c);	 
	
	//Creation zone de saisie
	JTextField connectLogin = new JTextField("");
	c.gridx = 1;
	c.gridy = 1;
	c.ipadx = 200;
	c.insets = new Insets(0,0,50,0);
	pane.add(connectLogin,c);
    
    frame.pack();
    frame.setVisible(true);
    
	
	}
	
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Bouton");
	}
	
	public static void main(String[] args) {
		new LoginWindow();
	}

}
