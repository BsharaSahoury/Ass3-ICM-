package Boundary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * 
 * @author Ayman Odeh
 *
 */
public class LoginController {

private Stage primaryStage;
public void start(Stage primaryStage)  {
	this.primaryStage=primaryStage;
	try{			
			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Login.fxml"));
			Scene scene = new Scene(root,900,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());			
			this.primaryStage.setScene(scene);
			this.primaryStage.setResizable(false);
			this.primaryStage.setTitle("ICM");
			this.primaryStage.show();
			this.primaryStage.setOnCloseRequest( event ->
		    {
		        System.out.println("EXIT ICM");
		        System.exit(0);	
		    });			
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}

}
