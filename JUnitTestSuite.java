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
	
	//Test personality
	@Test public void TestChrysoprase()
	{
        GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        PersonalityCards Chrysoprase = new PersonalityCards("Chrysoprase", 1, true, CardType.PersonalityCards);
        
        //Assign new personality to current player
        ge.ListPlayer.get(CurrentPlayerIndex).SetPersonalityCard(Chrysoprase);
		ge.ListPlayer.get(CurrentPlayerIndex).AddToMoney(50);
        
        boolean Win = ge.IsWinner();
        
		assertTrue("Winning Condition for Chrysoprase not executed properly", Win);
    }
	//win if no more cards
	@Test public void TestCommanderVimes()
	{
        GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        PersonalityCards CommanderVimes = new PersonalityCards("Commander Vimes", 1, true, CardType.PersonalityCards);
        
        //Assign new personality to current player
        ge.ListPlayer.get(CurrentPlayerIndex).SetPersonalityCard(CommanderVimes);
		ge.EmptyCard();
		
		String PathState = "NoMoreCards";
		
		StateManager sm = new StateManager();
		if(sm.ExportGameState(ge, PathState))
		{
			System.out.println("State successfully exported to " + PathState);
		}
		
		
        boolean Win = ge.IsWinner();
        
		assertTrue("Winning Condition for Commander Vimes not executed properly", Win);
    }
	@Test public void TestDragonKingofArms()
	{
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        PersonalityCards DragonKingOfArms = new PersonalityCards("Dragon King of Arms", 1, true, CardType.PersonalityCards);
        ge.ListPlayer.get(CurrentPlayerIndex).SetPersonalityCard(DragonKingOfArms);
        
        //Assign troubleMaker in every area
        ge.PlaceTrollInEachArea(CurrentPlayerIndex);
		
        boolean Win = ge.IsWinner();
        
		assertTrue("Winning Condition for DragonKing Of Arms not executed properly", Win);
	}
	
	//control many area
	@Test public void TestLorddeWorde()
	{
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        PersonalityCards LordWorde = new PersonalityCards("Lord Worde", 1, true, CardType.PersonalityCards);
        ge.ListPlayer.get(CurrentPlayerIndex).SetPersonalityCard(LordWorde);
        
        //Assign troubleMaker in every area
        ge.PlaceMinionInEachArea(CurrentPlayerIndex);
		
        boolean Win = ge.IsWinner();
        
		assertTrue("Winning Condition for Lord Worde not executed properly", Win);
	}
	
	@Test public void TestLordRust()
	{
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        PersonalityCards LordRust = new PersonalityCards("Lord Rust", 1, true, CardType.PersonalityCards);
        ge.ListPlayer.get(CurrentPlayerIndex).SetPersonalityCard(LordRust);
        
        //Assign troubleMaker in every area
        ge.PlaceMinionInEachArea(CurrentPlayerIndex);
		
        boolean Win = ge.IsWinner();
        
		assertTrue("Winning Condition for Lord Rust not executed properly", Win);
	}
	
	@Test public void TestLordSelachii()
	{
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        PersonalityCards LordSelachii = new PersonalityCards("Lord Selachii", 1, true, CardType.PersonalityCards);
        ge.ListPlayer.get(CurrentPlayerIndex).SetPersonalityCard(LordSelachii);
        
        //Assign troubleMaker in every area
        ge.PlaceMinionInEachArea(CurrentPlayerIndex);
		
        boolean Win = ge.IsWinner();
        
		assertTrue("Winning Condition for Lord Selachii not executed properly", Win);
	}

	@Test public void TestLordVetinari()
	{
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        PersonalityCards LordVetinari = new PersonalityCards("Lord Vetinari", 1, true, CardType.PersonalityCards);
        ge.ListPlayer.get(CurrentPlayerIndex).SetPersonalityCard(LordVetinari);
        
        //Assign troubleMaker in every area
        ge.PlaceMinionInEachArea(CurrentPlayerIndex);
		
        boolean Win = ge.IsWinner();
        
		assertTrue("Winning Condition for Lord Vetinari not executed properly", Win);
	}
	
	//Test City Area cards
    //City Area
    /*name=The Hyppo
    name=The Sours
    name=Small Gods 
    name=Dragon's Landing
    name=Unreal Estate
    name=Dolly Sisters
    name=Nap Hill
    name=Seven Sleepers
    name=Isle of Gods 
    name=Longwall
    name=Dimwell 
    name=The Shades */
    
    //once per turn, you can pay 3$ and place of your minion in DimWell or an adjacent area
	@Test public void TestDimWell()
	{
        GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		int DieValue = ge.ReturnCurrentDieValue();
		
        //dim well index is 8
		CityAreaCards Dimwell = new CityAreaCards("Dimwell", 1, true, CardType.CityAreaCards); 
		
        int TotalAmountBefore = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        ge.ListPlayer.get(CurrentPlayerIndex).AddCityAreayCard(Dimwell);
		ge.ActivateCityAreaEffect(CurrentPlayerIndex);
        
        int TotalAmountAfter = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        
        //TODO try to remove all dimwell area and all adjacent area...one of them must be true
        boolean RemoveSuccess = ge.RemoveMinion(8, CurrentPlayerIndex);
        
        assertTrue("Failed executing Dm well city area", RemoveSuccess);
        assertTrue("Failed executing Dm well city area", ((TotalAmountBefore-TotalAmountAfter)==3));
    }
    
    //once per turn, you can take 2$ from bank
	@Test public void TestDragonsLanding()
	{
        GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
		CityAreaCards DragonLanding = new CityAreaCards("Dragon's Landing", 1, true, CardType.CityAreaCards); 
		ge.ListPlayer.get(CurrentPlayerIndex).AddCityAreayCard(DragonLanding);
		
        int TotalAmountBefore = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        int BankBefore = ge.GetBankBalance();
		//ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.remove(4);
		ge.ActivateCityAreaEffect(CurrentPlayerIndex);
        
        int TotalAmountAfter = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        int BankAfter = ge.GetBankBalance();
        
		assertTrue("Failed executing DragonLanding city area", ((BankBefore - BankAfter)==2));
        assertTrue("Failed executing DragonLanding city area", ((TotalAmountAfter - TotalAmountBefore)==2));
    }
    
    //once per turn you can pay 2$ and remove one troublemaker from the board
	@Test public void TestIsleOfGods()
	{
        GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        
		CityAreaCards IsleOfGod = new CityAreaCards("Isle of Gods", 1, true, CardType.CityAreaCards); 
		ge.ListPlayer.get(CurrentPlayerIndex).AddCityAreayCard(IsleOfGod);
		
        //put a troublemaker
        ge.PlaceTroubleMarker(1);
        int TotalAmountBefore = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        
		
		ge.ActivateCityAreaEffect(CurrentPlayerIndex);
		
		//check if area is empty afterward
		boolean RemoveEmptyArea = ge.RemoveTroubleMaker(1);
        int TotalAmountAfter = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        
        assertFalse("Failed executing IsleOfGod city area", RemoveEmptyArea);
        assertTrue("Failed executing IsleOfGod city area", ((TotalAmountBefore - TotalAmountAfter)==2));
    }
    
    //once per turn, you can take $1 from the bank
	@Test public void TestLongwall()
	{
        GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
		CityAreaCards Longwall = new CityAreaCards("Longwall", 1, true, CardType.CityAreaCards); 
		ge.ListPlayer.get(CurrentPlayerIndex).AddCityAreayCard(Longwall);
		
        int TotalAmountBefore = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        int BankBefore = ge.GetBankBalance();
		
		ge.ActivateCityAreaEffect(CurrentPlayerIndex);
        
        int TotalAmountAfter = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        int BankAfter = ge.GetBankBalance();
        
		assertTrue("Failed executing Longwall city area", ((BankBefore - BankAfter)==1));
        assertTrue("Failed executing Longwall city area", ((TotalAmountAfter - TotalAmountBefore)==1));
    }
    
	@Test public void TestNapHill()
	{
        GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        CityAreaCards NapHill = new CityAreaCards("Nap Hill", 1, true, CardType.CityAreaCards); 
		ge.ListPlayer.get(CurrentPlayerIndex).AddCityAreayCard(NapHill);
		
        int TotalAmountBefore = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        int BankBefore = ge.GetBankBalance();
		//ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.remove(4);
		ge.ActivateCityAreaEffect(CurrentPlayerIndex);
        
        int TotalAmountAfter = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        int BankAfter = ge.GetBankBalance();
        
		assertTrue("Failed ex	ecuting NapHill city area", ((BankBefore - BankAfter)==1));
        assertTrue("Failed executing NapHill city area", ((TotalAmountAfter - TotalAmountBefore)==1));
    }
    
	@Test public void TestSevenSleepers()
	{
        GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
	
		CityAreaCards SevenSleepers = new CityAreaCards("Seven Sleepers", 1, true, CardType.CityAreaCards); 
		ge.ListPlayer.get(CurrentPlayerIndex).AddCityAreayCard(SevenSleepers);
		
        int TotalAmountBefore = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        int BankBefore = ge.GetBankBalance();
		ge.ActivateCityAreaEffect(CurrentPlayerIndex);
        
        int TotalAmountAfter = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        int BankAfter = ge.GetBankBalance();
        
		assertTrue("Failed executing Seven Sleepers city area", ((BankBefore - BankAfter)==3));
        assertTrue("Failed executing Seven Sleepers city area", ((TotalAmountAfter - TotalAmountBefore)==3));
    }
    
	@Test public void TestTheHippo()
	{
       GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
		CityAreaCards Hippo = new CityAreaCards("The Hyppo", 1, true, CardType.CityAreaCards); 
		ge.ListPlayer.get(CurrentPlayerIndex).AddCityAreayCard(Hippo);
		
        int TotalAmountBefore = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        int BankBefore = ge.GetBankBalance();
		//
		ge.ActivateCityAreaEffect(CurrentPlayerIndex);
        
        int TotalAmountAfter = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        int BankAfter = ge.GetBankBalance();
        
		assertTrue("Failed executing The Hippo city area", ((BankBefore - BankAfter)==2));
        assertTrue("Failed executing The Hippo city area", ((TotalAmountAfter - TotalAmountBefore)==2));
    }
    
	@Test public void TestTheScours()
	{
       GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
		CityAreaCards TheScours = new CityAreaCards("The Scours", 1, true, CardType.CityAreaCards); 
		ge.ListPlayer.get(CurrentPlayerIndex).AddCityAreayCard(TheScours);
		
		int PlayerHandBefore = ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size();
        
        int TotalAmountBefore = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        int BankBefore = ge.GetBankBalance();

		ge.ActivateCityAreaEffect(CurrentPlayerIndex);
        int PlayerHandAfter = ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size();
        
        int TotalAmountAfter = ge.ListPlayer.get(CurrentPlayerIndex).GetMoneyCount();
        int BankAfter = ge.GetBankBalance();
        
        assertTrue("Failed executing The Scours city area", ((PlayerHandBefore - PlayerHandAfter)==1));
		assertTrue("Failed executing The Scours city area", ((BankBefore - BankAfter)==2));
        assertTrue("Failed executing The Scours city area", ((TotalAmountAfter - TotalAmountBefore)==2));
    }
    
    //once per turn, you can place a trouble marker in the Shades or Adjacent area
	@Test public void TestTheShades()
	{
        GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        //dim well index is 8
        
		CityAreaCards TheShades = new CityAreaCards("The Shades", 1, true, CardType.CityAreaCards); 
		ge.ListPlayer.get(CurrentPlayerIndex).AddCityAreayCard(TheShades);
        
		//ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.remove(4);
		ge.ActivateCityAreaEffect(CurrentPlayerIndex);
        
        //TODO try to remove all the shades area and all adjacent area...one of them must be true
        boolean RemoveSuccess = ge.RemoveTroubleMaker(7);
        
		assertTrue("Failed executing The Shades city area", RemoveSuccess);
    }
    
	//TODO - TestVisually
    //once per turn, you can discard a card and take anther one
	@Test public void TestUnrealEstate()
	{
        //tough to test
		 GameEngine ge = new GameEngine();
			ge.DetermineFirstPlayer();
			int CurrentPlayerIndex = ge.GetCurrentPlayer();
			boolean RemoveSuccess = true;
	        //dim well index is 8
	        ge.ShowCard(CurrentPlayerIndex);
			CityAreaCards TheShades = new CityAreaCards("Unreal Estate", 1, true, CardType.CityAreaCards); 
			ge.ListPlayer.get(CurrentPlayerIndex).AddCityAreayCard(TheShades);
	        
			//ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.remove(4);
			ge.ActivateCityAreaEffect(CurrentPlayerIndex);
	    
			ge.ShowCard(CurrentPlayerIndex);
			assertTrue("Failed executing Unreal Estate city area", RemoveSuccess);
    }
	
	//Test Random Events
    //Roll the die. If the city area card of the same value is in play then that card is placed to one side. The owner of
    //the card must remove one of his minions from the same area as the card
	@Test public void TestBloodyStupidJohnsonEvent()
	{
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
		EventCards StupidJohnson = new EventCards("Bloody Stupid Johnson", 1, true, CardType.CityAreaCards); 
        boolean Success = ge.PlayEvent(StupidJohnson, CurrentPlayerIndex, true);
        int areaAffected = ge.GetBoardDie();
        
		//boolean RemoveStatus = ge.RemoveMinion( areaAffected, CurrentPlayerIndex);
        
		assertTrue("Failed executing Bloody Stupid Johnson event", Success);
	}
    
    //Roll the die 4 times and place a demon on that area
	@Test public void TestDemonsfromtheDungeonDimensionsEvent()
	{
		//almost always the same
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
		CityAreaCards DemonsFromDungeonDimension = new CityAreaCards("Demons From The Dungeon Dimension", 1, true, CardType.CityAreaCards); 
		
		boolean Success = ge.PlayEvent(DemonsFromDungeonDimension, CurrentPlayerIndex, true);
		
        //count if there is 4 demons
        int TotalDemons = ge.CountDemonsInArea();
		assertTrue("Failed executing Demons From The Dungeon Dimension event", (TotalDemons==4));
		assertTrue("Failed executing Demons From The Dungeon Dimension event", Success);
	}
    
    //roll the dice twice and remove any building in those areas
	@Test public void TestEarthquakeEvent()
	{
		//almost always the same
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        //put a building in each area and count afterward
		CityAreaCards Earthquake = new CityAreaCards("Earthquake", 1, true, CardType.CityAreaCards); 
		
        //Used in testing only - should be 12
		
		//ge.PlaceMinionInEachArea(CurrentPlayerIndex);
		//ge.RemoveAllTroubleMaker();
		ge.PlaceBuildingInEachArea(CurrentPlayerIndex);
		
		//should show true on all arae
		ge.PrintState();
		int TotalBuildingBefore = ge.GetBuildingCount();
		boolean Success = ge.PlayEvent(Earthquake, CurrentPlayerIndex, true);
		
        int TotalBuilding = ge.GetBuildingCount();
        ge.PrintState();
		assertTrue("Failed executing Earthquake event", (TotalBuilding==10));
	}
    
    //roll the die once and remove any building in that area
	@Test public void TestExplosionsEvent()
	{
		//almost always the same
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
		CityAreaCards Explosion = new CityAreaCards("Explosion", 1, true, CardType.CityAreaCards); 
		
        //Used in testing only
        ge.PlaceBuildingInEachArea(CurrentPlayerIndex);
        
		//ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.remove(4);
		boolean Success = ge.PlayEvent(Explosion, CurrentPlayerIndex, true);
		
		int TotalBuilding = ge.GetBuildingCount();
        
		assertTrue("Failed executing Explosion event", (TotalBuilding==11));
	}
    
    //Roll the die to see where it started and continue rolling to see where it spreads
	@Test public void TestFireEvent()
	{
		//almost always the same
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
		CityAreaCards Fire = new CityAreaCards("Fire", 1, true, CardType.CityAreaCards); 
		
		//ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.remove(4);
		boolean Success = ge.PlayEvent(Fire, CurrentPlayerIndex, true);
		int TotalBuilding = ge.GetBuildingCount();
		assertTrue("Failed executing Fire event", (TotalBuilding<12));
		assertTrue("Failed executing Fire event", Success);
	}
    
    //roll the die twice to see which area are affected. If the area rolled is adjacent to the river
    //then players must move their minions to adjacent areas. Trolls and demons remain in the area
	@Test public void TestFloodEvent()
	{
		//almost always the same
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
        
        //try putting minione everywhere
		ge.PlaceMinionInEachArea(CurrentPlayerIndex );
        int TotalMinionBefore = ge.GetTotalMinion();
        
        
		CityAreaCards Flood = new CityAreaCards("Flood", 1, true, CardType.CityAreaCards); 
		
        //test if
        boolean TryToRemoveEmptyArea = ge.RemoveMinion(ge.GetBoardDie(), CurrentPlayerIndex);
        
		//ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.remove(4);
		boolean Success = ge.PlayEvent(Flood, CurrentPlayerIndex, true);
		int TotalMinionAfter = ge.GetTotalMinion();
        
        assertTrue("Failed executing Flood event", (TotalMinionBefore == TotalMinionAfter));
        assertFalse("Failed executing Flood event", TryToRemoveEmptyArea);
		assertTrue("Failed executing Flood event", Success);
	}
    
    //Each player takes it in turn to roll the die and remove one minion from the area rolled, even
    //if it does not contain a troublemaker
	@Test public void TestMysteriousMurdersEvent()
	{
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        ge.PlaceMinionInEachArea(CurrentPlayerIndex);
        
		CityAreaCards MysteriousMurders = new CityAreaCards("Mysterious Murders", 1, true, CardType.CityAreaCards); 
		
		boolean Success = ge.PlayEvent(MysteriousMurders, CurrentPlayerIndex, true);
		
        int TotalMinion = ge.GetTotalMinion();
        
		assertTrue("Failed executing MysteriousMurders event", (TotalMinion==10));
		assertTrue("Failed executing MysteriousMurders event", Success);
	}
    
    //If there are eight or more troublemaker on board, games ends immediately
	@Test public void TestRiotsEvent()
	{
		//almost always the same
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        ge.PlaceTrollInEachArea(CurrentPlayerIndex);
		CityAreaCards Riots = new CityAreaCards("Riots", 1, true, CardType.CityAreaCards); 
		
		//ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.remove(4);
		boolean Success = ge.PlayEvent(Riots, CurrentPlayerIndex, true);
		boolean GameEnded = ge.IsGameEnded();
		assertTrue("Failed executing Riots event", GameEnded);
		assertTrue("Failed executing Riots event", Success);
	}
    
    //all players must pay 2$ for each building they have or remove it instead
	@Test public void TestSubsidenceEvent()
	{
		//almost always the same
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        //put 2 buildings for player 0
        ge.PlaceMinion(1, CurrentPlayerIndex);
        ge.PlaceMinion(2, CurrentPlayerIndex);
        ge.PlaceMinion(3, CurrentPlayerIndex);
        
        int originalAmount = ge.GetPlayerBalance(CurrentPlayerIndex);
        
		CityAreaCards Subsidence = new CityAreaCards("Subsidence", 1, true, CardType.CityAreaCards); 
		
		boolean Success = ge.PlayEvent(Subsidence, CurrentPlayerIndex, true);
        int afterAmount = ge.GetPlayerBalance(CurrentPlayerIndex);
        
		boolean AmountCorrect = ((originalAmount-afterAmount)==6);
		assertTrue("Failed executing Subsidence event", AmountCorrect);
		assertTrue("Failed executing Subsidence event", Success);
	}
    
    //Roll the die to see where it lands. Remove all pieces from the area.
	@Test public void TestTheDragonEvent()
	{
		//almost always the same
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
        //place minion in every area
        
		CityAreaCards TheDragon = new CityAreaCards("The Dragon", 1, true, CardType.CityAreaCards); 
		boolean Success = ge.PlayEvent(TheDragon, CurrentPlayerIndex, true);
		
        //get current die
        int AffectedArea = ge.GetBoardDie();
        boolean RemoveEmptyMinion = ge.RemoveMinion(AffectedArea, CurrentPlayerIndex);
		assertFalse("Failed executing TheDragon event", RemoveEmptyMinion);
		assertTrue("Failed executing TheDragon event", Success);
	}
    
    //Roll the die three times and place one troll  minion in each area
	@Test public void TestTrollEvent()
	{
		//almost always the same
		GameEngine ge = new GameEngine();
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
		CityAreaCards Troll = new CityAreaCards("Troll", 1, true, CardType.CityAreaCards); 
		
		//ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.remove(4);
		boolean Success = ge.PlayEvent(Troll, CurrentPlayerIndex, true);
		
        //count total trolls
        int TotalTroll = ge.CountTrollsInArea();
		assertTrue("Failed executing Troll event", (TotalTroll == 3));
		assertTrue("Failed executing Troll event", Success);
	}
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
		
		boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
		//assertTrue("Failed executing Hubert", Success);
		//public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,List<String> s ,String relation, boolean keep) 
		
				//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
		 
	}
	
	//////////////////CMOT Dibbler//////////////////
	@Test public void TestCMOTDibbler()
	{//almost always the same
		GameEngine ge = new GameEngine(4);
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
	//SPECIFIC
	//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
	GreenCards card = new GreenCards("CMOT Dibbler", 1, true, CardType.GreenCards);
	List<String> ListVerb = new Vector<String>(); 
	ListVerb.add("roll");
	ListVerb.add("get");
	ListVerb.add("pay");
	ListVerb.add("remove");
	
	List<String> ListObject = new Vector<String>(); 
	ListObject.add("die");
	ListObject.add("4$ from Bank");
	ListObject.add("2$ to bank");
	ListObject.add("1 minion");
	
	List<String> ListCondition=new Vector<String>();
	ListCondition.add("dice>7");
	ListCondition.add("dice=1");

	
//	/public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
	Action a = new Action(1, ListVerb,ListObject , ListCondition, "and", false);
	card.AddAction(a);
	
	card.AddSymbols("S");
	card.AddSymbols("C");
		
	ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
	//check if money transfered
	
	boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
	assertTrue("Failed executing card", Success);
	}
	
	///////////////////Dr Cruces//////////////////
	@Test public void TestDrCruces()
	{//almost always the same
		GameEngine ge = new GameEngine(4);
		ge.DetermineFirstPlayer();
		int CurrentPlayerIndex = ge.GetCurrentPlayer();
		
	//SPECIFIC
	//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
	GreenCards card = new GreenCards("Dr Cruces", 1, true, CardType.GreenCards);
	//List<String> ListVerb = new Vector<String>(); 
	
	
	//List<String> ListObject = new Vector<String>(); 
	
	
	//List<String> ListCondition=new Vector<String>();
	

	
//	/public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
	Action a = new Action(1, null,null , null, "and", false);
	card.AddAction(a);
	
	card.AddSymbols("A");
	card.AddSymbols("T(3)");
		
	ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
	//check if money transfered
	
	boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
	assertTrue("Failed executing card", Success);
	}
/////////////////////end of Dr Cruces/////////////////////////
	
///////////////////captain carrot//////////////////
@Test public void Testcaptaincarrot()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("captain carrot", 1, true, CardType.GreenCards);
//List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("roll");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("die");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null , null, "and", false);
card.AddAction(a);

card.AddSymbols("M");
card.AddSymbols("RT");
card.AddSymbols("T(1)");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of captain carrot/////////////////////////


///////////////////Drumknott//////////////////
@Test public void TestDrumknott()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Drumknott", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("play");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("2 other cards");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Drumknott/////////////////////////

///////////////////Dr Whiteface//////////////////
@Test public void TestDrWhiteface()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Dr Whiteface", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("take");
ListVerb.add("give");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("5$");
ListObject.add("card to player hand");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "OR", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Dr Whiteface////////////////////////

///////////////////Foul Ole Ron//////////////////
@Test public void TestFoulOleRon()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Foul Ole Ron", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("move");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1 minion of another player to adjacent area");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Foul Ole Ron/////////////////////////

///////////////////Fresh Start Club//////////////////
@Test public void TestFreshStartClub()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Fresh Start Club", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("put minion");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("different area");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

List<String> ListCondition=new Vector<String>();
ListCondition.add("remove minion");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , ListCondition, "and", false);
card.AddAction(a);

card.AddSymbols("I");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Fresh Start Club/////////////////////////

///////////////////Gaspode//////////////////
@Test public void TestGaspode()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Gaspode", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("stop");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("move or remove minion");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("I");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Gaspode/////////////////////////

///////////////////Gimlet's Dwarf Delicatessen//////////////////
@Test public void TestGimletsDwarfDelicatessen()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Gimlet's Dwarf Delicatessen", 1, true, CardType.GreenCards);
//List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("roll");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("die");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null , null, "and", false);
card.AddAction(a);

card.AddSymbols("M");
card.AddSymbols("T(3)");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Gimlet's Dwarf Delicatessen/////////////////////////

///////////////////Groat//////////////////
@Test public void TestGroat()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Groat", 1, true, CardType.GreenCards);
//List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("roll");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("die");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null , null, "and", false);
card.AddAction(a);

card.AddSymbols("M");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Groat/////////////////////////

///////////////////Horga�s House of Ribs//////////////////
@Test public void TestHorgasHouseofRibs()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Horga�s House of Ribs", 1, true, CardType.GreenCards);
//List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("roll");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("die");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null , null, "and", false);
card.AddAction(a);

card.AddSymbols("T(3)");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Horga�s House of Ribs/////////////////////////

///////////////////Harry King//////////////////
@Test public void TestHarryKing()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Harry King", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("get");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("2$ times number of discarded card");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("M");
card.AddSymbols("S");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Harry King /////////////////////////


///////////////////HERE �N� NOW//////////////////
@Test public void TestHERENNOW()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("HERE �N� NOW", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("roll");
ListVerb.add("get");
ListVerb.add("remove from board");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1 die");
ListObject.add("3$ from a player of choice");
ListObject.add("1minion");

List<String> ListCondition=new Vector<String>();
ListCondition.add("number die>=7");
ListCondition.add("number die==1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , ListCondition, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of HERE �N� NOW/////////////////////////


//////////////////Hex//////////////////
@Test public void TestHex()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Hex", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("draw");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("3 cards");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("B");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Hex/////////////////////////


///////////////////History Monkes//////////////////
@Test public void TestHistoryMonkes()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("History Monkes", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("shuffle");
ListVerb.add("draw");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("discard cards");
ListObject.add("4cards from discard cards");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of History Monkes/////////////////////////

///////////////////Inigo Skimmer//////////////////
@Test public void TestInigoSkimmer()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Inigo Skimmer", 1, true, CardType.GreenCards);
//List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("roll");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("die");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null , null, "and", false);
card.AddAction(a);

card.AddSymbols("A");
card.AddSymbols("T(2)");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Inigo Skimmer/////////////////////////


///////////////////Leonard of Quirm//////////////////
@Test public void TestLeonardofQuirm()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Leonard of Quirm", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("roll");
ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("4 cards");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Leonard of Quirm/////////////////////////


///////////////////Librarian//////////////////
@Test public void TestLibrarian()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Librarian", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("roll");
ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("4 cards");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Librarian/////////////////////////


///////////////////Modo//////////////////
@Test public void TestModo()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Modo", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("discard");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1 card");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Modo/////////////////////////


///////////////////Mr Bent//////////////////
@Test public void TestMrBent()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Mr Bent", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("loan");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("10$ bank");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

List<String> ListCondition=new Vector<String>();
ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , ListCondition, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Mr Bent/////////////////////////


///////////////////Mr Boggis//////////////////
@Test public void TestMrBoggis()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Mr Boggis", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("roll");
//ListVerb.add("get");
ListVerb.add("take");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("2$ from each player");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Mr Boggis/////////////////////////


///////////////////Mr Gryle//////////////////
@Test public void TesMrGryle()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Mr Gryle", 1, true, CardType.GreenCards);
//List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("roll");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("die");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("dice>7");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null , null, "and", false);
card.AddAction(a);

card.AddSymbols("A");
card.AddSymbols("T(1)");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Mr Gryle/////////////////////////

///////////////////Mr Pin & Mr  Tulip//////////////////
@Test public void TestMrPinAndMrTulip()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Mr Pin & Mr  Tulip", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("loan");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("10$ bank");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

List<String> ListCondition=new Vector<String>();
ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , ListCondition, "and", false);
card.AddAction(a);

card.AddSymbols("A");
card.AddSymbols("T(1)");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Mr Pin & Mr  Tulip/////////////////////////


///////////////////mrs cake//////////////////
@Test public void Testmrscake()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("mrs cake", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("look");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("all but one personality");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

List<String> ListCondition=new Vector<String>();
ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , ListCondition, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("T(2)");
card.AddSymbols("B");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of mrs cake/////////////////////////


///////////////////Nobby Nobbs//////////////////
@Test public void TestNobbyNobbs()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Nobby Nobbs", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("get");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("3$ from a player of choice");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Nobby Nobbs/////////////////////////


///////////////////The Pink PussyCat//////////////////
@Test public void TestThePinkPussyCat()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The Pink PussyCat", 1, true, CardType.GreenCards);
//List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("10$ bank");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null , null, "and", false);
card.AddAction(a);

card.AddSymbols("T(3)");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The Pink PussyCat/////////////////////////



///////////////////Queen Molly//////////////////
@Test public void TestQueenMolly()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Queen Molly", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("get");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("2cards from a player");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("M");
card.AddSymbols("S");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Queen Molly/////////////////////////


///////////////////Rincewind//////////////////
@Test public void TestRincewind()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Rincewind", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("move");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1 minion from area with troublemaker to an adjacent area");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("RE");
card.AddSymbols("S");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Rincewind/////////////////////////



///////////////////Rosie Palm//////////////////
@Test public void TestRosiePalm()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Rosie Palm", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("give");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1 card");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

List<String> ListCondition=new Vector<String>();
ListCondition.add("take 2$");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , ListCondition, "and", false);
card.AddAction(a);

card.AddSymbols("M");
card.AddSymbols("S");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Rosie Palm/////////////////////////



///////////////////Sacharissa Cripslock//////////////////
@Test public void TestSacharissaCripslock()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Sacharissa Cripslock", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1$ for each trouble maker on board");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Sacharissa Cripslock/////////////////////////



///////////////////Sergeant Angua//////////////////
@Test public void TestSergeantAngua()
{
//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Sergeant Angua", 1, true, CardType.GreenCards);
//List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("10$ bank");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null ,null, "and", false);
card.AddAction(a);

card.AddSymbols("RT");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
int playthis = ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1;
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, playthis);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Sergeant Angua/////////////////////////



///////////////////Shonky Shop//////////////////
@Test public void TestShonkyShop()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Shonky Shop", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1$ times number of discarded card");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("B");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Shonky Shop/////////////////////////


///////////////////The Agony Aunts//////////////////
@Test public void TestTheAgonyAunts()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The Agony Aunts", 1, true, CardType.GreenCards);
//List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("10$ bank");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null ,null, "and", false);
card.AddAction(a);

card.AddSymbols("A");
card.AddSymbols("T(2)");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The Agony Aunts/////////////////////////


///////////////////patternThe Ankh Morpork Sunshine Dragon Sanctuary//////////////////
@Test public void TestTheAnkhMorporkSunshineDragonSanctuary()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The Ankh Morpork Sunshine Dragon Sanctuary", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("take");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1$ or card");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The Ankh Morpork Sunshine Dragon Sanctuary/////////////////////////


///////////////////The bank of Ankh-Morpork//////////////////
@Test public void TestThebankofAnkhMorpork()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The bank of Ankh-Morpork", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("loan");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("10$ bank");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

List<String> ListCondition=new Vector<String>();
ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , ListCondition, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The bank of Ankh-Morpork/////////////////////////


///////////////////The beggars' Guild//////////////////
@Test public void TestThebeggarsGuild()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The beggars' Guild", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("take");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("2 cards of another players");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The beggars' Guild/////////////////////////



///////////////////The Duckman//////////////////
@Test public void TestTheDuckman()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The Duckman", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("move");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1 minion of another player from area ");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

List<String> ListCondition=new Vector<String>();
ListCondition.add("adjacent area");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , ListCondition, "and", false);
card.AddAction(a);

card.AddSymbols("S");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The Duckman/////////////////////////



///////////////////The dysk//////////////////
@Test public void TestThedysk()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The dysk", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1$ for each minion in the Isle of Gods");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("B");
card.AddSymbols("S");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The dysk/////////////////////////



///////////////////The Fire Brigade//////////////////
@Test public void TestTheFireBrigade()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The Fire Brigade", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("choose");
ListVerb.add("get");
ListVerb.add("remove");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1 player");
ListObject.add("5$ from a player");
ListObject.add("building");
//ListObject.add("1 minion");

List<String> ListCondition=new Vector<String>();
ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , ListCondition, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The Fire Brigade/////////////////////////


///////////////////The Fools' Guild//////////////////
@Test public void TestTheFoolsGuild()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The Fools' Guild", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("take");
ListVerb.add("give");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("5$");
ListObject.add("card to player hand");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "OR", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The Fools' Guild/////////////////////////


///////////////////The Mended Drum//////////////////
@Test public void TestTheMendedDrum()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The Mended Drum", 1, true, CardType.GreenCards);
//List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("10$ bank");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null,null, "and", false);
card.AddAction(a);

card.AddSymbols("B");
card.AddSymbols("T(2)");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The Mended Drum/////////////////////////


///////////////////The Opera House//////////////////
@Test public void TestTheOperaHouse()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The Opera House", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1$ for each minion in the Isle of Gods");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("B");
card.AddSymbols("S");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The Opera House/////////////////////////


///////////////////The Peeled Nuts//////////////////
@Test public void TestThePeeledNuts()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The Peeled Nuts", 1, true, CardType.GreenCards);
//List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("1$ for each minion in the Isle of Gods");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null , null, "and", false);
card.AddAction(a);

//card.AddSymbols("B");
//card.AddSymbols("S");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The Peeled Nuts/////////////////////////

///////////////////The Royal Mint//////////////////
@Test public void TestTheRoyalMint()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The Royal Mint", 1, true, CardType.GreenCards);
//List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
//ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("1$ for each minion in the Isle of Gods");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null , null, "and", false);
card.AddAction(a);

card.AddSymbols("B");
card.AddSymbols("T(5)");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The Royal Mint/////////////////////////

///////////////////The Seamstresses' Guild//////////////////
@Test public void TestTheSeamstressesGuild()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The Seamstresses' Guild", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
ListVerb.add("give");
ListVerb.add("get");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("1card of yours to a player");
ListObject.add("2$ from the player");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The Seamstresses' Guild/////////////////////////


///////////////////The thieves guild//////////////////
@Test public void TestThethievesguild()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("The thieves guild", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
ListVerb.add("take");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("2$ from all player");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of The thieves guild/////////////////////////


///////////////////Wallace Sonky//////////////////
@Test public void TestWallaceSonky()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Wallace Sonky", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
ListVerb.add("protected");
//ListVerb.add("pay");
//ListVerb.add("remove");

//List<String> ListObject = new Vector<String>(); 
//ListObject.add("1$ for each minion in the Isle of Gods");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,null , null, "and", false);
card.AddAction(a);

card.AddSymbols("I");
//card.AddSymbols("S");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Wallace Sonky/////////////////////////


///////////////////Zorgo the Retro-phrenologist//////////////////
@Test public void TestZorgotheRetrophrenologist()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Zorgo the Retro-phrenologist", 1, true, CardType.GreenCards);
List<String> ListVerb = new Vector<String>(); 
//ListVerb.add("loan");
ListVerb.add("exchange");
//ListVerb.add("pay");
//ListVerb.add("remove");

List<String> ListObject = new Vector<String>(); 
ListObject.add("personality with one random unused");
//ListObject.add("4$ from Bank");
//ListObject.add("2$ to bank");
//ListObject.add("1 minion");

//List<String> ListCondition=new Vector<String>();
//ListCondition.add("pay back 12$ or lose 15 points end game");
//ListCondition.add("dice=1");


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("B");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.size()-1);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Zorgo the Retro-phrenologist/////////////////////////



	
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
		
		for (Cards card : cardManager.Green_Cards)
		{
			GreenCards CurrentGreenCard = (GreenCards)card;
			
			CurrentGreenCard.ShowImage("Test");
			
		}
	}
	
	@Test public void TestCityCardCreation()
	{
		
		ManageCards cardManager = new ManageCards(1);
		
		for (Cards card : cardManager.CityArea_Cards)
		{
			CityAreaCards CurrentCityCard = (CityAreaCards)card;
			
			CurrentCityCard.ShowImage("Test");
			
		}
	}
	
	@Test public void TestRandomEventsCreation()
	{
		
		ManageCards cardManager = new ManageCards(1);
		
		for (Cards card : cardManager.Event_Card)
		{
			EventCards CurrentEventCard = (EventCards)card;
			
			CurrentEventCard.ShowImage("Test");
			
		}
	}
	
	@Test public void TestPersonalityCreation()
	{
		
		ManageCards cardManager = new ManageCards(1);
		
		for (Cards card : cardManager.Personality_Card)
		{
			PersonalityCards CurrentPersoCard = (PersonalityCards)card;
			
			CurrentPersoCard.ShowImage("Test");
			
		}
	}


}
