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
		if(args[1] instanceof Object[]) {
			String msg;
			Object[] Message=(Object[])args[1];
			if(Message.length==4 ) 
				if(Message[0] instanceof String)
				{
				msg=(String)Message[0];
				if(msg.equals("UpdatingImageOfProfileSetting")) {
				String username = null,job;
				MyFile file;
				//Image pic;
				if(Message[1] instanceof String && Message[2] instanceof String && Message[3] instanceof MyFile )
				username=(String)Message[1];
				file=(MyFile)Message[3];
				//pic=(Image)Message[4];
				Connection con = mysqlConnection.makeAndReturnConnection();
				 file=mysqlConnection.updateImageUserInProfileSetting(con,username,file);
				//if (newRequest != null) {
				String keymessage = "UpdatingImageOfProfileSettingSucceeded";
				Object[] message=new Object[2];
				message[0]=keymessage;
				message[1]=file;
				try {
					System.out.println("ServerUpdating...........");
						client.sendToClient(message);
						} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				}//if
			}
		}
		}
}

