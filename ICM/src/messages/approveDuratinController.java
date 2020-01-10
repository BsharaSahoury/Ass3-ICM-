package messages;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
import Boundary.RequestsWorkedOnController;
import Entity.Phase;
import Entity.RequestPhase;
import Entity.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class approveDuratinController implements Initializable {
	@FXML
	Label label;
	@FXML
	DatePicker start;
	@FXML
	DatePicker due;
	@FXML
	Button approve;
	@FXML
	private Label note;
	@FXML
	public static approveDuratinController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;
	public static int id;
	public void start(SplitPane splitpane,String content, String start, String due, int id) {
		this.id=id;
		primaryStage=LoginController.primaryStage;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/approveDuration.fxml"));
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
			ctrl.label.setVisible(false);
			ctrl.label.setText(content);
			ctrl.label.setVisible(true);
			LocalDate startDate = LocalDate.parse(start);
			LocalDate dueDate = LocalDate.parse(due);
			ctrl.start.setValue(startDate);
			ctrl.due.setValue(dueDate);
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	public void approveAction(){
		LocalDate startDate = start.getValue();
		LocalDate dueDate = due.getValue();
		LocalDate today = LocalDate.now();
		if (startDate != null && dueDate != null & dueDate.compareTo(startDate) >= 0 && startDate.compareTo(today) >= 0) {
			String keymessage ="ispector duration";
			String d[] = { startDate.toString(), dueDate.toString() };
			Object[] message = { keymessage, id, d,Phase.evaluation,State.work};

			try {
				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			approve.setDisable(true);

		}
		 else {
				Alert alertWarning = new Alert(AlertType.WARNING);
				alertWarning.setHeaderText("Warning!");
				alertWarning.setContentText("Please check the dates correctly");
				alertWarning.showAndWait();
		 }
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String keymessage ="checkAprproveDuration";
		Object[] message = { keymessage, id, Phase.evaluation.toString()};
		try {
			LoginController.cc.getClient().sendToServer(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void checkApprove(RequestPhase rp) {
		if(rp.getState().equals(State.work))
		{
			note.setDisable(false);
			note.setText("you already approved duration");
			note.setDisable(true);
			approve.setDisable(true);
		}
	}
}
