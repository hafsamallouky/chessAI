import javafx.util.Pair;

import java.util.ArrayList;

public class Piece {
	protected int x;
	protected int y;
	protected int joueur;
	protected Plateau plateau;
	
	public Piece(int x, int y, int joueur, Plateau plateau) {
		this.x = x;
		this.y = y;
		this.joueur = joueur;
		this.plateau = plateau;
	}

	public Piece copier(Plateau plateau){
		System.out.println("Erreur");
		return null;
	}

	public void deplacement(int destX, int destY){
		System.out.println("Erreur");
	}

	public boolean atteindre(int destX, int destY){
		//System.out.println("erreur");
		return false;
	}

	public ArrayList<Pair<Integer, Integer>> deplacementPossible(Plateau plat){
		System.out.println("Erreur");
		return null;
	}

	public double heuristiquePiece(){
		return 0;
	}

	public boolean caseOccupee(int x, int y, int joueur, Plateau plat){
		if(plat.tabCase[x][y].getPiece() == null){
			return false;
		}else{
			return true;
		}
	}

	public boolean estMangeable(int x, int y, int joueur, Plateau plat){
		if(plat.tabCase[x][y].getPiece().joueur == joueur){
			return false;
		}else{
			return true;
		}
	}

	public boolean trajectoireOccupee(int y, int x, int destY, int destX, Plateau plateau){
		//deplacement en ligne droite
		//N, S, E, W
		if(y == destY){
			if(x < destX){
				for(int i = destX-1; i>x; i--){
					if(plateau.tabCase[y][i].getPiece() != null) {
						return true;
					}
				}
			}else if(x > destX){
				for(int i = destX+1; i<x; i++){
					if(plateau.tabCase[y][i].getPiece() != null) {
						return true;
					}
				}
			}
		}else if(x == destX){
			if(y < destY){
				for(int i = destY-1; i>y; i--){
					if(plateau.tabCase[i][x].getPiece() != null) {
						return true;
					}
				}
			}else if(y > destY){
				for(int i = destY+1; i<y; i++){
					if(plateau.tabCase[i][x].getPiece() != null) {
						return true;
					}
				}
			}
		}

		//deplacement en diagonal
		//NW, SW, SE, NE
		if(destX > x && destY > y){
			for(int cpt = 1; cpt < (destX-x); cpt ++){
				if(plateau.tabCase[y+cpt][x+cpt].getPiece() != null){
					return true;
				}
			}
		}else if(destX > x && destY < y){
			for(int cpt = 1; cpt < (destX-x); cpt ++){
				if(plateau.tabCase[y-cpt][x+cpt].getPiece() != null){
					return true;
				}
			}
		}else if(destX < x && destY > y){
			for(int cpt = 1; cpt < (x-destX); cpt ++){
				if(plateau.tabCase[y+cpt][x-cpt].getPiece() != null){
					return true;
				}
			}
		}else if(destX < x && destY < y){
			for(int cpt = 1; cpt < (x-destX); cpt ++){
				if(plateau.tabCase[y-cpt][x-cpt].getPiece() != null){
					return true;
				}
			}
		}
		return false;
	}
}
