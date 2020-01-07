package Boundary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.RequestPhase;
import Entity.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MakeDicisionController implements Initializable {
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	private static ClientConsole cc;
    private FXMLLoader loader;;
	@FXML
	private static SplitPane splitpane;
    @FXML
    private RadioButton Approve;
    @FXML
    private RadioButton Reject;
    @FXML
    private RadioButton AdditionalInfo;
    @FXML
    private TextArea ExplainDectxt;
    private RequestPhase selected;
	public void start(SplitPane splitpane,RequestPhase selected,User user) {
		primaryStage = LoginController.primaryStage;
		this.selected=selected;
		this.cc = LoginController.cc;
		this.splitpane = splitpane;
		try {
			loader = new FXMLLoader(getClass().getResource("/Boundary/DecisionCommitteMember.fxml"));
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	public void VisibleExplain(ActionEvent e) {
		ExplainDectxt.setDisable(false);
	}
	
	public void SendToChairMan(ActionEvent e) { 
		if(!Approve.isSelected()&&!Reject.isSelected()&&!AdditionalInfo.isSelected()) {
			 Alert alertWarning = new Alert(AlertType.WARNING);
		     alertWarning.setTitle("Warning Alert Title");
		     alertWarning.setHeaderText("Warning!");
		     alertWarning.setContentText("Please Select your decision");
		     alertWarning.showAndWait();
		}
		else if(ExplainDectxt.getText().equals("")) {
			 Alert alertWarning = new Alert(AlertType.WARNING);
		     alertWarning.setTitle("Warning Alert Title");
		     alertWarning.setHeaderText("Warning!");
		     alertWarning.setContentText("Please fill Explain about the decision");
		     alertWarning.showAndWait();
		}
		else{
			String[] Message=new String[4];
			Message[0]="Committee Member Decision";
			if(Approve.isSelected())
			Message[1]="approve";
			else if(Reject.isSelected())
			Message[1]="reject";	
			else
			Message[1]="ask for additional Information";
			Message[2]=ExplainDectxt.getText();
			Message[3]=Integer.toString(RequestsWorkedOnController.decision.selected.getR().getId());
			try {
				cc.getClient().sendToServer(Message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ExplainDectxt.setDisable(true);
	}
}
