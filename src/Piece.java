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

	public boolean atteindre(int destX, int destY){
		//System.out.println("erreur");
		return false;
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

	public boolean estMangeable(int x, int y, int joueur){
		if(Plateau.tabCase[x][y].getPiece().joueur == joueur){
			return false;
		}else{
			return true;
		}
	}

	public boolean trajectoireOccupee(int y, int x, int destY, int destX){
		//deplacement en ligne droite
		//N, S, E, W
		if(y == destY){
			if(x < destX){
				for(int i = destX-1; i>x; i--){
					if(Plateau.tabCase[y][i].getPiece() != null) {
						return true;
					}
				}
			}else if(x > destX){
				for(int i = destX+1; i<x; i++){
					if(Plateau.tabCase[y][i].getPiece() != null) {
						return true;
					}
				}
			}
		}else if(x == destX){
			if(y < destY){
				for(int i = destY-1; i>y; i--){
					if(Plateau.tabCase[i][x].getPiece() != null) {
						return true;
					}
				}
			}else if(y > destY){
				for(int i = destY+1; i<y; i++){
					if(Plateau.tabCase[i][x].getPiece() != null) {
						return true;
					}
				}
			}
		}

		//deplacement en diagonal
		//NW, SW, SE, NE
		if(destX > x && destY > y){
			for(int cpt = 1; cpt < (destX-x); cpt ++){
				if(Plateau.tabCase[y+cpt][x+cpt].getPiece() != null){
					return true;
				}
			}
		}else if(destX > x && destY < y){
			for(int cpt = 1; cpt < (destX-x); cpt ++){
				if(Plateau.tabCase[y-cpt][x+cpt].getPiece() != null){
					return true;
				}
			}
		}else if(destX < x && destY > y){
			for(int cpt = 1; cpt < (x-destX); cpt ++){
				if(Plateau.tabCase[y+cpt][x-cpt].getPiece() != null){
					return true;
				}
			}
		}else if(destX < x && destY < y){
			for(int cpt = 1; cpt < (x-destX); cpt ++){
				if(Plateau.tabCase[y-cpt][x-cpt].getPiece() != null){
					return true;
				}
			}
		}
		return false;
	}

	public boolean estEchec(int destX, int destY, int joueur){
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++){
				if(Plateau.tabCase[i][j].getPiece() != null
				&& Plateau.tabCase[i][j].getPiece().joueur != joueur){
					if(joueur == 0 && Plateau.tabCase[i][j].getPiece().atteindre(Plateau.roiBlanc.x, Plateau.roiBlanc.y)){
						return true;
					}else if(joueur == 1 && Plateau.tabCase[i][j].getPiece().atteindre(Plateau.roiNoir.x, Plateau.roiNoir.y)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
