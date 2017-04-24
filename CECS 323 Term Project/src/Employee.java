import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class Employee {

	private int employeeID;
	private String firstName;
	private String lastName;
	private String title;
	private String address;
	private int zip;
	
	public Employee(int employeeID, String firstName, String lastName, String title, String address, int zip) {
		super();
		this.employeeID = employeeID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.address = address;
		this.zip = zip;
	}
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
	public void insertNewUserStory(Connection conn) {
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
