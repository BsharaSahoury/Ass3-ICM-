package Boundary;

import java.io.IOException;

import Client.ClientConsole;
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

public class AdministratorHomeController {
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
	private SplitPane splitpane;
	@FXML
	private AnchorPane lowerAnchorPane;
	public static Stage primaryStage;
	private Employee Administrator;
	
	public static RequestsWorkedOnController RequestsInWork;
	
	public void start(Employee Administrator) {
		this.Administrator = Administrator;
		primaryStage = LoginController.primaryStage;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/NewBoundary/Administrator-Home.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setResizable(false);
					primaryStage.setTitle("ICM-Home");
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

	public void GoToHome(ActionEvent event) throws Exception {
		HomeController home = new HomeController();
		home.start(splitpane);
	}

	public void RequestWorkedOnAction(ActionEvent event) throws Exception {

		RequestsInWork = new RequestsWorkedOnController();
		RequestsInWork.start(splitpane, "/Boundary/MyRequests.fxml");

	}

	public void RequestSubmissionAction(ActionEvent event) throws Exception {
		RequestSubmissionController Submit = new RequestSubmissionController();
		Submit.start(splitpane);
	}

	public void ProfileSettingAction(ActionEvent event) throws Exception {
		ProfileSettingController Submit = new ProfileSettingController();
		Submit.start(splitpane);
	}

	public void MyRequestsAction(ActionEvent event) throws Exception {
		MyRequestsController Submit = new MyRequestsController();
		Submit.start(splitpane);
	}

//@FXML
	public void AboutICMAction(ActionEvent event) throws Exception {
		AboutICMController about = new AboutICMController();
		about.start(splitpane);
	}

}