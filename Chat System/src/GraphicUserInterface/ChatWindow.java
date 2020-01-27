package GraphicUserInterface;

import java.awt.*;
import Data.*;
import java.awt.event.*;
import java.util.List;
import java.util.ListIterator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;

public class ChatWindow implements ActionListener,KeyListener {
	/**
	 * /!\ NEW GUIDELINES /!\
	 * Commence par les TODO ?? (icone notepad sur le cot�) --- l� c'est que du front-end du coup
	 * Faire une fonction statique g�n�rique "erreur" ---- comme ca local user can notify the GUI when
	   there's an issue. L'afficher quelque part sur la fen�tre or pop up --- whatever's easier for ya
	 * Only keep login, change log and chat window
	 * J'ai rajout� updateChat ou chaipukoi la qui est cens�e rafraichir l'affichage de l'historique � chaque envoi
	   Elle doit appeler la m�thode getHistory de ChatSession ---- you'll need me for this one
	 * Pr�voir le cas ou un user veut commencer une session avec qqun d'autre alors qu'il a une session en cours ---- tu peux faire un
	 	pop up pour notifier ou mettre une condition (test if dest != null par exemple) and end session before starting anew (i'd rather you do that tbh :) ).
	 * Is there a way to let the user know -- graphiquement --- that a remote user started a session with him ? like a thread or sum ?
	 * Can we find a way to resize the window ? chui suure que c'est faisable
	 * Pourquoi quand on commence une session la liste des connect�s disparait ?
	 * */
	private static JPanel affBorder = new JPanel();
	private static JPanel affInner = new JPanel();
	private static JLabel affTxt = new JLabel();
	private static JLabel affNom = new JLabel(); 
	private static JScrollPane scroll = new JScrollPane(affTxt);
	private static JFrame frame;
	
	private static JTextField text;
	private static JLabel affNotif;

	private static JList<String> list;
	private static DefaultListModel<String> listModel = new DefaultListModel<String>();
	private static LocalUser localHost ;
	
	
	private static JButton quitSession;
	private static JButton changeLogin;
	private static JButton sendFile;
	private static JButton logOut ;
	
	public static String dest ;
	
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
		JLabel onliners = new JLabel("Utilisateurs en ligne :");
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
        
        
        /*		Chat Options 	*/
        logOut = new JButton("Deconnexion");
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.insets = new Insets(10,10,10,10);
        logOut.addActionListener(this);
        pane.add(logOut,c);
        
        
        quitSession = new JButton("Fin de session");
		c.gridx = 2;
		c.gridy = 0;
		quitSession.setEnabled(false);
		quitSession.addActionListener(this);
		pane.add(quitSession,c);
		
		changeLogin = new JButton("Changer de Login");
		c.gridx = 0;
		c.gridy = 3;
		changeLogin.addActionListener(this);
		pane.add(changeLogin,c);
        
        //Test de l'envoi de fichier
        sendFile = new JButton("Envoyer un fichier");
        c.gridx = 3;
        c.gridy = 0;
		sendFile.setEnabled(false);
        sendFile.addActionListener(this);
        pane.add(sendFile,c);
        
		
		//Création label qui affichera le nom de la personne avec qui la session de clavardage est ouverte	
		c.gridx = 1;
		c.gridy = 0;
		affNom.setText("");
		pane.add(affNom,c);
		
					
		
		//Création zone d'affichage de la conversation		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);	
		affBorder.setBorder(border);
		affInner.setBorder(border);
		
		//affBorder.setBounds(500,0,500, (noms.length * 410)/15 );
		//affBorder.setMaximumSize(new Dimension(500,(noms.length*410)/15));
		//affBorder.setMinimumSize(new Dimension(500,(noms.length*410)/15));
		
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 500; // Ne pas mettre de ipadxy ce sont des marges intérieures !
		c.ipady = 470;
		c.gridwidth = 3;
		c.insets = new Insets(0,0,10,10);
		affInner.add(affTxt);
		affBorder.add(affInner);
		
		pane.add(affBorder,c);
		
		//Création zone de saisie de texte
		text = new JTextField("");
		c.gridx = 1;
		c.gridy = 2;
		c.ipadx = 520;
		c.ipady = 5;
		c.gridwidth = 3;
		c.insets = new Insets(0,0,10,10);
		text.addKeyListener(this);
		pane.add(text,c);	
		
		//Zone d'affichage d'erreur
		affNotif = new JLabel("");
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(0,0,10,0);
		pane.add(affNotif,c);
		affTxt.add(scroll);
		frame.pack();
        frame.setVisible(true);
	
    } 
    
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("Deconnexion")) {
    		ChatWindow.localHost.disconnect();
    		frame.dispose();
    		new LoginWindow();
    	}
    	else if (e.getActionCommand().equals("Fin de session")) {
    		/** Clear l'affichage 
    		 * TODO
    		 * */
    		    		
    		/** Fermer la session
    		 */

    		localHost.endSessionWith(dest);
    		quitSession.setEnabled(false);
    		   	
    		
    	}
    	else if (e.getActionCommand().equals("Changer de Login")) {
  
    		new ChangeWindow(localHost);
    		
    	}
    	else if (e.getActionCommand().equals("Envoyer un fichier")) {
    		
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
            int r = chooser.showSaveDialog(null); 
            
            if (r == JFileChooser.APPROVE_OPTION) { 
            	
            	localHost.sendFile(chooser.getSelectedFile().getAbsolutePath());
            }    
    	}else if (e.getActionCommand().equals("save")){
    		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
            int r = chooser.showSaveDialog(null); 
            
            if (r == JFileChooser.APPROVE_OPTION) { 
            	
            	localHost.sendFile(chooser.getSelectedFile().getAbsolutePath());
            }    
    	}
    }
    
    public static void updateUsers(List<RemoteUser> newList) {
    	
    	listModel.removeAllElements();
    	
    	ListIterator<RemoteUser> iterator = newList.listIterator() ;
		RemoteUser current = null; 
	    while(iterator.hasNext()){
	     	current = iterator.next() ;
			listModel.addElement(current.getLogin());
			
			list = new JList<String>(listModel);
	        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        list.setSelectedIndex(0);
	        list.addMouseListener(new MouseAdapter() { 
	        	public void mouseClicked(MouseEvent e) {
	        
	        		if (e.getClickCount() == 2) {
			            int index = list.locationToIndex(e.getPoint());
			            dest = (String) listModel.getElementAt(index);
			            localHost.startSession(dest);
			            affNom.setText("Conversation avec " + listModel.getElementAt(index));			            
			            quitSession.setEnabled(true);
			            sendFile.setEnabled(true);
	        		}
	        	}
	        });
	        list.setVisibleRowCount(15);
	    }
    }
    
    public static void notificationMessage(String message) {
    	 affNotif.setText(message);
    	 affNotif.setForeground (Color.red);
    }
    
    
    /**
     * 
     * JscrollPad
     * TODO : Ce serait bien une liste de message qui s'affichent l� ou t'affiche le message envoy�.
     * Pour le back-end  ----- localHost.findSessionWith(Dest).getSentMessage() ---- check it out
      * */
    
    public static void updateMessageDisplay(List<Message> newList) {
    	
    	
    	affTxt = new JLabel();
    	
    	ListIterator<Message> iterator = newList.listIterator() ;
		Message current = null; 
	    while(iterator.hasNext()){
	     	current = iterator.next() ;
	     	affInner.add(new JLabel(current.toString()));
	     	affTxt.add(new JLabel(current.toString()));
			//Another magic trick ^^ 
	    }
    }
    
    public void keyTyped(KeyEvent keyEvent) {
	    char typed = keyEvent.getKeyChar();
	    if(typed == '\n') {
	    	if(localHost.findSessionWith(dest) != null) {
				//Send message
		    	localHost.sendMessage(text.getText());
				updateMessageDisplay(localHost.findSessionWith(dest).getHistory());
		    	text.setText("");;
	    	}else {
	    		/**
	    		 * TODO
	    		 * Créer une classe NotifWindow qui prend un message dans son constructeur et fait juste un pop up du message
	    		 * ***/
	    		//new NotificationWindow("Commencer une session");
	    	}
			
	    }
	}
	  
	public void keyPressed(KeyEvent keyEvent) {/*Nothing*/}

	public void keyReleased(KeyEvent keyEvent) {/*Nothing*/}
    
    
}



