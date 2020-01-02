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

	public void start(SplitPane splitpane, boolean flag) {
		FXMLLoader loader;
		primaryStage = LoginController.primaryStage;
		this.splitpane = splitpane;
		System.out.println(flag);
		try {
			if (flag == false)// committee member
			{
				loader = new FXMLLoader(getClass().getResource("/Boundary/DecisionCommitteMember.fxml"));
			} else // chairman
			{
				loader = new FXMLLoader(getClass().getResource("Decision For request by Cairman_Final.fxml"));
			}
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
