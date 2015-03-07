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
	public List<Player> ListPlayer;
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
	
	public void ActivateCityAreaEffect(int player)
    {
        //traverse city area list and activate all the effect only if there is no demon
        for(int i=0; i<ListPlayer.get(player).CityAreaCards.size(); i++)
        {
            //check if the area is occupied with a demon
            Area CityArea = GameBoard.ListArea.get(ListPlayer.get(player).ListCityAreaCards.get(i).GetID());
            		
            boolean HasDemon = (CityArea.ListDemons.size()>0);
            Scanner scan = new Scanner(System.in);
            if(!HasDemon)
            {
                if(CityArea.Name.compareToIgnoreCase("The Scours") == 0)
                {
                    System.out.println("Do you want to play the effect of <The Scours> (exchange a card for 2$) (yes or no)?");
                    String answer = scan.next();
                    if(answer.compareToIgnoreCase("yes") == 0)
                    {
                        System.out.println("Enter card index to discard");
                        int cIndex = scan.nextInt();
                        ListPlayer.get(player).PlayerCards.remove(cIndex);
                        ListPlayer.get(player).AddToMoney(2);
                        GameBoard.DeductFromBank(2);
                    }
                }
                else if(CityArea.Name.compareToIgnoreCase("Small Gods") == 0)
                {
                    //defense effect
                	System.out.println("Player has the Small Gods city area cards but it has no effect per turn");
                }
                else if(CityArea.Name.contains("Landing"))
                {
                    System.out.println("Do you want to play the effect of <Dragon's Landing> (get 2$ from bank) (yes or no)?");
                    String answer = scan.next();
                    if(answer.compareToIgnoreCase("yes") == 0)
                    {
                        ListPlayer.get(player).AddToMoney(2);
                        GameBoard.DeductFromBank(2);
                    }
                }
                else if(CityArea.Name.compareToIgnoreCase("Unreal Estate") == 0)
                {
                	System.out.println("Do you want to play the effect of <Unreal Estate> (Discard a card and fetch one) (yes or no)?");
                    String answer = scan.next();
                    if(answer.compareToIgnoreCase("yes") == 0)
                    {
                    	System.out.println("Enter card index to discard");
                        int cIndex = scan.nextInt();
                        ListPlayer.get(player).PlayerCards.remove(cIndex);
                        
                        //fetch a card
                        Cards c = CardManager.GetCard(CardType.GreenCards);
                        if(c == null)
                        	 c = CardManager.GetCard(CardType.BrownCards);
                        ListPlayer.get(player).PlayerCards.add(c);
                    }
                }
                else if(CityArea.Name.compareToIgnoreCase("Seven Sleepers") == 0)
                {
                     System.out.println("Do you want to play the effect of <Seven Sleepers> (get 3$ from bank) (yes or no)?");
                    String answer = scan.next();
                    if(answer.compareToIgnoreCase("yes") == 0)
                    {
                        ListPlayer.get(player).AddToMoney(3);
                        GameBoard.DeductFromBank(3);
                    }
                }
                else if(CityArea.Name.compareToIgnoreCase("Isle of Gods") == 0)
                {
                	System.out.println("Do you want to play the effect of <Isle of Gods> (Pay 2$ to remove a troublemaker) (yes or no)?");
                    String answer = scan.next();
                    if(answer.compareToIgnoreCase("yes") == 0)
                    {
                    	System.out.println("Enter area index to remove troublemaker");
                        int Area = scan.nextInt();
                 
                        ListPlayer.get(player).DeductFromMoney(2);
                        GameBoard.AddToBank(2);
                        
                        //remove troublemaker
                        GameBoard.Removetrouble(Area);
                        
                    }
                }
                else if(CityArea.Name.compareToIgnoreCase("Longwall") == 0)
                {
                    System.out.println("Do you want to play the effect of <Longwall> (get 1$ from bank) (yes or no)?");
                    String answer = scan.next();
                    if(answer.compareToIgnoreCase("yes") == 0)
                    {
                        ListPlayer.get(player).AddToMoney(1);
                        GameBoard.DeductFromBank(1);
                    }
                }
                else if(CityArea.Name.compareToIgnoreCase("Dimwell") == 0)
                {
                	System.out.println("Do you want to play the effect of <Dimwell> (pay 3$ to place minion in Dimwell or adjacent area) (yes or no)?");
                    String answer = scan.next();
                    if(answer.compareToIgnoreCase("yes") == 0)
                    {
                    	System.out.println("Enter the area index where you want to put minion. Dimwell index is 8, Longwall index is 9 and the Shades index is 7.");
                    	int Area = scan.nextInt();
                    	GameBoard.PlaceMinion(Area, ListPlayer.get(player));
                    }
                }
                else if(CityArea.Name.compareToIgnoreCase("The Hippo") == 0)
                {
                	System.out.println("Do you want to play the effect of <The Hippo> (get 2$ from bank) (yes or no)?");
                    String answer = scan.next();
                    if(answer.compareToIgnoreCase("yes") == 0)
                    {
                        ListPlayer.get(player).AddToMoney(2);
                        GameBoard.DeductFromBank(2);
                    }
                }
                else if(CityArea.Name.compareToIgnoreCase("Nap Hill") == 0)
                {
                	System.out.println("Do you want to play the effect of <Nap Hill> (get 1$ from bank) (yes or no)?");
                    String answer = scan.next();
                    if(answer.compareToIgnoreCase("yes") == 0)
                    {
                        ListPlayer.get(player).AddToMoney(1);
                        GameBoard.DeductFromBank(1);
                    }
                }
                else if(CityArea.Name.compareToIgnoreCase("Dolly Sisters") == 0)
                {
                	System.out.println("Do you want to play the effect of <Dolly Sisters> (pay 3$ to place minion in Dolly Sisters or adjacent area) (yes or no)?");
                    String answer = scan.next();
                    if(answer.compareToIgnoreCase("yes") == 0)
                    {
                    	System.out.println("Enter the area index where you want to put minion. Dolly Sisters index is 1, Unreal Estate index is 2 and Nap Hill index is 12.");
                    	int Area = scan.nextInt();
                    	GameBoard.PlaceMinion(Area, ListPlayer.get(player));
                    }
                }
                else if(CityArea.Name.compareToIgnoreCase("The Shades") == 0)
                {
                	System.out.println("Do you want to play the effect of <The Shades> (Place troublemaker in The Shades or adjacent area) (yes or no)?");
                    String answer = scan.next();
                    if(answer.compareToIgnoreCase("yes") == 0)
                    {
                    	System.out.println("Enter the area index where you want to put minion. The Shades index is 7, Dimwell index is 8 and The Hippo index is 6.");
                    	int Area = scan.nextInt();
                    	GameBoard.PlaceTroubleMarker(Area);
                    }
                }
            }
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
		
		GreenCards g = null;
		BrownCards b = null;
		List<String> lstSymbols = null;
		
		//fetch card of playchoice
		Cards CardPlayed = (GreenCards)ListPlayer.get(CurrentPlayerIndex).GetCards().get(playChoice);
		boolean ActionStatus = true;
		
		if (CardPlayed.GetCardType() == CardType.GreenCards)
		{
			g = (GreenCards)CardPlayed;
			lstSymbols = g.GetSymbol();
		}
		else if ((CardPlayed.GetCardType() == CardType.BrownCards))
		{
			b = (BrownCards)CardPlayed;
			lstSymbols = b.GetSymbol();
		}
		
        System.out.println("Player " + CurrentPlayerIndex + " decides to play " + CardPlayed.GetName());
        
		//Execute the symbol of the
		for(int sIterator = 0; ActionStatus && (sIterator < lstSymbols.size()); sIterator++)
		{
			String currentSymbol = lstSymbols.get(sIterator);
			
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
				ActionStatus = PlayEvent(CardPlayed, CurrentPlayerIndex);
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

	private boolean BelongToException(Cards c)
    {
        boolean b1 = c.GetName().contains("CMOT Dibbler");
        boolean b2 = c.GetName().contains("The Fire Brigade");
        boolean b3 = c.GetName().contains("Whiteface");
        boolean b4 = c.GetName().contains("Errol");
        boolean b5 = c.GetName().contains("HERE ");
        
        return (b1 && b2 &&b3 &&b4 && b5);
    }
	
    private boolean PlayEffect(Cards CardPlayed, int player)
    {
    	boolean ActionStatus = false;
        Action currentEffect = null;
        GreenCards g = null;
        BrownCards b = null;
        
        if (CardPlayed.GetCardType() == CardType.GreenCards)
        {
        	g = (GreenCards)CardPlayed;
        	currentEffect = g.GetAction(1);
        }
        else if (CardPlayed.GetCardType() == CardType.BrownCards)
        {
        	b = (BrownCards)CardPlayed;
        	currentEffect = b.GetAction(1);
        }
        
        
        //traverse the verb
        for(int verbCount=0; verbCount<currentEffect.Verb.size(); verbCount++)
        {
            //order of execution has to be reinforce when it is an AND
        	if(!BelongToException(CardPlayed))
            //if(currentEffect.Relation.compareToIgnoreCase("and") == 0)
            {
            	//TODO check condition
                if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("pay") ==0)
                {
                	String object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    
                    if(object.contains("another player and have them remove 1 minion with troublemaker"))
                    {
                    	//cosmos lavish
                    }
                    
                    else if(object.contains("player"))
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
                    	for(int i=0; i<ListPlayer.size(); i++)
                		{
                			if(i != player) {
                				ListPlayer.get(i).AddToMoney(amount);
                				ListPlayer.get(player).DeductFromMoney(amount);
                			}
                		}
                    }
                    
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("interrupt") ==0)
                {
                	//have to findout how to play Doctor Mossy Lawn
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("loan") ==0)
                {
                    //have to find out how to do the loan
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("get") ==0)
                {
                	String object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    
                    if(object.contains("minion in the Isle of Gods"))
                    {
                        int TotalMinionInArea = 0;
                    	//The dysk & the opera house
                    	//count minion in Isle of Gods
                        for(int a=0; a<GameBoard.ListArea.size(); a++)
                        {
                            if(GameBoard.ListArea.get(a).Name.contains("in Isle of Gods"))
                                TotalMinionInArea = GameBoard.ListArea.get(a).ListMinions.size();
                        }
                        GameBoard.DeductFromBank(amount*TotalMinionInArea);
                		ListPlayer.get(player).AddToMoney(amount*TotalMinionInArea);
            
                    }
                    else if(object.contains("times number of discarded card"))
                    {
                    	//Harry King
                    	//Shonky shop
                        GameBoard.DeductFromBank(amount*DiscardCards.size());
                		ListPlayer.get(player).AddToMoney(amount*DiscardCards.size());
                    }
                    else if(object.contains("for each troublemaker in board"))
                    {
                    	//Willian de worde
                        ////Sacharissa Cripslock
                        int TotalTroubleMaker = 0;
                    	
                        for(int a=0; a<GameBoard.ListArea.size(); a++)
                        {
                            if(GameBoard.ListArea.get(a).HasTroubleMaker())
                                TotalTroubleMaker++;
                        }
                        GameBoard.DeductFromBank(amount*TotalTroubleMaker);
                		ListPlayer.get(player).AddToMoney(amount*TotalTroubleMaker);
                    	
                    }
                    else if(object.contains("for each building on board"))
                    {
                        //The Post office
                    	//Willian de worde
                        int TotalBuilding =0;
                    	for(int a=0; a<GameBoard.ListArea.size(); a++)
                    	{
                    		if(ListPlayer.get(player).GetColor() == GameBoard.ListArea.get(a).GetBuilding().GetPieceColor())
                    		{
                    			TotalBuilding++;
                    		}
                    		
                    	}

                        GameBoard.DeductFromBank(amount*TotalBuilding);
                		ListPlayer.get(player).AddToMoney(amount*TotalBuilding);
                    	
                    }
                    else if(object.contains("$ from a player of choice"))
                    {
                    	Scanner scan = new Scanner(System.in);
                    	//Nobby Nobbs
                        System.out.println("Enter player to get money from.");
                        int PlayerIndex = scan.nextInt();
                        ListPlayer.get(player).AddToMoney(amount);
                		ListPlayer.get(PlayerIndex).DeductFromMoney(amount);
                    }
                    else if(object.contains(" for each minion in area with troublemaker"))
                    {
                    	//mr slant
                    	//Otto Shriek
                         int TotalMinionInArea = 0;
                    	//The dysk & the opera house
                    	//count minion in Isle of Gods
                        for(int a=0; a<GameBoard.ListArea.size(); a++)
                        {
                            if(GameBoard.ListArea.get(a).HasTroubleMaker())
                                TotalMinionInArea += GameBoard.ListArea.get(a).ListMinions.size();
                        }
                        GameBoard.DeductFromBank(amount*TotalMinionInArea);
                		ListPlayer.get(player).AddToMoney(amount*TotalMinionInArea);
                    }
                    else if(object.contains("cards of other player and give back 1"))
                    {
                    	//stanley pick two cards randomly and randomly select 1
                        System.out.println("Select a player to get cards from. ");
                        Scanner scan = new Scanner(System.in);
                        int PlayerIndex = scan.nextInt();
                        int [] PlayerChoice = new int[2];
                        
                        System.out.println("Enter card index 0 to take from player:");
                        PlayerChoice[0] = scan.nextInt();
                        
                        System.out.println("Enter card index 1 to take from player:");
                        PlayerChoice[1] = scan.nextInt();
                        
                        Random r = new Random();
                        int RandomIndex = r.nextInt()%2;
                        System.out.println("God of random selected index # " + PlayerChoice[RandomIndex]);
                        
                        ListPlayer.get(player).PlayerCards.add(ListPlayer.get(PlayerIndex).PlayerCards.get(PlayerChoice[RandomIndex]));
                        ListPlayer.get(PlayerIndex).PlayerCards.remove(PlayerChoice[RandomIndex]);
                        
                    }
                    else if(object.contains("cards from a player"))
                    {
                    	Scanner scan = new Scanner(System.in);
                    	
                    	//Queen molly (selected player)
                        System.out.println("Select a player to get cards from. ");
                        int PlayerIndex = scan.nextInt();
                        
                        System.out.println("Player " + PlayerIndex + ": Enter card index 0 you are willing to give up:");
                        int CardIndex0 = scan.nextInt();
                        
                        System.out.println("Player " + PlayerIndex + ": Enter card index 1 you are willing to give up:");
                        int CardIndex1 = scan.nextInt();
                        
                        ListPlayer.get(player).PlayerCards.add(ListPlayer.get(PlayerIndex).PlayerCards.get(CardIndex0));
                        ListPlayer.get(player).PlayerCards.add(ListPlayer.get(PlayerIndex).PlayerCards.get(CardIndex1));
                        ListPlayer.get(PlayerIndex).PlayerCards.remove(CardIndex0);
                        ListPlayer.get(PlayerIndex).PlayerCards.remove(CardIndex1);
                    }
                    else if(object.contains("cards"))
                    {
                    	//Leonard of Quirm
                    	//the clacks
                    	//professor of recent runes
                    	//Sergeant Cheery Littlebottom
                        List<Cards> DrawCardList = new ArrayList<Cards>();
                        for(int i=0; i<amount; i++)
                        {
                            Cards c = CardManager.GetCard(CardType.GreenCards);
                            if(c==null) c=CardManager.GetCard(CardType.BrownCards);
                            ListPlayer.get(player).PlayerCards.add(c);
                        }
                    	
                    }
                    
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("discard") ==0)
                {
                	String object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    
                    
                    if(object.contains("up to 3 cards and fill hands"))
                    {
                    	//alchemist guild
                    	Scanner scan = new Scanner(System.in);
                    	
                        System.out.println("Enter number of cards you want to discard (1-3)");
                        int CardToTake = scan.nextInt();
                        
                        for(int i=0; i<CardToTake; i++)
                        {
                            System.out.println("Enter card index "+i+": you are willing to give up:");
                            int CardIndex = scan.nextInt();
                            ListPlayer.get(player).PlayerCards.remove(CardIndex);
                        }
                        for(int j=0; j<(5-ListPlayer.get(player).PlayerCards.size()); j++)
                        {
                            Cards c = CardManager.GetCard(CardType.GreenCards);
                            if(c==null) c=CardManager.GetCard(CardType.BrownCards);
                            ListPlayer.get(player).PlayerCards.add(c);
                        }
                     
                    }
                    else if(object.contains("card player card from a other hand"))
                    {
                    	//Cable Street Particular
                    	Scanner scan = new Scanner(System.in);
                        System.out.println("Enter player index you want to peek and discard");
                        int PlayerIndex = scan.nextInt();
                        
                        //TODO: show card maybe
                        System.out.println("Enter card index you want to discard");
                        int CardToDiscard = scan.nextInt();
                        ListPlayer.get(PlayerIndex).PlayerCards.remove(CardToDiscard);
                    }
                    else if(object.contains("card"))
                    {
                    	///modo
                    	//The Mob
                    	Scanner scan = new Scanner(System.in);
                        for(int i=0; i<amount; i++)
                        {  
                            System.out.println("Enter card index you want to discard");
                            int CardToDiscard = scan.nextInt();
                            ListPlayer.get(player).PlayerCards.remove(CardToDiscard);
                        }
                        
                    } 
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("remove") ==0)
                {
                	String object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    Scanner scan = new Scanner(System.in);
                    if(object.contains("minion in player order"))
                    {
                    	//The Auditors
                        for(int i=0; i<TotalPlayer; i++)
                        {
                            if(i!=player)
                            {
                                System.out.println("Enter area index you want to remove minion");
                                int Area = scan.nextInt();
                                GameBoard.RemoveMinion(Area, ListPlayer.get(i).GetColor());
                            }
                        }
                    }
                    else if(object.contains("minion of choice in that area and roll dice twice"))
                    {
                 
                        //Carcer
                        int RollDieValue0 = GameBoard.RollDie();
                        int RollDieValue1 = GameBoard.RollDie();
                        
                        System.out.println("Enter Player index to remove minion from for area "+RollDieValue0 +" : ");
                        int PlayerIndex0 = scan.nextInt();
                        System.out.println("Enter Player index to remove minion from for area "+RollDieValue1 +" : ");
                        int PlayerIndex1 = scan.nextInt();
                    	GameBoard.RemoveMinion(RollDieValue0, ListPlayer.get(PlayerIndex0).GetColor());
                        GameBoard.RemoveMinion(RollDieValue1, ListPlayer.get(PlayerIndex1).GetColor());
                    }
                }
                
                
                // ***************** PARINAZ SECTION ***********************************
                
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("draw") ==0)
                {
                	//verb=shuffle; object=discard cards; actionnumber=1;
                	//verb=draw; object=4cards from discard cards; symbol=S,M; 
                	String object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    
                    if(object.contains("discard"))
                    {
                    	Cards [] c = new Cards[4];
                    	for(int i=0; i<4; i++)
                    	   ListPlayer.get(player).PlayerCards.add(this.DiscardCards.get(i));
                    	
                    }
                    else if(object.contains("building"))
                    {
                    	int NumBuilding =0;
                    	for(int a=0; a<GameBoard.ListArea.size(); a++)
                    	{
                    		if(ListPlayer.get(player).GetColor() == GameBoard.ListArea.get(a).GetBuilding().GetPieceColor())
                    		{
                    			NumBuilding++;
                    		}
                    		
                    	}
                    	for(int DrawCount=0; DrawCount < NumBuilding; DrawCount++)
                    	{
                    		Cards c = CardManager.GetCard(CardType.GreenCards);
                    		if(c==null) c = CardManager.GetCard(CardType.GreenCards);
                    		ListPlayer.get(DrawCount).AddPlayerCard(c);
                    	}
                    }
                    else if(object.contains("cards"))
                    {
                         	
                     	for(int DrawCount=0; DrawCount < amount; DrawCount++)
                     	{
                     		Cards c = CardManager.GetCard(CardType.GreenCards);
                     		if(c==null) c = CardManager.GetCard(CardType.GreenCards);
                     		ListPlayer.get(DrawCount).AddPlayerCard(c);
                     	}
                    }
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("withdraw") ==0)
                {
                	//no verb found
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("exchange") ==0)
                {  
                	String object = currentEffect.Object.get(verbCount);
                    //int amount = (int)object.charAt(0);
                    
                	
                	//Zorgo the Retro-phrenologist
                    if(object.contains("personality"))
                    {
                    	
                    	//I get a personlaity card and set player personality card
                    	   ListPlayer.get(player).SetPlayerPersonality(CardManager.GetCard(CardType.PersonalityCards));
                    
                    	
                    }//The Bursar
                    else if(object.contains("minion"))
                    {
                    Scanner scan = new Scanner(System.in);
                	
                    System.out.println("Enter the player index you want to move his minion.");
                    int PlayerIndex = scan.nextInt();
                    System.out.println("Enter area index from which u want to move his minion from:");
                    int area = scan.nextInt();
                                        
                    GameBoard.RemoveMinion(area, ListPlayer.get(PlayerIndex).GetColor());
                    
                    System.out.println("Enter the player index you want to move his minion.");
                    int PlayerIndex2 = scan.nextInt();
                    System.out.println("Enter area index to which you want to move his minion:");
                    int area2 = scan.nextInt();
                    
                    GameBoard.RemoveMinion(area2, ListPlayer.get(PlayerIndex2).GetColor());
                    GameBoard.PlaceMinion(area2, ListPlayer.get(PlayerIndex));
                    GameBoard.RemoveMinion(area, ListPlayer.get(PlayerIndex2).GetColor());
                    }
                    //The Chair of Indefinite Studies
                    else if(object.contains("cards"))
                    {   Scanner scan = new Scanner(System.in);
                    	List<Cards> hand=new ArrayList<Cards>();
               
                    	System.out.println("Enter the player index you want to change your hand with");
                        int PlayerIndex = scan.nextInt();
                        
                        hand=ListPlayer.get(CurrentPlayer).PlayerCards;
                        ListPlayer.get(CurrentPlayer).PlayerCards=ListPlayer.get(PlayerIndex).PlayerCards;
                        ListPlayer.get(PlayerIndex).PlayerCards=hand;
                    }
                  
                }
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("assassinate") ==0)
                {
                	String object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    
                	
                	//Burleigh & Stronginth
                    if(object.contains("minion"))
                    {
                    	
                    	Scanner scan = new Scanner(System.in);
                    	
                        System.out.println("Enter the player index you want to assassinate his minion.");
                        int PlayerIndex = scan.nextInt();
                        System.out.println("Enter area index ");
                        int area = scan.nextInt();
                                                               
                        GameBoard.RemoveMinion(area, ListPlayer.get(PlayerIndex).GetColor());
                    	
                    }
                	
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("roll") ==0)
                {
                	String object = currentEffect.Object.get(verbCount);
                	// int amount = (int)object.charAt(0);
                
                 //HERE �N� NOW - //Carcer  -//Eroll     
                if(object.contains("die"))
                { 
                	int amount = (int)object.charAt(0);
                	int[] roll=new int[amount];
             
                for(int i=0;i<amount;i++)
            	{
                	roll[i]=GameBoard.RollDie();
            		System.out.println("Die shows "+ roll[i]);
            	
                    if(CardPlayed.GetName()=="Errol" && roll[i]>=7)
                    {
                    	Scanner scan = new Scanner(System.in);
                    	
                        System.out.println("Enter the player index you want to remove his minion from an area containing a troubleMarker.");
                        int PlayerIndex = scan.nextInt();
                        System.out.println("Enter area index ");
                        int area = scan.nextInt();
                        
                        if(GameBoard.ListArea.get(area).HasTroubleMaker())
                        {                                   
                        GameBoard.RemoveMinion(area, ListPlayer.get(PlayerIndex).GetColor());
                        }
                    }
                    else if(CardPlayed.GetName()=="Errol" && roll[i]==1)
                    {   
                    	Scanner scan = new Scanner(System.in);
                    	System.out.println("Enter area index where your minion should be removed ");
                        int area = scan.nextInt();
                    	GameBoard.RemoveMinion(area, ListPlayer.get(CurrentPlayer).GetColor());
                    }//CMOT Dibbler
                    else if(CardPlayed.GetName()=="CMOT Dibbler" && roll[i]>=7)
                    {   
                    	ListPlayer.get(CurrentPlayer).AddToMoney(4);
                    	GameBoard.DeductFromBank(4);
                    	
                    }
                    
                    else if(CardPlayed.GetName()=="CMOT Dibbler" && roll[i]==1)
                    {   
                    	Scanner scan = new Scanner(System.in);
                    	System.out.println("Enter 1 to pay 2$ to bank or 2 to remove your minion");
                    	int choice = scan.nextInt();
                    	
                    	if(choice==1)
                    	{
	                    	ListPlayer.get(CurrentPlayer).DeductFromMoney(2);
	                    	GameBoard.AddToBank(2);
                    	}
                    	
                    	else if(choice==2)
                    	{
	                    	System.out.println("Enter area index where your minion should be removed ");
	                        int area = scan.nextInt();
	                    	GameBoard.RemoveMinion(area, ListPlayer.get(CurrentPlayer).GetColor());
                    	}
                    }
            	}
                //Carcer
                if(CardPlayed.GetName()=="Carcer" )
                {	
                	
                	Scanner scan = new Scanner(System.in);
            	
                    //int RollDieValue1 = GameBoard.RollDie();
                    
                    System.out.println("Enter Player index to remove minion from area "+roll[0] +" : ");
                    int PlayerIndex0 = scan.nextInt();
                    System.out.println("Enter Player index to remove minion from area "+roll[1] +" : ");
                    int PlayerIndex1 = scan.nextInt();
                	GameBoard.RemoveMinion(roll[0], ListPlayer.get(PlayerIndex0).GetColor());
                    GameBoard.RemoveMinion(roll[1], ListPlayer.get(PlayerIndex1).GetColor());
                }
                }
                //TODO personality cards
                //else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("win") ==0)
                //{}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("move") ==0)
                {
                	String object1 = currentEffect.Object.get(verbCount);
                     int amount = (int)object1.charAt(0);
                     
                     //The Duckman -//Foul Ole Ron--//Canting Crew
                     if(object.contains("minion"))
                     { 
                     	     Scanner scan = new Scanner(System.in);
                         	
                             System.out.println("Enter the player index you want to move his minion ");
                             int PlayerIndex = scan.nextInt();
                             System.out.println("Enter area index from where you want to move minion");
                             int Source = scan.nextInt();
                             System.out.println("Enter area index to where you want to move minion-it should be adjacent");
                             int Destination = scan.nextInt();
                             
                             if(CardPlayed.GetName()=="The Duckman" || CardPlayed.GetName()=="Foul Ole Ron" ||CardPlayed.GetName()=="Canting Crew")
                             {
                            	 //TODO Review with Parinaz
                            	 if (GameBoard.ListArea.get(1).AreaAdjacency(Source,Destination))
                            	 {
                            		 GameBoard.RemoveMinion(Source,ListPlayer.get(PlayerIndex).GetColor()) ;
                            		 GameBoard.PlaceMinion(Destination, ListPlayer.get(PlayerIndex));
                            	 }//Rincewind
                             }else if(CardPlayed.GetName()=="Rincewind")
                             {
                            // Scanner scan = new Scanner(System.in);
                         	
                             System.out.println("Enter area index from where you want to move minion-it should have troubleMarker");
                             int source = scan.nextInt();
                             System.out.println("Enter area index to where you want to move minion-it should be adjacent");
                             int destination = scan.nextInt();
                            
                             //TODO Review with Parinaz
                             if(GameBoard.GetArea(source).HasTroubleMaker() && GameBoard.ListArea.get(1).AreaAdjacency(source,destination))
                             {
                            	 GameBoard.RemoveMinion(source,ListPlayer.get(CurrentPlayer).GetColor()) ;
                        		 GameBoard.PlaceMinion(destination, ListPlayer.get(CurrentPlayer)); 
                             }//Dorfl--//Hobsons's Livery Stable
                             }else if(CardPlayed.GetName()=="Dorfl" || CardPlayed.GetName()=="Hobsons's Livery Stable")
                             {
                            // Scanner scan = new Scanner(System.in);
                         	
                             System.out.println("Enter area index from where you want to move minion");
                             int _Source = scan.nextInt();
                             System.out.println("Enter area index to where you want to move minion");
                             int _Destination = scan.nextInt();
                             
                             GameBoard.RemoveMinion(_Source,ListPlayer.get(CurrentPlayer).GetColor()) ;
                        	 GameBoard.PlaceMinion(_Destination, ListPlayer.get(CurrentPlayer)); 
                             }
                             }
                             
                         }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("remove") ==0)
                {
                	object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    
                    
                  //CMOT Dibbler-//The Fire Brigade
                    if(object.contains("building")  )
                    { 
                    	    Scanner scan = new Scanner(System.in);
                        	
                            System.out.println("Enter the player index you want to move his building ");
                            int PlayerIndex = scan.nextInt();
                            System.out.println("Enter area index from where you want to move building");
                            int area = scan.nextInt();
                            GameBoard.RemoveBuilding(area, ListPlayer.get(PlayerIndex));
                            
                    }//HERE �N� NOW
                    else if (object.contains("minion") && CardPlayed.GetName()=="HERE �N� NOW"  )
                    {
                    	Scanner scan = new Scanner(System.in);
                	
                    	System.out.println("Enter area index from where you want to move building");
                    	int area = scan.nextInt();
                    	//TODO Review with Parinaz
                    	GameBoard.RemoveMinion(area, ListPlayer.get(player).GetColor());
                    }
                    //The Dean
                     else if (object.contains("minion") && CardPlayed.GetName()=="The Dean"  )
                	{
                    	 Scanner scan = new Scanner(System.in);
                     	//unreal state is area number 2
                         System.out.println("Enter the player index you want to move his building ");
                         int PlayerIndex = scan.nextInt();
                		 GameBoard.RemoveMinion(1, ListPlayer.get(PlayerIndex).GetColor());
                	}
                    //The Auditors
                    else if (object.contains("player order") && CardPlayed.GetName()=="The Auditors"  )
                    {
                    	 	Player _CurrentPlayer=ListPlayer.get(player);
                    	 	
                    	    for (Player thisPlayer : this.ListPlayer)
                    	 	{
                    	 		if (thisPlayer !=_CurrentPlayer)
                    	 		{
                    	 			Scanner scan = new Scanner(System.in);
                    	 			System.out.println("Enter area number from where you want to remove Minion");
                    	 			int area = scan.nextInt();
                    	 			GameBoard.RemoveMinion(area, thisPlayer.GetColor());
                    	 		}
                    	 
                    	 	}
                    }
                    
                }
        	
        		// ***************** END PARINAZ SECTION *******************************
        	
                // ***************** NILOUFAR SECTION **********************************
              
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("place") ==0)
                {
                	object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    
                	// In "dorfi", "Adora Bell Dearheart" Place is the second verb????
                    //move one minion your own minion from an area to another area
                    
                  //Deep Dwarves ,Mr Shine
                	if (object.contains("1 minion in any area" ))
        			{
			        		//place a minion in any area withount puting trouble marker
			        		System.out.println("Please select a area to put a Minion In.");
			        		boolean IsMinionIn=PutMinion(CurrentPlayer);
			        		if( !IsMinionIn)
			        		{
			        			//throw exceptions;
			        			System.out.println("!!!!!IsMinionIn is TRUE...WHAT THE HELL!!!!!!!");
			        		}	
        			}
                	//Willikins
                	else if (object.contains("1 minion in area with building in"  ))
                	{
                		System.out.println("Please select a area to put a Minion In.");
                	}
                	//Archchancellor Ridcully
                	else if (object.contains("1 or 2 minion in or adjacent to Unreal Estate"  ))
                	{
                		
                	}
                	//The Senior Wrangler
                	else if (object.contains("1 minion in or adjacent to Unreal Estate"  ))
                	{
                		
                	}
                	//The Smoking Gnu
                	else if (object.contains("1 minion containing in area trouble marker" ))
                	{
                		
                	}
                	//Doctor Hix
                	else if (object.contains("trouble marker in any area" ))
                	{
                		
                	}
                		
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("play") ==0)
                {
                	object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    //Pondor Stibbons ,Drumknott
                    if (object.contains("2 other cards"))
                    {
                    	
                    }
                    	
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("choose") ==0)
                {
                	
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("return") ==0)
                {
                	
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("replace") ==0)
                {
                	object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    
                    //Sybil Vimes
                    if (object.contains("1 building of your own with another player"))
                    {
                    
                    }
                    else  if (object.contains("1 building of your own with another player"))
                    {
                    	//pay the cost +must has a trouble marker
                    }
                    	
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("stop") ==0)
                {
                	object = currentEffect.Object.get(verbCount);
                    int amount = (int)object.charAt(0);
                    //Gaspode
                    
                    if (object.contains("move or remove minion"))
                    {
                    	//pay the cost +must has a trouble marker
                    }
                    	//Susan
                    else if (object.contains("1 minion from removing"))
                    {
                    	//pay the cost +must has a trouble marker
                    }
                    	
                }
        	
                //there is two of verb choose
                /*else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("choose") ==0)
                {}
                {}*/
        	
        		// ********************** END NILOUFAR SECTION ****************************
        	
        	
                // ********************** GAY SECTION *************************************
        	
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("see") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("exchange") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("ignore") ==0)
                {}
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("end") ==0)
                {
                	//RIOT CARD : Games end of there are more then eight trouble markers
                	int countTroubleMarkers = 0;
                	
                	for (Area area : this.GameBoard.ListArea)
                	{
                		if (area.GetIsTrouble())
                		{
                			countTroubleMarkers++;
                		}
                		
                		if (countTroubleMarkers >= 8)
                		{
                			//END GAMES PROCEDURE
                		}
                	}
                	
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("select") ==0)
                {
                	//Queen Molly
                	Scanner scan = new Scanner(System.in);
                	
                	System.out.println("Select one player:");
                    int playerIndex= scan.nextInt();
                    
                    System.out.println("Player " + playerIndex + " give player " + player + " two cards of your choice by specifying the card number");
                    ListPlayer.get(playerIndex).PrintCardsIndex();
                    
                    System.out.println("First Card:");
                    int FirstCard= scan.nextInt();
                    
                    System.out.println("Second Card:");
                    int SecondCard = scan.nextInt();
                
                    
                    Cards c1 = ListPlayer.get(playerIndex).GetCards().get(FirstCard);
                    Cards c2 = ListPlayer.get(playerIndex).GetCards().get(SecondCard);
                  
                    ListPlayer.get(player).AddPlayerCard(c1);
                    ListPlayer.get(player).AddPlayerCard(c2);
                    
                    ListPlayer.get(playerIndex).RemovePlayerCard(FirstCard);
                    ListPlayer.get(playerIndex).RemovePlayerCard(SecondCard);
                   
                	
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("putminion") ==0)
                {
                	Player currentPlayer = ListPlayer.get(player);
                	
                	//Does player have a minion removed
                	Colors PlayerColor = currentPlayer.GetColor();
                	
                	//Iterate through dead minions to see if player has a removed minions
                	Pieces minionPiece = null;
                	
                	for(Pieces piece : this.GameBoard.GetDeadMinions())
                	{
                		if (piece.GetPieceColor() == PlayerColor)
                		{
                			minionPiece = piece;
                			break;
                		}
                	}
                	
                	if (minionPiece != null)
                	{
	                	//Add minion piece to player minion bank
	                	currentPlayer.RetrieveMinion(minionPiece);
	                	
	                	Scanner scan = new Scanner(System.in);
	                	
	                	boolean minionPlaced = false;
	                	
	                	while (!minionPlaced)
	                	{
		                	System.out.println("Enter area to place minion in: ");
		                    int areaIndex = scan.nextInt() - 1;
		                    
		                    minionPlaced = this.GameBoard.PlaceMinion(areaIndex, currentPlayer);
	                	}
                	}
                	else
                	{
                		System.out.println("You do not have a minion that was removed");
                	}
                }
                else if(currentEffect.Verb.get(verbCount).compareToIgnoreCase("loan") ==0)
                {
                	//Player takes a 10$ loan
                	ListPlayer.get(player).GetLoan(10);
                	
                	//Player owed 12$ at end of games
                	ListPlayer.get(player).AddtoPayBack(12);
                	
                	//Player needs to pay back 12 at end of game or player will lose 15 points
                	ListPlayer.get(player).IncreaseLostPoints(15);
                }
                else
                {
                    System.out.println("!!!!!!! Unknown verb found !!!!!!!! : " + currentEffect.Verb.get(verbCount));
                }
        	}
        
        	// ************************* END GAY SECTION ***************************************
        
            //order of execution not important. Can only execute one of them
            else 
            {
                if(CardPlayed.Name.contains("CMOT Dibbler"))
                {
                    int DieValue = GameBoard.RollDie();
                    System.out.println("Value of Die is "+ DieValue);
                    if(DieValue>7)
                    {
                        //get 4$ from bank
                        GameBoard.DeductFromBank(4);
                		ListPlayer.get(player).AddToMoney(4);
                    }
                    else
                    {
                        //pay 2$ from bank
                        GameBoard.AddToBank(2);
                		ListPlayer.get(player).DeductFromMoney(2);
                    }
                }
                else if(CardPlayed.Name.contains("Fire Brigade"))
                {
                	Scanner scan = new Scanner(System.in);
                    //Choose a player and have him pay 5$. If not, remove a building
                    System.out.println("Enter the player index you want to get your money.");
                    int PlayerIndex = scan.nextInt();
                    System.out.println("Player " + PlayerIndex + ": Do you want to give 5$. If not, he will remove one of your building");
                    String choice = scan.next();
                    if(choice.compareToIgnoreCase("yes")==0)
                    {
                        ListPlayer.get(PlayerIndex).DeductFromMoney(5);
                        ListPlayer.get(player).AddToMoney(5);
                    }
                    else
                    {
                        System.out.println("Enter area index to remove the minion:");
                        int area = scan.nextInt();
                        GameBoard.RemoveMinion(area, ListPlayer.get(PlayerIndex).GetColor());
                    }
                }
                else if(CardPlayed.Name.contains("Dr Whiteface"))
                {
                	Scanner scan = new Scanner(System.in);
                //Choose a player and have him pay 5$. If not, reduce hand size to 4
                 //Choose a player and have him pay 5$. If not, remove a building
                    System.out.println("Enter the player index you want to get your money.");
                    int PlayerIndex = scan.nextInt();
                    System.out.println("Player " + PlayerIndex + ": Do you want to give 5$. If not, you will need to keep this card which will reduce your hand size to 4.");
                    String choice = scan.next();
                    if(choice.compareToIgnoreCase("yes")==0)
                    {
                        ListPlayer.get(PlayerIndex).DeductFromMoney(5);
                        ListPlayer.get(player).AddToMoney(5);
                    }
                    else
                    {
                    	//TO DISCUSS
                        ListPlayer.get(PlayerIndex).HandSize--;
                    }                
                }
                else if(CardPlayed.Name.contains("Errol"))
                {
                    //Remove minion of choice with area with Troublemaker
                    int DieValue = GameBoard.RollDie();
                    Scanner scan = new Scanner(System.in);
                    System.out.println("Value of Die is "+ DieValue);
                    if(DieValue>7)
                    {
                        //remove minion
                        System.out.println("Enter the player index you want to remove minion.");
                        int PlayerIndex = scan.nextInt();
                        System.out.println("Enter area index to remove his minion:");
                        int area = scan.nextInt();
                        
                        GameBoard.RemoveMinion(area, ListPlayer.get(PlayerIndex).GetColor());
                    }
                    else if(DieValue==1)
                    {
                        System.out.println("Enter area index to remove your minion:");
                        int area = scan.nextInt();
                        GameBoard.RemoveMinion(area, ListPlayer.get(player).GetColor());
                    }
                }
                else if(CardPlayed.Name.contains("HERE "))// HERE �N� NOW
                {
                    //Remove minion of choice with area with Troublemaker
                    int DieValue = GameBoard.RollDie();
                    Scanner scan = new Scanner(System.in);
                    System.out.println("Value of Die is "+ DieValue);
                    if(DieValue>7)
                    {
                        //take 3$ from a player
                        System.out.println("Enter the player index you want to get your money.");
                        int PlayerIndex = scan.nextInt();
                        ListPlayer.get(PlayerIndex).DeductFromMoney(3);
                        ListPlayer.get(player).AddToMoney(3);
                    }
                    else if(DieValue==1)
                    {
                        //remove your own minion
                        System.out.println("Enter area index to remove your minion:");
                        int area = scan.nextInt();
                        GameBoard.RemoveMinion(area, ListPlayer.get(player).GetColor());
                    }
                }
            }
            
        }
        
        return ActionStatus;
    }

	private boolean PlayEvent(Cards CardPlayed, int player)
	{
		boolean ActionSuccess = false;
		System.out.println("Activating the event effect now");
        
        //TODO - add code to analyze the event action
        if(CardPlayed.Name.compareToIgnoreCase("The Dragon") == 0)
        {
            System.out.println("Dragon Event \n===========================================");
            int AreaAffected = GameBoard.RollDie();
            
            System.out.println("Area " + AreaAffected + " will be affected by the fire. Removing all minions in it");
            for(int i=0; i<TotalPlayer; i++)
            {
                GameBoard.RemoveBuilding(AreaAffected, ListPlayer.get(i));
                GameBoard.RemoveTroll(AreaAffected);
                GameBoard.Removetrouble(AreaAffected);
                GameBoard.RemoveDemon(AreaAffected);
                GameBoard.RemoveMinion(AreaAffected, ListPlayer.get(i).GetColor());
            }
        }
        else if(CardPlayed.Name.compareToIgnoreCase("Flood") == 0)
        {
            System.out.println("Floood Event \n===========================================");
            for(int FloodCount=0; FloodCount<2; FloodCount++)
            {
                int AreaAffected = GameBoard.RollDie();
                for(int EachPlayer= CurrentPlayer; EachPlayer<(CurrentPlayer+4); EachPlayer++)
                {
                    if(EachPlayer > 4) EachPlayer=EachPlayer%4;
                    
                    //TODO move minion to an adjacent area
                }
            }
        }
        else if(CardPlayed.Name.compareToIgnoreCase("Fire") == 0)
        {
            System.out.println("Fire Event \n===========================================");
            boolean ContinueRolling = true;
            int AffectedArea =-1;
            int PrevArea = -1;
            do
            {
                AffectedArea = GameBoard.RollDie();
                if(PrevArea ==-1) PrevArea=AffectedArea;
                
                int diff = AffectedArea-PrevArea;
                //if 
                if((diff <1 && diff >-1) || (diff == 12) || (diff == -12))
                {
                    ContinueRolling = true;
                    for(int i=0; i<TotalPlayer; i++)
                        GameBoard.RemoveBuilding(AffectedArea, ListPlayer.get(i));
                        
                    PrevArea = AffectedArea;
                } 
                else
                    ContinueRolling = false;
            }while(ContinueRolling);
            
        }
        else if(CardPlayed.Name.compareToIgnoreCase("Fog") == 0)
        {
            System.out.println("Fog Event \n===========================================");
            //Discard top five cards
            Cards DiscardCard = null;
            
            for(int i=0; i<5; i++)
            {
                DiscardCard = CardManager.GetCard(CardType.GreenCards);
                if(DiscardCard == null)
                    DiscardCard = CardManager.GetCard(CardType.BrownCards);

                DiscardCards.add(DiscardCard);
            }
        }
        else if(CardPlayed.Name.compareToIgnoreCase("Riots") == 0)
        {
            System.out.println("Riots Event \n===========================================");
            //traverse Area to count
            if(GameBoard.CountTroubleMaker() >= 8)
            {
                System.out.println("Game is ending preemptively because EventCard RIOT is played");
                //TODO calculating points and determine winner
            }
        }
        else if(CardPlayed.Name.compareToIgnoreCase("Explosion") == 0)
        {
            System.out.println("Explosion Event \n===========================================");
            int AreaAffected = GameBoard.RollDie();
            
            System.out.println("Removing building in Area " + AreaAffected + ".");
            for(int i=0; i<TotalPlayer; i++)
            {
                GameBoard.RemoveBuilding(AreaAffected, ListPlayer.get(i));
            }
        }
        else if(CardPlayed.Name.compareToIgnoreCase("Mysteriuos Murderer") == 0)
        {
            System.out.println("Mysterious Murdered Event \n===========================================");
            int AreaAffected = 0;
            //rotate through player left-side
            for(int i=0; i<TotalPlayer; i++)
            {
                int CurrentPlayerTurn = CurrentPlayer-i;
                if(CurrentPlayerTurn < 0) CurrentPlayerTurn=TotalPlayer-1;
                
                AreaAffected = GameBoard.RollDie();
                Area currentArea = GameBoard.GetArea(AreaAffected);
                
                // TODO:remove a minion in that area of their choice
            }
        }
        else if(CardPlayed.Name.compareToIgnoreCase("Demons From The Dungeon Dimension") == 0)
        {
            System.out.println("Demons From The Dungeon Dimension \n===========================================");
            for(int i=0; i<4; i++)
            {
                int AreaAffected = GameBoard.RollDie();
                GameBoard.ListArea.get(AreaAffected).AddDemons(new Pieces(PieceType.Demon, Colors.None));
            }
        }
        else if(CardPlayed.Name.compareToIgnoreCase("Subsidence") == 0)
        {
            System.out.println("Subsidence Event \n===========================================");
            
            for(int i=0; i<TotalPlayer; i++)
            {
                //TODO For each building in the board, pay 2$ or remove it
            }
        }
        else if(CardPlayed.Name.compareToIgnoreCase("Bloody Stupid Johson") == 0)
        {
            System.out.println("Bloody Stupid Johson \n===========================================");
            int AreaAffected = GameBoard.RollDie();
            
            //disable effect of City Area of that card by discarding the card
            for(int i=0; i<TotalPlayer; i++)
            {
                for(int c=0; c<ListPlayer.get(i).ListCityAreaCards.size(); c++)
                {
                	//boolean 
                	//
                    if(ListPlayer.get(i).ListCityAreaCards.get(c).Name.compareToIgnoreCase(GameBoard.ListArea.get(AreaAffected).CityAreaCardName) == 0)
                    {
                        //TODO
                        ListPlayer.get(i).ListCityAreaCards.remove(c);
                    }
                }
                
            }
            
            //remove 1 minion of that area
            GameBoard.RemoveMinion(AreaAffected, ListPlayer.get(player).GetColor());
            
        }
        else if(CardPlayed.Name.compareToIgnoreCase("Trolls") == 0)
        {
            System.out.println("Trolls \n===========================================");
            int AreaAffected = 0;
            
            for(int i=0; i<3; i++)
            {
                AreaAffected = GameBoard.RollDie();
                GameBoard.ListArea.get(AreaAffected).AddDemons(new Pieces(PieceType.Demon,Colors.None));
            }
        }
        
        else
        {
            System.out.println("!!!!ERROR UNKNOWN EVENT ENCOUNTERED. Card name " + CardPlayed.Name);
        }
        
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