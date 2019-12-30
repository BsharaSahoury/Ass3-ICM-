package Boundary;

import java.awt.Button;


import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import Client.ClientConsole;
import Client.ClientObserver;
import Client.MainForClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;

public class MainClientController {
	@FXML
	TextField ip;
	@FXML
	Label wrong;
	public static Stage primaryStage;
	public void connect() {
		String host=ip.getText();
		try {
			ClientConsole cc=new ClientConsole(host);
			ClientObserver co=new ClientObserver(cc.getClient());
			cc.getClient().openConnection();
			wrong.setVisible(false);
			LoginController login=new LoginController(cc);
			login.start(MainForClient.stage);
		}catch(Exception e) {
			wrong.setVisible(true);
			ip.clear();
			System.out.println("connection failed");
		}

	
		
	}

}
