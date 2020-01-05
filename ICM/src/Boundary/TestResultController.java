package Boundary;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.MyFile;
import Entity.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestResultController implements Initializable {
	@FXML
	private RadioButton TestFaildRadioButton;
	@FXML
	private RadioButton TestSuccessRadioButton;
	@FXML
	private TextArea FailureDetails;
	@FXML
	Button SendBtn;
	public static RequestsWorkedOnController RequestWorkON;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	private static SplitPane splitpane;
	//private String RadioSelect;
	
	public void start(SplitPane splitpane) {
		primaryStage = LoginController.primaryStage;
		this.splitpane = splitpane;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Testresults.fxml"));
			lowerAnchorPane = loader.load();

			splitpane.getItems().set(1, lowerAnchorPane);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void radioselectFaild(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/TestResultFaild.fxml"));
			lowerAnchorPane = loader.load();

			splitpane.getItems().set(1, lowerAnchorPane);
	//		RadioSelect ="Failure";

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void radioselectSuccess(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/TestResultPass.fxml"));
			lowerAnchorPane = loader.load();

			splitpane.getItems().set(1, lowerAnchorPane);
	//		RadioSelect = "Success";

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void SendBtn(ActionEvent e) {
	//	System.out.println("xdxdxd");
	//	if (RadioSelect.equals(Failure)) {
		//	System.out.println("xd");
			boolean NoFailureDetails = FailureDetails.getText().equals("");
			if (NoFailureDetails) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Send Test Result Failure");
				alert.setHeaderText("No Failure Details");
				alert.setContentText("please fill Failure Details field");
				alert.showAndWait();
				return;
			}
		//}

/*		if (RadioSelect.equals(Success)) {

		}
*/
	}
	
	
	public void SendBtnSuccess(ActionEvent e) {
		//	System.out.println("xdxdxd");
		//	if (RadioSelect.equals(Failure)) {
			
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Send Test Result Success");
					alert.setHeaderText("Send Success");
					alert.setContentText("Test passed , the request will move to next phase -__-");
					alert.showAndWait();
					return;
			
			//}

	/*		if (RadioSelect.equals(Success)) {

			}
	*/
		}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
