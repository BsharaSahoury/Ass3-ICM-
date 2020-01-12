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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import Entity.Employee;
import Entity.EvaluationReport;
import Entity.MyFile;
import Entity.Notification;
import Entity.Phase;
import Entity.Request;
import Entity.RequestPhase;
import Entity.State;
import Entity.Student;
import Entity.User;
import Server.MainForServer;

public class mysqlConnection {
	private static int count = 0;
	private static int numOfReport=0;
	private static String DB_PASSWORD;
	private static String DB_USERNAME;
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
	        	DB_USERNAME = Server.ServerController.get_DB_UserName();
	    		DB_PASSWORD = Server.ServerController.get_DB_Password();
	            MainForServer.set_Connection(DriverManager.getConnection("jdbc:mysql://localhost/icm?serverTimezone=IST",DB_USERNAME,DB_PASSWORD));
	            System.out.println("SQL connection succeed");
	            return MainForServer.get_Connection();
	     	} catch (SQLException ex) 
	     	    {/* handle any errors*/
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	            }
	        return null;
	        
	   	}

	public static User isInDB(Connection con, String username, String password) {
		PreparedStatement stm = null;

		try {
			stm = con
					.prepareStatement("SELECT user.username, user.password FROM user WHERE username=? AND password=?;");
			stm.setString(1, username);
			stm.setString(2, password);
			ResultSet rs = stm.executeQuery();

			if (!rs.next())
				return null;
			stm = con.prepareStatement("SELECT employee.* FROM employee WHERE username=?;");
			stm.setString(1, username);
			rs = stm.executeQuery();
			if (rs.next()) {
				Employee employee1 = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
				return employee1;
			}
			stm = con.prepareStatement("SELECT student.* FROM student WHERE username=?;");
			stm.setString(1, username);
			rs = stm.executeQuery();
			if (rs.next()) {
				Student student1 = new Student(rs.getString(1), rs.getString(2), rs.getString(3));
				return student1;
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
public static String getinitiatorname(Connection con,String username ) {
	PreparedStatement stmt2=null;
	String Initiatorname=null;
	try {
		stmt2 = con.prepareStatement("SELECT E.* FROM icm.employee E WHERE username=?;");
		stmt2.setString(1, username);	
		ResultSet rs2=stmt2.executeQuery();	
		if(rs2.next())
		Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
		if(Initiatorname.equals(null)) {
			stmt2 = con.prepareStatement("SELECT E.* FROM icm.student E WHERE username=?;");
			stmt2.setString(1, username);
			rs2=stmt2.executeQuery();	
			rs2.next();
			Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
		}
		if(!Initiatorname.equals(null)) 
		rs2.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return Initiatorname;
}
public static ArrayList<RequestPhase> getDataFromDB(Connection con){
	   String[] phases = {"closing","testing","performance","decision","evaluation"};
		String Initiatorname=null;
		ArrayList<RequestPhase> arr = new ArrayList<RequestPhase>();
		Statement stmt1 = null;
		PreparedStatement stmt2=null;
		PreparedStatement stmt3=null;
		PreparedStatement stmt4=null;
		Request s=null;
		RequestPhase result=null;
		int i=0;
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
				Initiatorname=getinitiatorname(con,rs.getString(9));
				stmt4 = con.prepareStatement("SELECT E.phase,E.state,E.repetion FROM icm.requestinphase E WHERE request_id=? AND phase=?;");
				stmt4.setInt(1, rs.getInt(7));
				stmt4.setString(2,phases[i]);
				ResultSet rs4=stmt4.executeQuery();
				if(rs4.next()) {
					i=-1;
					s=new Request(rs.getInt(7),Initiatorname,rs.getString(8),rs.getString(1),rs.getDate(6));
					result=new RequestPhase(null,null,s,Phase.valueOf(rs4.getString(1)),State.valueOf(rs4.getString(2)));					
					rs4.close();
				}
				else {
			do {
				i++;
				stmt3 = con.prepareStatement("SELECT E.phase,E.state,E.repetion FROM icm.requestinphase E WHERE request_id=? AND phase=? AND state!='over';");
				stmt3.setInt(1, rs.getInt(7));
				stmt3.setString(2, phases[i]);
				ResultSet rs3=stmt3.executeQuery();
				if(rs3.next()) {				
					i=-1;
					s=new Request(rs.getInt(7),Initiatorname,rs.getString(8),rs.getString(1),rs.getDate(6));
					result=new RequestPhase(null,null,s,Phase.valueOf(rs3.getString(1)),State.valueOf(rs3.getString(2)));	
					result.setRepetion(rs3.getInt(3));
					rs3.close();
				}	       
			    }while(i!=-1&&i!=5);
				}
			    if(!result.equals(null))
				arr.add(result);
			    stmt2=null;
				stmt3=null;
				stmt4=null;
				s=null;
				result=null;
				i=0;
				Initiatorname=null;
				}					
				rs.close();	
                }			 		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return arr;
	}

public static ArrayList<RequestPhase> getRequestsWorkOn(Connection con,String username,String phase) {
	String Initiatorname=null;
	ArrayList<RequestPhase> arr = new ArrayList<RequestPhase>();
	PreparedStatement stmt1 = null;
	PreparedStatement stmt2=null;
	PreparedStatement stmt3=null;
	PreparedStatement stmt4=null;
	Request s=null;
	RequestPhase result=null;
	try {
		stmt1=con.prepareStatement("SELECT DISTINCT R.request_id FROM icm.requestinphase R WHERE phase_administrator=? AND phase=?;");
		stmt1.setString(1, username);
		stmt1.setString(2, phase);
		ResultSet rs=stmt1.executeQuery();
		while(rs.next())
 		{
			stmt2=con.prepareStatement("SELECT R.* FROM icm.requestinphase R WHERE phase_administrator=? AND phase=? AND request_id=?;");
			stmt2.setString(1, username);
			stmt2.setString(2, phase);
			stmt2.setInt(3, rs.getInt(1));
			ResultSet rs2=stmt2.executeQuery();
			int max=0;
			while(rs2.next()) {
				if(rs2.getInt(3)>=max)
					max=rs2.getInt(3);
			}
			rs2.close();
			stmt3=con.prepareStatement("SELECT R.* FROM icm.requestinphase R WHERE phase_administrator=? AND phase=? AND request_id=? AND repetion=?;");
			stmt3.setString(1, username);
			stmt3.setString(2, phase);
			stmt3.setInt(3, rs.getInt(1));
			stmt3.setInt(4, max);
			ResultSet rs3=stmt3.executeQuery();	
			rs3.next();
			try {
				stmt4 = con.prepareStatement("SELECT E.* FROM icm.request E WHERE id=?;");
				stmt4.setInt(1, rs.getInt(1));	
				ResultSet rs4=stmt4.executeQuery();	
			if(rs4.next()) {
				Initiatorname=getinitiatorname(con,rs4.getString(9));
			if(!Initiatorname.equals(null)) {
				s=new Request(rs4.getInt(7),Initiatorname,rs4.getString(8),rs4.getString(1),rs4.getDate(6));
				result=new RequestPhase(null,null,s,Phase.valueOf(rs3.getString(2)),State.valueOf(rs3.getString(7)));	
				arr.add(result);	
			}
			}
			stmt3 = null;
			stmt4=null;
			rs4.close();
		}
	 catch (SQLException e) {
//TODO Auto-generated catch block
		e.printStackTrace();
	}
 		}
		rs.close();
}catch (SQLException e) {
	//TODO Auto-generated catch block
			e.printStackTrace();
		}
	return arr;
}

public static ArrayList<Request> getmyRequestFromDB(Connection con, String username) {
	String Initiatorname = null;
	ArrayList<Request> arr = new ArrayList<Request>();
	PreparedStatement stmt1 = null;
	PreparedStatement stmt2 = null;
	Request s = null;
	try {
		stmt1 = con.prepareStatement("SELECT R.* FROM icm.request R WHERE initiator_username=?;");
		stmt1.setString(1, username);
		ResultSet rs = stmt1.executeQuery();
		while (rs.next()) {
			stmt2 = con.prepareStatement("SELECT E.* FROM icm.employee E WHERE username=?;");
			stmt2.setString(1, username);
			ResultSet rs2 = stmt2.executeQuery();
			rs2.next();
			Initiatorname = rs2.getString(2) + " " + rs2.getString(3);
			if (Initiatorname.equals(null)) {
				stmt2 = con.prepareStatement("SELECT E.* FROM icm.student E WHERE username=?;");
				stmt2.setString(1, rs.getString(9));
				rs2 = stmt2.executeQuery();
				rs2.next();
				Initiatorname = rs2.getString(2) + " " + rs2.getString(3);
			}
			if (!Initiatorname.equals(null)) {
				s = new Request(rs.getInt(7), Initiatorname, rs.getString(8), rs.getString(1), rs.getDate(6));
				arr.add(s);
			}
			stmt2 = null;
			s = null;
			rs2.close();
		}
		rs.close();
	} catch (SQLException e) {
//TODO Auto-generated catch block
		e.printStackTrace();
	}
	return arr;
}

//this function make the update done by the client in the database
	public static void UpdateUserInDB(Request r, Connection con) {
		if (con != null) {
			try {
				// String id=r.getId();
				PreparedStatement stm = con.prepareStatement("UPDATE requirement SET status=? WHERE ID=?;");
				stm.setString(1, r.getStatus());
				// stm.setString(2, id);
				stm.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Request insertRequestToDB(Connection con, Request request) {
		PreparedStatement stm = null;
		Statement st = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(request.id) FROM request;");
			if (rs.next()) {
				count = rs.getInt(1) + 1;
			} else
				count = 0;
			stm = con.prepareStatement("INSERT INTO request VALUES(?,?,?,?,?,?,?,?,?,?,?);");
			stm.setString(1, request.getPrivilegedInfoSys());
			stm.setString(2, request.getExistingSituation());
			stm.setString(3, request.getExplainRequest());
			stm.setString(4, request.getReason());
			stm.setString(5, request.getComment());
			stm.setDate(6, request.getDate());
			stm.setInt(7, count);
			request.setId(count);
			stm.setString(8, "active");
			stm.setString(9, request.getInitiator().getUsername());
			if (request.getMyFile() == null) {
				stm.setBytes(10, null);
				stm.setString(11,null);
			}
			else {
				stm.setBytes(10, request.getMyFile().getMybyterray());
				stm.setString(11,request.getFilename());
			}
			stm.executeUpdate();
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return request;
	}

	public static void insertRecruitNotificationToDB(Connection con, Request newRequest) {
		PreparedStatement stm = null;
		Statement st = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(notification.id) FROM notification;");
			if (rs.next()) {
				count = rs.getInt(1) + 1;
			} else
				count = 0;
			stm = con.prepareStatement("INSERT INTO notification VALUES(?,?,?,?);");
			stm.setInt(1, count);
			stm.setString(2, "You've been recruited to evaluate request#" + newRequest.getId());
			stm.setDate(3, newRequest.getDate());
			stm.setString(4, "automatic recruit");
			stm.executeUpdate();
			stm = con.prepareStatement("SELECT employee.username FROM employee WHERE support_system=?;");
			stm.setString(1, newRequest.getPrivilegedInfoSys());
			rs = stm.executeQuery();
			if (rs.next()) {
				String username = rs.getString(1);
				stm = con.prepareStatement("INSERT INTO notificationforuser VALUES(?,?);");
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
		PreparedStatement stm = null;
		ArrayList<Notification> Nlist = new ArrayList<>();
		try {
			stm = con.prepareStatement(
					"SELECT notification.* FROM notification,notificationforuser WHERE notification.id=notificationforuser.notification_id AND notificationforuser.username=?;");
			stm.setString(1, username);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Notification n=new Notification(rs.getString(2), rs.getDate(3), rs.getString(4));
				n.setId(rs.getInt(1));
				Nlist.add(n);
			}
			return Nlist;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Employee getAutomaticRecruit(Connection con, String privilegedInfoSys) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("SELECT employee.* FROM employee WHERE job='evaluator' AND support_system=?;");
			stm.setString(1, privilegedInfoSys);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Notification insertNotificationToDB(Connection con, Notification n1) {
		PreparedStatement stm = null;
		Statement st = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(notification.id) FROM notification;");
			if (rs.next()) {
				count = rs.getInt(1) + 1;
			} else
				count = 0;
			stm = con.prepareStatement("INSERT INTO notification VALUES(?,?,?,?);");
			stm.setInt(1, count);
			stm.setString(2, n1.getContent());
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
		Statement st = null;
		PreparedStatement stm = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT employee.username FROM employee WHERE job='inspector';");
			if (rs.next()) {
				String username = rs.getString(1);
				stm = con.prepareStatement("INSERT INTO notificationforuser VALUES(?,?);");
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
		Statement st = null;
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("SELECT request.Privileged_information_system FROM request WHERE id=?;");
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				String privilegedSystem = rs.getString(1);
				Employee evaluator = mysqlConnection.getAutomaticRecruit(con, privilegedSystem);
				mysqlConnection.assignEmployeeToPhaseRequest(con, evaluator, id,"evaluation");
				return evaluator;
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static boolean assignEmployeeToPhaseRequest(Connection con, Employee employee, int id,String phase) {
		PreparedStatement stm = null;
		PreparedStatement stm2 = null;
		try {
			int Max=0;
			stm2 = con.prepareStatement("SELECT R.repetion FROM icm.requestinphase R WHERE request_id=? AND phase=?;");
			stm2.setInt(1, id);
			stm2.setString(2, phase);
			ResultSet rs = stm2.executeQuery();	
			while(rs.next()) {
				if(rs.getInt(1)>=Max)
					Max=rs.getInt(1)+1;
			}
			stm = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
			stm.setInt(1, id);
			stm.setString(2, phase);
			stm.setInt(3, Max);
			stm.setDate(4, null);
			stm.setDate(5, null);
			stm.setString(6, employee.getUsername());
			stm.setString(7, "wait");
			stm.executeUpdate();
			return true;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	
	
	public static boolean assignPerformerToRequest(Connection con, Employee Performer, int id) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
			stm.setInt(1, id);
			stm.setString(2, "performance");
			Statement st = null;
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(request.id) FROM request;");
			if (rs.next()) {
				count = rs.getInt(1) + 1;
			} else
				count = 0;
			stm.setInt(3, count);
			stm.setDate(4, null);
			stm.setDate(5, null);
			stm.setString(6, Performer.getUsername());
			stm.setString(7, "wait");
			stm.executeUpdate();
			return true;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public static ArrayList<Employee> getEmployees(Connection con,String job) {
		Employee employee;
		ArrayList<Employee> list = new ArrayList<>();
		PreparedStatement st = null;
		try {
            st=con.prepareStatement("SELECT employee.* FROM employee WHERE job=?;");
            st.setString(1, job);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				employee = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
				list.add(employee);
			}
			return list;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Employee getSpecificEmployee(Connection con, String fullname) {
		PreparedStatement stm = null;
		String[] name = new String[2];
		name = fullname.split(" ");
		try {
			stm = con.prepareStatement("SELECT employee.* FROM employee WHERE first_name=? AND last_name=?;");
			stm.setString(1, name[0]);
			stm.setString(2, name[1]);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static void insertNotificationForUserToDB(Connection con, Notification n1, User employee) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("INSERT INTO notificationforuser VALUES(?,?);");
			stm.setInt(1, n1.getId());
			stm.setString(2, employee.getUsername());
			stm.executeUpdate();
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Request getRequestInfo(Connection con, int id) {
		PreparedStatement stm1 = null;
		PreparedStatement stm2 = null;
		String Initiatorname = null;
		String role = null;
		Request r = null;
		try {
			stm1 = con.prepareStatement("SELECT R.* FROM icm.request R WHERE id=?;");
			stm1.setInt(1, id);
			ResultSet rs = stm1.executeQuery();

			while (rs.next()) {
				String username = rs.getString(9);
				stm2 = con.prepareStatement("SELECT E.* FROM icm.employee E WHERE username=?;");
				stm2.setString(1, username);
				ResultSet rs2 = stm2.executeQuery();
				rs2.next();
				Initiatorname = rs2.getString(2) + " " + rs2.getString(3);
				role = rs2.getString(4);
				if (Initiatorname.equals(null)) {
					stm2 = con.prepareStatement("SELECT E.* FROM icm.student E WHERE username=?;");
					stm2.setString(1, rs.getString(9));
					rs2 = stm2.executeQuery();
					rs2.next();
					Initiatorname = rs2.getString(2) + " " + rs2.getString(3);
					role = "student";
				}
				if (!Initiatorname.equals(null)) {
					if(rs.getBytes(10)==null) {
					r = new Request(rs.getInt(7), Initiatorname, role, rs2.getString(8), rs.getString(8),
							rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), rs.getString(5),
							rs.getDate(6), new MyFile(),null);
					}
					else {
						MyFile myfile=new MyFile();
						myfile.setMybytearray(rs.getBytes(10));
						r = new Request(rs.getInt(7), Initiatorname, role, rs2.getString(8), rs.getString(8),
								rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), rs.getString(5),
								rs.getDate(6),myfile,rs.getString(11));
					}

				}
				rs2.close();
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;

	}

	public static RequestPhase getRequestTrack(Connection con, int id) {
		PreparedStatement stmRP=null;
		PreparedStatement stmR=null;
		RequestPhase rp=null;
		Request r=null;

		try {
			stmR=con.prepareStatement("SELECT R.* FROM icm.request R WHERE id=?;");
			stmR.setInt(1, id);
			ResultSet rs1 = stmR.executeQuery();
			if(rs1.next()) {
				r=new Request(rs1.getInt(7), rs1.getString(9),rs1.getString(8),rs1.getString(1),rs1.getDate(6));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			stmRP=con.prepareStatement(" SELECT RP.* FROM icm.requestinphase RP where request_id=? and state='work';");
			stmRP.setInt(1, id);
			ResultSet rs2 = stmRP.executeQuery();
			if(rs2.next())
			{
				rp=new RequestPhase(rs2.getDate(4), rs2.getDate(5), r,Enum.valueOf(Phase.class, rs2.getString(2)) , Enum.valueOf(State.class, rs2.getString(7)));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rp;
	}
	public static boolean insertDate(Connection con, int id, String[] d, Phase p) {
		
		d[0] = d[0].replaceAll("(\\r|\\n)", "");
        d[1] = d[1].replaceAll("(\\r|\\n)", "");
        PreparedStatement stm = null;
		Statement st = null;
		int maxRepetion=0;
		if(getStatus(con, id).equals("frozen"))
			return false;
			try {
				
				stm=con.prepareStatement("SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
				stm.setInt(1, id);
				
				stm.setString(2, p.toString());
				ResultSet rs = stm.executeQuery();	
	            if(rs.next()) {
	            	maxRepetion = rs.getInt(1);
	            	System.out.println(rs.getInt(1));
				PreparedStatement stm1 = con.prepareStatement("UPDATE icm.requestinphase"
						+ " SET start_date = ?, due_date = ?,state='waitingForApprove' "
						+ "WHERE (request_id = ? and phase=? and repetion=?);");
				stm1.setString(1, d[0]);
				stm1.setString(2,d[1]);
				stm1.setInt(3, id);
				stm1.setString(4, p.toString());
				stm1.setInt(5, maxRepetion);
				stm1.executeUpdate();
	            }
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		}
	

	public static Employee FindEmployee(Connection con, int id,String phase) {
		PreparedStatement stmR=null;
		PreparedStatement stmt=null;
		RequestPhase rp=null;
		Request r=null;
		try {
			try {
		  		PreparedStatement stm= con.prepareStatement("UPDATE requestinphase SET state=? WHERE request_id=?;");
		  		stm.setString(1, "over");
		  		stm.setInt(2, id);
		  		stm.executeUpdate();
		  		} 
		  		catch (SQLException e) {
		  			e.printStackTrace();
		  		}	
			stmR=con.prepareStatement("SELECT R.phase_administrator FROM icm.requestinphase R WHERE request_id=? AND phase=?;");
			stmR.setInt(1, id);
			stmR.setString(2, phase);
			ResultSet rs = stmR.executeQuery();			
            if(rs.next()) {
            	stmt=con.prepareStatement("SELECT R.* FROM icm.employee R WHERE username=?;");
            	stmt.setString(1, rs.getString(1));
            	ResultSet rs2 = stmt.executeQuery();
            	if(rs2.next()) {
            		return new Employee(rs2.getString(1),rs2.getString(2),rs2.getString(3),rs2.getString(8));
            	
            	}
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
	 public static Employee getChairman(Connection con) {
			Statement st = null;
			Employee Chairman=null;
			try {
				st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT employee.* FROM employee WHERE job='chairman';");
				if(rs.next())
					Chairman = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));	
				return Chairman;
			} catch (SQLException e) {
	// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}


	public static void updatePerfomanceFinishedInDB(Connection con, int id) {
		PreparedStatement stm=null;
		try {
			stm=con.prepareStatement("UPDATE requestinphase SET state='over' WHERE request_id=?;");
			stm.setInt(1, id);
			stm.executeUpdate();
			stm=con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
			stm.setInt(1, id);
			stm.setString(2, "testing");
			stm.setInt(3, 0);
			stm.setDate(4, null);
			stm.setDate(5, null);
			stm.setDate(6, null);
			stm.setString(7, "wait");
			stm.executeUpdate();
		}
			catch(SQLException e) {
				e.printStackTrace();
			}
			
		}

	public static void updateDBdueToFailTest(Connection con, int requestId) {
		PreparedStatement stm=null;
		int maxRepetion;
		try {
			stm=con.prepareStatement("UPDATE requestinphase SET state='over' WHERE phase='testing' AND request_id=?;");
			stm.setInt(1, requestId);
			stm.executeUpdate();
			stm=con.prepareStatement("SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase WHERE request_id=? AND phase='performance';");
			stm.setInt(1,requestId);
			ResultSet rs = stm.executeQuery();	
            if(rs.next()) {
            	maxRepetion = rs.getInt(1)+1;
            }
            else {
            	    maxRepetion=0;
            }
            stm=con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
            stm.setInt(1, requestId);
            stm.setString(2, "performance");
            stm.setInt(3,maxRepetion);
            stm.setDate(4,null);
            stm.setDate(5,null);
            stm.setString(6, null);
            stm.setString(7,"wait");
            stm.executeUpdate();
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void sendFailDetailsToInspector(Connection con, Notification n) {
		Statement st=null;
		Employee inspector = null;
		try {
			st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT username,first_name,last_name FROM employee WHERE job='inspector';");
			if(rs.next()) {
				inspector=new Employee(rs.getString(1),rs.getString(2),rs.getString(3));
			}
			mysqlConnection.insertNotificationForUserToDB(con, n, inspector);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void sendExtensionConfiramtionToInspector(Connection con, Notification n) {
		Statement st=null;
		Employee inspector = null;
		try {
			st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT username,first_name,last_name FROM employee WHERE job='inspector';");
			if(rs.next()) {
				inspector=new Employee(rs.getString(1),rs.getString(2),rs.getString(3));
			}
			mysqlConnection.insertNotificationForUserToDB(con, n, inspector);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void updateDBdueToSuccessTest(Connection con, int requestId) {
		
		PreparedStatement stm=null;
		int maxRepetion;
		try {
			stm=con.prepareStatement("UPDATE requestinphase SET state='over' WHERE phase='testing' AND request_id=?;");
			stm.setInt(1, requestId);
			stm.executeUpdate();
			
	    /*  stm=con.prepareStatement("SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase WHERE request_id=? AND phase='performance';");
			ResultSet rs = stm.executeQuery();	
            if(rs.next()) {
            	maxRepetion = rs.getInt(1);
            }
            maxRepetion=0;
            stm=con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
            stm.setInt(1, requestId);
            stm.setString(2, "performance");
            stm.setInt(3,maxRepetion);
            stm.setDate(4,null);
            stm.setDate(5,null);
            stm.setString(6, null);
            stm.setString(7,"wait");*/
            stm.executeUpdate();
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	   public static void addRequestToDB(Connection con,int id,String dec) {
	    	PreparedStatement stm = null;
	    	PreparedStatement stm2 = null;
	    	PreparedStatement stm3=null;
			try {
				if(dec.equals("approve")) {
				int Max=0;
				stm2 = con.prepareStatement("SELECT R.repetion FROM icm.requestinphase R WHERE request_id=? AND phase=?;");
				stm2.setInt(1, id);
				stm2.setString(2, "performance");
				ResultSet rs = stm2.executeQuery();	
				while(rs.next()) {
					if(rs.getInt(1)>=Max)
						Max=rs.getInt(1)+1;
				}
				stm = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
				stm.setInt(1, id);
				stm.setString(2, "performance");
				stm.setInt(3, Max);
				stm.setString(4, null);
				stm.setString(5, null);
				stm.setString(6, null);
				stm.setString(7, "wait");
				stm.executeUpdate();
				PreparedStatement stm5 = con.prepareStatement(
						"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
				stm.setInt(1, id);
				stm.setString(2, "decision");
				ResultSet rs2=stm5.executeQuery();
				if(rs2.next()) {
				stm3=con.prepareStatement("UPDATE icm.requestinphase SET state='over' WHERE request_id = ? and phase='decision' and repetion=?;");
				stm3.setInt(1, id);
				stm3.setInt(2, rs2.getInt(1));
				stm3.executeUpdate();
				}
				}
				else if(dec.equals("reject")) {
					stm = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
					stm.setInt(1, id);
					stm.setString(2, "closing");
					stm.setInt(3, 0);
					stm.setString(4, null);
					stm.setString(5, null);
					stm.setString(6, null);
					stm.setString(7, "wait");
					stm.executeUpdate();
					PreparedStatement stm5 = con.prepareStatement(
							"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
					stm.setInt(1, id);
					stm.setString(2, "decision");
					ResultSet rs2=stm5.executeQuery();
					/*
					if(rs2.next()) {
					stm3=con.prepareStatement("UPDATE icm.requestinphase SET state='over' WHERE request_id = ? and phase='decision' and repetion=?;");
					stm3.setInt(1, id);
					stm3.setInt(2, rs2.getInt(1));
					stm3.executeUpdate();
					}*/
				}
				else {
					int Max=0;
					stm2 = con.prepareStatement("SELECT R.repetion FROM icm.requestinphase R WHERE request_id=? AND phase=?;");
					stm2.setInt(1, id);
					stm2.setString(2, "evaluation");
					ResultSet rs = stm2.executeQuery();	
					while(rs.next()) {
						if(rs.getInt(1)>=Max)
							Max=rs.getInt(1);
					}
					stm = con.prepareStatement("INSERT INTO requestinphase VALUES(?,?,?,?,?,?,?);");
					stm.setInt(1, id);
					stm.setString(2, "evaluation");
					stm.setInt(3, Max+1);
					stm.setString(4, null);
					stm.setString(5, null);
					stm.setString(6, null);
					stm.setString(7, "wait");
					stm.executeUpdate();
					PreparedStatement stm5 = con.prepareStatement(
							"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? AND phase=?;");
					stm.setInt(1, id);
					stm.setString(2, "decision");
					ResultSet rs2=stm5.executeQuery();
					/*if(rs2.next()) {
					stm3=con.prepareStatement("UPDATE icm.requestinphase SET state='over' WHERE request_id = ? and phase='decision' and repetion=?;");
					stm3.setInt(1, id);
					stm3.setInt(2, rs2.getInt(1));
					stm3.executeUpdate();
					}*/
				}
			} catch (SQLException e) {
	// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

    public static void insertNotificationDetailsToDB(Connection con, Notification n1,String details) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("INSERT INTO notificationdetails VALUES(?,?);");
			stm.setInt(1, n1.getId());
			stm.setString(2, details);
			stm.executeUpdate();
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static String getnotificationdetails(Connection con,int id) {
    	PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("SELECT R.Details FROM icm.notificationdetails R WHERE notification_id=?;");
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
		    if(rs.next()) 
		    	return rs.getString(1);
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

	public static void insertReport(Connection con, EvaluationReport er) {
		PreparedStatement stm1 = null;
		Statement st = null;
		int maxRepetion = 0;
		try {
			PreparedStatement stm = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=?;");
			stm.setInt(1, er.getRequestID());
			ResultSet rs1 = stm.executeQuery();
			if (rs1.next()) {
				maxRepetion = rs1.getInt(1);
			}
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(evaluationreport.number) FROM evaluationreport;");
			if (rs.next()) {
				numOfReport = rs.getInt(1) + 1;
			} else
				numOfReport = 0;
			stm1 = con.prepareStatement("INSERT INTO icm.evaluationreport VALUES(?,?,?,?,?,?,?,?);");
			stm1.setInt(1, numOfReport);
			er.setId(numOfReport);
			stm1.setString(2, er.getLocation());
			stm1.setString(3, er.getDescription());
			stm1.setString(4, er.getExpectedResult());
			stm1.setString(5, er.getConstraints());
			stm1.setString(6, er.getRisks());
			stm1.setInt(7, er.getEstimatedPerfomanceDuration());
			stm1.setInt(8, er.getRequestID());
			stm1.executeUpdate();
			PreparedStatement stm2 = con.prepareStatement(
					"UPDATE icm.requestinphase SET state='over' WHERE request_id = ? and phase='evaluation' and repetion=?;");
			stm2.setInt(1, er.getRequestID());
			stm2.setInt(2, maxRepetion);
			stm2.executeUpdate();
			long millis = System.currentTimeMillis();
			Date Startdate = new java.sql.Date(millis);
			long week = Startdate.getTime() + (int) (1000 * 60 * 60 * 24 * 7);
			Date dueDate = new java.sql.Date(week);
			PreparedStatement stm3 = con.prepareStatement("INSERT INTO icm.requestinphase  VALUES(?,?,?,?,?,?,?) ");
			stm3.setInt(1, er.getRequestID());
			stm3.setString(2, "decision");
			stm3.setInt(3, maxRepetion);
			stm3.setDate(4, Startdate);
			stm3.setDate(5, dueDate);
			Employee chairman = mysqlConnection.getChairman(con);
			stm3.setString(6, chairman.getUsername());
			stm3.setString(7, "work");
			stm3.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		




	}

	public static RequestPhase getRequestPhase(Connection con, int id, String phase) {
		RequestPhase rp = null;
		PreparedStatement stm1 =null;
		PreparedStatement stm =null;
		int maxRepetion = 0;
		try {
			stm = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? and phase=?;");
			stm.setInt(1, id);
			stm.setString(2, phase);
			ResultSet rs1 = stm.executeQuery();
			if (rs1.next()) {
				maxRepetion = rs1.getInt(1);
			}
			stm1 = con.prepareStatement(
					"SELECT  requestinphase.* FROM icm.requestinphase where request_id=? and phase=? and repetion=?;");
			stm1.setInt(1, id);
			stm1.setString(2, phase);
			stm1.setInt(3, maxRepetion);
			ResultSet rs2 = stm1.executeQuery();
			if (rs2.next()) {
	
				rp = new RequestPhase(id,rs2.getDate(4), rs2.getDate(5), Enum.valueOf(Phase.class, phase),
						Enum.valueOf(State.class, rs2.getString(7)),rs2.getString(6));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return rp;
	}

	public static void getToWork(Connection con, Date today) {
		PreparedStatement stm=null;
		try {
			stm=con.prepareStatement("UPDATE requestinphase SET state='work' WHERE state='wait' AND start_date=?;");
			stm.setDate(1, today);
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static ArrayList<RequestPhase> detectExceptions(Connection con, Date today) {
		PreparedStatement stm=null;
		PreparedStatement stm1=null;
		ArrayList<RequestPhase> list=new ArrayList<>();
		try {
			stm=con.prepareStatement("SELECT request_id,phase,repetion FROM requestinphase WHERE state='work' AND due_date<?;");
			stm.setDate(1, today);
			ResultSet rs=stm.executeQuery();
			while(rs.next()) {
				stm1=con.prepareStatement("SELECT request_id FROM exception WHERE request_id=? AND phase=? AND repetion=?");
				stm1.setInt(1, rs.getInt(1));
				stm1.setString(2, rs.getString(2)); 
				stm1.setInt(3, rs.getInt(3));
				ResultSet rs1=stm1.executeQuery();
				if(!rs1.next()) {
				stm=con.prepareStatement("INSERT INTO exception (date,phase,request_id,repetion) VALUES(?,?,?,?);");
				stm.setDate(1, today);
				stm.setString(2, rs.getString(2));
				stm.setInt(3, rs.getInt(1));
				stm.setInt(4, rs.getInt(3));
				stm.executeUpdate();
				RequestPhase rp=new RequestPhase(rs.getInt(1),Enum.valueOf(Phase.class,rs.getString(2)),rs.getInt(3));
				list.add(rp);
				}
				
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public static Employee getAdmin(Connection con) {
		Statement st = null;
		Employee admin=null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT employee.* FROM employee WHERE job='administrator';");
			if(rs.next())
				admin = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));	
			return admin;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public static Employee getPhaseAdministrator(Connection con, int id, Phase phase, int repetion) {
		PreparedStatement stm=null;
		try {
			stm=con.prepareStatement("SELECT E.* FROM employee E,requestinphase P WHERE E.username=P.phase_administrator AND P.request_id=? AND P.phase=? AND P.repetion=?;");
			stm.setInt(1, id);
			stm.setString(2, phase.toString());
			stm.setInt(3, repetion);
			ResultSet rs=stm.executeQuery();
			if(rs.next()) {
				Employee em=new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));
				return em;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<RequestPhase> findWhatNeedsDocument(Connection con, Date today) {
		PreparedStatement stm=null;
		PreparedStatement stm1=null;
		ArrayList<RequestPhase> list=new ArrayList<>();
		try {
			stm=con.prepareStatement("SELECT X.request_id,X.phase,X.repetion,X.date FROM exception X,requestinphase P WHERE X.request_id=P.request_id AND X.phase=P.phase AND X.repetion=P.repetion AND X.overdue='-1' AND P.state='over';");
			ResultSet rs=stm.executeQuery();
			while(rs.next()) {
				RequestPhase rp=new RequestPhase(rs.getInt(1),Enum.valueOf(Phase.class, rs.getString(2)),rs.getInt(3));
				list.add(rp);
				stm1=con.prepareStatement("UPDATE exception SET overdue=? WHERE request_id=? AND phase=? AND repetion=?;");
				int overdue=(int)((today.getTime()-rs.getDate(4).getTime())/(24 * 60 * 60 * 1000));
				stm1.setInt(1, overdue);
				stm1.setInt(2, rs.getInt(1));
				stm1.setString(3, rs.getString(2));
				stm1.setInt(4, rs.getInt(3));
				stm1.executeUpdate();
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Employee getInspector(Connection con) {
		Statement st = null;
		Employee admin=null;
		try {
			st = con.createStatement();

			ResultSet rs = st.executeQuery("SELECT employee.* FROM employee WHERE job='inspector';");

			if(rs.next())
				admin = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(8));	
			return admin;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		
	}

	public static void addDocumentToException(Connection con, int id, String phase, int repetion, String document) {
		PreparedStatement stm=null;
		try {
			stm=con.prepareStatement("UPDATE exception SET documentation=? WHERE request_id=? AND phase=? AND repetion=?;");
			stm.setString(1, document);
			stm.setInt(2, id);
			stm.setString(3, phase);
			stm.setInt(4, repetion);
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static ArrayList<RequestPhase> whoNeedsReminding(Connection con, Date today) {
		PreparedStatement stm=null;
		PreparedStatement stm1=null;
		ArrayList<RequestPhase> list=new ArrayList<>();
		try {
			stm=con.prepareStatement("SELECT request_id,phase,repetion,phase_administrator FROM requestinphase WHERE due_date=?;");
			LocalDate tommorrow1=today.toLocalDate().plusDays(1);
			Date tommorrow=Date.valueOf(tommorrow1);
			stm.setDate(1, tommorrow);
			ResultSet rs=stm.executeQuery();
			while(rs.next()) {
				stm1=con.prepareStatement("SELECT reminder.* FROM reminder WHERE request_id=? AND phase=? AND repetion=?;");
				stm1.setInt(1, rs.getInt(1));
				stm1.setString(2, rs.getString(2));
				stm1.setInt(3, rs.getInt(3));
				ResultSet rs1=stm1.executeQuery();
				if(!rs1.next()) {
					stm1=con.prepareStatement("INSERT INTO reminder VALUES(?,?,?);");
					stm1.setInt(1, rs.getInt(1));
					stm1.setString(2, rs.getString(2));
					stm1.setInt(3, rs.getInt(3));
					stm1.executeUpdate();
					RequestPhase rp=new RequestPhase(rs.getInt(1),Enum.valueOf(Phase.class, rs.getString(2)),rs.getInt(3));
					list.add(rp);
				}
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public static String getStatus(Connection con,int id) {
		PreparedStatement st=null;
		String s=null;
		try {
			st=con.prepareStatement("SELECT * FROM icm.request Where id=?;");
			st.setInt(1, id);
			ResultSet rs=st.executeQuery();
			if(rs.next())
				s=rs.getString(8);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return s;
	}

	public static Boolean changestatus(Connection con,int id,String newstatus) {
		PreparedStatement stm=null;
		PreparedStatement stmt1=null;
		PreparedStatement stmt2=null;
		try {
			stm=con.prepareStatement("SELECT E.* FROM icm.request E WHERE status='active' AND id=?;");
			stm.setInt(1, id);
			ResultSet rs=stm.executeQuery();
			if(rs.next()) {
			stmt1 = con.prepareStatement("UPDATE request SET status=? WHERE id=?;");
			stmt1.setString(1, newstatus);
			stmt1.setInt(2, id);
			stmt1.executeUpdate();
			stmt2=con.prepareStatement("UPDATE requestinphase SET state='over' WHERE phase='closing' AND request_id=?;");
			stmt2.setInt(1, id);
			stmt2.executeUpdate();
			return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean FreazeRequest(Connection con,int id)
	{
		PreparedStatement stm=null;
		PreparedStatement stmt1=null;
		PreparedStatement stmt2=null;
		try {
			stmt2=con.prepareStatement("SELECT E.* FROM icm.request E WHERE id=?;");
			stmt2.setInt(1, id);
			ResultSet rs2=stmt2.executeQuery();
			rs2.next();
			if(!rs2.getString(8).equals("frozen")) {
			stm=con.prepareStatement("SELECT E.* FROM icm.request E WHERE id=?;");
			stm.setInt(1, id);
			ResultSet rs=stm.executeQuery();
			if(rs.next()) {
				System.out.println("ww");
			stmt1 = con.prepareStatement("UPDATE request SET status=? WHERE id=?;");
			stmt1.setString(1, "frozen");
			stmt1.setInt(2, id);
			stmt1.executeUpdate();
			return true;
			}
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return true;
	}
	public static void EnterFreazeToDBUpdateTable(Connection con,Employee Inspector,int requestid,String explain)
	{
		PreparedStatement stm=null;
		PreparedStatement stmt1=null;
		PreparedStatement stmt2=null;		
		try {
			
			stm = con.prepareStatement("INSERT INTO icm.update VALUES(?,?,?,?,?);");		
			stm.setString(1, Inspector.getUsername());
			stm.setString(2,Inspector.getFirstName()+Inspector.getLastName() );
			stm.setString(3, explain);
			long millis = System.currentTimeMillis();
			stm.setDate(4, new java.sql.Date(millis));
			stm.setInt(5, requestid);
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Map<Integer, String> getMap(Connection con) {
		PreparedStatement stm=null;
		Map<Integer,String> map=new HashMap<>();
		try {
			stm=con.prepareStatement("SELECT id,status FROM request;");
			ResultSet rs=stm.executeQuery();
			while(rs.next()) {
				map.put(rs.getInt(1), rs.getString(2));
			}
			return map;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;


		}	
	public static User getInitiatorUser(Connection con, int id) {
		PreparedStatement stm =null;
		PreparedStatement stmt2=null;
		String Initiatorname=null;
		User initiator=null;
		try {
			stm = con.prepareStatement("SELECT R.initiator_username FROM icm.request R WHERE id=?;");
			stm.setInt(1, id);
			ResultSet rs=stm.executeQuery();
			if(rs.next()) {
				stmt2 = con.prepareStatement("SELECT E.* FROM icm.employee E WHERE username=?;");
				stmt2.setString(1, rs.getString(1));	
				ResultSet rs2=stmt2.executeQuery();	
				if(rs2.next()) {
				Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
				initiator=new User(rs.getString(1),null,null);
				initiator.setEmail(rs2.getString(4));
				}
				if(Initiatorname.equals(null)) {			
					stmt2 = con.prepareStatement("SELECT E.* FROM icm.student E WHERE username=?;");
					stmt2.setString(1, rs.getString(1));
					rs2=stmt2.executeQuery();	
					if(rs2.next()) {
					Initiatorname=rs2.getString(2)+" "+rs2.getString(3);
					initiator=new User(rs.getString(1),null,null);
					initiator.setEmail(rs2.getString(5));
					}
				}	
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(initiator.getUsername());
		return initiator;
	}
	public static void changeState(Connection con,int id,Phase phase,State state) {
		RequestPhase rp = null;
		PreparedStatement stm1 =null;
		PreparedStatement stm =null;
		int maxRepetion = 0;
		try {
			stm = con.prepareStatement(
					"SELECT MAX(icm.requestinphase.repetion) FROM icm.requestinphase where request_id=? and phase=?;");
			stm.setInt(1, id);
			stm.setString(2, phase.toString());
			ResultSet rs1 = stm.executeQuery();
			if (rs1.next()) {
				maxRepetion = rs1.getInt(1);
			}
			stm1 = con.prepareStatement(
					"Update  icm.requestinphase SET state=? where request_id=? and phase=? and repetion=?;");
			stm1.setString(1, state.toString());
			stm1.setInt(2, id);
			stm1.setString(3, phase.toString());
			stm1.setInt(4, maxRepetion);
			stm1.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public static EvaluationReport getevaluationreport(Connection con,int request_id) {
		PreparedStatement stm =null;
		EvaluationReport report=null;
		try {
			stm=con.prepareStatement("SELECT E.* FROM evaluationreport E WHERE request_id=?;");
			stm.setInt(1, request_id);
			ResultSet rs=stm.executeQuery();
			int max=0;
			while(rs.next()) {
				if(rs.getInt(1)>=max) {
					max=rs.getInt(1);
					report=new EvaluationReport(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7),request_id);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	return report;
	}
}

