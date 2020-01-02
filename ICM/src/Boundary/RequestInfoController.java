package Boundary;

import javafx.collections.FXCollections;  
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Request;
import javafx.fxml.*;

public class RequestInfoController implements Initializable{
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	@FXML
	private Label lbId;
	@FXML
	private Label lbSystem;
	@FXML
	private Label lbSituation;
	@FXML
	private Label lbChange;
	@FXML
	private Label lbComment;
	@FXML
	private static SplitPane splitpane;
	private static ClientConsole cc;

	public void start(SplitPane splitpane, Request s) {
		primaryStage=LoginController.primaryStage;
		this.cc=LoginController.cc;
		this.splitpane=splitpane;
		String keymessage;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/RequestInfo.fxml"));
			lowerAnchorPane = loader.load();

			splitpane.getItems().set(1, lowerAnchorPane);	
			keymessage="Request Info";
			int x=s.getId();
			Object[] message= {keymessage,x};
			LoginController.cc.getClient().sendToServer(message);

		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
