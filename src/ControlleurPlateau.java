import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class ControlleurPlateau implements Initializable {
	@FXML
	Pane root;

	public Rectangle[][] tabPiece;
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
	}

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

	public void enleverPiece(int x,int y){
		root.getChildren().remove(tabPiece[x][y]);
	}

	public void click(double x, double y){
		System.out.println((int)(x/64) + " / " + (int)(y/64));
		if(selection == null){
			selection = Jeu.plateau.selectionPiece((int)(x/64), (int)(y/64));
		}else{
			selection.deplacement((int)(x/64), (int)(y/64));
			selection = null;
		}
	}
}
