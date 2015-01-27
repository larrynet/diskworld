import java.util.List;
import java.util.Vector;

/**
 * @author gayhazan
 *
 */
public class Player {
	
	private Card Personality;
	private List<Card> PlayerCards;
	private int PlayerNumber;
	private int Money;
	List<Pieces> ListMinion;
	List<Pieces> ListBuilding;
	String Demon;
	
	public Player () {}
	
	public Player(int _PlayerNumber, Card _Personality, String _Color, List<Card> _PlayerCards){
		PlayerNumber = _PlayerNumber;
		Personality = _Personality;
		Money = 10;
		ListMinion = CreateMinions(_Color);
		ListBuilding = CreateBuildings(_Color);
		PlayerCards = _PlayerCards;
		//Demon = new Pieces();
	}
	
	/**
	 * Public Getter for Player Personality Card
	 * 
	 * @return Card personality card object
	 */
	public Card GetPlayerPersonality()
	{
		return this.Personality;
	}
	
	
	/**
	 * Public Getter for player money sum
	 * 
	 * @return player total money
	 */
	public int GetMoneyCount()
	{
		return this.Money;
	}
	
	/**
	 * Add to player bank roll
	 * 
	 * @param amount to add
	 */
	public void AddToMoney(int amount)
	{
		this.Money += amount;
		
	}
	
	/**
	 * Deduct from player bank roll
	 * 
	 * @param amount to deduct
	 */
	public void DeductFromMoney(int amount)
	{
		this.Money -= amount;
	
	}

	
	/**
	 * Get the number of cards currently in players hand
	 * 
	 * @return count of player cards
	 */
	public int GetPlayerCardCount()
	{
		return this.PlayerCards.size();
	}
	
	
	/**
	 * Get the count of minions
	 * @return number of minions currently belonging to user
	 */
	private int GetMinionCount()
	{
		return this.ListMinion.size();
	}
	
	/**
	 * Get the count of buildings
	 * @return number of buildings currently belonging to user
	 */
	private int GetBuildingCount()
	{
		return this.ListBuilding.size();
	}
	
	
	
	/**
	 * Get the current state of the player as a string
	 * 
	 * @return Player State format : Player, player number, personality card id, money count, number of cards,_
	 * cards ids, number of minions, minion ids, number of buildings, buildings ids, last
	 */
	public String GetPlayerState()
	{
		
		String playerState = 	"Player,"
								+ Integer.toString(this.PlayerNumber) + "," 
								+ "personality"+Integer.toString(this.Personality.GetCardID()) + ","
								+ Integer.toString(this.GetMoneyCount()) + ","
								+ Integer.toString(this.GetPlayerCardCount()) + ","
								+ this.GetPlayerCardIds() + ","
								+ Integer.toString(this.GetMinionCount()) + ","
								+ this.GetMinionIds() + ","
								+ Integer.toString(this.GetBuildingCount()) + ","
								+ this.GetBuildingIds() + ","
								+ "last";
							 
		return playerState;
	}
	
	
	/**
	 * @param Color
	 * @return
	 */
	private List<Pieces> CreateMinions(String Color)
	{
		int pieceIdSequence = this.PlayerNumber * 100;
		
		List<Pieces> lstPlayerPieces = new Vector<Pieces>();
		for (int i = 1; i<= 12; i++ )
		{
			Pieces piece = new Pieces(pieceIdSequence + i,"mignion",Color);
			
			lstPlayerPieces.add(piece);
		}
		
		return lstPlayerPieces;
	}
	
	/**
	 * @param Color
	 * @return
	 */
	private List<Pieces> CreateBuildings(String Color)
	{
		int pieceIdSequence = (this.PlayerNumber * 100) + 10;
		List<Pieces> lstPlayerPieces = new Vector<Pieces>();
		for (int i = 1; i<= 6; i++ )
		{
			Pieces piece = new Pieces(pieceIdSequence + i,"building",Color);
			
			lstPlayerPieces.add(piece);
		}
		
		return lstPlayerPieces;
	}

	/**
	 * Iterate through the player cards and create a string of card id separated by a comma
	 * @return PlayerCards Id's in comma delimited format
	 */
	private String GetPlayerCardIds()
	{
		StringBuilder AllCards = new StringBuilder();
		
		//
		for (int i = 0; i < this.GetPlayerCardCount(); i++)
		{
			AllCards.append(Integer.toString(this.PlayerCards.get(i).GetCardID()));
			
			//Add Comma logic
			if (i < this.GetPlayerCardCount())
			{
				AllCards.append(",");
			}
		}
		
		return AllCards.toString();
	}
	
	
	/**
	 * Iterate through the player minions list and create a string of minion id separate by a comma
	 * @return Minion Id's in comma delimited format
	 */
	private String GetMinionIds()
	{
		StringBuilder AllMinions = new StringBuilder();
		
		for (int i = 0; i < 12; i++)
		{
			AllMinions.append(Integer.toString(this.ListMinion.get(i).GetPieceID()));
			
			if (i < 11)
			{
				AllMinions.append(",");
			}
			
		}
		return AllMinions.toString();
	}

	
	/**
	 * Iterate through all the player buildings and create a string of building ids by a comma
	 * @return	Comma separated list of Building Ids
	 */
	private String GetBuildingIds()
	{
		StringBuilder AllBuildings  = new StringBuilder();
		
		for (int i = 0; i < 6; i++)
		{
			AllBuildings.append(Integer.toString(this.ListBuilding.get(i).GetPieceID()));
			
			if (i < 5)
			{
				AllBuildings.append(",");
			}
		}
		
		return AllBuildings.toString();
	}


}
