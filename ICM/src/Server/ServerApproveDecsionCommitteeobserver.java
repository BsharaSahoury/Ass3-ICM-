package Server;

import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import ocsf.server.ConnectionToClient;

public class ServerApproveDecsionCommitteeobserver implements Observer {
	public ServerApproveDecsionCommitteeobserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg1 instanceof Object[]) {
			Object[] arg2=(Object[])arg1;
			ConnectionToClient client=(ConnectionToClient)arg2[0];
			if(arg2[1] instanceof Object[]) {
				Object[] arg3=(Object[])arg2[1];
				if(arg3[0] instanceof String) {
					String keymessage=(String)arg3[0];
					if(keymessage.equals("approve committee decision")) {
						int id=(int)arg3[1];
						Connection con=mysqlConnection.makeAndReturnConnection();
						String dec=(String)arg3[2];
						mysqlConnection.addRequestToDB(con,id,dec);
					}
				}
			}
		}
		
	}

}
