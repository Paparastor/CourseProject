package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Connector {
	
	private static Connection connection;
	
	public static final String CLASSNAME = "org.sqlite.JDBC";
	public static final String ADDRESS = "jdbc:sqlite:test.db";

	//Connection initialization
	public static void initializeConnection() {
		try {
			Class.forName(CLASSNAME);
			connection = DriverManager.getConnection(ADDRESS);
			
			System.out.println("Opened database successfully");

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public static Connection getConnection(){
		return connection;
	}
	
	public static void closeConnection(){
		try {
			connection.close();
			System.out.println("Closed database successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
