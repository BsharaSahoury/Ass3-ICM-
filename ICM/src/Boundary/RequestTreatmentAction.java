package Boundary;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.omg.PortableServer.POAManagerPackage.State;

import Client.ClientConsole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Entity.Request;
import Entity.RequestPhase;
import Entity.Phase;
public class RequestTreatmentAction extends AllRequestsController implements Initializable {
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
 private Label statuslable;
 @FXML
 private TextArea Explaintxt;
 private int chosenindex;
 private  RequestPhase chosenRequest;
 private static RequestTreatmentAction ctrl;
 ObservableList<Phase> phaseslist;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Phase> Phases=new ArrayList<Phase>();
		Phases.add(Phase.evaluation);
		Phases.add(Phase.performance);
		phaseslist=FXCollections.observableArrayList(Phases);
		Phasee.setItems(phaseslist);
    	chosenindex=AllRequestsController.getselectedindex();
        chosenRequest=AllRequestsController.getList().get(chosenindex);  
        statuslable.setText(chosenRequest.getStatus());
        //
    //    PhaseAdministrator.setPromptText(chosenRequest.getph);
	}
	public void start(SplitPane splitpane) {
		this.splitpane=splitpane;
		primaryStage=LoginController.primaryStage;
		this.cc=LoginController.cc;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Update.fxml"));		
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			ctrl.Phasee.setPromptText(ctrl.chosenRequest.getPhase().toString());
			ctrl.PhaseAdministrator.setPromptText(ctrl.chosenRequest.getEmployee());
			ctrl.DatePickerFrom.setPromptText(ctrl.chosenRequest.getStartDate().toString());
			ctrl.DatePickerTo.setPromptText(ctrl.chosenRequest.getDueDate().toString());
			//String AllRequests="All Requests";
			//cc.getClient().sendToServer(AllRequests);
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	public void ApplyAction() {
		String explain=null;
		explain=Explaintxt.getText();
		if(explain.equals("")) {
			Alert alertSuccess = new Alert(AlertType.WARNING);
			 alertSuccess.setTitle("Warning");
			 alertSuccess.setHeaderText("Miss");
			 alertSuccess.setContentText("PLease fill explain for your decision");
			 alertSuccess.showAndWait();
		}
		else {
			statuslable.setText("frozen");
			RequestPhase chosen=AllRequestsController.getselectedRequest();
			Object[] send=new Object[4];
			send[0]="Inspector changed status to Frozen";
			send[1]=chosen.getR().getId();
			send[2]=InspectorHomeController.getinspector();
			send[3]=explain;
			try {
				LoginController.cc.getClient().sendToServer(send);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
	}
    
	
	
	
	public void BackBtnAction(ActionEvent e) {
		
		InspectorHomeController.AllRequests.start(splitpane, "/Boundary/allRequests.fxml", "Inspector");
      
        
	}
	
	
	
	
	public void cantactive() {
		
	}
	public void ChangePhase() {
		//if()
	}
}
