package Boundary;
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Request;
import Entity.User;
import javafx.fxml.*;
public class MyRequestsController implements Initializable {
		public static Stage primaryStage;
		private static ClientConsole cc;
		private AnchorPane lowerAnchorPane;
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
		private TableView<Request> tableRequests;
		@FXML
		private ComboBox combo1;
		@FXML
		private TextField searchID;
		@FXML
		private Button searchbtn;
		@FXML
        private Button groupbyBtn;
		@FXML
		private Button requestInfo;
		@FXML
		private Button refresh;
		@FXML 
		private Button question;
		@FXML
		private static SplitPane splitpane;
		ObservableList<String> statuslist = FXCollections.observableArrayList("Active", "Frozen", "Closed");
		private static ObservableList<Request> list;
		private FXMLLoader loader;
		public void start(SplitPane splitpane,User user)  {
			this.splitpane=splitpane;
			primaryStage=LoginController.primaryStage;
			String[] myRequests=new String[2];
			this.cc=LoginController.cc;
			try{	
				
				loader = new FXMLLoader(getClass().getResource("/Boundary/MyRequests.fxml"));
				lowerAnchorPane = loader.load();
				splitpane.getItems().set(1, lowerAnchorPane);		
				myRequests[0]="my Requests";
				myRequests[1]=user.getUsername();
				cc.getClient().sendToServer(myRequests);
			} catch(Exception e) {
				e.printStackTrace();
			}			
		}
	/*	public void fillTable(ArrayList<Request> arr1) {
				// TODO Auto-generated method stub
				list=FXCollections.observableArrayList(arr1);
				colID.setStyle("-fx-alignment: CENTER;");
				colName.setStyle("-fx-alignment: CENTER;");
				colID.setCellValueFactory(new PropertyValueFactory<Request,String>("id"));
				colName.setCellValueFactory(new PropertyValueFactory<Request,String>("nameInitiator"));
				colStatus.setCellValueFactory(new PropertyValueFactory<Request,String>("status"));
				colPriflig.setCellValueFactory(new PropertyValueFactory<Request,String>("privilegedInfoSys"));
				colSubDate.setCellValueFactory(new PropertyValueFactory<Request,String>("date"));
				//tableRequests.setStyle("-fx-alignment: CENTER;");
				tableRequests.setItems(list);			
			}*/
		public void RequestInfoAction()
		{
			RequestInfoController myRequest = new RequestInfoController();
			myRequest.start(splitpane);
		}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		combo1.setItems(statuslist);
	}
}
