import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class Sprint {
	
	/**
	 * the sprint's number, or id
	 */
	int sprintNum;
	/**
	 * the project's id
	 */
	int projectCode;
	/**
	 * whether the sprint is active 
	 */
	boolean active;
	/**
	 * the start date of the sprint
	 */
	String startDate;
	/** 
	 * the end date of the sprint
	 */
	String endDate;
	/**
	 * the id of the team that has been assigned to this sprint
	 */
	int teamNum;
	
	/**
	 * 
	 * @param sprintNum the sprint's id
	 * @param projectCode the project's id
	 * @param active whether this sprint is active
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param teamNum the team id assigned to this sprint
	 */
	public Sprint(int sprintNum, int projectCode, boolean active, String startDate, String endDate, int teamNum) {
		this.sprintNum = sprintNum;
		this.projectCode = projectCode;
		this.active = active;
		this.startDate = startDate;
		this.endDate = endDate;
		this.teamNum = teamNum;
	}
	/**
	 * this will list all sprints
	 * @param conn the connection to the database
	 */
	public static void listAllSprints(Connection conn){
		String sql = "SELECT * FROM sprint";
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet results = ps.executeQuery();
			ResultSetMetaData rsmd = results.getMetaData();
			int numCols = rsmd.getColumnCount();
			for (int i = 1; i <= numCols; i++) {
				System.out.print("\t" + rsmd.getColumnLabel(i) + "\t\t");
			}
			System.out.println(
					"\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			while (results.next()) {
				int sprintNum = results.getInt(1);
				int projectCode = results.getInt(2);
				boolean active = results.getBoolean(3);
				String startDate = results.getString(4);
				String endDate = results.getString(5);
				int teamNum = results.getInt(6);
				System.out.format("%15d%30d%33s%28s%30s%22d\n", sprintNum, projectCode, active, startDate, endDate, teamNum);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	/**
	 * this will list all developers on a specific sprint
	 * @param conn the connection to the database
	 */
	public static void listAllDevelopersOnSprint(Connection conn){
		System.out.println("Please enter what sprint number you'd like to search for: ");
		int sprintNum = Validator.checkInt();
		String sql = "SELECT employee.employeeID, CONCAT(employee.firstName, ' ', employee.lastName) AS name FROM employee INNER JOIN (SELECT * FROM employee_scrumTeam WHERE EXISTS "
				+ "(SELECT * FROM sprint WHERE sprintNum=? AND sprint.teamNum=employee_scrumTeam.teamNum)) AS employee_scrumTeam USING (employeeID);"; // I'm sorry
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,sprintNum);
			ResultSet results = ps.executeQuery();
			ResultSetMetaData rsmd = results.getMetaData();
			int numCols = rsmd.getColumnCount();
			for (int i = 1; i <= numCols; i++) {
				System.out.print("\t" + rsmd.getColumnLabel(i) + "\t\t");
			}
			System.out.println(
					"\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			while (results.next()) {
				int employeeID = results.getInt(1);
				String name = results.getString(2);
				System.out.format("%15d%30s\n", employeeID, name);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	/**
	 * this will get all info from the user to create a new sprint
	 * @return the sprint containing all info about it
	 */
	public static Sprint getSprintInsertInformation() {
		System.out.println("Please enter the sprint number: ");
		int sprintNum = Validator.checkInt();
		System.out.println("Please enter the project code: ");
		int projectCode = Validator.checkInt();
		System.out.println("Please enter whether this sprint is active: Y/N");
		boolean active = Validator.validateBoolean();
		System.out.println("Please enter the start date of this sprint in the format yyyy-MM-dd: ");
		String startDate = Validator.validateDate();
		System.out.println("Please enter the end date of this sprint in the format yyyy-MM-dd: ");
		String endDate = Validator.validateDate();
		System.out.println("Please enter the team number of the team participating in this sprint: ");
		int teamNum = Validator.checkInt();
		return new Sprint(sprintNum, projectCode, active, startDate, endDate, teamNum);
	}
	/**
	 * inserts a new sprint into the database
	 * @param conn the connection to the database
	 */
	public void insertNewSprint(Connection conn) {
		String sql = "INSERT INTO sprint VALUES (?,?,?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, sprintNum);
			ps.setInt(2, projectCode);
			ps.setBoolean(3, active);
			ps.setString(4, startDate);
			ps.setString(5, endDate);
			ps.setInt(6, teamNum);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
