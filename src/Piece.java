import javafx.util.Pair;

import java.util.ArrayList;

public class Piece {
	protected int x;
	protected int y;
	protected int joueur;
	
	public Piece(int x, int y, int joueur) {
		this.x = x;
		this.y = y;
		this.joueur = joueur;
	}

	public void deplacement(int destX, int destY){
		System.out.println("Erreur");
	}

	public ArrayList<Pair<Integer, Integer>> deplacementPossible(){
		System.out.println("Erreur");
		return null;
	}

	public boolean caseOccupee(int x, int y, int joueur){
		if(Plateau.tabCase[x][y].getPiece() == null){
			return false;
		}else{
			return true;
		}
	}
	
}
