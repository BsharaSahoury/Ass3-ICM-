package Boundary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Request;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreateEvaluationReportController implements Initializable{
	@FXML
	private TextField lbId;
	@FXML
	private TextField Location;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	private static ClientConsole cc;
	 private int chosenindex;
	 private Request chosenRequest;
	public static CreateEvaluationReportController evaluationReport;
	
	
	public void start(SplitPane splitpane, int id) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Evaluator-EvaluationReport.fxml"));
			lowerAnchorPane = loader.load();
			evaluationReport = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chosenindex=RequestsWorkedOnController.getselectedindex();
        chosenRequest=RequestsWorkedOnController.getList().get(chosenindex);   
		lbId.setText(Integer.toString(chosenRequest.getId())); 
		Location.setText(chosenRequest.getPrivilegedInfoSys());
	}

}
