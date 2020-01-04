package Boundary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.Employee;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TesterHomeController implements Initializable {
	@FXML
	private Button notifications;
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
	private SplitPane splitpane;
	@FXML
	private AnchorPane lowerAnchorPane;
	@FXML
	private MenuButton UserNameMenu;
	public static Stage primaryStage;
	private static Employee Tester;
	public static RequestsWorkedOnController RequestOnWorkCommitteTester;

	public void start(Employee Tester) {
		this.Tester = Tester;
		primaryStage = LoginController.primaryStage;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Tester-Home.fxml"));
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
		RequestOnWorkCommitteTester = new RequestsWorkedOnController();
		RequestOnWorkCommitteTester.start(splitpane, "/Boundary/RequestsWorkOnTester.fxml");
	}

	public void RequestSubmissionAction(ActionEvent event) throws Exception {
		RequestSubmissionController Submit = new RequestSubmissionController();
		Submit.start(splitpane,Tester);
	}

	public void ProfileSettingAction(ActionEvent event) throws Exception {
		ProfileSettingController Submit = new ProfileSettingController();
		Submit.start(splitpane);
	}

	public void MyRequestsAction(ActionEvent event) throws Exception {
		MyRequestsController Submit = new MyRequestsController();
		Submit.start(splitpane, Tester);
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		UserNameMenu.setText(Tester.getFirstName()+Tester.getLastName());
	}
	public void clickNotifications(ActionEvent event) throws Exception {
		NotificationsController notific=new NotificationsController();
		notific.start(splitpane,Tester);
	}

}
