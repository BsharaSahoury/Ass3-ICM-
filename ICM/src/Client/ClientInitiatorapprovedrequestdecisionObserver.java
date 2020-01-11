package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ocsf.client.ObservableClient;

public class ClientInitiatorapprovedrequestdecisionObserver implements Observer {
	public ClientInitiatorapprovedrequestdecisionObserver(ObservableClient client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				System.out.println(keymessage+"111");
				if (keymessage.equals("initiator approved the decision of the request")) {				
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("SAVE");
							alert.setHeaderText("Success");
							alert.setContentText("Your approved saved successfully");
							alert.showAndWait();
							System.out.println("S");
							System.out.println("S");
							System.out.println("S");
							System.out.println("S");System.out.println("S");
							System.out.println("S");
							System.out.println("S");
						}
					});
				}/*
				else if(keymessage.equals("already approved and finished")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Already");
							alert.setContentText("You Already approved the decision");
							alert.showAndWait();
						}

					});
				}*/
			}

		}
	}
}

