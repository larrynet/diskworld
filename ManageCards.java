import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

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
	
	private PersonalityCards[] Personality_Card=new PersonalityCards[7];
	private EventCards []Event_Card =new EventCards[12];
	private CityAreaCards []CityArea_Cards =new CityAreaCards[12];
	private GreenCards[] Green_Cards=new GreenCards[48];
	private BrownCards[] Brown_Cards=new BrownCards[53];
	
	
	
	//ManageCards Constructor
	
	/**
	 * Default constructor
	 * 
	 */
	public ManageCards()
	{
		String []PersonalityName={"Lord Vetinari","Lord Selachii ","Lord Rust ","Lord de Word ","Dragon King of Arms ","Chrysoprase ","Commander Vimes"};
	     String []EventName={"The Dragon","Flood","Fire","Fog","Riots","Explorion","Mysterious Murders","Demons From The Dungeon Dimension","Subsidence","Bloody Stupid Johnson","Trolls","Earthquake"};
		 String []CityAreaName={"Dolly Sisters","Unreal Estate","Dragon's Landing","Small Gods","The Scours","The Hippo","The Shades","Dimwell","Longwall","Isle of Gods","Seven Sleepers","Nap Hill"};
		 String [] BrownName=
			 {
			"Sergeant Cheery Littlebottom"     , "Otto Shriek"               , "The Claks"                         ,    "Sergeant Colon"             ,  "Cosmo Lavish" ,   
			  "The Dean"                       , "HELLO"                     ,"Burleigh & Stronginth"              ,    "The Bursar"                 , "Cable Street Particulars", 
			  "Canting Crew"                   ,"Cancer"                     ,  "The Chair of Indefinite Studies"  ,"Sir Charles Lavatory"           ,"Dorfi"                    ,
			  "Sergeant Detritus"              ,"Deep Dwarves"               ,"Adora Bell Dearheart"               ,"The Alchemists'Guild"           ,"The Auditors"             ,
			  "Susan"                          ,"Sybil Vimes"                ,"Mr Teatime"                         ,"The Watch"                      ,"Wee Mad Arthur",
			  "Willian de Worde"               ,"Willikins"                  ,"Archchancellor Ridcully"            ,"Ruby"                           ,"The Senior Wrangler",
			  "Mr Shine"                       ,"Mr Slant"                   ,"The Smoking Gnu"                     , "Stanley"                      ,"Moist Wong Lipwig",
			  "Doctor Mossy Lawn"              , "Patrician's Palace"        ,"Pondor Stibbons"                     ,"The Post Office","Reacher Gilt","Professor of Recent Runes",
			  "Doctor Hix"                     ,"Hobsons's Livery Stable"     ,"Hubert"                            ,"Igor"                           ,"The Luggage"                 
			  ,"The Mob"                       ,"Lord Downey"                ,"The Mob"                            ,"Lord Downey"                    ,"Dwarves"                     ,
			  "Edward d'Earth"                ,"Buggy Swires"                ,"Errol"                              , "Gargoyles"
			  };
		 
		 String []GreenName=
			   {
				 "Mr Boggis"                      ,"Mr Bent"             ,"Mr Beggas'Guild"    ,"The Bank of Akh-Morpork"   ,"The Ankh Mor Pork Sunshine Dragon Sanctuary",
				 "Sergeant Angua"                 ,"The Agony Aunts"     ,"The Dysk"           ,"The Duckman"               ,"Drumknott",
				 "CMOT Dibbler"                   ,"Dr Cruces"           ,"Captain Carrot"     ,"Mrs Cake"                   ,"Groat",
				 "Gimlet's Dwarf Delicatessen"    ,"Gaspode"             ,"Fresh Start Club"   ,"Foul Ole Ron"               ,"The Fools'Guild",
				 "The Fire Brigade"                ,"Inigo Skimmer"       ,"History Monks"     ,"Hex"                       ,"Here'nNow",
				 "Harry King"                     ,"Harga's House of Ribs","Mr Gryle"          ,"The Peeled Nuts"           ,"The Opera House",
				 "Nobby Nobbs"                    ,"Modo"                 ,"The Mended Drum"   ,"Librarian"                 ,"Leonard of Quirm",
				 "Shonky Shop"                    ,"Sacharissa Cripslock" ,"Rosie Palm"         ,"Rincewind"                 ,"The Royal Mint",
				 "Queen Mollu"                    ,"Pink PussyCat Club"    ,"Dr Whiteface"      ,"Wallace Sonky"             ,"THe Seamstresses Guild",
				 "Mr Pin & Mr Tulip"              ,"The Thieves'Guild"     ,"Zorgo the Retro-phrenologist"
				 };
				 
		 
		 
		 int [] PersonalityId={101,102,103,104,105,106,107};
	     int [] EventId={201,202,203,204,205,206,207,208,209,210,211,212};
		 int []CityAreaId={301,302,303,304,305,306,307,308,309,310,311,312};
		 
		 Boolean Status =true;
		 
		 
		for (int i = 0; i <= 4; i++) 
		{
			if (i == 0)
			{
				for (int j = 0; j <= this.Personality_Card.length - 1; j++)

					this.Personality_Card[j] = new PersonalityCards(PersonalityName[j], PersonalityId[j], Status,CardType.PersonalityCards);
				
			} else if (i == 1)
			{
				for (int j = 0; j <= this.Event_Card.length - 1; j++)

					this.Event_Card[j] = new EventCards(EventName[j],EventId[j], Status, CardType.EventCards);
			}

			else if (i == 2) 
			{
				for (int j = 0; j <= this.CityArea_Cards.length - 1; j++)

					this.CityArea_Cards[j] = new CityAreaCards(CityAreaName[j],CityAreaId[j], Status, CardType.CityAreaCards);

			} else if (i == 3) 
			{
				for (int j = 0; j <= this.Green_Cards.length - 1; j++)

					this.Green_Cards[j] = new GreenCards(
							(GreenName[j] ), 401 + j, Status,CardType.GreenCards);

			} else if (i == 4) 
			{
				for (int j = 0; j <= this.Brown_Cards.length - 1; j++)

					this.Brown_Cards[j] = new BrownCards((BrownName[j]), (501 + j), Status,CardType.BrownCards);

			}

		}

	}
	
	
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
     
     if (i==0)              
     {   
    	 for (int j=0; j<=this.Personality_Card.length-1 ;j++)
    		 
    	 {
    	 this.Personality_Card[j]=new PersonalityCards(PersonalityName[j],PersonalityId[j],Status,CardType.PersonalityCards);
    	 System.out.print(PersonalityName[j]);
    	 System.out.print(",");
    	 }
    	
     }
     else if (i==1)
     {
    	 for (int j=0;j<=this.Event_Card.length-1 ;j++)
    	 {
    		 this.Event_Card[j]=new EventCards(EventName[j],EventId[j],Status,CardType.EventCards);
    		 System.out.print(EventName[j]);
    		 System.out.print(",");
    	 }
    	
     }
     
     else if (i==2)
     {
    	 for (int j=0;j<=this.CityArea_Cards.length-1;j++)
    	 {
    		 this.CityArea_Cards[j]=new CityAreaCards(CityAreaName[j],CityAreaId[j],Status,CardType.CityAreaCards);
    		 System.out.print(CityAreaName[j]);
    		 System.out.print(",");
    	 }
    	 
     }
     else if (i==3)
     {
    	 for (int j=0;j<=this.Green_Cards.length-1;j++)
    	 {
    		 this.Green_Cards[j]=new GreenCards(("green"+(j+1)),401+j,Status,CardType.GreenCards);
    		 System.out.print(this.Green_Cards[j].GetName());
    		 System.out.print(",");
    	 }
    	 
    	 
    	  
     }
     else if(i==4)
     {
    	 for (int j=0;j<=this.Brown_Cards.length-1;j++)
    	 {
    		 this.Brown_Cards[j]=new BrownCards(("Brown"+" "+(j+1)),(501+j),Status,CardType.BrownCards);
    		 System.out.print(this.Brown_Cards[j].GetName());
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
		System.out.println("State of Current Cards ");
		System.out.println("===========================================================================");
		System.out.print("Personality Cards : ");
		for (int i=0;i<this.Personality_Card.length-1  ;i++ )
			System.out.print(this.Personality_Card[i].GetName()+ "," + this.Personality_Card[i].GetID()+",");
		System.out.print(",Last");
		System.out.println("");
		System.out.println("");
		
		
		System.out.print("Event Cards :");
		for (int i=0;i< this.Event_Card.length-1  ;i++ )
			System.out.print( this.Event_Card[i].GetName() + "," + this.Event_Card[i].GetID()+",");
		System.out.print(",Last"); 
		System.out.println("");
		System.out.println("");
		
		System.out.print("City Area Cards :");
		for (int i=0;i<this.CityArea_Cards.length-1  ;i++ )
			System.out.print(this.CityArea_Cards[i].GetName() +","+ this.CityArea_Cards[i].GetID()+",");
		System.out.print(",Last");  
		System.out.println("");
		System.out.println("");

		System.out.print("Board Cards Green : ");
		for (int i=0;i<this.Green_Cards  .length-1  ;i++ )
			System.out.print(this.Green_Cards[i].GetName() +","+ this.Green_Cards[i].GetID()+",");
		System.out.print(",Last");  
		System.out.println("");
		System.out.println("");
		
		System.out.print("Boards Card Brown : ");
		for (int i=0;i<this.Brown_Cards.length-1  ;i++ )
			System.out.print( this.Brown_Cards[i].GetName() + "," + this.Brown_Cards[i].GetID() +",");
		System.out.print(",Last"); 
		System.out.println("");
		
		
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
			
		{  this.ShuffleCards((Cards [])  this.Personality_Card);
			for (int i=0;i<=this.Personality_Card.length-1;i++)
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
			for (int i=0;i<=this.Brown_Cards.length-1;i++)
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

	for (int i=0; i<_Cards.length; i++)
	{
	    int randomPosition = rgen.nextInt(_Cards.length);
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


}

		
		
	



	

