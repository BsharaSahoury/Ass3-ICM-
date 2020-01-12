package Server;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;



public class ServerController implements Initializable {

	private static String db_Password;
	private static String db_Username;
	private static String IP;
	@FXML
	private TextField IP_txt;	
	@FXML
	private TextField Port_txt;	
	@FXML
	private TextField dbHostname;
	@FXML
	private TextField dbSchema;	
	@FXML
	private TextField dbUsername;	
	@FXML
	private TextField dbPassword;	
	@FXML
	private Button connectBtn;
	@FXML
	Label wrong;
	
	 @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
	    	editTextFeilds();
		}
	 
	 public void editTextFeilds()  {
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
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public static String getIp() {
		 return IP;
	 }
	 public static String get_DB_Password() {
		 return db_Password;
	 }
	 public static String get_DB_UserName() {
		 return db_Username;
	 }
	 @FXML
	 private void connecting(ActionEvent event) {
	    	db_Password=dbPassword.getText();
	    	db_Username=dbUsername.getText();
	    	if((!(dbPassword.getText().equals("")))&&(!(dbUsername.getText().equals(""))))
	    		{
	    		wrong.setText("Connection established");
				wrong.setVisible(true);	    
				dbUsername.setEditable(false);
				dbPassword.setEditable(false);
	    		connectBtn.setDisable(true);
	    		MainForServer.ConnectAfterDBPassword();
	    		}
	    	else
	    	{
	    		wrong.setText("wrong MySQL Username OR Password, please try again!");	    		
				wrong.setVisible(true);
	    	}
	    }
	public static class NewHandler implements Thread.UncaughtExceptionHandler {
			public NewHandler() {}
		    @Override
		    public void uncaughtException(Thread t, Throwable e) {
		        // do something here - log to file and upload to    server/close resources/delete files...
		    }//uncaughtException
	}//NewHandler
	 @SuppressWarnings("deprecation")
	public static void shutdown() throws IOException {
	        // cleanup code here...
		 try {
	        System.out.println("StopAll");
	        if(MainForServer.get_ObservableServer().isListening())
	        	{
	        	MainForServer.get_ObservableServer().stopListening();
	        	MainForServer.get_ObservableServer().close();
	        	Thread [] clients =MainForServer.get_ObservableServer().getClientConnections();
	        	for(Thread client : clients)
	        	{
	        		client.stop();
	        		NewHandler eh=new NewHandler();
	        		client.setUncaughtExceptionHandler(eh);
	        	}//for
		 }//if
		 }//try
		 catch(ClassCastException e1) {}
	    }
	 
}
