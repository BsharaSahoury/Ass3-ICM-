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

public class ComitteeMemberHomeController implements Initializable {
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
	private MenuButton MBusername;
	@FXML
	private MenuButton UserNameMenu;
	public static Stage primaryStage;
	private static Employee comitteeMember;
	public static RequestsWorkedOnController RequestOnWorkCommitteMembers;

	public void start(Employee comitteeMember) {
		this.comitteeMember = comitteeMember;
		primaryStage = LoginController.primaryStage;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/Boundary/CommitteeMember-Home.fxml"));
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

	public void GoToHome(ActionEvent event) throws Exception {
		HomeController home = new HomeController();
		home.start(splitpane);
	}

	public void RequestWorkedOnAction(ActionEvent event) throws Exception {
		RequestOnWorkCommitteMembers = new RequestsWorkedOnController();
		RequestOnWorkCommitteMembers.start(splitpane, "/Boundary/RequestWorkOnCommittemember.fxml");
	}

	public void RequestSubmissionAction(ActionEvent event) throws Exception {
		RequestSubmissionController Submit = new RequestSubmissionController();
		Submit.start(splitpane,comitteeMember);
	}

	public void ProfileSettingAction(ActionEvent event) throws Exception {
		ProfileSettingController Submit = new ProfileSettingController();
		Submit.start(splitpane);
	}

	public void MyRequestsAction(ActionEvent event) throws Exception {
		MyRequestsController Submit = new MyRequestsController();
		Submit.start(splitpane, comitteeMember);
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
	public void RequestInfoAction() {
		RequestInfoController info = new RequestInfoController();
		info.start(splitpane);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//MBusername.setText(comitteeMember.getUsername());
		UserNameMenu.setText(comitteeMember.getFirstName()+comitteeMember.getLastName());
	}
	
	public void clickNotifications(ActionEvent event) throws Exception {
		NotificationsController notific=new NotificationsController();
		notific.start(splitpane,comitteeMember);
	}
}
