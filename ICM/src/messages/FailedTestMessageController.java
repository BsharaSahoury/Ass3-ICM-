package messages;
import java.io.IOException; 
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
import Entity.User;
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

public class FailedTestMessageController implements Initializable {
	@FXML
	Label requestLabel;
	@FXML
	Label evaluatorLabel;
	
	@FXML
	Button other;
	@FXML
	ComboBox<String> combo;
	
	public static FailedTestMessageController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;
	private int requestID;
	private ObservableList<String> list;
	public void start(SplitPane splitpane,int id) {
		this.requestID=id;
		primaryStage=LoginController.primaryStage;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/FaildTest-message.fxml"));
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
			ctrl.requestLabel.setVisible(false);
			ctrl.requestLabel.setText("Request with id #"+id+", Test Failed");
			ctrl.requestLabel.setVisible(true);
			/*ctrl.evaluatorLabel.setVisible(false);
			ctrl.evaluatorLabel.setText("Choose Performance: "+fullname+" will be recruited automatically to evaluate the request.would you like to approve?");
			ctrl.evaluatorLabel.setVisible(true);
			ctrl.requestID=id;
			this.fullname=fullname;*/
			
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	
	
	
	
	/*public void RecruitAction(ActionEvent e) {
		Object[] message= {"automatic",requestID};
		try {
			LoginController.cc.getClient().sendToServer(message);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}*/
	
	
	
	public void RecruitAction(ActionEvent e) {
		String fullname=combo.getSelectionModel().getSelectedItem();
		if(fullname==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("ERROR");
	        alert.setContentText("please choose an Inspector");
	        alert.showAndWait();
	        return;
		}
		Object[] msg= {"Performance leaders"};
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Object[] msg= {"Inspectors"};
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public void fillCombo(ArrayList<String> names) {
		list=FXCollections.observableArrayList(names);
		combo.setItems(list);
		
	}
	
	

}

/*import java.util.ArrayList;

import Boundary.AdministratorHomeController;
import Boundary.AllRequestsController;
import Boundary.InspectorHomeController;
import Boundary.LoginController;
import Entity.RequestPhase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FailedTestMessageController {
    
	@FXML
	private ComboBox performanceList;
	@FXML
	ComboBox<String> combo;
	
	
	
	
	public static FailedTestMessageController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;
	
	

	public void start(SplitPane splitpane,int id) {
		primaryStage=LoginController.primaryStage;
		try{	
			System.out.println("okay2");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/FaildTest-message.fxml"));
			System.out.println("okay3");
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
			//ctrl.label1.setVisible(false);
			//ctrl.label1.setText("you've been recruited to work on request#"+id);
		    //ctrl.label1.setVisible(true);
			
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	


}
*/
