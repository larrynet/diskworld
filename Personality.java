/**
 * @author Niloufar
 *
 */


public class Personality extends Cards

{

	public String Name ;
   // public List FaceValue;
    public int Id;
	public boolean Status;

	public Personality (String Name,int Id,boolean Status)
	{
		
		this.Name=Name;
		this.Id=Id;
		this.Status=Status;
	}
	public Personality ()
	{
	}

public string GetPersonality()
	{
		return(" Face Value of Personality Card");
	}
}
