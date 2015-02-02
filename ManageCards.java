import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;



/**
 * @author Niloufar
 * 
 * Class ManageCards use to hold the attributes of every single card in the game.
 * These Cards will be initiated at the beginning of the game.
 *
 */

public class ManageCards implements Serializable
{
	
   //  ManageCards Attributes 
	public Cards [] c =new Cards[5];
	public PersonalityCards[] p=new PersonalityCards[7];
	public EventCards []e =new EventCards[12];
	public CityAreaCards [] ct=new CityAreaCards[12];
	public GreenCards[] g=new GreenCards[48];
	public BrownCards[] b=new BrownCards[53];
	public CardType Cardtype;
	
	
	//ManageCards Constructor
	
	/**
	 * ManageCards constructor will initiate all cards of the game.
	 */
	public ManageCards()
	{
		String []PersonalityName={"Lord Vetinari","Lord Selachii ","Lord Rust ","Lord de Word ","Dragon King of Arms ","Chrysoprase ","Commander Vimes"};
	     String []EventName={"The Dragon","Flood","Fire","Fog","Riots","Explorion","Mysterious Murders","Demons From The Dungeon Dimension","Subsidence","Bloody Stupid Johnson","Trolls","Earthquake"};
		 String []CityAreaName={"Dolly Sisters","Unreal Estate","Dragon's Landing","Small Gods","The Scours","The Hippo","The Shades","Dimwell","Longwall","Isle of Gods","Seven Sleepers","Nap Hill"};
		 
		 int [] PersonalityId={101,102,103,104,105,106,107};
	     int [] EventId={201,202,203,204,205,206,207,208,209,210,211,212};
		 int []CityAreaId={301,302,303,304,305,306,307,308,309,310,311,312};
		
		 Boolean Status =true;
		 /*
			 * Make an array of c with 5 card size to point to each five of 
			 * cards type
			 * 
			 * When i is zero an arraye of seven personality type card will be initiated an so the rest.
			 * 
			 */
		 
		for (int i =0;i<=4; i++)
		{	
	     this.c[i] = new  Cards();
	     if (i==0)              
	     {   
	    	 for (int j=0; j<=this.p.length-1 ;j++)
	    		 
	    	 {
	    	 this.p[j]=new PersonalityCards(PersonalityName[j],PersonalityId[j],Status,CardType.PersonalityCards );
	    	 System.out.print(PersonalityName[j]);
	    	 System.out.print(",");
	    	 }
	    	
	     }
	     else if (i==1)
	     {
	    	 for (int j=0;j<=this.e.length-1 ;j++)
	    	 {
	    		 this.e[j]=new EventCards(EventName[j],EventId[j],Status,CardType.EventCards);
	    		 System.out.print(EventName[j]);
	    		 System.out.print(",");
	    	 }
	    	
	     }
	     
	     else if (i==2)
	     {
	    	 for (int j=0;j<=this.ct.length-1;j++)
	    	 {
	    		 this.ct[j]=new CityAreaCards(CityAreaName[j],CityAreaId[j],Status,CardType.CityAreaCards);
	    		 System.out.print(CityAreaName[j]);
	    		 System.out.print(",");
	    	 }
	    	 
	     }
	     else if (i==3)
	     {
	    	 for (int j=0;j<=this.g.length-1;j++)
	    	 {
	    		 this.g[j]=new GreenCards(("Green"+" "+(j+1)),401+j,Status,CardType.GreenCards);
	    		 System.out.print(this.g[j].Name);
	    		 System.out.print(",");
	    	 }
	    	 
	    	 
	    	  
	     }
	     else if(i==4)
	     {
	    	 for (int j=0;j<=this.b.length-1;j++)
	    	 {
	    		 this.b[j]=new BrownCards(("Brown"+" "+(j+1)),(501+j),Status,CardType.BrownCards);
	    		 System.out.print(this.b[j].Name);
	    		 System.out.print(",");
	    	 }
	    	 
	      }
	     System.out.println("");
	     }
	
	}
	
	//ManageCards Methods

    public EventCards GetEventCard()
    { 
    	EventCards AvailableE=new EventCards();
       
    	for (int i=0;i<e.length-1  ;i++ )
    		if (this.e[i].Status==true)
    		{
    			this.e[i].Status=false;
    			AvailableE =this.e[i];
    			break;
    		}
    		else
    			AvailableE.Name=null;
    	
    	return AvailableE;
    		
    }
	
	/*
	 * Initiate the Cards and copy Cards value to the CardManager for further changes
	 * 
	 * 
	 */
	public  void CardInitiate()
	{
   
	 String []PersonalityName={"PersonalityRole 1","PersonalityRole 2","PersonalityRole 3","PersonalityRole 4","PersonalityRole 5","PersonalityRole 6","PersonalityRole 7"};
     String []EventName={"Event 1","Event 2","Event 3","Event 4","Event 5","Event 6","Event 7","Event 8","Event 9","Event 10","Event 11","Event 12"};
	 String []CityAreaName={"CityArea 1","CityArea 2","CityArea 3","CityArea 4","CityArea 5","CityArea 6","CityArea","CityArea 8","CityArea 9","CityArea 10","CityArea 11","CityArea 12"};
	 
	 int [] PersonalityId={101,102,103,104,105,106,107};
     int [] EventId={201,202,203,204,205,206,207,208,209,210,211,212};
	 int []CityAreaId={301,302,303,304,305,306,307,308,309,310,311,312};
	
	 Boolean Status =true;
	 /*
		 * Make an array of c with 5 card size to point to each five of 
		 * cards type
		 * 
		 * When i is zero an arraye of seven personality type card will be initiated an so the rest.
		 * 
		 */
	 
	for (int i =0;i<=4; i++)
	{	
     this.c[i] = new  Cards();
     if (i==0)              
     {   
    	 for (int j=0; j<=this.p.length-1 ;j++)
    		 
    	 {
    	 this.p[j]=new PersonalityCards(PersonalityName[j],PersonalityId[j],Status,CardType.PersonalityCards);
    	 System.out.print(PersonalityName[j]);
    	 System.out.print(",");
    	 }
    	
     }
     else if (i==1)
     {
    	 for (int j=0;j<=this.e.length-1 ;j++)
    	 {
    		 this.e[j]=new EventCards(EventName[j],EventId[j],Status,CardType.EventCards);
    		 System.out.print(EventName[j]);
    		 System.out.print(",");
    	 }
    	
     }
     
     else if (i==2)
     {
    	 for (int j=0;j<=this.ct.length-1;j++)
    	 {
    		 this.ct[j]=new CityAreaCards(CityAreaName[j],CityAreaId[j],Status,CardType.CityAreaCards);
    		 System.out.print(CityAreaName[j]);
    		 System.out.print(",");
    	 }
    	 
     }
     else if (i==3)
     {
    	 for (int j=0;j<=this.g.length-1;j++)
    	 {
    		 this.g[j]=new GreenCards(("Green"+" "+(j+1)),401+j,Status,CardType.GreenCards);
    		 System.out.print(this.g[j].Name);
    		 System.out.print(",");
    	 }
    	 
    	 
    	  
     }
     else if(i==4)
     {
    	 for (int j=0;j<=this.b.length-1;j++)
    	 {
    		 this.b[j]=new BrownCards(("Brown"+" "+(j+1)),(501+j),Status,CardType.BrownCards);
    		 System.out.print(this.b[j].Name);
    		 System.out.print(",");
    	 }
    	 
      }
     System.out.println("");
     }
	
	}
	
	/**
	 * GetState is used to show the current state of Cards
	 * Used to test the value of cards in built 1 of project.
	 */
	public void GetState() //used for testing
	{ 
		System.out.print("Cards,");
		for (int i=0;i<p.length-1  ;i++ )
			System.out.print(p[i].Name+ "," + p[i].Id + "," + p[i].Status+",");
		System.out.print(",Last");
		System.out.println("");
		
		
		System.out.print("Cards,");
		for (int i=0;i<e.length-1  ;i++ )
			System.out.print( e[i].Name + "," + e[i].Id +","+ e[i].Status+",");
		System.out.print(",Last"); 
		System.out.println("");
		
		
		System.out.print("Cards,");
		for (int i=0;i<ct.length-1  ;i++ )
			System.out.print(ct[i].Name +","+ ct[i].Id +","+ ct[i].Status+",");
		System.out.print(",Last");  
		
		System.out.println("");

		System.out.print("Cards,");
		for (int i=0;i<g.length-1  ;i++ )
			System.out.print(g[i].Name +","+ g[i].Id +","+ g[i].Status+",");
		System.out.print(",Last");  
		
		System.out.println("");
		
		System.out.print("Cards,");
		for (int i=0;i<b.length-1  ;i++ )
			System.out.print( b[i].Name + "," + b[i].Id +","+ b[i].Status+",");
		System.out.print(",Last"); 
		System.out.println("");
		
		
	}
//	 System.out.println(this.e[1].Id);
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	   public String toString() 
	{
		 return  (this.toString());
	   
 }
	
	
/**
 * 
 * Method GetCard will be call from GameEngin to Draw cards to player during the game.
 * 
 * @param Type  Type of cards which would be personality,event...
 * @return return type is an object of type cards
 */
public Cards GetCard( CardType Type)
	{
		
	Cards available =new Cards();
	
		if(CardType.PersonalityCards==Type  )    
		{  this.ShuffleCards(this.p);
			for (int i=0;i<=this.p.length-1;i++)
			{
		
			 if (this.p[i].Status==true  )
			 { 
			    p[i].Status=false;
			    available= this.p[i];
				 break;
			 } 
			}
		}
		else if(CardType.EventCards==Type  )    
		{   this.ShuffleCards(this.e);
			for (int i=0;i<=this.e.length-1;i++)
			{		 
			 if (this.e[i].Status==true  )
			  {
				 e[i].Status=false;
				 available= this.e[i];
				 break;
	          }
			}
		}
			
		else if(CardType.CityAreaCards  ==Type  )    
		{   this.ShuffleCards(ct);
			for (int i=0;i<=this.ct.length-1;i++)
				{
				 if (this.ct[i].Status==true  )
				  {
					 ct[i].Status=false;
					 available=this.ct[i];
					 break;
				 
				  }
				}
		}
					
		else if(CardType.GreenCards  ==Type  )    
		{   this.ShuffleCards(g);
			for (int i=0;i<=this.g.length-1;i++)
				{
				 if (this.g[i].Status==true  )
				 { 
					 g[i].Status=false;
					 available=this.g[i];
					 break;
				 }	
				}
		}
		else if(CardType.BrownCards  ==Type  )    
		{this.ShuffleCards(b);
			for (int i=0;i<=this.b.length-1;i++)
				{
					
				if (this.b[i].Status==true  )
				{
				 b[i].Status=false;
				 available= this.b[i];
				 break;
				 }
			}
		}
		else available=null;
return available;
		
	}

/**
 * Shuffle method was written shuffle the cards before giving them to players.
 * 
 * @param c This parameter is array of cards
 * @return  An array of cards will be return
 */
public  Cards[] ShuffleCards(Cards[] c)
 {
	Random rgen = new Random();  // Random number generator			

	for (int i=0; i<c.length; i++)
	{
	    int randomPosition = rgen.nextInt(c.length);
	    Cards temp = c[i];
	    c[i] = c[randomPosition];
	    c[randomPosition] = temp;
	}

	return c;
}



}

		
		
	



	

