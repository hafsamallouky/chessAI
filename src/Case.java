
public class Case {
	//Piece sur la case / null si vide
	private Piece piece;
	//coordonnées de la case (x,y)
	private int x;
	private int y;
	
	public Case(int x, int y, Piece piece) {
		this.x = x;
		this.y = y;
		this.piece = piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Piece getPiece(){ return this.piece;}
}
