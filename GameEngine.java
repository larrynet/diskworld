import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * GameEngine is the class which the user will interact. It will call the other class according to user decisions inputted from the console or UI (later build). 
 * This class acts as a Facade to hide the internal structure to player. 
 * 
 * @author Lawrence
 * @version 2.0
 */
 
public class GameEngine implements Serializable
{

	private static final long serialVersionUID = 1L;
	public List<Player> ListPlayer;
	private ManageCards CardManager;
	private Board GameBoard;
	private int TotalPlayer;
	private int CurrentPlayer;
	private List<Cards> DiscardCards;
	private int CurrentDie;
	private boolean HasGameEnded;
	private int BoardDie;
	private int ToDiscard;
	/**
	 * Default constructor who will init all internal structure with minimum supported player 
	 */
	public GameEngine() 
	{
		TotalPlayer = 2; //initialize to the minimal player in default constructor
		InitializeData();
	}
	
	/**
	 * @param set current player turn to t
	 */
	public void SetPlayerTurn(int t) 
	{
		CurrentPlayer = t;
	}
	
	/**
	 * Show cards current in player hand
	 * 
	 * @param player
	 */
	public void ShowCard(int player)
	{
		//traverse each player and print the current hand they have
        for(int i=0; i<ListPlayer.get(player).PlayerCards.size(); i++)
        {
        	if(ListPlayer.get(player).PlayerCards.get(i) != null)
        		System.out.println((i)+"- " + ListPlayer.get(player).PlayerCards.get(i).GetName());
        }
	}
	
	/**
	 * Getter to see if game has ended
	 * @return State of game
	 */
	public boolean IsGameEnded() 
	{
		return HasGameEnded;
	}
	
	/**
	 * Signal the game that the end is ending.
	 */
	public void ActivateGameEnd()
	{
		HasGameEnded = true;
	}
	
	/**
	 * Function will reset the status of cityArea Cards in his hand
	 * 
	 * @param p - Index of player
	 */
	public void ReactivateCityAreaEffectForPlayer(int p)
	{
		ListPlayer.get(p).EnableAllCityArea();
	}
	
	/**
	 * Default constructor who will init all internal structure with player p
	 * 
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
	 * Play city area effect card
	 * @param player
	 */
	public void ActivateCityAreaEffect(int player)
    {
		Scanner scan = new Scanner(System.in);
		
		if(DoesPlayerHasCityArea(player))
		{
			System.out.println("Player " + (player+1) + " city area card available to play: ");
			
			//traverse city area list and activate all the effect only if there is no demon
	        for(int i=0; i<ListPlayer.get(player).ListCityAreaCards.size(); i++)
	        {
	        	boolean NoDemon = true;
	        	for(Area _area: GameBoard.ListArea)
	        	{
	        		if(_area.GetName().compareToIgnoreCase(ListPlayer.get(player).ListCityAreaCards.get(i).GetName()) == 0)
	        		{
	        			NoDemon = (_area.GetDemonCount()==0);
	        		}
	        	}
	        	//play city area only if it is desactivated
	        	if(!ListPlayer.get(player).ListCityAreaCards.get(i).IsEffectActivate() && NoDemon)
	        	{
	        		System.out.println("City Area card" + ListPlayer.get(player).ListCityAreaCards.get(i).GetName() + " is available. Would you like to play it?");
	        		String WantPlayCard = scan.next();
	        		if(WantPlayCard.compareToIgnoreCase("yes") == 0)
	        		{

		        		//check if the area is occupied with a demon
		        		String CityAreaName = ListPlayer.get(player).ListCityAreaCards.get(i).GetName();
		                Area CityArea = null;
		                
		                for(Area eachArea : GameBoard.ListArea)
		                {
		                	if(eachArea.GetName().contains(CityAreaName))
		                	{
		                		CityArea = eachArea;
		                		break;
		                	}
		                	
		                }
		                		
		                boolean HasDemon = (CityArea.GetDemonCount()>0);
		                
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
		                            ListPlayer.get(player).DisableStatusCityArea(i);
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
		                            ListPlayer.get(player).DisableStatusCityArea(i);
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
		                            ListPlayer.get(player).DisableStatusCityArea(i);
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
		                            ListPlayer.get(player).DisableStatusCityArea(i);
		                        }
		                    }
		                    else if(CityArea.Name.compareToIgnoreCase("Isle of Gods") == 0)
		                    {
		                    	System.out.println("Do you want to play the effect of <Isle of Gods> (Pay 2$ to remove a troublemaker) (yes or no)?");
		                        String answer = scan.next();
		                        if(answer.compareToIgnoreCase("yes") == 0)
		                        {
		                        	RemoveTrouble();
		                            ListPlayer.get(player).DeductFromMoney(2);
		                            GameBoard.AddToBank(2);
		                            ListPlayer.get(player).DisableStatusCityArea(i);
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
		                            ListPlayer.get(player).DisableStatusCityArea(i);
		                        }
		                    }
		                    else if(CityArea.Name.compareToIgnoreCase("Dimwell") == 0)
		                    {
		                    	//5 7 8 9
		                    	System.out.println("Do you want to play the effect of <Dimwell> (pay 3$ to place minion in Dimwell or adjacent area) (yes or no)?");
		                        String answer = scan.next();
		                        if(answer.compareToIgnoreCase("yes") == 0)
		                        {
		                        	System.out.println("Enter the area index where you want to put minion. Dimwell index is 8, Longwall index is 9, The Scours index is 5 and the Shades index is 7.");
		                        	int Area = scan.nextInt();
		                        	GameBoard.PlaceMinion(Area, ListPlayer.get(player));
		                        	ListPlayer.get(player).DeductFromMoney(3);
		                        	ListPlayer.get(player).DisableStatusCityArea(i);
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
		                            ListPlayer.get(player).DisableStatusCityArea(i);
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
		                            ListPlayer.get(player).DisableStatusCityArea(i);
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
		                        	ListPlayer.get(player).DisableStatusCityArea(i);
		                        }
		                    }
		                    else if(CityArea.Name.compareToIgnoreCase("The Shades") == 0)
		                    {
		                    	System.out.println("Do you want to play the effect of <The Shades> (Place troublemaker in The Shades or adjacent area) (yes or no)?");
		                        String answer = scan.next();
		                        if(answer.compareToIgnoreCase("yes") == 0)
		                        {
		                        	System.out.println("Enter the area index where you want to put minion. The Shades index is 7, The Scours index is 6, Dimwell index is 8 and The Hippo index is 6.");
		                        	int Area = scan.nextInt();
		                        	GameBoard.PlaceTroubleMarker(Area);
		                        	ListPlayer.get(player).DisableStatusCityArea(i);
		                        }
		                    }
		                }
		                else //has demon 
		                	this.Print("Area has demon in it. Can't play current CityArea cards");
	        		}
	        		
	        	}
	        	else
	        	{
	        		if(!NoDemon)
	        			this.Print("Area " + ListPlayer.get(player).ListCityAreaCards.get(i).GetName() + " has a demon in it. City Area effect is neglected");
	        		else
	        			this.Print("City Area Card " + ListPlayer.get(player).ListCityAreaCards.get(i).GetName() + " has already been played");
	        	}
	            
	        }
		}
		else
		{
			System.out.println("Player " + (player+1) + " does NOT have a city area card available to play");
		}
		scan.close();
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
	 * Play action cards
	 * @param CurrentPlayerIndex
	 * @param playChoice
	 * @return
	 */
	public boolean PlayCard(int CurrentPlayerIndex, int playChoice)
	{
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

		System.out.println("Player " + (CurrentPlayerIndex+1) + " decides to play " + CardPlayed.GetName());
		Scanner scan = new Scanner(System.in);

		//No Symbol
		if (lstSymbols == null && CardPlayed.GetName().contains("Nuts"))
		{
			ActionStatus = true;
		}
		else
		{
			//Execute the symbol of card
			for(int sIterator = 0; ActionStatus && (sIterator < lstSymbols.size()); sIterator++)
			{
				String currentSymbol = lstSymbols.get(sIterator);
				//if Random Event, we have to play right away without asking
				if(currentSymbol.compareToIgnoreCase("RE") == 0)
				{
					ActionStatus = PlayEvent(CardPlayed, CurrentPlayerIndex, false);

				}
				else
				{
					System.out.println("Current symbol is " + currentSymbol+". Would you like to play it?");
					String PlaySymbol = scan.next();

					if(PlaySymbol.compareToIgnoreCase("yes") == 0)
					{
						//Place a building
						if(currentSymbol.compareToIgnoreCase("B") == 0)
						{
							ActionStatus = PutBuilding(CurrentPlayerIndex);
						}
						//Place a minion
						else if(currentSymbol.compareToIgnoreCase("M") == 0)
						{
							ActionStatus = PutMinion(CurrentPlayerIndex);
						}
						//Assassination
						else if(currentSymbol.compareToIgnoreCase("A") == 0)
						{
							ActionStatus = Assassinate(CurrentPlayerIndex);
						}
						//Remove one trouble marker
						else if(currentSymbol.compareToIgnoreCase("RT") == 0)
						{
							ActionStatus = RemoveTrouble();
						}
						//Take money
						else if(currentSymbol.contains("T("))
						{
							this.GetPlayerBalance("Current Balance", this.ListPlayer.get(CurrentPlayerIndex));

							ActionStatus = PayPlayer(CurrentPlayerIndex, Character.getNumericValue(currentSymbol.charAt(2)));

							this.GetPlayerBalance("New Balance", this.ListPlayer.get(CurrentPlayerIndex));
						}
						//Play another card
						else if(currentSymbol.compareToIgnoreCase("C") == 0)
						{
							DiscardCards.add(CardPlayed);
							ListPlayer.get(CurrentPlayerIndex).RemovePlayerCard(playChoice);

							//Print Player Cards and Play next card
							this.ListPlayer.get(CurrentPlayerIndex).PrintCardsIndex();
							System.out.println("What card do you want to play next ? (Enter index of card)");

							int newCard = scan.nextInt();
							ActionStatus = PlayCard(CurrentPlayerIndex, newCard);
						}
						//Interupt
						else if(currentSymbol.compareToIgnoreCase("I") == 0)
						{
							System.out.println("Interrupt symbol has no effect. Oh well too late now :)");
						}
						//Scroll: Play action described in card
						else if(currentSymbol.compareToIgnoreCase("S") == 0)
						{
							ActionStatus = PlayEffect(CardPlayed, CurrentPlayerIndex);
						}
						//No such symbol
						else
						{
							System.out.println("Symbol " + currentSymbol + " is invalid. ");
						}
					}
				}
			}
		}
		if(ActionStatus) 
		{

			DiscardCards.add(CardPlayed);
			ListPlayer.get(CurrentPlayerIndex).RemovePlayerCard(playChoice);

			if (this.ToDiscard > 0)
			{
				for (int i = 0; i < this.ToDiscard; i++)
				{
					ListPlayer.get(CurrentPlayerIndex).RemovePlayerCard(i);
				}

				this.ToDiscard = 0;

			}

			this.GameBoard.PrintState();

			System.out.println("");
		}

		return ActionStatus;
	}


	/**
	 * Exception card that have particular instructions
	 * @param c to verify
	 * @return if card is exception
	 */
	private boolean BelongToException(Cards c)
    {
		String CardName = c.GetName().toLowerCase();
		
        boolean b1 = CardName.contains("cmot");
        boolean b2 = CardName.contains("brigade");
        boolean b3 = CardName.contains("whiteface");
        boolean b4 = CardName.contains("errol");
        boolean b5 = CardName.contains("here ");
        
        return (b1 || b2 || b3 || b4 || b5);
    }
	
	/**
	 * Play the symbol effect on the card
	 * @param CardPlayed
	 * @param player
	 * @return true once card is played
	 */
    private boolean PlayEffect(Cards CardPlayed, int player)
    {
    	int IndexOfCardPlayed = 0;
    	
    	for(int i=0; i<ListPlayer.get(player).PlayerCards.size(); i++)
    	{
    		if(CardPlayed == ListPlayer.get(player).PlayerCards.get(i))
    		{
    			IndexOfCardPlayed = i;
    			break;
    		}
    	}
    	
    	boolean ActionStatus = false;
        Action currentEffect = null;
        GreenCards g = null;
        BrownCards b = null;
        List<Action> lstCardActions= null;
        
        String CardName = "";
        
        Scanner scan = new Scanner(System.in);
        if (CardPlayed.GetCardType() == CardType.GreenCards)
        {
        	g = (GreenCards)CardPlayed;
        	lstCardActions = g.GetActionList();
        	CardName = g.GetName().toLowerCase();
        }
        else if (CardPlayed.GetCardType() == CardType.BrownCards)
        {
        	b = (BrownCards)CardPlayed;
        	lstCardActions = b.GetActionList();	
        	CardName = g.GetName().toLowerCase();
        }
  
        if(BelongToException(CardPlayed))
        {

            //activating the area effect
            ActivateCityAreaEffect(player);
    		
        	// *************************** CMOT Dibbler ********************************
            if(CardName.equalsIgnoreCase("CMOT Dibbler"))
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
                	String choice = "";
                	if(ListPlayer.get(player).CanPlaySmallGod())
                	{
                		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                		choice = scan.next();
                	}
                	if(choice.compareToIgnoreCase("yes") == 0)
                	{
                		ListPlayer.get(player).DesactivateSmallGod();
                	}
                	else
                	{
                		if(!ListPlayer.get(player).HasInterruptCard())
                        {
                        	System.out.println("Player " + player + "has an interrupt card. Do you want he wants to play it?");
                        	choice = scan.next();
                        	
                        }
                    	if(choice.compareToIgnoreCase("no") == 0)
                    	{
                        //pay 2$ from bank
                        GameBoard.AddToBank(2);
                		ListPlayer.get(player).DeductFromMoney(2);
                    	}
                    	else
                    	{
                    		//remove interrupt
                    		ListPlayer.get(player).RemoveInterruptCard();
                    	}
                	}
                	
                }
            }
         // *************************** Fire Brigade ********************************
            else if(CardName.equalsIgnoreCase("the fire brigade"))
            {
                //Choose a player and have him pay 5$. If not, remove a building
                System.out.println("Enter the player index you want to get your money.");
                int PlayerIndex = scan.nextInt() - 1;
                String ActivateSmallGod = "";
                if(ListPlayer.get(player).CanPlaySmallGod())
            	{
            		this.Print("Player " + (player + 1) + " has the city Area card Small Gods available. Would you like to play it?");
            		ActivateSmallGod = scan.next();
            	}
            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
            	{
            		ListPlayer.get(player).DesactivateSmallGod();
            	}
            	else
            	{
                if(ListPlayer.get(PlayerIndex).HasInterruptCard())
                {
                	System.out.println("Player " + (PlayerIndex + 1) + " has an interrupt card. Player " + (PlayerIndex + 1) + ", do you want to play it? (yes/no)");
                	String choice = scan.next();
                	
                	if(choice.compareToIgnoreCase("yes") == 0)
                	{
						ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                		return true;
                	}
                }
            	}
                System.out.println("Player " + (PlayerIndex + 1) + ": Do you want to give 5$. If not, he will remove one of your building");
                String choice = scan.next();
                if(choice.compareToIgnoreCase("yes")==0)
                {
                    ListPlayer.get(PlayerIndex).DeductFromMoney(5);
                    ListPlayer.get(player).AddToMoney(5);
                    return true;
                }
                else
                {
                	this.GameBoard.PrintState();
                	this.Print("");
                    System.out.println("Enter area index to remove the building:");
                    int area = scan.nextInt();
                    RemoveBuldingInBoard(area-1, ListPlayer.get(PlayerIndex));
                    return true;
                }
            }
            // *************************** DR Whiteface ********************************
            else if(CardName.equalsIgnoreCase("Dr Whiteface"))
            {
                //Choose a player and have him pay 5$. If not, remove a building
                System.out.println("Enter the player index you want to get your money.");
                int PlayerIndex = scan.nextInt() - 1;
                String ActivateSmallGod = "";
                if(ListPlayer.get(player).CanPlaySmallGod())
            	{
            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
            		ActivateSmallGod = scan.next();
            	}
            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
            	{
            		ListPlayer.get(player).DesactivateSmallGod();
            	}
            	else
            	{
                if(ListPlayer.get(PlayerIndex).HasInterruptCard())
                {
                	System.out.println("Player " + PlayerIndex + "has an interrupt card. Player " + PlayerIndex + ", do you want to play it? (yes/no)");
                	String choice = scan.next();
                	if(choice.compareToIgnoreCase("yes") == 0)
                	{
						ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                		return true;
                	}
                }
            	}
                System.out.println("Player " + PlayerIndex + ": Do you want to give 5$ ? If not, you will need to keep this card which will count toward your hand size. You cannot get rid of this card.(yes/no)");
                String choice = scan.next();
                if(choice.compareToIgnoreCase("yes")==0)
                {
                    ListPlayer.get(PlayerIndex).DeductFromMoney(5);
                    ListPlayer.get(player).AddToMoney(5);
                    return true;
                   
                }
                else
                {
                    ListPlayer.get(PlayerIndex).HandSize--;
                    return true;
                }
                
               
            }
         // *************************** Errol ********************************
            else if(CardName.equalsIgnoreCase("Errol"))
            {
                //Remove minion of choice with area with Troublemaker
                int DieValue = GameBoard.RollDie();
                System.out.println("Value of Die is "+ DieValue);
                if(DieValue>7)
                {
                    //remove minion
                    System.out.println("Enter the player index you want to remove minion.");
                    int PlayerIndex = scan.nextInt() - 1;
                    String ActivateSmallGod = "";
                    if(ListPlayer.get(player).CanPlaySmallGod())
                	{
                		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                		ActivateSmallGod = scan.next();
                	}
                	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                	{
                		ListPlayer.get(player).DesactivateSmallGod();
                	}
                	else
                	{
                    if(!ListPlayer.get(PlayerIndex).HasInterruptCard())
                    {
                    	System.out.println("Player " + PlayerIndex + "has an interrupt card. Player " + PlayerIndex + ", do you want to play it? (yes/no)");
                    	String choice = scan.next();
                    	if(choice.compareToIgnoreCase("yes") == 0)
                    	{
							ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                    		return true;
                    	}
                    }
                	}
                    System.out.println("Enter area index to remove his minion:");
                    int area = scan.nextInt();
                    
                    GameBoard.RemoveMinion(area, ListPlayer.get(PlayerIndex).GetColor());
                }
                else if(DieValue==1)
                {
                	String choice = "";
                	String ActivateSmallGod = "";
                    if(ListPlayer.get(player).CanPlaySmallGod())
                	{
                		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                		ActivateSmallGod = scan.next();
                	}
                	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                	{
                		ListPlayer.get(player).DesactivateSmallGod();
                	}
                	else
                	{
                	if(!ListPlayer.get(player).HasInterruptCard())
                    {
                    	System.out.println("Player " + player + "has an interrupt card. Do you want he wants to play it?");
                    	choice = scan.next();
                    	
                    }
                	if(choice.compareToIgnoreCase("no") == 0)
                	{
                    System.out.println("Enter area index to remove your minion:");
                    int area = scan.nextInt();
                    GameBoard.RemoveMinion(area, ListPlayer.get(player).GetColor());
                	}
					else
						ListPlayer.get(player).RemoveInterruptCard();
                	}
                }
            }
            else if(CardName.contains("here "))// HERE AND NOW
            {
                //Remove minion of choice with area with Troublemaker
                int DieValue = GameBoard.RollDie();

                System.out.println("Value of Die is "+ DieValue);
                if(DieValue>=7)
                {
                    //take 3$ from a player
                    System.out.println("Enter the player index you want to get your money.");
                    int PlayerIndex = scan.nextInt() - 1;
                    String ActivateSmallGod = "";
                    if(ListPlayer.get(player).CanPlaySmallGod())
                	{
                		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                		ActivateSmallGod = scan.next();
                	}
                	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                	{
                		ListPlayer.get(player).DesactivateSmallGod();
                	}
                	else
                	{
                    if(ListPlayer.get(PlayerIndex).HasInterruptCard())
                    {
                    	System.out.println("Player " + PlayerIndex + "has an interrupt card, does player " + PlayerIndex + " want to play it.");
                    	String choice = scan.next();
                    	if(choice.compareToIgnoreCase("yes") == 0)
                    	{
							ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                    		return true;
                    	}
                    }
                	}
					
                    ListPlayer.get(PlayerIndex).DeductFromMoney(3);
                    ListPlayer.get(player).AddToMoney(3);
                    
                    return true;
                }
                else if(DieValue==1)
                {
                	String choice = "";
                	String ActivateSmallGod = "";
                    if(ListPlayer.get(player).CanPlaySmallGod())
                	{
                		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                		ActivateSmallGod = scan.next();
                	}
                	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                	{
                		ListPlayer.get(player).DesactivateSmallGod();
                	}
                	else
                	{
                	if(ListPlayer.get(player).HasInterruptCard())
                    {
                    	System.out.println("Player " + player + "has an interrupt card. Do you want he wants to play it?");
                    	choice = scan.next();
                    	
                    	if (choice.compareToIgnoreCase("yes") == 0)
                    	{
							ListPlayer.get(player).RemoveInterruptCard();
                    		return true;
                    	}
                    	
                    }
                	}
                	
                		//remove your own minion
                		System.out.println("Enter area index to remove your minion:");
                		int area = scan.nextInt();
                		GameBoard.RemoveMinion(area, ListPlayer.get(player).GetColor());
                		
                		return true;
                }
                
                return true;
            }
        }
        else //go through normal cards
        {
        	//traverse each action
        	for(int actionCount=0; actionCount<lstCardActions.size(); actionCount++)
        	{
        		ActivateCityAreaEffect(player);
        		//ask user to see if he wants to play city area
        		/*System.out.println("Before we continue, would you like to activate city area cards player " + (player+1));
				String ActivateArea = scan.next();
				if(ActivateArea.compareToIgnoreCase("yes") == 0)
				{
					//activating the area effect
                    ActivateCityAreaEffect(player);
				}*/
        		currentEffect = lstCardActions.get(actionCount);
        		//traverse the verb
                for(int verbCount=0; verbCount<currentEffect.GetVerbList().size(); verbCount++)
                {
                        if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("pay") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
                            int amount = (int)object.charAt(0);
                            
                            if(object.contains("another player and have them remove 1 minion with troublemaker"))
                            {
                            	//cosmos lavish
                            	System.out.println("Please enter Player index which you wish to remove minion");
                            	int PlayerIndex = scan.nextInt() - 1;
                            	
                            	String choice = "";
                            	String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(!ListPlayer.get(PlayerIndex).HasInterruptCard())
                                {
                                	System.out.println("Player " + PlayerIndex + "has an interrupt card. Player " + PlayerIndex + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                	
                                }
                            	if(choice.compareToIgnoreCase("no") == 0)
                            	{
                            		System.out.println("Please enter Area index which you wish to remove minion. (Area must have troublemaker)");
                                	int AreaIndex = scan.nextInt();
                                	
                                	//get cost of building
                                	GameBoard.RemoveMinion(AreaIndex, ListPlayer.get(PlayerIndex).GetColor());
                               
                            	}
								else
									ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                            	}
                            }
                            else if(object.contains("another player and move 1 minion to any area"))
                            {
                            	//Hobsons's Livery Stable
                            	System.out.println("Please enter Player index which you move minion");
                            	int PlayerIndex = scan.nextInt() - 1;
                            	
                            	String choice = "";
                            	String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(!ListPlayer.get(PlayerIndex).HasInterruptCard())
                                {
                                	System.out.println("Player " + PlayerIndex + "has an interrupt card. Player " + PlayerIndex + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                	
                                }
                            	if(choice.compareToIgnoreCase("no") == 0)
                            	{
                            		System.out.println("Please enter Area index where minion is found.");
                                	int AreaIndex = scan.nextInt();
                                	System.out.println("Please enter new Area index where minion you want to put minion.");
                                	int NewArea = scan.nextInt();
                                	GameBoard.RemoveMinion(AreaIndex, ListPlayer.get(PlayerIndex).GetColor());
                                	GameBoard.PlaceMinion(NewArea, ListPlayer.get(PlayerIndex));
                            	}
								else
									ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                            	}
                            	
                            }
                            else if(object.contains(" another player and assassinate 1 minion"))
                            {
                            	int AreaIndex = 0;
                            	//Burleigh & Stronginth
                            	System.out.println("Please enter Player index which you assasinate minion");
                            	int PlayerIndex = scan.nextInt() - 1;
                            	String choice = "";
                            	String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(!ListPlayer.get(PlayerIndex).HasInterruptCard())
                                {
                                	System.out.println("Player " + PlayerIndex + "has an interrupt card. Player " + PlayerIndex + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                	
                                }
                            	if(choice.compareToIgnoreCase("no") == 0)
                            	{
                            	System.out.println("Please enter Area index where minion is found.");
                            	AreaIndex = scan.nextInt();
                            	}
								else
									ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                            	}
									
                            	
                            	GameBoard.RemoveMinion(AreaIndex, ListPlayer.get(PlayerIndex).GetColor());
                            }
                            
                            else if(object.contains("bank"))
                            {
                            	String choice = "";
                            	String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(!ListPlayer.get(player).HasInterruptCard())
                                {
                                	System.out.println("Player " + player + "has an interrupt card. Do you want he wants to play it?");
                                	choice = scan.next();
                                	
                                }
                            	if(choice.compareToIgnoreCase("no") == 0)
                            	{
                            	ListPlayer.get(player).DeductFromMoney(amount);
                            	GameBoard.AddToBank(amount);
                            	}
								else
									ListPlayer.get(player).RemoveInterruptCard();
                            	}
                            }
                            else if(object.contains("building"))
                            {
                            	//Reacher Gilt
                            	System.out.println("Please enter Area index which you wish to take over building. (Area must have troublemaker)");
                            	int AreaIndex = scan.nextInt();
                            	System.out.println("Please enter Player index which you wish to take over building");
                            	int PlayerIndex = scan.nextInt() - 1;
                            	String choice = "";
                            	String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(!ListPlayer.get(PlayerIndex).HasInterruptCard())
                                {
                                	System.out.println("Player " + PlayerIndex + "has an interrupt card. Player " + PlayerIndex + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                	
                                }
                            	if(choice.compareToIgnoreCase("no") == 0)
                            	{
									//get cost of building
									int Cost = GameBoard.GetArea(AreaIndex).GetAreaCost();
									System.out.println("Area " + AreaIndex + " (" + GameBoard.GetArea(AreaIndex).GetName()+") cost "+Cost +". Proceeding payment");
									ListPlayer.get(PlayerIndex).AddToMoney(Cost);
									ListPlayer.get(player).DeductFromMoney(Cost);
                            	}
								else
									ListPlayer.get(player).RemoveInterruptCard();
                            	}
                            }
                            else if(object.contains("player"))
                            {
                            	//pay to each player - Mr Boggis
                            	if(object.contains("each"))
                            	{
                            		String choice = "";
                            		String ActivateSmallGod = "";
                                    if(ListPlayer.get(player).CanPlaySmallGod())
                                	{
                                		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                                		ActivateSmallGod = scan.next();
                                	}
                                	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                                	{
                                		ListPlayer.get(player).DesactivateSmallGod();
                                	}
                                	else
                                	{
                                	if(!ListPlayer.get(player).HasInterruptCard())
                                    {
                                    	System.out.println("Player " + player + "has an interrupt card. Do you want he wants to play it?");
                                    	choice = scan.next();
                                    	
                                    }
                                	if(choice.compareToIgnoreCase("no") == 0)
                                	{
	                            		for(int i=0; i<ListPlayer.size(); i++)
	                            		{
	                            			if(i != player) {
	                            				String choice2 = "";
	                                        	if(!ListPlayer.get(i).HasInterruptCard())
	                                            {
	                                            	System.out.println("Player " + i + "has an interrupt card. Do you want he wants to play it?");
	                                            	choice2 = scan.next();
	                                            	
	                                            }
	                                        	if(choice2.compareToIgnoreCase("no") == 0)
	                                        	{
	                                        		ListPlayer.get(i).AddToMoney(amount);
	                                        		ListPlayer.get(player).DeductFromMoney(amount);
	                                        	}
												else
													ListPlayer.get(i).RemoveInterruptCard();
	                                        }
	                            		}
                                	}
									else
										ListPlayer.get(player).RemoveInterruptCard();
                                	}
                            		
                            	}
                            	else //if(object.contains("another"))//pay to a specific player
                            	{
                            		String choice = "";
                            		String ActivateSmallGod = "";
                                    if(ListPlayer.get(player).CanPlaySmallGod())
                                	{
                                		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                                		ActivateSmallGod = scan.next();
                                	}
                                	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                                	{
                                		ListPlayer.get(player).DesactivateSmallGod();
                                	}
                                	else
                                	{
                                	if(!ListPlayer.get(player).HasInterruptCard())
                                    {
                                    	System.out.println("Player " + player + "has an interrupt card. Do you want he wants to play it?");
                                    	choice = scan.next();
                                    	
                                    }
                                	if(choice.compareToIgnoreCase("no") == 0)
                                	{
                            		System.out.println("Please enter the Area index you want to remove the troublemaker.");
                                    
                                    int otherPlayer = scan.nextInt();
                                    ListPlayer.get(otherPlayer).AddToMoney(amount);
                    				ListPlayer.get(player).DeductFromMoney(amount);
                            	
                                	}
									else
										ListPlayer.get(player).RemoveInterruptCard();
                                	}
                            	}
                            }
                            
                           
                            else
                            {
                            	System.out.println("unknown object " + object);
                            }
            
                        }
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("look") ==0)
                        {
                        	List<Integer> ListOfPersonalityIndex = new ArrayList<Integer>();
                        	
                        	for(int i=0; i<CardManager.Personality_Card.length; i++)
                        	{
                        		if(CardManager.Personality_Card[i].Status)
                        			ListOfPersonalityIndex.add(i);
                        	}
                        	Random randGenerator = new Random();
                        	int noLookIndex = randGenerator.nextInt()%ListOfPersonalityIndex.size();
                        	
                        	for(int j=0; j<ListOfPersonalityIndex.size(); j++)
                        	{
                        		if(j!=noLookIndex)
                        		{
                        			PersonalityCards p = CardManager.Personality_Card[ListOfPersonalityIndex.get(j)];
                        			p.ShowImage(p.GetName());
                        		}
                        	}
                        	return true;
                        }
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("give") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
                            int amount = Character.getNumericValue(object.charAt(0));
                            
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
                            
                            	System.out.println("Enter card index to give.");
                                int cardIndex= scan.nextInt();
                                
                                System.out.println("Enter another player index who you will exchange card for 2$");
                                int playerIndex= scan.nextInt() - 1;
                                
                                String choice = "";
                                String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(ListPlayer.get(playerIndex).HasInterruptCard())
                                {
                                	System.out.println("Player " + playerIndex + "has an interrupt card. Player " + playerIndex + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                	
                                	if (choice.compareToIgnoreCase("yes") == 0)
                                	{
                                		ListPlayer.get(playerIndex).RemoveInterruptCard();
                                		return true;
                                	}
                                	
                                }
                            	}
                            	
                                ListPlayer.get(playerIndex).AddPlayerCard(CardPlayed);
                                ListPlayer.get(player).RemovePlayerCard(cardIndex);
                            	
                                return true;
									
                            }
                            else //Hubert
                            {
                            	
                            	System.out.println("Enter player index  who will be forced to give 3$.");
                                int Src= scan.nextInt();
                                
                                String choice = "";
                                String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(ListPlayer.get(Src).HasInterruptCard())
                                {
                                	System.out.println("Player " + Src + "has an interrupt card. Player " + Src + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                	
                                	if(choice.compareToIgnoreCase("yes") == 0)
                                	{
                                		ListPlayer.get(Src).RemoveInterruptCard();
                                		return true;
                                	}
                                }
                            	}
                            	System.out.println("Enter player index who will receive 3$");
                                int Dst= scan.nextInt();
                                
                                ListPlayer.get(Dst).AddToMoney(3);
                				ListPlayer.get(Src).DeductFromMoney(3);	
                                
                            }
                        }
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("take") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
                            int amount = Character.getNumericValue(object.charAt(0));
                            
                            if(object.contains("cards"))
                            {
                            	//the beggars
                            	

                            	System.out.println("Enter player index to take cards from.");
                                int playerIndex= scan.nextInt() - 1;
                                String choice = "";
                                String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(!ListPlayer.get(playerIndex).HasInterruptCard())
                                {
                                	System.out.println("Player " + playerIndex + "has an interrupt card. Player " + playerIndex + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                	
                                	if(choice.compareToIgnoreCase("yes") == 0)
                                	{
                                		ListPlayer.get(playerIndex).RemoveInterruptCard();
                                		return true;	
                                	}
                                	
                                }
                            	}
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
								
								return true;
                            	
									
                            }
                            else if(object.contains("1$ or card"))
                            {
                            	
                            	//The Ankh Morpork Sunshine Dragon Sanctuary
                            	
                            	this.GetPlayerBalance("Current Balance",ListPlayer.get(player));
                            	
                            	for (Player iterPlayer : this.ListPlayer)
                            	{
                            		if (iterPlayer.GetColor() != ListPlayer.get(player).GetColor())
                            		{
                            			String ActivateSmallGod = "";
                                        if(ListPlayer.get(player).CanPlaySmallGod())
                                    	{
                                    		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                                    		ActivateSmallGod = scan.next();
                                    	}
                                    	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                                    	{
                                    		ListPlayer.get(player).DesactivateSmallGod();
                                    	}
                                    	else
                                    	{
                            			if(iterPlayer.HasInterruptCard())
                            			{
                            				System.out.println("Player " + iterPlayer.GetPlayerNumber() + "has an interrupt card. Player " + iterPlayer.GetPlayerNumber() + ", do you want to play it? (yes/no)");
                            				String choice = scan.next();

                            				if(choice.compareToIgnoreCase("yes") == 0)
                            				{
                            					iterPlayer.RemoveInterruptCard();
                            					break;
                            				}
                            			}
                            			else 
                            			{
                            				System.out.println("Player " + iterPlayer.GetPlayerNumber() + " you must give either 1$ (choice 1) or one of your cards (choice 2)");
                            				int choice1= scan.nextInt();

                            				if(choice1 == 1)
                            				{
                            					ListPlayer.get(player).AddToMoney(1);
                            					iterPlayer.DeductFromMoney(1);
                            				}
                            				else if (choice1 == 2)
                            				{
                            					System.out.println("Enter card index to take.");
                            					int cardIndex0= scan.nextInt();
                            					Cards c1 = iterPlayer.GetCards().get(cardIndex0);

                            					ListPlayer.get(player).AddPlayerCard(c1);
                            					iterPlayer.RemovePlayerCard(cardIndex0);

                            				}

                            			}
                                    	}
                            		}
                                }
                            	
                            	this.GetPlayerBalance("New Balance",ListPlayer.get(player));
                                
                                return true;

                            }
                            else if(object.contains("$ from all"))
                            {
                            	Player thisPlayer = this.ListPlayer.get(player);
                            	
                            	this.GetPlayerBalance("Current Balance", thisPlayer);
                            	
                            	//thief guild
                            	for(int i=0; i<ListPlayer.size(); i++)
                        		{
                            		if(i != player) 
                            		{
                           
                            			boolean giveMoney = true;
                            			String choice = "";
                            			String ActivateSmallGod = "";
                                        if(ListPlayer.get(player).CanPlaySmallGod())
                                    	{
                                    		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                                    		ActivateSmallGod = scan.next();
                                    	}
                                    	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                                    	{
                                    		ListPlayer.get(player).DesactivateSmallGod();
                                    	}
                                    	else
                                    	{
                            			if(ListPlayer.get(i).HasInterruptCard())
                            			{
                            				System.out.println("Player " + i + "has an interrupt card. Player " + i + ", do you want to play it? (yes/no)");
                            				choice = scan.next();

                            				if(choice.compareToIgnoreCase("yes") == 0)
                            				{
                            					thisPlayer.RemoveInterruptCard();
                            					giveMoney = false;
                            				}

                            			}
                                    	}
                            			if (giveMoney && ListPlayer.get(i).GetMoneyCount() >= 2)
                            			{
                            				ListPlayer.get(player).AddToMoney(amount);
                            				ListPlayer.get(i).DeductFromMoney(amount);
                            			}
                            		}
                        		}
                            	
                            	this.GetPlayerBalance("New Balance", thisPlayer);
                            	return true;
                            }
                            else if(object.contains("$ from each"))
                            {
                            	
                            	Player currentPlayer = ListPlayer.get(player);
                            	
                            	this.GetPlayerBalance("Current Balance", currentPlayer);
                            	
                            	//Mr Boggis
                            	for(int i=0; i<ListPlayer.size(); i++)
                            	{
                            		if(i != player) 
                            		{
                            			Player thisPlayer = this.ListPlayer.get(i);
                            			
                            			boolean giveMoney = true;
                            			String choice = "";
                            			String ActivateSmallGod = "";
                                        if(ListPlayer.get(player).CanPlaySmallGod())
                                    	{
                                    		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                                    		ActivateSmallGod = scan.next();
                                    	}
                                    	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                                    	{
                                    		ListPlayer.get(player).DesactivateSmallGod();
                                    	}
                                    	else
                                    	{
                            			if(ListPlayer.get(i).HasInterruptCard())
                            			{
                            				System.out.println("Player " + i + "has an interrupt card. Player " + i + ", do you want to play it? (yes/no)");
                            				choice = scan.next();

                            				if(choice.compareToIgnoreCase("yes") == 0)
                            				{
                            					thisPlayer.RemoveInterruptCard();
                            					giveMoney = false;
                            				}

                            			}
                                    	}
                            			
                            			if (giveMoney && ListPlayer.get(i).GetMoneyCount() >= 2)
                            			{
                            				ListPlayer.get(player).AddToMoney(amount);
                            				ListPlayer.get(i).DeductFromMoney(amount);
                            			}
                            		}
                            	}
                            	
                            	this.GetPlayerBalance("New Balance", currentPlayer);
                            	
                            	return true;
                            }
                            
                        }
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("interrupt") ==0)
                        {
                        	//todo - later (Brown card)
                        	//have to findout how to play Doctor Mossy Lawn
                        }
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("loan") ==0)
                        {
                    		this.GetPlayerLoanBank("Current Finances: ", ListPlayer.get(player));
                        	
                        	//Player takes a 10$ loan
                        	ListPlayer.get(player).GetLoan(10);
                        	
                        	//Player owed 12$ at end of games
                        	ListPlayer.get(player).AddtoPayBack(12);
                        	
                        	//Player needs to pay back 12 at end of game or player will lose 15 points
                        	ListPlayer.get(player).IncreaseLostPoints(15);
                        	
                        	this.GetPlayerLoanBank("New Finances: ", ListPlayer.get(player));
                        	
                        	return true;
                        }
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("get") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
                            int amount = Character.getNumericValue(object.charAt(0));
                            
                            if(object.contains("minion in the Isle of Gods"))
                            {
                                int TotalMinionInArea = 0;
                            	//The dysk & the opera house
                            	//count minion in Isle of Gods
                                for(int a=0; a<GameBoard.ListArea.size(); a++)
                                {
                                    if(GameBoard.ListArea.get(a).Name.contains("in Isle of Gods"))
                                    {
                                        TotalMinionInArea = GameBoard.CountPlayerMinionsArea(Colors.None, a);
                                    }
                                }
                                GameBoard.DeductFromBank(amount*TotalMinionInArea);
                        		ListPlayer.get(player).AddToMoney(amount*TotalMinionInArea);
                        		return true;
                    
                            }
                            else if(object.contains("times number of discarded card"))
                            {
                            	//Harry King -- Shonky shop
                            	
                            	System.out.println("Enter the number of cards you would like to discard: ");
                            	int cardNumber = scan.nextInt();
                            	this.ToDiscard = cardNumber;
                            	
                            	GameBoard.DeductFromBank(amount*cardNumber);
                        		ListPlayer.get(player).AddToMoney(amount*cardNumber);
                        		
                        		return true;
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
                                System.out.println("There is " + TotalTroubleMaker + " troublemaker in the field. PLayer will earn " + amount*TotalTroubleMaker + " dollars. THat same amount will be deducted from the bank.");
                                GameBoard.DeductFromBank(amount*TotalTroubleMaker);
                        		ListPlayer.get(player).AddToMoney(amount*TotalTroubleMaker);
                        		return true;
                            	
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
                            	
                            	//Nobby Nobbs
                                System.out.println("Enter player to get money from.");
                                int PlayerIndex = scan.nextInt() - 1;
                                
                                ListPlayer.get(player).AddToMoney(amount);
                        		ListPlayer.get(PlayerIndex).DeductFromMoney(amount);
                        		
                        		return true;
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
                                        TotalMinionInArea += GameBoard.CountPlayerMinionsArea(Colors.None,a);
                                }
                                GameBoard.DeductFromBank(amount*TotalMinionInArea);
                        		ListPlayer.get(player).AddToMoney(amount*TotalMinionInArea);
                            }
                            else if(object.contains("cards of other player and give back 1"))
                            {
                            	//stanley pick two cards randomly and randomly select 1
                                System.out.println("Select a player to get cards from. ");

                                int PlayerIndex = scan.nextInt() - 1;
                                String choice = "";
                                String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(!ListPlayer.get(PlayerIndex).HasInterruptCard())
                                {
                                	System.out.println("Player " + PlayerIndex + "has an interrupt card. Player " + PlayerIndex + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                }
                            	if(choice.compareToIgnoreCase("no") == 0)
                            	{
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
								else
									ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                            	}
                            }
                            else if(object.contains("cards from a player"))
                            {
                            	//Queen molly (selected player)
                            	System.out.println("Select a player to get cards from. ");

                            	int PlayerIndex = scan.nextInt() - 1;

                            	String choice = "";

                            	String ActivateSmallGod = "";

                            	if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            		if(ListPlayer.get(PlayerIndex).HasInterruptCard())
                            		{
                            			System.out.println("Player " + (PlayerIndex + 1) + " has an interrupt card. Player " + (PlayerIndex + 1)+ ", do you want to play it? (yes/no)");
                            			choice = scan.next();

                            			if(choice.compareToIgnoreCase("yes") == 0)
                            			{
                            				ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                            				return true;
                            			}

                            		}
                            	}
                            	
                            	this.ShowCard(PlayerIndex);
                            	
								System.out.println("Player " + (PlayerIndex + 1) + ": Enter first card you are willing to give up:");
								int CardIndex0 = scan.nextInt();
								
								System.out.println("Player " + (PlayerIndex + 1) +  ": Enter second card you are willing to give up:");
								int CardIndex1 = scan.nextInt();
								
								ListPlayer.get(player).PlayerCards.add(ListPlayer.get(PlayerIndex).PlayerCards.get(CardIndex0));
								ListPlayer.get(player).PlayerCards.add(ListPlayer.get(PlayerIndex).PlayerCards.get(CardIndex1));
								ListPlayer.get(PlayerIndex).PlayerCards.remove(CardIndex0);
								ListPlayer.get(PlayerIndex).PlayerCards.remove(CardIndex1);
								
								this.Print("Player " + (player + 1) +  " New hand");
								
								this.ShowCard(player);
								
								return true;
								
                            }
                            else if(object.contains("cards"))
                            {
                            	//Leonard of Quirm
                            	//the clacks
                            	//professor of recent runes
                            	//Sergeant Cheery Littlebottom
                            	
                                List<Cards> DrawCardList = new ArrayList<Cards>();
                                
                                Player thisPlayer = ListPlayer.get(player);
                                
                                //Print Cards Before
                                this.PrintHandState("Current Hand", thisPlayer);
                                
                                for(int i=0; i<amount; i++)
                                {
                                    Cards c = CardManager.GetCard(CardType.GreenCards);
                                    if(c==null) c=CardManager.GetCard(CardType.BrownCards);
                                    thisPlayer.PlayerCards.add(c);
                                }
                            	
                                //Print Cards after
                                this.PrintHandState("New Hand", thisPlayer);
                                
                                return true;
                            }
                            
                        }
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("discard") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
                            int amount = Character.getNumericValue(object.charAt(0));
                            
                            if(object.contains("up to 3 cards and fill hands"))
                            {
                            	String choice = "";
                            	String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(ListPlayer.get(player).HasInterruptCard())
                                {
                            		System.out.println("Player " + player + "has an interrupt card. Player " + player + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                	
                                	if(choice.compareToIgnoreCase("yes") == 0)
                                	{
                                		ListPlayer.get(player).RemoveInterruptCard();
                                		return true;
                                	}
                                }
                            	}
                            	
                            	//alchemist guild                   	
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
                                System.out.println("Enter player index you want to peek and discard");
                                int PlayerIndex = scan.nextInt() - 1;
                                
                                String choice = "";
                                String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(!ListPlayer.get(PlayerIndex).HasInterruptCard())
                                {
                                	System.out.println("Player " + PlayerIndex + "has an interrupt card. Player " + PlayerIndex + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                }
                            	if(choice.compareToIgnoreCase("no") == 0)
                            	{
	                                //show card
	                                for(int i=0; i<ListPlayer.get(PlayerIndex).PlayerCards.size(); i++)
	                                	System.out.println((i+1)+"- " + ListPlayer.get(PlayerIndex).PlayerCards.get(i).GetName());
	                                
	                                System.out.println("Enter card index you want to discard");
	                                int CardToDiscard = scan.nextInt();
	                                ListPlayer.get(PlayerIndex).PlayerCards.remove(CardToDiscard);
	                            
                            	}
								else
									ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                            	}
                            }
                            else if(object.contains("card"))
                            {
                            	String choice = "";
                            	String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(ListPlayer.get(player).HasInterruptCard())
                                {
                            		System.out.println("Player " + player + "has an interrupt card. Player " + player + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                	
                                	if(choice.compareToIgnoreCase("yes") == 0)
                                	{
                                		ListPlayer.get(player).RemoveInterruptCard();
                                		return true;
                                	}
                                }
                            	}
                            	
                            	///modo
                            	//The Mob
                            	
                            	this.PrintHandState("Current Hand", ListPlayer.get(player));
                            	
                            	for(int i=0; i<amount; i++)
                                {  
                                	System.out.println("Enter card index you want to discard");
                                    
                                	int CardToDiscard = scan.nextInt();
                                    ListPlayer.get(player).PlayerCards.remove(CardToDiscard);
                                    
                                }
                            	
                            	this.PrintHandState("New Hand", ListPlayer.get(player));
                            	return true;
                            	
                            } 
                        }
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("remove") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
                            int amount = (int)object.charAt(0);
                            if(object.contains("minion in player order"))
                            {
                            	//The Auditors
                                for(int i=0; i<TotalPlayer; i++)
                                {
                                    if(i!=player)
                                    {
                                    	String choice = "";
                                    	String ActivateSmallGod = "";
                                        if(ListPlayer.get(player).CanPlaySmallGod())
                                    	{
                                    		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                                    		ActivateSmallGod = scan.next();
                                    	}
                                    	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                                    	{
                                    		ListPlayer.get(player).DesactivateSmallGod();
                                    	}
                                    	else
                                    	{
                                    	if(!ListPlayer.get(i).HasInterruptCard())
                                        {
                                        	System.out.println("Player " + i + "has an interrupt card. Do you want he wants to play it?");
                                        	choice = scan.next();
                                        }
                                    	if(choice.compareToIgnoreCase("no") == 0)
                                    	{
											System.out.println("Enter area index you want to remove minion");
											int Area = scan.nextInt();
											GameBoard.RemoveMinion(Area, ListPlayer.get(i).GetColor());
                                    	}
										else
											ListPlayer.get(i).RemoveInterruptCard();
                                    	}
                                    }
                                }
                            }
                            else if(object.contains("minion of choice in that area and roll dice twice"))
                            {
                                //Carcer
                                int RollDieValue0 = GameBoard.RollDie();
                                int RollDieValue1 = GameBoard.RollDie();
                                
                                System.out.println("Enter Player index to remove minion from for area "+RollDieValue0 +" : ");
                                int PlayerIndex0 = scan.nextInt() - 1;
                                String choice = "";
                                String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(!ListPlayer.get(PlayerIndex0).HasInterruptCard())
                                {
                                	System.out.println("Player " + PlayerIndex0 + "has an interrupt card. Do you want he wants to play it?");
                                	choice = scan.next();
                                }
                            	if(choice.compareToIgnoreCase("no") == 0)
                            	{
                            		GameBoard.RemoveMinion(RollDieValue0, ListPlayer.get(PlayerIndex0).GetColor());
                                
                            	}
                            	else
									ListPlayer.get(PlayerIndex0).RemoveInterruptCard();
                            	}
                                System.out.println("Enter Player index to remove minion from for area "+RollDieValue1 +" : ");
                                int PlayerIndex1 = scan.nextInt() - 1;
                 
                                String choice1 = "";
                              
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(!ListPlayer.get(PlayerIndex1).HasInterruptCard())
                                {
                                	System.out.println("Player " + PlayerIndex1 + "has an interrupt card. Do you want he wants to play it?");
                                	choice1 = scan.next();
                                }
                            	if(choice1.compareToIgnoreCase("no") == 0)
                            	{
                            		GameBoard.RemoveMinion(RollDieValue1, ListPlayer.get(PlayerIndex1).GetColor());                           
                            	}else
									ListPlayer.get(PlayerIndex1).RemoveInterruptCard();
                            	}
                            
                            }
                        }
                        
                        
                        // ***************** PARINAZ SECTION ***********************************
                        
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("draw") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
                            int amount = Character.getNumericValue(object.charAt(0));
                            
                            if(object.contains("discard"))
                            {
                            	Cards [] c = new Cards[4];
                            	for(int i=0; i<4; i++)
                            	{
                            	   if(this.DiscardCards.get(i) != null)
                            	   {
                            		   ListPlayer.get(player).PlayerCards.add(this.DiscardCards.get(i));
                            	
                            	   }
                            	}
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
                             		//if(c==null) c = CardManager.GetCard(CardType.GreenCards);
                             		ListPlayer.get(player).AddPlayerCard(c);
                             	}
                             	
                             	return true;
                            }
                        }
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("exchange") ==0)
                        {  
                        	String object = currentEffect.GetObjectList().get(verbCount);
                            
                        	
                        	//Zorgo the Retro-phrenologist
                            if(object.contains("personality"))
                            {
                            	
                            	//I get a personlaity card and set player personality card
                            	//get old card
                            	Cards OriginalPersonality = ListPlayer.get(player).GetPlayerPersonality();
                            	ListPlayer.get(player).SetPlayerPersonality(CardManager.GetCard(CardType.PersonalityCards));
                            	for(int i=0; i<CardManager.Personality_Card.length; i++)
                            	{
                            		if(CardManager.Personality_Card[i].Status)
                            		{
                            			//exchange with current one
                            			CardManager.Personality_Card[i].Status = false;
                            			ListPlayer.get(player).SetPersonalityCard(CardManager.Personality_Card[i]);
                            			break;
                            		}
                            		//if(CardManager.Personality_Card[i].GetName().compareToIgnoreCase(p.GetName()) == 0)
                            		//	CardManager.Personality_Card[i].Status = true;
                            	}
                            	
                            	for(int j=0; j<CardManager.Personality_Card.length; j++)
                            	{
                            		if(OriginalPersonality.GetName().compareToIgnoreCase(CardManager.Personality_Card[j].GetName()) == 0)
                            			CardManager.Personality_Card[j].Status = true;
                            	}
                            	return true;
                            }//The Bursar
                            else if(object.contains("minion"))
        	                    {
        	                	
        	                    System.out.println("Enter the player index you want to move his minion.");
        	                    int PlayerIndex = scan.nextInt() - 1;
        	                    String choice = "";
        	                    String ActivateSmallGod = "";
        	                    if(ListPlayer.get(player).CanPlaySmallGod())
        	                	{
        	                		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
        	                		ActivateSmallGod = scan.next();
        	                	}
        	                	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
        	                	{
        	                		ListPlayer.get(player).DesactivateSmallGod();
        	                	}
        	                	else
        	                	{
                            	if(!ListPlayer.get(PlayerIndex).HasInterruptCard())
                                {
                                	System.out.println("Player " + PlayerIndex + "has an interrupt card. Player " + PlayerIndex + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                }
                            	if(choice.compareToIgnoreCase("no") == 0)
                            	{
									System.out.println("Enter area index from which u want to move his minion from:");
									int area = scan.nextInt();
														
									GameBoard.RemoveMinion(area, ListPlayer.get(PlayerIndex).GetColor());
									
									System.out.println("Enter the player index you want to move his minion.");
									int PlayerIndex2 = scan.nextInt() - 1;
									System.out.println("Enter area index to which you want to move his minion:");
									int area2 = scan.nextInt();
									
									GameBoard.RemoveMinion(area2, ListPlayer.get(PlayerIndex2).GetColor());
									GameBoard.PlaceMinion(area2, ListPlayer.get(PlayerIndex));
									GameBoard.RemoveMinion(area, ListPlayer.get(PlayerIndex2).GetColor());
                            	}
								else
									ListPlayer.get(PlayerIndex).RemoveInterruptCard();
        	                	}
                            }
                            //The Chair of Indefinite Studies
                            else if(object.contains("cards"))
                            {   
                            	List<Cards> hand=new ArrayList<Cards>();
                       
                            	System.out.println("Enter the player index you want to change your hand with");
                                int PlayerIndex = scan.nextInt() - 1;
                                String choice = "";
                                String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(!ListPlayer.get(PlayerIndex).HasInterruptCard())
                                {
                                	System.out.println("Player " + PlayerIndex + "has an interrupt card. Player " + PlayerIndex + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                }
                            	if(choice.compareToIgnoreCase("no") == 0)
                            	{
									hand=ListPlayer.get(CurrentPlayer).PlayerCards;
									ListPlayer.get(CurrentPlayer).PlayerCards=ListPlayer.get(PlayerIndex).PlayerCards;
									ListPlayer.get(PlayerIndex).PlayerCards=hand;
								
                            	}
								else
									ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                            	}
                            }
                        
                        }
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("assassinate") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
                            int amount = (int)object.charAt(0);
                            
                        	
                        	//Burleigh & Stronginth
                            if(object.contains("minion"))
                            {
                                System.out.println("Enter the player index you want to assassinate his minion.");
                                int PlayerIndex = scan.nextInt() - 1;
                                String choice = "";
                                String ActivateSmallGod = "";
                                if(ListPlayer.get(player).CanPlaySmallGod())
                            	{
                            		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		ActivateSmallGod = scan.next();
                            	}
                            	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	{
                            		ListPlayer.get(player).DesactivateSmallGod();
                            	}
                            	else
                            	{
                            	if(!ListPlayer.get(PlayerIndex).HasInterruptCard())
                                {
                                	System.out.println("Player " + PlayerIndex + "has an interrupt card. Player " + PlayerIndex + ", do you want to play it? (yes/no)");
                                	choice = scan.next();
                                }
                            	if(choice.compareToIgnoreCase("no") == 0)
                            	{
                                System.out.println("Enter area index ");
                                int area = scan.nextInt();
                                                                       
                                GameBoard.RemoveMinion(area, ListPlayer.get(PlayerIndex).GetColor());
                            	}
								else
									ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                            	}
                            }
                        	
                        }
                        
                        // ------------------------------- BEGIN SHUFFLE ------------------------------
                        
                        else if (currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("shuffle") == 0)
                        {
                        	//History Monks
                        	//Shuffle Discarded Cards
                        	if (DiscardCards != null && DiscardCards.size() > 0)
                        	{
                        		long seed = System.nanoTime();
                        		Collections.shuffle(DiscardCards, new Random(seed));
                        		
                        		if (DiscardCards.size() > 4)
                        		{
                        			for (int i = 0; i < 4; i++)
                        			{
                        				Cards c = DiscardCards.remove(0);
                        				this.ListPlayer.get(player).AddPlayerCard(c);
                        			}
                        		}
                        		else
                        		{
                        			for (int i = 0; i< DiscardCards.size(); i++)
                        			{
                        				Cards c = DiscardCards.remove(0);
                        				this.ListPlayer.get(player).AddPlayerCard(c);
                        			}
                        		}
                        		
                        	}
                        	
                        	return true;
                        }
                        
                        // ------------------------------ END SHUFFLE -----------------------------
                        
                        // ------------------------------ BEGIN ROLL ------------------------------
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("roll") ==0)
                        {
                        	System.out.println("Should never come here because all verbs with roll are exceptions");
                        
                        }
                        // ---------------------- END ROLL ---------------------------------------
                        
                        // ---------------------- BEGIN MOVE -------------------------------------
                        
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("move") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
                             int amount = Character.getNumericValue(object.charAt(0));
                             
                             String thisCardName = CardPlayed.GetName().toLowerCase();
                             
                             // ====================== begin rincewind ===========================
                             
                             if(thisCardName.equalsIgnoreCase("Rincewind"))
                        	 {
                        		 int source;
                        		 int destination;
                        		 Colors playerColor = this.ListPlayer.get(player).GetColor();
                        		 
                        		 // Scanner scan = new Scanner(System.in);
                                 do
                                 {
                            		 System.out.println("Enter area index from where you want to move minion-it should have troubleMarker");
                            		 source = scan.nextInt();
                                 }while(!this.GameBoard.AreaHasTrouble(source) && !(this.GameBoard.CountPlayerMinionsArea(playerColor, source) > 0));
                        		 
                                 do
                                 {
                            		 System.out.println("Enter area index to where you want to move minion-it should be adjacent");
                            		 destination = scan.nextInt();
                            		 
                                 }while (!this.GameBoard.AreaAdjacency(source, destination));

                        		
                        		
                        			 GameBoard.RemoveMinion(source,ListPlayer.get(CurrentPlayer).GetColor()) ;
                        			 GameBoard.PlaceMinion(destination, ListPlayer.get(CurrentPlayer)); 
                        			 
                        			 return true;
                        		
                        	 }
                             
                             // ================================ end  rincewind ======================================
                             
                             //The Duckman -//Foul Ole Ron--//Canting Crew
                             else if(object.contains("minion"))
                             {                          	
                            	 int PlayerIndex ;
                            	 boolean hasMinion = false;
                            	 boolean sourceHasNoMinion = true;
                            	 int Source;
                            	 int Destination;
                            	 
                            	 //Make sure player chooses anyone but himself and area has minion
                            	 do
                            	 {
	                            	 
                            		 System.out.println("Choose the index of the player (other than yourself) that you want to move a minion?");
                            		 System.out.println("The player you select needs to have at least 1 minion on the board");
	                            	 PlayerIndex = scan.nextInt() - 1;
	                            	 
	                            	 //Get Color of player
	                            	 Colors PlayerColor = this.ListPlayer.get(PlayerIndex).GetColor();
	                            	 
	                            	 //Does player have a minion on the board
	                            	 if (this.GameBoard.CountPlayerMinions(PlayerColor) > 0)
	                            	 {
	                            		 hasMinion  = true;
	                            	 }
	                            	 
                            	 } while (PlayerIndex == player && !hasMinion);
                            	 
                            	 String choice = "";
                            	 String ActivateSmallGod = "";
                            	 
                            	 if(ListPlayer.get(player).CanPlaySmallGod())
                            	 {
                            		 this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                            		 ActivateSmallGod = scan.next();
                            	 }
                                 
                            	 if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                            	 {
                            		 ListPlayer.get(player).DesactivateSmallGod();
                            	 }

                            	 else
                            	 {
                            		 if(ListPlayer.get(PlayerIndex).HasInterruptCard())
                            		 {
                            			 System.out.println("Player " + PlayerIndex + "has an interrupt card. Does player " + PlayerIndex + "want to play the interupt?");
                            			 choice = scan.next();
                            			 if(choice.compareToIgnoreCase("yes") == 0)
                            			 {
                            				 ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                            				 return true;
                            			 }

                            		 }
                            	 }
                             	
                            	 //Move minion only if area contains a minion for the player
                            	 do
                            	 {
                            		 
	                            	 System.out.println("Enter the area index from where you want to move minion");
	                            	 Source = scan.nextInt();
	                            	 
	                            	//Get Color of player
	                            	 Colors PlayerColor = this.ListPlayer.get(PlayerIndex).GetColor();
	                            	 
	                            	 if (this.GameBoard.CountPlayerMinionsArea(PlayerColor, Source) > 0)
	                            	 {
	                            		 sourceHasNoMinion = false;
	                            	 }
                            	 }
                            	 while (sourceHasNoMinion);
                            	 
                            	 //Will continue asking until user enters adjacent area.
                            	 do
                            	 {
	                            	 System.out.println("Enter the area index to where you want to move minion-it should be adjacent");
	                            	 Destination = scan.nextInt();
	                            	 
                            	 }while(!GameBoard.ListArea.get(Source).AreaAdjacency(Destination));
                            	 
                            	 thisCardName = CardPlayed.GetName().toLowerCase();
                            	 
                            	 if(thisCardName.equalsIgnoreCase("The Duckman") || thisCardName.equalsIgnoreCase("Foul Ole Ron") ||thisCardName.equalsIgnoreCase("Canting Crew"))
                            	 {
                            		 
                            		 //Source and destination are adjacent
                            		 
                        			 GameBoard.RemoveMinion(Source,ListPlayer.get(PlayerIndex).GetColor()) ;
                        			 GameBoard.PlaceMinion(Destination, ListPlayer.get(PlayerIndex));
                        			 
                            		 
                            		 return true;
                            	 }
                            	 
                        		 //Rincewind
                            	 else if(thisCardName.equalsIgnoreCase("Rincewind"))
                            	 {
                            		 int source;
                            		 int destination;
                            		 Colors playerColor = this.ListPlayer.get(player).GetColor();
                            		 
                            		 // Scanner scan = new Scanner(System.in);
                                     do
                                     {
	                            		 System.out.println("Enter area index from where you want to move minion-it should have troubleMarker");
	                            		 source = scan.nextInt();
                                     }while(this.GameBoard.AreaHasTrouble(source) && this.GameBoard.CountPlayerMinionsArea(playerColor, source) > 0);
                            		 
                                     do
                                     {
	                            		 System.out.println("Enter area index to where you want to move minion-it should be adjacent");
	                            		 destination = scan.nextInt();
	                            		 
                                     }while (!this.GameBoard.AreaAdjacency(source, destination));

                            		
                            		 if(GameBoard.GetArea(source).HasTroubleMaker() && GameBoard.ListArea.get(source).AreaAdjacency(destination))
                            		 {
                            			 GameBoard.RemoveMinion(source,ListPlayer.get(CurrentPlayer).GetColor()) ;
                            			 GameBoard.PlaceMinion(destination, ListPlayer.get(CurrentPlayer)); 
                            		 }//Dorfl--//Hobsons's Livery Stable
                            	 }
                             }
									
                     		 else if(CardPlayed.GetName()=="Dorfl" || CardPlayed.GetName()=="Hobsons's Livery Stable")
                             {
            
                         	
                             System.out.println("Enter area index from where you want to move minion");
                             int _Source = scan.nextInt();
                             System.out.println("Enter area index to where you want to move minion");
                             int _Destination = scan.nextInt();
                             
                             GameBoard.RemoveMinion(_Source,ListPlayer.get(CurrentPlayer).GetColor()) ;
                        	 GameBoard.PlaceMinion(_Destination, ListPlayer.get(CurrentPlayer)); 
                             
                             }
                                     
                        }
                        // ------------------------ END MOVE -----------------------------------------
                        
                        // ------------------------ BEGIN REMOVE -------------------------------------
                        
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("remove") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
                            int amount = (int)object.charAt(0);
                          
                            if (object.contains("minion") && CardPlayed.GetName()=="The Dean"  )
                        	{
                             	//unreal state is area number 2
                                 System.out.println("Enter the player index you want to move his building ");
                                 int PlayerIndex = scan.nextInt() - 1;
                                 String choice = "";
                                 String ActivateSmallGod = "";
                                 if(ListPlayer.get(player).CanPlaySmallGod())
                             	{
                             		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                             		ActivateSmallGod = scan.next();
                             	}
                             	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                             	{
                             		ListPlayer.get(player).DesactivateSmallGod();
                             	}
                             	else
                             	{
                             	if(!ListPlayer.get(PlayerIndex).HasInterruptCard())
                                 {
                                 	System.out.println("Player " + PlayerIndex + "has an interrupt card. Player " + PlayerIndex + ", do you want to play it? (yes/no)");
                                 	choice = scan.next();
                                 }
                             	if(choice.compareToIgnoreCase("no") == 0)
                             	{
									GameBoard.RemoveMinion(1, ListPlayer.get(PlayerIndex).GetColor());
                             	}
								else
									ListPlayer.get(PlayerIndex).RemoveInterruptCard();
                             	}
                        	}
                            //The Auditors
                            else if (object.contains("player order") && CardPlayed.GetName()=="The Auditors"  )
                            {
                            	 	Player _CurrentPlayer=ListPlayer.get(player);
                            	 	
                            	    for (Player thisPlayer : this.ListPlayer)
                            	 	{
                            	 		if (thisPlayer !=_CurrentPlayer)
                            	 		{
                            	 			String choice = "";
                            	 			String ActivateSmallGod = "";
                                            if(ListPlayer.get(player).CanPlaySmallGod())
                                        	{
                                        		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                                        		ActivateSmallGod = scan.next();
                                        	}
                                        	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                                        	{
                                        		ListPlayer.get(player).DesactivateSmallGod();
                                        	}
                                        	else
                                        	{
                                        	if(!thisPlayer.HasInterruptCard())
                                            {
                                            	System.out.println("Player " + thisPlayer.GetPlayerNumber() + "has an interrupt card. Do you want he wants to play it?");
                                            	choice = scan.next();
                                            }
                                        	if(choice.compareToIgnoreCase("no") == 0)
                                        	{
												System.out.println("Enter area number from where you want to remove Minion");
												int area = scan.nextInt();
												GameBoard.RemoveMinion(area, thisPlayer.GetColor());
                                        	}
											else
												thisPlayer.RemoveInterruptCard();
                                        	}
                            	 		}
                            	 
                            	 	}
                            }
                            
                        }
                        
                        // -------------------------- END REMOVE ---------------------------------------
                	
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("play") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
                            int amount = (int)object.charAt(0);
                            //Pondor Stibbons ,Drumknott
                            
                            if (object.contains("2 other cards")) 
                            { 
                            	System.out.println("Enter card index of first card you want to play"); 
                            	int index0 = scan.nextInt();
                            	System.out.println("Enter card index of second card you want to play"); 
                            	int index1 = scan.nextInt();
                            	//discard current card first to avoid recursion
                            	//todo
                            	ListPlayer.get(player).PlayerCards.remove(IndexOfCardPlayed);
                            	
                            	if(PlayCard(player, index0))
                            		System.out.println("Player " + player + "played card index " + index0 + "successfully");
                            	if(PlayCard(player, index1))
                            		System.out.println("Player " + player + "played card index " + index1 + "successfully");
                           
                            	
                            }
                        }
                        
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("replace") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
                            int amount = (int)object.charAt(0);
                            
                            //Sybil Vimes
                            if (object.contains("1 building of your own with another player"))
                            {
                            	/*TODO
                            	 * System.out.println("Enter one of your bulding Index"); 
                            	  
                            	 int BuildingIndex= scan.nextInt(); 
                            	 System.out.println("Enter with whome you want to exchange building"); 
                            	  
                            	 int AnotherPlayerIndex=scan.nextInt(); 
                            	 */ 

                            }
                            else  if (object.contains("1 building of your own with another player"))
                            {
                            	//pay the cost +must has a trouble marker
                            }
                            	
                        }
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("stop") ==0)
                        {
                        	String object = currentEffect.GetObjectList().get(verbCount);
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
                	       	
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("end") ==0)
                        {
                        	//RIOT CARD : Games end of there are more then eight trouble markers
                        	System.out.println("You should not come here");
                        	
                        }
                        // ------------------------ BEGIN SELECT -----------------------------------
                        
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("select") ==0)
                        {
                        	//Queen Molly
                        	System.out.println("Select one player:");
                        	int playerIndex= scan.nextInt() - 1;

                        	String choice = "";
                        	String ActivateSmallGod = "";
                        	if(ListPlayer.get(player).CanPlaySmallGod())
                        	{
                        		this.Print("Player " + player + " has the city Area card Small Gods available. Would you like to play it?");
                        		ActivateSmallGod = scan.next();
                        	}
                        	if(ActivateSmallGod.compareToIgnoreCase("yes") == 0)
                        	{
                        		ListPlayer.get(player).DesactivateSmallGod();
                        	}
                        	else
                        	{
                        		if(!ListPlayer.get(playerIndex).HasInterruptCard())
                        		{
                        			System.out.println("Player " + playerIndex + "has an interrupt card. Player " + playerIndex + ", do you want to play it? (yes/no)");
                        			choice = scan.next();
                        		}
                        		if(choice.compareToIgnoreCase("no") == 0)
                        		{
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
                        		else
                        			ListPlayer.get(playerIndex).RemoveInterruptCard();
                        	}
                        }
                        
                        // ------------------------- END SELECT -----------------------------
                        
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("putminion") ==0)
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
                        else if(currentEffect.GetVerbList().get(verbCount).compareToIgnoreCase("loan") ==0)
                        {
                        	this.GetPlayerLoanBank("Current Finances: ", ListPlayer.get(player));
                        	
                        	//Player takes a 10$ loan
                        	ListPlayer.get(player).GetLoan(10);
                        	
                        	//Player owed 12$ at end of games
                        	ListPlayer.get(player).AddtoPayBack(12);
                        	
                        	//Player needs to pay back 12 at end of game or player will lose 15 points
                        	ListPlayer.get(player).IncreaseLostPoints(15);
                        	
                        	this.GetPlayerLoanBank("New Finances: ", ListPlayer.get(player));
                        	
                        	return true;
                        }
                        else
                        {
                            System.out.println("!!!!!!! Unknown verb found !!!!!!!! : " + currentEffect.GetVerbList().get(verbCount));
                        }
                
            
            
                } //end-for
				//NOCOMMIT
				//activating the area effect
                ActivateCityAreaEffect(player);
        	}
        	
        } //else for normal verb
        	// ************************* END GAY SECTION ***************************************
        
            //order of execution not important. Can only execute one of them

            
        return ActionStatus;
    }
    
    /**
     * Function will remove building and change the status of thee CardManager. 
     * It will also discard the CityArea from player 
     * 
     * @param AreaNumber
     * @param player
     */
    public void RemoveBuldingInBoard(int AreaNumber, Player player)
    {
    	Area currentArea = GameBoard.ListArea.get(AreaNumber);
		
		boolean RemoveSuccess = GameBoard.RemoveBuilding(AreaNumber, player);
		if(RemoveSuccess)
		{
			//remove city area cards from player hand and put it back to deck
			for(CityAreaCards _PlayerArea : player.ListCityAreaCards)
			{
				if(_PlayerArea.GetName().compareToIgnoreCase(currentArea.GetName())== 0)
				{
					player.ListCityAreaCards.remove(_PlayerArea);
					break;
				}
			}
			//remove city area cards from player hand and put it back to deck
			for(int i=0; i<CardManager.CityArea_Cards.length; i++)
			{
				if(CardManager.CityArea_Cards[i].GetName().compareToIgnoreCase(currentArea.GetName())== 0)
				{
					CardManager.CityArea_Cards[i].Status = true;
					break;
				}
			}
		}
    }
    
    /**
     * Play the event on of the cards
     * @param CardPlayed
     * @param player
     * @param UseCardName
     * @return true once card is played
     */
    public boolean PlayEvent(Cards CardPlayed, int player, boolean UseCardName)
	{
    	Cards NewCardPlayed = CardManager.GetCard(CardType.EventCards);
    	String cardName = NewCardPlayed.GetName().toLowerCase();
    	if(UseCardName)
    		cardName = CardPlayed.GetName();
    	
		boolean ActionSuccess = true;
		System.out.println("Activating the event effect now");
        
        if(cardName.compareToIgnoreCase("The Dragon") == 0)
        {
            System.out.println("Dragon Event \n===========================================");
            int AreaAffected = GameBoard.RollDie();
            BoardDie = AreaAffected;
            
            System.out.println("Area " + AreaAffected + " will be affected by the fire. Removing all minions/trouble marker/building in it");
            for(int i=0; i<TotalPlayer; i++)
            {
                RemoveBuldingInBoard(AreaAffected-1, ListPlayer.get(i));
                GameBoard.RemoveTroll(AreaAffected);
                GameBoard.Removetrouble(AreaAffected);
                GameBoard.RemoveDemon(AreaAffected);
                GameBoard.RemoveMinion(AreaAffected, ListPlayer.get(i).GetColor());
            }
        }
        else if(cardName.compareToIgnoreCase("Flood") == 0)
        {
            System.out.println("Floood Event \n===========================================");
            for(int FloodCount=0; FloodCount<2; FloodCount++)
            {
                int AreaAffected = GameBoard.RollDie();
                CurrentDie = AreaAffected;
                
                //If area doesn't belong to the ones not connected to a river
                if(AreaAffected!=3 && AreaAffected!= 6)
                {
                	for(int EachPlayer= CurrentPlayer; EachPlayer<(CurrentPlayer+4); EachPlayer++)
                	{
                		if(EachPlayer > 4) EachPlayer=EachPlayer%4;
                		//NOCOMMIT

                		if (this.GameBoard.CountPlayerMinions(this.ListPlayer.get(EachPlayer).GetColor()) > 0)
                		{

                			int[] ListAdjacentArea = GameBoard.ListArea.get(AreaAffected-1).GetAdjAreas();

                			boolean userChoice= false;

                			Scanner scan = new Scanner(System.in);

                			while(!userChoice)
                			{   
                				for(int i=0; i<ListAdjacentArea.length; i++)
                				{

                					System.out.println("Area " + ListAdjacentArea[i] + "is adjacent to " + AreaAffected + ". Would you like to move your minion there?");
                					String chooseMove = scan.next();
                					if(chooseMove.compareToIgnoreCase("yes") == 0)
                					{

                						GameBoard.RemoveMinion(AreaAffected, ListPlayer.get(EachPlayer).GetColor());
                						GameBoard.PlaceMinion(AreaAffected, ListPlayer.get(EachPlayer));
                						userChoice = true;
                						break;
                					}

                				}
                			}
                		} //END IFF


                	}
                }
                
            }
        }
        else if(cardName.compareToIgnoreCase("Fire") == 0)
        {
            System.out.println("Fire Event \n===========================================");
            boolean ContinueRolling = true;
            int AffectedArea =-1;
            int PrevArea = -1;
            do
            {
                AffectedArea = GameBoard.RollDie();
                System.out.println("Area affected " + AffectedArea + " - " + GameBoard.ListArea.get(AffectedArea-1).GetName());
                
                boolean HasBuilding = GameBoard.ListArea.get(AffectedArea-1).HasBuilding();
                boolean RollIsAdjacent = false;
                if(PrevArea ==-1)
                {
                	RollIsAdjacent = true;
                	PrevArea = AffectedArea;
                }
                else
                {
                	RollIsAdjacent = GameBoard.AreaAdjacency(PrevArea, AffectedArea);//.ListArea.get(PrevArea-1).AreaAdjacency(AffectedArea-1);
                }
                
                if(RollIsAdjacent && HasBuilding)
                {
                    ContinueRolling = true;
                    for(int i=0; i<TotalPlayer; i++)
                    {
                        RemoveBuldingInBoard(AffectedArea-1, ListPlayer.get(i));
                        Print("Removed building in" + "Area affected " + AffectedArea + " - " + GameBoard.ListArea.get(AffectedArea-1).GetName());
                    }  
                    PrevArea = AffectedArea;
                } 
                else
                    ContinueRolling = false;
            }while(ContinueRolling);
            
        }
        else if(cardName.compareToIgnoreCase("Fog") == 0)
        {
            System.out.println("Fog Event \n===========================================");
            //Discard top five cards
            Cards DiscardCard = null;
            
            for(int i=0; i<5; i++)
            {
                DiscardCard = CardManager.GetCard(CardType.GreenCards);
                System.out.println("Card " + DiscardCard.GetName() + " has been discarded");

                DiscardCards.add(DiscardCard);
            }
        }
        else if(cardName.compareToIgnoreCase("Riots") == 0)
        {
            System.out.println("Riots Event \n===========================================");
            //traverse Area to count
            if(GameBoard.CountTroubleMaker() >= 8)
            {
                System.out.println("Game is ending preemptively because EventCard RIOT is played");
                //calculating points and determine winner
                ActivateGameEnd();
            }
        }
        else if(cardName.compareToIgnoreCase("Explosion") == 0)
        {
            System.out.println("Explosion Event \n===========================================");
            int AreaAffected = GameBoard.RollDie();
            
            System.out.println("Removing building for all player in Area " + AreaAffected + " - " + GameBoard.ListArea.get(AreaAffected-1).GetName());
            for(int i=0; i<TotalPlayer; i++)
            {
                RemoveBuldingInBoard(AreaAffected-1, ListPlayer.get(i));
            }
        }
        else if(cardName.compareToIgnoreCase("Earthquake") == 0)
        {
            System.out.println("Earthquake Event \n===========================================");
            int AreaAffected0 = GameBoard.RollDie();
            int AreaAffected1 = GameBoard.RollDie();
            
            System.out.println("Removing building in Area " + AreaAffected0 + ".");
            System.out.println("Removing building in Area " + AreaAffected1 + ".");
            for(int i=0; i<TotalPlayer; i++)
            {
                RemoveBuldingInBoard(AreaAffected0-1, ListPlayer.get(i));
                RemoveBuldingInBoard(AreaAffected1-1, ListPlayer.get(i));
            }
        }
        else if(cardName.compareToIgnoreCase("Mysterious Murders") == 0)
        {
        	Scanner scan = new Scanner(System.in);
            System.out.println("Mysterious Murdered Event \n===========================================");
            int AreaAffected = 0;
            //rotate through player left-side
            for(int i=0; i<TotalPlayer; i++)
            {
                int CurrentPlayerTurn = CurrentPlayer-i;
                if(CurrentPlayerTurn < 0) CurrentPlayerTurn=TotalPlayer-1;
                
                AreaAffected = GameBoard.RollDie();
                System.out.println("Area affected : " + AreaAffected + " - " + GameBoard.ListArea.get(AreaAffected-1).GetName());
                
                // TODO:remove a minion in that area of their choice
                System.out.println("Enter player index on which to remove the minion");
                int PlayerIndex = scan.nextInt() - 1;
                GameBoard.RemoveMinion(AreaAffected, ListPlayer.get(PlayerIndex).GetColor());
            }
        }
        else if(cardName.compareToIgnoreCase("Demons From The Dungeon Dimension") == 0)
        {
            System.out.println("Demons From The Dungeon Dimension \n===========================================");
            for(int i=0; i<4; i++)
            {
                int AreaAffected = GameBoard.RollDie();
                System.out.println("Area affected " + AreaAffected);
                if(!GameBoard.ListArea.get(AreaAffected-1).GetIsTrouble())
                {
                	System.out.println("Area does not have a troublemaker yet. Adding one");
                	GameBoard.ListArea.get(AreaAffected-1).AddTroubleMaker(new Pieces(PieceType.TroubleMarker, Colors.None));
                }
                System.out.println("Adding demons in Area " + AreaAffected);
                GameBoard.ListArea.get(AreaAffected-1).AddDemons(new Pieces(PieceType.Demon, Colors.None));
            }
        }
        else if(cardName.compareToIgnoreCase("Subsidence") == 0)
        {
        	Scanner scan = new Scanner(System.in);
            System.out.println("Subsidence Event \n===========================================");
            
            for(int i=0; i<TotalPlayer; i++)
            {
                //TODO For each building in the board, pay 2$ or remove it
            	for(int a=0; a<GameBoard.ListArea.size(); a++)
            	{
            		if(GameBoard.ListArea.get(a).GetBuilding().GetPieceColor() == ListPlayer.get(i).GetColor())
            		{
            			System.out.println("Building owned by player " + (i+1) + " is found. Would you like to pay 2$ to keep it? (yes/no)");
            			String choice = scan.next();
            			if(choice.compareToIgnoreCase("yes") == 0)
            			{
            				GameBoard.AddToBank(2);
            				ListPlayer.get(i).DeductFromMoney(2);
            			}
            			else
            			{
            				System.out.println("Removing building in Area " + (a+1)+ " - " + GameBoard.ListArea.get(a).GetName());
            				RemoveBuldingInBoard(a, ListPlayer.get(i));
            			}
            		}
            	}
            }
        }
        else if(cardName.compareToIgnoreCase("Bloody Stupid Johnson") == 0)
        {
            System.out.println("Bloody Stupid Johson \n===========================================");
            int AreaAffected = GameBoard.RollDie();
            CurrentDie = AreaAffected;
            System.out.println("Current area affected is " + AreaAffected + " - " + GameBoard.ListArea.get(AreaAffected-1).GetName());
            //disable effect of City Area of that card by discarding the card
            for(int i=0; i<TotalPlayer; i++)
            {
                for(int c=0; c<ListPlayer.get(i).ListCityAreaCards.size(); c++)
                {
                	//boolean 
                    if(ListPlayer.get(i).ListCityAreaCards.get(c).GetName().compareToIgnoreCase(GameBoard.ListArea.get(AreaAffected-1).GetName()) == 0)
                    {
                    	System.out.println("City area card " + GameBoard.ListArea.get(AreaAffected-1).GetName() + " is no longer in play. Discarding it.");
                    	DiscardCards.add(ListPlayer.get(i).ListCityAreaCards.get(c));
                    	ListPlayer.get(i).ListCityAreaCards.remove(c);
                        GameBoard.RemoveMinion(c, ListPlayer.get(i).GetColor());
                    }
                }
            }
            
            //remove 1 minion of that area
            GameBoard.RemoveMinion(AreaAffected, ListPlayer.get(player).GetColor());
        }
        else if(cardName.compareToIgnoreCase("Trolls") == 0)
        {
            System.out.println("Trolls \n===========================================");
            int AreaAffected = 0;
            
            for(int i=0; i<3; i++)
            {
                AreaAffected = GameBoard.RollDie();
                
                //test if current area has a minion, if they do, we add a troublemaker as per rule
                if(GameBoard.ListArea.get(AreaAffected-1).GetMinionCount(Colors.None) > 0)
                {
                	System.out.println("Area " + AreaAffected + " has at least one minion. Adding a TroubleMarker on area.");
                	GameBoard.ListArea.get(AreaAffected-1).AddTroubleMaker(new Pieces(PieceType.TroubleMarker,Colors.None));
                }
                
                System.out.println("Adding Troll in Area " + AreaAffected + " - " + GameBoard.ListArea.get(AreaAffected-1).GetName());
                GameBoard.ListArea.get(AreaAffected-1).AddTrolls(new Pieces(PieceType.Troll,Colors.None));
            }
        }
        else
        {
            System.out.println("!!!!ERROR UNKNOWN EVENT ENCOUNTERED. Card name " + CardPlayed.Name);
        }
        
		return ActionSuccess;
	}
	
    
    /**
     * RemoveTrouble Marker function
     * @param player to 
     * @return 
     */
	public boolean RemoveTrouble()
	{
		boolean ActionSuccess = false;
		boolean NoTroubleArea = true;
		int AreaNumber;

		//Does board have troublemaker
		if (this.GameBoard.BoardHasTrouble())
		{

			//Print board Status
			this.GameBoard.PrintState();

			do
			{
				System.out.println("Please enter the Area index you want to remove the troublemaker.");
				Scanner scan = new Scanner(System.in);
				AreaNumber = scan.nextInt();

				NoTroubleArea = !this.GameBoard.AreaHasTrouble(AreaNumber);
				scan.close();
			}while(NoTroubleArea);

			ActionSuccess = this.GameBoard.Removetrouble(AreaNumber);

			this.PrintAreaState(AreaNumber);

		}
		else
		{
			this.Print("Board does not have any Trouble Makers");
			ActionSuccess = true;
		}


		return ActionSuccess;
	}
	
	
	/**
	 * Assassinate symbol function
	 * @param player
	 * @return
	 */
	private boolean Assassinate(int player)
	{
		boolean ActionSuccess = false;
        boolean Continue = true;
        int AreaNumber;
        boolean NoPiece = false;
        Scanner scan = new Scanner(System.in);
        
        
        if (this.GameBoard.BoardHasTrouble())
        {
        	//Print State of board
        	this.GameBoard.PrintState();
        	
        	do
        	{


        		do
        		{
        			System.out.println("You are about to remove one minion or troll or demon. Please enter the Area index you want to do that. ");
        			System.out.println("******Area cannot be empty - Area has to have a trouble marker *****");

        			AreaNumber = scan.nextInt();

        		}while(this.GameBoard.EmptyArea(AreaNumber) || !this.GameBoard.AreaHasTrouble(AreaNumber));


        		//Show Current Area Info
        		this.PrintAreaState(AreaNumber);

        		String choice = "";

        		do
        		{
        			System.out.println("Do you want to remove a troll, demon or minion?");
        			choice  = scan.next();
        			String message = "";

        			if (choice.equalsIgnoreCase("minion") && this.GameBoard.CountPlayerMinionsArea(Colors.None, AreaNumber) == 0)
        			{
        				NoPiece = false;
        				message = "Area has no minions";
        			}
        			else if (choice.equalsIgnoreCase("demon") && this.GameBoard.AreaDemonCount(AreaNumber) == 0)
        			{
        				NoPiece = false;
        				message = "Area has no demons";
        			}
        			else if (choice.equalsIgnoreCase("troll") && this.GameBoard.AreatrollCount(AreaNumber) == 0)
        			{
        				NoPiece = false;
        				message = "Area has no trolls";
        			}


        			this.Print(message);

        		}while (NoPiece);

        		if(choice.compareToIgnoreCase("demon") == 0)
        		{
        			//To activate later
        			ActionSuccess = this.GameBoard.RemoveDemon( AreaNumber);

        			Continue = !ActionSuccess;

        		}
        		else if(choice.compareToIgnoreCase("troll") == 0)
        		{
        			//to activate later
        			ActionSuccess = this.GameBoard.RemoveTroll( AreaNumber);
        			Continue = !ActionSuccess;
        		}
        		else if(choice.compareToIgnoreCase("minion") == 0)
        		{
        			int PlayerIndex;

        			do
        			{

        				System.out.println("Please enter the player you want to remove the index from:");
        				System.out.println("You cannot assassinate your own minion.");

        				System.out.println(this.ShowPlayerIndexAndColor());

        				PlayerIndex  = scan.nextInt();

        			}while (PlayerIndex - 1 == player);

        			//To activate later
        			ActionSuccess = this.GameBoard.RemoveMinion( AreaNumber, this.ListPlayer.get(PlayerIndex-1).GetColor());
        			Continue = !ActionSuccess;
        		}
        		else
        		{
        			System.out.println("Invalid choice:  " + choice+". Please try again. ");
        		}
        	} while(Continue);

        	//Show Current Area Info
        	this.PrintAreaState(AreaNumber);
        }
        else 
        {
        	this.Print("Board has not trouble markers, No assassinations for you");

        	ActionSuccess = true;
        }
        scan.close();
        return ActionSuccess;
	}
	
	/**
	 * Place minion on board symbol
	 * @param player
	 * @return if action was successful
	 */
	private boolean PutMinion(int player)
	{
		boolean ActionSuccess = false;
		int AreaNumber;
		boolean CanNotPut = true;
		
		 //Get player object
        Player thisPlayer = this.ListPlayer.get(player);
		
		
		this.GameBoard.PrintState();
		this.ShowBoardState();
		
		do 
		{
			this.Print("");
			this.Print("Please enter the Area index you want to put your minion. Keep in mind that you must place minion in either an area that you already have a minion in or an adjacent area. ");
			Scanner scan = new Scanner(System.in);
        
			 AreaNumber = scan.nextInt();
			 
			 if (this.GameBoard.CountPlayerMinionsArea(thisPlayer.GetColor(), AreaNumber) > 0)
			 {
				 CanNotPut = false;
			 }
			 else
			 {
				for (int i : this.GameBoard.ListArea.get(AreaNumber-1).GetAdjAreas())
	    		{
	    			if (this.GameBoard.ListArea.get(i).GetMinionCount(thisPlayer.GetColor()) > 0)
	            	{
	    				CanNotPut = false;
	            	}
	    		}
			 }
			 scan.close();
		}while (CanNotPut);
        
		ActionSuccess = GameBoard.PlaceMinion(AreaNumber,ListPlayer.get(player));
        
        this.PrintAreaState(AreaNumber);
        
		return ActionSuccess;
	}
	
	/**
	 * Place building on board
	 * @param player
	 * @return If action was
	 */
	private boolean PutBuilding(int player)
	{
		boolean ActionSuccess = false;
		this.GameBoard.PrintState();
		this.Print("");
		
        System.out.println("Please enter the Area index you want to put your building. Keep in mind that you cannot build in an area that already contains either a building or troublemaker. ");
        
        Scanner scan = new Scanner(System.in);
        int AreaNumber = scan.nextInt();
		ActionSuccess =GameBoard.PlaceBuilding(AreaNumber,ListPlayer.get(player));
		
		if(ActionSuccess)
		{
			//search for the right city area cards
			for(int i=0; i<CardManager.CityArea_Cards.length; i++)
			{
				if(CardManager.CityArea_Cards[i].GetName().compareTo(GameBoard.ListArea.get(AreaNumber-1).GetName()) == 0)
				{
					CardManager.CityArea_Cards[i].Status = false;
					ListPlayer.get(player).AddCityAreayCard(CardManager.CityArea_Cards[i]);	
				}
					
			}
			
		}
			
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
		return ((PlayerIndex <= 4) && (PlayerIndex >= 0));
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
		{
			return false;
		}
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
			{
				return false;
			}
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
		{
			
			return GameBoard.PlaceMinion(AreaNumber, ListPlayer.get(player-1));
		}
		
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
		//OLD-Code to randomly select a player. Will not be used in this build because we will always start with player 1
		/*int LastDieValue = 0;
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
			
		}*/
		CurrentPlayer = 0;
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
		DiscardCards = new ArrayList<Cards>();
		GameBoard = new Board();
		HasGameEnded = false;
		
		for(int PlayerCount = 0; PlayerCount <TotalPlayer; PlayerCount++)
		{
			//Assign set of RandomCard to player
	        List<Cards> ListPlayerCards = new ArrayList<Cards>();
	        int PlayerIndex = ListPlayer.size();
	        
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
	 * Assign the player playing to the CurrentPlayer var
	 * @param p
	 */
	public void SetCurrentPlayer(int p)
	{
		CurrentPlayer = p;
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
		
     	//Lord de Word Lord=1 , Selachii=2 , Lord Rust=5 should have certain number of city area under control
		PersonalityCards CurrentPersonality = ListPlayer.get(CurrentPlayer).GetPlayerPersonality();
		String CardName = CurrentPersonality.GetName();
	
		if (CardName.contains("Word")|| CardName.contains("Selachii")|| CardName.contains("Rust"))
		{
			int control = 0;
			
			for (Area area : this.GameBoard.ListArea)
			{
				if (area.AreaControllled(ListPlayer.get(this.CurrentPlayer).GetColor())) 
				{
						control++;
				}
			}
			
			if (this.TotalPlayer==2)
			{
				//Does player control 7 areas
			
				
				if ( control >=7 )
				{
					 WiningCondition=true;
				}	
			}
			
			else if (this.TotalPlayer==3)
			{
				if ( control >=5 )
				{
					
					 WiningCondition=true;
				}	
			}
			
			else if (this.TotalPlayer==4)
			{
				if ( control >=4 )
				{
					Print("Player controls at least 4 areas.");
					 WiningCondition=true;
				}	
			}
		}
		
		//Lord Vetinari should have certain number of minions to win the game
		else if (CardName.contains("Vetinari"))
		{
			int TotalAreaCovered = 0;
			for (Area area : this.GameBoard.ListArea)
			{
				if (area.GetMinionPoint(ListPlayer.get(CurrentPlayer).GetColor()) > 0) 
				{
					TotalAreaCovered++;
				}
			}
			if (this.TotalPlayer==2)
			{
				
				if (TotalAreaCovered>=11)
					 WiningCondition=true;
			}
			else if (this.TotalPlayer==3)
			{
				if (TotalAreaCovered>=10)
					 WiningCondition=true;
			}
			else if (this.TotalPlayer==4)
			{
				if (TotalAreaCovered>=9)
				{
					Print("Player has minion in at least 9 areas.");
					 WiningCondition=true;
				}
			}
		}
		//Dragon King of Arms  
		else if (CardName.contains("Arms"))
		{
			if (this.GameBoard.CountTroubleMaker()>=8 )
			{
				Print("Game has at least 8 troublemarkers in board.");
				 WiningCondition=true;
			}
		}
		//Commandor Vimes .If cards run out he whill be the winner
		else if (CardName.contains("Vimes"))
		{
			
			WiningCondition=NoMoreCard();
			if(WiningCondition) 
				Print("Deck has no more cards");
		}
		//Chrysoprase
		else if (CardName.contains("Chrysoprase"))
		{
			int CurrentPlayerValue = GetPlayerMoney(CurrentPlayer);
			
			if(CurrentPlayerValue >= 50)
			{
				this.Print("Current player " + (CurrentPlayer + 1) + " Cash: " + CurrentPlayerValue);
				WiningCondition=true;
			}
		}
	
		
	
	
		return WiningCondition;
	}
	
	/**
	 * @param player to inquire to see if there is any city area cards 
	 * @return true if he does have
	 */
	public boolean DoesPlayerHasCityArea(int player)
	{
		return ListPlayer.get(player).HasCityArea();
	}
	
	/**
	 * Calculate points for  a player
	 * @param playerIndex
	 * @return
	 */
    public int GetPlayerPoints(int playerIndex)
    {
    	
    	int totalPoints = 0 ;
    	
    	//Retrieve player object 
    	Player player = this.ListPlayer.get(playerIndex);
    	
    	//First calculate point from minions
    	for (Area area : this.GameBoard.ListArea)
    	{
    		if(area.GetDemonCount() == 0)
    		{
    			System.out.println("Total Minion Count (5$ for each): " + area.GetMinionCount(player.GetColor()));
        		
        		totalPoints += 5 * area.GetMinionCount(player.GetColor());	
    		}
    	}
    	
    	////Calculate Points from buildings
    	for (Area area : this.GameBoard.ListArea)
    	{
    		if(area != null && (area.GetDemonCount() == 0))
    		{
    			Pieces b = area.GetBuilding();
	    		if ( b != null && (b.GetPieceColor() == player.GetColor()))
	    		{
	    			System.out.println("Building owned by player in " + area.GetName()+". Area cost is " + area.GetAreaCost());
	        		
	    			totalPoints += area.GetAreaCost();
	    		}
    		}
    	}
    	System.out.println("");
    	ListPlayer.get(playerIndex).PayLoan(ListPlayer.get(playerIndex).GetPayBack());
    	
    	//Calculate Total Dollars
    	totalPoints += player.GetMoneyCount();
    	
    	return totalPoints;
    	
    }
	
    /**
     * Display player index with color
     * @return
     */
    private String ShowPlayerIndexAndColor()
    {
StringBuilder AllPlayers = new StringBuilder();
    	
    	for(Player player : this.ListPlayer)
    	{
    		AllPlayers.append("Player " + (player.GetPlayerNumber() + 1)+ ":" + player.GetColor() + ";");
    	}
    	
    	AllPlayers.deleteCharAt(AllPlayers.length()-1);
    	return AllPlayers.toString();
    }
    
    /**
     * Print the current state of an Area
     * @param AreaNumber
     */
    private void PrintAreaState(int AreaNumber)
    {
    	 System.out.println("AREA INFO");
         System.out.printf("%-16S %-25S  %-10s %-10s %-8s %-10s %n","area","minions","trouble?","building?","demons","trolls");
         this.GameBoard.ListArea.get(AreaNumber - 1).PrintState();
    }
    
    /**
     * 
     * @param message
     * @param player
     */
    public void PrintHandState(String message, Player player)
    {
    	System.out.println(message);
    	player.PrintCardsIndex();
    }
	
    /**
     * Display a pring out of player financials
     * @param Message
     * @param player
     */
    private void GetPlayerLoanBank(String Message, Player player)
    {
    	System.out.println(Message);
    	System.out.println("Current Balance: " + player.GetMoneyCount()) ;
    	System.out.println("Current Loan Balance: " + player.TotalLoan());
    	
    }
    
    /**
     * Display balance of player
     * @param message
     * @param player
     */
    private void GetPlayerBalance(String message, Player player)
    {
    	System.out.println(message);
    	System.out.println(player.GetMoneyCount());
    }
    
    
    /**
     * Get Player total money . Bank - Loan + Building
     */
    private int GetPlayerMoney(int PlayerIndex)
    {
    	int moneyCount = 0;
    	Player thisPlayer = this.ListPlayer.get(PlayerIndex);
    	Colors thisColor = thisPlayer.GetColor();
    	
    	moneyCount = thisPlayer.GetMoneyCount() - thisPlayer.TotalLoan();
    	
    	for (Area area : this.GameBoard.ListArea)
    	{
    		if (area.GetIsBuilt() && area.BuildingColor() == thisColor && area.GetDemonCount() == 0)
    		{
    			moneyCount += area.GetAreaCost();
    		}
    	}
    	
    	return moneyCount;
    	
    }
    
    /**
     * Assign die value to board 
     * @param i
     */
	public void SetBoardDie (int i) {BoardDie = i;}
	
	/**
	 * Retrieve Die value
	 * @return
	 */
	public int GetBoardDie () {return BoardDie;}
	
	/**
	 * Used for JUNIT testing - Place minion in each area
	 * @param player
	 */
    public void PlaceMinionInEachArea(int player)
    {
        for(int i=1; i<=GameBoard.ListArea.size(); i++)
            GameBoard.PlaceMinion(i,ListPlayer.get(player));
    }
    
    /**
     * Used for JUNIT testing - Place building in each area
     * @param player
     */
    public void PlaceBuildingInEachArea(int player)
    {
    	for (int j = 0; j <= 1; j++)
    	{	
    		int x = 1;
    		
    		if (j > 0)
    		{
    			x = 7;
    		}
    		
	        for(int i = x ;i<=GameBoard.ListArea.size(); i++)
	        {
	            //GameBoard.PlaceBuilding(i,ListPlayer.get(player+j));
	        	GameBoard.PlaceBuildingInitial(i,ListPlayer.get(player+j));
	        }
    	}
    }

    /**
     * Used for JUNIT testing - Place troll in each area
     * @param player
     */
    public void PlaceTrollInEachArea(int player)
    {
        for(int i=1; i<=GameBoard.ListArea.size(); i++)
            GameBoard.PlaceTroll(i);
    }
    
    /**
     * Used for JUNIT testing - Place demon in each area
     * @param player
     */
    public void PlaceDemonsInEachArea(int player)
    {
        for(int i=1; i<=GameBoard.ListArea.size(); i++)
            GameBoard.PlaceDemon(i);
    }
    
    /**
     * Retrieve the building count on board
     * @return
     */
    public int GetBuildingCount()
    {
    	int TotalBuilding = 0;
        for(int i=0; i<GameBoard.ListArea.size(); i++)
        {
        	Pieces b =GameBoard.ListArea.get(i).GetBuilding();
        	if(b!=null) TotalBuilding++;
        }
        return TotalBuilding;
    }
    
    /**
     * Retrieve demon count of board
     * @return
     */
	public int CountDemonsInArea()
    {
        int TotalDemons = 0;
        for(int i=0; i<GameBoard.ListArea.size(); i++)
        {
            TotalDemons +=GameBoard.ListArea.get(i).GetDemonCount();
        }
        return TotalDemons;
    }
    
	/**
	 * Retrieve troll count of board
	 * @return
	 */
	public int CountTrollsInArea()
    {
        int TotalTrolls= 0;
        for(int i=0; i<GameBoard.ListArea.size(); i++)
        {
        	TotalTrolls +=GameBoard.ListArea.get(i).GetTrollCount();
        }
        return TotalTrolls;
    }
    
	/**
	 * Get the balance of current player
	 * @param player
	 * @return
	 */
    public int GetPlayerBalance(int player)
    {
        return ListPlayer.get(player).GetMoneyCount();
    }
    
    /**
     * Are any action cards left on the board
     * @return
     */
    public boolean NoMoreCard()
    {
    	Cards g = CardManager.GetCard(CardType.GreenCards);
    	//Cards b = CardManager.GetCard(CardType.BrownCards);
    	return (g==null);
    }
    
    /**
     * Remove a minion from an area
     * @param area
     * @param player
     * @return
     */
    public boolean RemoveMinion(int area, int player)
    {
    	return GameBoard.RemoveMinion(area, ListPlayer.get(player).GetColor());
    }
    
    /**
     * Function to empty the current deck of green cards. It is only used for testing purpose 
     */
    public void EmptyCard()
    {
    	Cards c;
    	do
    	{
    		c = CardManager.GetCard(CardType.GreenCards);
    		//if(c == null) CardManager.GetCard(CardType.BrownCards);
    	}
    	while(c != null);
    	
    }
    
    /**
     * Function that will the total minion in board
     * 
     * @return total 
     */
    public int GetTotalMinion()
    {
    	int TotalMinion = 0;
    	for(int i=0; i<GameBoard.ListArea.size(); i++)
    	{
    		for(int p=0; p<TotalPlayer; p++)
    			TotalMinion = GameBoard.ListArea.get(i).GetMinionCount(ListPlayer.get(p).GetColor());
    	}
    	return TotalMinion;
    }
    
    /**
     * Return current die value. 
     * @return die value
     */
    public int ReturnCurrentDieValue() 
    { 
    	return CurrentDie;
    }
    
    
    /**
     * Determine the winner based on points
     */
    public void DetermineWinner()
    {
    	int [] PointsPerPlayer = new int[TotalPlayer];
    	int max=0;
    	for(int i=0; i<TotalPlayer; i++)
    	{
    		System.out.println("Player " + i + " report\n==================================================\n");
    		PointsPerPlayer[i] = GetPlayerPoints(i);
    		if(PointsPerPlayer[i] > max)
    		{
    			max = PointsPerPlayer[i];
    		}
    	}
    	
    	for(int j=0; j<TotalPlayer; j++)
    	{
    		if(max == GetPlayerPoints(j))
    		{
    			System.out.println("\n\nCongratulation Player " + j + "!!! You have won the game. ");
    			break;
    		}
    	}
    }
    
    
    private void Print(String message)
    {
    	System.out.println(message);
    }
    
    /**
  	 * This function will show the current state of the board which includes the minions, demons, building, trolls and troublemaker
  	 */
  	public void ShowBoardState() 
  	{
  		//coordinate to draw components
  		final int DollySisterMapX = 535;
  		final int DollySisterMapY = 195;
  		final int UnrealEstateMapX = 440;
  		final int UnrealEstateMapY = 340;
  		final int DragonLandingMapX = 650;
  		final int DragonLandingMapY = 355;
  		final int SmallGodsMapX = 665;
  		final int SmallGodsMapY = 550;
  		final int TheScoursMapX = 510;
  		final int TheScoursMapY = 610;
  		final int TheHippoMapY = 690;
  		final int TheHippoMapX = 690;
  		final int TheShadesMapX = 440;
  		final int TheShadesMapY = 755;
  		final int DimWellMapX = 195;
  		final int DimWellMapY = 770;
  		final int LongwallMapX = 115;
  		final int LongwallMapY = 570;
  		final int SevenSleeperMapX = 215;
  		final int SevenSleeperMapY = 440;
  		final int NapHillMapX = 190;
  		final int NapHillMapY = 250;
  		final int IsleOfGodMapX = 400;
  		final int IsleOfGodMapY = 510;
  		BufferedImage image = null;
	    Image ScaledMap = null;
	    BufferedImage biScaledImage = null;
	    
	    //load the image from file
  		ClassLoader classLoader = getClass().getClassLoader();
  		File file = new File(classLoader.getResource("diskworld_map.png").getFile());
	    
	    try
		{
			image = ImageIO.read(file);
		}
	    catch (Exception e)    {e.printStackTrace();}

	    if(image != null)
	    {
	    	BufferedImage bi = (BufferedImage)image;
	    	ScaledMap = bi.getScaledInstance(900, 1080, Image.SCALE_SMOOTH);
	    	
	    	biScaledImage = toBufferedImage(ScaledMap);
	    	Graphics2D  myGraphObj = biScaledImage.createGraphics();
	    	
	    	//traverse each area to draw elements
	    	for(int eachArea = 0; eachArea < GameBoard.ListArea.size(); eachArea++)
	    	{
	    		String CurrentArea = GameBoard.ListArea.get(eachArea).GetName();
	    		int StartX = 0;
	    		int StartY = 0;
	    		
	    		if(CurrentArea.compareTo("Dolly Sisters") == 0)
	    		{
	    			StartX = DollySisterMapX;
	    			StartY = DollySisterMapY;
	    		}
	    		else if(CurrentArea.compareTo("Unreal Estate") == 0)
	    		{
	    			StartX = UnrealEstateMapX;
	    			StartY = UnrealEstateMapY;
	    		} 
	    		else if(CurrentArea.compareTo("Dragon's Landing") == 0)
	    		{
	    			StartX = DragonLandingMapX;
	    			StartY = DragonLandingMapY;
	    		} 
	    		else if(CurrentArea.compareTo("Small Gods") == 0)
	    		{
	    			StartX = SmallGodsMapX;
	    			StartY = SmallGodsMapY;
	    		} 
	    		else if(CurrentArea.compareTo("The Scours") == 0)
	    		{
	    			StartX = TheScoursMapX;
	    			StartY = TheScoursMapY;
	    		} 
	    		else if(CurrentArea.compareTo("The Hippo") == 0)
	    		{
	    			StartX = TheHippoMapX;
	    			StartY = TheHippoMapY;
	    		} 
	    		else if(CurrentArea.compareTo("The Shades") == 0)
	    		{
	    			StartX = TheShadesMapX;
	    			StartY = TheShadesMapY;
	    		} 
	    		else if(CurrentArea.compareTo("Dimwell") == 0)
	    		{
	    			StartX = DimWellMapX;
	    			StartY = DimWellMapY;
	    		} 
	    		else if(CurrentArea.compareTo("Longwall") == 0)
	    		{
	    			StartX = LongwallMapX;
	    			StartY = LongwallMapY;
	    		} 
	    		else if(CurrentArea.compareTo("Isle of Gods") == 0)
	    		{
	    			StartX = IsleOfGodMapX;
	    			StartY = IsleOfGodMapY;
	    		} 
	    		else if(CurrentArea.compareTo("Seven Sleepers") == 0)
	    		{
	    			StartX=SevenSleeperMapX;
	    			StartY=SevenSleeperMapY;
	    		} 
	    		else //if(CurrentArea.compareTo("Nap Hill") == 0)
	    		{
	    			StartX = NapHillMapX;
	    			StartY = NapHillMapY;
	    		} 
	    		
	    		//draw trolls and demons
	    		Boolean HasTroubleMaker = GameBoard.ListArea.get(eachArea).HasTroubleMaker();
	    		Boolean HasTrolls       = (GameBoard.ListArea.get(eachArea).GetTrollCount() > 0);
	    		Boolean HasDemons       = (GameBoard.ListArea.get(eachArea).GetDemonCount() > 0);
	    		
	    		if(HasTroubleMaker)
	    		{
	    			myGraphObj.setColor(Color.BLACK);
	    			char [] TroubleMaker = {'T'};
	    			myGraphObj.setFont(new Font("TimesRoman", Font.BOLD, 15)); 
	    			myGraphObj.drawChars(TroubleMaker, 0, 1, StartX, StartY-42);
	    		}
	    		
	    		if(HasTrolls)
	    		{
	    			myGraphObj.setColor(Color.BLACK);
	    			myGraphObj.fillOval(StartX, StartY-35, 10,10);
	    		}
	    		
	    		if(HasDemons)
	    		{
	    			myGraphObj.setColor(Color.BLACK);
	    			char [] TroubleMaker = {'D'};
	    			myGraphObj.setFont(new Font("TimesRoman", Font.BOLD, 15)); 
	    			myGraphObj.drawChars(TroubleMaker, 0, 1, StartX, StartY-55);
	    		}
	    		
	    		int CoordBuildingX = StartX;
    			int CoordBuildingY = StartY - 20;
    			
    			//draw minions for each player
	    		for(int eachPlayer=0; eachPlayer<TotalPlayer; eachPlayer++)
		    	{
	    			Colors PlayerColor = ListPlayer.get(eachPlayer).GetColor();
	    			if(PlayerColor == Colors.Red)
	    				myGraphObj.setColor(Color.RED);
	    			else if(PlayerColor == Colors.Green)
	    				myGraphObj.setColor(Color.GREEN);
	    			else if(PlayerColor == Colors.Blue)
	    				myGraphObj.setColor(Color.BLUE);
	    			else if(PlayerColor == Colors.Yellow)
	    				myGraphObj.setColor(Color.YELLOW);
	    			else
	    				System.out.println("Invalid player colors!!!");
	    			
	    			//draw building of each player
	    			Boolean HasBuilding = GameBoard.ListArea.get(eachArea).HasBuilding();
	    			if(HasBuilding && (GameBoard.ListArea.get(eachArea).GetBuilding().GetPieceColor() == PlayerColor))
	    			{
	    				myGraphObj.fillRect(CoordBuildingX, CoordBuildingY, 10, 10);
	    				CoordBuildingX += 15;
	    				
	    			}
	    				
	    			int PointX  = StartX;
	    			for(int eachMinion=0; eachMinion < GameBoard.ListArea.get(eachArea).GetMinionCount(PlayerColor); eachMinion++)
	    			{
	    				//myGraphObj.(PointX, StartY, 5, 5);
	    				myGraphObj.fillOval(PointX, StartY, 5,5);
	    				PointX += 10;
	    				
	    			}
	    			StartY += 10;
		    	}
	    		
	    	}


	    }

	    //set the right property and show in screen
		JLabel jLabel = new JLabel();

		jLabel.setIcon(new ImageIcon(biScaledImage));
		JFrame editorFrame = new JFrame("Diskworld Current Status");
		editorFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);
		editorFrame.pack();
		editorFrame.setLocationRelativeTo(null);
		editorFrame.setVisible(true);
  	}
  	
  	/**
  	 * Function to convert an Image to a BufferedImage
  	 * @param img
  	 * @return
  	 */
  	private BufferedImage toBufferedImage(Image img)
  	{
  	    if (img instanceof BufferedImage)
  	    {
  	        return (BufferedImage) img;
  	    }

  	    // Create a buffered image with transparency
  	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

  	    // Draw the image on to the buffered image
  	    Graphics2D bGr = bimage.createGraphics();
  	    bGr.drawImage(img, 0, 0, null);
  	    bGr.dispose();

  	    // Return the buffered image
  	    return bimage;
  	}
  	
  	/**
  	 * Function will remove all troublemarker in boards
  	 */
  	public void RemoveAllTroubleMaker()
  	{
  		for(int i=0; i<12; i++)
  		{
  			GameBoard.Removetrouble(i);
  		}
  	}
    /* (non-Javadoc)
  	 * @see java.lang.Object#toString()
  	 */
  	public String toString()
  	{
  		return this.toString();
  	}
  	
  	/**
  	 * Initialize the game to state for build 3
  	 */
  	public void InitializeBuild3()
  	{
  		
  		TotalPlayer = 4;
		
		//reinitialize everything
		ListPlayer = new ArrayList<Player>();
		DiscardCards = new ArrayList<Cards>();
		GameBoard = new Board();
		HasGameEnded = false;
		
      	//initialize personality
		PersonalityCards Vetinari = new PersonalityCards("Lord Vetinari", 1, true, CardType.PersonalityCards);
  	    PersonalityCards Chrysoprase= new PersonalityCards("Chrysoprase", 1, true, CardType.PersonalityCards);
  	    PersonalityCards Arms= new PersonalityCards("Dragon King of Arms", 1, true, CardType.PersonalityCards);
  	    PersonalityCards Worde = new PersonalityCards("Lord Worde", 1, true, CardType.PersonalityCards);
  	    
  	    //Dolly Sister
  	    Area DollySisterArea = GameBoard.ListArea.get(0);
  	    DollySisterArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Red));
  	    DollySisterArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Yellow)); 
  	    DollySisterArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Green));
  	    DollySisterArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Blue));
  	    DollySisterArea.AddTroubleMaker(new Pieces(PieceType.TroubleMarker, Colors.None));
  	    
  	    //Unreal Estate
  	    Area UnrealEstateArea = GameBoard.ListArea.get(1);
  	    UnrealEstateArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Red));
  	    
  	    //Dragon Landing
  	    Area DragonLandingArea = GameBoard.ListArea.get(2);
  	    DragonLandingArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Red));
  	    DragonLandingArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Red));
  	    DragonLandingArea.AddTroubleMaker(new Pieces(PieceType.TroubleMarker, Colors.None));
  	    
  	    
  	    //SmallGods - empty
  	    
  	    //The Scours
  	    Area TheScoursArea = GameBoard.ListArea.get(4);
  	    TheScoursArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Yellow));
  	    TheScoursArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Blue)); 
  	    TheScoursArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Blue));
  	    TheScoursArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Blue));
  	    TheScoursArea.AddTroubleMaker(new Pieces(PieceType.TroubleMarker, Colors.None));
  	    TheScoursArea.AddBuildingInitial(new Pieces(PieceType.Building, Colors.Yellow));
  	    TheScoursArea.AddDemons(new Pieces(PieceType.Demon, Colors.None));
  	    
  	    //The Hippo
  	    Area TheHippoArea = GameBoard.ListArea.get(5);
  	    TheHippoArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Blue)); 
  	    TheHippoArea.AddBuildingInitial(new Pieces(PieceType.Building, Colors.Blue));
  	    TheHippoArea.AddTrolls(new Pieces(PieceType.Troll, Colors.None));
  	    
  	    //The Shades
  	    Area TheShadesArea = GameBoard.ListArea.get(6);
  	    TheShadesArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Yellow)); 
  	    TheShadesArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Blue)); 
  		TheShadesArea.AddBuildingInitial(new Pieces(PieceType.Building, Colors.Yellow));
      	TheShadesArea.AddTroubleMaker(new Pieces(PieceType.TroubleMarker, Colors.None));
  	    
  	    //DimWell
  	    Area DimWellArea = GameBoard.ListArea.get(7);
  	    DimWellArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Red)); 
  	    DimWellArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Green)); 
  	    DimWellArea.AddBuildingInitial(new Pieces(PieceType.Building, Colors.Red));
  	    
  	    //LongWall
  	    Area LongWallArea = GameBoard.ListArea.get(8);
  	    LongWallArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Blue)); 
  	    LongWallArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Green)); 
  	    LongWallArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Blue)); 
  	    LongWallArea.AddTroubleMaker(new Pieces(PieceType.TroubleMarker, Colors.None));
  	    
  	    //Isles of Gods
  	    Area IsleOfGods = GameBoard.ListArea.get(9);
  	    IsleOfGods.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Red));
  	    
  	    //Seven Sleepers
  	    Area SevenSleepersArea = GameBoard.ListArea.get(10);
  	    SevenSleepersArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Red));
  	    SevenSleepersArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Yellow)); 
  	    SevenSleepersArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Green));
  	    SevenSleepersArea.AddTroubleMaker(new Pieces(PieceType.TroubleMarker, Colors.None));
  	    SevenSleepersArea.AddBuildingInitial(new Pieces(PieceType.Building, Colors.Yellow));
  	    
  	    //Nap Hill
  	    Area NappHillArea = GameBoard.ListArea.get(11);
  	    NappHillArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Red));
  	    NappHillArea.AddMinionsInit(new Pieces(PieceType.Minion, Colors.Blue)); 
  	    NappHillArea.AddTrolls(new Pieces(PieceType.Troll, Colors.None));
  	    
  	    //initialize player city area and bank
  	    CardManager = new ManageCards(4);
  	    ListPlayer.clear();
  	    
		//set of cards for player 0
  	    List<Cards> PlayerHand0 = new ArrayList<Cards>(); 
  	    PlayerHand0.add(CardManager.SearchCards("captain carrot"));
  	    PlayerHand0.add(CardManager.SearchCards("Fresh Start Club"));
  	    PlayerHand0.add(CardManager.SearchCards("Mr Pin & Mr  Tulip"));
  	    PlayerHand0.add(CardManager.SearchCards("Shonky Shop"));
  	    PlayerHand0.add(CardManager.SearchCards("The Peeled Nuts"));
  	    PlayerHand0.add(CardManager.SearchCards("The Thieves Guild"));
  	    
  	    List<Cards> PlayerHand1 = new ArrayList<Cards>();
	    PlayerHand1.add(CardManager.SearchCards(" Inigo Skimmer"));
	    PlayerHand1.add(CardManager.SearchCards("Modo"));
	    PlayerHand1.add(CardManager.SearchCards("Queen Molly"));
	    PlayerHand1.add(CardManager.SearchCards("The Duckman"));
	    PlayerHand1.add(CardManager.SearchCards("The Dysk"));
	    
	    List<Cards> PlayerHand2 = new ArrayList<Cards>(); 
  	    PlayerHand2.add(CardManager.SearchCards("Dr Cruces"));
  	    PlayerHand2.add(CardManager.SearchCards("Foul Ole Ron"));
  	    PlayerHand2.add(CardManager.SearchCards("Mr Boggis"));
  	    PlayerHand2.add(CardManager.SearchCards("Mrs Cake"));
  	    PlayerHand2.add(CardManager.SearchCards("Sergeant Angua"));

  	    List<Cards> PlayerHand3 = new ArrayList<Cards>(); 
	    PlayerHand3.add(CardManager.SearchCards("Gaspode"));
	    PlayerHand3.add(CardManager.SearchCards("Mr Gryle"));
	    PlayerHand3.add(CardManager.SearchCards("Rincewind"));
	    PlayerHand3.add(CardManager.SearchCards("Sacharissa Cripslock"));
	    PlayerHand3.add(CardManager.SearchCards("The Opera House"));
	    PlayerHand3.add(CardManager.SearchCards("Zorgo the Retro-phrenologist"));
	    
	    Colors PlayerColor = Colors.values()[ListPlayer.size()+1];
	    
	    List<Pieces> ListMinions0 = new ArrayList<Pieces>();
        List<Pieces> ListBuildings0 = new ArrayList<Pieces>();
        
        List<Pieces> ListMinions1 = new ArrayList<Pieces>();
        List<Pieces> ListBuildings1 = new ArrayList<Pieces>();
        
        List<Pieces> ListMinions2 = new ArrayList<Pieces>();
        List<Pieces> ListBuildings2 = new ArrayList<Pieces>();
        
        List<Pieces> ListMinions3 = new ArrayList<Pieces>();
        List<Pieces> ListBuildings3 = new ArrayList<Pieces>();
        
        
  	    int PlayerMinionTotal = 4;
  	    int PlayerBuildingTotal = 5;
  	    int PlayerBank = 15;
        for(int MinionCount = 0; MinionCount < PlayerMinionTotal; ++MinionCount)
      	   ListMinions0.add(new Pieces(PieceType.Minion, PlayerColor));
      	
        for(int BuildingCount = 0; BuildingCount < PlayerBuildingTotal; ++BuildingCount)
        	ListBuildings0.add(new Pieces(PieceType.Building, PlayerColor));
        
        //Add player 0
        Player p0 = new Player(0, Vetinari, PlayerColor, PlayerHand0, ListMinions0, ListBuildings0);
        p0.AddToMoney(PlayerBank-10);
        p0.AddCityAreayCard(CardManager.SearchCityArea("Dimwell"));
	    ListPlayer.add(p0);
	    	    
	    //player 1
	    PlayerColor = Colors.values()[ListPlayer.size()+1];
	    PlayerMinionTotal = 8;
  	    PlayerBuildingTotal = 3;
  	    PlayerBank = 22;
  	    
        for(int MinionCount = 0; MinionCount < PlayerMinionTotal; ++MinionCount)
      	   ListMinions1.add(new Pieces(PieceType.Minion, PlayerColor));
      	
        for(int BuildingCount = 0; BuildingCount < PlayerBuildingTotal; ++BuildingCount)
        	ListBuildings1.add(new Pieces(PieceType.Building, PlayerColor));
        
        Player p1 = new Player(1, Chrysoprase, PlayerColor, PlayerHand1, ListMinions1, ListBuildings1);
        p1.AddToMoney(PlayerBank-10);
        p1.AddCityAreayCard(CardManager.SearchCityArea("The Scours"));
        p1.AddCityAreayCard(CardManager.SearchCityArea("The Shades"));
        p1.AddCityAreayCard(CardManager.SearchCityArea("Seven Sleepers"));
	    ListPlayer.add(p1);
	    
	    //player 2
	    PlayerColor = Colors.values()[ListPlayer.size()+1];
	    PlayerMinionTotal = 8;
  	    PlayerBuildingTotal = 6;
  	    PlayerBank = 17;
  	    
        for(int MinionCount = 0; MinionCount < PlayerMinionTotal; ++MinionCount)
      	   ListMinions2.add(new Pieces(PieceType.Minion, PlayerColor));
      	
        for(int BuildingCount = 0; BuildingCount < PlayerBuildingTotal; ++BuildingCount)
        	ListBuildings2.add(new Pieces(PieceType.Building, PlayerColor));
        
        Player p2 = new Player(2, Arms, PlayerColor, PlayerHand2, ListMinions2, ListBuildings2);
        p2.AddToMoney(PlayerBank-10);
	    ListPlayer.add(p2);
	    
	    //Player 3
	    PlayerColor = Colors.values()[ListPlayer.size()+1];
	    PlayerMinionTotal = 3;
  	    PlayerBuildingTotal = 5;
  	    PlayerBank = 18;
  	    
        for(int MinionCount = 0; MinionCount < PlayerMinionTotal; ++MinionCount)
      	   ListMinions3.add(new Pieces(PieceType.Minion, PlayerColor));
      	
        for(int BuildingCount = 0; BuildingCount < PlayerBuildingTotal; ++BuildingCount)
        	ListBuildings3.add(new Pieces(PieceType.Building, PlayerColor));
        
        Player p3 = new Player(3, Worde, PlayerColor, PlayerHand3, ListMinions3, ListBuildings3);
        p3.AddToMoney(PlayerBank-10);
        p3.AddCityAreayCard(CardManager.SearchCityArea("The Hippo"));
	    ListPlayer.add(p3);
	    
	    //disable 
	    CardManager.Personality_Card[1].Status = false;
	    CardManager.Personality_Card[2].Status = false;
	    CardManager.Personality_Card[3].Status = false;
	    CardManager.Personality_Card[6].Status = false;
	    	
  	    GameBoard.SetBalance(48);
  	}
  	    
  	/**
  	 * Function will return player color
  	 * 
  	 * @param p
  	 * @return
  	 */
  	public String GetPlayerColor(int p)
  	{
  		if(ListPlayer.get(p).GetColor() == Colors.Red)
  			return "Red";
  		else if(ListPlayer.get(p).GetColor() == Colors.Blue)
  			return "Blue";
  		else if(ListPlayer.get(p).GetColor() == Colors.Green)
  			return "Green";
  		else if(ListPlayer.get(p).GetColor() == Colors.Yellow)
  			return "Yellow";
  		else
  			return "None";
  	}

}