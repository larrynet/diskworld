import java.util.List;


public abstract class CardAction {

	 List<Action> CardAction= null;
	 List<String> Symbols = null;
	 
	 public Action GetAction(int i)
	 {
		 return this.CardAction.get(i);
	 }
	 
	 public void AddAction(Action action)
	 {
		 this.CardAction.add(action);
	 }
	 
	 public void AddSymbols(String symbol)
	 {
		this.Symbols.add(symbol); 
	 }
	 
	 public List<String> GetSymbol()
	 {
		 return this.Symbols;
	 }
	

}
