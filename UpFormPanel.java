import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class UpFormPanel extends JPanel {
	
	
	public UpFormPanel()
	{
		Dimension dim=getPreferredSize();
			
		dim.height=50;
		setPreferredSize(dim);
		//System.out.println(dim);
		setBorder(BorderFactory.createRaisedSoftBevelBorder());
		
	}

}
