
public class Cavalier extends Piece {

	public Cavalier(int x, int y, int joueur) {
		super(x, y, joueur);
		Jeu.controlleurPlateau.ajouterPiece(x,y,"cavalier", joueur);
	}

	public void deplacement(int destX, int destY) {
		if (destX == x && destY == y){
			return;
		}
		//Deplacement cancer :(
		if((Math.abs(destX - x) == 2 && Math.abs(destY - y) == 1) ||
			(Math.abs(destX - x) == 1 && Math.abs(destY - y) == 2)	){
			//Deplacement possible
			Jeu.controlleurPlateau.enleverPiece(x,y);
			Jeu.controlleurPlateau.ajouterPiece(destX,destY,"cavalier",joueur);
			Jeu.plateau.deplacerDonnee(x,y,destX,destY,this);
			this.x = destX;
			this.y = destY;
		}else{
			System.out.println("Déplacement impossible");
		}
	}

}
