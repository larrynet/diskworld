import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class RFormPanel extends JPanel {
	
	public RFormPanel(){
	Dimension dim=getPreferredSize();
	
	
	dim.width=150;
	setPreferredSize(dim);

	setBorder(BorderFactory.createRaisedSoftBevelBorder());
	}
}
