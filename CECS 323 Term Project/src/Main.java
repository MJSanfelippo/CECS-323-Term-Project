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

	/**
	 * The url to connect to the database
	 */
	private String dbURL = "jdbc:mysql://cecs-db01.coe.csulb.edu:3306/cecs323h24?" + "user=cecs323h24&password=le0ao2";
	/**
	 * the connection to the database, initially set to null
	 */
	private Connection conn = null;
	/**
	 * the statement used to query the database
	 */
	private Statement statement = null;
	/**
	 * 
	 * @param args the command-line arguments (none)
	 */
	public static void main(String[] args){
		//new Main().run();
		Main m = new Main();
		m.createConnection();
		try {
			m.insertZips();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.shutDown();
	}
	/**
	 * This will create the connection, display the main menu and handle whatever choice the user chooses
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
				EmployeeScrumTeam.listAllEmployeeScrumTeam(conn);
				break;
			case 11:
				runCRUDProjects(displayProjectMenu());
				break;
			case 12:
				runCRUDEmployees(displayEmployeeMenu());
				break;
			case 13:
				running = false;
				break;
			default:
				break;
			}
		}
		shutDown();
	}
	public void runCRUDEmployees(int choice){
		switch(choice){
		case 1:
			Employee e = Employee.getEmployeeInsertInformation();
			e.insertNewEmployee(conn);
			break;
		case 2:
			Employee.updateEmployee(conn);
			break;
		case 3:
			Employee.listEmployee(conn);
			break;
		case 4:
			Employee.deleteEmployee(conn);
			break;
		default:
			break;
		}
	}
	/**
	 * Display the menu for CRUD operations on employees
	 * @return the user's choice
	 */
	public int displayEmployeeMenu(){
		System.out.println("1. Add a new employee");
		System.out.println("2. Update an employee");
		System.out.println("3. List all employees");
		System.out.println("4. Delete an employee");
		System.out.println("5. Back to main menu");
		int choice = Validator.checkIntRange(1,5);
		return choice;
		
	}
	/**
	 * runs whatever choice the user chose for crud operations on projects
	 * @param choice the user's choice
	 */
	public void runCRUDProjects(int choice){
		switch(choice){
		case 1:
			Project p = Project.getProjectInsertInformation();
			p.insertNewProject(conn);
			break;
		case 2:
			Project.updateProject(conn);
			break;
		case 3:
			Project.listAllProjects(conn);
			break;
		default:
			break;
		}
	}
	/**
	 * menu to display options for projects
	 * @return the user's choice
	 */
	public int displayProjectMenu(){
		System.out.println("1. Add a new project");
		System.out.println("2. Update an existing project");
		System.out.println("3. List all projects");
		System.out.println("4. Back to main menu");
		int choice = Validator.checkIntRange(1, 4);
		return choice;
	}
	/**
	 * This will run whatever choice the user made for crud operations with user stories to the project backlog
	 * @param choice the user's choice
	 */
	public void runCRUDUserStoriesToProjectBackLog(int choice){
		switch(choice){
		case 1:
			UserStory us = UserStory.getUserStoryInsertInformation();
			us.insertNewUserStory(conn);
			ProjectBacklog pb = ProjectBacklog.getProjectBacklogInsertInformation();
			pb.insertNewProjectBacklog(conn);
			break;
		case 2:
			pb = ProjectBacklog.getProjectBacklogInsertInformation();
			pb.insertNewProjectBacklog(conn);
			break;
		case 3:
			ProjectBacklog.deleteUserStoryFromProjectBacklog(conn);
			break;
		default:
			break;
		}
	}
	/**
	 * This will display the menu for crud operations for user stories to the project backlog
	 * @return the user's choice
	 */
	public int displayCRUDUserStoriesToProjectBacklogMenu(){
		System.out.println("1. Add a new user story to the project backlog");
		System.out.println("2. Add an existing user story to the project backlog");
		System.out.println("3. Delete a user story from the project backlog");
		System.out.println("4. Back to main menu");
		int choice = Validator.checkIntRange(1, 4);
		return choice;
	}
	/**
	 * This will run whatever choice the user chose for crud operations for scrum team members
	 * @param choice the user's choice
	 */
	public void runCRUDScrumTeamMembers(int choice){
		switch(choice){
		case 1:
			EmployeeScrumTeam est = EmployeeScrumTeam.getEmployeeScrumTeamInsertInformation();
			est.insertNewEmployeeScrumTeam(conn);
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
	/**
	 * This will run whatever choice the user chose for crud operations on stakeholders
	 * @param choice the user's choice
	 */
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
	/**
	 * This will display the menu to allow the user to do crud operations with stakeholders
	 * @return the user's choice
	 */
	public int displayCRUDStakeholdersMenu(){
		System.out.println("1. Add a new stakeholder");
		System.out.println("2. List all stakeholders");
		System.out.println("3. Update a stakeholder");
		System.out.println("4. Delete a stakeholder");
		System.out.println("5. Add an existing stakeholder to a project");
		System.out.println("6. Delete an existing stakeholder from a project");
		System.out.println("7. Update an existing stakeholder's interest or position in a project");
		System.out.println("8. Back to main menu");
		int choice = Validator.checkIntRange(1,8);
		return choice;
	}
	/**
	 * This will display the menu for the user to choose what crud operations they want to run for scrum teams
	 * @return the user's choice
	 */
	public int displayCRUDScrumTeamMembersMenu(){
		System.out.println("1. Add an employee to a scrum team");
		System.out.println("2. Remove an employee from a scrum team");
		System.out.println("3. Create new scrum team");
		System.out.println("4. Back to main menu");
		int choice = Validator.checkIntRange(1, 4);
		return choice;
	}
	/**
	 * This will create a new sprint
	 */
	public void createSprint(){
		Sprint s = Sprint.getSprintInsertInformation();
		s.insertNewSprint(conn);
	}
	/**
	 * This will run whatever choice the user chose
	 * @param choice the choice the user chose in the menu
	 */
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
	/**
	 * This will display all crud operations available for the user for the sprint backlog
	 * @return the user's choice
	 */
	public int displayCRUDOperationsSprintBacklogMenu(){
		System.out.println("1. Add new user story to sprint backlog");
		System.out.println("2. Add existing user story to sprint backlog");
		System.out.println("3. Update the user stories in the sprint backlog");
		System.out.println("4. Delete a user story in the sprint backlog");
		System.out.println("5. List all user stories for a specific sprint");
		System.out.println("6. Back to main menu");
		int choice = Validator.checkIntRange(1, 6);
		return choice;
	}
	/**
	 * This will list every developer
	 */
	public void listAllDevelopers(){
		Employee.listEmployee(conn);
	}
	/**
	 * This will list every sprint
	 */
	public void listAllSprints(){
		Sprint.listAllSprints(conn);
	}
	/**
	 * This will list all developers in a specific sprint
	 */
	public void listAllDevelopersInASprint(){
		Sprint.listAllDevelopersOnSprint(conn);
	}
	/**
	 * This will run whatever option the user chose
	 * @param choice the user's choice
	 */
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
	/**
	 * This will display the menu to allow the user to add user stories to the sprint backlog
	 * @return the user's choice
	 */
	public int displayUserStoryToSprintBacklogMenu(){
		System.out.println("1. Add a new user story to the sprint backlog");
		System.out.println("2. Add an existing user story to the sprint backlog");
		System.out.println("3. Back to main menu");
		int choice = Validator.checkIntRange(1, 3);
		return choice;
	}
	/**
	 * The main menu of the program
	 * @return the user's choice
	 */
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
		System.out.println("10. Display all employees that are part of a scrum team");
		System.out.println("11. CRUD Operations for Projects");
		System.out.println("12. CRUD Operations for employees");
		System.out.println("13. Quit");
		int choice = Validator.checkIntRange(1, 13);
		return choice;
	}
	public Connection getConnection(){
		return conn;
	}
	/**
	 * This is a function to read all ~43,000 different zip codes into the zipCode table
	 * @throws SQLException throw any sql exceptions because it works every time
	 * @throws IOException throw any io exceptions because they will never be caught
	 */
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
	/**
	 * This will create the connection to the database
	 */
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
