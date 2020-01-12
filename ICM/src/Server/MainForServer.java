package Server;

import java.io.IOException;
import java.sql.Connection;

import DBconnection.mysqlConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ocsf.server.ObservableServer;
 
public class MainForServer extends Application {
	public static boolean CONNECTED_STATUS;
	private static Stage ServerStage;
	private static ObservableServer sv;
	private static Connection conn = null;


	  public static void main(String[] args) {
				launch(args);
	  }
	public static ObservableServer get_ObservableServer() {
			 return sv;
		 }
	public static Connection get_Connection() {
		 return conn;
	 }
	public static void set_Connection(Connection con1) {
		  conn=con1;
	 }	
	  public static void ConnectAfterDBPassword()
	  {
		  conn=mysqlConnection.makeAndReturnConnection();
	    int port = 0; //Port to listen ons
	    try
	    {    
	      port = 5555;
	    }
	    catch(Throwable t)
	    {
	      port = 5555; //Set port to 5555
	    }		
	    sv = new ObservableServer(port);
	    ServerObserver so=new ServerObserver(sv);
	    loginHandler loginHandler=new loginHandler(sv);
	    serverSubmissionObserver sso=new serverSubmissionObserver(sv);
	    ServerAllRequestsObserver serverallrequestobserver=new ServerAllRequestsObserver(sv);
	    
	      try {
		   sv.listen();
		   //ServerWindow.launchMain(sv, args);//we launch the server's window//
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Start listening for connections	  	
	  }
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Server/Server_Configure.fxml"));

	//		Parent root = FXMLLoader.load(getClass().getResource("/Server/Server_Configure.fxml"));
			Parent root = loader.load();
			ServerController controller = loader.getController();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);		
			primaryStage.setTitle("ICM-Server");
			primaryStage.show();
			this.ServerStage=primaryStage;
			ServerStage.setOnHidden(e -> {
				 try {
					controller.shutdown();
				    Platform.exit();
				} catch (IOException e1) {}
				 catch(NullPointerException e2) {}
				 
			});
	} catch(Exception e) {
		e.printStackTrace();
	}
	}


}
