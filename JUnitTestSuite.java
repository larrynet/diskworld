import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;
import java.util.Random;

public class JUnitTestSuite {
	 
	/**
	 * Test if functions for import/export GameEngine is working fine
	 */
	@Test public void TestStateManager() {
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
	}
	
	/**
	 * Test a success and fail scenario while transferring money to player 
	 */
	@Test public void TestTransferMoneyToPlay()
	{
		boolean TransferSuccessScenario = false;
		boolean TransferBadScenario = false;
		
		int [] ValidPlayerNumber = {2,3,4};
		Random _random = new Random();
		
		//Pick a random player
		int RandomAmountOfMoney = 5;
		int RandomPlayerIndex = ValidPlayerNumber[_random.nextInt(3)]-1;
		
		//initialize game with 4 players
		GameEngine ge = new GameEngine(4);
		
		//Test Success Scenario
		int InitialBankAmount = ge.GetBankBalance(); 
		int InitialPlayerAmount = ge.GetPlayerBank(RandomPlayerIndex);
		
		//Pay Random player  a random amount
		boolean PaySuccess = ge.PayPlayer(RandomPlayerIndex, RandomAmountOfMoney); 
		
		if(PaySuccess)
		{
			int NewBalance = ge.GetPlayerBank(RandomPlayerIndex);
			TransferSuccessScenario = (NewBalance == (InitialPlayerAmount+ RandomAmountOfMoney));
			TransferSuccessScenario = TransferSuccessScenario && (ge.GetBankBalance() == (InitialBankAmount-RandomAmountOfMoney));
		}
		else 
			TransferSuccessScenario = false;
		
		//Test Failed Scenario by trying of transfering more money than what the bank has
		TransferBadScenario = ge.PayPlayer(RandomPlayerIndex, InitialBankAmount+1);
		
		assertTrue("Failed while transferring money from bank to player", TransferSuccessScenario);
		assertFalse("Failed because Bank should not have sufficient money to transfer", TransferBadScenario);
	}

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

}
