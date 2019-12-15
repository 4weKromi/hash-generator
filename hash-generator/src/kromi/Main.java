package kromi;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = new GridPane();
			// Load the FXML File
			root = FXMLLoader.load(getClass().getResource("View.fxml"));
			root.setStyle("-fx-padding: 20;");
			Scene scene = new Scene(root, 350, 400); //width, height
			primaryStage.setTitle("HashGenerator v0.1");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}

