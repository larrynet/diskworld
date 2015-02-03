import java.io.Serializable;


public class GreenCards implements Cards, Serializable

{

	String Name;
	int Id;
	Boolean Status;
	 public CardType Type ;
	    public GreenCards (String Name,int Id,boolean Status,CardType Type)
		{
			
			this.Name=Name;
			this.Id=Id;
			this.Status=Status;
			this.Type=Type;
		}
	
	public GreenCards ()
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
