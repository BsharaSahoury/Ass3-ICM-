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

public class LecturerHomeController implements Initializable {

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
	@FXML
	private MenuButton UserNameMenu;
	public static Stage primaryStage;
	private static Employee lecturer;
	public void start(Employee lecturer) {
		this.lecturer=lecturer;
		primaryStage=LoginController.primaryStage;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
		try{
			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Lecturer-Home.fxml"));			
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
	try {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Home.fxml"));
		lowerAnchorPane = loader.load();
		splitpane.getItems().set(1, lowerAnchorPane);
	} catch (IOException e) {		
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void RequestSubmissionAction(ActionEvent event) throws Exception {
	RequestSubmissionController Submit=new RequestSubmissionController();
	Submit.start(splitpane,lecturer);
}

public void ProfileSettingAction(ActionEvent event) throws Exception {
	ProfileSettingController Submit=new ProfileSettingController();
	Submit.start(splitpane);
}

public void MyRequestsAction(ActionEvent event) throws Exception {
	MyRequestsController Submit = new MyRequestsController();
	Submit.start(splitpane, lecturer);
}

public void AboutICMAction(ActionEvent event) throws Exception {
	AboutICMController about=new AboutICMController();
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
	UserNameMenu.setText(lecturer.getFirstName()+lecturer.getLastName());
}

}
