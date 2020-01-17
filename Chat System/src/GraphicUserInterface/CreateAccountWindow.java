package GraphicUserInterface;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Data.LocalUser;

public class CreateAccountWindow implements ActionListener, KeyListener{
	
	private JLabel errNouveauLogin;
	private JTextField nouveauLogin;
	private LocalUser localHost;

	public CreateAccountWindow() {
		//Create and set up the window.
	    JFrame frame = new JFrame("CreateAccountWindow");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    
	    Container pane = frame.getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Création de la premiere zone : creer un compte
		JLabel creerCompte = new JLabel("Créez un compte");
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(20,20,20,20);
		pane.add(creerCompte,c);
		
		//Creation zone de saisie
		nouveauLogin = new JTextField("");
		c.gridx = 0;
		c.gridy = 1;		
		c.ipadx = 200;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(20,20,20,20);
		nouveauLogin.addKeyListener(this);
		pane.add(nouveauLogin,c);
		
		
		//Creation bouton verification
		JButton buttonVerif = new JButton("Verifier");
		buttonVerif.setSize(new Dimension (100,20));
		buttonVerif.addActionListener(this);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(20,20,20,20);
		pane.add(buttonVerif,c);
		
		//Zone d'affichage d'erreur
		errNouveauLogin = new JLabel("");
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,0,10,0);
		pane.add(errNouveauLogin,c);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private String getLogin(JTextField log) {
		return(log.getText());
	}
	
	public void login(String lgn) {		
		localHost = LocalUser.createAccount(lgn);
		localHost = localHost.authentify(lgn);
		if(localHost != null)	new ChatWindow(localHost);
		if(localHost == null)	System.out.println("CEST LA MERDE ");
	}
	
	public void actionPerformed(ActionEvent e) {		
		login(getLogin(nouveauLogin));	
	}
	
	public void keyTyped(KeyEvent keyEvent) {
	    char typed = keyEvent.getKeyChar();
	    if(typed == '\n') {    
			login(getLogin(nouveauLogin));	   	
	}
	}
	  
	public void keyPressed(KeyEvent keyEvent) {/*Nothing*/}

	public void keyReleased(KeyEvent keyEvent) {/*Nothing*/}
	
	public static void main (String[] args) {
		new CreateAccountWindow();
	}
		
	
	
	
	
}
