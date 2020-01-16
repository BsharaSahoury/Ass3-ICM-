package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import messages.ExtensionConfirmationMessage;

public class ClientGetExtensionDataObsaerver implements Observer {
	public ClientGetExtensionDataObsaerver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Object[]) {
			Object[] arg1 = (Object[]) arg;
			if (arg1[0] instanceof String) {
				String keymessage = (String) arg1[0];
				if (keymessage.equals("get extension data")) {
					String[] data = (String[]) arg1[1];
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							ExtensionConfirmationMessage.ctrl.setData(data);
						}

					});
				}
			}
		}
	}
}