import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.io.IOException;


public class Board implements Serializable {
	
	//Board Attributes
	private String[] ArrName ={ "Dolly Sisters","Unreal Estate","Dragon's Landing","Small Gods","The Scours","The Hippo","The Shades","Dimwell","Longwall","Isle of Gods","Seven Sleepers","Nap Hill"};
	private int[] ArrBuildingCost={6,18,12,18,6,12,6,6,12,12,18,12};
	
    private int Bank;
    private int Die;
	private  List<Area> ListArea;
	private List<Card> ListCityAreaCards;
	private List<Pieces> ListTroubleMakers;
	private List<Pieces> ListDemons;
	private List<Pieces> ListTrolls;
	private List<Pieces> ListDeadMinions;
	
	//Board Public Methos

	public String toString() 
	{
		return this.toString();
	}
	
	public Board() 
	{	
		Bank = 120;
		
		ListCityAreaCards = new ArrayList<Card>();
		ListTroubleMakers = new ArrayList<Pieces>();
		ListDemons = new ArrayList<Pieces>();
		ListTrolls = new ArrayList<Pieces>();
		ListDeadMinions = new ArrayList<Pieces>();
		ListArea = new ArrayList<Area>();
		
		CreateAreas();
		
		CreatePieces();
	}
	
	public int RollDie()
	{
		this.Die = (int)(Math.ceil(Math.random() * 12)); 
		
		return this.Die;
	}
	
	public int GetBalance()
	{
		return this.Bank;
	}

	public void DeductFromBank(int amount)
	{
		this.Bank -= amount;
	}

	public void AddToBank(int amount)
	{
		this.Bank += amount;
	}
	
	public void AddArea(List<Area> a) 
	{
		//TODO
	}
	
	public boolean PlaceMinion(int AreaNumber,Player player)
	{
		if(player.GetMinionCount()!=0)
		{
			ListArea.get(AreaNumber-1).AddMinions(player.PlaceMinion());
			
			return true;
		}
		return false;
	}
	
	public boolean PlaceBuilding(int AreaNumber,Player player)
	{
		if(player.GetBuildingCount()!=0)
		{
			ListArea.get(AreaNumber-1).AddBuilding(player.PlaceBuilding());
			
			return true;
		}
		
		return false;
	}
	
	public boolean PlaceTroll(int AreaNumber)
	{
		ListArea.get(AreaNumber-1).AddTrolls(this.ListTrolls.get(this.ListTrolls.size()-1));
		return true;
	}
	
	public boolean PlaceDemon(int AreaNumber)
	{
		ListArea.get(AreaNumber-1).AddDemons(this.ListDemons.get(this.ListDemons.size()-1));
		return true;
	}

	public boolean PlaceTroubleMarker(int AreaNumber)
	{
		ListArea.get(AreaNumber-1).AddTroubleMaker(this.ListTroubleMakers.get(this.ListTroubleMakers.size()-1));
		return true;
	}


	public boolean RemoveMinion(int AreaNumber, Player player)
	{
		
		return true;
	}
	
	public Board(int d, int b, Area[] list) 
	{
		Die = d;
		Bank = b;
		
		/*for(int i=0; i<list.length; i++) 
			ListArea.add(new Area(list[i].Name, list[i].Cost, list[i].Number, list[i].IsEmpty));
		*/
	}

	public void SaveState (BufferedWriter bw)
	{
		try {
			bw.append("board,die,"+this.Die+"\n");
			bw.append("board,bank,"+this.Bank+"\n");
			
			Iterator<Area> it=ListArea.iterator();

	        while(it.hasNext())
	        {
	        	Area CurrentArea = (Area)it.next();
	        	String StrIsEmpty = "";
	        	//if(CurrentArea.IsEmpty) StrIsEmpty = "true";
	        	//else StrIsEmpty = "false";
	        	//TBM: Area.SaveState()
	        	//bw.append("area,"+CurrentArea.Name+","+CurrentArea.Cost+","+CurrentArea.Number+","+StrIsEmpty+"\n");
	        }


		} catch (IOException e) {e.printStackTrace(); } 
	}

	public void PrintState() 
	{
		System.out.println("Current state of the game board\n==============================\n");
		System.out.println("Die current value: " + this.Die);
		System.out.println("Bank current balance: " + this.Bank);
		System.out.println();
		System.out.printf("%-16S %-16S  %-10s %-10s %-8s %-10s %n","area","minions","trouble?","building?","demons","trolls");
		
		
		for (Area area : ListArea)
		{
			area.PrintState();
		}
	}
	
	//Boar Private Methods

	private void CreateAreas()
	{
		for (int i=0;i< ArrName.length;i++)
		{
			Area NewArea = new Area(ArrName[i],i+1,ArrBuildingCost[i]);
			this.ListArea.add(i,NewArea);
		}
	}
	
	private void CreatePieces()
	{
		//Create Demons

		for (int i = 0; i < 4; i++){

			Pieces Demon = new Pieces(PieceType.Demon, Colors.Orange);

			this.ListDemons.add(Demon);

			}

		//Create Trolls

		for (int i =0; i < 3; i++){

			Pieces Troll = new Pieces(PieceType.Troll, Colors.Brown);

			this.ListTrolls.add(Troll);

			}

			//Create Trouble Marker

			for (int i = 0; i < 12; i++) {

				Pieces TroubleMarker = new Pieces(PieceType.TroubleMarker, Colors.Black);

				this.ListTroubleMakers.add(TroubleMarker);

			}

	}
}
