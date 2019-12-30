package Client;

import java.util.Observable;
import java.util.Observer;

import Boundary.ChairmanHomeController;
import Boundary.ComitteeMemberHomeController;
import Boundary.EvaluatorHomeController;
import Boundary.InspectorHomeController;
import Boundary.LecturerHomeController;
import Boundary.MainClientController;
import Boundary.StudentHomeController;
import Boundary.TesterHomeController;
import Entity.Employee;
import Entity.Student;
import Entity.User;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientLoginObserver extends Application implements Observer{
	private static InspectorHomeController  inspector;
	private Employee employee1;
	
	public ClientLoginObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof User) {
		if(arg1 instanceof Employee) {
			Employee employee1=(Employee)arg1;
			switch(employee1.getJob()) {
			case "inspector":
				InspectorHomeController inspector=new InspectorHomeController();
				inspector.start(employee1);
				break;
			case "evaluator":
				EvaluatorHomeController evaluator=new EvaluatorHomeController();
				evaluator.start(employee1);
				break;
			case "comittee member":
				ComitteeMemberHomeController comitteeMember=new ComitteeMemberHomeController();
				comitteeMember.start(employee1);
				break;
			case "chairman":
				ChairmanHomeController chairman=new ChairmanHomeController();
				chairman.start(employee1);
				break;
			case "lecturer":
				LecturerHomeController lecturer=new LecturerHomeController();
				lecturer.start(employee1);
				break;
			case "tester":
				TesterHomeController tester=new TesterHomeController();
				tester.start(employee1);
				break;
			}
		}
		else if(arg1 instanceof Student) {
			Student student1=(Student)arg1;
			StudentHomeController student=new StudentHomeController();
			student.start(student1);
		}
		else {
			MainClientController.login.displayErrorMessage();
		}
	}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		inspector.start(employee1, primaryStage);
	}

}
