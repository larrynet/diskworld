import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;


public class JUnitTestSuite {
	 
	/**
	 * Test if functions for import/export GameEngine is working fine
	 */
	@Test public void TestStateManager() {
//		boolean ExportSuccess = false,
//				ImportSuccess = false;
//        String RandomString = "Michael Jordan rules";
//        String PathState = "%TEMP%DiskWorldState_JUNIT.txt";
//		StateManager sm = new StateManager();
//		
//		GameEngine ge = new GameEngine();
//		ManageCards CardManager = ge.GetCardManager();
//		//CardManager.c[0].Name = RandomString;
//		ExportSuccess = sm.ExportGameState(ge,PathState);
//		
//		GameEngine LoadFromState = sm.ImportGameState(PathState);
//		ManageCards LoadFromStateCards = LoadFromState.GetCardManager();
//		//ImportSuccess = RandomString.equals(LoadFromStateCards.c[0].Name);
//
//		//evaluate test result
//		assertTrue("Failed while import state", ImportSuccess);
//		assertTrue("Failed while export state", ExportSuccess);
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
		
	

}