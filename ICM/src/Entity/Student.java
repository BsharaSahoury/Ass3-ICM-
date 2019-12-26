package Entity;

public class Student extends User{
	
private int id;
private String firstName;
private String lastName;
private String department;
public Student(int id, String firstName, String lastName, String email, String department, String password,String username) {
	super(email,password,username);
	this.id = id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.department = department;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}


}
