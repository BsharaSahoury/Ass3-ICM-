package Client;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Boundary.InspectorHomeController;
import Entity.Request;
import Entity.User;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientAllRequestsObserver implements Observer{
	public ClientAllRequestsObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg1 instanceof ArrayList<?>) {
		
		ArrayList<Request> arr=(ArrayList<Request>) arg1;
		InspectorHomeController.AllRequests.fillTable(arr);
		}
	}
}
