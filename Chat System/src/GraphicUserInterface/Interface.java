package GraphicUserInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;



public class Interface implements ActionListener {
	
	public static JPanel aff_border = new JPanel();
	public static JPanel aff_inner = new JPanel();
	public static JLabel aff_txt = new JLabel();
	public static JLabel aff_nom = new JLabel(); 

    public Interface(String[] ponline) {
    	
    	//Create and set up the window.
        JFrame frame = new JFrame("Bonjour");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        Container pane = frame.getContentPane();
        
	    JButton button;
	    int index;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		String[] noms = ponline;
		
		
		
		//affichage label "People Online"
		JLabel onliners = new JLabel("People Online :");
		c.gridy = 0;
		c.insets = new Insets(10,0,15,10);
		c.anchor = GridBagConstraints.LINE_START;
		pane.add(onliners,c);
		
		//Création et ajout des boutons correspondant aux people connectés
		for(index = 1; index<(noms.length);index++) {
			
			button = new JButton(noms[index-1]);	
			c.anchor = GridBagConstraints.LINE_START;
			c.gridy = index;
			c.gridwidth = 1;
			c.insets = new Insets(0,0,10,10);
			button.setPreferredSize(new Dimension (100,20));
			button.addActionListener(this);
			
			pane.add(button, c);
		}
		
		button = new JButton(noms[index-1]);
		button.setPreferredSize(new Dimension (100,20));
		c.gridy = index;	
		pane.add(button,c);
		
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
		
		aff_border.setBounds(500,0,500, (noms.length * 410)/15 );
		aff_border.setMaximumSize(new Dimension(500,(noms.length*410)/15));
		
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
    
    
	public void actionPerformed(ActionEvent e) {
		// Affichage de la personne avec qui on ouvre une session
		aff_nom.setText("Conversation avec " + e.getActionCommand());
		
		aff_txt.setText(e.getActionCommand());
    	
		
		aff_border.setSize(aff_border.getWidth()-13, aff_border.getHeight()-27);
    	
    	
    }

    public static void main(String[] args) {
    	
    	String[] noms = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q"};
        new Interface(noms);
    }
}



