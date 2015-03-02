import java.io.Serializable;
/**
 * @author Niloufar
 *
 *
 */
public class GreenCards implements Cards, Serializable {
	private String Name;
	private int Id;
	boolean Status;
	private CardType Type;
	private Action CardAction;
	
	public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type) {
		Name = _Name;
		Id = _Id;
		Status = _Status;
		Type = _Type;

	}
    public void SetAction(Action a) {CardAction = a;}
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

