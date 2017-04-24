import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class Project {

	int projectCode;
	String startDate;
	String deadline;
	String name;

	public Project(int projectCode, String startDate, String deadline, String name) {
		this.projectCode = projectCode;
		this.startDate = startDate;
		this.deadline = deadline;
		this.name = name;
	}
	public int updateMenu(){
		System.out.println("1. Deadline");
		System.out.println("2. Name");
		int choice = Validator.checkIntRange(1,2);
		return choice;
	}
	public void updateProject(Connection conn){
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
				e.printStackTrace();
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
				e.printStackTrace();
			}
			break;
			
		}
	}
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
			e.printStackTrace();
		}
	}
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
			e.printStackTrace();
		}
	}

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
