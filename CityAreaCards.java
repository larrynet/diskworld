import java.io.Serializable;


public class CityAreaCards implements Cards, Serializable
{

	public String Name ;
    //public List FaceValue;
	public int Id;
    public boolean Status;
    public CardType Type ;
    
	public CityAreaCards(String Name,int Id,boolean Status,CardType Type)
	{
		this.Name=Name;
		this.Id=Id;
		this.Status=Status;
		this.Type=Type;
	}
	public CityAreaCards()
	{
		
	}
	
	 public String toString() 
		   {
			 return  (this.toString());
		   
		  
		   }
	 
	 public String GetName ()
		{
			return this.Name;
		}
	 
	 public int GetID()
		{
			return this.Id;
		}
	 
	 public CardType GetCardType()
	 {
		 return this.Type;
	 }
}
