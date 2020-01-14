package Boundary;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Phase;
import Entity.Request;
import Entity.RequestPhase;
import Entity.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SetDurationController implements Initializable {
	public static SetDurationController duratin;
	public static Stage primaryStage;
	private static ClientConsole cc;
	private AnchorPane lowerAnchorPane;
	@FXML
	private static SplitPane splitpane;
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker dueDate;
	@FXML
	private Button save;

	private static Request r;
	private static Phase phase;

	public void start(SplitPane splitpane, Request r,String path, Phase phase) {
		this.splitpane = splitpane;
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		this.r = r;
		this.phase=phase;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();
			duratin = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveBtn() {
		LocalDate start = startDate.getValue();
		LocalDate due = dueDate.getValue();
		LocalDate today = LocalDate.now();
		if (start != null && due != null & due.compareTo(start) >= 0 && start.compareTo(today) >= 0) {
				Alert alertWarning = new Alert(AlertType.CONFIRMATION);
		        alertWarning.setTitle("Warning Alert Title");
		        alertWarning.setHeaderText("confirm!");
		        alertWarning.setContentText("Are you sure about the dates you intered, you can't change it later!");
		        Optional<ButtonType> result=alertWarning.showAndWait();
		        ButtonType button=result.orElse(ButtonType.CANCEL);
		        if(button==ButtonType.OK) {
		        	try {
						String keymessage = "save duration";
						String d[] = { startDate.getValue().toString(), dueDate.getValue().toString() };
						Object[] message = { keymessage, r.getId(), d, phase};

						LoginController.cc.getClient().sendToServer(message);
			        	save.setDisable(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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

		if(RequestsWorkedOnController.getRP().getStartDate()!=null && RequestsWorkedOnController.getRP().getDueDate()!=null)
 {
			save.setDisable(true);
			dueDate.setValue(RequestsWorkedOnController.getRP().getDueDate().toLocalDate());
			startDate.setValue(RequestsWorkedOnController.getRP().getStartDate().toLocalDate());
		}
	}

}
