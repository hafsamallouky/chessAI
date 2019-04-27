import javafx.util.Pair;

import java.util.ArrayList;

public class Roi extends Piece{

	public Roi(int x, int y, int joueur, Plateau plateau) {
		super(x, y, joueur, plateau);
		if(plateau == Jeu.plateau) {
			Jeu.controlleurPlateau.ajouterPiece(x, y, "roi", joueur);
		}
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
		for(int i = 0; i < Jeu.plateau.liste.size(); i++) {
			if (Jeu.plateau.liste.get(i).getKey() == destX && Jeu.plateau.liste.get(i).getValue() == destY) {
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

	public boolean atteindre(int destX, int destY){
		if(Math.abs(destX  - x) <= 1 && Math.abs(destY - y) <= 1){
			return true;
		}
		return false;
	}

	public ArrayList<Pair<Integer, Integer>> deplacementPossible(Plateau plat) {
		ArrayList<Pair<Integer, Integer>> liste = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(Math.abs(i  - x) <= 1 && Math.abs(j - y) <= 1 && (!caseOccupee(i,j,joueur) || estMangeable(i,j,joueur))){
					liste.add(new Pair(i,j));
				}
			}
		}

		Plateau p = null;
		ArrayList<Pair<Integer, Integer>> listeRMV = new ArrayList<>();
		for(int k = 0; k < liste.size(); k++){
			//Creation d'un autre plateau fictif sur le modele du plateau courant
			p = new Plateau(plat);
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

	public double heuristiquePiece(){
		if(joueur == 0) {
			double[][] tab =
					{
							{2,3,1,0,0,1,3,2},
							{2,2,0,0,0,0,2,2},
							{-1,-2,-2,-2,-2,-2,-2,-1},
							{-2,-3,-3,-4,-4,-3,-3,-2},
							{-3,-4,-4,-5,-5,-4,-4,-3},
							{-3,-4,-4,-5,-5,-4,-4,-3},
							{-3,-4,-4,-5,-5,-4,-4,-3},
							{-3,-4,-4,-5,-5,-4,-4,-3}
					};
			return tab[y][x];
		}else{
			double[][] tab =
					{
							{-3,-4,-4,-5,-5,-4,-4,-3},
							{-3,-4,-4,-5,-5,-4,-4,-3},
							{-3,-4,-4,-5,-5,-4,-4,-3},
							{-3,-4,-4,-5,-5,-4,-4,-3},
							{-2,-3,-3,-4,-4,-3,-3,-2},
							{-1,-2,-2,-2,-2,-2,-2,-1},
							{2,2,0,0,0,0,2,2},
							{2,3,1,0,0,1,3,2}
					};
			return tab[y][x];
		}
	}

}
