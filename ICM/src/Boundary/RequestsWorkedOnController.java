package Boundary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import Client.ClientConsole;
import Entity.Request;

public class RequestsWorkedOnController {
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
	private static ObservableList<Request> list;
	//public static 
	public void start(SplitPane splitpane) {
		primaryStage=LoginController.primaryStage;
		this.cc=LoginController.cc;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/allRequests.fxml"));
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);
			String AllRequests="All Requests";
			cc.getClient().sendToServer(AllRequests);
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	public void fillTable(ArrayList<Request> arr1) {
		
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
		}
}
