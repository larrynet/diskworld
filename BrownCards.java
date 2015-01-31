import java.io.Serializable;


public class BrownCards extends Cards implements Serializable
{

	String Name;
	int Id;
	boolean Status;
	
	
		public BrownCards(String Name,int Id,boolean Status)
		{
				this.Name=Name;
				this.Id=Id;
				this.Status=Status;
		}
		public BrownCards()
		{
			
		}
	
}