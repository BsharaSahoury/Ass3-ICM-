package Boundary;

import javafx.collections.FXCollections;




import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.layout.Pane;
import Client.ClientConsole;
import Entity.Phase;
import Entity.Request;
import Entity.RequestPhase;

public class AllRequestsController implements Initializable {
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
	private Button RequestInfo;
	private static SplitPane splitpane;
	@FXML
	private ComboBox Groupby;
	private FXMLLoader loader;
	private TableView<Request> tabl;
	private static int chosenRequest=-1;
	private static int chosengroupbytype=-1;
	private static ObservableList<RequestPhase> list;
	private static ArrayList<RequestPhase> arrofRequests;
	private static String job;
	ObservableList<String> statuslist=FXCollections.observableArrayList("Active","Frozen","Closed","All");
	public void start(SplitPane splitpane,String path,String job) {
		this.job=job;
		primaryStage=LoginController.primaryStage;
		this.cc=LoginController.cc;
		String[] AllRequests=new String[2];
		try{	
			loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();		
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
			AllRequests[0]="All Requests";
			AllRequests[1]=job;
			cc.getClient().sendToServer(AllRequests);
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	public void setTableRequests(ArrayList<RequestPhase> arr1){
		list=FXCollections.observableArrayList(arr1);
		//tableRequests.setStyle("-fx-alignment: CENTER;");
       // colName.set
		tableRequests.setItems(list);
	}
	public void fillTable(ArrayList<RequestPhase> arr1) {
		arrofRequests=arr1;
			// TODO Auto-generated method stub
		//ArrayList<RequestPhase> arr=new ArrayList<RequestPhase>();
		//arr.add(new RequestPhase(null,null,arr1.get(0),Phase.Closed));
		loader.<AllRequestsController>getController().setTableRequests(arr1);
			
		}
	public void GroupbyAction(ActionEvent e) {
		chosengroupbytype=Groupby.getSelectionModel().getSelectedIndex();
		String groupbystatus=null;
		ArrayList<RequestPhase> arr=new ArrayList<RequestPhase>();
		if(chosengroupbytype!=-1) {
			if(chosengroupbytype==0)
				groupbystatus="active";
			else if(chosengroupbytype==1)
				groupbystatus="frozen";
			else if(chosengroupbytype==2)
				groupbystatus="closed";
			else if(chosengroupbytype==3)
				groupbystatus="All";
			if(groupbystatus.equals("All")&&job.equals("Inspector")) {
				InspectorHomeController.AllRequests.loader.<AllRequestsController>getController().tableRequests.setItems(FXCollections.observableArrayList(arrofRequests));
			}
			else if(groupbystatus.equals("All")&&job.equals("Administrator")) {
				AdministratorHomeController.AllRequests.loader.<AllRequestsController>getController().tableRequests.setItems(FXCollections.observableArrayList(arrofRequests));	
			}
			else {
			for(int i=0;i<arrofRequests.size();i++) 
				if((arrofRequests.get(i)).getStatus().equals(groupbystatus))
					arr.add(arrofRequests.get(i));	
			if(job.equals("Inspector"))
			InspectorHomeController.AllRequests.loader.<AllRequestsController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
			else
			AdministratorHomeController.AllRequests.loader.<AllRequestsController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));	
			}		
	}
	}
	public void RequestInfoAction(ActionEvent e) {
		chosenRequest=tableRequests.getSelectionModel().getSelectedIndex();
		if(chosenRequest!=-1) {
			RequestPhase s =tableRequests.getSelectionModel().getSelectedItem();
			System.out.println("00000000000");
			RequestInfoController requestifo = new RequestInfoController();
			System.out.println("00000000000");
	    	//requestifo.start(splitpane,s);
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
		return chosenRequest;
	}
	
	public static ObservableList<RequestPhase> getList(){
		return list;
	}
	public void RequestTreatmentAction(ActionEvent e) {
		chosenRequest=tableRequests.getSelectionModel().getSelectedIndex();
		if(chosenRequest!=-1) {
		RequestTreatmentAction Treatment = new RequestTreatmentAction();
		Treatment.start(splitpane);
		}
		else {
	        Alert alertWarning = new Alert(AlertType.WARNING);
	        alertWarning.setTitle("Warning Alert Title");
	        alertWarning.setHeaderText("Warning!");
	        alertWarning.setContentText("please choose requset");
	        alertWarning.showAndWait();
	        }
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Groupby.setItems(statuslist);
		colID.setCellValueFactory(new PropertyValueFactory<RequestPhase,Integer>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<RequestPhase,String>("initiatorName"));
		colStatus.setCellValueFactory(new PropertyValueFactory<RequestPhase,String>("status"));		
		colPriflig.setCellValueFactory(new PropertyValueFactory<RequestPhase,String>("privilegedInfoSys"));
		colSubDate.setCellValueFactory(new PropertyValueFactory<RequestPhase,Date>("date"));
		colPhase.setCellValueFactory(new PropertyValueFactory<RequestPhase,Phase>("phase"));
		//this.tabl=tableRequests;
		//ObservableList<Request> aa=FXCollections.observableArrayList();
		//aa.add(new Request(1,"sss","xxx","Qqqq",LocalDate.of(1915, Month.SEPTEMBER, 1)));
		//tableRequests.setItems(aa);
	}	
}
