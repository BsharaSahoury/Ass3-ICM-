package Boundary;

import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Request;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RequestTrackController implements Initializable{
	public static Stage primaryStage;
	private static ClientConsole cc;
	private AnchorPane lowerAnchorPane;
	 @FXML
	 private static SplitPane splitpane;
		public static RequestTrackController RequestTrack;

	public void start(SplitPane splitpane, Request r) {
		this.splitpane=splitpane;
		primaryStage=LoginController.primaryStage;
		this.cc=LoginController.cc;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/UserStatus.fxml"));		
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);
			String keymessage="Track request";
			int id = r.getId();
			Object[] message = { keymessage, id };
			LoginController.cc.getClient().sendToServer(message);
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	public void SetInfo(Request request) {
		// TODO Auto-generated method stub
		
	}

	
}
