import java.io.Serializable;

/**
 * @author Niloufar
 *
 */
public class EventCards  implements Cards, Serializable

{

	public String Name ;
	public int Id;
	public boolean Status;
	 public CardType Type ;
	 
public EventCards(String Name,int Id,boolean Status,CardType Type)
		{
			
			this.Name=Name;
			this.Id=Id;
			this.Status=Status;
			this.Type=Type;
		}
	public EventCards ()
	{
		
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
