package diskWorld;

public class Area 
{
	
	
		public String Name;
		public int buildingCost;
		public int Number;
		public boolean IsEmpty;
		public boolean IsBuilt; //in case a building is built in the area
		
		 
		
		public Area()
	 	{
		
		}
		
		//set Variables
		public Area(String na, int c, int n, boolean e, boolean b) 
		{
		Name = na;
		buildingCost = c;
		Number = n; 
		IsEmpty = e; 
		IsBuilt = b;
		}
		
		// to define if two area are adjacent
		public boolean isAdjacent(int playerNumber,int areaNumber)
		{
			
		}
		
}
	
	
	
	
	
	

}
