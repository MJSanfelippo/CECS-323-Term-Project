import java.sql.Connection;
import java.sql.PreparedStatement;
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
		System.out.println("Please enter the priority for this user story: ");
		int sprintPriority = Validator.checkInt();
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
