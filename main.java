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
		
		ge.ShowBoardState();		
		
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
				System.out.println("\nCurrent player turn is " + (CurrentPlayerIndex+1)+"\n+++++++++++++++++++++++++++++++++++++++");
				
				//have draw cards if not full hand
				if(ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size() < ge.ListPlayer.get(CurrentPlayerIndex).HandSize)
				{
					Cards c = ge.GetCardManager().GetCard(CardType.GreenCards);
					if(c == null)
						c = ge.GetCardManager().GetCard(CardType.BrownCards);
					
					ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(c);
					
				}
				//check if someone won first
				if(ge.IsWinner())
				{
					System.out.println("!!!! Congratulation. Player " + (CurrentPlayerIndex+1) + " won the game !!!! ");
					Continue = false;
					break;
				}
				
				//Show all the cards and let the person choose what he wants
				System.out.println("1 - Peek at your card");
				System.out.println("2 - Play card");
				System.out.println("3 - Import State");
				System.out.println("4 - Export state");
				System.out.println("5 - Print Board");
				System.out.println("6 - Load Game state of build 3");
				System.out.println("8 - Keep current state but change player turn for testing");
				System.out.println("7 - Quit the game");
				//int choice = scan.nextInt();
				int choice = 6;
				if(choice == 8)
				{
					System.out.println("Please enter current player turn (1-4");
					int currentTurn = scan.nextInt();
					ge.SetPlayerTurn(currentTurn);
					CurrentPlayerIndex = ge.GetCurrentPlayer();
				}	
				else if(choice == 6)
				{
					ge.InitializeBuild3();
					ge.PrintState();
					ge.ShowBoardState();
					
					ge.ShowCard(0);
					System.out.println("\n");
					ge.ShowCard(1);
					System.out.println("\n");
					ge.ShowCard(2);
					System.out.println("\n");
					ge.ShowCard(3);
				}
				else if(choice == 6)
				{
					Continue = false;
					break;
				}
				else if(choice == 3)
				{
					//IMPORT
					System.out.println("Please enter state path: ");
					String PathState = scan.next();
					File TempFile = new File(PathState);
					while(!TempFile.exists())
					{
						System.out.println("The file path entered does not exist. Please try to re-enter a valid file path.");
						PathState=scan.next();
						TempFile = new File(PathState);
					}
					StateManager sm = new StateManager();
					ge = sm.ImportGameState(PathState);
					if(ge != null)
					{
						ge.PrintState();	
					}
					else
					{
						System.out.println("Import state failed. ");
					}
				}
				else if(choice == 4)
				{
					System.out.println("Please enter state path to export to (any file extension is fine): ");
					String PathState = scan.next();
					StateManager sm = new StateManager();
					if(sm.ExportGameState(ge, PathState))
					{
						System.out.println("State successfully exported to " + PathState);
					}
					else
					{
						System.out.println("State failed to export");
					}
				}
				else if(choice == 5)
				{
					ge.PrintState();
				}
				else if(choice == 1)
				{
					//show card
                    ge.ShowCard(CurrentPlayerIndex);
                    for(int i=0; i<ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size(); i++)
                    {
                    	if(ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.get(i).GetCardType() == CardType.GreenCards)
                    	{
                    		GreenCards CurrentGreenCard = (GreenCards)ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.get(i);
                    		String Title = "Player " + CurrentPlayerIndex + " - Card Index " + i;
                    		CurrentGreenCard.ShowImage(Title);
                    	}
                    	else if(ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.get(i).GetCardType() == CardType.GreenCards)
                    	{
                    		BrownCards CurrentBrownCard = (BrownCards)ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.get(i);
                    		String Title = "Player " + CurrentPlayerIndex + " - Card Index " + i;
                    		CurrentBrownCard.ShowImage(Title);
                    	}
                    	
                    }
				}
				else // play card
				{
				
						if(ge.DoesPlayerHasCityArea(CurrentPlayerIndex))
						{
							System.out.println("Would you like to activate city area cards player " + (CurrentPlayerIndex+1));
							String ActivateArea = scan.next();
							if(ActivateArea.compareToIgnoreCase("yes") == 0)
							{
								//activating the area effect
			                    ge.ActivateCityAreaEffect(CurrentPlayerIndex);
			                    
							}
						}
						
						System.out.println("Which card you want to play?");
						int playChoice = scan.nextInt();
						boolean CardPlayed = ge.PlayCard(CurrentPlayerIndex, playChoice);
						
						if(CardPlayed )
	                    {
	                        System.out.println("Card played successfully");
	                        
	                        //increment turn
	                        ge.ReactivateCityAreaEffectForPlayer(CurrentPlayerIndex);
	                        CurrentPlayerIndex += 1;
	                        CurrentPlayerIndex = CurrentPlayerIndex % NumPlayer;
	                        ge.SetCurrentPlayer(CurrentPlayerIndex);                        
	                        	
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
	                    	//RIOTS
	                        System.out.println("The Game Engine signaled a game end event preemptively because certains cards force to end. ");
	                        
	                        Continue = false;
	                    }
	                    if(ge.NoMoreCard())
	                    {
	                    	System.out.println("No more cards. Calculating ");
	                    }
						if(count == 0)
						{
							System.out.println("PROBLEM. INFINITE LOOP DETECTED ");
							Continue = false;
						}
					
                    
				}
				 
                
                //update current turn
                ge.SetCurrentPlayer(CurrentPlayerIndex);
			} while (Continue);	
		}
		
				
    scan.close();
    System.out.println("Game Over! Please try again.");
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
