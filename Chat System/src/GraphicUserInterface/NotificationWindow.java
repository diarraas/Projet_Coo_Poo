package GraphicUserInterface;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class NotificationWindow  {
	
	private JLabel notifArea;
	private JFrame frame;
	public NotificationWindow(String message) {
		
		frame = new JFrame("Notification");
		frame.setPreferredSize(new Dimension(300, 200));
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    
	    Container pane = frame.getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		//Zone d'affichage d'erreur
		notifArea = new JLabel(message);
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,0,10,0);
		pane.add(notifArea,c);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}


	


