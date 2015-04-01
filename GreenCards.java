import java.io.Serializable;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
/**
 * GreenCards is used to define card cards. It implements the Cards class with the basic attribute. 
 * It also extends the CardAction to get function to control the action of a class
 * 
 * @author Niloufar
 * @version 3.0
 */
public class GreenCards extends CardAction implements Cards, Serializable

{
	private String Name;
	private int Id;
	boolean Status;
	private CardType Type;
	
	//Constructor
	/**
	 * @param _Name nmae of the card
	 * @param _Id id of the card
	 * @param _Status card status
	 * @param _Type type of card
	 */
	public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
 {
		Name = _Name;
		Id = _Id;
		Status = _Status;
		Type = _Type;

	}
	
	
	/**
	 * empty constructor
	 */
	public GreenCards ()
	{
		
	}
	
	

//Getter
/* (non-Javadoc)
 * @see Cards#GetName()
 */
public String GetName ()
	{
		return this.Name;
	}
	
/* (non-Javadoc)
 * @see Cards#GetID()
 */
public int GetID()
{
	return this.Id;
}

/* (non-Javadoc)
 * @see Cards#GetCardType()
 */
public CardType GetCardType()
 {
	 return this.Type;
 }

/**
 * @param _Title
 * shows the image of the related card
 */
public void ShowImage(String _Title)
{
	
	//replace all space in Name
	Name = Name.replace(" ", "");
	String path = "/Users/gayhazan/Documents/workspace/LocDiskworld/src/Cards/GreenCards/" + Name+".jpg";
	System.out.println("Looking for file in " + path);
    JFrame editorFrame = new JFrame(_Title);
    
    ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource("Cards/GreenCards/" + Name + ".jpg").getFile());

	editorFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

    BufferedImage image = null;

    try

    {
      image = ImageIO.read(file);

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

/* (non-Javadoc)
 * @see CardAction#toString()
 */
public String toString() 
   {
	 return  (this.toString());
   
  
   }



}

