import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Action {
	public int NumberOfAction=0;
	public List<String> Verb;
	public List<String> Object;
	public List<String> Condition;
	public List<String> ActionNumber;
	public String Relation;
	public boolean KeepTillEnd;
	
	public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
	{
		NumberOfAction = n;
		Verb = v;
		Object=o;
		Condition = c;
		ActionNumber = a;
		Relation = relation;
		KeepTillEnd = keep;
	}
	
	public Action() {
		// TODO Auto-generated constructor stub
	}

	public static Action ParseString(String action)
	{
		int nAction = 1;
		List<String> verbs = new ArrayList<String>();
		List<String> objects = new ArrayList<String>();
		List<String> conditions = new ArrayList<String>();
		List<String> actionNumber = new ArrayList<String>();
		boolean KeepTillEnd = false;
		String [] description;
		String connector = "";
		
		if (action.contains("endgame"))
		{
			KeepTillEnd = true;
		}
		
		//Verify if there are 2 version in action string
		if (action.contains("OR"))
		{
			description = action.split("OR");
			connector = "OR";
			nAction++;
		}
		else if (action.contains("AND"))
		{
			description = action.split("AND");
			connector = "AND";
		}
		else
		{	
			description = new String[1];
			description[0] = action;
		}
				
		int iterDescription = 0;
		
		for (String item : description)
		{
			iterDescription++;
			
			//Separate split item by separator
			String [] separated = item.split(";");
			
			verbs.add(ReturnDesc(separated[0]));
			objects.add(ReturnDesc(separated[1]));
			conditions.add(ReturnDesc(separated[2]));
			actionNumber.add(ReturnDesc(separated[3]));
			
		}
	
		return new Action(nAction, verbs,objects,conditions, actionNumber, connector, KeepTillEnd);
	}
	
	public String GetVerbs(int index)
	{
		if (!this.Verb.isEmpty() && this.Verb.size() >= index)
		{
			return this.Verb.get(index);
		}
		
		return "";
	}
	
	public String GetObject(int index)
	{
		if (!this.Object.isEmpty() && this.Object.size() >= index)
		{
			return this.Object.get(index);
		}
		
		return "";
	}
	
	public String GetCondition(int index){
		
		if (!this.Condition.isEmpty() && this.Condition.size() >= index)
		{
			return this.Condition.get(index);
		}
		
		return "";
	}
	
	public String GetActionNumber(int index){
		
		if (!this.ActionNumber.isEmpty() && this.ActionNumber.size() >= index)
		{
			return this.ActionNumber.get(index);
		}
		
		return "";
		
	}
	
	
	
	public void PrintAll() {
		
		int max = this.Verb.size();
		
		for (int i = 0; i < max; i++)
		{
			System.out.println("Verb=" + this.GetVerbs(i) + ";object=" + this.GetObject(i) + ";condition=" + this.GetCondition(i) + ";actionnumber=" + this.GetActionNumber(i) );
		}
	}
	
	private static String ReturnDesc(String desc)
	{
	
		//Is their a verb
		if (desc.contains("="))
		{
			return desc.split("=")[1];
		}
		
		
		return "";
			
		
	}

}