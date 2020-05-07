package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager
{
	private static String username = "user";
	private static String password = "NFohFZx3Y3L7AH/";
	private static Connection conn;
	private static String url = "jdbc:mysql://127.0.0.1:3306/coloretto?serverTimezone=UTC&useSSL=false";
	
	public Connection getConnection() throws SQLException
	{
		conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
}
