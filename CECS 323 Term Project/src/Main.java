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
		
		shutDown();
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
		int choice = Validator.checkIntRange(1, 2);
		return choice;
	}
	public int displayCRUDUserStoriesToProjectBacklogMenu(){
		System.out.println("1. Add a new user story to the project backlog");
		System.out.println("2. Add an existing user story to the project backlog");
		System.out.println("3. Delete a user story from the project backlog");
		int choice = Validator.checkIntRange(1, 3);
		return choice;
	}
	public void createSprint(){
		Sprint s = Sprint.getSprintInsertInformation();
		s.insertNewSprint(conn);
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
		// I have no idea why or how this works, but it does for now... maybe have someone come up with a better way?
		/*
		 *  SELECT employee.employeeID, employee.firstName, employee.lastName FROM employee
			INNER JOIN employee_scrumTeam
			ON employee.employeeID=employee_scrumTeam.employeeID 
			INNER JOIN scrum_team 
			ON employee_scrumTeam.teamNum=scrum_team.teamNum 
			INNER JOIN sprint 
			ON scrum_team.teamNum=sprint.teamNum
			GROUP BY employee.employeeID;
		 */
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
		int choice = Validator.checkIntRange(1, 9);
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
