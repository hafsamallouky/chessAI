public class Plateau {
	private static final int COTE = 8;
	private Case[][] tabCase;
	
	public Plateau() {
		tabCase = new Case[COTE][COTE];
		for(int i = 0; i < COTE; i++) {
			for(int j=0; j < COTE; j++) {
				tabCase[i][j] = new Case(i, j, initPlateau(i,j));
				
			}
		}
	}
	
	public Piece initPlateau(int x, int y) {
		Piece piece = null;
		int coordonnees = x * COTE + y;
		switch(coordonnees){
			case 0 : return new Tour(x,y,0); 
			case 1 : return new Cavalier(x,y,0);
			case 2 : return new Fou(x,y,0);
			case 3 : return new Dame(x,y,0);
			case 4 : return new Roi(x,y,0);
			case 5 : return new Fou(x,y,0);
			case 6 : return new Cavalier(x,y,0);
			case 7 : return new Tour(x,y,0);
			case 8 : case 9 : case 10 : case 11 : case 12 : case 13 : case 14 : case 15 : 
				return new Pion(x,y,0);
			case 48 : case 49 : case 50 : case 51 : case 52 : case 53 : case 54 : case 55 :
				return new Pion(x,y,1);
			case 56 : return new Tour(x,y,1);
			case 57 : return new Cavalier(x,y,1);
			case 58 : return new Fou(x,y,1);
			case 59 : return new Roi(x,y,1);
			case 60 : return new Dame(x,y,1);
			case 61 : return new Fou(x,y,1);
			case 62 : return new Cavalier(x,y,1);
			case 63 : return new Tour(x,y,1);
				
		}
		
		
		
		return piece;
	}
}
