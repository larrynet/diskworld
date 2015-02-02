import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;


public class JUnitTestSuite {
	 
	/**
	 * Test if functions for import/export GameEngine is working fine
	 */
	@Test public void TestStateManager() {
		boolean ExportSuccess = false,
				ImportSuccess = false;
        String RandomString = "Michael Jordan rules";
        String PathState = "%TEMP%DiskWorldState_JUNIT.txt";
		StateManager sm = new StateManager();
		
		GameEngine ge = new GameEngine();
		ManageCards CardManager = ge.GetCardManager();
		CardManager.c[0].Name = RandomString;
		ExportSuccess = sm.ExportGameState(ge,PathState);
		
		GameEngine LoadFromState = sm.ImportGameState(PathState);
		ManageCards LoadFromStateCards = LoadFromState.GetCardManager();
		ImportSuccess = RandomString.equals(LoadFromStateCards.c[0].Name);

		//evaluate test result
		assertTrue("Failed while import state", ImportSuccess);
		assertTrue("Failed while export state", ExportSuccess);
	}
	
		
	

}
