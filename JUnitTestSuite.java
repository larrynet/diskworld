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
		ge.CardManager.c[0].Name = RandomString;
		ExportSuccess = sm.ExportGameState(ge,PathState);
		
		GameEngine LoadFromState = sm.ImportGameState(PathState);
		ImportSuccess = RandomString.equals(LoadFromState.CardManager.c[0].Name);

		//evaluate test result
		assertTrue("Failed while import state", ImportSuccess);
		assertTrue("Failed while export state", ExportSuccess);
	}
	
		
	

}
