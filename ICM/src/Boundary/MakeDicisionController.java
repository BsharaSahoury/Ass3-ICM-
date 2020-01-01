package Boundary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MakeDicisionController {
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;

	@FXML
	private static SplitPane splitpane;

	public void start(SplitPane splitpane) {
		primaryStage = LoginController.primaryStage;
		this.splitpane = splitpane;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/DecisionCommitteMember.fxml"));
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
