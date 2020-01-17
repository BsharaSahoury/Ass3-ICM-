package Boundary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Client.Func;
import Client.MainForClient;
import Entity.Employee;
import javafx.application.Application;
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

public class AdministratorHomeController implements Initializable {
	@FXML
	Button allEmployees;
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
	private MenuButton UserNameMenu;
	@FXML
	private SplitPane splitpane;
	@FXML
	private AnchorPane lowerAnchorPane;
	public static Stage primaryStage;
	private static Employee Administrator;
	public static MyRequestsController MyRequests;
	public static AllRequestsController AllRequests;
	public static ProfileSettingController ProfileSetting;

	public void start(Employee Administrator) {
		this.Administrator = Administrator;
		primaryStage = LoginController.primaryStage;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
// TODO Auto-generated method stub
				try {
					System.out.println("Hi baby");
					Parent root = FXMLLoader.load(getClass().getResource("/Boundary/newAdHome.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setResizable(false);
					primaryStage.setTitle("ICM-Home");
					primaryStage.show();
					primaryStage.setOnCloseRequest(event -> {
						System.out.println("EXIT ICM");
						LogOutController logOut = new LogOutController();
						logOut.exit(primaryStage, Administrator);
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

	public void AllEmployeesAction(ActionEvent e) {

	}

	private void runLater(Func f) {
		f.call();
		Platform.runLater(() -> {
			try {
				Thread.sleep(10);
				f.call();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public void GoToHome(ActionEvent event) throws Exception {
		HomeController home = new HomeController();
		runLater(() -> {
			home.start(splitpane);
		});
	}

	public void AllRequestsAction(ActionEvent event) throws Exception {
		AllRequests = new AllRequestsController();
		runLater(() -> {
			AllRequests.start(splitpane, "/Boundary/allRequests-for-Admin.fxml", "Administrator");
		});
	}

	public void RequestSubmissionAction(ActionEvent event) throws Exception {
		RequestSubmissionController Submit = new RequestSubmissionController();
		runLater(() -> {
			Submit.start(splitpane, Administrator);
		});
	}

	public void ProfileSettingAction(ActionEvent event) throws Exception {
		ProfileSetting = new ProfileSettingController();
		runLater(() -> {
			ProfileSetting.start(splitpane, Administrator, "Administrator");
		});

	}

	public void MyRequestsAction(ActionEvent event) throws Exception {
		MyRequests = new MyRequestsController();
		runLater(() -> {
			MyRequests.start(splitpane, Administrator, "Administrator");
		});
	}

	public void AboutICMAction(ActionEvent event) throws Exception {
		AboutICMController about = new AboutICMController();
		runLater(() -> {
			about.start(splitpane);
		});
	}

	public void LogOutAction(ActionEvent event) throws Exception {
		LogOutController logOut = new LogOutController();
		primaryStage.close();
		logOut.start(primaryStage, Administrator);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UserNameMenu.setText(Administrator.getFirstName() + " " + Administrator.getLastName());
	}

	public void clickNotifications(ActionEvent event) throws Exception {
		NotificationsController notific = new NotificationsController();
		runLater(() -> {
			notific.start(splitpane, Administrator);
		});
	}

	public static Employee getAdministrator() {
		return Administrator;
	}

}