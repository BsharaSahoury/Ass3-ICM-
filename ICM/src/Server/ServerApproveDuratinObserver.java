package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Phase;
import Entity.State;
import ocsf.server.ConnectionToClient;

public class ServerApproveDuratinObserver implements Observer{
	public ServerApproveDuratinObserver(Observable server) {
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
				if (keymessage.equals("ispector duration")) {
					System.out.println("55555555555");
					int id = (int) arg3[1];
					String d[] = (String[]) arg3[2];
					Phase p = (Phase) arg3[3];
					State s=(State) arg3[4];
					Connection con = mysqlConnection.makeAndReturnConnection();
					boolean b = mysqlConnection.insertDate(con, id, d, p);
					Object[] send = new Object[2];
					send[0] = "approvedDuration";
					System.out.println("00000000000");
					if (b == false) {
						System.out.println("1111111111");
						send[1] = false;
					} else {
						send[1] = d;
						mysqlConnection.changeState(con,id,p,s);
					}
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
