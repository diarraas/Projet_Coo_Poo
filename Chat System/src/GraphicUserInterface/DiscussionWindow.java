package GraphicUserInterface;

import java.awt.event.*;

import java.util.List;
import java.util.ListIterator;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import Data.*;


public class DiscussionWindow extends javax.swing.JFrame implements ActionListener,KeyListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JButton changeLogin;
    private static JScrollPane chatTextDisplay;
    private static JButton endSession;
    private static JScrollPane onlinersDisplay;
    private static JList<String> listOnliners;
    private static JButton logOut;
    private static JList<String> messageList;
    private static JLabel onlinersLabel;
    private static JButton sendFile;
    private static JTextField textField;
    
    private static LocalUser localHost ;
    private static String dest ;

    
    
    public DiscussionWindow(LocalUser user) {
    	localHost = user ;
        initComponents();
    }


    private void initComponents() {

        logOut = new JButton();
        changeLogin = new JButton();
        sendFile = new JButton();
        endSession = new JButton();
        textField = new JTextField();
        chatTextDisplay = new JScrollPane();
        messageList = new JList<String>();
        onlinersDisplay = new JScrollPane();
        listOnliners = new JList<String>();
        onlinersLabel = new JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        updateOnlineUsers(localHost.getOnliners());
        
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



        onlinersLabel.setText("Utilisateurs en ligne :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(onlinersLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(onlinersDisplay)
                    .addComponent(changeLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(logOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chatTextDisplay)
                            .addComponent(textField)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
                        .addComponent(endSession, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sendFile, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sendFile)
                            .addComponent(endSession)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(onlinersLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chatTextDisplay)
                    .addComponent(onlinersDisplay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logOut)
                    .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changeLogin)
                .addContainerGap())
        );

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
    		   	
    		
    	}
    	else if (e.getActionCommand().equals("Changer de pseudo")) {
  
    		new ChangeWindow(localHost);
    		
    	}
    	else if (e.getActionCommand().equals("Envoyer un fichier")) {
    		
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
		DefaultListModel<String> onlinersModel = new DefaultListModel<String>();
		
		ListIterator<RemoteUser> iterator = newList.listIterator() ;
		RemoteUser current = null; 
	    while(iterator.hasNext()){
	     	current = iterator.next() ;
	     	System.out.println("Current user is : " + current.toString());
	     	onlinersModel.addElement(current.getLogin());
	    }
		
		listOnliners.setModel(onlinersModel);
		listOnliners.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOnliners.setSelectedIndex(0);
		listOnliners.addMouseListener(new MouseAdapter() { 
        	public void mouseClicked(MouseEvent e) {
        
        		if (e.getClickCount() == 2) {
		            int index = listOnliners.locationToIndex(e.getPoint());
		            updateSession((String) onlinersModel.getElementAt(index));			            
        		}
        	}
        });
		onlinersDisplay.setViewportView(listOnliners);
        
	}
	
	public static void updateMessageDisplay(List<Message> newList) {
		if(newList != null) {
			DefaultListModel<String> messageModel = new DefaultListModel<String>();
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
		dest = user;
		localHost.startSession(dest);
	    endSession.setEnabled(true);
	    sendFile.setEnabled(true);
		
	}
	
                 
}
