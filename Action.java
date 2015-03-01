import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Action {
	public int NumberOfAction=0;
	public List<String> Verb;
	public List<String> Object;
	public List<String> Symbol;
	public String Relation;
	public boolean KeepTillEnd;
	
	public Action(int n, List<String> v, List<String> o, List<String> s , String relation, boolean keep ) 
	{
		NumberOfAction = n;
		Verb = v;
		Object=o;
		Symbol = s;
		Relation = relation;
		KeepTillEnd = keep;
	}
	
	public static Action ParseString(String action)
	{
		int nAction = 1;
		List<String> verbs = new ArrayList<String>();
		List<String> objects = new ArrayList<String>();
		List<String> conditions = new ArrayList<String>();
		List<String> symbols = new ArrayList<String>();
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
			System.out.println(item);
			//Separate split item by separator
			String [] separated = item.split(";");
			
			verbs.add(ReturnDesc(separated[0]));
			objects.add(ReturnDesc(separated[1]));
			conditions.add(ReturnDesc(separated[2]));
			
			if (description.length == 1 || iterDescription > 1)
			{
				objects.add(ReturnDesc(separated[3]));
			}
			
		}
	
		return new Action(nAction, verbs,objects,symbols, connector, KeepTillEnd);
	}
	
	public void PrintActionVerbs()
	{
		StringBuilder strBuilder = new StringBuilder();
		
		
		for(String thisVerb : this.Verb)
		{
			strBuilder.append(thisVerb);
			strBuilder.append(", ");
		}
		
		
		
		System.out.println(strBuilder.toString());
	}
	
	public void PrintActionObject()
	{
		StringBuilder strBuilder = new StringBuilder();
		
		
		for(String thisObject : this.Object)
		{
			strBuilder.append(thisObject);
			strBuilder.append(", ");
		}
		
		
		
		System.out.println(strBuilder.toString());
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
