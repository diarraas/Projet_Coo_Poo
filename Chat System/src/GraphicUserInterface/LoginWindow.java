package GraphicUserInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Data.*;

public class LoginWindow implements ActionListener, KeyListener {
	
	
	private JTextField connectLogin;
	
	private JLabel errConnectLogin;
	private static LocalUser localHost ;
	private JFrame frame;
	
	public LoginWindow() {	
		//Create and set up the window.
	    frame = new JFrame("LoginWindow");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    
	    Container pane = frame.getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		//CrÃ©ation de la zone : se connecter
		JLabel seConnecter = new JLabel("Connectez vous");
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(20,20,20,20);
		c.anchor = GridBagConstraints.CENTER;
		pane.add(seConnecter,c);	 
		
		//Creation zone de saisie
		connectLogin = new JTextField("");
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 200;
		c.insets = new Insets(20,20,20,20);
		connectLogin.addKeyListener(this);
		pane.add(connectLogin,c);
		
		//Creation bouton envoi
		JButton buttonLogin = new JButton("Se connecter");
		buttonLogin.setSize(new Dimension (100,20));
		buttonLogin.addActionListener(this);
		c.gridx = 0;
		c.gridy = 2;
		pane.add(buttonLogin,c);
		
		//Zone d'affichage d'erreur
		errConnectLogin = new JLabel("");
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(0,0,10,0);
		pane.add(errConnectLogin,c);
	    
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);    
	
	}
	
	/** 
	 * 				/!\      NEW GUIDE LINES POUR KENTIN       /!\
	 * 
	 *	Ya moyen qu'on garde que cette fenetre et chat session en fait pour faire un truc entièrement decentralisé ---- no need to create
	 	an account, just login with a unic username mais bon pour l'instant j'ai rien supprimé jai tout mis en commentaire
	 * 
	 * 
	 * **/
	
	public void login(String lgc) {
		localHost = new LocalUser();
		localHost = localHost.authentify(lgc);
		new ChatWindow(localHost);
	}
	
	public void actionPerformed(ActionEvent e) {
			login(getLogin(connectLogin));
			frame.dispose();	
	}
	
	private String getLogin(JTextField log) {
		return(log.getText());
	}
	
	
	public void keyTyped(KeyEvent keyEvent) {
	    char typed = keyEvent.getKeyChar();
	    if(typed == '\n') {    	    
	    	login(getLogin(connectLogin));
	    	frame.dispose();
	  	}
	 }
	  
	public void keyPressed(KeyEvent keyEvent) {/*Nothing*/}

	public void keyReleased(KeyEvent keyEvent) {/*Nothing*/}
	
	public static void main(String[] args) {
		new LoginWindow();
	}


}
