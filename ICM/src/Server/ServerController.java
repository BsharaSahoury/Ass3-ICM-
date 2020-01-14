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

			IP_txt.setText(InetAddress.getLocalHost().getHostAddress());//set the label of the IP in the window for the IP of the computer that the server runs on//
			IP=IP_txt.getText();
			IP_txt.setEditable(false);
			Port_txt.setText("5555");
			Port_txt.setEditable(false);
			dbHostname.setText("localhost:3306");
			dbHostname.setEditable(false);
			dbSchema.setText("icm");
			dbSchema.setEditable(false);
			dbUsername.setText("root");
			dbPassword.setText("Xd0509144223");

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
