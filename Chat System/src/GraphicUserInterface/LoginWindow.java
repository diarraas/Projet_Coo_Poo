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
	    frame = new JFrame("Bienvenue !");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    Container pane = frame.getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		//Création de la zone : se connecter
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
	
	public void login(String lgc) {
		localHost = new LocalUser();
		localHost = localHost.authentify(lgc);
		if(localHost != null) {
			new DiscussionWindow(localHost);
			frame.dispose();	
		}else {
			errConnectLogin.setText("Pseudo deja pris");
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(getLogin(connectLogin).contains(" ") || getLogin(connectLogin).contains("\"")|| getLogin(connectLogin).contains("\'")) {
			errConnectLogin.setText("Pseudo indisponible");
		}else {	
			login(getLogin(connectLogin));			
		}
	}
	
	private String getLogin(JTextField log) {
		return(log.getText());
	}
	
	
	public void keyTyped(KeyEvent keyEvent) {
	    char typed = keyEvent.getKeyChar();
	    if(typed == '\n') {    	    
	    	if(getLogin(connectLogin).contains(" ") || getLogin(connectLogin).contains("\"")|| getLogin(connectLogin).contains("\'")) {
				errConnectLogin.setText("Pseudo indisponible");
			}else {	
				login(getLogin(connectLogin));
				frame.dispose();
			}
	  	}
	 }
	  
	public void keyPressed(KeyEvent keyEvent) {/*Nothing*/}

	public void keyReleased(KeyEvent keyEvent) {/*Nothing*/}


}
