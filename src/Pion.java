import javafx.util.Pair;

import java.util.ArrayList;

public class Pion extends Piece{

	public Pion(int x, int y, int joueur, Plateau plateau) {
		super(x, y, joueur, plateau);
		Jeu.controlleurPlateau.ajouterPiece(x,y,"pion", joueur);
	}

	public Piece copier(Plateau plateau){
		return new Pion(x,y,joueur, plateau);
	}

	public void deplacement(int destX, int destY){
		for(int i = 0; i < Plateau.liste.size(); i++) {
			if (Plateau.liste.get(i).getKey() == destX && Plateau.liste.get(i).getValue() == destY) {
				//Deplacement possible
				Jeu.controlleurPlateau.enleverPiece(destX,destY);
				Jeu.controlleurPlateau.enleverPiece(x,y);
				Jeu.controlleurPlateau.ajouterPiece(destX,destY,"pion",joueur);
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
				//Cas du joueur blanc (les pions vont vers le bas)
				if(((joueur == 0 && i == x && ( j == y+1 || (j == y+2 && y == 1 && !trajectoireOccupee(x,y,i,j))))&& !caseOccupee(i,j,joueur))
					||	(joueur == 0 && (i == x+1 || i == x-1) && j == y+1 && caseOccupee(i,j,joueur) && estMangeable(i,j,joueur))){
					liste.add(new Pair(i,j));
				}

				//Cas du joueur noir (les pions vont vers le haut)
				if(((joueur == 1 && i == x && ( j == y-1 || (j == y-2 && y == 6 && !trajectoireOccupee(x,y,i,j))))&& !caseOccupee(i,j,joueur))
					|| (joueur == 1 && (i == x+1 || i == x-1) && j == y-1 && caseOccupee(i,j,joueur) && estMangeable(i,j,joueur))){
					liste.add(new Pair(i,j));
				}
			}
		}
		return liste;
	}

}
