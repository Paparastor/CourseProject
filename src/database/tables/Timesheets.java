package database.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;

import entities.Entity;
import entities.Timesheet;

public class Timesheets extends DBTable {

	public static final String name = "timesheets";
	
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

	public static ResultSet getFormatted(Entity entity) {
		Timesheet timesheet = (Timesheet)entity;
		String firstHalf = "select * from " + name;
		String secondHalf = "";
		if (timesheet.getTimesheetID().length() != 0){
			secondHalf += " TIMESHEET_ID = " + timesheet.getTimesheetID();
		}
		if (timesheet.getDate().length() != 0){
			if (timesheet.getTimesheetID().length() != 0)
				secondHalf+=" and ";
			secondHalf += " DATE = " + "'" + timesheet.getDate() + "'";
		}
		if (timesheet.getEmployeeID().length() != 0){
			if (timesheet.getDate().length() != 0)
				secondHalf+=" and ";
			secondHalf += " EMPLOYEE_ID = " +  timesheet.getEmployeeID();
		}
		if (timesheet.getPlanPercentage().length() != 0){
			if (timesheet.getEmployeeID().length() != 0)
				secondHalf+=" and ";
			secondHalf += " PLAN_PERCENTAGE = " +  timesheet.getPlanPercentage();
		}
		if (secondHalf.length() != 0){
			firstHalf += " where " + secondHalf + ";";
			System.out.println(firstHalf);
			return Database.getResultSet(firstHalf);
		}
		return Database.getResultSet(firstHalf + ";");
	}

}
