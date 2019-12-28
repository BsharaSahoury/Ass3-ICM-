package Boundary;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RequestSubmissionController {
	public static Stage primaryStage;
	public void start() {
		primaryStage=LoginController.primaryStage;
		try{	
			
			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Request Submission.fxml"));
			//System.out.print("sss");
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("ICM-RequestSubmission");
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
	
public void GoToHome(ActionEvent event) throws Exception {
	HomeController ss=new HomeController();
	ss.start();
}
	
	

}
