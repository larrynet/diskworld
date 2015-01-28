


public class Event  extends Cards{

	public String Name ;
   // public List FaceValue;
	public int Id;
	
	public Event (String Name,int Id) {
		this.Name=Name;
		this.Id=Id;
	}
	public String GetEvent()
	{
		return(" Face Value of Event Card");
	}
}
