package messages;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DecisionCommitteeMemberMessageController implements Initializable {
	@FXML
	Label DecisionLable;
	@FXML
	Button approve;
	public static DecisionCommitteeMemberMessageController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;
	private int notificationID;
	private String CommitteeDecision;
	public static int flag=-1;
	private static String notdetails;
	public void start(SplitPane splitpane) {
		primaryStage=LoginController.primaryStage;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/CommitteeMemberDecision-message.fxml"));
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			Object[] message= {"get explain notification",notificationID};
			try {
				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}				
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;			
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	public static void setdetails(String details) {
		ctrl.notdetails=details;
		System.out.println("ssxxx");
		System.out.println(ctrl.notdetails);
		ctrl.DecisionLable.setText(ctrl.notdetails);
	}
	public void approveAction(ActionEvent e) {	
		if(flag==-1) {
			flag=0;
		Object[] message= {"approve committee decision",notificationID,CommitteeDecision,notdetails};
		try {
			LoginController.cc.getClient().sendToServer(message);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		}else {	
			 Alert alertSuccess = new Alert(AlertType.WARNING);
			 alertSuccess.setTitle("Warning");
			 alertSuccess.setHeaderText("Already Approve");
			 alertSuccess.setContentText("You already approved this decision");
			 alertSuccess.show();
		}
	}
	/*
	public void chooseOtherAction(ActionEvent e) {
		String fullname=combo.getSelectionModel().getSelectedItem();
		if(fullname==null) {
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("TEST");
	        alert.setHeaderText("ERROR");
	        alert.setContentText("please choose an evaluator");
	        alert.showAndWait();
	        return;
		}
		Object[] msg= {"manualR",fullname,requestID};
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		notificationID=NotificationsController.getidofrequestforDecision();
		CommitteeDecision=NotificationsController.getDecisionofcommitteemember();
	}
}
