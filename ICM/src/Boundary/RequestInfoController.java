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

public class RequestInfoController implements Initializable {
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	@FXML
	private TextField lbId;
	@FXML
	private TextField lbSystem;
	@FXML
	private TextField lbSituation;
	@FXML
	private TextField lbChange;
	@FXML
	private TextField lbComment;
	@FXML
	private TextField InitiatorName;
	@FXML
	private TextField InitiatorRole;
	@FXML
	private TextField Date;
	@FXML
	private TextField Email;
	@FXML
	private static SplitPane splitpane;

	private static ClientConsole cc;

	public static RequestInfoController Requestinfo;

	public void start(SplitPane splitpane, Request s) {
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		this.splitpane = splitpane;
		String keymessage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/RequestInfo.fxml"));
			lowerAnchorPane = loader.load();
			Requestinfo = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			keymessage = "Request Info";
			int id = s.getId();
			Object[] message = { keymessage, id };
			LoginController.cc.getClient().sendToServer(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SetInfo(Request r) {
		InitiatorName.setText(r.getInitiatorName());
		InitiatorRole.setText(r.getInitiatorRole());
		Date.setText(r.getDate().toString());
		Email.setText(r.getInitiatorEmail());
		lbSystem.setText(r.getPrivilegedInfoSys());
		lbId.setText(Integer.toString(r.getId()));
		lbSituation.setText(r.getExistingSituation());
		lbChange.setText(r.getExplainRequest());
		lbComment.setText(r.getComment());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
