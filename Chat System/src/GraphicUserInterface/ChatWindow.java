package GraphicUserInterface;

import java.awt.*;
import Data.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;

import Data.RemoteUser;

public class ChatWindow implements ListSelectionListener{
	
	public static JPanel aff_border = new JPanel();
	public static JPanel aff_inner = new JPanel();
	public static JLabel aff_txt = new JLabel();
	public static JLabel aff_nom = new JLabel(); 
	public static JFrame frame;
	
	public static JList list;
	public static DefaultListModel listModel;
	private static LocalUser localHost ;
	
    public ChatWindow(List<RemoteUser> noms) {
    	
    	//Create and set up the window.
        frame = new JFrame("ChatWindow");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        Container pane = frame.getContentPane();
        
	    JButton button;
	    int index;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();		
		
		
		//affichage label "People Online"
		JLabel onliners = new JLabel("People Online :");
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10,0,15,10);
		c.anchor = GridBagConstraints.LINE_START;
		pane.add(onliners,c);
		
		//Création et ajout des people connectés (pas dans cette classe, tableau dynamique)
		listModel = new DefaultListModel();
		for(index = 1; index<(noms.length);index++) { //Foutre un itérateur
			listModel.addElement(noms[index-1]);			
		}
		
		list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(15);
        
        JScrollPane listScrollPane = new JScrollPane(list); 
        listScrollPane.setPreferredSize(new Dimension(150, 490));
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
		c.gridy = 1;        
        pane.add(listScrollPane,c);
		
		//Création label qui affichera le nom de la personne avec qui la session de clavardage est ouverte	
		c.gridx = 1;
		c.gridy = 0;
		aff_nom.setText("");
		pane.add(aff_nom);
		
		//Création zone de saisie de texte
		JTextField text = new JTextField("");
		c.gridx = 1;
		c.gridy = index;
		c.ipadx = 510;
		c.ipady = 5;
		pane.add(text,c);		
		
		//Création zone d'affichage de la conversation		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);	
		aff_border.setBorder(border);
		aff_inner.setBorder(border);
		
		//aff_border.setBounds(500,0,500, (noms.length * 410)/15 );
		aff_border.setMaximumSize(new Dimension(500,(noms.length*410)/15));
		aff_border.setMinimumSize(new Dimension(500,(noms.length*410)/15));
		
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 500;
		c.ipady = (noms.length * 410)/15;
		c.gridheight = (noms.length -1) ;
		aff_inner.add(aff_txt);
		aff_border.add(aff_inner);
		
		pane.add(aff_border,c);
		
		frame.pack();
        frame.setVisible(true);
	
    } 
    
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
 
            if (list.getSelectedIndex() == -1) {            
            		//Nothing
            } else {
            	aff_nom.setText("Conversation avec " + listModel.getElementAt(list.getSelectedIndex()));
        		
        		//aff_inner.remove(aff_txt);
        		
        		aff_txt.setText((String) listModel.getElementAt(list.getSelectedIndex()));
        		
        		//aff_border.setSize(aff_border.getWidth()-13, aff_border.getHeight()-27);
            	
        		//aff_inner.add(aff_txt);  */
            }
        }
    }
    
    public static void newConnected(String newC) {
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
    }

    public static void main(String[] args) {
    	
    	List<RemoteUser> noms = localHost.getOnliners();
        new ChatWindow(noms);
    }
}



