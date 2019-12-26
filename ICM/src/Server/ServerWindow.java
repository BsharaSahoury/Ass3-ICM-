package Server;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Server.MainServer;
public class ServerWindow extends Application {

	private static MainServer ms;
	@Override
	public void start(Stage primaryStage) {	//create the server's window//
		try {		
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/Server/Server.fxml").openStream());
			Scene scene = new Scene(root,400,320);		
			scene.getStylesheets().add(getClass().getResource("/Server/Server.css").toExternalForm());	
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("ICM-Server");
			primaryStage.show();
			primaryStage.setOnCloseRequest( event ->
		    {
		    	
		        System.out.println("Server Exit");
		        System.exit(0);
		        try {
					ms.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    });
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void launchMain(MainServer sv, String[] args) {//launching the server's application//
		// TODO Auto-generated method stub
		ServerWindow.ms=sv;
		launch(args);
	}
	
}
