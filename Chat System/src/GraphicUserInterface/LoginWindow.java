package GraphicUserInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginWindow implements ActionListener, KeyListener {
	
	public JTextField nouveauLogin;
	public JTextField connectLogin;
	
	public LoginWindow() {	
	
	//Create and set up the window.
    JFrame frame = new JFrame("LoginWindow");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setBounds(500,400,0,0);
    
    Container pane = frame.getContentPane();
	pane.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	
	//Création de la premiere zone : creer un compte
	JLabel creerCompte = new JLabel("Créez un compte");
	c.gridx = 0;
	c.gridy = 0;
	c.insets = new Insets(10,150,50,100);
	pane.add(creerCompte,c);
	
	//Creation zone de saisie
	nouveauLogin = new JTextField("");
	c.gridx = 0;
	c.gridy = 1;
	c.ipadx = 200;
	c.insets = new Insets(0,45,50,0);
	nouveauLogin.addKeyListener(this);
	pane.add(nouveauLogin,c);
	
	//Creation bouton verification
	JButton buttonVerif = new JButton("Verifier");
	buttonVerif.setSize(new Dimension (100,20));
	buttonVerif.addActionListener(this);
	c.gridx = 0;
	c.gridy = 2;
	pane.add(buttonVerif,c);
	
	//Création de la deuxieme zone : se connecter
	JLabel seConnecter = new JLabel("Connectez vous");
	c.gridx = 1;
	c.gridy = 0;
	c.insets = new Insets(10,190,50,0);
	pane.add(seConnecter,c);	 
	
	//Creation zone de saisie
	connectLogin = new JTextField("");
	c.gridx = 1;
	c.gridy = 1;
	c.ipadx = 200;
	c.insets = new Insets(0,0,50,0);
	connectLogin.addKeyListener(this);
	pane.add(connectLogin,c);
	
	//Creation bouton envoi
	JButton buttonLogin = new JButton("Se connecter");
	buttonLogin.setSize(new Dimension (100,20));
	buttonLogin.addActionListener(this);
	c.gridx = 1;
	c.gridy = 2;
	pane.add(buttonLogin,c);
    
    frame.pack();
    frame.setVisible(true);    
	
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Verifier") {
			
			String loginNew = getLogin(nouveauLogin);
			System.out.println(loginNew); //vérifier que le login est disponible
			
		}
		else if (e.getActionCommand() == "Se connecter")
		{
			String loginConnect = getLogin(connectLogin);
			System.out.println(loginConnect);
		}
	}
	
	private String getLogin(JTextField log) {
		return(log.getText());
	}
	
	

	public void keyTyped(KeyEvent keyEvent) {
	    char typed = keyEvent.getKeyChar();
	    if(typed == '\n') {
	    	
	    if (keyEvent.getSource() == nouveauLogin) {
			String loginNew = getLogin(nouveauLogin);
			System.out.println(loginNew); //vérifier que le login est disponible
	   	}
	    	
	   	else if (keyEvent.getSource() == connectLogin) {
		String loginNew = getLogin(connectLogin);
		System.out.println(loginNew); //vérifier que le login est disponible
	  		}
	  }
	}
	  
	public void keyPressed(KeyEvent keyEvent) {/*Nothing*/}

	public void keyReleased(KeyEvent keyEvent) {/*Nothing*/}
	
	public static void main(String[] args) {
		new LoginWindow();
	}

}
