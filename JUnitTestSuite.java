import static org.junit.Assert.*;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.List;
public class JUnitTestSuite {
	 
	/**
	 * Test if functions for import/export GameEngine is working fine
	 */
	/*@Test public void TestStateManager() {
		boolean ExportSuccess = false,
				ImportSuccess = false;
        int RandomAmountOfMoney = 15;
        String PathState = "%TEMP%DiskWorldState_JUNIT.txt";
		StateManager sm = new StateManager();
		
		GameEngine ge = new GameEngine();
		int InitialBankAmount = ge.GetBankBalance(); 
		ge.PayPlayer(1, RandomAmountOfMoney);
		ExportSuccess = sm.ExportGameState(ge,PathState);
		
		GameEngine LoadFromState = sm.ImportGameState(PathState);
		
		ImportSuccess = (LoadFromState.GetBankBalance() == (InitialBankAmount-RandomAmountOfMoney));

		//evaluate test result
		assertTrue("Failed while import state", ImportSuccess);
		assertTrue("Failed while export state", ExportSuccess);
	}*/
	
	@Test public void TestHubert()
	{
		//almost always the same
		GameEngine ge = new GameEngine(4);
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
		//SPECIFIC
		GreenCards Hubert = new GreenCards("Hubert", 1, true, CardType.GreenCards);
		List<String> ListVerb = new Vector<String>(); 
		ListVerb.add("give");
		List<String> ListSymbol = new Vector<String>(); 
		ListSymbol.add("s");
		ListSymbol.add("m");
		List<String> ListObject = new Vector<String>(); 
		ListObject.add("3$ to another player of choice");
//		/public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
		
		Action a = new Action(1, ListVerb,ListObject , null, "and", false);
		Hubert.AddAction(a);
		Hubert.AddSymbols("s");
		Hubert.AddSymbols("m");
		//ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.remove(4);
		
		ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(Hubert);
		//check if money transfered
		
		boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
		//assertTrue("Failed executing Hubert", Success);
		//public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,List<String> s ,String relation, boolean keep) 
		
				//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
		 
	}
	
	/**
	 * Test a success and fail scenario while transferring money to player 
	 */
	/*@Test public void TestTransferMoneyToPlay()
	{
		boolean TransferSuccessScenario = false;
		boolean TransferBadScenario = false;
		
		int [] ValidPlayerNumber = {2,3,4};
		Random _random = new Random();
		
		//Pick a random player
		int RandomAmountOfMoney = 5;
		int RandomPlayerIndex = ValidPlayerNumber[_random.nextInt(3)];
		
		//initialize game with 4 players
		GameEngine ge = new GameEngine(4);
		
		//Test Success Scenario
		int InitialBankAmount = ge.GetBankBalance(); 
		int InitialPlayerAmount = ge.GetPlayerBank(RandomPlayerIndex-1);
		
		//Pay Random player  a random amount
		boolean PaySuccess = ge.PayPlayer(RandomPlayerIndex, RandomAmountOfMoney); 
		
		if(PaySuccess)
		{
			int NewBalance = ge.GetPlayerBank(RandomPlayerIndex-1);
			TransferSuccessScenario = (NewBalance == (InitialPlayerAmount+ RandomAmountOfMoney));
			TransferSuccessScenario = TransferSuccessScenario && (ge.GetBankBalance() == (InitialBankAmount-RandomAmountOfMoney));
		}
		else 
			TransferSuccessScenario = false;
		
		//Test Failed Scenario by trying of transfering more money than what the bank has
		TransferBadScenario = ge.PayPlayer(RandomPlayerIndex, InitialBankAmount+1);
		
		assertTrue("Failed while transferring money from bank to player", TransferSuccessScenario);
		assertFalse("Failed because Bank should not have sufficient money to transfer", TransferBadScenario);
	}*/

	/**
	 * Test will test how game reacts if trying to init with an invalid number of player 
	 */
	@Test public void TestGamePlayerBoundary()
	{
		boolean InitSuccessScenario = false;
		boolean InitFailedcenario = false;
		int [] ValidPlayerNumber = {2,3,4};
		Random _random = new Random();
		GameEngine GoodSetting = new GameEngine(ValidPlayerNumber[_random.nextInt(3)]);
		GameEngine BadSetting = new GameEngine(5); //Try to init game with 5 players which is not supported
		
		InitSuccessScenario = GoodSetting.IsGameInitialize();
		InitFailedcenario = !BadSetting.IsGameInitialize();
		
		assertTrue("Failed while import state", InitSuccessScenario);
		assertTrue("Failed while export state", InitFailedcenario);
		
	}
	/*
	@Test public void TestGreenCard() {
		// TODO Auto-generated method stub
		boolean GeneratedError = false;
	String  thisLine = null;
	List<Action> lstAction = new Vector<Action>();
	
	
	  try{
	     // open input stream test.txt for reading purpose.
	     BufferedReader br = new BufferedReader(new FileReader("/Users/gayhazan/Documents/workspace/CardAction/src/action.txt"));
	     while ((thisLine = br.readLine()) != null) {
	    	
	    	//If not comment then read
	    	 if (!thisLine.startsWith("/") && !thisLine.isEmpty())
	    	 {
	    		 Action newAction = Action.ParseString(thisLine);
	    		 lstAction.add(newAction);
	    	 }
	     }       
	  }catch(Exception e){
	     e.printStackTrace();
	     GeneratedError = true;
	  }		  
	  
	  System.out.println(lstAction.size());
	  lstAction.get(1).PrintAll();

	  
	  assertFalse("Generated an exeption testing action", GeneratedError);
	}
	@Test public void TestBankCount() {
		
		int BoardBankBalance = 120;
		
		Board TestBoard = new Board();
		assertTrue("Bank Balance is wrong",TestBoard.GetBalance() == BoardBankBalance);
		
		TestBoard.DeductFromBank(20);
		assertFalse("Bank Balance is wrong",TestBoard.GetBalance() == BoardBankBalance);
		
	}
		
	@Test public void TestRollDie() {
			
		Board TestBoard = new Board();
		
		for (int i = 0; i < 100 ; i++)
		{
			TestBoard.RollDie();
			assertTrue("Die not in range",TestBoard.GetDie() > 0 && TestBoard.GetDie() < 13);
		}
	}
	
	@Test public void TestAddMinionToArea()
	{
		Area area = new Area("Test", 1,5);
		Pieces Minion = new Pieces(PieceType.Minion, Colors.Red);
		
		area.AddMinions(Minion);
		
		
		assertTrue("Minion Add not working", area.GetMinionCount(Colors.None) == 1);
	}
	
/**
	 * Test First Player selection to make sure all players can be selected
	 * 
	 */
	/*@Test public void TestFirstPlayerSelection()
	{
		//initialize game with 4 players
		GameEngine ge = new GameEngine(4);
		boolean player1 = false;
		boolean player2 = false;
		boolean player3 = false;
		boolean player4 = false;
		
		for (int i = 0; i < 100 ; i++)
		{
			ge.DetermineFirstPlayer();
			
			switch (ge.GetCurrentPlayer()+1)
			{
			case 1: player1 = true;
				break;
			case 2: player2 = true;
				break;
			case 3: player3 = true;
				break;
			case 4: player4 = true;
				break;
			}
		}
		
		
		assertTrue(player1 && player2 && player3 && player4);
	
	}
	*/	
	
	@Test public void TestGreenCardCreation()
	{
		
		ManageCards cardManager = new ManageCards(1);
		for (int i = 0 ; i < cardManager.Event_Card.length; i++)
		{
			if (cardManager.Event_Card[i] != null)
			{
				System.out.println(cardManager.Event_Card[i].GetName());
			}
		}
		
		
		
	}

}
