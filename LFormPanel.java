import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class LFormPanel extends JPanel {
	
	public LFormPanel()
	{
		Dimension dim=getPreferredSize();
		//System.out.println(dim);
		
		dim.width=150;
		setPreferredSize(dim);
		//System.out.println(dim);
		setBorder(BorderFactory.createRaisedSoftBevelBorder());
	}

	

}
