import java.awt.BorderLayout;
import java.awt.Dimension;



import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;


public class DownFormPanel extends JPanel {
	
	private JButton playerState;
	private JButton gameState;
	private JTextArea showState;
	
	public DownFormPanel(){
		Dimension dim=getPreferredSize();
		dim.height=100;
		setPreferredSize(dim);
		//System.out.println(dim);
		setBorder(BorderFactory.createRaisedSoftBevelBorder());
		
		//Initializing
		playerState=new JButton("Player State");
		playerState.setSize(20, 20);
		gameState=new JButton("Game State");
		gameState.setSize(20, 20);
		showState=new JTextArea("place to show state");
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc=new GridBagConstraints();
		
		//TextArea
		
		gc.gridx=0;
		gc.gridy=0;
		gc.weightx=1;
		gc.weighty=1;
		gc.fill=GridBagConstraints.HORIZONTAL;
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		add(showState,gc);
		
		//first line button
		gc.gridx=1;
		gc.gridy=0;
		gc.weightx=1;
		gc.weighty=1;		
		gc.fill=GridBagConstraints.NONE;
		gc.anchor=GridBagConstraints.FIRST_LINE_END;
		add(playerState,gc);
		
		//Second Line button
		
		gc.gridx=1;
		gc.gridy=1;
		gc.weightx=1;
		gc.weighty=1;
		gc.fill=GridBagConstraints.NONE;
		gc.anchor=GridBagConstraints.LAST_LINE_END;
		add(gameState,gc);
		
		
	}

}
