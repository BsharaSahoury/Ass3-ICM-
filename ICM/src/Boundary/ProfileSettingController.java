package Boundary;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProfileSettingController extends HomeController{
private Stage primaryStage;

public void start(Stage primaryStage) {
	this.primaryStage=primaryStage;
	try{			
		Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Profile.fxml"));
		Scene scene = new Scene(root,900,600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());			
		this.primaryStage.setScene(scene);
		this.primaryStage.setResizable(false);
		this.primaryStage.setTitle("ICM-Profile");
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
public void GoToHomeFromProfile(ActionEvent event) throws Exception {
	GoToHome(event);
}
/*
public void RequestWorkedOnAction(ActionEvent event) throws Exception {
	RequestWorkedOnController 
}
*/
public void RequestSubmissionActionFromProfile(ActionEvent event) throws Exception {
	this.start(primaryStage);
}

public void ProfileSettingActionFromProfile(ActionEvent event) throws Exception {
	ProfileSettingAction(event);
}

public void AboutICMActionFromProfile(ActionEvent event) throws Exception {
	AboutICMAction(event);
}
}
