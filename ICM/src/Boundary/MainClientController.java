package Boundary;

import java.awt.Button;


import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import Client.ClientConsole;
import Client.ClientLoginObserver;
import Client.ClientObserver;
import Client.MainForClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;

public class MainClientController {
	public static LoginController login;
	@FXML
	TextField ip;
	@FXML
	Label wrong;
	public void connect() {
		String host=ip.getText();
		System.out.println(Thread.currentThread().toString());
		try {
			ClientConsole cc=new ClientConsole(host);
			ClientObserver co=new ClientObserver(cc.getClient());
			ClientLoginObserver clo=new ClientLoginObserver(cc.getClient());
			cc.getClient().openConnection();
			wrong.setVisible(false);
			login=new LoginController();
			login.start(MainForClient.stage,cc);
		}catch(Exception e) {
			wrong.setVisible(true);
			ip.clear();
			System.out.println("connection failed");
		}

	
		
	}

}
