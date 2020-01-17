package Client;
import java.io.IOException;


import java.util.ArrayList;
import java.util.Optional;
//import application.RequestInfoController;
//import application.RequestsController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
//import logic.Request;
import ocsf.client.*;

public class ICMClient extends AbstractClient  {
	ClientConsole clientUI;
	//RequestsController rc;
	//RequestInfoController rc2;
	//public static ArrayList<Request> arr1; 
    /**
     * We set the client console of this object to be the same as one who called the constuctor
     * @param host here is the IP of the server that we want to connect
     * @param port
     * @param clientUI we save the client console for future using
     * @throws IOException
     */
	public ICMClient(String host,int port,ClientConsole clientUI) throws IOException {
		super(host,port);
		this.clientUI=clientUI;
		try {
			if(host.equals(""))
				throw new Exception();
		openConnection();
		}catch(Exception e){//we display an error message if the connection failed//
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Exception Dialog");
			alert.setHeaderText("error");
			alert.setContentText("Incorrect IP");
			alert.setGraphic(null);
			alert.showAndWait();
			Optional<ButtonType> result = alert.showAndWait();
			result.ifPresent(btnType -> {
				clientUI.getstage().hide();
			});
			if(result.get() == ButtonType.OK) {	
				clientUI.getstage().hide();
			}
			
		}			
	}
	
	@Override
	protected void handleMessageFromServer(Object msg) {
		// TODO Auto-generated method stub
		
	}
/*
	@Override
	public void handleMessageFromServer(Object msg) {
		if(msg instanceof ArrayList<?>) {		//if it's an arrayList then it's an arrayList of Request and we'll have to insert the data in the window table//
			arr1=(ArrayList<Request>)msg;
		rc.InsertDataToTable(arr1);}
		// TODO Auto-generated method stub	
	}
	
	//this a message from the console in case the client wants to open the table's window
	public void handleMessageFromClientUI(Object msg,RequestsController rc) {
		this.rc=rc;
		try {		
			sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	/*
    //this is a message from the console in case the client wants to update a status of a request
	public void handleMessageFromClientUI2(Object msg,RequestInfoController rc2) {
		try {				
			sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

	}*/
	/*
	//here we notify the client in case the connection is closed for some reason//
	@Override
	public void connectionClosed() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception Dialog");
		alert.setHeaderText("error");
		alert.setContentText("Server Disconnected");
		Optional<ButtonType> result = alert.showAndWait();	
		if(result.get() == ButtonType.OK) {
			System.exit(0);
		}
		alert.initStyle(StageStyle.UTILITY);
	}*/
	
}
