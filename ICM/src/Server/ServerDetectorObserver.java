package Server;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import Entity.RequestPhase;

public class ServerDetectorObserver implements Observer {
	public ServerDetectorObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Connection con=MainForServer.con;
		long millis=System.currentTimeMillis();
		Date today=new java.sql.Date(millis);
		mysqlConnection.getToWork(con,today);
		ArrayList<RequestPhase> RPlist=mysqlConnection.detectExceptions(con,today);
		for(RequestPhase rp : RPlist) {
			Notification n1=new Notification("Exception in request#"+rp.getId()+" in phase "+rp.getPhase().toString()+".",today,"Exception message");
			n1=mysqlConnection.insertNotificationToDB(con, n1);
			Employee inspector=mysqlConnection.getInspector(con);
			Employee admin=mysqlConnection.getAdmin(con);
			Employee phase_administrator=mysqlConnection.getPhaseAdministrator(con,rp.getId(),rp.getPhase(),rp.getRepetion());
			mysqlConnection.insertNotificationForUserToDB(con, n1, inspector);
			mysqlConnection.insertNotificationForUserToDB(con, n1, admin);
			mysqlConnection.insertNotificationForUserToDB(con, n1, phase_administrator);
		}
		ArrayList<RequestPhase> RPlist1=mysqlConnection.findWhatNeedsDocument(con,today);
		for(RequestPhase rp : RPlist1) {
			Notification n2=new Notification("Request#"+rp.getId()+": phase "+rp.getPhase()+"is over with an exception, needs documentation.",today,"Exception document");
			Employee inspector=mysqlConnection.getInspector(con);
			n2=mysqlConnection.insertNotificationToDB(con, n2);
			mysqlConnection.insertNotificationForUserToDB(con, n2, inspector);
		}
		
		
	}

}
