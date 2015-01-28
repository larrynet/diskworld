
public class ManageCards 
{

	
	
	public static void CardInitiate()
	{
	 Cards[] c=new Cards[5];
	 Personality [] p=new Personality[8];
	 Event []e=new Event[12];
	 CityArea [] ct=new CityArea[12];
	 
	 String []PersonalityName={"PersonalityRole 1","PersonalityRole 2","PersonalityRole 3","PersonalityRole 4","PersonalityRole 5","PersonalityRole 6","PersonalityRole 7"};
     String []EventName={"Event 1","Event 2","Event 3","Event 4","Event 5","Event 6","Event 7","Event 8","Event 9","Event 10","Event 11","Event 12"};
	 String []CityAreaName={"CityArea 1","CityArea 2","CityArea 3","CityArea 4","CityArea 5","CityArea 6","CityArea","CityArea 8","CityArea 9","CityArea 10","CityArea 11","CityArea 12"};
	 
	 
	 int [] PersonalityId={101,102,103,104,105,106,107};
     int [] EventId={201,202,203,204,205,206,207,208,209,210,211,212};
	 int []CityAreaId={301,302,303,304,305,306,307,308,309,310,311,312};
	 
	 
	 
	 
	 
	for (int i =0;i<=3; i++)
	{	
     c[i] = new  Cards();
     if (i==0)              // If It is a Personality Card then create the list of personality cards
     {   
    	 for (int j=0; j<=6;j++)
    		 
    	 {
    	 p[j]=new Personality(PersonalityName[j],PersonalityId[j]);
    	 System.out.print(PersonalityName[j]);
    	 System.out.print(",");
    	 }
     }
     else if (i==0)
     {
    	 for (int j=0;j<=11;j++)
    	 {
    		 e[j]=new Event(EventName[j],EventId[j]);
    		
    		 System.out.print(EventName[j]);
    		 System.out.print(",");
    	 }
     }
     
     else if (i==1)
     {
    	 for (int j=0;j<=11;j++)
    	 {
    		 ct[j]=new CityArea(CityAreaName[j],CityAreaId[j]);
    		
    		 System.out.print(CityAreaName[j]);
    		 System.out.print(",");
    	 }
     }
     else if (i==2)
     {
    	  Green g= new Green();
     }
     else if(i==3)
     {
          Brown b=new Brown();
      }
     System.out.println("");
     }
	 
	}
	}

