import java.io.Serializable;

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
    public Cards (String Name,int Id,boolean Status)
	{
		
		this.Name=Name;
		this.Id=Id;
		this.Status=Status;
	}
    public Cards()
    {
    	
    }
    
    public String toString()
    {
    	return this.toString();
    }
}
		
	