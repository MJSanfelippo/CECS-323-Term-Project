import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class Project {
	
	/**
	 * The project code, or its ID
	 */
	int projectCode;
	/**
	 * The start date of the project
	 */
	String startDate;
	/**
	 * The project's deadline
	 */
	String deadline;
	/**
	 * The name of the project
	 */
	String name;
	/**
	 * 
	 * @param projectCode the id of the project
	 * @param startDate the start date of the project
	 * @param deadline the deadline of the project
	 * @param name the name of the project
	 */
	public Project(int projectCode, String startDate, String deadline, String name) {
		this.projectCode = projectCode;
		this.startDate = startDate;
		this.deadline = deadline;
		this.name = name;
	}
	/**
	 * The menu to update something about a project
	 * @return the user's choice as to what to update about the project
	 */
	public static int updateMenu(){
		System.out.println("1. Deadline");
		System.out.println("2. Name");
		int choice = Validator.checkIntRange(1,2);
		return choice;
	}
	/**
	 * this will update a project based on the project's id and what the user wants to update about it
	 * @param conn the connection to the datbaase
	 */
	public static void updateProject(Connection conn){
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the ID of the project you'd like to update: ");
		int id = Validator.checkInt();
		String sql = "";
		int choice = updateMenu();
		switch (choice){
		case 1:
			System.out.println("What is the new deadline?");
			String deadline = Validator.validateDate();
			sql = "UPDATE project SET deadline = ? WHERE projectCode = ?;";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, deadline);
				ps.setInt(2, id);
				ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				CustomErrorMessages.printSQLException(e);
			}
			break;
		case 2:
			System.out.println("What is the new name?");
			String name = in.nextLine();
			sql = "UPDATE project SET name = ? WHERE projectCode = ?;";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, name);
				ps.setInt(2, id);
				ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				CustomErrorMessages.printSQLException(e);
			}
			break;
			
		}
	}
	/**
	 * this will list all projects 
	 * @param conn the connection to the database
	 */
	public static void listAllProjects(Connection conn){
		String sql = "SELECT * FROM project";
		try{
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet results = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = results.getMetaData();
			int numCols = rsmd.getColumnCount();
			for (int i = 1; i <= numCols; i++) {
				System.out.print("\t" + rsmd.getColumnLabel(i) + "\t\t\t");
			}
			System.out.println(
					"\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			while (results.next()) {
				int projectCode = results.getInt(1);
				String startDate = results.getString(2);
				String name = results.getString(3);
				String deadline = results.getString(4);
				System.out.format("%15d%43s%47s%25s\n", projectCode, startDate, name, deadline);
			}
		} catch(SQLException e){
			CustomErrorMessages.printSQLException(e);
		}
	}
	/**
	 * this will insert a new project to the database
	 * @param conn the connection to the database
	 */
	public void insertNewProject(Connection conn) {
		String sql = "INSERT INTO project VALUES (?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, projectCode);
			ps.setString(2, startDate);
			ps.setString(3, deadline);
			ps.setString(4, name);
			ps.execute();
		} catch (SQLException e) {
			CustomErrorMessages.printSQLException(e);
		}
	}
	/**
	 * This will get information about a project
	 * @return the project the user has created
	 */
	public static Project getProjectInsertInformation() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the project's ID: ");
		int projectCode = Validator.checkInt();
		System.out.println("Please enter the project's start date in the format yyyy-MM-dd: ");
		String startDate = Validator.validateDate();
		System.out.println("Please enter the project's deadline in the format yyyy-MM-dd: ");
		String deadline = Validator.validateDate();
		System.out.println("Please enter the project's name: ");
		String name = in.nextLine();
		return new Project(projectCode, startDate, deadline, name);
	}
}
