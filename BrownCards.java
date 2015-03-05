import java.io.Serializable;

/**
 * @author Niloufar
 *
 *
 */
public class BrownCards implements Cards, Serializable
{

	private String Name;
	private int Id;
	boolean Status;
	private CardType Type;
	private Action CardAction;

	public BrownCards(String _Name, int _Id, boolean _Status, CardType _Type)
	{
		Name = _Name;
		Id = _Id;
		Status = _Status;
		Type = _Type;

	}
	//Setter
		public void SetAction(Action _action)
		{
			CardAction = _action;
		}
		
	public String GetName() 
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

	public Action GetAction()
	{
		return this.CardAction;
		
	}
	public String toString() 
	{
		return (this.toString());

	}

}