package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import ocsf.server.ConnectionToClient;

public class ServerGetEvaluatorsObserver implements Observer {
	public ServerGetEvaluatorsObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Object[]) {
			Object[] arg1=(Object[])arg;
			ConnectionToClient client=(ConnectionToClient)arg1[0];
			if(arg1[1] instanceof Object[]) {
				Object[] arg2=(Object[])arg1[1];
				if(arg2[0] instanceof String) {
					String keymessage=(String)arg2[0];
					if(keymessage.equals("evaluators")) {
						Connection con=mysqlConnection.makeAndReturnConnection();
						ArrayList<Employee> evaluators=mysqlConnection.getEvaluators(con);
						Object[] msg= {keymessage,evaluators};
						try {
							client.sendToClient(msg);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}

}
