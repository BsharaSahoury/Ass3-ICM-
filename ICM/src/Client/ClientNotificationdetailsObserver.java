package Client;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Boundary.NotificationsController;
import Entity.Notification;
import messages.DecisionCommitteeMemberMessageController;

public class ClientNotificationdetailsObserver implements Observer {
	public ClientNotificationdetailsObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Object[]) {
			Object[] arg2=(Object[])arg1;
			if(arg2[0] instanceof String) {
				String keymessage=(String)arg2[0];
				if(keymessage.equals("notification details")) {
					System.out.println("ssxxx");
					if(arg2[1] instanceof String) {
					String details=(String)arg2[1];					
					DecisionCommitteeMemberMessageController.ctrl.setdetails(details);
					}
					
				}
			}
		
		}
	}
}
