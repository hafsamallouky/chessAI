
public class Roi extends Piece{

	public Roi(int x, int y, int joueur) {
		super(x, y, joueur);
		Jeu.controlleurPlateau.ajouterPiece(x,y,"roi", joueur);
	}

	public void deplacement(int destX, int destY) {
		if (destX == x && destY == y){
			return;
		}
		//Deplacement de 1 dans toutes les directions
		if(Math.abs(destX  - x) <= 1 && Math.abs(destY - y) <= 1){
			//Deplacement possible
			Jeu.controlleurPlateau.enleverPiece(x,y);
			Jeu.controlleurPlateau.ajouterPiece(destX,destY,"roi",joueur);
			Jeu.plateau.deplacerDonnee(x,y,destX,destY,this);
			this.x = destX;
			this.y = destY;
		}else{
			System.out.println("Déplacement impossible");
		}
	}

}
