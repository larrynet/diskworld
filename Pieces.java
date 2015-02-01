import java.io.Serializable;
/**
 * @author Gay Hazan
 *
 */
public class Pieces implements Serializable{
	
	private PieceType Type;
	private Colors Color;
	
	/**
	 * Empty Constructor
	 */
	public Pieces() {}
	
	/**
	 * Default Constructor
	 * 
	 * @param Id - unique Identifier for this piece
	 * @param Type - What type of piece it is
	 * @param What color is the piece
	 */
	public Pieces( PieceType _Type, Colors _Color)
	{
		Type = _Type;
		Color = _Color;
	}
	
	public String toString() 
	{
		return this.toString();
	}
	
	/**
	 * @return
	 */
	public Colors GetPieceColor() {
		return this.Color;
	}
	
	/**
	 * @return
	 */
	public PieceType GetPieceType() {
		return this.Type;
	}
	

}


//Test for how many pieces are present per color when game start
//Test that all ID's are unique