import javafx.util.Pair;

import java.util.ArrayList;

public class Pion extends Piece{

	public Pion(int x, int y, int joueur) {
		super(x, y, joueur);
		Jeu.controlleurPlateau.ajouterPiece(x,y,"pion", joueur);
	}

	public void deplacement(int destX, int destY){
		if (destX == x && destY == y){
			return;
		}
		//Le pion peut se deplacer de deux depuis sa position de depart / sinon il se deplace de 1
		if(joueur == 0){
			//Joueur blanc, le pion se deplace vers le bas
			if(destX == x && ( destY == y+1 || (destY == y+2 && y == 1))){
				//Deplacement possible
				Jeu.controlleurPlateau.enleverPiece(x,y);
				Jeu.controlleurPlateau.ajouterPiece(destX,destY,"pion",joueur);
				Jeu.plateau.deplacerDonnee(x,y,destX,destY,this);
				this.x = destX;
				this.y = destY;
			}else{
				System.out.println("Déplacement impossible");
			}
		}else{
			//Joueur noir, le pion se déplace vers le haut
			if(destX == x && ( destY == y-1 || (destY == y-2 && y == 6))){
				//Deplacement possible
				Jeu.controlleurPlateau.enleverPiece(x,y);
				Jeu.controlleurPlateau.ajouterPiece(destX,destY,"pion",joueur);
				Jeu.plateau.deplacerDonnee(x,y,destX,destY,this);
				this.x = destX;
				this.y = destY;
			}else{
				System.out.println("Déplacement impossible");
			}

		}
	}

	public ArrayList<Pair<Integer, Integer>> deplacementPossible() {
		ArrayList<Pair<Integer, Integer>> liste = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				//Cas du joueur blanc (les pions vont vers le bas)
				if((joueur == 0 && i == x && ( j == y+1 || (j == y+2 && y == 1)))&& !caseOccupee(i,j,joueur)){
					liste.add(new Pair(i,j));
				}
				//Cas du joueur noir (les pions vont vers le haut)
				if((joueur == 1 && i == x && ( j == y-1 || (j == y-2 && y == 6)))&& !caseOccupee(i,j,joueur)){
					liste.add(new Pair(i,j));
				}
			}
		}
		return liste;
	}

}
