import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class StakeholderProject {
	
	/**
	 * the id of the stakeholder
	 */
	int stakeholderID;
	/**
	 * the project id
	 */
	int projectCode;
	/**
	 * the interest level of the stakeholder in the project
	 */
	int interestLevel;
	/**
	 * the type of stakeholder for this project
	 */
	String type;
	
	/**
	 * 
	 * @param stakeholderID the id of the stakeholder
	 * @param projectCode the id of the project
	 * @param interestLevel the interest level of of the stakeholder in this project
	 * @param type the type of stakeholder for this project
	 */
	public StakeholderProject (int stakeholderID, int projectCode, int interestLevel, String type){
		this.stakeholderID = stakeholderID;
		this.projectCode = projectCode;
		this.interestLevel = interestLevel;
		this.type = type;
	}
	/**
	 * the menu to allow the update for the stakeholders in the projects
	 * @return the user's choice as to what to update
	 */
	public static int showUpdateMenu(){
		System.out.println("What would you like to update?");
		System.out.println("1. Interest level");
		System.out.println("2. Stakeholder type");
		int choice = Validator.checkIntRange(1,2);
		return choice;
	}
	/**
	 * updates a stakeholder based off their id, the project id and what needs to be updated
	 * @param conn the connection to the database
	 */
	public static void updateStakeholderProject(Connection conn){
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the stakeholder's ID: ");
		int id = Validator.checkInt();
		System.out.println("Please enter the project code: ");
		int projectCode = Validator.checkInt();
		String sql = "";
		int choice = showUpdateMenu();
		switch(choice){
		case 1:
			System.out.println("What is the stakeholder's new interest level in this project? (1-10)");
			int interestLevel = Validator.checkIntRange(1, 10);
			sql = "UPDATE stakeholder_project SET stakeholder_project.interestLevel = ? WHERE stakeholder_project.stakeholderID = ? AND stakeholder_project.projectCode = ?;";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, interestLevel);
				ps.setInt(2, id);
				ps.setInt(3, projectCode);
				ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("What is the stakeholder's new type for this project?");
			String type = in.nextLine();
			sql = "UPDATE stakeholder_project SET stakeholder_project.type = ? WHERE stakeholder_project.stakeholderID = ? AND stakeholder_project.projectCode = ?;";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, type);
				ps.setInt(2, id);
				ps.setInt(3, projectCode);
				ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
			
		}
	}
	/**
	 * Deletes a stakeholder from a project based off of the stakeholder's id and the project's id
	 * @param conn the connection to the database
	 */
	public static void deleteStakeholderProject(Connection conn){
		System.out.println("Please enter the id of the stakeholder you'd like to delete: ");
		int id = Validator.checkInt();
		System.out.println("Please enter the project code: ");
		int projectCode = Validator.checkInt();
		String sql = "DELETE FROM stakeholder_project WHERE stakeholder_project.stakeholderID = ? AND stakeholder_project.projectCode = ?";
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, projectCode);
			ps.execute();
		} catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	/**
	 * Lists all stakeholders for a certain project
	 * @param conn the connection to the database
	 */
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
	/**
	 * this will get info from the user to insert a stakeholder into a project
	 * @return a new stakeholder_project containing all info necessary to be inserted into the database
	 */
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
	/**
	 * inserts a stakeholder and project into the assocation table in the datbase
	 * @param conn the connection to the database
	 */
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
