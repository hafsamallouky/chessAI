import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Jeu extends Application{
	public static Plateau plateau;
	public static ControlleurPlateau controlleurPlateau;

	public void start(Stage primaryStage) throws Exception{
		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent root = fxmlLoader.load(getClass().getResource("plateauVue.fxml").openStream());
		controlleurPlateau = (ControlleurPlateau) fxmlLoader.getController();
		plateau = new Plateau(null);
		plateau.appartenancePiece();
		plateau.origineMinMax(plateau, 3);

		primaryStage.setTitle("ChessAI");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
