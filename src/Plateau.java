import javafx.util.Pair;

import java.sql.Struct;
import java.util.ArrayList;

public class Plateau {
	public static boolean fin = false;
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
			liste = piece.deplacementPossible(this);
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
		if(tourJoueur == 1){
			tourJoueur = 0;
			if(estEchec(0)){
				System.out.println("blanc est echec");
				if(estMat(0)){
					System.out.println("le joueur blanc a perdu");
					fin = true;
				}
			}
			if(!fin) {
				Jeu.plateau.origineMinMax(Jeu.plateau, 3);
			}
		}else{
			tourJoueur = 1;
			if(estEchec(1)){
				System.out.println("noir est echec");
				if(estMat(1)){
					System.out.println("le joueur noir a perdu");
					fin = true;
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
					liste = tabCase[i][j].getPiece().deplacementPossible(this);
					if (liste.size() != 0) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/*==================== IA ====================*/

	public double heuristique(int joueur){
		double res = 0;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(tabCase[i][j].getPiece() != null && tabCase[i][j].getPiece().joueur == joueur) {
					res += tabCase[i][j].getPiece().heuristiquePiece();
					res += tabCase[i][j].getPiece().valeur * 4;
				}else if (tabCase[i][j].getPiece() != null && tabCase[i][j].getPiece().joueur != joueur){
					res -= tabCase[i][j].getPiece().heuristiquePiece();
					res -= tabCase[i][j].getPiece().valeur * 4;
				}
			}
		}
		return res;
	}

	public void origineMinMax(Plateau plateau, int profondeur){
		StructPlateau root = new StructPlateau(null, 0,0,0,0, plateau, null, 0);
		root.peuplerFils(0);
		minMax(root, profondeur, true);
		root.maximiser();
		root.deplacerIA();
	}

	public void minMax(StructPlateau structPlateau, int profondeur, boolean max){
		if(profondeur == 0){
			structPlateau.heuristique = structPlateau.plateau.heuristique(0);
		}else{
			if(max) {
				for (int i = 0; i < structPlateau.fils.size(); i++) {
					if(profondeur != 1) {
						structPlateau.fils.get(i).peuplerFils(1);
					}
					if(structPlateau.fils.get(i).plateau.estEchec(1) && structPlateau.fils.get(i).plateau.estMat(1)){
						structPlateau.fils.get(i).heuristique = 2000;
					}else {
						minMax(structPlateau.fils.get(i), profondeur - 1, false);
					}
				}
			}else{
				for (int i = 0; i < structPlateau.fils.size(); i++) {
					if(profondeur != 1) {
						structPlateau.fils.get(i).peuplerFils(0);
					}
					if(structPlateau.fils.get(i).plateau.estEchec(0) && structPlateau.fils.get(i).plateau.estMat(0)){
						structPlateau.fils.get(i).heuristique = -2000;
					}else {
						minMax(structPlateau.fils.get(i), profondeur - 1, true);
					}
				}
			}
		}
	}


	class StructPlateau{
		Piece piece;
		Pair<Integer, Integer> depart;
		Pair<Integer, Integer> arrive;
		Plateau plateau;
		StructPlateau pere;
		ArrayList<StructPlateau> fils;
		double heuristique;
		int joueur;

		public StructPlateau(Piece piece, int depX, int depY, int arrX, int arrY, Plateau plateau, StructPlateau pere, int joueur){
			this.piece = piece;
			this.depart = new Pair<>(depX, depY);
			this.arrive = new Pair<>(arrX, arrY);
			this.plateau = plateau;
			this.pere = pere;
			heuristique = -9999;
			fils = new ArrayList<>();
			this.joueur = joueur;
		}

		public void deplacerIA(){
			StructPlateau tmpSP = null;
			double tmpH = -999;
			for(int i = 0; i < this.fils.size(); i ++){
				if(this.fils.get(i).heuristique > tmpH){
					tmpSP = this.fils.get(i);
					tmpH = this.fils.get(i).heuristique;
				}
			}

			Jeu.plateau.liste = new ArrayList<Pair<Integer, Integer>>();
			Jeu.plateau.liste.add(tmpSP.arrive);
			Jeu.plateau.tabCase[tmpSP.depart.getKey()][tmpSP.depart.getValue()].getPiece().deplacement(tmpSP.arrive.getKey(), tmpSP.arrive.getValue());
		}


		public void maximiser(){
			for(int i = 0; i<fils.size(); i++){
				if(fils.get(i).heuristique == -9999){
					fils.get(i).minimiser();
				}
			}
			this.heuristique = this._maximum();
		}

		public void minimiser(){
			for(int i = 0; i<fils.size(); i++){
				if(fils.get(i).heuristique == -9999){
					fils.get(i).maximiser();
				}
			}
			this.heuristique = this._minimum();
		}

		public double _maximum(){
			double tmp = -9999;
			for(int i = 0; i < fils.size(); i++){
				if(tmp < fils.get(i).heuristique){
					tmp = fils.get(i).heuristique;
				}
			}
			return tmp;
		}

		public double _minimum(){
			double tmp = 9999;
			for(int i = 0; i < fils.size(); i++){
				if(tmp > fils.get(i).heuristique){
					tmp = fils.get(i).heuristique;
				}
			}
			return tmp;
		}

		public void peuplerFils(int joueur){
			ArrayList<Pair<Integer, Integer>> listeDep;
			int joueurAdverse;
			if(joueur == 1){
				joueurAdverse = 0;
			}else{
				joueurAdverse = 1;
			}

			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (plateau.tabCase[i][j].getPiece() != null && plateau.tabCase[i][j].getPiece().joueur == joueur) {
						listeDep = plateau.tabCase[i][j].getPiece().deplacementPossible(plateau);
							for (int k = 0; k < listeDep.size(); k++) {
								this.fils.add(
										new StructPlateau(plateau.tabCase[i][j].getPiece(), i, j,
												listeDep.get(k).getKey(), listeDep.get(k).getValue(),
												new Plateau(this.plateau),
												this, joueurAdverse)
								);
							fils.get(fils.size() - 1).plateau.tabCase[i][j].getPiece().x = listeDep.get(k).getKey();
							fils.get(fils.size() - 1).plateau.tabCase[i][j].getPiece().y = listeDep.get(k).getValue();
							fils.get(fils.size() - 1).plateau.deplacerDonnee(i, j, listeDep.get(k).getKey(), listeDep.get(k).getValue(), fils.get(fils.size() - 1).plateau.tabCase[i][j].getPiece());
						}
					}
				}
			}
		}
	}



}
