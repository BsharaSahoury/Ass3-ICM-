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
import Entity.Request;

public class AllRequestsController implements Initializable {
	public static Stage primaryStage;
	private static ClientConsole cc;
	private AnchorPane lowerAnchorPane;
	@FXML
	private TableView<Request> tableRequests;	
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
	private Button RequestInfo;
	private static SplitPane splitpane;
	@FXML
	private ComboBox Groupby;
	private FXMLLoader loader;
	private TableView<Request> tabl;
	private static int chosen=-1;
	private static ObservableList<Request> list;
	ObservableList<String> statuslist=FXCollections.observableArrayList("Active","Frozen","Closed");
	public void start(SplitPane splitpane,String path) {
		primaryStage=LoginController.primaryStage;
		this.cc=LoginController.cc;
		try{	
			loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();		
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
			String AllRequests="All Requests";
			cc.getClient().sendToServer(AllRequests);
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	public void setTableRequests(ArrayList<Request> arr1){
		list=FXCollections.observableArrayList(arr1);	
		//tableRequests.setStyle("-fx-alignment: CENTER;");
       // colName.set
		tableRequests.setItems(list);
	}
	public void fillTable(ArrayList<Request> arr1) {
			// TODO Auto-generated method stub
		loader.<AllRequestsController>getController().setTableRequests(arr1);
			
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
	public static int getselectedindex() {
		return chosen;
	}
	
	public static ObservableList<Request> getList(){
		return list;
	}
	public void RequestTreatmentAction() {
		chosen=tableRequests.getSelectionModel().getSelectedIndex();
		if(chosen!=-1) {
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
		colID.setCellValueFactory(new PropertyValueFactory<Request,Integer>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<Request,String>("initiatorName"));
		colStatus.setCellValueFactory(new PropertyValueFactory<Request,String>("status"));		
		colPriflig.setCellValueFactory(new PropertyValueFactory<Request,String>("privilegedInfoSys"));
		colSubDate.setCellValueFactory(new PropertyValueFactory<Request,Date>("date"));
		//this.tabl=tableRequests;
		//ObservableList<Request> aa=FXCollections.observableArrayList();
		//aa.add(new Request(1,"sss","xxx","Qqqq",LocalDate.of(1915, Month.SEPTEMBER, 1)));
		//tableRequests.setItems(aa);
	}	
}
