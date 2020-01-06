package Client;

import java.util.Observer;

import Boundary.RequestInfoController;
import Boundary.RequestTrackController;
import Entity.Request;
import ocsf.client.ObservableClient;;

public class ClientRequestTrack implements Observer {

	public ClientRequestTrack(ObservableClient client) {
		client.addObserver(this);
	}

	@Override
	public void update(java.util.Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("Track request")) {
					if (arg2[1] instanceof Request) {
						Request request = (Request) arg2[1];
						RequestTrackController.RequestTrack.SetInfo(request);
					}

				}
			}
		}

	}

}
