import java.io.Serializable;

/**
 * @author Niloufar
 *
 */


public class EventCards  extends Cards implements Serializable

{

	public String Name ;
   // public List FaceValue;
	public int Id;
	public boolean Status;
	
	public EventCards (String Name,int Id,Boolean Status) {
		this.Name=Name;
		this.Id=Id;
		this.Status=Status;
	}
	public EventCards ()
	{
		
	}
	public String GetEvent()
	{
		return(" Face Value of Event Card");
	}
}
