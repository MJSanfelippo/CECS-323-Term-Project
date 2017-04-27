import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class Employee {

	/**
	 * the employee's id
	 */
	private int employeeID;
	/**
	 * the first name of the employee
	 */
	private String firstName;
	/** 
	 * the last name of the employee
	 */
	private String lastName;
	/**
	 * the employee's title
	 */
	private String title;
	/**
	 * the address of the employee
	 */
	private String address;
	/**
	 * the zip code of the employee
	 */
	private int zip;
	
	/**
	 * 
	 * @param employeeID the employee's id
	 * @param firstName the first name of the employee
	 * @param lastName the last name of the employee
	 * @param title the title of the employee
	 * @param address the address of the employee
	 * @param zip the zip code of the employee
	 */
	public Employee(int employeeID, String firstName, String lastName, String title, String address, int zip) {
		super();
		this.employeeID = employeeID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.address = address;
		this.zip = zip;
	}
	public static void deleteEmployee(Connection conn){
		System.out.println("Please enter the employee's ID: ");
		int id = Validator.checkInt();
		String sql = "DELETE FROM employee WHERE employee.employeeID = ?;";
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		} catch(SQLException e){
			CustomErrorMessages.printSQLException(e);
		}
	}
	/**
	 * Updates an employee's information based on user input
	 * @param conn the connection to the database
	 */
	public static void updateEmployee(Connection conn){
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the employee's ID: ");
		int id = Validator.checkInt();
		String sql = "";
		int choice = employeeUpdateMenu();
		switch(choice){
		case 1:
			System.out.println("Please enter the employee's new first name: ");
			String newFirstName = in.nextLine();
			sql = "UPDATE employee SET employee.firstName = ? WHERE employee.employeeID = ?;";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, newFirstName);
				ps.setInt(2, id);
				ps.execute();
			} catch (SQLException e) {
				CustomErrorMessages.printSQLException(e);
			}
			break;
		case 2:
			System.out.println("Please enter the employee's new last name: ");
			String newLastName = in.nextLine();
			sql = "UPDATE employee SET employee.lastName = ? WHERE employee.employeeID = ?;";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, newLastName);
				ps.setInt(2, id);
				ps.execute();
			} catch (SQLException e) {
				CustomErrorMessages.printSQLException(e);
			}
			break;
		case 3:
			System.out.println("Please enter the employee's new title: ");
			String newTitle = in.nextLine();
			sql = "UPDATE employee SET employee.title = ? WHERE employee.employeeID = ?;";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, newTitle);
				ps.setInt(2, id);
				ps.execute();
			} catch (SQLException e) {
				CustomErrorMessages.printSQLException(e);
			}
			break;
		case 4:
			System.out.println("Please enter the employee's new address: ");
			String newAddress = in.nextLine();
			sql = "UPDATE employee SET employee.address = ? WHERE employee.employeeID = ?;";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, newAddress);
				ps.setInt(2, id);
				ps.execute();
			} catch (SQLException e) {
				CustomErrorMessages.printSQLException(e);
			}
			break;
		case 5:
			System.out.println("Please enter the employee's new zip code: ");
			int newZip = Validator.checkInt();
			sql = "UPDATE employee SET employee.zip = ? WHERE employee.employeeID = ?;";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, newZip);
				ps.setInt(2, id);
				ps.execute();
			} catch (SQLException e) {
				CustomErrorMessages.printSQLException(e);
			}
			break;
		default:
			break;
			
		}
	}
	/**
	 * displays the employee update menu
	 * @return the user's choice on what to update
	 */
	public static int employeeUpdateMenu(){
		System.out.println("1. Update first name");
		System.out.println("2. Update last name");
		System.out.println("3. Update employee title");
		System.out.println("4. Update employee address");
		System.out.println("5. Update employee zip");
		int choice = Validator.checkIntRange(1, 5);
		return choice;
	}
	/**
	 * lists all employees that are software engineers or developers
	 * @param conn the connection to the database
	 */
	public static void listEmployee(Connection conn){
		String sql = "SELECT employeeID, CONCAT(firstName, ' ', lastName) AS name, address, employee.zip, city, st FROM employee INNER JOIN zipCode ON employee.zip = zipCode.zip "
				+ "WHERE title = 'Software Engineer' OR title = 'Software Developer' OR title = 'Developer';";
		try{
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet results = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = results.getMetaData();
			int numCols = rsmd.getColumnCount();
			for (int i = 1; i <= numCols; i++) {
				System.out.print(rsmd.getColumnLabel(i) + "\t\t\t\t");
			}
			System.out.println(
					"\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			while (results.next()) {
				int employeeID = results.getInt(1);
				String name = results.getString(2);
				String address = results.getString(3);
				int zip = results.getInt(4);
				String city = results.getString(5);
				String st = results.getString(6);
				System.out.format("%5d%43s%35s%25d%35s%27s\n", employeeID, name, address, zip, city, st);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	/**
	 * gets the info from the user for a new employee
	 * @return a new employee containing the info the employee entered
	 */
	public static Employee getEmployeeInsertInformation() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the employee's ID: ");
		int employeeID = Validator.checkInt();
		System.out.println("Please enter the employee's first name: ");
		String firstName = in.nextLine();
		System.out.println("Please enter the employee's last name: ");
		String lastName = in.nextLine();
		System.out.println("Please enter the employee's job title: ");
		String title = in.nextLine();
		System.out.println("Please enter the employee's address: ");
		String address = in.nextLine();
		System.out.println("Please enter the employee's zip code: ");
		int zip = Validator.checkInt();
		return new Employee(employeeID, firstName, lastName, title, address, zip);
	}
	/**
	 * inserts a new employee to the database
	 * @param conn the connection to the database
	 */
	public void insertNewEmployee(Connection conn) {
		String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeID);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, title);
			ps.setString(5, address);
			ps.setInt(6,zip);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
