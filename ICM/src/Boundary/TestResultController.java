package Boundary;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestResultController implements Initializable{
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	private static SplitPane splitpane;
	public void start(SplitPane splitpane)  {
		primaryStage=LoginController.primaryStage;
		this.splitpane=splitpane;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Testresults.fxml"));
			lowerAnchorPane = loader.load();

			splitpane.getItems().set(1, lowerAnchorPane);	

		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
