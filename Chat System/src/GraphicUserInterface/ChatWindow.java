package GraphicUserInterface;

import java.awt.*;
import Data.*;
import java.awt.event.*;
import java.util.List;
import java.util.ListIterator;

import javax.swing.*;
import javax.swing.border.Border;

public class ChatWindow implements ActionListener,KeyListener {
	/**
	 * /!\ NEW GUIDELINES /!\
	 * 
	 * Il faut r√©initialiser la barre de text quand le message est envoy√© !!!
	 * Faire une fonction statique "erreur" ---- comme ca local user can notify the GUI when
	   there's an issue
	 * -----Je me suis vraiment maitrisÈe pour pas tout privatiserrrrrr
	 * May have to get rid of the create account option
	 * 
	 * */
	private static JPanel affBorder = new JPanel();
	private static JPanel affInner = new JPanel();
	private static JLabel affTxt = new JLabel();
	private static JLabel affNom = new JLabel(); 
	private static JFrame frame;
	
	private static String dest;
	
	private JTextField text;
	
	private static JList<String> list;
	private static DefaultListModel<String> listModel = new DefaultListModel<String>();
	private static LocalUser localHost ;
	
	
	private static JButton quitSession;
	private static JButton changeLogin;
	
    public ChatWindow(LocalUser user) {
    	
    	ChatWindow.localHost = user;
    	    	
    	//Create and set up the window.
        frame = new JFrame("ChatWindow");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter()
        {
        	public void windowClosing(WindowEvent e) {
        		localHost.disconnect();
        		frame.dispose();
        		new HomeWindow();
        	}       	
        });
        
        //frame.setResizable(false);
        Container pane = frame.getContentPane();
        
	    
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();		
		
		
		//affichage label "People Online"
		JLabel onliners = new JLabel("People Online :");
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10,0,15,10);
		c.anchor = GridBagConstraints.LINE_START;
		pane.add(onliners,c);
	        
        
        JScrollPane listScrollPane = new JScrollPane(list); 
        listScrollPane.setPreferredSize(new Dimension(150, 490));
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
		c.gridy = 1;        
        pane.add(listScrollPane,c);
        
        JButton logOut = new JButton("Deconnexion");
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.insets = new Insets(10,10,10,10);
        logOut.addActionListener(this);
        pane.add(logOut,c);
		
		//Cr√©ation label qui affichera le nom de la personne avec qui la session de clavardage est ouverte	
		c.gridx = 1;
		c.gridy = 0;
		affNom.setText("");
		pane.add(affNom,c);
		
		quitSession = new JButton("Fin de session");
		c.gridx = 2;
		c.gridy = 0;
		quitSession.setEnabled(false);
		quitSession.addActionListener(this);
		pane.add(quitSession,c);
		
		changeLogin = new JButton("Change de Login");
		c.gridx = 3;
		c.gridy = 0;
		changeLogin.addActionListener(this);
		pane.add(changeLogin,c);
					
		
		//Cr√©ation zone d'affichage de la conversation		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);	
		affBorder.setBorder(border);
		affInner.setBorder(border);
		
		//affBorder.setBounds(500,0,500, (noms.length * 410)/15 );
		//affBorder.setMaximumSize(new Dimension(500,(noms.length*410)/15));
		//affBorder.setMinimumSize(new Dimension(500,(noms.length*410)/15));
		
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 500; // Ne pas mettre de ipadxy ce sont des marges int√©rieures !
		c.ipady = 470;
		c.gridwidth = 3;
		c.insets = new Insets(0,0,10,10);
		affInner.add(affTxt);
		affBorder.add(affInner);
		
		pane.add(affBorder,c);
		
		//Cr√©ation zone de saisie de texte
		text = new JTextField("");
		c.gridx = 1;
		c.gridy = 2;
		c.ipadx = 520;
		c.ipady = 5;
		c.gridwidth = 3;
		c.insets = new Insets(0,0,10,10);
		text.addKeyListener(this);
		pane.add(text,c);	
		
		//creation zone de texte pour changement de login
		
		
		frame.pack();
        frame.setVisible(true);
	
    } 
    
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand() == "Deconnexion") {
    		ChatWindow.localHost.disconnect();
    		frame.dispose();
    		new HomeWindow();
    	}
    	else if (e.getActionCommand() == "Fin de session") {
    		/** Clear l'affichage 
    		 * TODO
    		 * */
    		    		
    		/** Fermer la session
    		 * TODO 
    		 * J'ai une fonction pour ca mais je dois r√©cuperer le nom de l'interlocuteur
    		 */
    		
    		ChatWindow.localHost.endSession(dest);
    		
    		
    		
    		/** quitButton.setEnable(false);
    		 * TODO
    		 * */
    		
    	}
    	else if (e.getActionCommand() == "Changer de Login") {
    		/** 
    		 TODO
    		 Doit avoir une zone de text ‡ cotÈ du bouton et appeler la fonction change login. I tried and messed up.
    		 Aussi, ma fonction change login rÈcupËre un string.
    		 
    		 */
    		localHost.changeLogin(text.getText());
    		
    		
    	}
    }
    
    public static void updateUsers(List<RemoteUser> newList) {
    	
    	listModel.removeAllElements();
    	
    	ListIterator<RemoteUser> iterator = newList.listIterator() ;
		RemoteUser current = null; 
	    while(iterator.hasNext()){
	     	current = iterator.next() ;
	     	if(current == null)	System.out.println("Problem");
			listModel.addElement(current.getLogin());
			
			list = new JList<String>(listModel);
	        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        list.setSelectedIndex(0);
	        list.addMouseListener(new MouseAdapter() { 
	        	public void mouseClicked(MouseEvent e) {
	        
	        		if (e.getClickCount() == 2) {
			            int index = list.locationToIndex(e.getPoint());
			            String log = (String) listModel.getElementAt(index);
			            localHost.startSession(log);
			            affNom.setText("Conversation avec " + listModel.getElementAt(index));
			            dest = (String)listModel.getElementAt(index);
			            
			            quitSession.setEnabled(true);
	        		}
	        	}
	        });
	        list.setVisibleRowCount(15);
	    }
    }
    
    public static void updateMessageDisplay(List<Message> newList) {
    	
    	/** 	RÈinitialiser chat display ?? ------ ou alors just keep adding last message to screen*/
    	
    	listModel.removeAllElements();
    	
    	ListIterator<Message> iterator = newList.listIterator() ;
		Message current = null; 
	    while(iterator.hasNext()){
	     	current = iterator.next() ;
			listModel.addElement(current.toString());
			list = new JList<String>(listModel);
	        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        list.setSelectedIndex(0);
	        list.setVisibleRowCount(15);
	    }
    }
    
    public void keyTyped(KeyEvent keyEvent) {
	    char typed = keyEvent.getKeyChar();
	    if(typed == '\n') {    
			//Send message
	    	localHost.sendMessage(dest,text.getText());
	    	//affTxt.setText(text.getText());
	    }
	}
	  
	public void keyPressed(KeyEvent keyEvent) {/*Nothing*/}

	public void keyReleased(KeyEvent keyEvent) {/*Nothing*/}
    

    public static void main(String[] args) {
    	
        //new ChatWindow(LocalUser user);
    }
}



