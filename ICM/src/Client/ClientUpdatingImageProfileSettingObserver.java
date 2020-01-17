package Client;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import Boundary.LoginController;
import Boundary.ProfileSettingController;
import Boundary.RequestInfoController;
import Boundary.RequestSubmissionController;
import Entity.MyFile;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientUpdatingImageProfileSettingObserver implements Observer {
	public ClientUpdatingImageProfileSettingObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Object[]) {
			Object[] myMsg=(Object[])arg1;
		if(myMsg.length==2)
			if(myMsg[0] instanceof String) {
				String keymessage=(String)myMsg[0];
				if(keymessage.equals("UpdatingImageOfProfileSettingSucceeded")) {
					if(myMsg[1] instanceof MyFile)
					{
					MyFile file =(MyFile)myMsg[1];
					if(file.getMybyterray() != null) {	
					ProfileSettingController.ProfileSetting.settingImage(file);
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
					        alert.setTitle("Success");
					        alert.setHeaderText("Success");
					        alert.setContentText("your image has been updated successfully!");
					        alert.showAndWait();
						}
						
					});
					
				}
				}
				}
			}
		
	}
	}
}


