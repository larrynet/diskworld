


public class PersonalityCards extends Cards

{

	public String Name ;
   // public List FaceValue;
    public int Id;
	public boolean Status;

	public PersonalityCards (String Name,int Id,boolean Status)
	{
		
		this.Name=Name;
		this.Id=Id;
		this.Status=Status;
	}
	public PersonalityCards ()
	{
	}

public String GetPersonality()
	{
		return(" Face Value of Personality Card");
	}
}
