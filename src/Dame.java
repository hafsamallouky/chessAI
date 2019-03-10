
public class Dame extends Piece {

	public Dame(int x, int y, int joueur) {
		super(x, y, joueur);
		Jeu.controlleurPlateau.ajouterPiece(x,y,"dame", joueur);
	}

	public void deplacement(int destX, int destY) {
		if (destX == x && destY == y){
			return;
		}
		//Deplacement en ligne droite et diagonal
		if((destX == x || destY == y) || (Math.abs(destX - x) == Math.abs(destY - y))){
			//Deplacement possible
			Jeu.controlleurPlateau.enleverPiece(x,y);
			Jeu.controlleurPlateau.ajouterPiece(destX,destY,"dame",joueur);
			Jeu.plateau.deplacerDonnee(x,y,destX,destY,this);
			this.x = destX;
			this.y = destY;
		}else{
			System.out.println("Déplacement impossible");
		}
	}

}
