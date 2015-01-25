import java.util.List;



//dummy class
public class Player {
	private Card Personality;
	private Card[] PlayerCards;
	private int PlayerNumber;
	private int Money;
	List<String> ListMinion;
	List<String> ListBuilding;
	String Demon;
	
	public Player () {}
	
	public Player(Card _personality, int _money , List<String> lm, List<String> lb, String d){
		PlayerNumber = 1;
		Personality = _personality;
		Money = _money;
		ListMinion = lm;
		ListBuilding = lb;
		Demon = d;
	}
	
	/*
	 * Public Getter for Player Personality Card
	 * 
	 * @return Card personality card object
	 */
	public Card GetPlayerPersonality()
	{
		return this.Personality;
	}
	
	
	
	
	/*
	 * Public Getter for player money sum
	 * 
	 * @return player total money
	 */
	public int GetMoneyCount()
	{
		return this.Money;
	}
	
	/*
	 * Add to player bank roll
	 * 
	 * @param amount to add
	 */
	public void AddToMoney(int amount)
	{
		this.Money += amount;
		
	}
	
	/*
	 * Deduct from player bank roll
	 * 
	 * @param amount to deduct
	 */
	public void DeductFromMoney(int amount)
	{
		this.Money -= amount;
	
	}

	/*
	 * Get the current state of the player as a string
	 * 
	 * @return Current state
	 */
	public String GetPlayerState()
	{
		String playerState = "Number;"+Integer.toString(this.PlayerNumber) + 
							 "personality"+Integer.toString(this.Personality.GetCardID());
							 
		return playerState;
	}
}
