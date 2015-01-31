import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * @author pari
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
		 * Contstructor
		 * @param _Name
		 * @param _Number
		 * @param _Cost
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
		
		//Methods
		public String GetName() {return Name;}
		

		public int GetDemonCount()
		{
			if (this.ListDemons.size() > 0)
			{
				return 1;
			}
			
			return 0;
		}

		public int GetTrollCount()
		{
			if (this.ListTrolls.size() > 0)
			{
				return 1;
			}
			
			return 0;
		}
		
		public void AddDemons(Pieces p) 
		{
			ListDemons.add(p);
		}
		
		public void AddTrolls(Pieces p) 
		{
			ListTrolls.add(p);
		}
		
		public void AddTroubleMaker(Pieces p) //only one troublemarker in each area can be--we should check the number
		{
			ListTroubleMakers.add(p);
			this.IsTrouble =true;
		}
		
		public void AddMinions(Pieces p) 
		{
			ListMinions.add(p);
		}
		
		public void AddBuilding(Pieces b) 
		{
			ListBuildings.add(b);
			this.IsBuilt = true;
		}
		
		
		public void RemoveDemons(Pieces p) 
		{
			ListDemons.remove(p);
		}
		
		public void RemoveTrolls(Pieces p) 
		{
			ListTrolls.remove(p);
		}
		
		public void RemoveTroubleMaker(Pieces p) 
		{
			ListTroubleMakers.remove(p);
			this.IsTrouble=false ;
			
		}
		
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
	
		public boolean HasBuilding()
		{
			return this.IsBuilt;
		}
		
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
			
		public void PrintState() //to print for demo
		{
			 System.out.printf("%-16S %-16S  %-10s %-10s %-8s %-10s %n",this.Name, this.ReportMinion(),IsTrouble, IsBuilt,this.GetDemonCount(),this.GetTrollCount());
            
		}
		
		
			 
}
	
	
	
	
	
	


