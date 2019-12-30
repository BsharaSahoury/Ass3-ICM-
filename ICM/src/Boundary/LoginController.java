package Boundary;

import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Client.MainForClient;
import Entity.Employee;
import Entity.Inspector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;
/**
 * 
 * @author Ayman Odeh
 *
 */
public  class LoginController {
@FXML
private Label error;
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

private static ClientConsole cc;
public static  Stage primaryStage;

public void start(Stage primaryStage,ClientConsole cc)  {
	this.cc=cc;
	try{	

			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/login.fxml"));

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
	error.setVisible(false);
	String username=Username.getText();
	String password=Password.getText();
	String[] loginMessage=new String[3];
	loginMessage[0]="login";
	loginMessage[1]=username;
	loginMessage[2]=password;
	cc.getClient().sendToServer(loginMessage);
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
public void displayErrorMessage() {
	error.setVisible(true);
	Username.clear();
	Password.clear();
}
public Stage getStage() {
	return primaryStage;
}


}
