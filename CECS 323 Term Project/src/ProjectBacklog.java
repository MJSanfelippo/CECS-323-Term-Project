import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ProjectBacklog {

	/**
	 * the id of the project
	 */
	private int projectCode;
	/**
	 * the id of the user story
	 */
	private int userStoryID;
	/**
	 * the name... I'm not really sure name of what
	 */
	private String name;
	/**
	 * the creation date of the project in the backlog
	 */
	private String creationDate;
	
	/**
	 * 
	 * @param projectCode the id of the project
	 * @param userStoryID the id of the user story
	 * @param name the name 
	 * @param creationDate the date the project was put in the backlog
	 */
	public ProjectBacklog(int projectCode, int userStoryID, String name, String creationDate) {
		this.projectCode = projectCode;
		this.userStoryID = userStoryID;
		this.name = name;
		this.creationDate = creationDate;
	}
	/**
	 * deletes a user story from the project backlog
	 * @param conn
	 */
	public static void deleteUserStoryFromProjectBacklog(Connection conn){
		System.out.println("Please enter the project code of the project in the backlog: ");
		int projectCode = Validator.checkInt();
		System.out.println("Please enter the user story ID that you'd like to delete from the project backlog: ");
		int userStoryID = Validator.checkInt();
		String sql = "DELETE FROM project_backlog WHERE project_backlog.projectCode = ? AND userStoryID = ?;";
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, projectCode);
			ps.setInt(2, userStoryID);
			ps.execute();
		} catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	/**
	 * gets info from the user for a project backlog
	 * @return a new project backlog object containing all info the user entered
	 */
	public static ProjectBacklog getProjectBacklogInsertInformation() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the project code: ");
		int projectCode = Validator.checkInt();
		System.out.println("Please enter the user story ID: ");
		int userStoryID = Validator.checkInt();
		System.out.println("Please enter the name: ");
		String name = in.nextLine();
		System.out.println("Please enter the creation date of this project in the format yyyy-MM-dd: ");
		String creationDate = Validator.validateDate();
		return new ProjectBacklog(projectCode, userStoryID, name, creationDate);
	}
	/**
	 * inserts a new user story into the project backlog
	 * @param conn the connection to the database
	 */
	public void insertNewProjectBacklog(Connection conn) {
		String sql = "INSERT INTO project_backlog VALUES (?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, projectCode);
			ps.setInt(2, userStoryID);
			ps.setString(3, name);
			ps.setString(4, creationDate);
			ps.execute();
		} catch (SQLException e) {
			CustomErrorMessages.printSQLException(e);
		}
	}
}
