import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeScrumTeam {
	
	/**
	 * the employee's id
	 */
	int employeeID;
	/**
	 * the team's id
	 */
	int teamNumber;
	/**
	 * whether the employee is the leader of this team
	 */
	boolean isTeamLead;
	
	/**
	 * 
	 * @param employeeID the id of the employee
	 * @param teamNumber the id of the team
	 * @param isTeamLead whether the employee is a leader of this team
	 */
	public EmployeeScrumTeam(int employeeID, int teamNumber, boolean isTeamLead){
		this.employeeID = employeeID;
		this.teamNumber = teamNumber;
		this.isTeamLead = isTeamLead;
	}
	/**
	 * deletes an employee from a team based off of their id and the id of the team they're being removed from
	 * @param conn the connection to the database
	 */
	public static void deleteEmployeeFromTeam(Connection conn){
		System.out.println("Please enter the team number for the team from which you'd like to remove an employee: ");
		int teamNumber = Validator.checkInt();
		System.out.println("Please enter the ID of the employee you'd like to remove from this team: ");
		int employeeID = Validator.checkInt();
		String sql = "DELETE FROM employee_scrumTeam WHERE employeeID = ? AND teamNum = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeID);
			ps.setInt(2, teamNumber);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * this inserts a new employee into a scrum team
	 * @param conn the connection to the database
	 */
	public void insertNewEmployeeScrumTeam(Connection conn) {
		String sql = "INSERT INTO employee_scrumTeam VALUES (?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeID);
			ps.setInt(2, teamNumber);
			ps.setBoolean(3, isTeamLead);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * this allows the user to enter the employee's id, the team's id and whether the employee is a leader of this team and creates a new object containing this info
	 * @return
	 */
	public static EmployeeScrumTeam getEmployeeScrumTeamInsertInformation() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the employee ID: ");
		int employeeID = Validator.checkInt();
		System.out.println("Please enter the team number: ");
		int teamNumber = Validator.checkInt();
		System.out.println("Is the employee a team leader for this scrum team? Y/N");
		boolean isTeamLead = Validator.validateBoolean();
		return new EmployeeScrumTeam(employeeID, teamNumber, isTeamLead);
	}
	/**
	 * this will list all employees that are on scrum teams
	 * @param conn
	 */
	public static void listAllEmployeeScrumTeam(Connection conn){
		String sql = "SELECT * FROM employee_scrumTeam";
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
				int employeeID = results.getInt(1);
				int teamNumber = results.getInt(2);
				boolean isTeamLead = results.getBoolean(3);
				System.out.format("%15d%30d%25s\n", employeeID, teamNumber, isTeamLead);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
}
