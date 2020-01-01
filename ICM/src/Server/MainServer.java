package Server;

import java.sql.Connection;


import java.util.ArrayList;
import DBconnection.mysqlConnection;
import Entity.Request;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import ocsf.server.ObservableServer;

import java.io.*;


public class MainServer{
	final public static int DEFAULT_PORT = 5555;
	private static ObservableServer server;
	   public MainServer(int port) {
		   server=new ObservableServer(port);
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
	  
	
	/*@Override
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
	}*/
}
