package messages;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
import Entity.Phase;
import Entity.Request;
import Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ExtensionConfirmationMessage implements Initializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML
	Label requestLabel;
	@FXML
	Label evaluatorLabel;
	@FXML
	Button approve;
	@FXML
	Button reject;
	@FXML
	Button other;
	@FXML
	ComboBox<String> combo;
	private static Request r;
	public static ExtensionConfirmationMessage ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;
	private static int requestID;
	private String fullname;
	private ObservableList<String> list;
	private static Phase phase;
	public void start(SplitPane splitpane,int id,Phase phase,String content) {
		primaryStage=LoginController.primaryStage;
		this.r=r;
		this.requestID=id;
		this.phase=phase;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/ExtensionConfirmationMessage.fxml"));
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
			content=content.replaceAll("#",Integer.toString(id));
			content=content.replaceAll("???", phase.toString());
			ctrl.requestLabel.setVisible(false);
			ctrl.requestLabel.setText(content);
			ctrl.requestLabel.setVisible(true);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	public void approveAction(ActionEvent e) {
		
		Alert alertWarning = new Alert(AlertType.INFORMATION);
        alertWarning.setTitle("Approve Extension Request Time Warning");
        alertWarning.setHeaderText("Are you sure about your Approve!");
        alertWarning.setContentText("Extension request will Approved!");
        Optional<ButtonType> result=alertWarning.showAndWait();
        ButtonType button=result.orElse(ButtonType.CANCEL);
        if(button==ButtonType.OK) {
        	
        	String keymessage = "send Request extension approve to Admin";
			//String d[] = {dueDateForExtend.getValue().toString(), ExtensionReasonText.getText().toString() };
			Object[] message = { keymessage, r.getId(), phase};
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	Alert alertWarning1 = new Alert(AlertType.CONFIRMATION);
			alertWarning1.setHeaderText("Success!");
			alertWarning1.setContentText("Request Extension has been approved successfully!");
			alertWarning1.showAndWait();
        }
		
		
		
		
		

		
	}
	public void RejectBtn(ActionEvent e) {
		
		
		
	
	    	Alert alertWarning = new Alert(AlertType.INFORMATION);
	        alertWarning.setTitle("Reject Extension Request Time Warning");
	        alertWarning.setHeaderText("Are you sure about your reject!");
	        alertWarning.setContentText("Extension request will reject!");
	        Optional<ButtonType> result=alertWarning.showAndWait();
	        ButtonType button=result.orElse(ButtonType.CANCEL);
	        if(button==ButtonType.OK) {
	        	Alert alertWarning1 = new Alert(AlertType.CONFIRMATION);
				alertWarning1.setHeaderText("Success!");
				alertWarning1.setContentText("Request Extension has been approved successfully!");
				alertWarning1.showAndWait();
	        }
	        
		
	}
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Object[] msg= {"evaluators",getClass().getName()};
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	


}
