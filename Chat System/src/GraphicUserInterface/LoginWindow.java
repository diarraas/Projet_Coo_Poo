package GraphicUserInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Data.*;

public class LoginWindow implements ActionListener, KeyListener {
	
	
	private JTextField connectLogin;
	private LocalUser localHost;
	
	private JLabel errConnectLogin;
	
	
	public LoginWindow() {	
	
	//Create and set up the window.
    JFrame frame = new JFrame("LoginWindow");
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
	
	/** 
	 * 				/!\      GUIDE LINE POUR KENTIN       /!\
	 * 
	 * Si je met OK c'est que la partie graphique est faite, il reste toujours à lier LocalUser avec mes trucs pour que tout fonctionne
	 * 
	 *  
	 * Need to implement logout button  OK
	 * __________________change login button <- On lui demande d'abord de se connecter ou direct s'il veut changer son pseudo ?
	 * When window closes ---- you're disconnected OK
	 * Print on screen (not console) when a login already exists in BDD (authentify or createAccount will return null) OK
	 * Same goes for an incorrect login ---- un pop up maybe ? OK
	 * Informe le user quand son compte est bien créé et qu'il peut se connecter <- Autant juste ouvrir le ChatWindow, c'est assez explicite
	 * PRIVATIZEEEEEEEEE <- ça on verra a la fin x) je privatizerai tout d'un coup
	 * je suppose que click sur login de qqun -> startSession(qqun) buuuut there's a method send message ---- you need to figure out how to use both <- Avant de send un message faut choisir à qui on l'envoie non ?
	 * Need a way to stop session ---- you cant just quit --- il faut permettre à l'utilisateur de mettre fin à une session unilatéralement OK
	 * Maybe add a refresh button qui actualise la liste des connectés <- Si on arrive pas a faire comme Brice c'est ce qu'on fera je pense
	 * 
	 * Il ya des null pointer PAS DE PANIQUE c'est normal ------- mets la ligne 11 en commentaire
	 * 
	 * 
	 * **/
	
	public void login(String lgc) {
		
		localHost = localHost.authentify(lgc);
		new ChatWindow(localHost);
		//System.out.println("Authentified");
		//localHost.startSession(localHost.getLogin());
		//localHost.sendMessage(localHost.getLogin(),"CINNAMOOOOOON "); // pourquoi tu envoies un message dans le login ?
		//ArrayList<Message> me = Database.getHistory(localHost.getLogin(), localHost.getLogin());
		
		//System.out.println(me.toString());
	}
	
	public void actionPerformed(ActionEvent e) {
		
			login(getLogin(connectLogin));
			
		}
	
	private String getLogin(JTextField log) {
		return(log.getText());
	}
	
	

	public void keyTyped(KeyEvent keyEvent) {
	    char typed = keyEvent.getKeyChar();
	    if(typed == '\n') {    	    
	    	login(getLogin(connectLogin));
	  		}
	 }
	  
	public void keyPressed(KeyEvent keyEvent) {/*Nothing*/}

	public void keyReleased(KeyEvent keyEvent) {/*Nothing*/}
	
	public static void main(String[] args) {
		new LoginWindow();
	}

}
