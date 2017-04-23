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
		Main m = new Main();
		m.createConnection();
		m.insertZips();
		
		
	}
	/**
	 * This will create the connection to the database
	 * @throws SQLException 
	 * @throws IOException 
	 */
	
	
	public void insertZips() throws SQLException, IOException{
		BufferedReader br = new BufferedReader(new FileReader("Zipcode Database.csv"));
		br.readLine();
		String line = "";
		Statement stmt = conn.createStatement();
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
			System.out.println("I did it");
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
