import java.util.List;
import java.util.ArrayList;

public abstract class CardAction {

	 List<Action> ListCardAction= null;
	 List<String> Symbols = null;
	 
	 public List<Action> GetActionList() {return ListCardAction;}
	 
	 public Action GetAction(int i)
	 {
		 return this.ListCardAction.get(i);
	 }
	 
	 public void AddAction(Action action)
	 {
		 if(ListCardAction == null) ListCardAction = new ArrayList<Action>();
		 this.ListCardAction.add(action);
	 }
	 
	 public void AddSymbols(String symbol)
	 {
		 if(Symbols == null) Symbols = new ArrayList<String>();
		this.Symbols.add(symbol); 
	 }
	 
	 public List<String> GetSymbol()
	 {
		 return this.Symbols;
	 }
	

}
