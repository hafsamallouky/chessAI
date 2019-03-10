
public class Tour extends Piece{

	public Tour(int x, int y, int joueur) {
		super(x,y,joueur);
		Jeu.controlleurPlateau.ajouterPiece(x,y,"tour", joueur);
	}


	public void deplacement(int destX, int destY) {
		if (destX == x && destY == y){
			return;
		}
		//Deplacement en ligne droite
		if(destX == x || destY == y){
			//Deplacement possible
			Jeu.controlleurPlateau.enleverPiece(x,y);
			Jeu.controlleurPlateau.ajouterPiece(destX,destY,"tour",joueur);
			Jeu.plateau.deplacerDonnee(x,y,destX,destY,this);
			this.x = destX;
			this.y = destY;
		}else{
			System.out.println("Déplacement impossible");
		}
	}
	
}
