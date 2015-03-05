import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Player Class to create and initiate a player.
 * 
 * @author Gay Hazan
 * @version 1.0
 */
public class Player implements Serializable {
	
	public static final int INITIAL_BANK = 10;
	
    //TEMPORARY
    public List<Cards> CityAreaCards;
	private Cards Personality;
	public List<Cards> PlayerCards;
    public int HandSize;
	private int PlayerNumber;
	private int Money;
	private int Loan;
	private int PayBack;
	private int LostPoints;
	private Colors Color;
	private List<Pieces> ListMinions;
	private List<Pieces> ListBuildings;
	public List<Cards> ListCityAreaCards;
	
	
	/**
	 * Player constructor 
	 * 
	 * @param _PlayerNumber
	 * @param _Personality
	 * @param _Color
	 * @param _PlayerCards
	 * @param _ListMinion
	 * @param _ListBuilding
	 */
	public Player(int _PlayerNumber, Cards _Personality, Colors _Color, List<Cards> _PlayerCards, List<Pieces> _ListMinion, List<Pieces> _ListBuilding){
		PlayerNumber = _PlayerNumber;
		Personality = _Personality;
		Money = INITIAL_BANK;
		HandSize = 5;
		ListMinions = _ListMinion;
		ListBuildings = _ListBuilding;
		PlayerCards = _PlayerCards;
        CityAreaCards = new ArrayList<Cards>();
        
		Color = _Color;
		
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
	
	public boolean RemovePlayerCard(int index )
	{
		return (PlayerCards.remove(index)) != null;
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
		System.out.println("Player " + this.PlayerNumber + " (" + this.Color + ") " + "is playing as " + this.Personality.GetName() + ".");

	}
	
	
	/**
	 * Print the current state of the player as a string
	 * 
	 */
	public void GetPlayerState()
	{
		System.out.println("Player " + this.PlayerNumber + "'s current invetory");
		System.out.println(this.GetMinionCount() + " minions, " + this.GetBuildingCount() + " buildings " + this.GetMoneyCount() + " Ankh-Morpork dollars");
		System.out.println();
		
		//If player has City Area Cards then print them
		if (this.ListCityAreaCards != null && this.ListCityAreaCards.size() > 0)
		{
			System.out.println("City Area Cards:");
			System.out.println(this.PrintCityAreaCards());
			System.out.println();
		}
		
		System.out.println("PlayerCards");
		System.out.println(this.PrintPlayerCards()[0]);
		System.out.println(this.PrintPlayerCards()[1]);

	}
	
	
	/**
	 * Get the color of the current player
	 * 
	 * @return Enum of type color
	 */
	public Colors GetColor()
	{
		return Color;
	}
	
	
	/**
	 * Add Minions back to the players minions
	 * 
	 * @param Minion Piece of type of minion
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
	 * @param Building Pieces of type Building
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

	/**
	 * Method to return list of cards.
	 * 
	 * @return
	 */
	public List<Cards> GetCityAreayCards()
	{
		return this.ListCityAreaCards;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return this.toString();
	}
	
	/**
	 * Method used to print a comma delimited string of all city area cards
	 * 
	 * @return Comma delimited string of city area card
	 */
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

	/**
	 * Method used to print a comma delimited string of all player cards
	 * 
	 * @return Comma delimited string of player cards
	 */
	private String[] PrintPlayerCards()
	{
		StringBuilder strGreenPlayerCards = new StringBuilder();
		StringBuilder strBrownPlayerCards = new StringBuilder();
		
		
		
		strGreenPlayerCards.append("Green ");
		strBrownPlayerCards.append("Brown ");
		
		for (Cards PlayerCard : this.PlayerCards)
		{
			if (PlayerCard.GetCardType() == CardType.BrownCards)
			{
				strBrownPlayerCards.append(PlayerCard.GetName());
				strBrownPlayerCards.append(",");
			}
			else if (PlayerCard.GetCardType() == CardType.GreenCards)
			{
				strGreenPlayerCards.append(PlayerCard.GetName());
				strGreenPlayerCards.append(",");
			}
		}
		
		//Remove Trailing comma from string builder
		strGreenPlayerCards.deleteCharAt(strGreenPlayerCards.length()-1);
		strGreenPlayerCards.deleteCharAt(strGreenPlayerCards.length()-1);
		
		String[] FinalString  = {strGreenPlayerCards.toString(),strBrownPlayerCards.toString()};
		
		return FinalString;
	}
	
	
	/**
	 * Player takes a loan from the bank, increment the loam amount
	 * @param amount
	 */
	public void GetLoan(int amount)
	{
		this.Loan = this.Loan + amount;
	}
	
	/**
	 * Player pays back all or part of the loan, decrease loan amount
	 * @param amount
	 */
	public void PayLoan(int amount)
	{
		this.Loan = this.Loan  - amount;
	}
	
	/**
	 * Retrieve total loans by player
	 * @return Loan amount
	 */
	public int TotalLoan()
	{
		return this.Loan;
	}
	
	/**
	 * 
	 * @return Total amount of money player has loan + bank
	 */
	public int TotalMoney()
	{
		int total = this.TotalLoan() + this.GetMoneyCount();
		
		return total; 
	}
	
	/**
	 * Add payback amount to player for loan cards
	 * @param amount
	 */
	public void AddtoPayBack(int amount)
	{
		this.PayBack += amount;
	}
	
	/**
	 * Increment points to lose if payback not paid
	 * @param points
	 */
	public void IncreaseLostPoints(int points)
	{
		this.LostPoints += points;
	}

	public void PrintCardsIndex()
	{
		StringBuilder strCards = new StringBuilder();
		
		for (int i = 0; i < this.PlayerCards.size(); i++)
		{
			strCards.append(i + ":" + this.PlayerCards.get(i).GetName() + "; ");
		}
		
		System.out.println(strCards.toString());
	}
	
}
