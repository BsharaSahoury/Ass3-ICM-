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
import messages.CommitteeDecisionAproveorRejectController;
import messages.DecisionCommitteeMemberMessageController;
import messages.RecruitMessageController;

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
    private static int IDRequestForDecision;
    private static String CommittteDecision;
    private static String ExplainDecision;
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
			case "Decision of Committee Member":
				content=n2.getContent();
				b=new String[2];
				b=content.split("id=");
				b=b[1].split("\n");			
				IDRequestForDecision=Integer.valueOf(b[0]);
				System.out.println(IDRequestForDecision);
				b=content.split("is '");
				b=b[1].split("' for");
				CommittteDecision=b[0];
				DecisionCommitteeMemberMessageController obj=new DecisionCommitteeMemberMessageController();
				obj.start(splitpane);
				break;
			case "Chairman Approved Comittee Members Decision is approve":
				content=n2.getContent();
				b=new String[2];
				b=content.split("id=");
				b=b[1].split("\n");
				IDRequestForDecision=Integer.valueOf(b[0]);
				b=content.split("is '");
				b=b[1].split("' for");
				CommittteDecision=b[0];
				CommitteeDecisionAproveorRejectController obj2=new CommitteeDecisionAproveorRejectController();
				obj2.start(splitpane,"/messages/RecruitPerformanceLeader.fxml");
				break;
		}
		}

		
	}
public static int getidofrequestforDecision() {
	return IDRequestForDecision;
}
public static String getDecisionofcommitteemember() {
	return CommittteDecision;
}
}
