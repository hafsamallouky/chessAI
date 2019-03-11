import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControlleurPlateau implements Initializable {
	@FXML
	Pane root;

	public Rectangle[][] tabPiece;
	public Rectangle[][] tabChemin;
	public Piece selection;

	public void initialize(URL location, ResourceBundle resources) {
		BackgroundImage myBI= new BackgroundImage(new Image("damier.png",512,512,false,true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		root.setBackground(new Background(myBI));

		selection = null;

		root.setOnMouseClicked(event -> {
			click(event.getX(), event.getY());
		});

		tabPiece = new Rectangle[8][8];
		tabChemin = new Rectangle[8][8];
	}

	//Ajouter le visuel d'une piece
	public void ajouterPiece(int x, int y, String type, int color){
		Image img;
		String urlImage;
		if(color == 0){
			urlImage = "b" + type + ".png";
		}else{
			urlImage = "n" + type + ".png";
		}
		img = new Image(urlImage);
		tabPiece[x][y] = new Rectangle(x*64, y*64,64,64);
		tabPiece[x][y].setFill(new ImagePattern(img));
		root.getChildren().add(tabPiece[x][y]);

	}

	//Afficher les deplacements possibles des pieces
	public void ajouterChemin(ArrayList<Pair<Integer, Integer>> liste){
		int x;
		int y;
		for(int i = 0; i < liste.size(); i++){
			x = liste.get(i).getKey();
			y = liste.get(i).getValue();
			tabChemin[x][y] = new Rectangle(x*64, y*64, 64,64);
			tabChemin[x][y].setFill(new ImagePattern(new Image("jaune.png")));
			root.getChildren().add(tabChemin[x][y]);
			System.out.println(liste.get(i));
		}
	}

	public void enleverPiece(int x,int y){
		root.getChildren().remove(tabPiece[x][y]);
	}

	public void click(double x, double y){
		if(selection == null){
			selection = Jeu.plateau.selectionPiece((int)(x/64), (int)(y/64));
			ajouterChemin(Plateau.liste);
		}else{
			selection.deplacement((int)(x/64), (int)(y/64));
			selection = null;
		}
	}
}
