package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Entity;
import entities.Timesheet;

public class Timesheets extends DBTable {
	
	public static final String ORDER_BY_DATE = "Order by date";
	public static final String ORDER_BY_PLAN = "Order by plan";

	public Timesheets(String creationQuery, String name) {
		super(creationQuery, name);
	}

	@Override
	public void addRow(Entity entity) throws SQLException {
		Timesheet timesheet = (Timesheet)entity;
		Database.update("insert into timesheets values("
				+ timesheet.getTimesheetID() + ","
				+ timesheet.getEmployeeID() + "," + "\""
				+ timesheet.getDate() + "\"" + ","
				+ timesheet.getPlanPercentage() + ");");
	}

	@Override
	public void editRow(String id, Entity newEntity) throws SQLException {
		Timesheet newTimesheet = (Timesheet)newEntity;
		Database.update("update timesheets set " + " TIMESHEET_ID = "
				+ newTimesheet.getTimesheetID() + ", EMPLOYEE_ID = "
				+ newTimesheet.getEmployeeID() + ", DATE = " + "'"
				+ newTimesheet.getDate() + "'" + ", PLAN_PERCENTAGE = "
				+ newTimesheet.getPlanPercentage()
				+ " where TIMESHEET_ID = " + id + ";");
	}

	@Override
	public void deleteRow(String id) throws SQLException {
		Database.update("delete from timesheets where TIMESHEET_ID = " + id);
	}

	@Override
	public Entity getRow(String id) throws SQLException {
		Timesheet timesheet;
		ResultSet resultSet = Database.getResultSet("Select * from timesheets where TIMESHEET_ID="
				+ id + ";");
		timesheet = new Timesheet(resultSet.getString("TIMESHEET_ID"),
				resultSet.getString("EMPLOYEE_ID"),
				resultSet.getString("DATE"),
				resultSet.getString("PLAN_PERCENTAGE"));
		return timesheet;
	}

	@Override
	public ResultSet getAllFormatted(String orderField) {
		switch (orderField) {
		case ORDER_BY_DATE:
			return Database.getResultSet("select * from " + super.getName() +" order by DATE;");
		case ORDER_BY_PLAN:
			return Database.getResultSet("select * from timesheets order by PLAN_PERCENTAGE;");
		case ORDER_BY_ID:
			return Database.getTimesheets().getAll();
		}
		return null;
	}

}
