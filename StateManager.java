import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * @author Lawrence
 * @version 1.0
 *
 */
public class StateManager 
{
	public StateManager() 
	{}
	
	
	/**
	 * Function will go through the filepath and load its datastructure. 
	 * 
	 * @param path
	 * @return void
	 */
	public GameEngine ImportGameState(String path) 
	{
		try
		{
			FileInputStream fin = new FileInputStream(path);
			ObjectInputStream istream = new ObjectInputStream(fin);
			GameEngine stateOfGame =  (GameEngine)istream.readObject();
			istream.close();
			
			return stateOfGame;
		}
		catch (Exception e) 
		{
			e.printStackTrace(); 
			return null;
		}
	}
	
	
	/**
	 * Function to export game state
	 * 
	 * @param path
	 * @return void
	 */
	public Boolean ExportGameState(GameEngine ge, String path) 
	{
		try
		{
			FileOutputStream fout = new FileOutputStream(path);
			ObjectOutputStream ostream = new ObjectOutputStream(fout);
			ostream.writeObject(ge);
			ostream.close();
			
			return true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
}
