package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Request;
import Entity.User;
import ocsf.server.ConnectionToClient;

public class serverSubmissionObserver implements Observer {
	public serverSubmissionObserver(Observable server) {
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
					if(keymessage.equals("submitRequest")) {
						Request newRequest=(Request)arg3[1];
						Connection con=mysqlConnection.makeAndReturnConnection();
						boolean flag=mysqlConnection.insertRequestToDB(con,newRequest);
						if(flag) {
							keymessage="sumbissionSucceeded";
							Object[] message= {keymessage};
							try {
								client.sendToClient(message);
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

}
