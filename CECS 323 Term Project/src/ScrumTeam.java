import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ScrumTeam {
	
	/**
	 * the team number, or its id
	 */
	int teamNumber;
	/**
	 * the name of the team
	 */
	String teamName;
	/**
	 * 
	 * @param teamNumber the team's id
	 * @param teamName the team's name
	 */
	public ScrumTeam(int teamNumber, String teamName){
		this.teamNumber = teamNumber;
		this.teamName = teamName;
	}
	/**
	 * Ask the user for info on the new scrum team
	 * @return a new scrum team containing the user's info
	 */
	public static ScrumTeam getScrumTeamInsertInformation() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the team number: ");
		int teamNumber = Validator.checkInt();
		System.out.println("Please enter the team name: ");
		String teamName = in.nextLine();
		return new ScrumTeam(teamNumber, teamName);
	}
	/**
	 * inserts a new scrum team to the database 
	 * @param conn the connection to the database
	 */
	public void insertNewScrumTeam(Connection conn) {
		String sql = "INSERT INTO scrum_team VALUES (?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, teamNumber);
			ps.setString(2, teamName);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
