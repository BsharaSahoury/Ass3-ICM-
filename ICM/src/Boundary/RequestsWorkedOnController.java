package Boundary;

import java.net.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Request;
import Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RequestsWorkedOnController implements Initializable {
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
	@FXML
	private static SplitPane splitpane;
	@FXML
	private Button MakeDecision; 
	@FXML
	private ComboBox Groupby;
	private static ObservableList<Request> list;
	ObservableList<String> statuslist = FXCollections.observableArrayList("Active", "Frozen", "Closed");
	private FXMLLoader loader;
	public void start(SplitPane splitpane, String path,User user,String job) {
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		String [] RequestWorkedON=new String[3];
		try {
			loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			RequestWorkedON[0]="Requests worked on";
			RequestWorkedON[1]=user.getUsername();
			RequestWorkedON[2]=job;
			cc.getClient().sendToServer(RequestWorkedON);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setTableRequests(ArrayList<Request> arr1){
		
		System.out.println(arr1.get(0));
		if(!arr1.equals(null)) {
			System.out.println("sssss");
		list=FXCollections.observableArrayList(arr1);				
		tableRequests.setItems(list);
		}
	}
	public void fillTable(ArrayList<Request> arr1) {
	loader.<RequestsWorkedOnController>getController().setTableRequests(arr1);	
	}
	public void RequestInfoAction() {
		RequestInfoController info = new RequestInfoController();
		info.start(splitpane);
	}
	public void MakeDecisionAction()
	{
		
		MakeDicisionController dicision=new MakeDicisionController();
		dicision.start(splitpane);
	}

	public void InsertTestResultAction()
	{
		TestResultController result = new TestResultController();
		result.start(splitpane);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Groupby.setItems(statuslist);
		colID.setCellValueFactory(new PropertyValueFactory<Request,Integer>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<Request,String>("initiatorName"));
		colStatus.setCellValueFactory(new PropertyValueFactory<Request,String>("status"));		
		colPriflig.setCellValueFactory(new PropertyValueFactory<Request,String>("privilegedInfoSys"));
		colSubDate.setCellValueFactory(new PropertyValueFactory<Request,Date>("date"));
	}

}
