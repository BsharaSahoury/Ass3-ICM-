package Boundary;

import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
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
	private static int chosen=-1;
	private static ObservableList<Request> list;
	ObservableList<String> statuslist = FXCollections.observableArrayList("Active", "Frozen", "Closed");
	public void start(SplitPane splitpane, String path) {
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			if (path.equals("/Boundary/RequestWorkOnCommittemember.fxml"))
			{
				String RequestsCommitteeMember = "Requests Committee Member";
				cc.getClient().sendToServer(RequestsCommitteeMember);
			}
			if (path.equals("/Boundary/RequestsWorkOnTester.fxml"))
			{
				String RequestsTester = "Requests Tester";
				cc.getClient().sendToServer(RequestsTester);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	}

}
