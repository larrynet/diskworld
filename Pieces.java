import java.io.Serializable;
/**
 * Class to create pieces of different type
 * 
 * @author Gay Hazan
 * @version 1.0
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
	 * @param _Type - What type of piece it is
	 * @param _Color What color is the piece
	 */
	public Pieces( PieceType _Type, Colors _Color)
	{
		Type = _Type;
		Color = _Color;
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
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return this.toString();
	}

}