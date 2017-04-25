import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Stakeholder {
	/**
	 * The id of the stakeholder
	 */
	private int id;
	/**
	 * The first name of the stakeholder
	 */
	private String firstName;
	/**
	 * The last name of the stakeholder
	 */
	private String lastName;
	/**
	 * 
	 * @param id id of the stakeholder
	 * @param firstName first name of the stakeholder
	 * @param lastName last name of the stakeholder
	 */
	public Stakeholder(int id, String firstName, String lastName){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	/**
	 * Inserts a new stakeholder into the stakeholder table
 	 * @param conn the connection to the database
 	 */
	public void insertNewStakeholder(Connection conn){
		String stakeholderSQL = "INSERT INTO stakeholder VALUES (?,?,?);"; // simple sql query to insert into stakeholder table
		try {
			PreparedStatement ps = conn.prepareStatement(stakeholderSQL);
			ps.setInt(1, id);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Ask the user for information on the stakeholder
	 * @return a new stakeholder containing the info the user entered
	 */
	public static Stakeholder getStakeholderInsertInformation(){
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the stakeholder's ID: ");
		int id = Validator.checkInt();
		System.out.println("Please enter the stakeholder's first name: ");
		String firstName = in.nextLine();
		System.out.println("Please enter the stakeholder's last name: ");
		String lastName = in.nextLine();
		return new Stakeholder(id, firstName, lastName);
	}
	/**
	 * This will list all stakeholders in the database
	 * @param conn the connection to the database
	 */
	public static void listAllStakeholders(Connection conn){
		String sql = "SELECT * FROM stakeholder";
		try{
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet results = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = results.getMetaData();
			int numCols = rsmd.getColumnCount();
			for (int i = 1; i <= numCols; i++) {
				System.out.print("\t" + rsmd.getColumnLabel(i) + "\t\t");
			}
			System.out.println(
					"\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			while (results.next()) {
				int stakeholderID = results.getInt(1);
				String firstName = results.getString(2);
				String lastName = results.getString(3);
				System.out.format("%15d%32s%33s\n", stakeholderID, firstName, lastName);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	/**
	 * This will delete a specific stakeholder based on the id the user enteres
	 * @param conn the connection to the database
	 */
	public static void deleteStakeholder(Connection conn){
		System.out.println("Please enter the id of the stakeholder you'd like to delete: ");
		int id = Validator.checkInt();
		String sql = "DELETE FROM stakeholder WHERE stakeholder.id = ?";
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		} catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	/**
	 * This will show the menu to allow the user to enter what info they want to update about a stakeholder
	 * @return
	 */
	public static int showUpdateMenu(){
		System.out.println("What would you like to update?");
		System.out.println("1. Stakeholder ID");
		System.out.println("2. First Name");
		System.out.println("3. Last name");
		int choice = Validator.checkIntRange(1, 3);
		return choice;
	}
	/**
	 * This will update a stakeholder based on the stakeholder's id and one other attribute which is the one to be updated
	 * @param conn the connection to the database
	 */
	public static void updateStakeholder(Connection conn){
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the stakeholder's ID that you'd like to update: ");
		int id = Validator.checkInt();
		String sql = "";
		int choice = showUpdateMenu();
		switch(choice){
		case 1:
			System.out.println("What is the stakeholder's new ID?");
			int newID = Validator.checkInt();
			String stakeholderSQL = "UPDATE stakeholder SET stakeholderID = ? WHERE stakeholderID = ?";
			String stakeholder_projectSQL = "UPDATE stakeholder_project SET stakeholderID = ? WHERE stakeholderID = ?";
			try {
				PreparedStatement ps = conn.prepareStatement(stakeholderSQL);
				ps.setInt(1, newID);
				ps.setInt(2, id);
				ps.execute();
				ps = conn.prepareStatement(stakeholder_projectSQL);
				ps.setInt(1, newID);
				ps.setInt(2, id);
				ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("What is the stakeholder's new first name?");
			String firstName = in.nextLine();
			sql = "UPDATE stakeholder SET firstName = ? WHERE stakeholderID = ?";
			try{
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, firstName);
				ps.setInt(2, id);
				ps.execute();
			} catch(SQLException e){
				e.printStackTrace();
			}
			break;
		case 3: 
			System.out.println("What is the stakeholder's new last name?");
			String lastName = in.nextLine();
			sql = "UPDATE stakeholder SET lastName = ? WHERE stakeholderID = ?";
			try{
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, lastName);
				ps.setInt(2, id);
				ps.execute();
			} catch(SQLException e){
				e.printStackTrace();
			}
			break;
		}
	}
}
