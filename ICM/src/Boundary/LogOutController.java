package Boundary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LogOutController {
	private Stage primaryStage;

	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("ICM-Login");
			primaryStage.show();
			primaryStage.setOnCloseRequest(event -> {
				System.out.println("EXIT ICM");
				System.exit(0);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
