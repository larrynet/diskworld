import java.io.Serializable;
import java.util.Random;

/**
 * @author Niloufar
 *
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
