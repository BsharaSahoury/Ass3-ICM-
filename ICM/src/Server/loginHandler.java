package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.User;
import ocsf.server.ConnectionToClient;

public class loginHandler implements Observer {
	public loginHandler(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		Object[] args = null;
		if(arg instanceof Object[]) {
		args=(Object[])arg;
		ConnectionToClient client=(ConnectionToClient)args[0];
	
		if(args[1] instanceof String[]) {
			String[] Message=(String[])args[1];
			if(Message.length==3 && Message[0].equals("login")) {
				Connection con=mysqlConnection.makeAndReturnConnection();
				
				User user=mysqlConnection.isInDB(con, Message[1], Message[2]);
				
				try {
					client.sendToClient(user);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		}
	}

}
