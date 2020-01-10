package Client;

import java.awt.Button;
import java.awt.TextField;

import Boundary.MainClientController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainForClient extends Application {
	public static Stage stage;
	public static  String[] args;


	public static void main(String[] args) {
		MainForClient.args=args;
		System.out.println(args.toString());
		MainForClient.launch(args);
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("/Boundary/MainClient.fxml"));
		Scene scene = new Scene(root,600,400);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);		
		primaryStage.setTitle("MainClient");
		primaryStage.show();
		stage=primaryStage;
		
		
	}

}
