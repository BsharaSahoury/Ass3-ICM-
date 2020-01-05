package DBconnection;

import java.sql.Connection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import Entity.*;

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

            conn = DriverManager.getConnection("jdbc:mysql://localhost/icm?serverTimezone=IST","root","ayman1234567891");       
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
			if(rs.next()) {
			Student student1=new Student(rs.getString(1),rs.getString(2),rs.getString(3));
			return student1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		

	}
	public static ArrayList<RequestPhase> getDataFromDB(Connection con){
		
		String Initiatorname=null;
		ArrayList<RequestPhase> arr = new ArrayList<RequestPhase>();
		Statement stmt1 = null;
		PreparedStatement stmt2=null;
		PreparedStatement stmt3=null;
		Request s=null;
		RequestPhase result=null;
		try {
			stmt1=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			
			ResultSet rs=stmt1.executeQuery("SELECT R.* FROM icm.request R;");
			while(rs.next())
	 		{
				stmt2 = con.prepareStatement("SELECT E.* FROM icm.employee E WHERE username=?;");
				stmt2.setString(1, rs.getString(9));	
				ResultSet rs2=stmt2.executeQuery();	
				rs2.next();
				Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
				if(Initiatorname.equals(null)) {
					stmt2 = con.prepareStatement("SELECT E.* FROM icm.student E WHERE username=?;");
					stmt2.setString(1, rs.getString(9));
					rs2=stmt2.executeQuery();	
					rs2.next();
					Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
				}	
				
				stmt3 = con.prepareStatement("SELECT E.phase,E.state FROM icm.requestinphase E WHERE request_id=? AND state=?;");
				stmt3.setInt(1, rs.getInt(7));
				stmt3.setString(2, "work");
				ResultSet rs3=stmt3.executeQuery();	
                if(rs3.next()) {     
				Date date1=null;
				if(!Initiatorname.equals(null)) {
					s=new Request(rs.getInt(7),Initiatorname,rs.getString(8),rs.getString(1),rs.getDate(6));
					result=new RequestPhase(null,null,s,Phase.valueOf(rs3.getString(1)),State.valueOf(rs3.getString(2)));	
					
					
				}	
				arr.add(result);
                }
                stmt3=null;
				stmt2=null;
				s=null;
				result=null;
				rs2.close();
				rs3.close();
			}
			rs.close();	 		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return arr;
	}
	public static ArrayList<Request> getRequestsWorkOn(Connection con,String username,String job) {
		String Initiatorname=null;
		ArrayList<Request> arr = new ArrayList<Request>();
		ArrayList<Integer> arr2 = new ArrayList<Integer>();
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2=null;
		PreparedStatement stmt3=null;
		Request s=null;
		try {
			stmt1=con.prepareStatement("SELECT DISTINCT R.request_id FROM icm.requestinphase R WHERE phase_administrator=?;");
			stmt1.setString(1, username);	
			ResultSet rs=stmt1.executeQuery();
			while(rs.next())
	 		{
				stmt2 = con.prepareStatement("SELECT E.* FROM icm.employee E WHERE username=?;");
				stmt2.setString(1, username);	
				ResultSet rs2=stmt2.executeQuery();	
				rs2.next();
				Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
				if(Initiatorname.equals(null)) {			
					stmt2 = con.prepareStatement("SELECT E.* FROM icm.student E WHERE username=?;");
					stmt2.setString(1, rs.getString(9));
					rs2=stmt2.executeQuery();	
					rs2.next();
					Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
				}	
				stmt3 = con.prepareStatement("SELECT E.* FROM icm.request E WHERE id=?;");
				stmt3.setInt(1, rs.getInt(1));	
				ResultSet rs3=stmt3.executeQuery();	
				rs3.next();
				if(!rs3.equals(null)&&!Initiatorname.equals(null)) {
					arr.add(new Request(rs3.getInt(7),Initiatorname,rs3.getString(8),rs3.getString(1),rs3.getDate(6)));
				}
				stmt2=null;
				stmt3=null;
				rs2.close();
				rs3.close();
	 		}
			rs.close();	 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return arr;
	}
	public static ArrayList<Request> getmyRequestFromDB(Connection con,String username) {
		String Initiatorname=null;
		ArrayList<Request> arr = new ArrayList<Request>();
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2=null;
		Request s=null;
		try {			
			stmt1=con.prepareStatement("SELECT R.* FROM icm.request R WHERE initiator_username=?;");
			stmt1.setString(1, username);
			ResultSet rs=stmt1.executeQuery();
			while(rs.next())
	 		{
				stmt2 = con.prepareStatement("SELECT E.* FROM icm.employee E WHERE username=?;");
				stmt2.setString(1, username);	
				ResultSet rs2=stmt2.executeQuery();	
				rs2.next();
				Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
				if(Initiatorname.equals(null)) {
					stmt2 = con.prepareStatement("SELECT E.* FROM icm.student E WHERE username=?;");
					stmt2.setString(1, rs.getString(9));
					rs2=stmt2.executeQuery();	
					rs2.next();
					Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
				}	
				if(!Initiatorname.equals(null)) {
					s=new Request(rs.getInt(7),Initiatorname,rs.getString(8),rs.getString(1),rs.getDate(6));
					arr.add(s);
				}				
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
		  	//	String id=r.getId();
		  		PreparedStatement stm= con.prepareStatement("UPDATE requirement SET status=? WHERE ID=?;");
		  		stm.setString(1, r.getStatus());
		  	//	stm.setString(2, id);
		  		stm.executeUpdate();
		  		} 
		  		catch (SQLException e) {
		  			e.printStackTrace();
		  		}		  	
		}
  	}
	public static Request insertRequestToDB(Connection con, Request request) {
		PreparedStatement stm=null;
		Statement st=null;
		try {
			st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT MAX(request.id) FROM request;");
			if(rs.next()) {
				count=rs.getInt(1)+1;
			}
			else count=0;
			stm=con.prepareStatement("INSERT INTO request VALUES(?,?,?,?,?,?,?,?,?,?);");
			stm.setString(1, request.getPrivilegedInfoSys());
			stm.setString(2, request.getExistingSituation());
			stm.setString(3, request.getExplainRequest());
			stm.setString(4, request.getReason());
			stm.setString(5, request.getComment());
			stm.setDate(6, request.getDate());
			stm.setInt(7, count);
			request.setId(count);
			stm.setString(8,"active");
			stm.setString(9, request.getInitiator().getUsername());
			if(request.getMyFile()==null)
				stm.setBytes(10, null);
			else
				stm.setBytes(10,request.getMyFile().getMybyterray());
			stm.executeUpdate();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return request;
	}
	public static void insertRecruitNotificationToDB(Connection con, Request newRequest) {
		PreparedStatement stm=null;
		Statement st=null;
		try {
			st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT MAX(notification.id) FROM notification;");
			if(rs.next()) {
				count=rs.getInt(1)+1;
			}
			else count=0;
			stm=con.prepareStatement("INSERT INTO notification VALUES(?,?,?,?);");
			stm.setInt(1, count);
			stm.setString(2, "You've been recruited to evaluate request#"+ newRequest.getId());
			stm.setDate(3, newRequest.getDate());
			stm.setString(4, "automatic recruit");
			stm.executeUpdate();
			stm=con.prepareStatement("SELECT employee.username FROM employee WHERE support_system=?;");
			stm.setString(1, newRequest.getPrivilegedInfoSys());
			rs=stm.executeQuery();
			if(rs.next()) {
				String username=rs.getString(1);
				stm=con.prepareStatement("INSERT INTO notificationforuser VALUES(?,?);");
				stm.setInt(1, count);
				stm.setString(2, username);
				stm.executeUpdate();
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public static ArrayList<Notification> getNotificationsForUser(Connection con, String username) {
		PreparedStatement stm=null;
		ArrayList<Notification> Nlist=new ArrayList<>();
		try {
			stm=con.prepareStatement("SELECT notification.* FROM notification,notificationforuser WHERE notification.id=notificationforuser.notification_id AND notificationforuser.username=?;");
			stm.setString(1, username);
			ResultSet rs=stm.executeQuery();
			while(rs.next()) {
				Nlist.add(new Notification(rs.getString(2),rs.getDate(3),rs.getString(4)));
			}
			return Nlist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Employee getAutomaticRecruit(Connection con, String privilegedInfoSys) {
		PreparedStatement stm=null;
		try {
			stm=con.prepareStatement("SELECT employee.* FROM employee WHERE job='evaluator' AND support_system=?;");
			stm.setString(1, privilegedInfoSys);
			ResultSet rs=stm.executeQuery();
			if(rs.next()) {
				return new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(8));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Notification insertNotificationToDB(Connection con, Notification n1) {
		PreparedStatement stm=null;
		Statement st=null;
		try {
			st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT MAX(notification.id) FROM notification;");
			if(rs.next()) {
				count=rs.getInt(1)+1;
			}
			else count=0;
			stm=con.prepareStatement("INSERT INTO notification VALUES(?,?,?,?);");
			stm.setInt(1, count);
			stm.setString(2,n1.getContent());
			stm.setDate(3, n1.getDate());
			stm.setString(4, n1.getType());
			stm.executeUpdate();
			n1.setId(count);
			return n1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;

	}
	public static void insertRecruitNotificationForInspectorToDB(Connection con, Notification n1) {
		Statement st=null;
		PreparedStatement stm=null;
		try {
			st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT employee.username FROM employee WHERE job='inspector';");
			if(rs.next()) {
				String username=rs.getString(1);
				stm=con.prepareStatement("INSERT INTO notificationforuser VALUES(?,?);");
				stm.setInt(1, n1.getId());
				stm.setString(2, username);
				stm.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public static Employee recruitAutomatically(Connection con, int id) {
		Statement st=null;
		PreparedStatement stm=null;
		try {
			stm=con.prepareStatement("SELECT request.Privileged_information_system FROM request WHERE id=?;");
			stm.setInt(1, id);
			ResultSet rs=stm.executeQuery();
			if(rs.next()) {
				String privilegedSystem=rs.getString(1);
				Employee evaluator=mysqlConnection.getAutomaticRecruit(con, privilegedSystem);
				mysqlConnection.assignEvaluatorToRequest(con,evaluator,id);
				return evaluator;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public static boolean assignEvaluatorToRequest(Connection con, Employee evaluator, int id) {
		PreparedStatement stm=null;
		try {
			stm=con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?);");
			stm.setInt(1, id);
			stm.setString(2,"evaluation");
			stm.setInt(3,0);
			stm.setDate(4,null);
			stm.setDate(5, null);
			stm.setString(6, evaluator.getUsername());
			stm.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	public static ArrayList<Employee> getEvaluators(Connection con) {
		Statement st=null;
		Employee evaluator;
		ArrayList<Employee> list=new ArrayList<>();
		try {
			st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT employee.* FROM employee WHERE job='evaluator';");
			while(rs.next()) {
				evaluator=new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(8));
				list.add(evaluator);
			}
			return list; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Employee getSpecificEmployee(Connection con, String fullname) {
		PreparedStatement stm=null;
		String[] name=new String[2];
		name=fullname.split(" ");
		try {
			stm=con.prepareStatement("SELECT employee.* FROM employee WHERE first_name=? AND last_name=?;");
			stm.setString(1, name[0]);
			stm.setString(2, name[1]);
			ResultSet rs=stm.executeQuery();
			if(rs.next()) {
				return new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(8));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	public static void insertNotificationForUserToDB(Connection con, Notification n1, Employee employee) {
		PreparedStatement stm=null;
		try {
			stm=con.prepareStatement("INSERT INTO notificationforuser VALUES(?,?);");
			stm.setInt(1, n1.getId());
			stm.setString(2, employee.getUsername());
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public static Request getRequestInfo(Connection con, int id) {
		PreparedStatement stm1=null;
		PreparedStatement stm2=null;
		String Initiatorname=null;
		String role =null;
		Request r=null;
			try {
				stm1=con.prepareStatement("SELECT R.* FROM icm.request R WHERE id=?;");
				stm1.setInt(1, id);
				ResultSet rs=stm1.executeQuery();
				
				while(rs.next()) {
					String username=rs.getString(9);
					stm2 = con.prepareStatement("SELECT E.* FROM icm.employee E WHERE username=?;");
					stm2.setString(1, username);	
					ResultSet rs2=stm2.executeQuery();	
					rs2.next();
					Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
					role=rs2.getString(4);
					if(Initiatorname.equals(null)) {
						stm2 = con.prepareStatement("SELECT E.* FROM icm.student E WHERE username=?;");
						stm2.setString(1, rs.getString(9));
						rs2=stm2.executeQuery();	
						rs2.next();
						Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
						role="student";
					}	
					if(!Initiatorname.equals(null)) {
						r=new Request(rs.getInt(7),Initiatorname,role,rs2.getString(8),rs.getString(8), rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), rs.getString(5), rs.getDate(6));
					
					}		
					rs2.close();
									}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return r;		
		}
}
