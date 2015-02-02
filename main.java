/**
 * This class will act as controller and test the game engine
 */

/**
 * @author Lawrence
 *
 */
import java.util.Scanner;

public class main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the number of players: ");
		
		int NumPlayer = scan.nextInt();
		
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
			System.out.println("8 - Print the current state");
			System.out.println("9- Quit");
			String Choice = scan.next();
			
			if(Choice.charAt(0) == '1') 
			{
				//IMPORT
				System.out.println("Please enter state path: ");
				String PathState = scan.next();
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
				System.out.println("Please enter state path: ");
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
				int PlayerIndex = scan.nextInt();
				System.out.println("Please enter the area index to put the minion (1-12):");
				int AreaIndex = scan.nextInt();
				ge.PlaceMinion(AreaIndex, PlayerIndex-1);
			}
			else if(Choice.charAt(0) == '4')
			{
				System.out.println("Please enter the player index of whom the building belong (1-" + NumPlayer + "):");
				int PlayerIndex = scan.nextInt();
				System.out.println("Please enter the area index to put the minion (1-12):");
				int AreaIndex = scan.nextInt();
				ge.PlaceBuilding(AreaIndex, PlayerIndex-1);
			}
			else if(Choice.charAt(0) == '5')
			{
				System.out.println("Please enter the area index to put the troublemaker (1-12):");
				int AreaIndex = scan.nextInt();
				ge.PlaceTroubleMarker(AreaIndex);
			}
			else if(Choice.charAt(0) == '6')
			{
				System.out.println("Please enter the area index to put the demon (1-12):");
				int AreaIndex = scan.nextInt();
				ge.PlaceDemon(AreaIndex);
			}
			else if(Choice.charAt(0) == '7')
			{
				System.out.println("Please enter the area index to put the troll (1-12):");
				int AreaIndex = scan.nextInt();
				ge.PlaceTroll(AreaIndex);
			}
			else if(Choice.charAt(0) == '8') 
			{
				ge.PrintState();
			}
			else if(Choice.charAt(0) == '9') 
			{
				Continue = false;
			}
		} while (Continue);
				
    scan.close();
	}

}
