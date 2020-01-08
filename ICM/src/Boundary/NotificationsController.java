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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import messages.AutomaticRecruitMessageController;
import messages.FailedTestMessageController;
import messages.RecruitMessageController;
import messages.SuccessTestMessageController;

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
	@FXML
	public static SplitPane splitpane;

	public void start(SplitPane splitpane,User user) {
		this.user=user;
		primaryStage=LoginController.primaryStage;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/All-Notifications.fxml"));
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
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
	@FXML
	public void clickCell(MouseEvent e) {
		Notification n2=table.getSelectionModel().getSelectedItem();
		String content;
		String[] b;
		int id;
		if(n2 != null) {
		switch(n2.getType()){
			case "recruitForInspector":
				content=n2.getContent();
				b=new String[2];
				b=content.split("#");
				id=Integer.valueOf(b[1]);
				b=b[0].split(": ");
				b=b[1].split(" for");
				b=b[0].split(" ");
				String fullname=b[0]+" "+b[1];
				AutomaticRecruitMessageController armc=new AutomaticRecruitMessageController();
				armc.start(splitpane,id,fullname);
				break;
			case "recruitNotificationForEvaluator":
				content=n2.getContent();
				b=new String[2];
				b=content.split("#");
				id=Integer.valueOf(b[1]);
				RecruitMessageController rmc=new RecruitMessageController();
				rmc.start(splitpane, id);
				break;
			case "fail message sent to Inspector":
				
				content=n2.getContent();
				String[] b2=new String[2];
				b2=content.split("#");
				b2=b2[1].split("failed");
				int id1=Integer.valueOf(b2[0]);
				FailedTestMessageController ftmc=new FailedTestMessageController();
				ftmc.start(splitpane,id1);
				
				break;
			case "success message sent to Inspector":
				content=n2.getContent();
				String[] b3=new String[2];
				b3=content.split("#");
				b3=b3[1].split("passed");
				int id2=Integer.valueOf(b3[0]);
				SuccessTestMessageController stmc=new SuccessTestMessageController();
				stmc.start(splitpane,id2);
				break;		
		}
		}

		
	}

	
	

}
