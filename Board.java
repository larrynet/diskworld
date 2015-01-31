import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.util.Iterator;
import java.io.IOException;
import java.io.Serializable;

public class Board implements Serializable{
	public int Die;
	public int Bank;
	public  List<Area> ListArea ;
	
	public Board() 
	{}

	public String toString() 
	{
		return this.toString();
	}
	public Board(int d, int b) {
		Die = d;
		Bank = b;
		
		//TBM: init default Area
		ListArea = new ArrayList<Area>();
		
	}
	
	//TBM
	public void AddArea(List<Area> a) {
		
	}
	public Board(int d, int b, Area[] list) {
		Die = d;
		Bank = b;
		
		/*for(int i=0; i<list.length; i++) 
			ListArea.add(new Area(list[i].Name, list[i].Cost, list[i].Number, list[i].IsEmpty));
		*/
	}
	public void SaveState (BufferedWriter bw)
	{
		try {
			bw.append("board,die,"+this.Die+"\n");
			bw.append("board,bank,"+this.Bank+"\n");
			
			Iterator<Area> it=ListArea.iterator();

	        while(it.hasNext())
	        {
	        	Area CurrentArea = (Area)it.next();
	        	String StrIsEmpty = "";
	        	//if(CurrentArea.IsEmpty) StrIsEmpty = "true";
	        	//else StrIsEmpty = "false";
	        	//TBM: Area.SaveState()
	        	//bw.append("area,"+CurrentArea.Name+","+CurrentArea.Cost+","+CurrentArea.Number+","+StrIsEmpty+"\n");
	        }


		} catch (IOException e) {e.printStackTrace(); } 
	}
	public void PrintState() 
	{
		System.out.println("Board info\n==============================\n");
		System.out.println("Die current value: " + Die);
		System.out.println("Bank current balance: " + Bank);
	}
}
