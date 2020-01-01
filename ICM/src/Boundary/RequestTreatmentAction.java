package Boundary;

import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientConsole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Entity.Phase;
public class RequestTreatmentAction implements Initializable {
 public static Stage primaryStage;
 private static ClientConsole cc;
 private AnchorPane lowerAnchorPane;
 @FXML
 private static SplitPane splitpane;
 @FXML
 private ComboBox Phasee;
 @FXML
 private ComboBox PhaseAdministrator;
 @FXML
 private DatePicker DatePickerFrom;
 @FXML
 private DatePicker DatePickerTo;
 private Phase s;
 ObservableList<Phase> phaseslist=FXCollections.observableArrayList(s);
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Phasee.setItems(phaseslist);
	}
	public void start(SplitPane splitpane) {
		this.splitpane=splitpane;
		primaryStage=LoginController.primaryStage;
		this.cc=LoginController.cc;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Update.fxml"));		
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);
			System.out.println("zzxzx");
			//String AllRequests="All Requests";
			//cc.getClient().sendToServer(AllRequests);
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
}
