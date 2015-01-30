/**
 * @author Niloufar
 *
 */


public class Event  extends Cards{

	public String Name ;
   // public List FaceValue;
	public int Id;
	public boolean Status;
	
	public Event (String Name,int Id,Boolean Status) {
		this.Name=Name;
		this.Id=Id;
		this.Status=Status;
	}
	public Event ()
	{
		
	}
	public String GetEvent()
	{
		return(" Face Value of Event Card");
	}
}
