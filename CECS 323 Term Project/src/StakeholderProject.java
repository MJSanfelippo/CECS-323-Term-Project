import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class StakeholderProject {
	
	int stakeholderID;
	int projectCode;
	int interestLevel;
	String type;
	
	public StakeholderProject (int stakeholderID, int projectCode, int interestLevel, String type){
		this.stakeholderID = stakeholderID;
		this.projectCode = projectCode;
		this.interestLevel = interestLevel;
		this.type = type;
	}
	public static void listAllStakeholderProjects(Connection conn){
		System.out.println("Please enter a project code to view all stakeholders for that project");
		int projectCode = Validator.checkInt();
		String sql = "SELECT stakeholder.stakeholderID, projectCode, interestLevel, firstName, lastName, type FROM stakeholder JOIN stakeholder_project"
				+ " ON stakeholder.stakeholderID = stakeholder_project.stakeholderID WHERE projectCode = ?;";
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, projectCode);
			ResultSet results = ps.executeQuery();
			ResultSetMetaData rsmd = results.getMetaData();
			int numCols = rsmd.getColumnCount();
			for (int i = 1; i <= numCols; i++) {
				System.out.print("\t" + rsmd.getColumnLabel(i) + "\t\t");
			}
			System.out.println(
					"\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			while (results.next()) {
				int stakeholderID = results.getInt(1);
				int interestLevel = results.getInt(3);
				String firstName = results.getString(4);
				String lastName = results.getString(5);
				String type = results.getString(6);
				System.out.format("%15d%30d%30d%35s%32s%30s\n", stakeholderID, projectCode, interestLevel, firstName, lastName, type);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	public static StakeholderProject getStakeholderProjectInsertInformation() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the project's ID: ");
		int projectCode = Validator.checkInt();
		System.out.println("Please enter the stakeholder's ID: ");
		int id = Validator.checkInt();
		System.out.println("Please enter the interest level of the stakeholder in the project (1-10): ");
		int interestLevel = Validator.checkIntRange(1, 10);
		System.out.println("What type of stakeholder is he/she (e.g. User, Advisor, Investor, etc.)?");
		String type = in.nextLine();
		return new StakeholderProject(id, projectCode, interestLevel, type);
	}
	
	public void insertNewStakeholderProject(Connection conn) {
		String sql = "INSERT INTO stakeholder_project VALUES (?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, stakeholderID);
			ps.setInt(2, projectCode);
			ps.setInt(3, interestLevel);
			ps.setString(4, type);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
