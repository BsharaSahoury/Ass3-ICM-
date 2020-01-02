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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private static boolean flag=false;//if chairman flag=true
	public void start(SplitPane splitpane, String path) {
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		try {
			System.out.println(path);
			if(path.equals("chairman"))
			{
				path="/Boundary/RequestWorkOnCommittemember.fxml";
				flag=true;
				
			}
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
		RequestInfoController info = new RequestInfoController();
		info.start(splitpane);
	}
	public void MakeDecisionAction()
	{
		
		MakeDicisionController dicision=new MakeDicisionController();
		dicision.start(splitpane,flag);
		flag=false;
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
