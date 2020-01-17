package Boundary;

import java.net.URL;
import java.util.ResourceBundle;
import Client.ConnectClient;
import Client.ClientConsole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * 
 * @author Ayman odeh
 * This class is the controller for the ConnectClient GUI
 */
public class ClientConSerController implements Initializable {
	@FXML
	private TextField Iptxt;
	@FXML
	private Button ConnectBtn;
	private Stage stage;
    /**
     * //this is the method that will be called upon pressing 'connect'//
     * @param event When the client pressed connect this function will be called
     * @throws Exception
     */
	public void connecttoserver(ActionEvent event) throws Exception {
	String IP = Iptxt.getText();
	System.out.print(IP);
	 if(IP!=null) {	
		 /**
		  * We called the connecttoIP function in the ClientConsole to connect to the server
		  */
			ClientConsole.connecttoIP(IP,stage);
		/**
	     * we hide this window in case the connection was a success
		*/
			((Node)event.getSource()).getScene().getWindow().hide();
			LoginController login=new LoginController();
			login.start(stage);
		}	
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stage=ConnectClient.getstage();
	}

}
