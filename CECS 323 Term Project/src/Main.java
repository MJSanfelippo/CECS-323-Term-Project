import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class Main {

	private String dbURL = "jdbc:mysql://192.168.62.130:3306/Dummy323?" + "user=cecs323b&password=cecs323";
	/**
	 * the connection to the database, initially set to null
	 */
	private Connection conn = null;
	/**
	 * the statement used to query the database
	 */
	private Statement statement = null;
	
	public static void main(String[] args) throws SQLException, IOException{
		new Main().run();
	}
	/**
	 * This will create the connection to the database
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public void run(){
		createConnection();
		boolean running = true;
		while (running){
			int choice = displayMainMenu();
			switch (choice){
			case 1:
				runCRUDStakeholder(displayCRUDStakeholdersMenu());
				break;
			case 2:
				runCRUDScrumTeamMembers(displayCRUDScrumTeamMembersMenu());
				break;
			case 3:
				runCRUDUserStoriesToProjectBackLog(displayCRUDUserStoriesToProjectBacklogMenu());
				break;
			case 4:
				Sprint s = Sprint.getSprintInsertInformation();
				s.insertNewSprint(conn);
				break;
			case 5:
				runUserStoryToSprintBacklog(displayUserStoryToSprintBacklogMenu());
				break;
			case 6:
				runCRUDOperationsSprintBacklog(displayCRUDOperationsSprintBacklogMenu());
				break;
			case 7:
				Employee.listEmployee(conn);
				break;
			case 8:
				Sprint.listAllSprints(conn);
			case 9:
				listAllDevelopersInASprint();
				break;
			case 10:
				running = false;
				break;
			default:
				break;
			}
		}
		shutDown();
	}
	public void runCRUDUserStoriesToProjectBackLog(int choice){
		switch(choice){
		case 1:
			UserStory us = UserStory.getUserStoryInsertInformation();
			us.insertNewUserStory(conn);
			ProjectBacklog pb = ProjectBacklog.getProjectBacklogInsertInformation();
			pb.insertNewUserStory(conn);
			break;
		case 2:
			pb = ProjectBacklog.getProjectBacklogInsertInformation();
			pb.insertNewUserStory(conn);
			break;
		case 3:
			ProjectBacklog.deleteUserStoryFromProjectBacklog(conn);
			break;
		default:
			break;
		}
	}
	public int displayCRUDUserStoriesToProjectBacklogMenu(){
		System.out.println("1. Add a new user story to the project backlog");
		System.out.println("2. Add an existing user story to the project backlog");
		System.out.println("3. Delete a user story from the project backlog");
		int choice = Validator.checkIntRange(1, 3);
		return choice;
	}
	public void runCRUDScrumTeamMembers(int choice){
		switch(choice){
		case 1:
			EmployeeScrumTeam est = EmployeeScrumTeam.getEmployeeScrumTeamInsertInformation();
			est.insertNewStakeholderProject(conn);
			break;
		case 2:
			EmployeeScrumTeam.deleteEmployeeFromTeam(conn);
			break;
		case 3:
			ScrumTeam st = ScrumTeam.getScrumTeamInsertInformation();
			st.insertNewScrumTeam(conn);
			break;
		default:
			break;
		}
	}
	public void runCRUDStakeholder(int choice){
		switch(choice){
		case 1:
			Stakeholder s = Stakeholder.getStakeholderInsertInformation();
			s.insertNewStakeholder(conn);
			break;
		case 2:
			Stakeholder.listAllStakeholders(conn);
			break;
		case 3:
			Stakeholder.updateStakeholder(conn);
			break;
		case 4:
			Stakeholder.deleteStakeholder(conn);
			break;
		case 5:
			StakeholderProject sp = StakeholderProject.getStakeholderProjectInsertInformation();
			sp.insertNewStakeholderProject(conn);
			break;
		case 6:
			StakeholderProject.deleteStakeholderProject(conn);
			break;
		case 7:
			StakeholderProject.updateStakeholderProject(conn);
			break;
		default:
			break;
		}
	}
	public int displayCRUDStakeholdersMenu(){
		System.out.println("1. Add a new stakeholder");
		System.out.println("2. List all stakeholders");
		System.out.println("3. Update a stakeholder");
		System.out.println("4. Delete a stakeholder");
		System.out.println("5. Add an existing stakeholder to a project");
		System.out.println("6. Delete an existing stakeholder from a project");
		System.out.println("7. Update an existing stakeholder's interest or position in a project");
		int choice = Validator.checkIntRange(1,7);
		return choice;
	}
	public int displayCRUDScrumTeamMembersMenu(){
		System.out.println("1. Add an employee to a scrum team");
		System.out.println("2. Remove an employee from a scrum team");
		System.out.println("3. Create new scrum team");
		int choice = Validator.checkIntRange(1, 3);
		return choice;
	}
	public void createSprint(){
		Sprint s = Sprint.getSprintInsertInformation();
		s.insertNewSprint(conn);
	}
	public void runCRUDOperationsSprintBacklog(int choice){
		switch(choice){
		case 1:
			UserStory us = UserStory.getUserStoryInsertInformation();
			us.insertNewUserStory(conn);
			SprintBacklog sb = SprintBacklog.getSprintBacklogInsertInformation();
			sb.insertNewUserStory(conn);
			break;
		case 2:
			sb = SprintBacklog.getSprintBacklogInsertInformation();
			sb.insertNewUserStory(conn);
			break;
		case 3:
			SprintBacklog.updateUserStoryInSprintBacklog(conn);
			break;
		case 4:
			SprintBacklog.deleteUserStory(conn);
			break;
		case 5:
			SprintBacklog.listAllSprintBacklogsForASprint(conn);
			break;
		default:
			break;
		}
	}
	public int displayCRUDOperationsSprintBacklogMenu(){
		System.out.println("1. Add new user story to sprint backlog");
		System.out.println("2. Add existing user story to sprint backlog");
		System.out.println("3. Update the user stories in the sprint backlog");
		System.out.println("4. Delete a user story in the sprint backlog");
		System.out.println("5. List all user stories for a specific sprint");
		int choice = Validator.checkIntRange(1, 5);
		return choice;
	}
	public void listAllDevelopers(){
		Employee.listEmployee(conn);
	}
	public void listAllSprints(){
		Sprint.listAllSprints(conn);
	}
	public void listAllDevelopersInASprint(){
		Sprint.listAllDevelopersOnSprint(conn);
	}
	public void runUserStoryToSprintBacklog(int choice){
		switch(choice){
		case 1:
			UserStory us = UserStory.getUserStoryInsertInformation();
			us.insertNewUserStory(conn);
			SprintBacklog sb = SprintBacklog.getSprintBacklogInsertInformation();
			sb.insertNewUserStory(conn);
			break;
		case 2:
			sb = SprintBacklog.getSprintBacklogInsertInformation();
			sb.insertNewUserStory(conn);
			break;
		default:
			break;
		}
	}
	public int displayUserStoryToSprintBacklogMenu(){
		System.out.println("1. Add a new user story to the sprint backlog");
		System.out.println("2. Add an existing user story to the sprint backlog");
		int choice = Validator.checkIntRange(1, 2);
		return choice;
	}
	public int displayMainMenu(){
		System.out.println("1. CRUD Operations for Stakeholders");
		System.out.println("2. CRUD Operations for sprint team members");
		System.out.println("3. CRUD Operations for user stories to project backlog");
		System.out.println("4. Create a sprint");
		System.out.println("5. Add user story to sprint backlog");
		System.out.println("6. CRUD operations for user stories in sprint backlog");
		System.out.println("7. List all developers");
		System.out.println("8. List all sprints");
		System.out.println("9. List all developers that are part of a sprint");
		System.out.println("10. Quit");
		int choice = Validator.checkIntRange(1, 10);
		return choice;
	}
	public Connection getConnection(){
		return conn;
	}
	
	public void insertZips() throws SQLException, IOException{
		BufferedReader br = new BufferedReader(new FileReader("Zipcode Database.csv"));
		br.readLine();
		String line = "";
		PreparedStatement ps = conn.prepareStatement("INSERT INTO zipCode VALUES (?, ?, ?)");
		while ((line = br.readLine()) != null){
			String[] info = line.split(",");
			ps.setInt(1, Integer.parseInt(info[0]));
			ps.setString(2, info[1]);
			ps.setString(3, info[2]);
			ps.execute();
		}
		System.out.println("Success!");
		
	}
	private void createConnection() {
		try {
			conn = DriverManager.getConnection(dbURL);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * This will shutdown the statement and close the connection to the database
	 */
	private void shutDown() {
		try {
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
				conn = DriverManager.getConnection(dbURL + "shutdown=true");
				conn.close();
			}
		} catch (SQLException ex) {
		}
	}
}
