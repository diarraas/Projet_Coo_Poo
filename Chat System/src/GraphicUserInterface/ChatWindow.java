package GraphicUserInterface;

import java.awt.*;
import Data.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;

public class ChatWindow implements ActionListener {
	/**
	 * /!\ NEW GUIDELINES /!\
	 * 
	 * Add une methode updateUsers ----- qui update graphiquement --- j'ajoute la même coté udp qui actualise les données des connecté
	 * En gros cette méthode prend une liste d'utilisateur et change le tableau d'affichage des connectés
	 * Il faut initialiser localHost dans ton constructeur ---- I'm changing some things tu m'expliqueras plus tard
	 * 
	 * 
	 * */
	public static JPanel aff_border = new JPanel();
	public static JPanel aff_inner = new JPanel();
	public static JLabel aff_txt = new JLabel();
	public static JLabel aff_nom = new JLabel(); 
	public static JFrame frame;
	
	public static JList list;
	public static DefaultListModel listModel = new DefaultListModel();
	private static LocalUser localHost ;
	
	private static JButton quitSession;
	
    public ChatWindow(LocalUser user) {
    	
    	this.localHost = user;
    	System.out.println("BroadcastCLIENT = " + this.localHost.getBroadcastClient()+ "\n");
    	    	
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
        
        JButton logOut = new JButton("LogOut");
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.insets = new Insets(10,10,10,10);
        logOut.addActionListener(this);
        pane.add(logOut,c);
		
		//Création label qui affichera le nom de la personne avec qui la session de clavardage est ouverte	
		c.gridx = 1;
		c.gridy = 0;
		aff_nom.setText("");
		pane.add(aff_nom,c);
		
		quitSession = new JButton("QuitSession");
		c.gridx = 2;
		c.gridy = 0;
		quitSession.setEnabled(false);
		quitSession.addActionListener(this);
		pane.add(quitSession,c);
			
		
		//Création zone d'affichage de la conversation		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);	
		aff_border.setBorder(border);
		aff_inner.setBorder(border);
		
		//aff_border.setBounds(500,0,500, (noms.length * 410)/15 );
		//aff_border.setMaximumSize(new Dimension(500,(noms.length*410)/15));
		//aff_border.setMinimumSize(new Dimension(500,(noms.length*410)/15));
		
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 500; // Ne pas mettre de ipadxy ce sont des marges intérieures !
		c.ipady = 470;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,10,10);
		aff_inner.add(aff_txt);
		aff_border.add(aff_inner);
		
		pane.add(aff_border,c);
		
		//Création zone de saisie de texte
		JTextField text = new JTextField("");
		c.gridx = 1;
		c.gridy = 2;
		c.ipadx = 520;
		c.ipady = 5;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,10,10);
		pane.add(text,c);	
		
		frame.pack();
        frame.setVisible(true);
	
    } 
    
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand() == "LogOut") {
    		this.localHost.disconnect();
    		frame.dispose();
    		new HomeWindow();
    	}
    	else if (e.getActionCommand() == "QuitSession") {
    		//Clear l'affichage
    		//Fermer la session
    		//quitButton.setEnable(false);
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
			
			list = new JList(listModel);
	        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        list.setSelectedIndex(0);
	        list.addMouseListener(new MouseAdapter() { 
	        	public void mouseClicked(MouseEvent e) {
	        
	        		if (e.getClickCount() == 2) {
			            int index = list.locationToIndex(e.getPoint());
			            String log = (String) listModel.getElementAt(index);
			            aff_nom.setText("Conversation avec " + listModel.getElementAt(index));
			            quitSession.setEnabled(true);
	        		}
	        	}
	        });
	        list.setVisibleRowCount(15);
	    }
    }
    
    /*public static void newConnected(String newC) {
    	int index = -1;
    	listModel.insertElementAt(newC,index);
    }
    
    public static void newDisconnected (String newDc) {
    	for(int index = 0; index < listModel.getSize(); index ++) {
    		if (listModel.getElementAt(index)== newDc) {
    			listModel.removeElementAt(index);
    			return;
    		}
    	}
    }*/

    public static void main(String[] args) {
    	
        //new ChatWindow(LocalUser user);
    }
}



