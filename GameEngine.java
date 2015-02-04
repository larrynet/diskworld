import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;
import java.util.List;

/**
 * GameEngine is the class which the user will interact. It will call the other class according to user decisions inputted from the console or UI (later build). 
 * This class acts as a Facade to hide the internal structure to player. 
 * 
 * @author Lawrence
 * @version 1.0
 */
public class GameEngine implements Serializable
{
	private List<Player> ListPlayer;
	private ManageCards CardManager;
	private Board GameBoard;
	private int TotalPlayer;
	
	/**
	 * Default constructor who will init all internal structure with minimum supported player 
	 */
	public GameEngine() 
	{
		TotalPlayer = 2; //initialize to the minimal player in default constructor
		InitializeData();
	}
	
	/**
	 * @param p - Init all internal structure with player p
	 */
	public GameEngine(int p) 
	{
		if (ValidPlayerIndex(p))
		{
			TotalPlayer = p;
			InitializeData();
		}
		else
		{
			System.out.println("Diskworld only supports 2 to 4 players. ");
			return; //return control to user without init the internal structure
		}
	}
	
	/**
	 * Return the current bank amount of player 
	 * 
	 * @param PlayerIndex whom to get Balance
	 * @return balance of player
	 */
	public int GetPlayerBank(int PlayerIndex)
	{
		if (ValidPlayerIndex(PlayerIndex))
			return ListPlayer.get(PlayerIndex).GetMoneyCount();
		else 
			return 0;
	}
	
	/**
	 * Called Internally to test if PlayerIndex is valid.
	 * 
	 * @param PlayerIndex
	 * @return
	 */
	private boolean ValidPlayerIndex(int PlayerIndex)
	{
		return ((PlayerIndex <= 4) && (PlayerIndex >= 1));
	}
	
	/**
	 * Called Internally to test if AreaIndex is valid.
	 * 
	 * @param PlayerIndex
	 * @return
	 */
	private boolean ValidAreaIndex(int AreaIndex)
	{
		return ((AreaIndex <= 12) && (AreaIndex >= 1));
	}
	
	/**
	 * @param PlayerIndex whom to pay
	 * @param amount to pay
	 * @return if succeeded or not
	 */
	public boolean PayPlayer(int PlayerIndex, int amount)
	{
		if (ValidPlayerIndex(PlayerIndex) == false)
			return false;
		else
		{
			int NewAmount = GameBoard.GetBalance()-amount;
			if(NewAmount >= 0)
			{
				GameBoard.SetBalance(NewAmount);
				ListPlayer.get(PlayerIndex).AddToMoney(amount);
				return true;
			}
			else
				return false;
		}
		
	}
	
	/**
	 * Function will put a player minion in the area
	 * 
	 * @param AreaNumber to place minion
	 * @param player index which minion belongs
	 * @return if Suceeded or not
	 */
	public boolean PlaceMinion(int AreaNumber,int player)
	{
		if(ValidPlayerIndex(player) && ValidAreaIndex(AreaNumber))
			return GameBoard.PlaceMinion(AreaNumber, ListPlayer.get(player));
		else 
			return false;
	}
	
	/**
	 * Function will put a player building in the area
	 * 
	 * @param AreaNumber to place building
	 * @param player index which building belongs
	 * @return if Suceeded or not
	 */
	public boolean PlaceBuilding(int AreaNumber,int player)
	{
		if(ValidPlayerIndex(player) && ValidAreaIndex(AreaNumber))
			return GameBoard.PlaceBuilding(AreaNumber, ListPlayer.get(player));
		else 
			return false;
	}
	
	
	/**
	 * Function will put a troll in the area
	 * 
	 * @param AreaNumber indicating the area where action will be performed
	 * @return if Suceeded or not
	 */
	public boolean PlaceTroll(int AreaNumber)
	{
		if(ValidAreaIndex(AreaNumber))
			return GameBoard.PlaceTroll(AreaNumber);
		else 
			return false;
	}
	
	/**
	 * Function will put a demon the area
	 * 
	 * @param AreaNumber indicating the area where action will be performed
	 * @return if Suceeded or not
	 */
	public boolean PlaceDemon(int AreaNumber)
	{
		if(ValidAreaIndex(AreaNumber))
			return GameBoard.PlaceDemon(AreaNumber);
		else 
			return false;
	}

	/**
	 * Function will put a troublemaker the area
	 * 
	 * @param AreaNumber indicating the area where action will be performed
	 * @return if Suceeded or not
	 */
	public boolean PlaceTroubleMarker(int AreaNumber)
	{
		if(ValidAreaIndex(AreaNumber))
			return GameBoard.PlaceTroubleMarker(AreaNumber);
		else 
			return false;
	}
	
	
	/**
	 * Print State of the game
	 */
	public void PrintState()
	{
		//Print Player Profile
		System.out.println("Game State");
		System.out.println("-----------");
		System.out.println();
		System.out.println("There are " + this.TotalPlayer + " players in the game");
		System.out.println();
		
		//Call player method to print player profile for each player
		for (Player player : this.ListPlayer)
		{
			player.PrintPlayerProfile();
		}
		
		System.out.println();
		GameBoard.PrintState();
		
		//Call player method to print player state
		for (Player player : this.ListPlayer)
		{
			player.GetPlayerState();
		}

		
	}
	
	/**
	 * Initialize internal structure and assign ressources to each player
	 */
	public void InitializeData()
	{
		int PlayerHandSize = 5;
		int TotalBuildingPerPlayer = 6;
		int TotalMinionPerPlayer = 12;
		ListPlayer = new ArrayList<Player>();
		CardManager = new ManageCards();
		GameBoard = new Board();
		
		for(int PlayerCount = 0; PlayerCount <TotalPlayer; PlayerCount++)
		{
			//Assign set of RandomCard to player
	        List<Cards> ListPlayerCards = new ArrayList<Cards>();
	        
	        // fetch a random personality card
	        Cards PlayerPersonality = CardManager.GetCard(CardType.PersonalityCards);
	        
	        //fetch a random city area card
	        for(int PlayerHandCount= 0; PlayerHandCount < PlayerHandSize; PlayerHandCount++)
	        {
	        	ListPlayerCards.add(CardManager.GetCard(CardType.GreenCards));      	
	        }
	        
	        //create a list minions and buildings for each player
	        List<Pieces> ListMinions = new ArrayList<Pieces>();
	        List<Pieces> ListBuildings = new ArrayList<Pieces>();
	        Colors PlayerColor = Colors.values()[ListPlayer.size()+1];
	        for(int MinionCount = 0; MinionCount < TotalMinionPerPlayer; ++MinionCount)
	        {
	        	ListMinions.add(new Pieces(PieceType.Minion, PlayerColor));
	        }
	        for(int BuildingCount = 0; BuildingCount < TotalBuildingPerPlayer; ++BuildingCount)
	        {
	        	ListBuildings.add(new Pieces(PieceType.Building, PlayerColor));
	        } 
	        
	        ListPlayer.add(new Player(ListPlayer.size(), PlayerPersonality, PlayerColor, ListPlayerCards, ListMinions, ListBuildings));
		}
		
		//Initialize a random value to dice
		GameBoard.RollDie();
	}
	
	/**
	 * @return bank balance
	 */
	public int GetBankBalance()
	{
		return GameBoard.GetBalance();
	}
	
	/**
	 * @return a copy of the card manager which contains all the card
	 */
	public ManageCards GetCardManager()
	{
		return CardManager;
	}
	
	/**
	 * @return if game is initialized
	 */
	public boolean IsGameInitialize()
	{
		return (TotalPlayer>0);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return this.toString();
	}
}
