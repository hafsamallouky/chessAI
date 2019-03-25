import javafx.util.Pair;

import java.util.ArrayList;

public class Roi extends Piece{

	public Roi(int x, int y, int joueur, Plateau plateau) {
		super(x, y, joueur, plateau);
		Jeu.controlleurPlateau.ajouterPiece(x,y,"roi", joueur);
	}

	public Piece copier(Plateau plateau){
		if(joueur == 0){
			plateau.roiBlanc = this;
		}else{
			plateau.roiNoir = this;
		}
		return new Roi(x,y,joueur, plateau);
	}

	public void deplacement(int destX, int destY) {
		for(int i = 0; i < Plateau.liste.size(); i++) {
			if (Plateau.liste.get(i).getKey() == destX && Plateau.liste.get(i).getValue() == destY) {
				//Deplacement possible
				Jeu.controlleurPlateau.enleverPiece(destX,destY);
				Jeu.controlleurPlateau.enleverPiece(x,y);
				Jeu.controlleurPlateau.ajouterPiece(destX,destY,"roi",joueur);
				Jeu.plateau.deplacerDonnee(x,y,destX,destY,this);
				this.x = destX;
				this.y = destY;
				Jeu.plateau.finTour();
				break;
			}
		}
	}

	public ArrayList<Pair<Integer, Integer>> deplacementPossible() {
		ArrayList<Pair<Integer, Integer>> liste = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(Math.abs(i  - x) <= 1 && Math.abs(j - y) <= 1 && (!caseOccupee(i,j,joueur) || estMangeable(i,j,joueur))){
					liste.add(new Pair(i,j));
				}
			}
		}
		return liste;
	}

}
