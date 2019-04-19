import javafx.util.Pair;

import java.util.ArrayList;

public class Plateau {
	public static final int COTE = 8;
	//Liste des 64 cases du plateau
	public Case[][] tabCase;
	//Liste des deplacement possibles pour une piece donnée (peut etre deplacer la variable ?)
	public ArrayList<Pair<Integer, Integer>> liste;
	//Tour de jeu
	public int tourJoueur = 0;
	//Roi noir et blanc
	public Roi roiNoir;
	public Roi roiBlanc;

	public Plateau(Plateau plateau) {
		//Cas du plateau originel
		tabCase = new Case[COTE][COTE];
		if(plateau == null) {
			for (int i = 0; i < COTE; i++) {
				for (int j = 0; j < COTE; j++) {
					tabCase[i][j] = new Case(i, j, _initPlateau(i, j));
				}
			}
		}else{
			//Copie d'un plateau existant
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(plateau.tabCase[i][j].getPiece() == null) {
						this.tabCase[i][j] = new Case(i, j, null);
					}else{
						this.tabCase[i][j] = new Case(i, j, plateau.tabCase[i][j].getPiece().copier(this));
					}
				}
			}
			roiNoir = (Roi)(this.tabCase[plateau.roiNoir.x][plateau.roiNoir.y]).getPiece();
			roiBlanc = (Roi)(this.tabCase[plateau.roiBlanc.x][plateau.roiBlanc.y]).getPiece();
			tourJoueur = plateau.tourJoueur;

		}
	}

	//Set l'appartenance des premieres pieces au plateau principal
	public void appartenancePiece(){
		for(int i = 0; i < COTE; i++){
			for(int j = 0; j < COTE; j++){
				if(tabCase[i][j].getPiece() != null){
					tabCase[i][j].getPiece().plateau = this;
				}
			}
		}
	}

	//Instancie et retourne la piece devant etre sur la case (x,y) au debut du jeu
	public Piece _initPlateau(int x, int y) {
		Piece piece = null;
		int coordonnees = y * COTE + x;
		switch(coordonnees){
			case 0 : return new Tour(x,y,0, Jeu.plateau);
			case 1 : return new Cavalier(x,y,0, Jeu.plateau);
			case 2 : return new Fou(x,y,0, Jeu.plateau);
			case 4 : return new Dame(x,y,0, Jeu.plateau);
			case 3 : roiBlanc = new Roi(x,y,0, Jeu.plateau); return roiBlanc;
			case 5 : return new Fou(x,y,0, Jeu.plateau);
			case 6 : return new Cavalier(x,y,0, Jeu.plateau);
			case 7 : return new Tour(x,y,0, Jeu.plateau);
			case 8 : case 9 : case 10 : case 11 : case 12 : case 13 : case 14 : case 15 :
				return new Pion(x,y,0, Jeu.plateau);
			case 48 : case 49 : case 50 : case 51 : case 52 : case 53 : case 54 : case 55 :
				return new Pion(x,y,1, Jeu.plateau);
			case 56 : return new Tour(x,y,1, Jeu.plateau);
			case 57 : return new Cavalier(x,y,1, Jeu.plateau);
			case 58 : return new Fou(x,y,1, Jeu.plateau);
			case 59 : roiNoir = new Roi(x,y,1, Jeu.plateau); return roiNoir;
			case 60 : return new Dame(x,y,1, Jeu.plateau);
			case 61 : return new Fou(x,y,1, Jeu.plateau);
			case 62 : return new Cavalier(x,y,1, Jeu.plateau);
			case 63 : return new Tour(x,y,1, Jeu.plateau);
		}
		
		
		
		return piece;
	}

	//Renvoie la piece selectionnée par le click de la souris (ou null si pas de piece ou piece de l'autre joueur)
	public Piece selectionPiece(int x, int y){
		Piece piece = tabCase[x][y].getPiece();
		if(piece != null && piece.joueur == tourJoueur) {
			liste = piece.deplacementPossible();
			return piece;
		}
		return null;
	}

	public void deplacerDonnee(int x, int y, int destX, int destY, Piece piece){
		tabCase[x][y].setPiece(null);
		tabCase[destX][destY].setPiece(piece);
	}

	//Rajouter le check de fin de jeu ici
	public void finTour(){
		System.out.println("h : " + heurisique(tourJoueur));
		if(tourJoueur == 1){
			tourJoueur = 0;
			if(estEchec(0)){
				System.out.println("blanc est echec");
				if(estMat(0)){
					System.out.println("le joueur blanc a perdu");
				}
			}
		}else{
			tourJoueur = 1;
			if(estEchec(1)){
				System.out.println("noir est echec");
				if(estMat(1)){
					System.out.println("le joueur noir a perdu");
				}
			}
		}
	}

	public boolean estEchec(int joueur){
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++){
				if(this.tabCase[i][j].getPiece() != null
						&& this.tabCase[i][j].getPiece().joueur != joueur){
					if(joueur == 0 && this.tabCase[i][j].getPiece().atteindre(this.roiBlanc.x, this.roiBlanc.y)){
						return true;
					}else if(joueur == 1 && this.tabCase[i][j].getPiece().atteindre(this.roiNoir.x, this.roiNoir.y)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean estMat(int joueur){
		ArrayList<Pair<Integer, Integer>> liste = new ArrayList<>();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(tabCase[i][j].getPiece() != null && tabCase[i][j].getPiece().joueur == joueur) {
					liste = tabCase[i][j].getPiece().deplacementPossible();
					if (liste.size() != 0) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public double heurisique(int joueur){
		double res = 0;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(tabCase[i][j].getPiece() != null && tabCase[i][j].getPiece().joueur == joueur) {
					res += tabCase[i][j].getPiece().heuristiquePiece();
				}
			}
		}
		return res;
	}
}
