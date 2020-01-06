package Boundary;

import java.io.IOException;

import Entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProfileSettingController{
	@FXML
	private CheckBox allowrecSMS;
	@FXML
	private CheckBox allowrecGmail;
	@FXML
	private TextField textfileddestination;
	@FXML
	private Button browsbtn;
	@FXML
	private Button applyandsavebtn;
	@FXML
	private TextField textfield;
public static Stage primaryStage;
private AnchorPane lowerAnchorPane;
private static User user;
public void start(SplitPane splitpane,User user) {
	this.user=user;
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
	System.out.println("sss");
}
public void BrowsAction(ActionEvent event) {
	System.out.println("sss");
}
}
