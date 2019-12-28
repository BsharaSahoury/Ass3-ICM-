package Boundary;

import javafx.event.ActionEvent;
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
	
	public void GoToHome(ActionEvent event) throws Exception {
		HomeController backhome=new HomeController();
		backhome.start();
	}
	public void RequestWorkedOnAction(ActionEvent event) throws Exception {
		//RequestWorkedOnController 
	}

	public void RequestSubmissionAction(ActionEvent event) throws Exception {
		RequestSubmissionController Submit=new RequestSubmissionController();
		Submit.start();
	}

	public void ProfileSettingAction(ActionEvent event) throws Exception {
		ProfileSettingController Submit=new ProfileSettingController();
		Submit.start();
	}

	public void AboutICMAction(ActionEvent event) throws Exception {
	}
}
