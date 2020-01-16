package Boundary;

import java.io.IOException;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientConsole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
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
 private Label currentphase;
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
 private TextArea Explaintxt2;
 @FXML
 private Label phaseadminlable;
 @FXML
 private MenuButton  chooseengineer;
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
	//	Phasee.setItems(phaseslist);
		/*
		 // create the data to show in the CheckComboBox 
		 final ObservableList<String> strings = FXCollections.observableArrayList();
		 for (int i = 0; i <= 100; i++) {
		     strings.add("Item " + i);
		 }
		 
		 // Create the CheckComboBox with the data 
		 final CheckComboBox<String> checkComboBox = new CheckComboBox<String>(strings);
		 
		 // and listen to the relevant events (e.g. when the selected indices or 
		 // selected items change).
		 checkComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
		     public void onChanged(ListChangeListener.Change<? extends String> c) {
		         System.out.println(checkComboBox.getCheckModel().getSelectedItems());
		     }
		 });
		 }*/
	//	chooseengineer.setItems(phaseslist);
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
			ctrl.currentphase.setText(ctrl.chosenRequest.getPhase().toString());
			Object[] msg1= {"getFullNameOfEmployee",getClass().getName(),ctrl.chosenRequest.getEmployee()};
			try {
				LoginController.cc.getClient().sendToServer(msg1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			if(ctrl.currentphase.getText().equals("evaluation")) {
				Object[] msg= {"evaluators",getClass().getName()};
				try {
					LoginController.cc.getClient().sendToServer(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(ctrl.currentphase.getText().equals("performance")) {
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
		//indexphase=ctrl.Phasee.getSelectionModel().getSelectedIndex();
		
		if(ctrl.PhaseAdministrator.getSelectionModel().getSelectedItem()==null&&ctrl.DatePickerFrom.getValue()==null&&ctrl.DatePickerTo.getValue()==null) {
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
		else if(ctrl.DatePickerTo!=null && ctrl.DatePickerFrom!=null&&(ctrl.DatePickerFrom.getValue().compareTo(ctrl.DatePickerTo.getValue()) >= 0 || LocalDate.now().compareTo(ctrl.DatePickerFrom.getValue()) >= 0)) {
			 Alert alertSuccess = new Alert(AlertType.WARNING);
			 alertSuccess.setTitle("Warning");
			 alertSuccess.setHeaderText("Wrong dates");
			 alertSuccess.setContentText("Cheak the dates the you specified");
			 alertSuccess.showAndWait();
		}
		else if(Explaintxt2.getText().equals("")) {
			 Alert alertSuccess = new Alert(AlertType.WARNING);
			 alertSuccess.setTitle("Warning");
			 alertSuccess.setHeaderText("Miss");
			 alertSuccess.setContentText("PLease fill explain for your update");
			 alertSuccess.showAndWait();
		}
		//
		else {
			String phase=ctrl.currentphase.getText();
			String phaseadmin=null;
			if(ctrl.PhaseAdministrator.getSelectionModel().getSelectedItem()==null) {
			phaseadmin=ctrl.PhaseAdministrator.getPromptText();
            if(phaseadmin.equals("Choose phase administrator"))
			phaseadmin=null;
			}
			else {
			phaseadmin=ctrl.PhaseAdministrator.getSelectionModel().getSelectedItem().toString();
			}
			LocalDate start=null;
			LocalDate end=null;
			if(DatePickerFrom.getValue()!=null&&DatePickerTo.getValue()!=null) {
			start=DatePickerFrom.getValue();
			end=DatePickerTo.getValue();
			}
			int id=ctrl.chosenRequest.getId();
			int repetion=ctrl.chosenRequest.getRepetion();
			String explain=Explaintxt2.getText();
			Object[] msg= {"manualRequestTreatmentRecruitEvaluator",phaseadmin,id,phase,repetion,start,end,explain};
			try {
				LoginController.cc.getClient().sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		else if(ctrl.currentphase.getText().equals("performance")) {
			String phase="performance";
			String phaseadmin=ctrl.PhaseAdministrator.getSelectionModel().getSelectedItem().toString();
			Date start=Date.valueOf(DatePickerFrom.getValue());
			//System.out.println(start);
			Date end=Date.valueOf(DatePickerTo.getValue());
		}
	}*/
	/*
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
	}*/
}
}
