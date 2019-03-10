
public class Fou extends Piece{

	public Fou(int x, int y, int joueur) {
		super(x, y, joueur);
		Jeu.controlleurPlateau.ajouterPiece(x,y,"fou", joueur);
	}

	public void deplacement(int destX, int destY) {
		if (destX == x && destY == y){
			return;
		}
		if(Math.abs(destX - x) == Math.abs(destY - y)){
			//Deplacement en diagonal
			Jeu.controlleurPlateau.enleverPiece(x,y);
			Jeu.controlleurPlateau.ajouterPiece(destX,destY,"fou",joueur);
			Jeu.plateau.deplacerDonnee(x,y,destX,destY,this);
			this.x = destX;
			this.y = destY;
		}else{
			System.out.println("Déplacement impossible");
		}
	}

}
