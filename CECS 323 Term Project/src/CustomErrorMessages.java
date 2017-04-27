import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomErrorMessages {

	public static void printSQLException(SQLException ex) {

		for (Throwable e : ex) {

			if (e instanceof SQLException) {

				//if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {

					//e.printStackTrace(System.err);

					//System.err.println("SQLState: " + ((SQLException) e).getSQLState());

					System.err.println("Error Code: " + ((SQLException) e).getErrorCode());

					System.err.println("Message: " + e.getMessage());

					Throwable t = ex.getCause();

					while (t != null) {
						System.out.println("Cause: " + t);
						t = t.getCause();
					}
				//}
			}
		}
	}
}