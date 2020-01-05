package Boundary;
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Employee;
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
		private static int chosen=-1;
		ObservableList<String> statuslist = FXCollections.observableArrayList("Active", "Frozen", "Closed");
		private static ObservableList<Request> list;
		private FXMLLoader loader;
		public void start(SplitPane splitpane,User user,String job)  {
			this.splitpane=splitpane;
			primaryStage=LoginController.primaryStage;
			String[] myRequests=new String[3];
			this.cc=LoginController.cc;
			try{					
				loader = new FXMLLoader(getClass().getResource("/Boundary/MyRequests.fxml"));
				lowerAnchorPane = loader.load();
				splitpane.getItems().set(1, lowerAnchorPane);		
				myRequests[0]="my Requests";
				myRequests[1]=user.getUsername();
				myRequests[2]=job;
				cc.getClient().sendToServer(myRequests);
			} catch(Exception e) {
				e.printStackTrace();
			}	
		}
		public void setTableRequests(ArrayList<Request> arr1){
			if(!arr1.equals(null)) {
			list=FXCollections.observableArrayList(arr1);				
			//tableRequests.setStyle("-fx-alignment: CENTER;");
	       // colName.set
			tableRequests.setItems(list);
			}
		}
		public void fillTable(ArrayList<Request> arr1) {
			// TODO Auto-generated method stub
		loader.<MyRequestsController>getController().setTableRequests(arr1);
			
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		    combo1.setItems(statuslist);
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
