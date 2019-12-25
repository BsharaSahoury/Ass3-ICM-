package Entity;

public class Employee extends User {

private int id;	
private String Job;

public Employee(String Job,String email, String password,String username,int id) {
	super(email,password,username);
	this.Job = Job;
	this.id=id;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getJob() {
	return this.Job;
}

public void setJob(String job) {
	this.Job = job;
}
	
}
