import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Parinaz Barakhshan
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
		
		private Pieces TroubleMakers;
		private List<Pieces> ListDemons;
		private List<Pieces> ListTrolls;
		private List<Pieces> ListMinions;
		private Pieces Building;
		
		
		
		public String toString() 
		{
			return this.toString();
		}

		/**
		 * Area Constructor
		 * 
		 * @param _Name Area Name
		 * @param _Number Area Number
		 * @param _Cost Area Cost
		 */
		public Area(String _Name, int _Number, int _Cost) 
		{
			Name = _Name;
			Number = _Number;
			BuildingCost = _Cost;
			IsTrouble = false;
			IsBuilt = false;
			
			TroubleMakers = null;
			ListDemons = new ArrayList<Pieces>();
			ListTrolls = new ArrayList<Pieces>();
			ListMinions = new ArrayList<Pieces>();
			Building = null;
			
		}
		
		
		/**
		 * @return Name of Area
		 */
		public String GetName() 
		{
			return this.Name;
		}
		
		
		/**
		 * @return Cost of Area
		 */
		public int GetAreaCost()
		{
			return this.BuildingCost;
		}

		/**
		 * @return number of Demons in an Area
		 */
		public int GetDemonCount()
		{
			return this.ListDemons.size() ;			
		}

		/**
		 * @return number of Trolls in an Area 
		 */
		public int GetTrollCount()
		{
			return this.ListTrolls.size();
		}
		
		
		/**
		 * @param p Piece of type Demon
		 */
		public void AddDemons(Pieces p) 
		{
			ListDemons.add(p);
		}
		
		/**
		 * @param p Piece of type Troll
		 */
		public void AddTrolls(Pieces p) 
		{
			ListTrolls.add(p);
		}
		
		/**
		 * @param p Piece of Type Trouble Marker
		 */
		public void AddTroubleMaker(Pieces p) 
		{
			this.TroubleMakers= p;
			this.IsTrouble =true;
		}
		
		/**
		 * @param p Piece of type minion
		 */
		public void AddMinions(Pieces p) 
		{
			ListMinions.add(p);
		}
		
		/**
		 * @param b piece of type building
		 */
		public void AddBuilding(Pieces b) 
		{
			this.Building = b;
			this.IsBuilt = true;
		}
		
		
		/**
		 * @param p Piece of type Demon
		 */
		public void RemoveDemons(Pieces p) 
		{
			ListDemons.remove(p);
		}
		
		/**
		 * @param p Troll from Pieces class
		 */
		public void RemoveTrolls(Pieces p) 
		{
			ListTrolls.remove(p);
		}
		
		/**
		 * @param p TroubleMarker from Pieces class
		 */
		public void RemoveTroubleMaker(Pieces p) 
		{
			TroubleMakers=null;
			this.IsTrouble=false ;
			
		}
		
		/**
		 * @param Color enum of type Color
		 * @return boolean to show if it has been done or not
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
		 * 
		 * @return boolean to show if it has been removed or not
		 */
		public boolean RemoveBuilding() 
		{
			//We need to make sure the area has building
			if (this.IsBuilt)
			{
				
				this.Building = null;
				this.IsBuilt = false;
				
				return true;
			}
			
			return false;
		}
		
		/**
		 * @param Color Enum of type Color
		 * @return the number of minions of the requested color
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
		 * @return boolean to show if the Area has building or not
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
			 System.out.printf("%-16S %-25S  %-10s %-10s %-8s %-10s %n",this.Name, this.ReportMinion(),IsTrouble, IsBuilt,this.GetDemonCount(),this.GetTrollCount());
            
		}
		
		
			 
}
	
	
	
	
	
	


