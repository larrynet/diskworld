

/**
 * @author pari Alt+shift+j
 *
 */


public class Area 
{
	
	
		private String[] ArrName ={ "Dolly Sisters","Unreal Estate","Dragon's Landing","Small Gods","The Scours","The Hippo","The Shades","Dimwell","Longwall","Isle of Gods","Seven Sleepers","Nap Hill"};
		private String Name;
		private int[] ArrBuildingCost={6,18,12,18,6,12,6,6,12,12,18,12};
		private int BuildingCost;
		private int Number;
		private boolean IsEmpty;
		private boolean IsBuilt; //in case a building is built in the area
		
		private int[][] AdjacentAreas=new int[][]
		{
			//first numbers is the number of Area,0 is there to keep the number of each column to 7
			//an array of 12*7
			{1,2,3,12,0,0,0},
			{2,1,4,11,10,0,0},
			{3,1,4,2,0,0,0},
			{4,3,6,5,0,0,0},
			{5,4,6,7,10,8,0},
			{6,4,5,7,0,0,0},
			{7,6,5,8,0,0,0},
			{8,9,7,5,0,0,0},
			{9,8,11,10,0,0,0},
			{10,2,4,5,9,11,0},
			{11,9,12,2,10,0,0},
			{12,11,1,2,0,0,0}
			
		};
		
		 
		//first initialization 
		public Area()
	 	{
		for (int i=0;i<12;i++)
		{
		    this.Name=ArrName[i];
		    this.BuildingCost=ArrBuildingCost[i];
		    this.Number=i+1;
		    
		    switch(Number)
		    {
		    case 1:
		    case 5:
		    case 7:
		    	{
		    		this.IsEmpty=false;
		    		break;
		    	}
		    default:
		    	{
		    	this.IsEmpty=true;
		    	break;
		    	}
		    		
		     }
		    
		    this.IsBuilt=false;
		    
		    System.out.println();
		    System.out.println();
		    System.out.println("Area" + (Number)+"  "+ Name+ "   $"+ BuildingCost  +" is created  "
	    			 +IsEmpty +"  "+IsBuilt);
		    System.out.print("Adjacent areas for Area" + (i+1) + "  are  ");
		    
		     for (int j=0;j<7;j++)
		     {
		    	 
		    	 System.out.print( AdjacentAreas[i][j] + "  ");
		     }
		}
		 System.out.println();
		 System.out.println();
		 System.out.println("12 Areas are created");
		}
		
		
		//restore----set Area--modify Area
		//set Variables what info lawrence should send for me to reload
		//IsEmpty is not enough.we should know which player.which piece.is in the area
		//IsBuilt is not enough.Which player has built in the area?
		public Area(int Number, boolean IsEmpty, boolean IsBuilt) 
		{
			this.Number=Number;
			this.IsEmpty=IsEmpty;
			this.IsBuilt=IsBuilt;
			this.Name=ArrName[Number-1];
			this.BuildingCost=ArrBuildingCost[Number-1];
			
			System.out.println("Area" + (Number)+"  "+ Name+ "   $"+ BuildingCost  +" is set  "
	    			 +IsEmpty +"  "+IsBuilt);
			
		}
		
		//restore from saved point---set Area
		public void CreateArea(int Number, boolean IsEmpty, boolean IsBuilt)
		{
			this.Number=Number;
			this.IsEmpty=IsEmpty;
			this.IsBuilt=IsBuilt;
			this.Name=ArrName[Number-1];
			this.BuildingCost=ArrBuildingCost[Number-1];
			
			System.out.println("Area" + (Number)+"  "+ Name+ "   $"+ BuildingCost  +" is set  "
	    			 +IsEmpty +"  "+IsBuilt);
		}
		
		
		public void GetArea()
		{
			for (int i=0;i<12;i++)
			{
				
				
			System.out.println("Area" + (Number)+"  "+ Name+ "   $"+ BuildingCost  +" is created  " +
					IsEmpty +"  "+IsBuilt);
			}
		}
		
	/*	public String SaveArea()
		{
			//Area,..........,Last
			// Each Area should be saved
			//Format of output should be passed to lawrence
		}
		
		public void print() //to print for demo
		{
			
		}
		
		// to define if two area are adjacent
		public boolean isAdjacent(int playerNumber,int areaNumber)
		{
			public int
		}
		
		
		SaveState(BufferedWriter bw)
		{
		}
		*/
		
}
	
	
	
	
	
	


