/**
 * This class will act as controller and test the game engine
 */

/**
 * @author Lawrence
 *
 */
import java.util.Scanner;
import java.io.File;
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the number of players: ");
		
		int NumPlayer = ValidateIntEntry(scan);
		while(NumPlayer <2 || NumPlayer >4)
		{
			System.out.println("Sorry this game only supports 2 to 4 players. Please try to enter the value of player again:");
			NumPlayer = scan.nextInt();
		}
		GameEngine ge = new GameEngine(NumPlayer);
		
		
		
		if(!ge.IsGameInitialize())
		{
			System.out.println("GameEngine failed to initiate.");
			
		}
		else
		{
			System.out.println("Game Start. Distributing cards to player");
			
			ge.DetermineFirstPlayer();
			int CurrentPlayerIndex = ge.GetCurrentPlayer();
			boolean Continue = true;
			int count =10000000;
			do
			{
				count --;
				System.out.println("\nCurrent player turn is " + CurrentPlayerIndex +"+++++++++++++++++++++++++++++++++++++++");
				
				//TODO -- Add encapsulation
				//have draw cards if not full hand
				if(ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size() < ge.ListPlayer.get(CurrentPlayerIndex).HandSize)
				{
					Cards c = ge.GetCardManager().GetCard(CardType.GreenCards);
					if(c == null)
						c = ge.GetCardManager().GetCard(CardType.BrownCards);
					
					ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(c);
					
				}
				//Show all the cards and let the person choose what he wants
				System.out.println("1 - Peak at your card");
				System.out.println("2 - Play card");
				int choice = scan.nextInt();
				if(choice == 1)
				{
					//System.out.println("Which card you want to see (1-5)?");
					//int peekChoice = scan.nextInt();	
					
					//show card
                    ge.ShowCard(CurrentPlayerIndex);
				}
				else
				{
					//check if someone won first
					if(ge.IsWinner())
					{
						System.out.println("!!!! Congratulation. Player " + CurrentPlayerIndex + "won the game !!!! ");
						Continue = false;
					}
					else
					{
						//activating the area effect
	                    ge.ActivateCityAreaEffect(CurrentPlayerIndex);
	                    
						System.out.println("Which card you want to play?");
						int playChoice = scan.nextInt();
						boolean CardPlayed = ge.PlayCard(CurrentPlayerIndex, playChoice);
						
						if(CardPlayed )
	                    {
	                        System.out.println("Card played successfully");
	                        
	                        //increment turn
	                        CurrentPlayerIndex = (CurrentPlayerIndex++)%NumPlayer;
	                        
	                        	
	                    }
						else
	                    {
	                        
	                        boolean PlayUntilSuccess = false;
	                        while(!PlayUntilSuccess)
	                        {
	                            System.out.println("Card failed to play. You will need to try again. Which card you want to play (1-5)?");
	                            playChoice = scan.nextInt();
	                            PlayUntilSuccess = ge.PlayCard(CurrentPlayerIndex, playChoice);
	                        }
	                        
	                    }
							
						
	                    if(ge.IsGameEnded())
	                    {
	                        System.out.println("The Game Engine signaled a game end event preemptively because certains cards force to end. ");
	                        Continue = false;
	                    }
						if(count == 0)
						{
							System.out.println("PROBLEM. INFINITE LOOP DETECTED ");
							Continue = false;
						}
					}
                    
				}
				 
                
                //update current turn
                ge.SetCurrentPlayer(CurrentPlayerIndex);
			} while (Continue);	
		}
		
				
    scan.close();
	}

	
	/**
	 * Function will get an input from user between min and max value. 
	 * 
	 * @param scan
	 * @param min
	 * @param max
	 * @return
	 */
	public static int ValidateEntry(Scanner scan, int min, int max)
	{
		int ValidEntry = 0;
		boolean InputInvalid = true;
		
		
		while (InputInvalid)
		{	
			try
			{	
				ValidEntry = Integer.parseInt(scan.next()); 
				if(ValidEntry < min || ValidEntry>max)
				{
					System.out.println("Value is out of bound. Please re-enter value index (1-" + max + "):");
				}
				else
				{
					InputInvalid = false;
				}
			} catch (Exception e) 
			{
				System.out.println("Input invalid. Please re-enter value index (1-" + max + "):");
			}
		}
		return ValidEntry;
	}

	
	/**
	 * Function will verify that input is int. 
	 * @param scan scanner object
	 * @return the integer entered if correct
	 */
	public static int ValidateIntEntry(Scanner scan)
	{
		int ValidEntry = 0;
		boolean InputInvalid = true;
		
		while (InputInvalid)
		{	
			try
			{	
				ValidEntry = Integer.parseInt(scan.next()); 
				InputInvalid = false;
				
			} catch (Exception e) 
			{
				System.out.println("Input invalid. Please re-enter value index (1-4)");
			}
		}
		return ValidEntry;
	}
	
		
}
