package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.tables.DBTable;
import database.tables.Employees;
import database.tables.Points;
import database.tables.Timesheets;

public class Database {

	private static Connection connection;
	
	public final static String NAME_EMPLOYEES = "employees";
	public final static String NAME_POINTS = "points";
	public final static String NAME_TIMESHEETS = "timesheets";
	
	private static final String SPECS_EMPLOYEES = "CREATE TABLE IF NOT EXISTS EMPLOYEES"
			+ " (EMPLOYEE_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ " PASSPORT TEXT NOT NULL,"
			+ " NAME TEXT NOT NULL,"
			+ " PROFESSION TEXT,"
			+ " POINT_ID INT,"
			+ " FOREIGN KEY(POINT_ID) REFERENCES POINTS(POINT_ID))";
	public static final String SPECS_POINTS = "CREATE TABLE IF NOT EXISTS POINTS"
			+ "(POINT_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "SALARY INTEGER NOT NULL," + "POST TEXT NOT NULL)";
	private static final String SPECS_TIMESHEETS = "CREATE TABLE IF NOT EXISTS TIMESHEETS"
			+ "(TIMESHEET_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "EMPLOYEE_ID INTEGER NOT NULL,"
			+ "DATE DATETIME NOT NULL,"
			+ "PLAN_PERCENTAGE INTEGER NOT NULL,"
			+ " FOREIGN KEY(EMPLOYEE_ID) REFERENCES EMPLOYEES(EMPLOYEE_ID))";
	
	public static DBTable employees;
	public static DBTable points;
	public static DBTable timesheets;

	public static String getNameEmployees() {
		return NAME_EMPLOYEES;
	}

	public static String getNamePoints() {
		return NAME_POINTS;
	}

	public static String getNameTimesheets() {
		return NAME_TIMESHEETS;
	}

	public static DBTable getEmployees() {
		return employees;
	}

	public static DBTable getPoints() {
		return points;
	}

	public static DBTable getTimesheets() {
		return timesheets;
	}

	// Database connection initialization
	public static void initializeDatabase() {
		Connector.initializeConnection();
		connection = Connector.getConnection();
		
		employees = new Employees(SPECS_EMPLOYEES,NAME_EMPLOYEES);
		points = new Points(SPECS_POINTS,NAME_POINTS);
		timesheets = new Timesheets(SPECS_TIMESHEETS,NAME_TIMESHEETS);

		// Tables initialization
		try {
			update(Points.getCreationQuery());
			update(Employees.getCreationQuery());
			update(Timesheets.getCreationQuery());
		} catch (SQLException e) {
			System.out.println("Error: something with tables.");
		}

	}

	// Closing the database connection
	public static void close() {
		Connector.closeConnection();
	}

	// Getting information from database
	public static ResultSet getResultSet(String query) {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Setting information to database
	public static void update(String query) throws SQLException {
		Statement statement;
		statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

}
