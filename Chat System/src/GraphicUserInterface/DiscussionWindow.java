package GraphicUserInterface;

import java.awt.event.*;

import java.util.List;
import java.util.ListIterator;

import  javax.swing.*;
import  javax.swing.filechooser.FileSystemView;

import Data.*;


public class DiscussionWindow extends  JFrame implements ActionListener,KeyListener {

	private static final long serialVersionUID = 1L;
	
	private static JButton changeLogin;
    private static JScrollPane chatTextDisplay;
    private static JButton endSession;
    private static JScrollPane onlinersDisplay;
    private static JList<String> listOnliners;
    private static DefaultListModel<String> onlinersModel;
    private static JButton logOut;
    private static JList<String> messageList;
    private static DefaultListModel<String> messageModel;
    private static JLabel onlinersLabel;
    private static JButton sendFile;
    private static JTextField textField;
    
    private static LocalUser localHost ;
    private static String dest ;

    
    
    public DiscussionWindow() {    }


    public void initComponents() {

        logOut = new JButton();
        changeLogin = new JButton();
        sendFile = new JButton();
        endSession = new JButton();
        textField = new JTextField();
        chatTextDisplay = new JScrollPane();
        messageModel = new DefaultListModel<String>();
        messageList = new JList<String>(messageModel);
        onlinersModel = new DefaultListModel<String>();
        onlinersDisplay = new JScrollPane();
        listOnliners = new JList<String>(onlinersModel);
        onlinersLabel = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                localHost.disconnect();
            }
        });
        
        logOut.setText("Deconnexion");
        logOut.addActionListener(this);

        changeLogin.setText("Changer de pseudo");
        changeLogin.addActionListener(this);
        
        sendFile.setText("Envoyer un fichier");
        sendFile.addActionListener(this);
        sendFile.setEnabled(false);
        
        endSession.setText("Fermer la session");
        endSession.addActionListener(this);
        endSession.setEnabled(false);
        
        textField.setText("");
        textField.addKeyListener(this);

    	updateOnlineUsers(localHost.getOnliners());

        
        onlinersLabel.setText("Utilisateurs en ligne :");

         GroupLayout layout = new  GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING, false)
                    .addComponent(onlinersLabel,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(onlinersDisplay)
                    .addComponent(changeLogin,  GroupLayout.Alignment.TRAILING,  GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(logOut,  GroupLayout.Alignment.TRAILING,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
                            .addComponent(chatTextDisplay)
                            .addComponent(textField)))
                    .addGroup( GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
                        .addComponent(endSession,  GroupLayout.PREFERRED_SIZE, 196,  GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sendFile,  GroupLayout.PREFERRED_SIZE, 196,  GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                            .addComponent(sendFile)
                            .addComponent(endSession)))
                    .addGroup( GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(onlinersLabel)))
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
                    .addComponent(chatTextDisplay)
                    .addComponent(onlinersDisplay,  GroupLayout.Alignment.TRAILING,  GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(logOut)
                    .addComponent(textField,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changeLogin)
                .addContainerGap())
        );
        setTitle("Fenetre de discussion");
        pack();
        this.setVisible(true);
    }                       



	public void keyTyped(KeyEvent e) {
		char typed = e.getKeyChar();
	    if(typed == '\n') {
	    	if(localHost.findSessionWith(dest) != null) {
		    	localHost.sendMessage(dest,textField.getText());
				updateMessageDisplay(localHost.findSessionWith(dest).getSentMessages());
				textField.setText("");;
	    	}else {
	    		new NotificationWindow("Veuillez commencer une session");
	    	}
			
	    }
	}


	@Override
	public  void keyPressed(KeyEvent e) {
		// N/A
		
	}


	@Override
	public  void keyReleased(KeyEvent e) {
		// N/A
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("Deconnexion")) {
    		localHost.disconnect();
    		this.dispose();
        	System.exit(0);
    	}
    	else if (e.getActionCommand().equals("Fermer la session")) {
    		updateMessageDisplay(null);
    		localHost.endSessionWith(dest);
    		dest = null ;
    		endSession.setEnabled(false);
    		sendFile.setEnabled(false);

    		   	
    		
    	}
    	else if (e.getActionCommand().equals("Changer de pseudo")) {
    		ChangeWindow change = new ChangeWindow();
    		change.setLocalHost(localHost);
    		change.init();
    		
    	}
    	else if (e.getActionCommand().equals("Envoyer un fichier")) {
    		if(dest == null) new NotificationWindow("Veuillez commencer une session");
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
            int r = chooser.showSaveDialog(null); 
            
            if (r == JFileChooser.APPROVE_OPTION) { 
            	
            	localHost.sendFile(dest,chooser.getSelectedFile().getAbsolutePath());
            }    
    	}else if (e.getActionCommand().equals("save")){
    		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
            int r = chooser.showSaveDialog(null); 
            
            if (r == JFileChooser.APPROVE_OPTION) { 
            	
            	localHost.sendFile(dest,chooser.getSelectedFile().getAbsolutePath());
            }    
    	}
    } 
	
	
	public static void updateOnlineUsers(List<RemoteUser> newList) {
		int preSelected = -1 ;
		if(onlinersModel != null) {
				preSelected = listOnliners.getSelectedIndex();
				onlinersModel.removeAllElements();
				System.out.println("selected : " + preSelected );
		}else {
			onlinersModel = new DefaultListModel<String>();
		}
		
		ListIterator<RemoteUser> iterator = newList.listIterator() ;
		RemoteUser current = null; 
	    while(iterator.hasNext()){
	     	current = iterator.next() ;
	     	onlinersModel.addElement(current.getLogin());
	    }
		
		listOnliners = new JList<String>(onlinersModel);
		listOnliners.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOnliners.addMouseListener(new MouseAdapter() { 
        	public void mouseClicked(MouseEvent e) {
        
        		if (e.getClickCount() == 2) {
		            int index = listOnliners.locationToIndex(e.getPoint());
		            updateSession((String) onlinersModel.getElementAt(index));			            
        		}
        	}
        });
		if(preSelected != -1) {
			listOnliners.setSelectedIndex(preSelected);
			updateSession((String) onlinersModel.getElementAt(preSelected));
		}
		onlinersDisplay = new JScrollPane();
		onlinersDisplay.setViewportView(listOnliners);
        
	}
	
	public static void updateMessageDisplay(List<Message> newList) {
		if(newList != null && messageModel != null) {
			messageModel.removeAllElements();
			ListIterator<Message> iterator = newList.listIterator() ;
			Message current = null; 
		    while(iterator.hasNext()){
		     	current = iterator.next() ;
		     	messageModel.addElement(current.toString());
		    }
					
			messageList.setModel(messageModel);
	        chatTextDisplay.setViewportView(messageList);
		}else {
			DefaultListModel<String> messageModel = new DefaultListModel<String>();
			messageList.setModel(messageModel);
	        chatTextDisplay.setViewportView(messageList);
		}
	}

	
	public static void updateSession(String user) {
		System.out.println("Now talking to : \t " + user);
		dest = user;
		localHost.startSession(dest);
	    endSession.setEnabled(true);
	    sendFile.setEnabled(true);
		
	}
	
	public void setLocalHost(LocalUser localHost) {
		DiscussionWindow.localHost = localHost;
	}
	
                 
}