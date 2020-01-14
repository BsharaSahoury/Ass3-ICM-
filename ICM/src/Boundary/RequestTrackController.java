package Boundary;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Request;
import Entity.RequestPhase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RequestTrackController implements Initializable {
	public static Stage primaryStage;
	private static ClientConsole cc;
	private AnchorPane lowerAnchorPane;
	@FXML
	private static SplitPane splitpane;
	@FXML
	private TextField requeststatus;
	@FXML
	private TextField requestPhase;
	@FXML
	private TextField subDate;
	@FXML
	private TextField reamining;
	@FXML
	private TextField dueDate;
	@FXML
	
	private TextField currentDate;

	public static RequestTrackController RequestTrack;

	public void start(SplitPane splitpane, Request r) {
		this.splitpane = splitpane;
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/UserStatus.fxml"));
			lowerAnchorPane = loader.load();
			RequestTrack = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			String keymessage = "Track request";
			int id = r.getId();
			Object[] message = { keymessage, id };
			LoginController.cc.getClient().sendToServer(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void SetTrack(RequestPhase rp) {
		requeststatus.setText(rp.getR().getStatus());
		requestPhase.setText(rp.getCurrentPhase().toString());
		subDate.setText(rp.getDate().toString());
		long millis = System.currentTimeMillis();
		Date date = new java.sql.Date(millis);
		currentDate.setText(date.toString());
		if(rp.getDueDate()!=null)
		{
			dueDate.setText(rp.getDueDate().toString());
			long diff = rp.getDueDate().getTime() - date.getTime();
			long diffdays = diff / (24 * 60 * 60 * 1000);
			long diffHours = diff / (60 * 60 * 1000) - (diffdays * 24);
			reamining.setText(String.valueOf(diffdays) + " Days and " + String.valueOf(diffHours) + " Hours");
		}
		else {
			dueDate.setText("not defined");
			reamining.setText("not defined");
		}
		
		
		
		

	}

}