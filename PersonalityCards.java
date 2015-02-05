import java.io.Serializable;
/**
 * @author Niloufar
 *
 *
 */
public class PersonalityCards implements Cards, Serializable {
	private String Name;
	private int Id;
	boolean Status;
	private CardType Type;
	
	
	public PersonalityCards (String _Name, int _Id, boolean _Status, CardType _Type) {
		Name = _Name;
		Id = _Id;
		Status = _Status;
		Type = _Type;

	}
public PersonalityCards()
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
	
	public String toString() 
	   {
		 return  (this.toString());
	   
	  
	   }
}

