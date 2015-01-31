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
import java.io.Serializable;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;
/**
 * @author Lawrence
 * @version 1.0
 */
public class GameEngine implements Serializable
{
	private List<Player> ListPlayer;
	public ManageCards CardManager;
	private Board GameBoard;
	
	//private Card[] List
	public GameEngine() 
	{
		ListPlayer = new ArrayList<Player>();
		CardManager = new ManageCards();
		GameBoard = new Board();
	}

	
	/*private Boolean HasPlayerPiece(Player p, Area a) 
	{
		List<Pieces> AreaMinions = new ArrayList<Pieces>();
		//for(int p=0; p<ListPlayer.size(); ++p)
		{
			//for each player, traverse minion
			for(int m=0; m<p.ListMinion.size(); ++m)
			{
				if(p.ListMinion.get(m).GetLocation().equalsIgnoreCase(a.GetName()))
					return true;
			}
		}
		return false;
	}*/
	
	private List<Pieces> GetMinionsArea(String n) 
	{
		/*List<Pieces> AreaMinions = new ArrayList<Pieces>();
		for(int p=0; p<ListPlayer.size(); ++p)
		{
			//for each player, traverse minion
			for(int m=0; m<ListPlayer.get(p).ListMinion.size(); ++m)
			{
				if(ListPlayer.get(p).ListMinion.get(m).GetLocation().equalsIgnoreCase(n))
					AreaMinions.add(ListPlayer.get(p).ListMinion.get(m));
			}
		}
		return AreaMinions; */
		return null;
	}
	
	/**
	 * Function will print the current state of the program 
	 */
	public void PrintState()
	{
		System.out.println("Game State");
		System.out.println("-----------\n");
		System.out.println("There are " + ListPlayer.size() + "players:\n");
		for(int p=0; p<ListPlayer.size(); ++p)
		{
			System.out.println("   Player " + p + "(" + ListPlayer.get(p).GetColor() + ")\t is playing as " + ListPlayer.get(p).Demon);
		}
		
		System.out.println("Current state of the game board:\n\tarea \tminions\ttrouble?\tbuilding?\tdemons\ttrolls \n");
		if(GameBoard.ListArea != null)
		{
			//traverse each area in board
			for(int a=0; a<GameBoard.ListArea.size(); ++a)
			{
				Area currentArea = GameBoard.ListArea.get(a);
				String AreaColorList ="";
				String HasTrouble ="";
				String HasBuilding ="";
				if(currentArea.TroubleMakers.size()>0) 
					HasTrouble = "YES";
				else
					HasTrouble = "no";
				
				if(currentArea.IsBuilt) 
					HasBuilding = "YES";
				else
					HasBuilding = "no";
				
				//get all the minions of that area
				for(int p=0; p<ListPlayer.size(); ++p)
				{
				//	if(HasPlayerPiece(ListPlayer.get(p), currentArea))
				//		AreaColorList = AreaColorList+","+ListPlayer.get(p).GetColor().charAt(0);
				}
				if(AreaColorList == "") 
					AreaColorList = "none";
				
				//discuss Demon, Troll and troublemaker
				System.out.println(currentArea.GetName()+"\t"+AreaColorList+"\t"+HasTrouble+"\t"+HasBuilding+"\t"+currentArea.Demons.size()+"\t"+currentArea.Trolls.size());
				
			}
		}
		
		for(int PlayerCount=0; PlayerCount<ListPlayer.size(); ++PlayerCount)
		{
			Player player = ListPlayer.get(PlayerCount);
			List<Cards> PlayerHand = player.GetCards();
			
			//traverse the player cards to gather info for output
			List<String> AreaList = new ArrayList<String>();
			List<String> BrownList = new ArrayList<String>();
			List<String> GreenList = new ArrayList<String>();
			
			for(int PlayerHandCount =0; PlayerHandCount < player.GetPlayerCardCount(); PlayerHandCount++)
			{
				if(PlayerHand.get(PlayerHandCount).Name.contains("City"))
					AreaList.add(PlayerHand.get(PlayerHandCount).Name);
				
				if(PlayerHand.get(PlayerHandCount).Name.contains("Green"))
					GreenList.add(PlayerHand.get(PlayerHandCount).Name);
				
				if(PlayerHand.get(PlayerHandCount).Name.contains("Brown"))
					BrownList.add(PlayerHand.get(PlayerHandCount).Name);
			}
				
			System.out.println("Player "+PlayerCount+"'s current inventory:\n");
			//System.out.println("   - "+player.ListMinion.size()+" minions, "+player.ListBuilding.size()+" buildings, "+player.GetMoneyCount()+" Ankh-Morpork dollars\n");
			System.out.println("   - City Area Cards: \n      ");
			for(int AreaCount=0; AreaCount < AreaList.size(); ++AreaCount)
			{
				if(AreaCount != AreaList.size()-1)
					System.out.println(AreaList.get(AreaCount)+",");
				else
					System.out.println(AreaList.get(AreaCount));
			}
			
			System.out.println("\n   - Player cards: \n      ");
			for(int GreenCardIterator=0; GreenCardIterator<GreenList.size(); GreenCardIterator++)
			{
				if(GreenCardIterator != GreenList.size()-1)
					System.out.println(GreenList.get(GreenCardIterator)+",");
				else
					System.out.println(GreenList.get(GreenCardIterator));
			}
				
			
			for(int BrownCardIterator=0; BrownCardIterator<BrownList.size(); BrownCardIterator++)
			{
				if(BrownCardIterator != BrownList.size()-1)
					System.out.println(BrownList.get(BrownCardIterator)+",");
				else
					System.out.println(BrownList.get(BrownCardIterator));	
			}
			
		
		}
		
		System.out.println("The bank has " + GameBoard.Bank + "  Ankh-Morpork dollars.");
	}

	
	/**
	 * Function will extract gamestate information.  
	 * 
	 * @param String 
	 * @return false if line has error
	 */
	private boolean ParseLine(String s)
	{
		boolean ParseSuccess = true;
		
		StringTokenizer st = new StringTokenizer(s, ",");
		
		//extra first token first
		String FirstToken = st.nextToken();
		
		if(FirstToken.equalsIgnoreCase("board")) {
			GameBoard = CreateBoardElement(s);
		} else if(FirstToken.equalsIgnoreCase("player")) {
			ListPlayer.add(CreatePlayer(s));
		}else if(FirstToken.equalsIgnoreCase("area")) {
			GameBoard.ListArea.add(CreateArea(s));
		}//else if(FirstToken.equalsIgnoreCase("card")) {
		//	ListCard.add(CreateCard(s));
		//}
		//perform SQA     
		return ParseSuccess;
	}

	//to be determined
	private Area CreateArea(String s)
	{
		return new Area(true);
	}
	private Board CreateBoardElement(String s)
	{
		StringTokenizer st = new StringTokenizer(s, ",");
		
		//TBM: Test token size
	     if(st.countTokens() != 4)
	     {
	    	 System.out.println("Error parsing board element. token count invalid: " + st.countTokens());
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
	/**
	 * Get the current state of the player as a string
	 * 
	 * @return Player State format : Player, player number, personality card id, money count, number of cards,_
	 * cards ids, number of minions, minion ids, number of buildings, buildings ids, last
	 */
	private Player CreatePlayer(String s)
	{ 
		String Personality ="";
		String Money ="";
		String Color = "";
		List<Pieces> ListMinion = new ArrayList<Pieces>();
		List<Pieces> ListBuilding= new ArrayList<Pieces>();
		List<Cards> PlayerHand = new ArrayList<Cards>();
		String DemonPiece = "";
		
		int indexToken= 0;
		StringTokenizer st = new StringTokenizer(s, ",");
		boolean ParseSuccess = true;
		//TBM: Test token size
	     while (ParseSuccess && st.hasMoreTokens()) {
	    	 String CurrentToken = st.nextToken();
	         
	         if(indexToken == 2 )
	        	 Personality = CurrentToken;
	         else if(indexToken == 3) 
	        	 Money = CurrentToken;
	         else if(indexToken == 4) 
	        	 Color = CurrentToken;
	         else if(indexToken == 5) {
	        	//extract the list of minion and their corresponding attribute
	        	 int TotalCard = Integer.parseInt(CurrentToken);
	        	 
	        	 //for(int CountCard=0; CountCard < TotalCard; CountCard++ )
	        	//	 PlayerHand.add(new Cards(st.nextToken(), "Player"+ListPlayer.size()+1));
	        	 
	        	//extract the list of minion and their corresponding attribute
	        	 int TotalMinionCount = Integer.parseInt(CurrentToken);
	        	 /*for(int CountMinion=0; CountMinion < TotalMinionCount; CountMinion++ )
	        		 ListMinion.add(new Pieces(Integer.parseInt(st.nextToken()),"",""));
	        	 
	        	 //extract the list of building
	        	 CurrentToken = st.nextToken();
	        	 int TotalBuildingCount = Integer.parseInt(CurrentToken);
	        	 for(int CountBuilding=0; CountBuilding < TotalBuildingCount; CountBuilding++ )
	        		 ListBuilding.add(new Pieces(Integer.parseInt(st.nextToken()),"",""));
	        	 */
	        	 //expect "last keyword"
	        	 CurrentToken = st.nextToken();
	        	 if(!CurrentToken.equalsIgnoreCase("last")) {
	        		 System.out.println("ERROR PARSING LINE: "+ s);
	        		 ParseSuccess = false;
	        	 }
	        	 else
	        		 System.out.println("PARSING PLAYER SUCCESSFUL");
	         } else if(indexToken != 0)
	        	 ParseSuccess = false; 
	         
	         indexToken ++;
	     }
	     //TBD
	     //return new Player();
	     //public Player(int _PlayerNumber, Card _Personality, String _Color, List<Card> _PlayerCards){
	    if(!ParseSuccess) 
	    	return new Player();
	    else
	    	return null;
	    	//return new Player(ListPlayer.size()+1, new Card(Personality, "Player"+ListPlayer.size()+1),Integer.parseInt(Money), Color, PlayerHand,ListMinion, ListBuilding);
		
	}
	
	public String toString()
	{
		return this.toString();
	}
}
