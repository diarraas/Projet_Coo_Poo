package GraphicUserInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;



public class Interface implements ActionListener {
	
	public static JPanel aff = new JPanel();
	public static JLabel aff_txt = new JLabel();

    public Interface() {
    	
    	//Create and set up the window.
        JFrame frame = new JFrame("Bonjour");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Container pane = frame.getContentPane();
        
	    JButton button;
	    int index;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		String[] noms = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q"};
		
		
		JLabel onliners = new JLabel("People Online :");
		c.gridy = 0;
		c.insets = new Insets(10,0,15,10);
		c.anchor = GridBagConstraints.LINE_START;
		pane.add(onliners,c);
		
		
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
		
		
		JTextField text = new JTextField("");
		c.gridx = 1;
		c.gridy = index;
		c.ipadx = 510;
		c.ipady = 5;
		c.anchor = GridBagConstraints.LINE_START;
		pane.add(text,c);
		
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);	
		aff.setBorder(border);
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 500;
		c.ipady = (noms.length * 410)/15;
		c.gridheight = (noms.length -1) ;
		aff.add(aff_txt);
		pane.add(aff,c);
		
		frame.pack();
        frame.setVisible(true);
	
    }
    
    public void actionPerformed(ActionEvent e) {
    	aff_txt.setText("Bonjour");    	
    }

    public static void main(String[] args) {
 
        new Interface();
    }
}



