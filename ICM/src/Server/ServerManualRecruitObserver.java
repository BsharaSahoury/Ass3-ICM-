package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import ocsf.server.ConnectionToClient;

public class ServerManualRecruitObserver implements Observer {
	public ServerManualRecruitObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Object[]) {
			Object[] arg2=(Object[])arg1;
			ConnectionToClient client=(ConnectionToClient)arg2[0];
			if(arg2[1] instanceof Object[]) {
				Object[] arg3=(Object[])arg2[1];
				if(arg3[0] instanceof String) {
					String keymessage=(String)arg3[0];
					if(keymessage.equals("manualEvaluator")) {
						String fullname=(String)arg3[1];
						int id=(int)arg3[2];
						Connection con=mysqlConnection.makeAndReturnConnection();
						Employee evaluator=mysqlConnection.getSpecificEmployee(con,fullname);
						boolean flag=mysqlConnection.assignEmployeeToPhaseRequest(con, evaluator, id,"evaluation");
						if(flag) {
							Object[] message= {"evaluatorRecruit"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							long millis=System.currentTimeMillis();
							Notification n1=new Notification(
									"You've been recruited to evaluate request#"+id,
									new java.sql.Date(millis),
									"recruitNotificationForEvaluator");
							n1=mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertNotificationForUserToDB(con, n1,evaluator);
						}	
					}
					else if(keymessage.equals("manualPerformer")) {
						String fullname=(String)arg3[1];
						int id=(int)arg3[2];
						Connection con=mysqlConnection.makeAndReturnConnection();
						Employee performer=mysqlConnection.getSpecificEmployee(con,fullname);
						boolean flag=mysqlConnection.assignEmployeeToPhaseRequest(con, performer, id,"performance");
						if(flag) {
							Object[] message= {"performerRecruit"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							long millis=System.currentTimeMillis();
							Notification n1=new Notification(
									"You've been recruited to Lead performance phase for request#"+id,
									new java.sql.Date(millis),
									"recruitNotificationForPerformance");
							n1=mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertNotificationForUserToDB(con, n1,performer);
						}
					}
					else if(keymessage.equals("manualEvaluatorAgain")) {
						String fullname=(String)arg3[1];
						int id=(int)arg3[2];
						Connection con=mysqlConnection.makeAndReturnConnection();
						Employee evaluator=mysqlConnection.getSpecificEmployee(con,fullname);
						boolean flag=mysqlConnection.assignEmployeeToPhaseRequest(con, evaluator, id,"evaluation");
						if(flag) {
							Object[] message= {"evaluatorRecruitAgain"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							long millis=System.currentTimeMillis();
							Notification n1=new Notification(
									"You've been recruited to evaluate request#"+id,
									new java.sql.Date(millis),
									"recruitNotificationForEvaluator");
							n1=mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertNotificationForUserToDB(con, n1,evaluator);
						}
					}
					else if (keymessage.equals("manualTester")) {
						String fullname=(String)arg3[1];
						int id=(int)arg3[2];
						System.out.println(id+"             111111111111");
						Connection con=mysqlConnection.makeAndReturnConnection();
						Employee tester=mysqlConnection.getSpecificEmployee(con,fullname);
						boolean flag=mysqlConnection.assignEmployeeToPhaseRequest(con, tester, id,"testing");
						if(flag) {
							Object[] message= {"testerRecruit"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							long millis=System.currentTimeMillis();
							Notification n1=new Notification(
									"You've been recruited to evaluate request#"+id,
									new java.sql.Date(millis),
									"recruitNotificationForEvaluator");
							n1=mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertNotificationForUserToDB(con, n1,tester);
						}
					}
					
				}
			}
		}
		
	}

}
