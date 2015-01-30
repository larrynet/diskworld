
public class ManageCards 
{
   //  ManageCards Attributes 
	public Personality[] p=new Personality[7];
	public Event []e =new Event[12];
	public CityArea [] ct=new CityArea[12];
	public Green[] g=new Green[43];
	public Brown[] b=new Brown[53];
	
	//ManageCards Methods

    public Event GetEventCard()
    { 
    	Event AvailableE=new Event();
       
    	for (int i=0;i<e.length-1  ;i++ )
    		if (this.e[i].Status==true)
    		{
    			this.e[i].Status=false;
    			AvailableE =this.e[i];
    			break;
    		}
    		else
    			AvailableE.Name=null;
    	
    	return AvailableE;
    		
    }
	/*
	 * Initiatiate all cards  the beginning of the game
	 * 
	 * 
	 */
	public  static void CardInitiate()
	{
     
	 Cards[] c=new Cards[5];
	 Personality [] p=new Personality[7];
	 Event []e=new Event[12];
	 CityArea [] ct=new CityArea[12];
	 Boolean Status=true;
	 
	 String []PersonalityName={"PersonalityRole 1","PersonalityRole 2","PersonalityRole 3","PersonalityRole 4","PersonalityRole 5","PersonalityRole 6","PersonalityRole 7"};
     String []EventName={"Event 1","Event 2","Event 3","Event 4","Event 5","Event 6","Event 7","Event 8","Event 9","Event 10","Event 11","Event 12"};
	 String []CityAreaName={"CityArea 1","CityArea 2","CityArea 3","CityArea 4","CityArea 5","CityArea 6","CityArea","CityArea 8","CityArea 9","CityArea 10","CityArea 11","CityArea 12"};
	 
	 
	 int [] PersonalityId={101,102,103,104,105,106,107};
     int [] EventId={201,202,203,204,205,206,207,208,209,210,211,212};
	 int []CityAreaId={301,302,303,304,305,306,307,308,309,310,311,312};
	 
	 
	for (int i =0;i<=4; i++)
	{	
     c[i] = new  Cards();
     if (i==0)              
     {   
    	 for (int j=0; j<=6;j++)
    		 
    	 {
    	 p[j]=new Personality(PersonalityName[j],PersonalityId[j],Status);
    	 System.out.print(PersonalityName[j]);
    	 System.out.print(",");
    	 }
    
     }
     else if (i==1)
     {
    	 for (int j=0;j<=11;j++)
    	 {
    		 e[j]=new Event(EventName[j],EventId[j],Status);
    		 System.out.print(EventName[j]);
    		 System.out.print(",");
    	 }
     }
     
     else if (i==2)
     {
    	 for (int j=0;j<=11;j++)
    	 {
    		 ct[j]=new CityArea(CityAreaName[j],CityAreaId[j],Status);
    		 System.out.print(CityAreaName[j]);
    		 System.out.print(",");
    	 }
     }
     else if (i==3)
     {
    	  Green g= new Green();
     }
     else if(i==4)
     {
          Brown b=new Brown();
      }
     System.out.println("");
     }
	 
	
	}
	
	/*
	 * Initiate the Cards and copy Cards value to the CardManager for further changes
	 * 
	 * 
	 */
	public  void CardInitiate2()
	{
     
	 Cards[] c=new Cards[5];
	 Personality [] p=new Personality[7];
	 Event []e=new Event[12];
	 CityArea [] ct=new CityArea[12];
	 
	 String []PersonalityName={"PersonalityRole 1","PersonalityRole 2","PersonalityRole 3","PersonalityRole 4","PersonalityRole 5","PersonalityRole 6","PersonalityRole 7"};
     String []EventName={"Event 1","Event 2","Event 3","Event 4","Event 5","Event 6","Event 7","Event 8","Event 9","Event 10","Event 11","Event 12"};
	 String []CityAreaName={"CityArea 1","CityArea 2","CityArea 3","CityArea 4","CityArea 5","CityArea 6","CityArea","CityArea 8","CityArea 9","CityArea 10","CityArea 11","CityArea 12"};
	 Boolean Status =true;
	 
	 int [] PersonalityId={101,102,103,104,105,106,107};
     int [] EventId={201,202,203,204,205,206,207,208,209,210,211,212};
	 int []CityAreaId={301,302,303,304,305,306,307,308,309,310,311,312};
	 
	 
	for (int i =0;i<=4; i++)
	{	
     c[i] = new  Cards();
     if (i==0)              
     {   
    	 for (int j=0; j<=this.p.length-1 ;j++)
    		 
    	 {
    	 p[j]=new Personality(PersonalityName[j],PersonalityId[j],Status);
    	 System.out.print(PersonalityName[j]);
    	 System.out.print(",");
    	 }
    	this.p=p; 
     }
     else if (i==1)
     {
    	 for (int j=0;j<=this.e.length-1 ;j++)
    	 {
    		 e[j]=new Event(EventName[j],EventId[j],Status);
    		 System.out.print(EventName[j]);
    		 System.out.print(",");
    	 }
    	 this.e=e;
     }
     
     else if (i==2)
     {
    	 for (int j=0;j<=this.ct.length-1;j++)
    	 {
    		 ct[j]=new CityArea(CityAreaName[j],CityAreaId[j],Status);
    		 System.out.print(CityAreaName[j]);
    		 System.out.print(",");
    	 }
    	 this.ct=ct;
     }
     else if (i==3)
     {
    	  Green g= new Green();
    	 
    	  
     }
     else if(i==4)
     {
          Brown b=new Brown();
      }
     System.out.println("");
     }
	
	}
	/*
	 * This Method is showing the current state of each cards 
	 * 
	 * 
	 */
	public void GetState()
	{ 
		System.out.print("Cards,");
		for (int i=0;i<p.length-1  ;i++ )
			System.out.print(p[i].Name+ "," + p[i].Id + "," + p[i].Status+",");
		System.out.print(",Last");
		System.out.println("");
		
		
		System.out.print("Cards,");
		for (int i=0;i<e.length-1  ;i++ )
			System.out.print( e[i].Name + "," + e[i].Id +","+ e[i].Status+",");
		System.out.print(",Last"); 
		System.out.println("");
		
		
		System.out.print("Cards,");
		for (int i=0;i<ct.length-1  ;i++ )
			System.out.print(ct[i].Name +","+ ct[i].Id +","+ ct[i].Status+",");
		System.out.print(",Last");  
		
		System.out.println("");
		
		//for (int i=0;i<p.length-1  ;i++ )
			//or (int i=0;i<p.length-1  ;i++ )
			//System.out.print("Cards"+p[i].Name+p[i].Id+p[i].Status+"Last");
		
	}
//	 System.out.println(this.e[1].Id);
}



	

