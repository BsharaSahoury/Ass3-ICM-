package messages;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
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
	public static approveDuratinController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;
	
	public void start(SplitPane splitpane,String content, String start, String due) {
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
	}
}
