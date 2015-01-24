/**
 * Sypnosis: This class will provide make the f
 * State structure:
 * 	//troublemakers
	//troublemaker, label, location
	//player definition:
	//player,personality,minioncount,minion1,...,buildingcount, building1,..,demonpiece, last
	//board definition:
	//board,areaname1,areacost1,number1,isempty1,status1, ..., last
	//card definition:
	//card,played,RandomEventCardCount, RandomEventCardString1, ..., last
	//card,played,Personality, Personality1..., last
	//card, played,area1string, ..., last
	//card, played, greenplayercount, greenplayer1, last
	//card, played, browncardcount, browncard1,..., last
 */
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
/**
 * @author Lawrence
 * @version 1.0
 */
public class GameEngine 
{
	//real game
	//public static final int MAX_GREEN_CARD = 48;
	//public static final int MAX_BROWN_CARD = 53;
	//public static final int MAX_PERS_CARD = 48;
	//public static final int MAX_CITY_CARD = 48;
	
	public static final int MAX_GREEN_CARD = 2;
	public static final int MAX_BROWN_CARD = 2;
	public static final int MAX_PERS_CARD = 3;
	public static final int MAX_CITY_CARD = 1;

	private List<Player> ListPlayer;
	private Card[] ListGreenCard;
	private Card[] ListBrownCard;
	private Board GameBoard;
	
	//private Card[] List
	public GameEngine(int PlayerNum) 
	{
		ListGreenCard = new Card[MAX_GREEN_CARD];
		ListBrownCard = new Card[MAX_BROWN_CARD];
		GameBoard = new Board();
	}
	
	/**
	 * Function will go through the filepath and load its datastructure. 
	 * 
	 * @param path
	 * @return void
	 */
	public void ImportGameState(String path) 
	{
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        if(!ParseLine(line))
		        	System.out.println("Error while parsing: " + line);
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Function will print the current state of the program 
	 */
	public void PrintState()
	{
	}
	
	/**
	 * Function to export game state
	 * 
	 * @param path
	 * @return void
	 */
	public void ExportGameState(String path) {
		
	}
	
	/**
	 * Function will extract gamestate information.  
	 * 
	 * @param String 
	 * @return false if line has error
	 */
	private boolean ParseLine(String s)
	{
		boolean HasError = false;
		
		StringTokenizer st = new StringTokenizer(s, ",");
		
		//extra first token first
		String FirstToken = st.nextToken();
		
		if(FirstToken.equalsIgnoreCase("board")) {
			
		} else if(FirstToken.equalsIgnoreCase("player")) {
			ListPlayer.add(CreatePlayer(s));
		}else if(FirstToken.equalsIgnoreCase("area")) {
			GameBoard = CreateBoard(s);
		}else if(FirstToken.equalsIgnoreCase("card")) {
			//create card
		}
		//perform SQA     
		return HasError;
	}
	
	private Board CreateBoard(String s)
	{
		StringTokenizer st = new StringTokenizer(s, ",");
		
		//TBM: Test token size
	     if(st.countTokens() != 3)
	     {
	    	 System.out.println("Error parsing borad. token count invalid: " + st.countTokens());
	    	 return new Board();
	     }
	     else
	     {
	    	 st.nextToken();
	    	 String Die = st.nextToken();
	    	 String Bank = st.nextToken();
	    	 return new Board(Integer.parseInt(Die), Integer.parseInt(Bank));
	     }
	}
	private Player CreatePlayer(String s)
	{ 
		String Personality ="";
		String Money ="";
		List<String> ListMinion = new ArrayList<String>();
		List<String> ListBuilding= new ArrayList<String>();
		String DemonPiece = "";
		
		int indexToken = 0;
		StringTokenizer st = new StringTokenizer(s, ",");
		boolean ParseSuccess = true;
		//TBM: Test token size
	     while (ParseSuccess && st.hasMoreTokens()) {
	    	 String CurrentToken = st.nextToken();
	         System.out.println("CurrentToken: " + CurrentToken);
	         if(indexToken == 1 )
	        	 Personality = CurrentToken;
	         else if(indexToken == 2) 
	        	 Money = CurrentToken;
	         else if(indexToken == 3) {
	        	//extract the list of minion and their corresponding attribute
	        	 int TotalMinionCount = Integer.parseInt(CurrentToken);
	        	 for(int CountMinion=0; CountMinion < TotalMinionCount; CountMinion++ )
	        		 ListMinion.add(st.nextToken());
	        	 
	        	 //extract the list of building
	        	 CurrentToken = st.nextToken();
	        	 int TotalBuildingCount = Integer.parseInt(CurrentToken);
	        	 for(int CountBuilding=0; CountBuilding < TotalBuildingCount; CountBuilding++ )
	        		 ListBuilding.add(st.nextToken());
	        	 
	        	 //extract demon name
	        	 DemonPiece = st.nextToken();
	        	 
	        	 //expect "last keyword"
	        	 CurrentToken = st.nextToken();
	        	 if(!CurrentToken.equalsIgnoreCase("last")) {
	        		 System.out.println("ERROR PARSING LINE: "+ s);
	        		 ParseSuccess = false;
	        	 }
	        	 else
	        		 System.out.println("PARSING PLAYER SUCCESSFUL");
	         } else 
	        	 ParseSuccess = false; 
	         
	         indexToken ++;
	     }
	    if(!ParseSuccess) 
	    	return new Player();
	    else
	    	return new Player(Personality, Integer.parseInt(Money), ListMinion, ListBuilding, DemonPiece);
		
	}
}
