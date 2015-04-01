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
 * Event Card Cards stores information for Random Event card
 * 
 * @author Niloufar
 * @version 3.0
 *
 */
public class EventCards implements Cards, Serializable 
{
	private String Name;
	private int Id;
	boolean Status;
	private CardType Type;
	
	
	/**
	 * @param _Name name of card
	 * @param _Id id of the card
	 * @param _Status status of the card
	 * @param _Type type of card
	 */
	public EventCards (String _Name, int _Id, boolean _Status, CardType _Type)
	{
		Name = _Name;
		Id = _Id;
		Status = _Status;
		Type = _Type;

	}

	/* (non-Javadoc)
	 * @see Cards#GetName()
	 */
	public String GetName ()
	{
		return this.Name;
	}
	
	/**
	 * @return action
	 */
	public Action GetAction()
	{return null;}
	
	/* (non-Javadoc)
	 * @see Cards#GetID()
	 */
	public int GetID()
	{
		return this.Id;
	}
	/**
	 * @param _Title
	 * shows the image of the card
	 */
	public void ShowImage(String _Title)
	{
		//replace all space in Name
		Name = Name.replace(" ", "");
		String path = "src\\Cards\\RandomEvents\\" + Name+".jpg";
		System.out.println("Looking for file in " + path);
        JFrame editorFrame = new JFrame(_Title);
        
        ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource("Cards/RandomEvents/" + Name + ".jpg").getFile());

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
	 * @see Cards#GetCardType()
	 */
	public CardType GetCardType()
	 {
		 return this.Type;
	 }
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	   {
		 return  (this.toString());
	   
	  
	   }
}

