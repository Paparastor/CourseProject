package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.*;

public class Database {

	private static Connection connection;

	// Database connection initialization
	public static void initializeDatabase() {
		Connector.initializeConnection();
		connection = Connector.getConnection();

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

	// 'employees' representation
	public static class Employees {

		private static final String SQL_SPECS = "CREATE TABLE IF NOT EXISTS EMPLOYEES"
				+ " (EMPLOYEE_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ " PASSPORT TEXT NOT NULL,"
				+ " NAME TEXT NOT NULL,"
				+ " PROFESSION TEXT,"
				+ " POINT_ID INT,"
				+ " FOREIGN KEY(POINT_ID) REFERENCES POINTS(POINT_ID))";

		public static String getCreationQuery() {
			return SQL_SPECS;
		}

		// Getting all rows from 'employees'
		public static ResultSet getAll() {
			return getResultSet("select * from employees");
		}

		// Adding a new employee to 'employees'
		public static void addEmployee(Employee employee) throws SQLException {
			// Employee employee = (Employee)rowInfo;
			update("insert into employees values(" + employee.getEmployeeID()
					+ "," + "\"" + employee.getPassport() + "\"" + "," + "\""
					+ employee.getName() + "\"" + "," + "\""
					+ employee.getProfession() + "\"" + ","
					+ employee.getPointID() + ");");
		}

		// Editing employee with given value
		public static void editEmployee(String employeeID, Employee newEmployee)
				throws SQLException {
			// Employee employee = (Employee)rowInfo;
			update("update employees set " + "EMPLOYEE_ID = "
					+ newEmployee.getEmployeeID() + ", PASSPORT = " + "'"
					+ newEmployee.getPassport() + "'" + ", NAME = " + "'"
					+ newEmployee.getName() + "'" + ", PROFESSION = " + "'"
					+ newEmployee.getProfession() + "'" + ", POINT_ID = "
					+ newEmployee.getPointID() + " where EMPLOYEE_ID = "
					+ employeeID + ";");
		}

		// Getting an employee from 'employees'
		public static Employee getEmployee(String id)
				throws NumberFormatException, SQLException {
			Employee employee;
			ResultSet resultSet = getResultSet("Select * from employees where EMPLOYEE_ID="
					+ id + ";");
			employee = new Employee(resultSet.getString("EMPLOYEE_ID"),
					resultSet.getString("PASSPORT"),
					resultSet.getString("NAME"),
					resultSet.getString("PROFESSION"),
					resultSet.getString("POINT_ID"));
			return employee;
		}
		
	}

	// 'points' representation
	public static class Points {

		public static final String SQL_SPECS = "CREATE TABLE IF NOT EXISTS POINTS"
				+ "(POINT_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "SALARY INTEGER NOT NULL," 
				+ "POST TEXT NOT NULL)";

		public static String getCreationQuery() {
			return SQL_SPECS;
		}

		// Getting all rows from 'points'
		public static ResultSet getAll() {
			return getResultSet("select * from points;");
		}

		// Adding a new point to 'points'
		public static void addPoint(Point point) throws SQLException {
			update("insert into points values(" + point.getPointID() + ","
					+ point.getSalary() + "," + "\"" + point.getPost() + "\""
					+ ");");
		}

		// Editing employee with given value
		public static void editPoint(String pointID, Point newPoint)
				throws SQLException {
			// Employee employee = (Employee)rowInfo;
			update("update points set " + "POINT_ID = "
					+ newPoint.getPointID() 
					+ ", SALARY = "	+ newPoint.getSalary() 
					+ ", POST = " + "'" + newPoint.getPost()+ "'" 
					+ " where POINT_ID = " + pointID + ";");
		}

		// Getting a point from 'points'
		public static Point getPoint(int id) throws NumberFormatException,
				SQLException {
			Point point;
			ResultSet resultSet = getResultSet("Select * from points where POINT_ID="
					+ id + ";");
			point = new Point(resultSet.getString("POINT_ID"),
					resultSet.getString("SALARY"), resultSet.getString("POST"));
			return point;
		}

	}

	// 'timesheets' representation
	public static class Timesheets {

		private static final String SQL_SPECS = "CREATE TABLE IF NOT EXISTS TIMESHEETS"
				+ "(TIMESHEET_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "EMPLOYEE_ID INTEGER NOT NULL,"
				+ "DATE DATETIME NOT NULL,"
				+ "PLAN_PERCENTAGE INTEGER NOT NULL,"
				+ " FOREIGN KEY(EMPLOYEE_ID) REFERENCES EMPLOYEES(EMPLOYEE_ID))";

		public static String getCreationQuery() {
			return SQL_SPECS;
		}

		// Getting all rows from 'timesheets'
		public static ResultSet getAll() {
			return getResultSet("select * from timesheets");
		}

		// Adding a new time sheet to 'timesheets'
		public static void addTimesheet(Timesheet timesheet)
				throws SQLException {
			update("insert into timesheets values("
					+ timesheet.getTimesheetID() + ","
					+ timesheet.getEmployeeID() + "," + "\""
					+ timesheet.getDate() + "\"" + ","
					+ timesheet.getPlanPercentage() + ");");
		}

		// Getting a time sheet from 'timesheets'
		public static Timesheet getTimesheet(int id)
				throws NumberFormatException, SQLException {
			Timesheet timesheet;
			ResultSet resultSet = getResultSet("Select * from timesheets where TIMESHEET_ID="
					+ id + ";");
			timesheet = new Timesheet(resultSet.getString("TIMESHEET_ID"),
					resultSet.getString("EMPLOYEE_ID"),
					resultSet.getString("DATE"),
					resultSet.getString("PLAN_PERCENTAGE"));
			return timesheet;
		}

	}

}
