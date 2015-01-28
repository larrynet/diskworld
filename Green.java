
public class Green extends Cards

{

	String[] Name={"Green"};
	int Id;
	String [] GreenName;
	
	public Green ()
	{
		String [] GreenName={"Green"};
		for (int i=1;i<=43;i++)
		{
	
		this.Id=401+i;
	     System.out.print(GreenName[0]);
	     System.out.print(" ");
	     System.out.print(i);
		 System.out.print(",");
		}
	}
}