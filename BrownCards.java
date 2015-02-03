import java.io.Serializable;


/**
 * @author Niloufar
 *
 *
 */
public class BrownCards implements Cards, Serializable
{

public	String Name;
public	int Id;
public	boolean Status;
public CardType Type;
	
	
public BrownCards(String Name,int Id,boolean Status,CardType Type)
{
		this.Name=Name;
		this.Id=Id;
		this.Status=Status;
		this.Type=Type;
}

public BrownCards()
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