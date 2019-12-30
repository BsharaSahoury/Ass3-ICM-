package Boundary;

import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProfileSettingController{
public static Stage primaryStage;
private AnchorPane lowerAnchorPane;
public void start(SplitPane splitpane) {
	primaryStage=LoginController.primaryStage;
	try{		
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Profile setting.fxml"));
		lowerAnchorPane = loader.load();
		splitpane.getItems().set(1, lowerAnchorPane);		
	} catch(Exception e) {
		e.printStackTrace();
	}	
	}

public void ApplyAndSaveAction(ActionEvent event) {
	
}
public void BrowsAction(ActionEvent event) {
	
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

}

public void AboutICMAction(ActionEvent event) throws Exception {
	AboutICMController about=new AboutICMController();
	about.start();
}
}
