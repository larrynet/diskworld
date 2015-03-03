import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.Serializable;
import java.util.List;

/**
 * GameEngine is the class which the user will interact. It will call the other class according to user decisions inputted from the console or UI (later build). 
 * This class acts as a Facade to hide the internal structure to player. 
 * 
 * @author Lawrence
 * @version 2.0
 */
 
public class GameEngine implements Serializable
{
	private List<Player> ListPlayer;
	private ManageCards CardManager;
	private Board GameBoard;
	private int TotalPlayer;
	private int CurrentPlayer;
	private List<Cards> DiscardCards;
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
	 * Default constructor who will init all internal structure with player p
	 * 
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
	
	public boolean PlayCard(int CurrentPlayerIndex, int playChoice)
	{
		boolean isCardPlayed = false;
		
		//fetch card of playchoice
		Cards CardPlayed = ListPlayer.get(CurrentPlayerIndex).GetCards().get(playChoice);
		boolean ActionStatus = true;
		
        System.out.println("Player " + CurrentPlayerIndex + "decides to play " + CardPlayed.Name);
        
		//Execute the symbol of the
		for(int sIterator = 0; ActionStatus && (sIterator < CardPlayed.CardAction.Symbol.size()); sIterator++)
		{
			String currentSymbol = CardPlayed.CardAction.Symbol.get(sIterator);
			
			if(currentSymbol.compareToIgnoreCase("B") == 0)
				ActionStatus = PutBuilding(CurrentPlayerIndex);
			else if(currentSymbol.compareToIgnoreCase("M") == 0)
				ActionStatus = PutMinion(CurrentPlayerIndex);
			else if(currentSymbol.compareToIgnoreCase("A") == 0)
				ActionStatus = Assassinate(CurrentPlayerIndex);
			else if(currentSymbol.compareToIgnoreCase("RT") == 0)
				ActionStatus = RemoveTrouble(CurrentPlayerIndex);
			else if(currentSymbol.contains("T("))
				ActionStatus = PayPlayer(CurrentPlayerIndex, (int)currentSymbol.charAt(2));
			else if(currentSymbol.compareToIgnoreCase("RE") == 0)
				ActionStatus = PlayEvent(CurrentPlayerIndex);
			else if(currentSymbol.compareToIgnoreCase("C") == 0)
			{
				Scanner scan = new Scanner(System.in);
				System.out.println("Again? Which card you now want to play (1-5)?");
				int newCard = scan.nextInt();
				ActionStatus = PlayCard(CurrentPlayerIndex, newCard);
			}
			else if(currentSymbol.compareToIgnoreCase("I") == 0)
				System.out.println("You shouldn't have played this card because it doesn't do anything. Oh well too late now :)");
            else if(currentSymbol.compareToIgnoreCase("S") == 0)
                ActionStatus = PlayEffect(CardPlayed, CurrentPlayerIndex);
            else
                System.out.println("Symbol " + currentSymbol + " is invalid. ");
		}
		
		return ActionStatus;
	}

    private boolean PlayEffect(Cards CardPlayed, int player)
    {
    	boolean ActionStatus = false;
        Action currentEffect = CardPlayed.CardAction;
        
        //traverse the verb
        for(int verbCount=0; verbCount<currentEffect.Verb.size(); verbCount++)
        {
            //order of execution has to be reinforce when it is an AND
            if(currentEffect.Relation.compareToIgnoreCase("and") == 0)
            {
            	//TODO check condition
                if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("pay") ==0)
                {
                	String object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    if(object.contains("player"))
                    {
                    	//pay to each player?
                    	if(object.contains("each"))
                    	{
                    		int OtherPlayer = ListPlayer.size()-1;
                    		for(int i=0; i<ListPlayer.size(); i++)
                    		{
                    			if(i != player) {
                    				ListPlayer.get(i).AddToMoney(amount);
                    				ListPlayer.get(player).DeductFromMoney(amount);
                    			}
                    		}
                    		
                    	}
                    	else //if(object.contains("another"))//pay to a specific player
                    	{
                    		
                    		System.out.println("Please enter the Area index you want to remove the troublemaker.");
                            Scanner scan = new Scanner(System.in);
                            int otherPlayer = scan.nextInt();
                            ListPlayer.get(otherPlayer).AddToMoney(amount);
            				ListPlayer.get(player).DeductFromMoney(amount);
                    	}
                    }
                    else if(object.contains("bank"))
                    {
                    	ListPlayer.get(player).DeductFromMoney(amount);
                    	GameBoard.AddToBank(amount);
                    }
                    else if(object.contains("building"))
                    {
                    	//TODO - Reacher Gilt - calculate cost of building
                    	//ListPlayer.get(player).DeductFromMoney(amount);
                    	//GameBoard.AddToBank(amount);
                    }
                    else
                    {
                    	System.out.println("unknown object " + object);
                    }
    
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("give") ==0)
                {
                	String object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    
                    if(object.contains("cards"))
                    {
                    	
                    	//Moist Wong Lipwig
                    	Cards c1 = CardManager.GetCard(CardType.GreenCards);
                    	if(c1 == null)
                    		c1 = CardManager.GetCard(CardType.BrownCards);
                    	
                    	Cards c2 = CardManager.GetCard(CardType.GreenCards);
                    	if(c2 == null)
                    		c2 = CardManager.GetCard(CardType.BrownCards);
                    	
                    	ListPlayer.get(player).AddPlayerCard(c1);
                    	ListPlayer.get(player).AddPlayerCard(c2);
                    	
                    }
                    //Rosie Palm
                    else if(object.contains("card"))
                    {
                    	Scanner scan = new Scanner(System.in);
                    	System.out.println("Enter card index to give.");
                        int cardIndex= scan.nextInt();
                        
                        System.out.println("Enter another player index who you will exchange card for 2$");
                        int playerIndex= scan.nextInt();
                        
                        ListPlayer.get(playerIndex).AddPlayerCard(CardPlayed);
                        ListPlayer.get(player).RemovePlayerCard(cardIndex);
                    }
                    else //Hubert
                    {
                    	Scanner scan = new Scanner(System.in);
                    	System.out.println("Enter player who needs to give 3$.");
                        int Src= scan.nextInt();
                        
                        System.out.println("Enter another player who will receive 3$");
                        int Dst= scan.nextInt();
                        
                        ListPlayer.get(Dst).AddToMoney(3);
        				ListPlayer.get(Src).DeductFromMoney(3);
                        
                    }
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("take") ==0)
                {
                	String object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    
                    if(object.contains("cards"))
                    {
                    	//the beggars
                    	Scanner scan = new Scanner(System.in);

                    	System.out.println("Enter player index to take cards from.");
                        int playerIndex= scan.nextInt();
                        
                    	System.out.println("Enter card index0 to take.");
                        int cardIndex0= scan.nextInt();
                        
                        System.out.println("Enter card index1 to take.");
                        int cardIndex1= scan.nextInt();
                        Cards c1 = ListPlayer.get(playerIndex).GetCards().get(cardIndex0);
                        Cards c2 = ListPlayer.get(playerIndex).GetCards().get(cardIndex1);
                        ListPlayer.get(player).AddPlayerCard(c1);
                        ListPlayer.get(player).AddPlayerCard(c2);
                        ListPlayer.get(playerIndex).RemovePlayerCard(cardIndex0);
                        ListPlayer.get(playerIndex).RemovePlayerCard(cardIndex1);
                    }
                    else if(object.contains("1$ or card"))
                    {
                    	Scanner scan = new Scanner(System.in);
                    	//The Ankh Morpork Sunshine Dragon Sanctuary
                    	System.out.println("Enter player index to take card or 1$ from.");
                        int playerIndex= scan.nextInt();
                        
                        System.out.println("Give $ or card?");
                        String choice= scan.next();
                        if(choice.contains("$"))
                        {
                        	ListPlayer.get(player).AddToMoney(1);
            				ListPlayer.get(playerIndex).DeductFromMoney(1);
                        }
                        else
                        {
                        	System.out.println("Enter card index to take.");
                            int cardIndex0= scan.nextInt();
                            Cards c1 = ListPlayer.get(playerIndex).GetCards().get(cardIndex0);
                          
                            ListPlayer.get(player).AddPlayerCard(c1);
                            ListPlayer.get(playerIndex).RemovePlayerCard(cardIndex0);
                         
                        }
                        
                    }
                    else if(object.contains("$ from all"))
                    {
                    	//thief guild
                    }
                    
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("loan") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("get") ==0)
                {
                	
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("interrupt") ==0)
                {
                	//have to findout how to play Doctor Mossy Lawn
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("discard") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("remove") ==0)
                {}
                //Parinaz
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("draw") ==0)
                {}else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("withdraw") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("exchange") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("assassinate") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("roll") ==0)
                {}
                //TODO personality cards
                //else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("win") ==0)
                //{}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("move") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("remove") ==0)
                {}
                //Niloufar
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("place") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("play") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("choose") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("return") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("replace") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("stop") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("choose") ==0)
                {}
                //Gay
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("see") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("exchange") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("ignore") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("end") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("select") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("putminion") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("loan") ==0)
                {}
                else
                    System.out.println("!!!!!!! Unknown verb found !!!!!!!! : " + currentEffect.Verb.get(verbCount));
            }
            //order of execution not important. Can only execute one of them
            else 
            {
                //only the following cards should come here. We will treat them exceptionally
                
                //name=CMOT Dibbler**
                //verb=get; object=4$ bank; condition=dice>7 order=1;
                // verb=pay; object=2$ bank; condition=dice=1 OR verb=remove; object=1 minion;
                
                //name=The Fire Brigade
                //verb=get; object=5$ from a player OR verb=remove; object=building; symbol=S,C;
                
                //name=Dr Whiteface
                //verb=take; object=5$ OR verb=give; object=card to player hand; symbol=S,M;
                
            }
            
        }
        
        return ActionStatus;
    }
	private boolean PlayEvent(int player)
	{
		boolean ActionSuccess = false;
		System.out.println("Activating the event effect now");
        
        //TODO - add code to analyze the event action
        
		return ActionSuccess;
	}
	private boolean RemoveTrouble(int player)
	{
		boolean ActionSuccess = false;
		System.out.println("Please enter the Area index you want to remove the troublemaker.");
        Scanner scan = new Scanner(System.in);
        int AreaNumber = scan.nextInt();
        ActionSuccess = RemoveTrouble(AreaNumber);
		return ActionSuccess;
	}
	
	private boolean Assassinate(int player)
	{
		boolean ActionSuccess = false;
        boolean Continue = true;
        Scanner scan = new Scanner(System.in);
        do
        {
            System.out.println("You are about to remove one minion or troll or demon. Please enter the Area index you want to do that. ");
            
            int AreaNumber = scan.nextInt();
            System.out.println("Do you want to remove a troll, demon or minion?");
            String choice  = scan.next();
            if(choice.compareToIgnoreCase("demon") == 0)
            {
            	//To activate later
                //ActionSuccess = RemoveDemon( AreaNumber);
	
            }
            else if(choice.compareToIgnoreCase("troll") == 0)
            {
            	//to activate later
                //ActionSuccess = RemoveTroll( AreaNumber);
            }
            else if(choice.compareToIgnoreCase("minion") == 0)
            {
                System.out.println("Please enter the player you want to remove the index from: ");
                String PlayerIndex  = scan.next();
                //To activate later
                //ActionSuccess = RemoveMinion( AreaNumber, PlayerIndex);
            }
            else
            {
                System.out.println("Invalid choice:  " + choice+". Please try again. ");
            }
        } while(Continue);
		
		return ActionSuccess;
	}
	
	private boolean PutMinion(int player)
	{
		boolean ActionSuccess = false;
        System.out.println("Please enter the Area index you want to put your minion. Keep in mind that you must place minion in either an area that you already have a minion in or an adjacent area. ");
        Scanner scan = new Scanner(System.in);
        int AreaNumber = scan.nextInt();
		ActionSuccess =GameBoard.PlaceMinion(AreaNumber,ListPlayer.get(player));
		return ActionSuccess;
	}
	
	private boolean PutBuilding(int player)
	{
		boolean ActionSuccess = false;
        System.out.println("Please enter the Area index you want to put your building. Keep in mind that you cannot build in an area that already contains either a building or troublemaker. ");
        
        Scanner scan = new Scanner(System.in);
        int AreaNumber = scan.nextInt();
		ActionSuccess =GameBoard.PlaceBuilding(AreaNumber,ListPlayer.get(player));
		return ActionSuccess;
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
	 * Function will print the state of all the cards 
	 */
	public void GetCardStateOfPlayer()
	{
		CardManager.GetState();
	}
	/**
	 * @param PlayerIndex whom to pay
	 * @param amount to pay
	 * @param amount 
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
				ListPlayer.get(PlayerIndex-1).AddToMoney(amount);
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
			return GameBoard.PlaceMinion(AreaNumber, ListPlayer.get(player-1));
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
			return GameBoard.PlaceBuilding(AreaNumber, ListPlayer.get(player-1));
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
	 * Function will randomly pick a player to be the first one
	 */
	public void DetermineFirstPlayer()
	{
		int LastDieValue = 0;
		int NewDieValue = 0;
		for (int i = 0; i < this.ListPlayer.size(); i++)
		{
			do
			{
				NewDieValue = GameBoard.RollDie();
				
			}while(NewDieValue == LastDieValue);
			
			if (NewDieValue > LastDieValue)
			{
				this.CurrentPlayer = i;
				LastDieValue = NewDieValue;
			}
			
		}
	}
	
	/**
	 * @return current Player's turn
	 */
	public int GetCurrentPlayer()
	{
		return this.CurrentPlayer;
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
		
		//CurrentPLayer
		System.out.println("Current Player : Player " + (this.GetCurrentPlayer() + 1) );
		System.out.println();
		
		//Call player method to print player profile for each player
		for (Player player : this.ListPlayer)
		{
			player.PrintPlayerProfile();
		}
		
		System.out.println();
		GameBoard.PrintState();
		System.out.println();
		
		//Call player method to print player state
		for (Player player : this.ListPlayer)
		{
			player.GetPlayerState();
			System.out.println();
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
		CardManager = new ManageCards(TotalPlayer);
		GameBoard = new Board();
		
		for(int PlayerCount = 0; PlayerCount <TotalPlayer; PlayerCount++)
		{
			//Assign set of RandomCard to player
	        List<Cards> ListPlayerCards = new ArrayList<Cards>();
	        int PlayerIndex = ListPlayer.size()+1;
	        
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
	        
	        ListPlayer.add(new Player(PlayerIndex, PlayerPersonality, PlayerColor, ListPlayerCards, ListMinions, ListBuildings));
	        GameBoard.DeductFromBank(10);
	        
	        //each player should place one of their minions in the Shades, The Scours, and Dolly Sisters
	        Player p = ListPlayer.get(PlayerCount);
	        GameBoard.PlaceMinion(1, ListPlayer.get(PlayerCount)); //dolly sister index
	        GameBoard.PlaceMinion(5, ListPlayer.get(PlayerCount)); //The Scouts index
	        GameBoard.PlaceMinion(7, ListPlayer.get(PlayerCount)); //The Shades index
	        GameBoard.PlaceTroubleMarker(1);
	        GameBoard.PlaceTroubleMarker(5);
	        GameBoard.PlaceTroubleMarker(7);
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
	/**
	 * @return true if the current player meet its required winning conditons.
	 */
	public boolean IsWinner()
	{
		boolean WiningCondition=false;
	//Lord de Word Lord=1 , Salachiim=2 , Lord Rust=5 should have certain number of city area under control
		
		if (this.CurrentPlayer==1 || this.CurrentPlayer==4 || this.CurrentPlayer==5)
		
		{
		if (this.TotalPlayer==2)
		{
			if ( (ListPlayer.get(this.CurrentPlayer)).GetCityAreayCards().size()>=7 )
			{
				
				 WiningCondition=true;
			}	
		}
		else if (this.TotalPlayer==3)
		{
			if ( (ListPlayer.get(this.CurrentPlayer)).GetCityAreayCards().size()>=5 )
			{
				
				 WiningCondition=true;
			}	
		}
		else if (this.TotalPlayer==4)
			if ( (ListPlayer.get(this.CurrentPlayer)).GetCityAreayCards().size()>=4 )
			{
				
				 WiningCondition=true;
			}	
		}
		//Lord Vetinari should have certain number of minions to win the game
		else if (this.CurrentPlayer==3)
		{
			if (this.TotalPlayer==2)
			{
				if (ListPlayer.get(this.CurrentPlayer).GetMinionCount()>=11)
					 WiningCondition=true;
			}
			else if (this.TotalPlayer==3)
			{
				if (ListPlayer.get(this.CurrentPlayer).GetMinionCount()>=10)
					 WiningCondition=true;
			}
			else if (this.TotalPlayer==4)
			{
				if (ListPlayer.get(this.CurrentPlayer).GetMinionCount()>=9)
					 WiningCondition=true;
			}
		}
		//Dragon King of Arms  
		else if (this.CurrentPlayer==6)
		{
			if (this.GameBoard.CountTroubleMarker()>=8 )
				 WiningCondition=true;
		}
		//Commandor Vimes .If cards run out he whill be the winner
		else if (this.CurrentPlayer==2)
		{// GreenCard=48 +BrownCards =53 
			if (this.DiscardCards.size()==101)
				 WiningCondition=true;
		}
		//Chrysoprase
		else if (this.CurrentPlayer==7)
		{
			if (ListPlayer.get(this.CurrentPlayer).GetMoneyCount()>=50 )//loan and building cost should be considered later
				WiningCondition=true;
		}
	
		
	
	
		return WiningCondition;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return this.toString();
	}
}