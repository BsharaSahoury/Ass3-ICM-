package Boundary;

import java.io.IOException;

import Client.MainForClient;
import Entity.Employee;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InspectorHomeController {
	@FXML
	private Button Homebtn;
	@FXML
	private Button RequestWorkedOnbtn;
	@FXML
	private Button RequestSubmissionbtn;
	@FXML
	private Button ProfileSettingbtn;
	@FXML
	private Button AboutICMbtn;
	@FXML
	private ComboBox Usercombobtn;	
	@FXML
	private SplitPane splitpane ;
	@FXML
    private AnchorPane lowerAnchorPane;
	public static Stage primaryStage;
	private Employee inspector;
	public void start(Employee inspector) {
		this.inspector=inspector;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				primaryStage=new Stage();
				try{
					Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Inspector-Home.fxml"));	
					Scene scene = new Scene(root);		
					primaryStage.setScene(scene);
					primaryStage.setResizable(false);
					primaryStage.setTitle("ICM-Home");			
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
			
		});
	}
	
public Stage getPrimaryStage() {
		return primaryStage;
	}

public void GoToHome(ActionEvent event) throws Exception {
	this.start(inspector);
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
//@FXML
public void AboutICMAction(ActionEvent event) throws Exception {
	/*
	AboutICMController about=new AboutICMController();
	about.start(splitpane);*/
	
    try {
    	//Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Chairman-Home.fxml"));	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Chairman-Home.fxml"));
    	//System.out.println("sssss");
		lowerAnchorPane = loader.load();
		//lowerAnchorPane.getChildren().setAll(FXMLLoader.load("/Boundary/Chairman-Home.fxml"));
		splitpane.getItems().set(1, lowerAnchorPane);
		//content.getChildren().setAll(FXMLLoader.load("vista2.fxml"));
	} catch (IOException e) {		
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		}   

}