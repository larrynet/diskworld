import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;


/**
 * 
 * 
 *  ManageCards class use to hold the attributes of every single card in the game.
 *  These Cards will be initiated at the beginning of the game.
 *  this class only interact with Game Engine.
 *  
 *  @author Niloufar
 */

public class ManageCards implements Serializable
{
	
   //  ManageCards Attributes 
	private int TotalBrownCard = 53;
	private int TotalPersonalityCard = 7;
	private PersonalityCards[] Personality_Card=new PersonalityCards[7];
	private EventCards []Event_Card =new EventCards[12];
	public CityAreaCards []CityArea_Cards =new CityAreaCards[12];
	private GreenCards[] Green_Cards=new GreenCards[48];
	private BrownCards[] Brown_Cards=new BrownCards[53];
	
	
	
	//ManageCards Constructor

	/**
	 * @param TotalPlayer indicates the number of player playing the game. 
	 */
	public ManageCards(int TotalPlayer)
	{
		
		  CreateGreenCards();
		  //CreateBrownCards();
		  //CreatePersoCards();
		
		
	}
	
	/**
	 * GetState is used to show the current state of Cards
	 * Used to test the value of cards in built 1 of project.
	 */
	public void GetState() //used for testing
	{ 
		System.out.println("State of Current Cards ");
		System.out.println("===========================================================================");
		System.out.print("Personality Cards : ");

		for (int i = 0; i < TotalPersonalityCard; i++) 
		{
			System.out.print(this.Personality_Card[i].GetName());
			if (this.Personality_Card[i].Status) 
				System.out.print("  Available  ,");
			else
				System.out.print("  Unavailable  ,");
		}
		
		System.out.println("");
		
		
		System.out.print("Event Cards : ");
		for (int i=0;i< this.Event_Card.length-1  ;i++ )
		{
			System.out.print( this.Event_Card[i].GetName());
		
		 if (this.Event_Card[i].Status)
		   {
			System.out.print("  Available  ,");
		   
		   }
		else
			System.out.print("  Unavailable , ");
		
		}
		System.out.println("");
		System.out.print("City Area Cards :");
		for (int i=0;i<this.CityArea_Cards.length-1  ;i++ )
		{
			System.out.print(this.CityArea_Cards[i].GetName());
		 if (this.CityArea_Cards[i].Status)
		   {
			System.out.print("  Available  ,");
		   
		   }
		else
			System.out.print("  Unavailable , ");
		
		}
		System.out.println("");
		System.out.print("Board Cards Green : ");
		for (int i=0;i<this.Green_Cards  .length-1  ;i++ )
		{
			System.out.print(this.Green_Cards[i].GetName() );
		 if (this.Green_Cards[i].Status)
		   {
			System.out.print("  Available , ");
		    
		   }
		else
			System.out.print("  Unavailable , ");
		
		}
		System.out.println(" ");
		
		System.out.print("Boards Card Brown : ");
				
		for (int i=0;i<TotalBrownCard  ;i++ )
		{
			  System.out.print(this.Brown_Cards[i].GetName());
		 if (this.Brown_Cards[i].Status)
		   {
			System.out.print("  Available  ,");
		 
		   }
		else
			System.out.print("  Unavailable  ,");
		}
		
		System.out.println();
					
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
		
	Cards available =null;
	
		if(CardType.PersonalityCards==Type  ) 
			
		{  
			this.ShuffleCards((Cards [])  this.Personality_Card);
			for (int i=0;i<TotalPersonalityCard;i++)
			{
		
			 if (this.Personality_Card[i].Status==true  )
			 { 
			    this.Personality_Card[i].Status=false;
			    available= (Cards) this.Personality_Card[i];
				 break;
			 } 
			}
		}
		else if(CardType.EventCards==Type  )    
		{   this.ShuffleCards( (Cards [])this.Event_Card);
			for (int i=0;i<=this.Event_Card.length-1;i++)
			{		 
			 if (this.Event_Card[i].Status==true  )
			  {
				 this.Event_Card[i].Status=false;
				 available= (Cards) this.Event_Card[i];
				 break;
	          }
			}
		}
			
		else if(CardType.CityAreaCards  ==Type  )    
		{   this.ShuffleCards((Cards [])this.CityArea_Cards);
			for (int i=0;i<=this.CityArea_Cards.length-1;i++)
				{
				 if (this.CityArea_Cards[i].Status==true  )
				  {
					this.CityArea_Cards[i].Status=false;
					 available=(Cards) this.CityArea_Cards[i];
					 break;
				 
				  }
				}
		}
					
		else if(CardType.GreenCards  ==Type  )    
		{   this.ShuffleCards((Cards [])this.Green_Cards);
			for (int i=0;i<=this.Green_Cards.length-1;i++)
				{
				 if (this.Green_Cards[i].Status==true  )
				 { 
					 this.Green_Cards[i].Status=false;
					 available=this.Green_Cards[i];
					 break;
				 }	
				}
		}
		else if(CardType.BrownCards  ==Type  )    
		{this.ShuffleCards((Cards [])this.Brown_Cards);
			for (int i=0;i<TotalBrownCard;i++)
				{
					
				if (this.Brown_Cards[i].Status==true  )
				{
				 this.Brown_Cards[i].Status=false;
				 available= (Cards) this.Brown_Cards[i];
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
 * @param _Cards This parameter is array of cards
 * @return  An array of cards will be return
 */
public  Cards[] ShuffleCards(Cards[] _Cards)
 {
	Random rgen = new Random();  // Random number generator
	int TotalCard = _Cards.length;
	if(_Cards[0].GetCardType() == CardType.BrownCards)
		TotalCard = TotalBrownCard;
	else if(_Cards[0].GetCardType() == CardType.PersonalityCards)
		TotalCard = TotalPersonalityCard;
	
	for (int i=0; i<TotalCard; i++)
	{
	    int randomPosition = rgen.nextInt(TotalCard);
	    Cards temp = _Cards[i];
	    _Cards[i] = _Cards[randomPosition];
	    _Cards[randomPosition] = temp;
	}

	return _Cards;
}


@Override
public String toString() 
{
	return (this.toString());

}

private void CreateGreenCards()
{
	String  thisLine = null;
	
	  try{
	     // open input stream test.txt for reading purpose.
	     BufferedReader br = new BufferedReader(new FileReader("/Users/gayhazan/Documents/workspace/LocDiskworld/src/GreenCards.txt"));
	     while ((thisLine = br.readLine()) != null) {
	    	
	    	 int iterator =  47;
	    	 int j = 1;
	    	//If not comment then read
	    	 if (!thisLine.contains("name"))
	    	 {
	    		 String cardName = thisLine.split("=")[1];
	    		 
	    		 this.Green_Cards[iterator] = new GreenCards( cardName, 401 + j, true,CardType.GreenCards);
	    		 
	    		 j++;
	    		 iterator--;
	    		
	    	 }
	    	 else if (!thisLine.contains("verb"))
	    	 {
	    		 Action newAction = Action.ParseString(thisLine);
	    		 this.Green_Cards[iterator+1].AddAction(newAction);
	    	 }
	    	 else if (!thisLine.contains("symbol"))
	    	 {
	    		 String [] symbols = thisLine.split("=")[1].split(",");
	    		 
	    		 for (String s : symbols)
	    		 {
	    			 this.Green_Cards[iterator+1].AddSymbols(s);
	    		 }
	    	 }
	    	 

	     }       
	  }catch(Exception e){
	     e.printStackTrace();
	  }		  
}

private void CreateBrownCards()
{
	String  thisLine = null;
	
	  try{
	     // open input stream test.txt for reading purpose.
	     BufferedReader br = new BufferedReader(new FileReader("/Users/gayhazan/Documents/workspace/LocDiskworld/src/GreenCards.txt"));
	     while ((thisLine = br.readLine()) != null) {
	    	
	    	 int iterator =  53;
	    	 int j = 1;
	    	//If not comment then read
	    	 if (!thisLine.contains("name"))
	    	 {
	    		 String cardName = thisLine.split("=")[1];
	    		 
	    		 this.Brown_Cards[iterator] = new BrownCards( cardName, 401 + j, true,CardType.BrownCards);
	    		 
	    		 j++;
	    		 iterator--;
	    		
	    	 }
	    	 else if (!thisLine.contains("verb"))
	    	 {
	    		 Action newAction = Action.ParseString(thisLine);
	    		 this.Brown_Cards[iterator+1].AddAction(newAction);
	    	 }
	    	 else if (!thisLine.contains("symbol"))
	    	 {
	    		 String [] symbols = thisLine.split("=")[1].split(",");
	    		 
	    		 for (String s : symbols)
	    		 {
	    			 this.Brown_Cards[iterator+1].AddSymbols(s);
	    		 }
	    	 }
	    	 

	     }       
	  }catch(Exception e){
	     e.printStackTrace();
	  }		  
}


private void CreatePersoCards()
{
	String  thisLine = null;
	
	  try{
	     // open input stream test.txt for reading purpose.
	     BufferedReader br = new BufferedReader(new FileReader("/Users/gayhazan/Documents/workspace/LocDiskworld/src/GreenCards.txt"));
	     while ((thisLine = br.readLine()) != null) {
	    	
	    	 int iterator =  6;
	    	 int j = 1;
	    	//If not comment then read
	    	 if (!thisLine.contains("name"))
	    	 {
	    		 String cardName = thisLine.split("=")[1];
	    		 
	    		 this.Personality_Card[iterator] = new PersonalityCards( cardName, 401 + j, true,CardType.PersonalityCards);
	    		 
	    		 j++;
	    		 iterator--;
	    		
	    	 
	    	 }
	    	 

	     }       
	  }catch(Exception e){
	     e.printStackTrace();
	  }		  
}
}

		
		
	



	

