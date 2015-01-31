import java.util.List;
import java.util.ArrayList;

/**
 * @author pari
 *
 */
public class Area 
{
	    //Attributes
		private String[] ArrName ={ "Dolly Sisters","Unreal Estate","Dragon's Landing","Small Gods","The Scours","The Hippo","The Shades","Dimwell","Longwall","Isle of Gods","Seven Sleepers","Nap Hill"};
		private String Name;
		private int[] ArrBuildingCost={6,18,12,18,6,12,6,6,12,12,18,12};
		private int BuildingCost;
		private int Number;
		public  boolean IsEmpty;
		public boolean IsBuilt; //in case a building is built in the area
		public boolean IsTrouble; //in case two minions are in the area
		public List<Pieces> TroubleMakers=new ArrayList<Pieces>();
		public List<Pieces> Demons=new ArrayList<Pieces>();
		public List<Pieces> Trolls=new ArrayList<Pieces>();
		public List<Pieces> Minions=new ArrayList<Pieces>();
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
		
		
		//Methods
		public String GetName() {return Name;}
		
		public Area(boolean b) //what is this? what is that boolean?
		{
			
		}
		
		public void AddDemons(Pieces p) 
		{
			Demons.add(p);
		}
		
		public void AddTrolls(Pieces p) 
		{
			Trolls.add(p);
		}
		
		public void AddTroubleMaker(Pieces p) //only one troublemarker in each area can be--we should check the number
		{
			TroubleMakers.add(p);
			this.IsTrouble =true;
		}
		public void AddMinions(Pieces p) 
		{
			Minions.add(p);
		}
		
		
		public void RemoveDemons(Pieces p) 
		{
			Demons.remove(p);
		}
		
		public void RemoveTrolls(Pieces p) 
		{
			Trolls.remove(p);
		}
		
		public void RemoveTroubleMaker(Pieces p) 
		{
			TroubleMakers.remove(p);
			this.IsTrouble=false ;
			
		}
		public void RemoveMinions(Pieces p) 
		{
			Minions.remove(p);
		}
		
		
		
		 
		//first initialization 
		public Area()
	 	{System.out.println( "Number"+"  "+ "Name"+"  "+" Minions"+"   $"+ "BuildingCost" +"  "+"Empty?"+"  "+"Trouble?"+"  "+"Building?"+"  "+"Demons"+"  "+"Trolls");
		for (int i=0;i<12;i++)
		{
		    this.Name=ArrName[i];
		    this.BuildingCost=ArrBuildingCost[i];
		    this.Number=i+1;
		    this.IsEmpty =true;
		    
		  /*  switch(Number)
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
		     */
		    
		    this.IsBuilt=false;
		    
		    System.out.println();
		    System.out.println();
		    System.out.println( Number+"  "+ Name+"  "+ Minions+"   $"+ BuildingCost +"  "+ IsEmpty +"  "+IsTrouble+"  "+IsBuilt+"  "+Demons+"  "+Trolls);

		    System.out.print("Adjacent areas for Area" + (i+1) + "  are  ");
		    
		     for (int j=0;j<7;j++)
		     {	
		    	 System.out.print( AdjacentAreas[i][j] + "  ");//we do not have area 0.0 means that adjacent areas finished.
		     		    	 
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
		public Area (int Number, List<Pieces> Minions, List<Pieces> Trolls, List<Pieces> Demons,List<Pieces> TroubleMakers)
		{
			try
			{
			checkAreaNumber(Number);
			}
			catch (IllegalArgumentException exception)
				{
				System.out.println(exception.getMessage());
				}
			
			this.Number=Number;
			this.Minions.addAll(Minions);
		    this.TroubleMakers.addAll(TroubleMakers);
		    this.Trolls.addAll(Trolls);
		    this.Demons.addAll(Demons);
		    
			this.Name=ArrName[Number-1];
			this.BuildingCost=ArrBuildingCost[Number-1];
			
			System.out.println("Area" + (Number)+"  "+ Name+ "   $"+ BuildingCost  +" "+Minions+" "+TroubleMakers+" "+Trolls+" "+Demons);
			
		}

		//throwing Exception
		private void checkAreaNumber(int Number) 
		{
			if(Number<0)
				throw new IllegalArgumentException("Area number cannot be Negative.");
		}
		
		
		//restore from saved point---set Area
		public void CreateArea(int Number,  boolean IsTrouble,boolean IsEmpty, boolean IsBuilt,List<Pieces> Minions,List<Pieces> Trolls,List<Pieces> Demons,List<Pieces> TroubleMaker)
		{
			this.Number=Number;
			this.IsEmpty=IsEmpty;
			this.IsBuilt=IsBuilt;
			this.Name=ArrName[Number-1];
			this.BuildingCost=ArrBuildingCost[Number-1];
			
			System.out.println("Area" + (Number)+"  "+ Name+ "   $"+ BuildingCost  +" is set  "
	    			 +IsEmpty +"  "+IsBuilt);
		}
		
		
		
		public String GetAreaState()
		{
			System.out.println("current state of Area");
			System.out.println( Number +"  "+ Name+"  "+"Minions   Trouble?    Building? "+"   $"+ BuildingCost+"Demons   Trolls");
			
			for (int i=0;i<12;i++)
			{
				
				
			System.out.println( Number +"  "+ Name+"  "+"Minions   Trouble?    Building? "+"   $"+ BuildingCost+"Demons   Trolls");
			}
			return "";
		
		
		}
		public String SaveAreaState()
		{
			//Area,..........,Last
			// Each Area should be saved
			//Format of output should be passed to lawrence
			 String EachArea;
			 String AllAreas="";
			 for (int i=0;i<12;i++)
			 {
				 EachArea=( "Area,"+(i+1)+","+ ArrName[i]+","+ Minions+","+ IsEmpty +","+IsTrouble+","+IsBuilt+","+Demons+","+Trolls+",last \n");
                 //System.out.println(EachArea);
                 
				 AllAreas=AllAreas.concat(EachArea);
				 
			 }
             //System.out.println(AllAreas);

				 return AllAreas;
			 
		}
			 
	 
		
		
		public void ReportAreaState() //to print for demo
		{
			System.out.println("Number    Name    Minions    Empty?   Trouble?   Building? Demons   Trolls");
			for (int i=0;i<12;i++)
			{
			 System.out.printf(" %-2d %-16S %-8S %-8s %-10s %-10s %-8s %-10s %n",(i+1), ArrName[i], Minions, IsEmpty ,IsTrouble, IsBuilt,Demons,Trolls);
              
			}
		}
		
		
			
		// to define if two area are adjacent
		public boolean IsAdjacent(int AreaNumber1,int AreaNumber2)
		{   
			boolean Adjacent=true;
			--AreaNumber1;
            
            for (int i=0;i<7;i++)
            	{
            	if(AdjacentAreas [AreaNumber1][i]==AreaNumber2)
            		{Adjacent=true;}
            	else
            		{Adjacent= false;}
            		
            	}
			return Adjacent;
		}
		
}
		
		
		//SaveState(BufferedWriter bw)
		//{
	//	}
		
//}
		
		//
		
		/*
		 * public int GetMoneyCount()
	{
		return this.Money;
	}
	
	/*
	 * Add to player bank roll
	 * 
	 * @param amount to add
	 */
	
	
	/*
	 * Deduct from player bank roll
	 * 
	 * @param amount to deduct
	 */
	

	/*
	 * Get the current state of the player as a string
	 * 
	 * @return Current state
	 */
	//public String GetPlayerState()
//	{
		/*
		String playerState = "Number;"+Integer.toString(this.PlayerNumber) + 
							 "personality"+Integer.toString(this.Personality.GetCardID());
							 
		return playerState; */
	//	return "";
	//}
		 
//}
	
	
	
	
	
	


