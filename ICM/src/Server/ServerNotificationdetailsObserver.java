package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Notification;
import ocsf.server.ConnectionToClient;

public class ServerNotificationdetailsObserver implements Observer {
	public ServerNotificationdetailsObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Object[]) {
			Object[] arg2=(Object[])arg1;
			ConnectionToClient client=(ConnectionToClient)arg2[0];
			if(arg2[1] instanceof Object[]) {
				Object[] arg3=(Object[])arg2[1];
				String keymessage=(String)arg3[0];
				if(keymessage.equals("get explain notification")) {
					int notid=(int)arg3[1];
					Connection con=mysqlConnection.makeAndReturnConnection();
					String details=mysqlConnection.getnotificationdetails(con,notid);
					System.out.println(details);
					keymessage="notification details";
					Object[] message= {keymessage,details};
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
