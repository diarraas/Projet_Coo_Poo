package GraphicUserInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import Data.*;

public class LoginWindow implements ActionListener, KeyListener {
	
	private JTextField nouveauLogin;
	private JTextField connectLogin;
	private LocalUser localHost;
	
	
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
	
	/** 
	 * 				/!\      GUIDE LINE POUR KENTIN       /!\
	 *  
	 * Need to implement logout button
	 * __________________change login button
	 * When window closes ---- you're disconnected
	 * Print on screen (not console) when a login already exists in BDD (authentify or createAccount will return null)
	 * Same goes for an incorrect login ---- un pop up maybe ?
	 * Informe le user quand son compte est bien créé et qu'il peut se connecter
	 * PRIVATIZEEEEEEEEE
	 * je suppose que click sur login de qqun -> startSession(qqun) buuuut there's a method send message ---- you need to figure out how to use both
	 * Need a way to stop session ---- you cant just quit --- il faut permettre à l'utilisateur de mettre fin à une session unilatéralement 
	 * Maybe add a refresh button qui actualise la liste des connectés
	 * Il ya des null pointer PAS DE PANIQUE c'est normal ------- mets la ligne 11 en commentaire
	 * 
	 * 
	 * **/
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Verifier") {
			
			String loginNew = getLogin(nouveauLogin);
			localHost = LocalUser.createAccount(loginNew);
			localHost = localHost.authentify(loginNew);
			 
			//System.out.println(loginNew);
			
		}
		else if (e.getActionCommand() == "Se connecter")
		{
			String loginConnect = getLogin(connectLogin);
			localHost = localHost.authentify(loginConnect);
			System.out.println("Authentified");
			localHost.startSession(localHost.getLogin());
			localHost.sendMessage(localHost.getLogin(),"CINNAMOOOOOON ");
			//ArrayList<Message> me = Database.getHistory(localHost.getLogin(), localHost.getLogin());
			
			//System.out.println(me.toString());
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
