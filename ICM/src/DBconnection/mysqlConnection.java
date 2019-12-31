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
	private static int count=0;
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
            conn = DriverManager.getConnection("jdbc:mysql://localhost/icm?serverTimezone=IST","root","hbk12345");
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
				Employee employee1=new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(8));
				return employee1;
			}
			stm=con.prepareStatement("SELECT student.* FROM student WHERE username=?;");
			stm.setString(1, username);
			rs=stm.executeQuery();
			Student student1=new Student(rs.getString(1),rs.getString(2),rs.getString(3));
			
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
			String Initiatorname=null;
			ArrayList<Request> arr = new ArrayList<Request>();
			Statement stmt1 = null;
			PreparedStatement stmt2=null;
			Request s=null;
			try {
				stmt1=con.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			try {
				ResultSet rs=stmt1.executeQuery("SELECT R.* FROM request R;");
				while(rs.next())
		 		{
					stmt2 = con.prepareStatement("SELECT E.* FROM employee E;Where username=?;");
					stmt2.setString(1, rs.getString(9));	
					ResultSet rs2=stmt2.executeQuery();	
					Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
					if(Initiatorname.equals(null)) {
						stmt2 = con.prepareStatement("SELECT E.* FROM student E;Where username=?;");
						stmt2.setString(1, rs.getString(9));
						rs2=stmt2.executeQuery();	
					}	
					s=new Request(rs.getString(7),rs2.getString(2)+" "+rs2.getString(3),rs.getString(8),rs.getString(1),rs.getDate(6));			
					arr.add(s);
					stmt2=null;
					s=null;
					rs2.close();
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
	public static boolean insertRequestToDB(Connection con, Request request) {
		PreparedStatement stm=null;
		try {
			stm=con.prepareStatement("INSERT INTO request VALUES(?,?,?,?,?,?,?,?,?,?);");
			stm.setString(1, request.getPrivilegedInfoSys());
			stm.setString(2, request.getExistingSituation());
			stm.setString(3, request.getExplainRequest());
			stm.setString(4, request.getReason());
			stm.setString(5, request.getComment());
			stm.setString(6, Integer.toString(count++));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}	
}
