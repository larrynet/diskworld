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
		
		GameEngine ge = new GameEngine();
		
		System.out.println("\nPlease enter action choice (1 for import state and 2 for export state)");
		int Choice = scan.nextInt();
		
		if(Choice == 1)//import choice 
		{
			System.out.println("Please enter state path: ");
			String statePath = scan.next();
			ge.ImportGameState(statePath);
			ge.PrintState();
		}
		else //export choice
		{
			System.out.println("Please enter state path: ");
			String statePath = scan.next();
			ge.ExportGameState(statePath);
		}

	}

}
