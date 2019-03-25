import javafx.util.Pair;

import java.util.ArrayList;

public class Dame extends Piece {

	public Dame(int x, int y, int joueur, Plateau plateau) {
		super(x, y, joueur, plateau);
		Jeu.controlleurPlateau.ajouterPiece(x,y,"dame", joueur);
	}

	public Piece copier(Plateau plateau){
		return new Dame(x,y,joueur, plateau);
	}

	public void deplacement(int destX, int destY) {
		for(int i = 0; i < Plateau.liste.size(); i++) {
			if (Plateau.liste.get(i).getKey() == destX && Plateau.liste.get(i).getValue() == destY) {
				//Deplacement possible
				Jeu.controlleurPlateau.enleverPiece(destX,destY);
				Jeu.controlleurPlateau.enleverPiece(x,y);
				Jeu.controlleurPlateau.ajouterPiece(destX,destY,"dame",joueur);
				Jeu.plateau.deplacerDonnee(x,y,destX,destY,this);
				this.x = destX;
				this.y = destY;
				Jeu.plateau.finTour();
				break;
			}
		}
	}

	public boolean atteindre(int destX, int destY){
		if(((destX == x || destY == y) || (Math.abs(destX - x) == Math.abs(destY - y)))
		&& !trajectoireOccupee(destY, destX, x, y)){
			return true;
		}
		return false;
	}

	public ArrayList<Pair<Integer, Integer>> deplacementPossible() {
		ArrayList<Pair<Integer, Integer>> liste = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(((i == x || j == y) || (Math.abs(i - x) == Math.abs(j - y))) && (!caseOccupee(i,j,joueur)
						|| estMangeable(i,j,joueur)) && !trajectoireOccupee(x,y,i,j)){
					//deplacementEchec(x, y, i, j, joueur, this);
					liste.add(new Pair(i, j));
				}
			}
		}
		return liste;
	}

}
