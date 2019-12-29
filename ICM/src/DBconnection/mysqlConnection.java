package DBconnection;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Entity.Employee;
import Entity.Request;
import Entity.Student;
import Entity.User;

public class mysqlConnection {
	private static Connection conn = null;
	//this method creates and returns a connection to the relevant schema in the database that we would like to work with
	public static Connection makeAndReturnConnection()
	{
		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeed");
        } catch (Exception ex) {
        	/* handle the error*/
        	 System.out.println("Driver definition failed");
        	 }      
        try 
        {      
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?serverTimezone=IST","root","hbk12345");
            System.out.println("SQL connection succeed");
            return conn;
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
        return null;
        
   	}
	public static User isInDB(Connection con,String username,String password) {
		PreparedStatement stm=null;
		try {
			stm=con.prepareStatement("SELECT user.username, user.password FROM user WHERE username=? AND password=?;");
			stm.setString(1, username);
			stm.setString(2, password);
			ResultSet rs=stm.executeQuery();
			if(!rs.next()) return null;
			stm=con.prepareStatement("SELECT employee.* FROM employee WHERE username=?;");
			stm.setString(1, username);
			rs=stm.executeQuery();
			if(rs.next()) {
				Employee employee1=new Employee(rs.getString(2),rs.getString(3),rs.getString(8));
				return employee1;
			}
			stm=con.prepareStatement("SELECT student.* FROM student WHERE username=?;");
			stm.setString(1, username);
			rs=stm.executeQuery();
			Student student1=new Student(rs.getString(2),rs.getString(3));
			return student1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		

	}
	//here we collect all the requests from the database and put it in an ArrayList<Request> and returns that array
	public static ArrayList<Request> getDataFromDB(Connection con)
	{
		ArrayList<Request> arr = new ArrayList<Request>();
		Statement stmt = null;
		try {
			stmt=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			ResultSet rs=stmt.executeQuery("SELECT R.* FROM requirement R;");
			while(rs.next())
	 		{
				//arr.add(new Request(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));		
			}
			rs.close();
		}catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return arr;
	}
//this function make the update done by the client in the database
	public static void UpdateUserInDB(Request r,Connection con) {
		if (con != null) {	
		  		try {
		  		String id=r.getId();
		  		PreparedStatement stm= con.prepareStatement("UPDATE requirement SET status=? WHERE ID=?;");
		  		stm.setString(1, r.getStatus());
		  		stm.setString(2, id);
		  		stm.executeUpdate();
		  		} 
		  		catch (SQLException e) {
		  			e.printStackTrace();
		  		}		  	
		}
  	}	
}
