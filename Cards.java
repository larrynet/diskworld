import java.io.Serializable;
import java.util.Random;

/**
 * @author Niloufar
 *
 */
public interface Cards  {

	public String Name = "";
	public int Id = 0;
	public boolean Status = false;
	public CardType Type = null;
	
	public String GetName();
	public int GetID();
	public CardType GetCardType();

}
