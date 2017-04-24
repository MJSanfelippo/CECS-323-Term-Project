import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class SprintBacklog {
	private int projectCode;
	private int userStoryID;
	private int sprintNum;
	private String progress;
	private int sprintPriority;
	
	public SprintBacklog(int projectCode, int userStoryID, int sprintNum, String progress, int sprintPriority) {
		this.projectCode = projectCode;
		this.userStoryID = userStoryID;
		this.sprintNum = sprintNum;
		this.progress = progress;
		this.sprintPriority = sprintPriority;
	}
	public static void listAllSprintBacklogsForASprint(Connection conn){
		System.out.println("Please enter the sprint number: ");
		int sprintNum = Validator.checkInt();
		System.out.println("Please enter the project code: ");
		int projectCode = Validator.checkInt();
		String sql = "SELECT sprint_backlog.userStoryID, progress, priority, asA, IwantTo, becauseSoThat, userClass FROM sprint_backlog INNER JOIN userStory"
				+ " ON sprint_backlog.userStoryID = userStory.userStoryID WHERE sprint_backlog.sprintNum = ? AND sprint_backlog.projectCode = ?;";
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, sprintNum);
			ps.setInt(2, projectCode);
			ResultSet results = ps.executeQuery();
			ResultSetMetaData rsmd = results.getMetaData();
			int numCols = rsmd.getColumnCount();
			for (int i = 1; i <= numCols; i++) {
				System.out.print(rsmd.getColumnLabel(i) + "\t\t\t");
			}
			System.out.println(
					"\n-----------------------------------------------------"
					+ "-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			while (results.next()) {
				int userStoryID = results.getInt(1);
				String progress = results.getString(2);
				int priority = results.getInt(3);
				String asA = results.getString(4);
				String IwantTo = results.getString(5);
				String becauseSoThat = results.getString(6);
				String userClass = results.getString(7);
				System.out.format("%5d%35s%30d%30s%30s%40s%23s\n", userStoryID, progress, priority, asA, IwantTo, becauseSoThat, userClass);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	public static void deleteUserStory(Connection conn){
		System.out.println("Please enter the id of the user story you'd like to remove: ");
		int userStoryID = Validator.checkInt();
		System.out.println("Please enter the sprint number from which this user story will be removed: ");
		int sprintNum = Validator.checkInt();
		String sql = "DELETE FROM sprint_backlog WHERE userStoryID = ? AND sprintNum = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userStoryID);
			ps.setInt(2, sprintNum);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int updateMenu(){
		System.out.println("1. Progress");
		System.out.println("2. Priority");
		int choice = Validator.checkIntRange(1, 2);
		return choice;
	}
	public static void updateUserStoryInSprintBacklog(Connection conn){
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the id of the user story you'd like to update: ");
		int userStoryID = Validator.checkInt();
		System.out.println("Please enter the sprint number the user story belongs to: ");
		int sprintNum = Validator.checkInt();
		String sql = "";
		int choice = updateMenu();
		switch(choice){
		case 1:
			System.out.println("What is the new progress?");
			String progress = in.nextLine();
			sql = "UPDATE sprint_backlog SET progress = ? WHERE userStoryID = ? AND sprintNum = ?";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, progress);
				ps.setInt(2, userStoryID);
				ps.setInt(3, sprintNum);
				ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("What is the new priority? (1-10)");
			int priority = Validator.checkIntRange(1, 10);
			sql = "UPDATE sprint_backlog SET priority = ? WHERE userStoryID = ? AND sprintNum = ?";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, priority);
				ps.setInt(2, userStoryID);
				ps.setInt(3, sprintNum);
				ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		
	}
	public static SprintBacklog getSprintBacklogInsertInformation() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the project code: ");
		int projectCode = Validator.checkInt();
		System.out.println("Please enter the user story ID: ");
		int userStoryID = Validator.checkInt();
		System.out.println("Please enter the sprint number: ");
		int sprintNum = Validator.checkInt();
		System.out.println("Please enter the progress: ");
		String progress = in.nextLine();
		System.out.println("Please enter the priority for this user story (1-10): ");
		int sprintPriority = Validator.checkIntRange(1,10);
		return new SprintBacklog(projectCode, userStoryID, sprintNum, progress, sprintPriority);
	}
	public void insertNewUserStory(Connection conn) {
		String sql = "INSERT INTO project_backlog VALUES (?,?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, projectCode);
			ps.setInt(2, userStoryID);
			ps.setInt(3, sprintNum);
			ps.setString(4, progress);
			ps.setInt(5, sprintPriority);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
