package Server;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.MyFile;
import javafx.scene.image.Image;
import ocsf.server.ConnectionToClient;

public class ServerUpdatingImageProfileSettingObserver implements Observer {
	public ServerUpdatingImageProfileSettingObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		Object[] args = null;
		if(arg instanceof Object[]) {
		args=(Object[])arg;
		ConnectionToClient client=(ConnectionToClient)args[0];
		if(args[1] instanceof String[]) {
			String msg;
			Object[] Message=(Object[])args[1];
			if(Message.length==5 ) 
				if(Message[0] instanceof String)
				{
				msg=(String)Message[0];
				if(Message[0].equals("UpdatingImageOfProfileSetting")) {
				String username,job,namePic;
				Image pic;
				if(Message[1] instanceof String && Message[2] instanceof String && Message[3] instanceof String && Message[4] instanceof Image)
				username=(String)Message[1];
				job=(String)Message[2];
				namePic=(String)Message[3];
				pic=(Image)Message[4];
				FileOutputStream fos = new FileOutputStream(namePic);
			    BufferedOutputStream bos = new BufferedOutputStream(fos);
			    bos.write(pic.getMybytearray() , 0 , fileSize);
			    bos.flush();
				System.out.println("UpdatingImageOfProfileSetting**********\n\n***************");
				Connection con=mysqlConnection.makeAndReturnConnection();
				ArrayList<String> arr=mysqlConnection.getUserData(con,Message[1],Message[2]);
				Object[] send=new Object[3];
				send[0]="ProfileSetting";
				send[1]=arr;//profile setting of user
				send[2]=Message[2];//Job of user
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
}

