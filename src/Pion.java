import javafx.util.Pair;

import java.util.ArrayList;

public class Pion extends Piece{

	public Pion(int x, int y, int joueur, Plateau plateau) {
		super(x, y, joueur, plateau);
		if(plateau == Jeu.plateau) {
			Jeu.controlleurPlateau.ajouterPiece(x, y, "pion", joueur);
		}
	}

	public Piece copier(Plateau plateau){
		return new Pion(x,y,joueur, plateau);
	}

	public void deplacement(int destX, int destY){
		for(int i = 0; i < Jeu.plateau.liste.size(); i++) {
			if (Jeu.plateau.liste.get(i).getKey() == destX && Jeu.plateau.liste.get(i).getValue() == destY) {
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

	public boolean atteindre(int destX, int destY){
		if(joueur == 0 && (destX == x+1 || destX == x-1) && destY == y+1){
				return true;
		}else if(joueur == 1 && (destX == x+1 || destX == x-1) && destY == y-1){
				return true;
		}
		return false;
	}

	public ArrayList<Pair<Integer, Integer>> deplacementPossible() {
		ArrayList<Pair<Integer, Integer>> liste = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				//Cas du joueur blanc (les pions vont vers le bas)
				if(((joueur == 0 && i == x && ( j == y+1 || (j == y+2 && y == 1 && !trajectoireOccupee(x,y,i,j,plateau))))&& !caseOccupee(i,j,joueur))
					||	(joueur == 0 && (i == x+1 || i == x-1) && j == y+1 && caseOccupee(i,j,joueur) && estMangeable(i,j,joueur))){
					liste.add(new Pair(i,j));
				}

				//Cas du joueur noir (les pions vont vers le haut)
				if(((joueur == 1 && i == x && ( j == y-1 || (j == y-2 && y == 6 && !trajectoireOccupee(x,y,i,j,plateau))))&& !caseOccupee(i,j,joueur))
					|| (joueur == 1 && (i == x+1 || i == x-1) && j == y-1 && caseOccupee(i,j,joueur) && estMangeable(i,j,joueur))){
					liste.add(new Pair(i,j));
				}
			}
		}

		Plateau p = null;
		ArrayList<Pair<Integer, Integer>> listeRMV = new ArrayList<>();
		for(int k = 0; k < liste.size(); k++){
			//Creation d'un autre plateau fictif sur le modele du plateau courant
			p = new Plateau(Jeu.plateau);
			//Deplacement de la piece fictive sur une case possible
			p.tabCase[x][y].getPiece().x = liste.get(k).getKey();
			p.tabCase[x][y].getPiece().y = liste.get(k).getValue();
			//Deplacement de la donnée fictive dans le tableau du plateau fictif
			p.deplacerDonnee(x, y, liste.get(k).getKey(), liste.get(k).getValue(), p.tabCase[x][y].getPiece());
			//Si le nouveau plateau est en echec, le deplacement n'est pas valide
			if(p.estEchec(this.joueur)) {
				listeRMV.add(liste.get(k));
			}
		}

		for(int l = 0; l < listeRMV.size(); l++){
			liste.remove(listeRMV.get(l));
		}
		return liste;
	}

}
