package Entity;


public class Inspector extends Employee {
	


	public Inspector(Employee e) {
		super(e.getId(), e.getJob(), e.getRoles(), e.getEmail(),e.getPassword(),e.getUsername(), e.getFirstName(), e.getLastName(),e.getMyRequests());
		// TODO Auto-generated constructor stub
	}


	private static Inspector MyInspector ;
	

	public static Inspector getInstance(Employee e) {
		if (MyInspector == null) {
			MyInspector = new Inspector(e);
		}
		return MyInspector;
	}
}
