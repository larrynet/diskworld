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
		
		int NumPlayer = scan.nextInt();
		while(NumPlayer <2 && NumPlayer >4)
		{
			System.out.println("Sorry this game only supports 2 to 4 players. Please try to enter the value of player again:");
			NumPlayer = scan.nextInt();
		}
		GameEngine ge = new GameEngine(NumPlayer);
		
		boolean Continue = true;
		
		do
		{
			System.out.println("\nPlease enter action choice. \n============================================");
			System.out.println("1 - Import state");
			System.out.println("2 - Export state");
			System.out.println("3 - [Change State of Game] Place Minion in Area");
			System.out.println("4 - [Change State of Game] Place Building in Area");
			System.out.println("5 - [Change State of Game] Place Troublemaker in an Area");
			System.out.println("6 - [Change State of Game] Place Demon in an Area");
			System.out.println("7 - [Change State of Game] Place Trolls in an Area");
			System.out.println("8 - [Change State of Game] Transfer 2 Ankh-Morpork dollars to Player");
			System.out.println("9 - Print the current state");
			System.out.println("q- Quit");
			String Choice = scan.next();
			
			if(Choice.charAt(0) == '1') 
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
			else if(Choice.charAt(0) == '2') //export choice
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
			if(Choice.charAt(0) == '3')
			{
				System.out.println("Please enter the player index of whom the minion belong (1-" + NumPlayer + "):");
				int PlayerIndex = ValidateEntry(scan, 1, NumPlayer);
				System.out.println("Please enter the area index to put the minion (1-12):");
				int AreaIndex = ValidateEntry(scan, 1, 12);
						
				ge.PlaceMinion(AreaIndex, PlayerIndex-1);
			}
			else if(Choice.charAt(0) == '4')
			{
				System.out.println("Please enter the player index of whom the building belong (1-" + NumPlayer + "):");
				int PlayerIndex = ValidateEntry(scan, 1, NumPlayer);
				System.out.println("Please enter the area index to put the building (1-12):");
				int AreaIndex = ValidateEntry(scan, 1, 12);
				ge.PlaceBuilding(AreaIndex, PlayerIndex-1);
			}
			else if(Choice.charAt(0) == '5')
			{
				System.out.println("Please enter the area index to put the troublemaker (1-12):");
				int AreaIndex = ValidateEntry(scan, 1, 12);
				ge.PlaceTroubleMarker(AreaIndex);
			}
			else if(Choice.charAt(0) == '6')
			{
				System.out.println("Please enter the area index to put the demon (1-12):");
				int AreaIndex = ValidateEntry(scan, 1, 12);
				ge.PlaceDemon(AreaIndex);
			}
			else if(Choice.charAt(0) == '7')
			{
				System.out.println("Please enter the area index to put the troll (1-12):");
				int AreaIndex = ValidateEntry(scan, 1, 12);
				ge.PlaceTroll(AreaIndex);
			}
			else if(Choice.charAt(0) == '8') 
			{
				System.out.println("Please enter the player index of whom to transfer money (1-" + NumPlayer + "):");
				int PlayerIndex = ValidateEntry(scan, 1, NumPlayer);
				//missing money transfer command
			}
			else if(Choice.charAt(0) == '9') 
			{
				ge.PrintState();
			}
			else if(Choice.charAt(0) == 'q') 
			{
				Continue = false;
			}
		} while (Continue);
				
    scan.close();
	}
	
	public static int ValidateEntry(Scanner scan, int min, int max)
	{
		int ValidEntry = 0;
		boolean InputInvalid = true;
		while (InputInvalid)
		{
			try
			{
				ValidEntry = scan.nextInt();
				if(ValidEntry < min && ValidEntry>max)
				{
					System.out.println("Value is out of bound. Please re-enter value index ("+min +"1-" + max + "):");
				}
				else
					InputInvalid = false;
			} catch (Exception e) 
			{
				System.out.println("Input invalid. Please re-enter value index ("+min +"1-" + max + "):");
			}
		}
		return ValidEntry;
	}

}
