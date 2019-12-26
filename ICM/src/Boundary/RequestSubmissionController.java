package Boundary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RequestSubmissionController {
	private Stage primaryStage;
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		try{			
			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/RequestSubmission.fxml"));
			Scene scene = new Scene(root,900,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());			
			this.primaryStage.setScene(scene);
			this.primaryStage.setResizable(false);
			this.primaryStage.setTitle("ICM-RequestSubmission");
			this.primaryStage.show();
			this.primaryStage.setOnCloseRequest( event ->
		    {
		        System.out.println("EXIT ICM");
		        System.exit(0);	
		    });			
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	

}
