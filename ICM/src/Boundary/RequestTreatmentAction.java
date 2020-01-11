package Boundary;

import java.net.URL;

import java.util.ArrayList;
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
import Entity.Request;
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
 @FXML
 private ComboBox Status;
 private int chosenindex;
 private  Request chosenRequest;
 ObservableList<Phase> phaseslist;
 ObservableList<String> Statuslist;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Phase> Phases=new ArrayList<Phase>();
		Phases.add(Phase.evaluation);
		Phases.add(Phase.decision);
		Phases.add(Phase.performance);
		Phases.add(Phase.testing);
		Phases.add(Phase.closed);
		phaseslist=FXCollections.observableArrayList(Phases);
		ArrayList<String> Statuses=new ArrayList<String>();
		Statuses.add("Active");
		Statuses.add("Frozen");
		Statuses.add("Closed");
		Statuslist=FXCollections.observableArrayList(Statuses);
		Status.setItems(Statuslist);
		Phasee.setItems(phaseslist);
    	chosenindex=AllRequestsController.getselectedindex();
        chosenRequest=AllRequestsController.getList().get(chosenindex);   
        Status.setValue(chosenRequest.getStatus());
     //   PhaseAdministrator.setText(chosenRequest.get);
		
	}
	public void start(SplitPane splitpane) {
		this.splitpane=splitpane;
		primaryStage=LoginController.primaryStage;
		this.cc=LoginController.cc;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Update.fxml"));		
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);
			//String AllRequests="All Requests";
			//cc.getClient().sendToServer(AllRequests);
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	
	public void ChangePhase() {
		//if()
	}
}
