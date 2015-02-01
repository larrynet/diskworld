import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * @author parinaz Barakhshan <parinaz1983@yahoo.com>
 *
 */

public class Area implements Serializable
{
		private String Name;
		private int BuildingCost;
		private int Number;

		//public  boolean IsEmpty;
		private boolean IsBuilt; //in case a building is built in the area
		private boolean IsTrouble; //in case two minions are in the area
		
		private List<Pieces> ListTroubleMakers;
		private List<Pieces> ListDemons;
		private List<Pieces> ListTrolls;
		private List<Pieces> ListMinions;
		private List<Pieces> ListBuildings;
		
		
		public String toString() 
		{
			return this.toString();
		}

		/**
		 * Area Constructor
		 * 
		 * @param Area Name
		 * @param Area Number
		 * @param _Area Cost
		 */
		public Area(String _Name, int _Number, int _Cost) 
		{
			Name = _Name;
			Number = _Number;
			BuildingCost = _Cost;
			IsTrouble = false;
			IsBuilt = false;
			
			ListTroubleMakers = new ArrayList<Pieces>();
			ListDemons = new ArrayList<Pieces>();
			ListTrolls = new ArrayList<Pieces>();
			ListMinions = new ArrayList<Pieces>();
			ListBuildings = new ArrayList<Pieces>();
			
		}
		
		
		/**
		 * @return Name of Area
		 */
		public String GetName() 
		{
			return this.Name;
		}
		
		
		/**
		 * @return Cost of building in the Area
		 */
		public int GetAreaCost()
		{
			return this.BuildingCost;
		}

		/**
		 * @return existence of Demon
		 */
		public int GetDemonCount()
		{
			if (this.ListDemons.size() > 0)
			{
				return 1;
			}
			
			return 0;
		}

		/**
		 * @return existence of Troll
		 */
		public int GetTrollCount()
		{
			if (this.ListTrolls.size() > 0)
			{
				return 1;
			}
			
			return 0;
		}
		
		/**
		 * @param a Demon from pieces class
		 */
		public void AddDemons(Pieces p) 
		{
			ListDemons.add(p);
		}
		
		/**
		 * @param a Troll from pieces class
		 */
		public void AddTrolls(Pieces p) 
		{
			ListTrolls.add(p);
		}
		
		/**
		 * @param a Trouble Marker from pieces class
		 */
		public void AddTroubleMaker(Pieces p) //only one troublemarker in each area can be--we should check the number
		{
			ListTroubleMakers.add(p);
			this.IsTrouble =true;
		}
		
		/**
		 * @param a Minion from pieces class
		 */
		public void AddMinions(Pieces p) 
		{
			ListMinions.add(p);
		}
		
		/**
		 * @param a building from pieces class
		 */
		public void AddBuilding(Pieces b) 
		{
			ListBuildings.add(b);
			this.IsBuilt = true;
		}
		
		
		/**
		 * @param a Demon from pieces class
		 */
		public void RemoveDemons(Pieces p) 
		{
			ListDemons.remove(p);
		}
		
		/**
		 * @param a Troll from pieces class
		 */
		public void RemoveTrolls(Pieces p) 
		{
			ListTrolls.remove(p);
		}
		
		/**
		 * @param a TroubleMarker from pieces class
		 */
		public void RemoveTroubleMaker(Pieces p) 
		{
			ListTroubleMakers.remove(p);
			this.IsTrouble=false ;
			
		}
		
		/**
		 * @param Color
		 * @return
		 */
		public boolean RemoveMinions(Colors Color) 
		{
			for (Pieces Minion : ListMinions)
			{
				
				if (Minion.GetPieceColor() == Color)
				{
					ListMinions.remove(Minion);
					return true;
				}
			}
			
			return false;
		}
		
		/**
		 * @param a building from pieces class
		 * @return
		 */
		public boolean RemoveBuilding(Pieces p) 
		{
			//We need to make sure the building that we want to remove is actually the one that the area has.
			if (p == this.ListBuildings.get(0))
			{
				ListBuildings.remove(p);
				this.IsBuilt = false;
				return true;
			}
			
			return false;
		}
		
		/**
		 * @param Color
		 * @return
		 */
		public int GetMinionCount(Colors Color)
		{
			int MinionCount = 0;
			
			if (Color == Colors.None)
			{
				return this.ListMinions.size();
			}
			else
			{
				for (Pieces minion : this.ListMinions){
					
					if (minion.GetPieceColor() == Color)
					{
						MinionCount ++;
					}
				}
				
				return MinionCount;
			}
			
		}
	
		/**
		 * @return Area has building
		 */
		public boolean HasBuilding()
		{
			return this.IsBuilt;
		}
		
		/**
		 * @return A comma separated string of minions present in Area
		 */
		public String ReportMinion()
		{
			StringBuilder MinionColors = new StringBuilder();
			
			if (this.GetMinionCount(Colors.None) > 0)
			{
				for (Pieces Minion : this.ListMinions) 
				{
					MinionColors.append(Minion.GetPieceColor());
					MinionColors.append(",");
					
				}
				
				MinionColors.deleteCharAt(MinionColors.length()-1);
				
				return MinionColors.toString();
			}
			
			return "none";
		}
			
		/**
		 * Prints State of Current Area: Name, Minions in Area, Trouble Marker present, Building present, Demon count, troll count
		 */
		public void PrintState() //to print for demo
		{
			 System.out.printf("%-16S %-16S  %-10s %-10s %-8s %-10s %n",this.Name, this.ReportMinion(),IsTrouble, IsBuilt,this.GetDemonCount(),this.GetTrollCount());
            
		}
		
		
			 
}
	
	
	
	
	
	


