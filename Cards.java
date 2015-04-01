import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * Card interface that will be inherited by all card type
 * 
 * @author Niloufar
 * @version 3.0
 */
public interface Cards
{
	
	 String Name = "";
	 int Id = 0;
     boolean Status = false;
	 CardType Type = null;
	
	 
	public String GetName();
	public int GetID();
	public CardType GetCardType();
}
