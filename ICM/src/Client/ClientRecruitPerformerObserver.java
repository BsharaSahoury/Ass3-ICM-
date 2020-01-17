package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientRecruitPerformerObserver implements Observer{
	public ClientRecruitPerformerObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Object[]) {
			Object[] arg2=(Object[])arg1;
			if(arg2[0] instanceof String) {
				String keymessage=(String)arg2[0];
				if(keymessage.equals("PerformerRecruit")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Alert alert = new Alert(AlertType.CONFIRMATION);
					        alert.setTitle("TEST");
					        alert.setHeaderText("Success");
					        alert.setContentText("Performer has been recruited successfully");
					        alert.showAndWait();
						}
						
					});
				}
			}
		}
		
	}

}










/*package Client;

public class ClientRecruitPerformerObserver {

}
*/