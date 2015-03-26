import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/**
 * @author Parinaz Barakhshan
 * @version 2.0
 */

public class Area implements Serializable
{
		public String Name;
		private int BuildingCost;
		private int Number;

		//public  boolean IsEmpty;
		private boolean IsBuilt; //in case a building is built in the area
		private boolean IsTrouble; //in case two minions are in the area
		public String CityAreaCardName;
		private Pieces TroubleMakers;
		private  List<Pieces> ListDemons;
		private List<Pieces> ListTrolls;
		private List<Pieces> ListMinions;
		//public List<Pieces> ListBuilding;
		//public Pieces Building;
		private Pieces Building;
		private String AdjArea; 
				
		public Pieces GetBuilding() {return Building; }
		
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
		public Area(String _Name, int _Number, int _Cost, String adjArea) 
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
			AdjArea = adjArea;
		}
		
		/**
		 * @return
		 */
		public boolean GetIsBuilt() {
			return IsBuilt;
		}

		/**
		 * @param isBuilt
		 */
		public void SetIsBuilt(boolean isBuilt) {
			IsBuilt = isBuilt;
		}

		/**
		 * @return
		 */
		public boolean GetIsTrouble() {
			return IsTrouble;
		}

		/**
		 * @param isTrouble
		 */
		public void SetIsTrouble(boolean isTrouble) {
			IsTrouble = isTrouble;
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
		 * pass color as None so that you get the number of all minions in area
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
			if ((GetMinionCount(Colors.None)+(GetDemonCount())+(GetTrollCount()))>1 )
			{
			  AddTroubleMaker(p);	
			}
				
			ListMinions.add(p);
		}
		
		/**
		 * @param b piece of type building
		 */
		public void AddBuilding(Pieces b) 
		{
			if (this.HasTroubleMaker())
			{
				System.out.println("This area has a trouble marker, you cannot build a building");
			}
			else if (this.GetMinionCount(b.GetPieceColor()) > 0)
			{
				if(this.IsBuilt)
				{
					System.out.println("Each Area can only hold one building.This area already has one");
				}
				else
				{
					this.Building = b;
					this.IsBuilt = true;
				}
			}
			else
			{
				System.out.println("you do not have a minion in the area, cannnot build building");
			}
		}
		
		/**
		 * Used to initialize board
		 * @param b
		 */
		public void AddBuildingInitial(Pieces b)
		{
			this.Building = b;
			this.IsBuilt = true;
		}
		
		/**
		 * @param p Piece of type Demon
		 */
		public boolean RemoveDemons() 
		{  
			//should I remove TroubleMarker?check if troublemarker is set then I unset it?
			if(ListDemons.size()>1)
		    {
				ListDemons.remove(ListDemons.size()-1);
				
				return true;
		    }
			
			return false;
		}
		
		/**
		 * @param p Troll from Pieces class
		 */
		public boolean RemoveTrolls() 
		{  
			//should I remove TroubleMarker?check if troublemarker is set then I unset it?
			if(ListTrolls.size()>1)
			{
				ListTrolls.remove(ListTrolls.size()-1);
				
				return true;
			}
			
			return false;
		}
		
        public boolean HasTroubleMaker()
        {
           return (TroubleMakers != null); 
        }
        
		/**
		 * @param p TroubleMarker from Pieces class
		 */
		public boolean RemoveTroubleMaker() 
		{
			if (this.TroubleMakers != null)
			{
				TroubleMakers = null;
				this.IsTrouble=false ;
				
				return true;
			}
			
			return false;
			
			
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
					
					if ((this.IsTrouble))
					{
					  RemoveTroubleMaker();	
					}
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
			 System.out.printf("%-16S %-25S  %-10s %-10s %-8s %-10s %n",this.Name, this.ReportMinion(),IsTrouble, this.BuildingColor(),this.GetDemonCount(),this.GetTrollCount());
            
		}
		
		public void Assassinate(Pieces p)
		{
			if (IsTrouble)
			{
				IsTrouble=false;
				RemoveTroubleMaker();

				switch(p.GetPieceType())
				{
				case Minion:
					RemoveMinions(p.GetPieceColor());
					break;
				case Demon:
					RemoveDemons();
					break;
				case Troll:
					RemoveTrolls();
					break;
				}

			}
		}
		
		/**
		 * Return a boolean to confirm the adjency of an area
		 * @param area2
		 * @return
		 */
		public boolean AreaAdjacency(int  area2)
		{
			String [] adjAreas = this.AdjArea.split(",");
			
			for (String s : adjAreas)
			{
				if (Integer.parseInt(s) == area2)
				{
					return true;
				}
			}
			
			return false;
		}
		
		public int[] GetAdjAreas(){
			
			String [] ArrAdjArr = this.AdjArea.split(",");
			int [] ArrIntAdjArr = new int[ArrAdjArr.length];
			
			for (int i = 0 ; i < ArrAdjArr.length; i++)
			{
				ArrIntAdjArr[i] = Integer.parseInt(ArrAdjArr[i]);
			}
			
			return ArrIntAdjArr;
		}

		public boolean AreaControllled(Colors color)
		{
			//if player have building then he controls area
			if (this.IsBuilt && this.Building.GetPieceColor() == color)
			{
				return true;
			}
			else if (this.ListDemons.size() > 0)
			{
				return false;
			}
			//Count minions
			else
			{
				if (this.GetMinionCount(color) == 1)
				{
					if (this.GetMinionCount(Colors.None) == 1 && this.GetTrollCount() == 0)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					int countThis = this.GetMinionCount(color);
					
					if (countThis > 0)
					{
						int countRed = this.GetMinionCount(Colors.Red);
						int countBlue = this.GetMinionCount(Colors.Blue);
						int countYellow= this.GetMinionCount(Colors.Yellow);
						int countGreen = this.GetMinionCount(Colors.Green);
						
						int [] ColorCount = {countRed, countRed, countYellow, countGreen};
						
						int maxVal = 0;
						
						for (int i : ColorCount)
						{
							if (i >= maxVal)
							{
								maxVal = i;
							}
						}
						
						return (countThis == maxVal) ;
					}
					else
					{
						return false;
					}
				}
					
				
			}
			
		}
		
		private Colors BuildingColor()
		{
			if (this.IsBuilt)
			{
			return this.Building.GetPieceColor();
			}
			else
			{
				return Colors.None;
			}
		}
}
	
	
	
	
	
	


