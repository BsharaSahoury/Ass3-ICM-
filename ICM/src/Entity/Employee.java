package Entity;

public class Employee extends User {

private int id;	
private Job job;

public Employee(Job job,String email, String password,String username,int id) {
	super(email,password,username);
	this.job = job;
	this.id=id;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public Job getJob() {
	return this.job;
}

public void setJob(Job job) {
	this.job = job;
}
public enum Job {
	Lecturer, Employee,Manager;
}
}
