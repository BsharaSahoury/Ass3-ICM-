package Entity;

import java.util.ArrayList;

public class Employee extends User {

	private int id;
	private Job job;
	private ArrayList<Role> roles = new ArrayList<Role>();

	

	public Employee(int id, Job job, ArrayList<Role> roles,String email, String password, String username, String firstName, String lastName) {
		super( email, password, username, firstName, lastName);
		this.id = id;
		this.job = job;
	}

	public Employee(int id, Job job, ArrayList<Role> roles,String email, String password, String username, String firstName, String lastName,
			ArrayList<Request> myRequests) {
		super( email, password, username, firstName, lastName,myRequests);
		this.id = id;
		this.job = job;
		this.roles = roles;
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
	public ArrayList<Role> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<Role> roles) {
		this.roles = roles;
	}
	public enum Job {
		Lecturer,AdminEmployee, Manager;
	}

	public Boolean addRole(Role role) {
		if (roles.contains(role)) {
			System.out.println("this role is already existing ");
			return false;
		}
		roles.add(role);
		return true;
	}

	public Boolean removeRole(Role role) {
		if (!roles.contains(role))
			return false;
		roles.remove(role);
		return true;

	}

	public enum Role {
		Tester, ComitteMember, Chairman, PerformanceLeader, Evaluator;
	}
}
