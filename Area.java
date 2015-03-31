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
		private String Name;
		private int Number;
		private int BuildingCost;
		private boolean IsBuilt; //in case a building is built in the area
		private boolean IsTrouble; //in case two minions are in the area
		private Pieces TroubleMakers;
		private  List<Pieces> ListDemons;
		private List<Pieces> ListTrolls;
		private List<Pieces> ListMinions;
		private Pieces Building;
		private String AdjArea; 
				
		/**
		 * @return building
		 */
		public Pieces GetBuilding() 
		{
			return Building; 
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
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
		 * @return IsBuilt variable.to show if this area has been built or not
		 */
		public boolean GetIsBuilt() {
			return IsBuilt;
		}

		/**
		 * It sets the amount of Isbuilt depending on the status of the Area 
		 * @param isBuilt
		 */
		public void SetIsBuilt(boolean isBuilt) {
			IsBuilt = isBuilt;
		}

		/**
		 * @return IsTrouble variable.In case there is a TroubleMarker in the Area it returns true.
		 */
		public boolean GetIsTrouble() {
			return IsTrouble;
		}

		/**
		 * sets the variable IsTrouble.In case there is a TRoubleMarker in that Area it would be set to true.
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
		 * pass color as None so that you get the number of all minions in area
		 * @param Color Enum of type Color
		 * @return the number of minions of the requested color
		 */
		public int GetMinionPoint(Colors Color)
		{
			int MinionCount = 0;
			
			if(this.GetDemonCount() >0)
				return 0;
			
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
		 * Adds Demon to the Area.
		 * @param p Piece of type Demon
		 */
		public void AddDemons(Pieces p) 
		{
			ListDemons.add(p);
		}
		
		/**
		 * Adds troll to the area
		 * @param p Piece of type Troll
		 */
		public void AddTrolls(Pieces p) 
		{
			ListTrolls.add(p);
		}
		
		/**
		 * adds troubleMraker to the area
		 * @param p Piece of Type Trouble Marker
		 */
		public void AddTroubleMaker(Pieces p) 
		{

				this.TroubleMakers= p;
				this.IsTrouble =true;				
			
		}
		
		/**
		 * adds minions to the area-Used for JUnit tests
		 * @param p Minion Piece to add in area 
		 */
		public void AddMinionsInit(Pieces p) 
		{	
			ListMinions.add(p);
		}
		
		
		/**
		 * checks the conditions of that area to see if a troubleMarker should be added or not.
		 * @param p Piece of type minion
		 */
		public void AddMinions(Pieces p) 
		{
			if ((GetMinionCount(Colors.None)+(GetDemonCount())+(GetTrollCount()))>0 )
			{
			  AddTroubleMaker(p);	
			}
				
			ListMinions.add(p);
		}
		
		/**Checks if the area contains troubleMarker it cannot add a building to that Area.
		 * if the Area is already built then no more building on that area
		 * @param b piece of type building
		 */
		public boolean AddBuilding(Pieces b) 
		{
			if (this.HasTroubleMaker())
			{
				System.out.println("This area has a trouble marker, you cannot build a building");
				return false;
			}
			else if (this.GetMinionCount(b.GetPieceColor()) > 0)
			{
				if(this.IsBuilt)
				{
					System.out.println("Each Area can only hold one building.This area already has one");
					return false;
				}
				else
				{
					this.Building = b;
					this.IsBuilt = true;
					return true;
				}
			}
			else
			{
				System.out.println("you do not have a minion in the area, cannnot build building");
				return false;
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
		
		/**removing Demon
		 * @param p Piece of type Demon
		 */
		public boolean RemoveDemons() 
		{  
			
			if(ListDemons.size()>0)
		    {
				ListDemons.remove(ListDemons.size()-1);
				
				return true;
		    }
			
			return false;
		}
		
		/**removing troll
		 * @param p Troll from Pieces class
		 */
		public boolean RemoveTrolls() 
		{  
			//should I remove TroubleMarker?check if troublemarker is set then I unset it?
			if(ListTrolls.size()>0)
			{
				ListTrolls.remove(ListTrolls.size()-1);
				
				return true;
			}
			
			return false;
		}
		
        /**
         * @return if it has TroubleMarker in the Area
         */
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
		 * @return boolean to show if the building has been removed or not
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
		
		/**
		 * @param p - Pieces to assassinate in current Area
		 */
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
		
		/**
		 * Function will return the list of adjacent area 
		 * @return list of area index
		 */
		public int[] GetAdjAreas(){
			
			String [] ArrAdjArr = this.AdjArea.split(",");
			int [] ArrIntAdjArr = new int[ArrAdjArr.length];
			
			for (int i = 0 ; i < ArrAdjArr.length; i++)
			{
				ArrIntAdjArr[i] = Integer.parseInt(ArrAdjArr[i]);
			}
			
			return ArrIntAdjArr;
		}

		/**
		 * Define if Area is controlled by the given colors
		 * 
		 * @param color
		 * @return
		 */
		public boolean AreaControllled(Colors color)
		{
			
			boolean controlled = true;
			
			//An area with a demon cannot be controlled
			if (this.ListDemons.size() > 0)
			{
				return false;
			}
			//Count all pieces
			else
			{
				
				int CountPieces = 0;
				int CountOther = 0;
				
				if (this.IsBuilt && this.BuildingColor() == color)
				{
					CountPieces += 1;
				}
				
				CountPieces += this.GetMinionCount(color);
				
				List<Colors> lstColors = new ArrayList<Colors>();
				lstColors.add(Colors.Red);
				lstColors.add(Colors.Blue); 
				lstColors.add(Colors.Yellow); 
				lstColors.add(Colors.Green);

				for (Colors thisColor : lstColors)
				{
					if (thisColor != color )
					{
						if (this.IsBuilt && this.BuildingColor() == thisColor)
						{
							CountOther += 1;
						}

						CountOther += this.GetMinionCount(thisColor);
					}
					
					if (CountOther >= CountPieces)
					{
						controlled = false;
					}
				}
				
				return controlled;

			}
			
		}
		
		/**
		 * Return the current building color in area
		 * @return Building colors
		 */
		public Colors BuildingColor()
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
	
	
	
	
	
	


