
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
 * @author Niloufar
 *
 *
 */

public class CityAreaCards implements Cards, Serializable 
{
	private String Name;
	public String AreaName;
	private int Id;
	boolean Status;
	private CardType Type;
	private boolean isEffectActivated; 
	
	/**
	 * @param _Name name of the card
	 * @param _Id id of the card
	 * @param _Status status of the card
	 * @param _Type type of card
	 */
	public CityAreaCards (String _Name, int _Id, boolean _Status, CardType _Type)
	{
		Name = _Name;
		Id = _Id;
		Status = _Status;
		Type = _Type;
		isEffectActivated = false;

	}
	/**
	 * @return status of isEffectActivated variable
	 */
	public boolean IsEffectActivate()
	{
		return isEffectActivated;
	}
	/**
	 * 
	 */
	public void DesactivateCityArea()
	{
		isEffectActivated = true;
	}
	
	/**
	 * 
	 */
	public void ActivateCityArea()
	{
		isEffectActivated = false;
	}
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
	 * @return action 
	 */
	public Action GetAction()
	{return null;}
	
	
	/**
	 * @param _Title
	 * shows the image related to the card
	 */
	public void ShowImage(String _Title)
	{
		//replace all space in Name
		Name = Name.replace(" ", "");
		String path = "src\\Cards\\CityAreas\\" + Name+".jpg";
		System.out.println("Looking for file in " + path);
        JFrame editorFrame = new JFrame(_Title);

        ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource("Cards/CityAreas/" + Name + ".jpg").getFile());
    	
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
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	   {
		 return  (this.toString());
	  
	   }
}

