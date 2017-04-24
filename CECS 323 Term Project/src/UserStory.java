import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UserStory {

	private int userStoryID;
	private String asA;
	private String iWantTo;
	private String becauseSoThat;
	private String type;
	public UserStory(int userStoryID, String asA, String iWantTo, String becauseSoThat, String type) {
		this.userStoryID = userStoryID;
		this.asA = asA;
		this.iWantTo = iWantTo;
		this.becauseSoThat = becauseSoThat;
		this.type = type;
	}
	
	public static UserStory getUserStoryInsertInformation() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the user story ID: ");
		int userStoryID = Validator.checkInt();
		System.out.println("Please enter the 'as a...' part of the user story: ");
		String asA = in.nextLine();
		System.out.println("Please enter the 'I want...' part of the user story: ");
		String iWantTo = in.nextLine();
		System.out.println("Please enter the 'so that...' part of the user story: ");
		String becauseSoThat = in.nextLine();
		System.out.println("Please enter the type of user story this is (e.g. GUI, database, etc.): ");
		String type = in.nextLine();
		return new UserStory(userStoryID, asA, iWantTo, becauseSoThat, type);
	}
	public void insertNewUserStory(Connection conn) {
		String sql = "INSERT INTO userStory VALUES (?,?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userStoryID);
			ps.setString(2, asA);
			ps.setString(3, iWantTo);
			ps.setString(4, becauseSoThat);
			ps.setString(5, type);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
