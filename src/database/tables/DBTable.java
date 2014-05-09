package database.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;
import entities.Entity;

public abstract class DBTable {

	private static String SQL_SPECS;

	private String name;

	private boolean sortingFlag;

	protected String getName() {
		return name;
	}

	public DBTable(String creationQuery, String name) {
		SQL_SPECS = creationQuery;
		this.name = name;
		sortingFlag = false;
	}

	public static String getCreationQuery() {
		return SQL_SPECS;
	}

	// Getting all rows from 'employees'
	public ResultSet getAll() {
		return Database.getResultSet("select * from " + name + ";");
	}

	public ResultSet getAll(String sortingField) {
		String sortingMode;
		if (sortingFlag) {
			sortingMode = " asc ";
			sortingFlag = false;
		} else {
			sortingMode = " desc ";
			sortingFlag = true;
		}
		return Database.getResultSet("select * from " + name + " order by "
				+ sortingField + sortingMode + ";");
	}

	public int getRowsCount() {
		ResultSet r = Database.getResultSet("select count(*) from " + name
				+ ";");
		try {
			return r.getInt(0);
		} catch (SQLException e) {
			System.out.println("Count error");
		}
		return 0;
	}

	// Adding a new entity
	public abstract void addRow(Entity entity) throws SQLException;

	// Editing entity with given value
	public abstract void editRow(String id, Entity newEntity)
			throws SQLException;

	// Deleting entity with given value
	public abstract void deleteRow(String id) throws SQLException;

	// Getting an entity
	public abstract Entity getRow(String id) throws SQLException;
	
	// Getting formatted data
	//public  abstract ResultSet getFormatted(Entity entity);

}
