package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Phase;
import Entity.RequestPhase;
import ocsf.server.ConnectionToClient;

public class ServerSetDuratinObserver  implements Observer {
	public ServerSetDuratinObserver(Observable server) {
		server.addObserver(this);
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				String keymessage = (String) arg3[0];
				if (keymessage.equals("save duration")) {
					int id=(int) arg3[1];
					String d[] = (String[]) arg3[2];
					Phase p=(Phase)arg3[3];
					Connection con = mysqlConnection.makeAndReturnConnection();
					System.out.println(id);
					System.out.println(d);
					System.out.println(p);
					mysqlConnection.insertDate(con,id,d,p);
					Object[] send = new Object[2];
					send[0] = "duration";
					send[1] = d;
					try {
						client.sendToClient(send);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
