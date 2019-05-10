import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControlleurPlateau implements Initializable {
	@FXML
	Pane rootBoard;
	@FXML
	Button btnSortie;
	@FXML
	Label info;
	@FXML
	Label dernierDeplacement;
	@FXML
	Label heuristique;
	@FXML
	ProgressBar barreDroite;
	@FXML
	ProgressBar barreGauche;

	public Rectangle[][] tabPiece;
	public Rectangle[][] tabChemin;
	public Piece selection;

	public void initialize(URL location, ResourceBundle resources) {
		BackgroundImage myBI= new BackgroundImage(new Image("damier.png",512,512,false,true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		rootBoard.setBackground(new Background(myBI));

		selection = null;

		rootBoard.setOnMouseClicked(event -> {
			if(event.getX() < 512 && event.getY() < 512  && event.getX() > 0 && event.getY() > 0) {
				click(event.getX(), event.getY());
			}
		});

		btnSortie.setOnAction(event -> {
			Stage stage = (Stage) btnSortie.getScene().getWindow();
			stage.close();
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
		rootBoard.getChildren().add(tabPiece[x][y]);

	}

	//Afficher les deplacements possibles des pieces
	public void ajouterChemin(ArrayList<Pair<Integer, Integer>> liste){
		int x;
		int y;
		for(int i = 0; i < liste.size(); i++){
			x = liste.get(i).getKey();
			y = liste.get(i).getValue();
			tabChemin[x][y] = new Rectangle(x*64, y*64, 64,64);
			if(Jeu.plateau.tabCase[x][y].getPiece() != null) {
				tabChemin[x][y].setFill(new ImagePattern(new Image("rouge.png")));
			}else{
				tabChemin[x][y].setFill(new ImagePattern(new Image("jaune.png")));
			}
			rootBoard.getChildren().add(tabChemin[x][y]);
		}
	}

	public void enleverPiece(int x,int y){
		rootBoard.getChildren().remove(tabPiece[x][y]);
	}

	//Retire l'affichage du chemin en jaune
	public void retirerChemin(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(tabChemin[i][j] != null){
					rootBoard.getChildren().remove(tabChemin[i][j]);
				}
			}
		}
	}


	//Selectionne une piece ou la deplace si une piece est deja selectionnée
	public void click(double x, double y){
		if(selection == null){
			selection = Jeu.plateau.selectionPiece((int)(x/64), (int)(y/64));
			if(selection != null) {
				ajouterChemin(Jeu.plateau.liste);
			}
		}else{
			selection.deplacement((int)(x/64), (int)(y/64));
			retirerChemin();
			Jeu.plateau.liste = null;
			selection = null;
		}
	}

	public void changerLabelInfo(String str){
		info.setText(str);
	}

	public void changerLabelDernierDeplacement(int departY, int departX, int arriveY, int arriveX, String type){
		dernierDeplacement.setText(type + " de " + departX + "/" + departY + " vers " + arriveX + "/" + arriveY);
	}

	public void changerLabelHeuristique(Double h){
		Double tmp = h;
		heuristique.setText("heuristique : " + tmp.intValue());
		if(tmp < 0){
			if(tmp < -100){
				tmp = -100.0;
			}
			barreGauche.setProgress(Math.abs(tmp)/100);
		}else{
			if(tmp > 100){
				tmp = 100.0;
			}
			barreDroite.setProgress(tmp/100);
		}
	}

}
