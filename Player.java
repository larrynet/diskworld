import java.util.List;
import java.io.Serializable;

/**
 * @author Gay Hazan
 *
 */
public class Player implements Serializable{
	
	private Cards Personality;
	private List<Cards> PlayerCards;
	private int PlayerNumber;
	private int Money;
	private Colors Color;
	List<Pieces> ListMinions;
	List<Pieces> ListBuildings;
	List<Cards> CityAreaCards;
	
	
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
	public Player(int _PlayerNumber, Cards _Personality, int _Money, Colors _Color, List<Cards> _PlayerCards, List<Pieces> _ListMinion, List<Pieces> _ListBuilding){
		PlayerNumber = _PlayerNumber;
		Personality = _Personality;
		Money = _Money;
		ListMinions = _ListMinion;
		ListBuildings = _ListBuilding;
		PlayerCards = _PlayerCards;
		Color = _Color;
		
	}
	
	public String toString() 
	{
		return this.toString();
	}
	
	/**
	 * Public Getter for Player Personality Cards
	 * 
	 * @return Cards personality card object
	 */
	public Cards GetPlayerPersonality()
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
	
	public List<Cards> GetCards() {return this.PlayerCards;}
	
	
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
	
	public void PrintPlayerProfile()
	{
		System.out.println("Player " + this.PlayerNumber + "(" + this.Color + ")" + "is playing as " + this.Personality.Name + ".");

	}
	
	
	/**
	 * Get the current state of the player as a string
	 * 
	 * @return Player State format : Player, player number, personality card id, money count, number of cards,_
	 * cards ids, number of minions, minion ids, number of buildings, buildings ids, last
	 */
	public void GetPlayerState()
	{
		System.out.println("Player " + this.PlayerNumber + "'s current invetory");
		System.out.println(this.GetMinionCount() + " minions, " + this.GetBuildingCount() + " buildings " + this.GetMoneyCount() + " Ankh-Morpork dollars");
	}
	
	
	/**
	 * @param Color
	 * @return
	 */
	public Colors GetColor()
	{
		return Color;
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
		
		this.ListBuildings.remove(this.GetBuildingCount()-1);
		
		
		return BuildingToSend;
	}

}
