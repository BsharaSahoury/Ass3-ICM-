package Boundary;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Client.ClientConsole;
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
	@FXML
	private Button SuccessSendBtn;
	@FXML
	private Button FaildSendBtn;
	@FXML
	private TextArea FaildDetails;
	public static TestResultController TestResult;
	public static RequestsWorkedOnController RequestWorkON;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	private static SplitPane splitpane;
	// private String RadioSelect;
	// private static ClientConsole cc;
	// public static RequestInfoController Requestinfo;
	private static Request r;
	private static ClientConsole cc;

	public void start(SplitPane splitpane, Request r) {
		this.splitpane = splitpane;
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		this.r = r;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/TestResults.fxml"));
			lowerAnchorPane = loader.load();
			TestResult = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
     
	
	public void SuccessSendBtn() {
		// LocalDate start = startDate.getValue();
		// LocalDate due = dueDate.getValue();
		// LocalDate today = LocalDate.now();
		//String FaildDetails = FailureDetails.getText().trim();

	//	if (!FaildDetails.equals("")) {
			try {

				String keymessage = "send Passed test result";
				//String d = FailureDetails.getText().toString();

				Object[] message = { keymessage, r.getId(), null};

				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e) {
				Alert alertWarning = new Alert(AlertType.ERROR);
				alertWarning.setHeaderText("Error!");
				alertWarning.setContentText("IO Error");
				alertWarning.showAndWait();
			}

			/*} else {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("Please check the dates correctly");
			alertWarning.showAndWait();
		}*/

	}
	
	
	
	
	
	
	/*public void SuccessSendBtn() {
		// LocalDate start = startDate.getValue();
		// LocalDate due = dueDate.getValue();
		// LocalDate today = LocalDate.now();
		if (start != null && due != null & due.compareTo(start) >= 0 && start.compareTo(today) > 0) {
			try {
				String keymessage = "save duration";
				String d[] = { startDate.getValue().toString(), dueDate.getValue().toString() };

				Object[] message = { keymessage, r.getId(), d };

				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("Please check the dates correctly");
			alertWarning.showAndWait();
		}

	}*/

	public void FaildSendBtn() {
		// LocalDate start = startDate.getValue();
		// LocalDate due = dueDate.getValue();
		// LocalDate today = LocalDate.now();
		String FaildDetails = FailureDetails.getText().trim();

		if (!FaildDetails.equals("")) {
			try {

				String keymessage = "send Failure test result";
				String d = FailureDetails.getText().toString();

				Object[] message = { keymessage, r.getId(), d };

				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e) {
				Alert alertWarning = new Alert(AlertType.ERROR);
				alertWarning.setHeaderText("Error!");
				alertWarning.setContentText("Please type failure details");
				alertWarning.showAndWait();
			}

		} else {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("Please check the dates correctly");
			alertWarning.showAndWait();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	/*
	 * public void start(SplitPane splitpane, Request s) { primaryStage =
	 * LoginController.primaryStage; this.cc = LoginController.cc; this.splitpane =
	 * splitpane; String keymessage; try { FXMLLoader loader = new
	 * FXMLLoader(getClass().getResource("/Boundary/Testresults.fxml"));
	 * lowerAnchorPane = loader.load(); Requestinfo = loader.getController();
	 * splitpane.getItems().set(1, lowerAnchorPane); keymessage = "Request Info";
	 * int id = s.getId(); Object[] message = { keymessage, id };
	 * LoginController.cc.getClient().sendToServer(message);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */

	/*
	 * public void start(SplitPane splitpane) { primaryStage =
	 * LoginController.primaryStage; this.splitpane = splitpane; try { FXMLLoader
	 * loader = new
	 * FXMLLoader(getClass().getResource("/Boundary/Testresults.fxml"));
	 * lowerAnchorPane = loader.load();
	 * 
	 * splitpane.getItems().set(1, lowerAnchorPane);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */

	public void radioselectFaild(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/TestResultFaild.fxml"));
			lowerAnchorPane = loader.load();

			splitpane.getItems().set(1, lowerAnchorPane);
			// RadioSelect ="Failure";

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void radioselectSuccess(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/TestResultPass.fxml"));
			lowerAnchorPane = loader.load();

			splitpane.getItems().set(1, lowerAnchorPane);
			// RadioSelect = "Success";

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void SendBtn(ActionEvent e) {
		// System.out.println("xdxdxd");
		// if (RadioSelect.equals(Failure)) {
		// System.out.println("xd");
		boolean NoFailureDetails = FailureDetails.getText().equals("");
		if (NoFailureDetails) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Send Test Result Failure");
			alert.setHeaderText("No Failure Details");
			alert.setContentText("please fill Failure Details field");
			alert.showAndWait();
			return;
		}
		// }

		/*
		 * if (RadioSelect.equals(Success)) {
		 * 
		 * }
		 */
	}

	public void SendBtnSuccess(ActionEvent e) {
		// System.out.println("xdxdxd");
		// if (RadioSelect.equals(Failure)) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Send Test Result Success");
		alert.setHeaderText("Send Success");
		alert.setContentText("Test passed , the request will move to next phase -__-");
		alert.showAndWait();
		return;

		// }

		/*
		 * if (RadioSelect.equals(Success)) {
		 * 
		 * }
		 */
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
