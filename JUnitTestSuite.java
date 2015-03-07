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
		
		Action a = new Action(1, ListVerb,ListObject , null, null, "and", false);
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


///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, ListVerb,ListObject , ListCondition, null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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



///public Action(int n, List<String> v, List<String> o, List<String> c, List<String> a,String relation, boolean keep) 
Action a = new Action(1, null,null , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("A");
card.AddSymbols("T(3)");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, null,null , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("M");
card.AddSymbols("RT");
card.AddSymbols("T(1)");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, ListVerb,ListObject , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, ListVerb,ListObject , null, null, "OR", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, ListVerb,ListObject , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, ListVerb,ListObject , ListCondition, null, "and", false);
card.AddAction(a);

card.AddSymbols("I");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, ListVerb,ListObject , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("I");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, null,null , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("M");
card.AddSymbols("T(3)");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, null,null , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("M");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Groat/////////////////////////

///////////////////Horga’s House of Ribs//////////////////
@Test public void TestHorgasHouseofRibs()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("Horga’s House of Ribs", 1, true, CardType.GreenCards);
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
Action a = new Action(1, null,null , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("T(3)");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Horga’s House of Ribs/////////////////////////

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
Action a = new Action(1, ListVerb,ListObject , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("M");
card.AddSymbols("S");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Harry King /////////////////////////


///////////////////HERE ‘N’ NOW//////////////////
@Test public void TestHERENNOW()
{//almost always the same
GameEngine ge = new GameEngine(4);
ge.DetermineFirstPlayer();
int CurrentPlayerIndex = ge.GetCurrentPlayer();

//SPECIFIC
//public GreenCards (String _Name, int _Id, boolean _Status, CardType _Type)
GreenCards card = new GreenCards("HERE ‘N’ NOW", 1, true, CardType.GreenCards);
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
Action a = new Action(1, ListVerb,ListObject , ListCondition, null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
assertTrue("Failed executing card", Success);
}
/////////////////////end of HERE ‘N’ NOW/////////////////////////


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
Action a = new Action(1, ListVerb,ListObject , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("B");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, ListVerb,ListObject , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, null,null , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("A");
card.AddSymbols("T(2)");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, ListVerb,ListObject , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, ListVerb,ListObject , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
//card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, ListVerb,ListObject , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, ListVerb,ListObject , ListCondition, null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("C");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, ListVerb,ListObject , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("S");
card.AddSymbols("M");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
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
Action a = new Action(1, null,null , null, null, "and", false);
card.AddAction(a);

card.AddSymbols("A");
card.AddSymbols("T(1)");

ge.ListPlayer.get(CurrentPlayerIndex).PlayerCards.add(card);
//check if money transfered

boolean Success = ge.PlayCard(CurrentPlayerIndex, 0);
assertTrue("Failed executing card", Success);
}
/////////////////////end of Mr Gryle/////////////////////////



	
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
