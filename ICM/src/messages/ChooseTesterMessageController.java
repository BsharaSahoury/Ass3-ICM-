package messages;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Boundary.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChooseTesterMessageController implements Initializable {
	@FXML
	Label label1;
	@FXML
	ComboBox<String> combo;
	@FXML
	Button recruit;
	
	public static ChooseTesterMessageController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;
	private ObservableList<String> list;
	private int requestID;
	public void start(SplitPane splitpane,int id) {
		primaryStage=LoginController.primaryStage;
		this.requestID=id;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/ChooseTester-message.fxml"));
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
			ctrl.label1.setVisible(false);
			ctrl.label1.setText("Performance phase is complete for request#"+requestID+".");
			ctrl.label1.setVisible(true);
			
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Object[] msg= {"comittee members",getClass().getName()};
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	public void recruitAction(ActionEvent e) {
		
	}
	public void fillCombo(ArrayList<String> names) {
		list=FXCollections.observableArrayList(names);
		combo.setItems(list);
	}

}
