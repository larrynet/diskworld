import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Action implements Serializable {
	private int NumberOfAction=0;
	private List<String> Verb;
	private List<String> Object;
	private List<String> Condition;
	private List<String> ActionNumber;
	private String Relation;
	private boolean KeepTillEnd;

	/**
	 * Constructor that will initialize an action class
	 * 
	 * @param n
	 * @param v
	 * @param o
	 * @param c
	 * @param relation
	 * @param keep
	 */
	public Action(int n, List<String> v, List<String> o, List<String> c,String relation, boolean keep)  
	{
		NumberOfAction = n;
		Verb = v;
		Object=o;
		Condition = c;
		//ActionNumber = a;
		Relation = relation;
		KeepTillEnd = keep;
	}

	/**
	 * @return the list of current verb
	 */
	public List<String> GetVerbList()
	{
		return Verb;
	}

	/**
	 * @return the list of current object
	 */
	public List<String> GetObjectList()
	{
		return Object;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return this.toString();
	}

	/**
	 * Function will convert a string read from file into a Action class
	 * @param action in text
	 * @return
	 */
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

		for (String item : description)
		{
			//Separate split item by separator
			String [] separated = item.split(";");

			verbs.add(ReturnDesc(separated[0]));
			objects.add(ReturnDesc(separated[1]));
			if(separated.length>2)
				conditions.add(ReturnDesc(separated[2]));
			else
				conditions.add("");
			if(separated.length>3)
				actionNumber.add(ReturnDesc(separated[3]));
			else
				actionNumber.add("");

		}

		return new Action(nAction, verbs,objects,conditions, connector, KeepTillEnd);
	}

	/**
	 * Return the verb in the given index
	 * 
	 * @param index
	 * @return
	 */
	public String GetVerbs(int index)
	{
		if (!this.Verb.isEmpty() && this.Verb.size() >= index)
		{
			return this.Verb.get(index);
		}

		return "";
	}

	/**
	 * Return the object in the given index
	 * 
	 * @param index
	 * @return
	 */
	public String GetObject(int index)
	{
		if (!this.Object.isEmpty() && this.Object.size() >= index)
		{
			return this.Object.get(index);
		}

		return "";
	}

	/**
	 * Return the condition in the given index
	 * 
	 * @param index
	 * @return
	 */
	public String GetCondition(int index){

		if (!this.Condition.isEmpty() && this.Condition.size() >= index)
		{
			return this.Condition.get(index);
		}

		return "";
	}

	//print all the action stored
	public void PrintAll() {

		int max = this.Verb.size();

		for (int i = 0; i < max; i++)
		{
			System.out.println("Verb=" + this.GetVerbs(i) + ";object=" + this.GetObject(i) + ";condition=" + this.GetCondition(i));
		}
	}

	/**
	 * Return the current description
	 * @param desc
	 * @return
	 */
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