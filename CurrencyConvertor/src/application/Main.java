package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage window) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("SceneBuilder.fxml"));
			Scene scene = new Scene(root);
			
			window.setScene(scene);
			window.show();
			window.setOnCloseRequest(e -> {
				//System.out.println("Closed suscesfully");
			});
			
			window.setTitle("Currency Converter");
			
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
