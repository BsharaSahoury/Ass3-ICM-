package Boundary;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeController {
	private Stage primaryStage;
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		try{			
			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Home.fxml"));
			Scene scene = new Scene(root,900,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());			
			this.primaryStage.setScene(scene);
			this.primaryStage.setResizable(false);
			this.primaryStage.setTitle("ICM-Home");
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
	
public Stage getPrimaryStage() {
		return primaryStage;
	}

public void GoToHome(ActionEvent event) throws Exception {
	this.start(this.primaryStage);
}
/*
public void RequestWorkedOnAction(ActionEvent event) throws Exception {
	RequestWorkedOnController 
}
*/
public void RequestSubmissionAction(ActionEvent event) throws Exception {
	RequestSubmissionController Submit=new RequestSubmissionController();
	Submit.start(this.primaryStage);
}

public void ProfileSettingAction(ActionEvent event) throws Exception {
	ProfileSettingController Submit=new ProfileSettingController();
	Submit.start(this.primaryStage);
}

public void AboutICMAction(ActionEvent event) throws Exception {
	AboutICMController about=new AboutICMController();
	about.start(this.primaryStage);
}

}
