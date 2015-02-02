


public class PersonalityCards extends Cards

{

	public String Name ;
   // public List FaceValue;
    public int Id;
	public boolean Status;
	public CardType Type;

	public PersonalityCards (String Name,int Id,boolean Status,CardType Type)
	{
		
		this.Name=Name;
		this.Id=Id;
		this.Status=Status;
		this.Type=Type;
	}
	public PersonalityCards ()
	{
	}

public String GetPersonality()
	{
		return(" Face Value of Personality Card");
	}
}
