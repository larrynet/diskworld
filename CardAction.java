import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * @author pari
 *
 */
public abstract class CardAction implements Serializable{

	 List<Action> ListCardAction= null;
	 List<String> Symbols = null;
	 
	 public List<Action> GetActionList() {return ListCardAction;}
	 
	 /**
	 * @param i
	 * @return action of the card
	 */
	public Action GetAction(int i)
	 {
		 return this.ListCardAction.get(i);
	 }
	 
	 /**
	 * @param action
	 */
	public void AddAction(Action action)
	 {
		 if(ListCardAction == null) ListCardAction = new ArrayList<Action>();
		 this.ListCardAction.add(action);
	 }
	 
	 /* (non-Javadoc)
  	 * @see java.lang.Object#toString()
  	 */
  	public String toString()
  	{
  		return this.toString();
  	}
  	
	 /**
	 * @param symbol
	 */
	public void AddSymbols(String symbol)
	 {
		 if(Symbols == null) Symbols = new ArrayList<String>();
		this.Symbols.add(symbol); 
	 }
	 
	 /**
	 * @return
	 */
	public List<String> GetSymbol()
	 {
		 return this.Symbols;
	 }
	

}
