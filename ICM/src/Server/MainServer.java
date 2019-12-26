package Server;

import java.sql.Connection;

import java.util.ArrayList;
import DBconnection.mysqlConnection;
import Entity.Request;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import java.io.*;


public class MainServer extends AbstractServer {
	final public static int DEFAULT_PORT = 5555;
	private ArrayList<Request> arr=null;	
	   public MainServer(int port) {
		   super(port);
	  }
	   
	  public static void main(String[] args) 
	  {
	    int port = 0; //Port to listen ons
	    try
	    {    
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }		
	    MainServer sv = new MainServer(port);	   
	      try {
		   sv.listen();
		   ServerWindow.launchMain(sv, args);//we launch the server's window//
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Start listening for connections	  	
	  }
	  
	 protected void serverStarted()
	  {
	    System.out.println("Server listening for connections on port " + getPort());
	  }
	 
	 protected void serverStopped()
	  {
		 try {
			close();
			 System.out.println("Server has stopped listening for connections.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	   
	  }
	  
	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {		
		
		if(msg instanceof String) {
			String me=(String)msg;
		if(me.equals("giveTableData")) {//if the message is 'giveTableData' then the client wants to fill in the table in his application//
		Connection con=mysqlConnection.makeAndReturnConnection();//this function creates and returns a Connection to DB//
		arr=mysqlConnection.getDataFromDB(con);
		}
		}
		else if(msg instanceof Request) {		//if the object is Request then it means that the client wants to update the status of that request//
			Connection con=mysqlConnection.makeAndReturnConnection();
			Request chosen = (Request)msg;	
			mysqlConnection.UpdateUserInDB(chosen,con);//this function updates the status of the request chosen in DB
			for(Request r : arr) {
				if(r.getId().equals(chosen.getId())){
					int i=arr.indexOf(r);
					arr.set(i, chosen);
				}
			}
		}		
		try {	
			client.sendToClient(arr);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
