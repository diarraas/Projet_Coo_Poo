package GraphicUserInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



/** TODO
 * Quand on reçoit un message (et qu'on a pas de session active) -> ouvrir une session avec dest = celui qui nous envoie un message
 * Mettre startSession en static (?)
 * 
 * changelogin <- nouvelle fenetre
 * 
 */





public class HomeWindow implements ActionListener{
	
	private JFrame frame; 
	
	public HomeWindow() {
	frame = new JFrame("HomeWindow");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    
    Container pane = frame.getContentPane();
    pane.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	
	JButton logIn = new JButton("Se connecter");
	c.gridx = 0;
	c.gridy = 0;
	c.anchor = GridBagConstraints.CENTER;
	c.insets = new Insets(20,20,20,20);
	logIn.addActionListener(this);
	logIn.setPreferredSize(new Dimension (300,20));
	pane.add(logIn,c);
	
	
	JButton changeLogin = new JButton("Changer Login");
	c.gridx = 0;
	c.gridy = 1;
	c.anchor = GridBagConstraints.CENTER;
	changeLogin.addActionListener(this);
	changeLogin.setPreferredSize(new Dimension (300,20));
	pane.add(changeLogin,c);
	
	
	
	JButton createAccount = new JButton("Creer un compte");
	c.gridx = 0;
	c.gridy = 2;
	c.anchor = GridBagConstraints.CENTER;
	createAccount.addActionListener(this);
	createAccount.setPreferredSize(new Dimension (300,20));
	pane.add(createAccount,c); 
	
	
	
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    
    
	}

	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Se connecter") {
			frame.dispose();
			new LoginWindow();
		}
		else if (e.getActionCommand() == "Change Login") {
			//
		}
		else if (e.getActionCommand() == "Creer un compte") {
			frame.dispose();
			new CreateAccountWindow();
		}
	
	}
	
	public static void main(String[] args) {
		//new HomeWindow();
	}
	
}
