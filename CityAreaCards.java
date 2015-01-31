import java.io.Serializable;


public class CityAreaCards extends Cards implements Serializable
{

	public String Name ;
    //public List FaceValue;
	public int Id;
    public boolean Status;
	
	public CityAreaCards(String Name, int Id,boolean Status) 
	{
		this.Name=Name;
		this.Id=Id;
		this.Status=Status;
	}
	public CityAreaCards()
	{
		
	}
	public String GetCityArea()
	{ 
		return(" Face Value of City Card");
	}
	 public String toString() 
		   {
			 return  (this.toString());
		   
		  
		   }
}
