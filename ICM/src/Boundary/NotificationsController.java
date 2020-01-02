package Boundary;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entity.Employee;
import Entity.Notification;
import Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NotificationsController implements Initializable {
	
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	private static User user;
	@FXML
	private TableView<Notification> table;
	@FXML
	private TableColumn<Notification,String> content;
	@FXML
	TableColumn<Notification,String> date;
	
	public ObservableList<Notification> list;
	
	public static NotificationsController ctrl;

	public void start(SplitPane splitpane,User user) {
		this.user=user;
		primaryStage=LoginController.primaryStage;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/All-Notifications.fxml"));
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);		
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String keymessage="notification";
		Object[] message= {keymessage,user.getUsername()};
		try {
			LoginController.cc.getClient().sendToServer(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insertNotificToTable(ArrayList<Notification> nlist) {
		list=FXCollections.observableArrayList(nlist);
		content.setCellValueFactory(new PropertyValueFactory<Notification,String>("content"));
		date.setCellValueFactory(new PropertyValueFactory<Notification,String>("dateStr"));
		table.setItems(list);
		
	}
	
	

}
