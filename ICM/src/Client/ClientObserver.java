package Client;

import java.util.Observable;
import java.util.Observer;

public class ClientObserver implements Observer {
	public ClientObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof String) {
			String s=(String)arg;
			System.out.println(s);
		}
	
		
	}

	
}
