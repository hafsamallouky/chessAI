import javafx.util.Pair;

import java.util.ArrayList;

public class Fou extends Piece{

	public Fou(int x, int y, int joueur) {
		super(x, y, joueur);
		Jeu.controlleurPlateau.ajouterPiece(x,y,"fou", joueur);
	}

	public void deplacement(int destX, int destY) {
		for(int i = 0; i < Plateau.liste.size(); i++) {
			if (Plateau.liste.get(i).getKey() == destX && Plateau.liste.get(i).getValue() == destY) {
				//Deplacement en diagonal
				Jeu.controlleurPlateau.enleverPiece(destX,destY);
				Jeu.controlleurPlateau.enleverPiece(x,y);
				Jeu.controlleurPlateau.ajouterPiece(destX,destY,"fou",joueur);
				Jeu.plateau.deplacerDonnee(x,y,destX,destY,this);
				this.x = destX;
				this.y = destY;
				break;
			}
		}
	}

	public ArrayList<Pair<Integer, Integer>> deplacementPossible() {
		ArrayList<Pair<Integer, Integer>> liste = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(Math.abs(i - x) == Math.abs(j - y) && (!caseOccupee(i,j,joueur) || estMangeable(i,j,joueur)) && !trajectoireOccupee(x,y,i,j)){
					liste.add(new Pair(i,j));
				}
			}
		}
		return liste;
	}

}
