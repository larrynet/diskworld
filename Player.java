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
	private Colors Color;
	List<Pieces> ListMinions;
	List<Pieces> ListBuildings;
	List<Card> CityAreaCards;
	String Demon;
	
	/**
	 * 
	 */
	public Player () {}
	
	/**
	 * @param _PlayerNumber
	 * @param _Personality
	 * @param _Money
	 * @param _Color
	 * @param _PlayerCards
	 * @param _ListMinion
	 * @param _ListBuilding
	 */
	public Player(int _PlayerNumber, Card _Personality, int _Money, Colors _Color, List<Card> _PlayerCards, List<Pieces> _ListMinion, List<Pieces> _ListBuilding){
		PlayerNumber = _PlayerNumber;
		Personality = _Personality;
		Money = _Money;
		ListMinions = _ListMinion;
		ListBuildings = _ListBuilding;
		PlayerCards = _PlayerCards;
		Color = _Color;
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
	
	public List<Card> GetCards() {return this.PlayerCards;}
	/**
	 * Get the count of minions
	 * @return number of minions currently belonging to user
	 */
	public int GetMinionCount()
	{
		return this.ListMinions.size();
	}
	
	/**
	 * Get the count of buildings
	 * @return number of buildings currently belonging to user
	 */
	public int GetBuildingCount()
	{
		return this.ListBuildings.size();
	}
	
	
	
	/**
	 * Get the current state of the player as a string
	 * 
	 * @return Player State format : Player, player number, personality card id, money count, number of cards,_
	 * cards ids, number of minions, minion ids, number of buildings, buildings ids, last
	 */
	/*public String GetPlayerState()
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
	}*/
	
	
	/**
	 * @param Color
	 * @return
	 */
	public Colors GetColor()
	{
		return Color;
	}
	
		/**
		 * Iterate through the player cards and create a string of card id separated by a comma
		 * @return PlayerCards Id's in comma delimited format
		 */
		private String GetPlayerCardIds()
	{
		StringBuilder AllCards = new StringBuilder();
		
		//
		/*for (int i = 0; i < this.GetPlayerCardCount(); i++)
		{
			AllCards.append(Integer.toString(this.PlayerCards.get(i).GetCardID()));
			
			//Add Comma logic
			if (i < this.GetPlayerCardCount())
			{
				AllCards.append(",");
			}
		}
		*/
		return AllCards.toString();
	}
	
	
		public void RetrieveMinion(Pieces Minion)
		{
			if (this.GetMinionCount() < 12)
			{
				if (Minion.GetPieceColor() == this.Color && Minion.GetPieceType() == PieceType.Minion)
				{
					this.ListMinions.add(Minion);
				}
			}
		}
		
		public Pieces PlaceMinion()
		{
			Pieces MinionToSend =  this.ListMinions.get(this.GetMinionCount()-1);
				
			this.ListMinions.remove(this.GetMinionCount()-1);
			
			
			return MinionToSend;
		
		}
		
		public void RetrieveBuiding(Pieces Building)
		{
			if (this.GetMinionCount() < 6)
			{
				if (Building.GetPieceColor() == this.Color && Building.GetPieceType() == PieceType.Building)
				{
					this.ListBuildings.add(Building);
				}
			}
		}
		
		public Pieces PlaceBuilding()
		{
			Pieces BuildingToSend =  this.ListBuildings.get(this.GetBuildingCount()-1);
			
			this.ListMinions.remove(this.GetBuildingCount()-1);
			
			
			return BuildingToSend;
		}
	




}
