package Client;

import java.util.ArrayList;

import java.util.Observable;
import java.util.Observer;

import Entity.Employee;
import javafx.application.Platform;
import messages.AutomaticRecruitMessageController;
import messages.CommitteeDecisionApproveController;
import messages.CommitteeDecisionAskForaddInfoController;

public class ClientGetEvaluatorsObserver implements Observer {
	public ClientGetEvaluatorsObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Object[]) {
			Object[] arg1=(Object[])arg;
			if(arg1[0] instanceof String) {
				String keymessage=(String)arg1[0];
				if(keymessage.equals("evaluators")) {
					if(arg1[1] instanceof ArrayList<?>) {
						ArrayList<Employee> Elist=(ArrayList<Employee>)arg1[1];
						ArrayList<String> names=new ArrayList<>();
						for(Employee f1 : Elist) {
							names.add(f1.getFirstName()+" "+f1.getLastName());
						}
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
						AutomaticRecruitMessageController.ctrl.fillCombo(names);
							}
						});
					}
				}
				else if(keymessage.equals("Performance leaders")) {
					if(arg1[1] instanceof ArrayList<?>) {
						ArrayList<Employee> Elist=(ArrayList<Employee>)arg1[1];
						ArrayList<String> names=new ArrayList<>();
						for(Employee f1 : Elist) {
							names.add(f1.getFirstName()+" "+f1.getLastName());
						}
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
						CommitteeDecisionApproveController.ctrl.fillCombo(names);
							}
						});
					}
				}
				else if(keymessage.equals("evaluatorsagain")) {
					if(arg1[1] instanceof ArrayList<?>) {
						ArrayList<Employee> Elist=(ArrayList<Employee>)arg1[1];
						ArrayList<String> names=new ArrayList<>();
						for(Employee f1 : Elist) {
							names.add(f1.getFirstName()+" "+f1.getLastName());
						}
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
						CommitteeDecisionAskForaddInfoController.ctrl.fillCombo(names);
							}
						});
					}
				}
			}
		}
		
	}

}
