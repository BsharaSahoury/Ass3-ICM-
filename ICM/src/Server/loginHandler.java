package Server;

import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.User;

public class loginHandler implements Observer {
	public loginHandler(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof String[]) {
			String[] Message=(String[])arg;
			if(Message.length==3 && Message[0].equals("login")) {
				Connection con=mysqlConnection.makeAndReturnConnection();
				User user=mysqlConnection.isInDB(con, Message[1], Message[2]);
				
			}
		}
		
	}

}
