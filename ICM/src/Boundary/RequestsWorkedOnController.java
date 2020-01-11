package Boundary;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Phase;
import Entity.Employee;
import Entity.Request;
import Entity.RequestPhase;
import Entity.User;
import Entity.State;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import messages.AutomaticRecruitMessageController;

public class RequestsWorkedOnController implements Initializable {
	public static Stage primaryStage;
	private static ClientConsole cc;
	private AnchorPane lowerAnchorPane;
	@FXML
	private TableView<RequestPhase> tableRequests;
	@FXML
	private TableColumn colID;
	@FXML
	private TableColumn colName;
	@FXML
	private TableColumn colStatus;
	@FXML
	private TableColumn colPriflig;
	@FXML
	private TableColumn colSubDate;
	@FXML
	private TableColumn colPhase;
	@FXML
	private TableColumn colState;
	@FXML
	private Button RequestInfo;
	@FXML
	private static SplitPane splitpane;
	@FXML
	private Button MakeDecision; 
	@FXML
	private ComboBox Groupby;
	@FXML
	private Button approveFinish;
	private static int chosengroupbytype=-1;
	private static int chosen=-1;
	private static ObservableList<RequestPhase> list;
	private static ArrayList<RequestPhase> arrofRequests;
	private static String job;
	public static RequestsWorkedOnController ctrl1;
	public static MakeDicisionController decision;
	private static int id;
	private static String system;
	ObservableList<String> statuslist = FXCollections.observableArrayList("work", "wait", "over","All");
	public static  FXMLLoader loader;
	private User user;
	private static RequestPhase rp; 
	public void start(SplitPane splitpane, String path,User user,String job) {
		this.job=job;
		this.user=user;
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		String [] RequestWorkedON=new String[3];
		try {
			loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();
			ctrl1=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			RequestWorkedON[0]="Requests worked on";
			if(job.equals("Comittee Member")) {
				RequestWorkedON[1]=ComitteeMemberHomeController.Chairman.getUsername();
			}else {
			RequestWorkedON[1]=user.getUsername();
			}
			RequestWorkedON[2]=job;
			
			cc.getClient().sendToServer(RequestWorkedON);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setTableRequests(ArrayList<RequestPhase> arr1){
		if(!arr1.equals(null)) {
		list=FXCollections.observableArrayList(arr1);				
		tableRequests.setItems(list);
		}
	}
	public void fillTable(ArrayList<RequestPhase> arr1) {
	
	arrofRequests=arr1;	
	loader.<RequestsWorkedOnController>getController().setTableRequests(arr1);	
	}
	public void GroupbyAction(ActionEvent e) {
		chosengroupbytype=Groupby.getSelectionModel().getSelectedIndex();
		String groupbystatus=null;
		ArrayList<RequestPhase> arr=new ArrayList<RequestPhase>();
		if(chosengroupbytype!=-1) {
			if(chosengroupbytype==0)
				groupbystatus="work";
			else if(chosengroupbytype==1)
				groupbystatus="wait";
			else if(chosengroupbytype==2)
				groupbystatus="over";
			else if(chosengroupbytype==3)
				groupbystatus="All";
			if(groupbystatus.equals("All")) {
				arr=arrofRequests;
			}else {
				for(int i=0;i<arrofRequests.size();i++) 
					if((arrofRequests.get(i)).getState().equals(State.valueOf(groupbystatus))) 
						arr.add(arrofRequests.get(i));			
			}
			if(!arr.equals(null)) {
				switch(job) {
				case "Evaluator":
					EvaluatorHomeController.RequestWorkON.loader.<RequestsWorkedOnController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
					break;
				case "Comittee Member":
					ComitteeMemberHomeController.RequestWorkON.loader.<RequestsWorkedOnController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
					break;
				case "Chairman":
					ChairmanHomeController.RequestWorkON.loader.<RequestsWorkedOnController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
					break;
				case "Tester":
					TesterHomeController.RequestWorkON.loader.<RequestsWorkedOnController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
					break;
				}
			}		
	      }
	}
	public void SetDuration() {
		chosen=tableRequests.getSelectionModel().getSelectedIndex();
		if(chosen!=-1) {
			RequestPhase s =tableRequests.getSelectionModel().getSelectedItem();
			id=s.getId();

			String keymessage = "get duration";
			Object[] message = { keymessage, s.getId(),s.getPhase()};
			try {
				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e) {
				e.printStackTrace();}
		
	}
		else {
	        Alert alertWarning = new Alert(AlertType.WARNING);
	        alertWarning.setTitle("Warning Alert Title");
	        alertWarning.setHeaderText("Warning!");
	        alertWarning.setContentText("please choose requset");
	        alertWarning.showAndWait();
	        }
	}
		public void SetDurationHelp(RequestPhase s) {
			this.rp=s;
			rp.setId(id);
			SetDurationController setDuration = new SetDurationController();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if(job.equals("Evaluator")) {
		            	setDuration.start(splitpane,rp,"/Boundary/DuratinForEvaluator.fxml",rp.getPhase());
					}
					else {
						setDuration.start(splitpane,rp ,"/Boundary/Duration.fxml",rp.getPhase());
					}
				}
			});
	}
	
	public void RequestInfoAction() {
		chosen=tableRequests.getSelectionModel().getSelectedIndex();
		if(chosen!=-1) {
			Request s =tableRequests.getSelectionModel().getSelectedItem();
			RequestInfoController requestifo = new RequestInfoController();
	    	requestifo.start(splitpane,s);
		}
		else {
	        Alert alertWarning = new Alert(AlertType.WARNING);
	        alertWarning.setTitle("Warning Alert Title");
	        alertWarning.setHeaderText("Warning!");
	        alertWarning.setContentText("please choose requset");
	        alertWarning.showAndWait();
	        }
	}

	public void approveFinishAction(ActionEvent e) {
		chosen=tableRequests.getSelectionModel().getSelectedIndex();
		if(chosen!=-1) {
			RequestPhase r=tableRequests.getSelectionModel().getSelectedItem();
			if(!r.getState().toString().equals("work")) {
				Alert alertWarning = new Alert(AlertType.WARNING);
		        alertWarning.setTitle("Warning Alert Title");
		        alertWarning.setHeaderText("Warning!");
		        alertWarning.setContentText("the request is not at performance phase!!");
		        alertWarning.showAndWait();
			}
			else {
				Alert alertWarning = new Alert(AlertType.CONFIRMATION);
		        alertWarning.setTitle("Warning Alert Title");
		        alertWarning.setHeaderText("confirm!");
		        alertWarning.setContentText("are you sure that you've finished working on this request?");
		        Optional<ButtonType> result=alertWarning.showAndWait();
		        ButtonType button=result.orElse(ButtonType.CANCEL);
		        if(button==ButtonType.OK) {
		        	Object[] msg= {"performance done",r.getId()};
					try {
						LoginController.cc.getClient().sendToServer(msg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
			}
		}
	}
				
	
   public void InsertTestResultAction() {
		chosen=tableRequests.getSelectionModel().getSelectedIndex();
		if(chosen!=-1) {
			RequestPhase s =tableRequests.getSelectionModel().getSelectedItem();
			if(s.getState().equals(State.work))
			{
				TestResultController setTestResult = new TestResultController();
				setTestResult.start(splitpane,s);
			}
			else {
				 Alert alertWarning = new Alert(AlertType.ERROR);
			        alertWarning.setContentText("The request state must be work");
			        alertWarning.showAndWait();
			}
		}
		else {
	        Alert alertWarning = new Alert(AlertType.WARNING);
	        alertWarning.setTitle("Warning Alert Title");
	        alertWarning.setHeaderText("Warning!");
	        alertWarning.setContentText("please choose requset");
	        alertWarning.showAndWait();
	        }
	}

			
   

	
	
	

	public static int getselectedindex() {
		return chosen;
	}
	public static int getId()
	{
		return id;
	}
	public static String getSystem() {
		return system;
	}
	public void MakeDecisionAction()
	{		
		chosen=tableRequests.getSelectionModel().getSelectedIndex();
		if(chosen==-1) {
			Alert alertWarning = new Alert(AlertType.WARNING);
	        alertWarning.setTitle("Warning Alert Title");
	        alertWarning.setHeaderText("Warning!");
	        alertWarning.setContentText("please choose requset");
	        alertWarning.showAndWait();
		}
		else if(!tableRequests.getSelectionModel().getSelectedItem().getState().equals(State.work)) {
			 Alert alertWarning = new Alert(AlertType.WARNING);
		     alertWarning.setTitle("Warning Alert Title");
		     alertWarning.setHeaderText("Warning!");
		     alertWarning.setContentText("This Request is not on work");
		     alertWarning.showAndWait();
		}
		else {
			RequestPhase selected =tableRequests.getSelectionModel().getSelectedItem();
			decision=new MakeDicisionController();
			decision.start(splitpane,selected,user);
		}
	}
	public void EvaluationReportAction() {
		chosen=tableRequests.getSelectionModel().getSelectedIndex();
		if(chosen!=-1) {
			RequestPhase s =tableRequests.getSelectionModel().getSelectedItem();
			System.out.println(s.getState().toString());
			System.out.println(State.work.toString());
			if(s.getState().toString().equals(State.work.toString()))
			{
			CreateEvaluationReportController requestifo = new CreateEvaluationReportController();
	    	requestifo.start(splitpane,s.getId());
			}
			else {
				 Alert alertWarning = new Alert(AlertType.WARNING);
			        alertWarning.setTitle("Warning Alert Title");
			        alertWarning.setHeaderText("Warning!");
			        alertWarning.setContentText("The Request should be in state work");
			        alertWarning.showAndWait();
			}
		}
		else {
	        Alert alertWarning = new Alert(AlertType.WARNING);
	        alertWarning.setTitle("Warning Alert Title");
	        alertWarning.setHeaderText("Warning!");
	        alertWarning.setContentText("please choose requset");
	        alertWarning.showAndWait();
	        }
	}
	public static ObservableList<RequestPhase> getList(){
		return list;
	}
	
public static RequestPhase getRP() {
	return rp;
}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Groupby.setItems(statuslist);
		colID.setCellValueFactory(new PropertyValueFactory<RequestPhase,Integer>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<RequestPhase,Integer>("initiatorName"));
		colStatus.setCellValueFactory(new PropertyValueFactory<RequestPhase,String>("status"));		
		colPriflig.setCellValueFactory(new PropertyValueFactory<RequestPhase,String>("privilegedInfoSys"));
		colSubDate.setCellValueFactory(new PropertyValueFactory<RequestPhase,Date>("date"));
		colState.setCellValueFactory(new PropertyValueFactory<RequestPhase,Integer>("State"));
	}

}