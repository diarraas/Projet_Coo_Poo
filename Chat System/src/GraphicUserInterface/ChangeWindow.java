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

public class ChangeWindow  implements ActionListener, KeyListener{
	
	private JLabel errArea;
	private JTextField newLogin;
	private LocalUser localHost;
	private JFrame frame;
	
	public ChangeWindow(LocalUser user) {
		localHost = user ;
		//Create and set up the window.
	    frame = new JFrame("ChangeWindow");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    
	    Container pane = frame.getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Création de la premiere zone : creer un compte
		JLabel changeLogin = new JLabel("Entrer un nouveau login");
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(20,20,20,20);
		pane.add(changeLogin,c);
		
		//Creation zone de saisie
		newLogin = new JTextField("");
		c.gridx = 0;
		c.gridy = 1;		
		c.ipadx = 200;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(20,20,20,20);
		newLogin.addKeyListener(this);
		pane.add(newLogin,c);
		
		
		//Creation bouton verification
		JButton buttonVerif = new JButton("Soumettre");
		buttonVerif.setSize(new Dimension (100,20));
		buttonVerif.addActionListener(this);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(20,20,20,20);
		pane.add(buttonVerif,c);
		
		//Zone d'affichage d'erreur
		errArea = new JLabel("");
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,0,10,0);
		pane.add(errArea,c);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	

	private String getLogin(JTextField log) {
		return(log.getText());
	}
	
	public void login(String lgn) {		
		if(!localHost.changeLogin(lgn))	{
			errArea.setText("Pseudo deja utilise");
		}
		else{
			ChatWindow.updateUsers(localHost.getOnliners());
			frame.dispose();
		}
	}
	
	public void actionPerformed(ActionEvent e) {		
		login(getLogin(newLogin));
	}
	
	public void keyTyped(KeyEvent keyEvent) {
	    char typed = keyEvent.getKeyChar();
	    if(typed == '\n') {    
			login(getLogin(newLogin));	  
	}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
