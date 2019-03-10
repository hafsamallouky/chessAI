
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

}
