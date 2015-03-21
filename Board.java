import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.io.IOException;

/**
 * @author parinaz Barakhshan
 * @param <ListAdjacentAreas>
 *
 */
public class Board implements Serializable {
	
	//Board Attributes
	private String[] ArrName ={ "Dolly Sisters","Unreal Estate","Dragon's Landing","Small Gods","The Scours","The Hippo","The Shades","Dimwell","Longwall","Isle of Gods","Seven Sleepers","Nap Hill"};
	private int[] ArrBuildingCost={6,18,12,18,6,12,6,6,12,12,18,12};
	public static final int TOTAL_AREA = 12;
	public static final int INITIAL_BANK = 120;
    private int Bank;
    private int Die;
	public List<Area> ListArea;
	private List<Cards> ListCityAreaCards;
	private List<Pieces> ListTroubleMakers;
	private List<Pieces> ListDemons;
	private List<Pieces> ListTrolls;
	private List<Pieces> ListDeadMinions;
	private String[] AdjacentAreas={"2,1,11",
						  "0,11,10,9,2,3",
						  "0,1,3",
						  "2,9,4",
						  "5,3,9,6,7",
						  "3,4,6",
						  "5,4,7",
						  "6,4,8",
						  "7,9,10",
						  "3,4,8,10,1",
						  "8,9,1,11",
						  "10,1,0"};
	
	//public int[] ListAdjacentAreas=new int[6];
	//Board Public Methods

	public String toString() 
	{
		Area a;
		return this.toString();
	}
	
	/**
	 * Board Constructor,
	 * initializing the areas and pieces related to Board
	 */
	public Board() 
	{	
		Bank = INITIAL_BANK;
		
		ListCityAreaCards = new ArrayList<Cards>();
		ListTroubleMakers = new ArrayList<Pieces>();
		ListDemons = new ArrayList<Pieces>();
		ListTrolls = new ArrayList<Pieces>();
		ListDeadMinions = new ArrayList<Pieces>();
		ListArea = new ArrayList<Area>();
		
		CreateAreas();
		
		CreatePieces();
	}
	
	/**
	 * Method that rolls die and sets the value of the Die attribute
	 * 
	 * @return New Die value between 1 to 12
	 */
	public int RollDie()
	{
		this.Die = (int)(Math.ceil(Math.random() * 12)); 
		
		return this.Die;
	}
	
	/**
	 * @return the amount of Die
	 */
	public  int GetDie()
	{
		return this.Die;
	}
	
	/**
	 * @param b
	 */
	public void SetBalance(int b)
	{
		this.Bank = b;
	}

	/**
	 * @return the current balance of the bank ofthe board
	 */
	public int GetBalance()
	{
		return this.Bank;
	}

	/**
	 * @param amount
	 */
	public void DeductFromBank(int amount)
	{
		this.Bank -= amount;
	}

	/**
	 * @param amount
	 */
	public void AddToBank(int amount)
	{
		this.Bank += amount;
	}

	/**
	 * @param AreaNumber
	 * @param player
	 * @return boolaen to show if it is done or not
	 */
	public boolean PlaceMinion(int AreaNumber,Player player)
	{
		if(player.GetMinionCount()!=0)
		{
			ListArea.get(AreaNumber-1).AddMinions(player.PlaceMinion());

			return true;
		}
		return false;
	}
	
	/**
	 * @param AreaNumber
	 * @param player
	 * @return
	 */
	public boolean PlaceBuilding(int AreaNumber,Player player)
	{
		if(player.GetBuildingCount()!=0)
		{
			ListArea.get(AreaNumber-1).AddBuilding(player.PlaceBuilding());
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param AreaNumber
	 * @param player
	 */
	public void RemoveBuilding(int AreaNumber, Player player)
	{
		Area currentArea = this.ListArea.get(AreaNumber);
		
		currentArea.RemoveBuilding();
	}
	
	/**
	 * @param AreaNumber
	 * @return
	 */
	public boolean PlaceTroll(int AreaNumber)
	{
		ListArea.get(AreaNumber-1).AddTrolls(this.ListTrolls.get(this.ListTrolls.size()-1));
		return true;
	}
	
	/**
	 * @param AreaNumber
	 * @return
	 */
	public boolean PlaceDemon(int AreaNumber)
	{
		ListArea.get(AreaNumber-1).AddDemons(this.ListDemons.get(this.ListDemons.size()-1));
		return true;
	}

	/**
	 * @param AreaNumber
	 * @return
	 */
	public boolean PlaceTroubleMarker(int AreaNumber)
	{
		ListArea.get(AreaNumber-1).AddTroubleMaker(this.ListTroubleMakers.get(this.ListTroubleMakers.size()-1));
		return true;
	}
	
    
	/**
	 * prints the current state of the board
	 */
	public void PrintState() 
	{
		System.out.println("Current state of the game board\n==============================\n");
		System.out.println("Die current value: " + this.Die);
		System.out.println("Bank current balance: " + this.Bank);
		System.out.println();
		System.out.printf("%-16S %-25S  %-10s %-10s %-8s %-10s %n","area","minions","trouble?","building?","demons","trolls");
		System.out.println();
		
		for (Area area : ListArea)
		{
			area.PrintState();
		}
	}
	
	//Boar Private Methods

	/**
	 * creates the areas
	 */
	private void CreateAreas()
	{
		for (int i=0;i< ArrName.length;i++)
		{
			Area NewArea = new Area(ArrName[i],i+1,ArrBuildingCost[i], AdjacentAreas[i]);
			this.ListArea.add(i,NewArea);
		}
	}
	
	/**
	 * creates the demons,Trolls,TroubleMarkers
	 */
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
	
	//check the adjacency of one area to another one-is a adjacent to b and return a boolean
	
		public boolean AreaAdjacency(int Area1, int Area2)
		{
			//since the array starts from array index 0
			return this.ListArea.get(Area1--).AreaAdjacency(Area2--);
			
		}
		
		public void Assassinate(int AreaNumber,Pieces p)
		{
			ListArea.get(AreaNumber-1).Assassinate(p);
		}
		
		/**
		 * Return Empty status of current area
		 * @param AreaIndex
		 * @return
		 */
		public boolean EmptyArea(int AreaIndex)
		{
			//Does area have any items
			if (this.ListArea.get(AreaIndex - 1).GetTrollCount() > 0)
			{
				return false;
			}
			else if (this.ListArea.get(AreaIndex - 1).GetDemonCount() > 0)
			{
				return false;
			}
			else if (this.ListArea.get(AreaIndex - 1).GetMinionCount(Colors.None) < 0)
			{
				return false;
			}
			
			return true;
		}
		
		/**
		 * Does area have demons
		 * @param AreaIndex
		 * @return
		 */
		public int AreaDemonCount(int AreaIndex)
		{
			
			return this.ListArea.get(AreaIndex - 1).GetDemonCount();
			
		}
		
		/**
		 * Does Area have trolls
		 * @param AreaIndex
		 * @return
		 */
		public int AreatrollCount(int AreaIndex)
		{
		
			return this.ListArea.get(AreaIndex -1).GetTrollCount();
		
		}
		
		/**
		 * Return the count of minions for a player
		 */
		public int CountPlayerMinionsArea(Colors Color, int Areaindex)
		{
			return this.ListArea.get(Areaindex - 1).GetMinionCount(Color);
		}
		
		/**
		 * Minion count for a specific player
		 * @param Color
		 * @return Count of minion for a player
		 */
		public int CountPlayerMinions(Colors Color)
		{
			int minionCount = 0;
			
			for (Area area : this.ListArea)
			{
				minionCount += area.GetMinionCount(Color);
			}
			
			return minionCount;
		}
		
		/**
		 * Remove a troll for an area
		 * @param areaNumber
		 */
		public boolean RemoveTroll(int areaNumber)
		{
			return this.ListArea.get(areaNumber-1).RemoveTrolls();
		}
		
		/**
		 * 
		 * @param AreaNumber
		 */
		public boolean Removetrouble(int areaNumber)
		{
			return this.ListArea.get(areaNumber-1).RemoveTroubleMaker();
		}
		
		/**
		 * 
		 * @param AreaNumber
		 */
		public boolean RemoveDemon(int areaNumber)
		{
			return this.ListArea.get(areaNumber-1).RemoveDemons();
		}
		
		/**
		 * 
		 * @param AreaNumber
		 */
		public boolean RemoveMinion(int areaNumber, Colors color)
		{
			return this.ListArea.get(areaNumber-1).RemoveMinions(color);
		}
        
        public int CountTroubleMaker()
        {
            int TotalTrouble = 0;
            for(int i=0; i<12; i++)
                if(ListArea.get(i).HasTroubleMaker())
                    TotalTrouble++;
            return TotalTrouble;
        }
        
        public Area GetArea(int i)
        {
            return ListArea.get(i);
        }
        
        public List<Pieces> GetDeadMinions()
        {
        	return this.ListDeadMinions;
        }
        
        
        //returns an array containing the areas adjacent.we do not have area 0
        public int[] GetAdjacent(int AreaIndex)
        {
        	return this.ListArea.get(AreaIndex--).GetAdjAreas();
        	
        }
        
        private boolean CheckPlaceMinion(int AreaNumber,Player player)
        {
        	//Does player have minion in area
        	if (this.ListArea.get(AreaNumber).GetMinionCount(player.GetColor()) > 0)
        	{
        		return true;
        	}
        	else
        	{
        		for (int i : this.ListArea.get(AreaNumber).GetAdjAreas())
        		{
        			if (this.ListArea.get(i).GetMinionCount(player.GetColor()) > 0)
                	{
                		return true;
                	}
        		}
        	}
        	return false;
        }
}