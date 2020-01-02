package Boundary;

import java.awt.Label;
import java.io.IOException;
import java.util.ArrayList;

import Client.ClientConsole;
import Client.MainForClient;
import Entity.Employee;
import Entity.Request;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
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
	public SplitPane splitpane;
	@FXML
	private AnchorPane lowerAnchorPane;
	public static Stage primaryStage;
	private static Employee inspector;
	private MenuItem btlogOut;
	public static AllRequestsController AllRequests;
	public static MyRequestsController MyRequests;
    private ArrayList<Request> arr;
    public static InspectorHomeController s;
	public void start(Employee inspector) {
		this.inspector = inspector;
		s=this;
		primaryStage = LoginController.primaryStage;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Inspector-Home.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setResizable(false);
					primaryStage.setTitle("ICM");
					primaryStage.show();
					primaryStage.setOnCloseRequest(event -> {
						System.out.println("EXIT ICM");
						System.exit(0);
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setarr(ArrayList<Request> arr){
		this.arr=arr;
	}
	public void GoToHome(ActionEvent event) throws Exception {
		HomeController home = new HomeController();
		home.start(splitpane);
	}

	public void RequestWorkedOnAction(ActionEvent event) throws Exception {
		AllRequests = new AllRequestsController();
		AllRequests.start(splitpane, "/Boundary/allRequests.fxml");

	}

	public void RequestSubmissionAction(ActionEvent event) throws Exception {
		RequestSubmissionController Submit = new RequestSubmissionController();
		Submit.start(splitpane,inspector);
		Submit.start(splitpane,inspector);
	}

	public void ProfileSettingAction(ActionEvent event) throws Exception {
		ProfileSettingController Submit = new ProfileSettingController();
		Submit.start(splitpane);
	}

	public void MyRequestsAction(ActionEvent event) throws Exception {
		MyRequests = new MyRequestsController();
		MyRequests.start(splitpane,inspector);
	}
	public void AboutICMAction(ActionEvent event) throws Exception {
		AboutICMController about = new AboutICMController();
		about.start(splitpane);
	}

	public void LogOutAction(ActionEvent event) throws Exception {
		LogOutController logOut = new LogOutController();
		primaryStage.close();
		logOut.start(primaryStage);
	}
}