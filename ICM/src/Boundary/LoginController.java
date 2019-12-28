package Boundary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * 
 * @author Ayman Odeh
 *
 */
public class LoginController {
@FXML
private Button Loginbtn;
@FXML
private TextField Username;
@FXML
private TextField Password;
@FXML
private CheckBox Remember;
@FXML
private Button ForgetPass;
public static Stage primaryStage;
public void start(Stage primaryStage)  {
	this.primaryStage=primaryStage;
	try{			
			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Login.fxml"));
			Scene scene =new Scene(root);		
			this.primaryStage.setScene(scene);
			this.primaryStage.setResizable(false);
			this.primaryStage.setTitle("ICM-Login");
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
/**
 * 
 * @param event if the user clicked on loging button
 * @throws Exception
 */
public void LoginAction(ActionEvent event) throws Exception{
	System.out.println("Sssssss");
	//if(Username.getText().equals("")) {
		//show message username is empty
	//}
	//else if(Password.getText().equals("")) {
		//show message password is empty
	//}
	//else if(Remember.isSelected()) {
		//save the username and the password
	//}
	//else {
		//go to database and check if the username and password are exist	
		//if(true) {//if the username and password exist in database
	//	if(Remember.isSelected()) {
			//save the username and the password
		//}
		System.out.println("Ssxxxxxxsssss");
	//	((Node)event.getSource()).getScene().
		((Node)event.getSource()).getScene().getWindow().hide();
		HomeController home =new HomeController();
		
		home.start();
		//}
		//else {
		//show message username or password is incorrect	
		//}
	//}	
}
/**
 * 
 * @param event if the user clicked on Forget Password button
 * @throws Exception
 */
public void ForgetPassAction(ActionEvent event) throws Exception{
	System.out.println("sssssss");
	//open new window to get the password by enter the email address and then send link to reset the password
}
}
