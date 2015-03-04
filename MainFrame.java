import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * @author pari alt-shift-j
 *
 */
public class MainFrame extends JFrame{
	
	//private variables to the components on the MainFrame
	//private JMenu menu;
	
	private RFormPanel rightFormPanel;
	private LFormPanel leftFormPanel;
	private UpFormPanel upFormPanel;
	private DownFormPanel downFormPanel;
	
	
	/**
	 * constructor
	 */
	public MainFrame()
	{	super("DISCWORLD ANKH_MORPORK");
	
	 //layout of the Frame	
	 setLayout(new BorderLayout());
		
	//initializing the Variables
	 rightFormPanel=new RFormPanel();
	 leftFormPanel=new LFormPanel();
	 upFormPanel=new UpFormPanel();
	 downFormPanel=new DownFormPanel();
		
		setJMenuBar(createMenuBar());
		
		
		//Adding components to the panels
		add(upFormPanel,BorderLayout.NORTH);
		add(rightFormPanel,BorderLayout.EAST);
		add(leftFormPanel,BorderLayout.WEST);
		add(downFormPanel,BorderLayout.SOUTH);
		
		
		setSize(1200,700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
		/**
		 * creates the MenuBar
		 * @return JMenuBar
		 */
		private JMenuBar createMenuBar()
	{
		JMenuBar menuBar=new JMenuBar();
	
		// --------------File Menu---------------
		JMenu fileMenu=new JMenu("File");
		JMenuItem newItem=new JMenuItem("New Game");
		JMenuItem loadItem=new JMenuItem("Load Game");
		JMenuItem saveItem=new JMenuItem("Save");
		JMenuItem saveAsItem=new JMenuItem("Save As");
		JMenuItem exitWithoutSavingItem=new JMenuItem("Exit Without Saving");
		JMenuItem saveExitItem=new JMenuItem("Save & Exit");
		JMenuItem exitItem=new JMenuItem("Exit");
		
		fileMenu.add(newItem);
		fileMenu.add(loadItem);
		fileMenu.addSeparator();
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.addSeparator();
		fileMenu.add(exitWithoutSavingItem);
		fileMenu.add(saveExitItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
				
		// --------------Edit Menu---------------
		JMenu editMenu=new JMenu("Edit");
		JMenuItem editGameStateItem=new JMenuItem("Edit Game State");
		
		editMenu.add(editGameStateItem);
		
		// --------------Help Menu---------------
		JMenu helpMenu=new JMenu("Help");
		JMenuItem aidCardsItem=new JMenuItem("Aid cards");
		JMenuItem aboutItem=new JMenuItem("About");
		
		helpMenu.add(aidCardsItem);
		helpMenu.add(aboutItem);
		
		//--------------Adding Menu Bars---------
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		
		
		return menuBar;
		
	}
	

}
