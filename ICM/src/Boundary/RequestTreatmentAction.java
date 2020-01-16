package Boundary;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
 @FXML
 private Label phaseadminlable;
 private int chosenindex;
 private static int indexphase=-1;
 private static int indexadmin=-1;
 private  RequestPhase chosenRequest;
 public static RequestTreatmentAction ctrl;
 ObservableList<Phase> phaseslist;
 private ObservableList<String> list;
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
			Object[] msg1= {"getFullNameOfEmployee",getClass().getName(),ctrl.chosenRequest.getEmployee()};
			try {
				LoginController.cc.getClient().sendToServer(msg1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			if(ctrl.Phasee.getPromptText().equals("evaluation")) {
				Object[] msg= {"evaluators",getClass().getName()};
				try {
					LoginController.cc.getClient().sendToServer(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(ctrl.Phasee.getPromptText().equals("performance")) {
				Object[] msg= {"Performance leaders",getClass().getName()};
				try {
					LoginController.cc.getClient().sendToServer(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
           // ctrl.phaseadminlable.setVisible(false);
			//ctrl.PhaseAdministrator.setVisible(false);
			//ctrl.PhaseAdministrator.setPromptText(ctrl.chosenRequest.getEmployee());
			if(ctrl.chosenRequest.getStartDate()!=null&&ctrl.chosenRequest.getDueDate()!=null) {
			ctrl.DatePickerFrom.setPromptText(ctrl.chosenRequest.getStartDate().toString());
			ctrl.DatePickerTo.setPromptText(ctrl.chosenRequest.getDueDate().toString());
			}
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
	public void setcombotext(String currentadmin) {
		if(currentadmin!=null)
		ctrl.PhaseAdministrator.setPromptText(currentadmin);
		//ctrl.phaseadminlable.setVisible(true);
		//ctrl.PhaseAdministrator.setVisible(true);
	}
	public void fillCombo(ArrayList<String> names) {
		list=FXCollections.observableArrayList(names);
		ctrl.PhaseAdministrator.setItems(list);
	}
	public void updateandsaveaction() {
		indexphase=ctrl.Phasee.getSelectionModel().getSelectedIndex();
		System.out.println(ctrl.Phasee.getPromptText());
	
		if((ctrl.Phasee.getSelectionModel().getSelectedIndex()==-1)&&ctrl.Phasee.getPromptText().equals("decision")||ctrl.Phasee.getPromptText().equals("testing")||ctrl.Phasee.getPromptText().equals("closing")) {
		Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Choose phase");
        alert.setContentText("You can't update phase administrator or duration for "+ctrl.Phasee.getPromptText()+" phase");
        alert.showAndWait();
		}
		
		else if(ctrl.PhaseAdministrator.getSelectionModel().getSelectedItem()==null&&ctrl.DatePickerFrom.getValue()==null&&ctrl.DatePickerTo.getValue()==null) {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Warning");
	        alert.setContentText("You didn't update anything");
	        alert.showAndWait();
		}
		else if(ctrl.DatePickerFrom.getValue()==null&&ctrl.DatePickerTo.getValue()!=null) {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Warning");
	        alert.setContentText("If you chose 'to' date you must choose 'start' date");
	        alert.showAndWait();
		}
		else if(ctrl.DatePickerFrom.getValue()!=null&&ctrl.DatePickerTo.getValue()==null) {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Warning");
	        alert.setContentText("If you chose 'start' date you must choose 'to' date");
	        alert.showAndWait();
		}
		else if(ctrl.Phasee.getSelectionModel().getSelectedIndex()==0||ctrl.Phasee.getPromptText().equals("evaluation")) {
			String phase="evaluation";
			String phaseadmin=null;
			if(ctrl.PhaseAdministrator.getSelectionModel().getSelectedItem()==null) {
			phaseadmin=ctrl.PhaseAdministrator.getPromptText();
            if(phaseadmin.equals("Choose phase administrator"))
			phaseadmin=null;
			}
			else {
			phaseadmin=ctrl.PhaseAdministrator.getSelectionModel().getSelectedItem().toString();
			}
			Date start=null;
			Date end=null;
			if(DatePickerFrom.getValue()!=null&&DatePickerTo.getValue()!=null) {
			start=Date.valueOf(DatePickerFrom.getValue());
			end=Date.valueOf(DatePickerTo.getValue());
			}
			int id=ctrl.chosenRequest.getId();
			int repetion=ctrl.chosenRequest.getRepetion();
			Object[] msg= {"manualRequestTreatmentRecruitEvaluator",phaseadmin,id,"evaluation",repetion,start,end};
			try {
				LoginController.cc.getClient().sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(ctrl.Phasee.getSelectionModel().getSelectedIndex()==1||ctrl.Phasee.getPromptText().equals("performance")) {
			Phase phase=Phase.performance;
			String phaseadmin=ctrl.PhaseAdministrator.getSelectionModel().getSelectedItem().toString();
			Date start=Date.valueOf(DatePickerFrom.getValue());
			//System.out.println(start);
			Date end=Date.valueOf(DatePickerTo.getValue());
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Warning");
	        alert.setHeaderText("Choose phase");
	        alert.setContentText("Please choose evaluation or performance phase\n that u want to update administrator or dates on the chosen phase");
	        alert.showAndWait();
		}
	}
	public void ChangePhase() {
		if(ctrl.Phasee.getSelectionModel().getSelectedIndex()==0) {
			Object[] msg= {"evaluators",getClass().getName()};
			try {
				LoginController.cc.getClient().sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ctrl.PhaseAdministrator.setPromptText("Choose phase administrator");
		}
		else if(ctrl.Phasee.getSelectionModel().getSelectedIndex()==1) {
			Object[] msg= {"Performance leaders",getClass().getName()};
			try {
				LoginController.cc.getClient().sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ctrl.PhaseAdministrator.setPromptText("Choose phase administrator");
		}
	}
}
