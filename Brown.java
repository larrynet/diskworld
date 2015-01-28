
public class Brown extends Cards
{

	String[] Name={"Brown"};
	int Id;
	String [] BrownName;
	
	public Brown ()
	{
		String [] BrownName={"Brown"};
		for (int i=1;i<=53;i++)
		{
	
		this.Id=401+i;
	     System.out.print(BrownName[0]);
	     System.out.print(" ");
	     System.out.print(i);
		 System.out.print(",");
		}
	}
}