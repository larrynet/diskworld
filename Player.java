import java.util.List;
//dummy class
public class Player {
	String Personality;
	int Money;
	List<String> ListMinion;
	List<String> ListBuilding;
	String Demon;
	
	public Player () {}
	public Player(String p, int i, List<String> lm, List<String> lb, String d){
		Personality = p;
		Money = i;
		ListMinion = lm;
		ListBuilding = lb;
		Demon = d;
	}
	
}
