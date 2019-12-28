package Boundary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AboutICMController {
	public static Stage primaryStage;
	public void start() {
		primaryStage=HomeController.primaryStage;
		try{		
			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/AboutICM_Final.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("ICM-About");
			primaryStage.show();
			primaryStage.setOnCloseRequest( event ->
		    {
		        System.out.println("EXIT ICM");
		        System.exit(0);	
		    });			
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}


}
