package Client;

import java.io.IOException;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
/**
 * 
 * @author Ayman Odeh
 *
 */
public class ConnectClient extends Application{
	
	private static Stage primaryStage;
	public static void main(String[] args)  {
		launch(args);
	}
	/**
	 * 
	 * @return the primary stage of the user
	 */
	public static Stage getstage() {
		return primaryStage;
	}
	/**
	 * In this tart function we create the first GUI to the client
	 * So the client could connect to the server
	 */
	@Override
	public void start(Stage primaryStage) throws IOException  {
		this.primaryStage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Server Configure.fxml"));	
		Scene scene = new Scene(root,900,600);
		//scene.getStylesheets().add(getClass().getResource("/Boundary/Connect.css").toExternalForm());
		this.primaryStage.setScene(scene);
		this.primaryStage.setResizable(false);		
		this.primaryStage.setTitle("ICM-Connect");
		this.primaryStage.show();
		this.primaryStage.setOnCloseRequest( event ->
	    {
	        System.out.println("EXIT ICM");
	        System.exit(0);	
	    });
	}
}
