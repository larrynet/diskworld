import java.util.List;
import java.io.Serializable;

/**
 * Player Class to create and initiate a player.
 * 
 * @author Gay Hazan
 * @version 1.0
 */
public class Player implements Serializable {
	
	public static final int INITIAL_BANK = 10;
	
	private Cards Personality;
	private List<Cards> PlayerCards;
	private int PlayerNumber;
	private int Money;
	private Colors Color;
	private List<Pieces> ListMinions;
	private List<Pieces> ListBuildings;
	private List<Cards> ListCityAreaCards;
	
	
	/**
	 * Player constructor 
	 * 
	 * @param _PlayerNumber
	 * @param _Personality
	 * @param _Money
	 * @param _Color
	 * @param _PlayerCards
	 * @param _ListMinion
	 * @param _ListBuilding
	 */
	public Player(int _PlayerNumber, Cards _Personality, Colors _Color, List<Cards> _PlayerCards, List<Pieces> _ListMinion, List<Pieces> _ListBuilding){
		PlayerNumber = _PlayerNumber;
		Personality = _Personality;
		Money = INITIAL_BANK;
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
	 * Method to play a player card and remove it from the players hand
	 * 
	 * @return Played Card
	 */
	public Cards PlayCard()
	{
		Cards PlayedCard = this.PlayerCards.get(this.PlayerCards.size()-1);
		
		this.PlayerCards.remove(this.PlayerCards.size()-1);
		
		return PlayedCard;
	}
	
	
	/**
	 * Add a player card to current players hand
	 * 
	 * @param PlayerCard
	 */
	public void AddPlayerCard(Cards PlayerCard)
	{
		this.PlayerCards.add(PlayerCard);
	}
	
	/**
	 * @param CityAreaCard
	 */
	public void AddCityAreayCard(Cards CityAreaCard)
	{
		this.ListCityAreaCards.add(CityAreaCard);
	}
	
	public Cards RemoveCityAreaCard()
	{
		Cards UsedCityAreaCard = this.ListCityAreaCards.get(this.ListCityAreaCards.size()-1);
		
		this.ListCityAreaCards.remove(this.ListCityAreaCards.size()-1);
		
		return UsedCityAreaCard;
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
	 * Add money to player bank roll
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
	 * Retrieve player cards
	 * @return List of Player Cards currently in player's hand
	 */
	public List<Cards> GetCards() 
	{
		return this.PlayerCards;
	}
	
	
	/**
	 * Get Current Minion count of player
	 * 
	 * @return number of minions currently belonging to user
	 */
	public int GetMinionCount()
	{
		return this.ListMinions.size();
	}
	
	/**
	 * Get current count of buildings
	 * 
	 * @return number of buildings currently belonging to user
	 */
	public int GetBuildingCount()
	{
		return this.ListBuildings.size();
	}
	
	/**
	 * Method used to print the player profile with the following info: 
	 * The player number and the player personality
	 */
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
		System.out.println();
		System.out.println("City Area Cards:");
		System.out.println(this.PrintCityAreaCards());
		System.out.println();
		System.out.println("PlayerCards");
		System.out.println(this.PrintPlayerCards()[0]);
		System.out.println(this.PrintPlayerCards()[1]);

	}
	
	
	/**
	 * Get the color of the current player
	 * 
	 * @param Color
	 * @return
	 */
	public Colors GetColor()
	{
		return Color;
	}
	
	
	/**
	 * Add Minions back to the players minions
	 * 
	 * @param Minion
	 */
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
	
	
	/**
	 * Remove a minion from player's inventory and add to Area
	 * 
	 */
	public Pieces PlaceMinion()
	{
		Pieces MinionToSend =  this.ListMinions.get(this.GetMinionCount()-1);
			
		this.ListMinions.remove(this.GetMinionCount()-1);
		
		
		return MinionToSend;
	
	}
	
	/**
	 * Add building back to the players minions
	 * 
	 * @param Pieces of type Building
	 */
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
	
	/**
	 * Remove a building from player's inventory and add to Area
	 * 
	 */
	public Pieces PlaceBuilding()
	{
		Pieces BuildingToSend =  this.ListBuildings.get(this.GetBuildingCount()-1);
		
		this.ListBuildings.remove(this.GetBuildingCount()-1);
		
		
		return BuildingToSend;
	}

	public List<Cards> GetCityAreayCards()
	{
		return this.ListCityAreaCards;
	}
	
	private String PrintCityAreaCards()
	{
		StringBuilder strCityAreaCards = new StringBuilder();
		 for (Cards CityAreaCard : this.ListCityAreaCards)
		 {
			 strCityAreaCards.append(CityAreaCard.Name);
			 strCityAreaCards.append(",");
			 
		 }
		 
		 strCityAreaCards.deleteCharAt(strCityAreaCards.length()-1);
		 
		 return strCityAreaCards.toString();
	}

	private String[] PrintPlayerCards()
	{
		StringBuilder strGreenPlayerCards = new StringBuilder();
		StringBuilder strBrownPlayerCards = new StringBuilder();
		
		
		
		strGreenPlayerCards.append("Green ");
		strBrownPlayerCards.append("Brown ");
		
		for (Cards PlayerCard : this.PlayerCards)
		{
			if (PlayerCard.Type == CardType.BrownCards)
			{
				strBrownPlayerCards.append(PlayerCard.Id);
				strBrownPlayerCards.append(",");
			}
			else if (PlayerCard.Type == CardType.GreenCards)
			{
				strGreenPlayerCards.append(PlayerCard.Id);
				strGreenPlayerCards.append(",");
			}
		}
		
		String[] FinalString  = {strGreenPlayerCards.toString(),strBrownPlayerCards.toString()};
		
		return FinalString;

		
	}

}
