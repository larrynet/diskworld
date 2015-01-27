/**
 * @author gayhazan
 *
 */
public class Pieces {
	
	private int PieceID;
	private String Type;
	private String Color;
	private String Location;
	
	
	/**
	 * Empty Constructor
	 */
	public Pieces(){}
	
	/**
	 * Default Constructor
	 * 
	 * @param Id - unique Identifier for this piece
	 * @param Type - What type of piece it is
	 * @param What color is the piece
	 */
	public Pieces(int _Id, String _Type, String _Color)
	{
		PieceID = _Id;
		Type = _Type;
		Color = _Color;
		Location = "";
	}
	
	
	/**
	 * @return Current Piece ID
	 */
	public int GetPieceID()
	{
		return this.PieceID;
	}
	
	
	/**
	 * Set location of Piece
	 * 
	 * @param Location of piece
	 */
	public void SetPieceLocation(String _location)
	{
		this.Location = _location;
	}
	

}


//Test for how many pieces are present per color when game start
//Test that all ID's are unique