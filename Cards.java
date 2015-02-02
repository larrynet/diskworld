import java.io.Serializable;
import java.util.Random;

/**
 * @author Niloufar
 *
 */
public class Cards implements Serializable
{   
	

	public String Name ;
   // public List FaceValue;
    public int Id;
    public boolean Status;
    public CardType Type ;
    public Cards (String Name,int Id,boolean Status,CardType Type)
	{
		
		this.Name=Name;
		this.Id=Id;
		this.Status=Status;
		this.Type=Type;
	}
    public Cards()
    {
    	
    }
    
    public String toString()
    {
    	return this.toString();
    }
  
    
  
}
		
	