package Boundary;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RequestSubmissionController {
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public void start(SplitPane splitpane) {
		primaryStage=LoginController.primaryStage;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Request Submission.fxml"));
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);		
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
}

public void ProfileSettingAction(ActionEvent event) throws Exception {
	ProfileSettingController Submit=new ProfileSettingController();
	Submit.start();
}

public void AboutICMAction(ActionEvent event) throws Exception {
	AboutICMController about=new AboutICMController();
	about.start();
}	
	

}
