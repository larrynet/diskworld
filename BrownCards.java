import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 * @author Niloufar
 *
 *
 */
public class BrownCards extends CardAction implements Cards, Serializable
{

	private String Name;
	private int Id;
	boolean Status;
	private CardType Type;
	
	
	
	public BrownCards(String _Name, int _Id, boolean _Status, CardType _Type)
	{
		Name = _Name;
		Id = _Id;
		Status = _Status;
		Type = _Type;
		

	}

		
	public String GetName() 
	{
		return this.Name;
	}

	public int GetID()
	{
		return this.Id;
	}

	public CardType GetCardType()
	{
		return this.Type;
	}
	public void ShowImage(String _Title)
	{
		//replace all space in Name
		Name = Name.replace(" ", "");
		String path = "src\\Cards\\Brown\\" + Name+".jpg";
		System.out.println("Looking for file in " + path);
        JFrame editorFrame = new JFrame(_Title);

        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        BufferedImage image = null;

        try

        {
          image = ImageIO.read(new File(path));

        }

        catch (Exception e)
        {
          e.printStackTrace();
          System.exit(1);
        }

        BufferedImage bi = (BufferedImage)image;
        Image Scaled = bi.getScaledInstance(300, 350, Image.SCALE_SMOOTH);
       
        JLabel jLabel = new JLabel();
        //jLabel.setIcon(imageIcon);
        
           
            
        jLabel.setIcon(new ImageIcon(Scaled));    
        
        
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);




        editorFrame.pack();

        editorFrame.setLocationRelativeTo(null);

        editorFrame.setVisible(true);

	}
	

	public String toString() 
	{
		return (this.toString());

	}


}