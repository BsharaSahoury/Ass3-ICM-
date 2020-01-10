package Server;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ServerController implements Initializable {
	@FXML
	private  Label IpLable;
	private static String IP;
	 @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
	    	editLabels();
		}
	 
	 public void editLabels()  {
		 try {
			IpLable.setText(InetAddress.getLocalHost().getHostAddress());//set the label of the IP in the window for the IP of the computer that the server runs on//
			IP=IpLable.getText();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public static String getIp() {
		 return IP;
	 }
	 
}
