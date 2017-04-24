import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UserStorySprint {

	int userStoryID;
	int sprintNum;
	String startDate;
	String endDate;
	String progress;
	
	public UserStorySprint(int userStoryID, int sprintNum, String startDate, String endDate, String progress) {
		this.userStoryID = userStoryID;
		this.sprintNum = sprintNum;
		this.startDate = startDate;
		this.endDate = endDate;
		this.progress = progress;
	}
	
	public static UserStorySprint getUserStorySprintInsertInformation() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the user story ID: ");
		int userStoryID = Validator.checkInt();
		System.out.println("Please enter the sprint number: ");
		int sprintNum = Validator.checkInt();
		System.out.println("Please enter the start date in the format yyyy-MM-dd: ");
		String startDate = Validator.validateDate();
		System.out.println("Please enter the goal end date of the user story in this sprint: ");
		String endDate = Validator.validateDate();
		System.out.println("Please enter the progress of the user story in this sprint: ");
		String progress = in.nextLine();
		return new UserStorySprint(userStoryID, sprintNum, startDate, endDate, progress);
	}
	public void insertNewUserStory(Connection conn) {
		String sql = "INSERT INTO userSprintStory VALUES (?,?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userStoryID);
			ps.setInt(2, sprintNum);
			ps.setString(3, startDate);
			ps.setString(4, endDate);
			ps.setString(5, progress);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
