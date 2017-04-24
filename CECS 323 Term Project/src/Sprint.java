import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class Sprint {

	int sprintNum;
	int projectCode;
	boolean active;
	String startDate;
	String endDate;
	int teamNum;

	public Sprint(int sprintNum, int projectCode, boolean active, String startDate, String endDate, int teamNum) {
		this.sprintNum = sprintNum;
		this.projectCode = projectCode;
		this.active = active;
		this.startDate = startDate;
		this.endDate = endDate;
		this.teamNum = teamNum;
	}
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
