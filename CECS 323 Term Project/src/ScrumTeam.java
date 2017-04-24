import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ScrumTeam {

	int teamNumber;
	String teamName;
	
	public ScrumTeam(int teamNumber, String teamName){
		this.teamNumber = teamNumber;
		this.teamName = teamName;
	}
	public static ScrumTeam getScrumTeamInsertInformation() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the team number: ");
		int teamNumber = Validator.checkInt();
		System.out.println("Please enter the team name: ");
		String teamName = in.nextLine();
		return new ScrumTeam(teamNumber, teamName);
	}
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
