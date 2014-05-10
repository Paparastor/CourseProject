package database.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;

import entities.*;

public class Employees extends DBTable {
	
	public static final String name = "employees";

	public Employees(String creationQuery, String name) {
		super(creationQuery, name);
	}

	@Override
	public void addRow(Entity entity) throws SQLException {
		Employee employee = (Employee) entity;
		Database.update("insert into employees values("
				+ employee.getEmployeeID() + "," + "\""
				+ employee.getPassport() + "\"" + "," + "\""
				+ employee.getName() + "\"" + "," + "\""
				+ employee.getProfession() + "\"" + "," + employee.getPointID()
				+ ");");
	}

	@Override
	public void editRow(String id, Entity newEntity) throws SQLException {
		Employee newEmployee = (Employee) newEntity;
		Database.update("update employees set " + "EMPLOYEE_ID = "
				+ newEmployee.getEmployeeID() + ", PASSPORT = " + "'"
				+ newEmployee.getPassport() + "'" + ", NAME = " + "'"
				+ newEmployee.getName() + "'" + ", PROFESSION = " + "'"
				+ newEmployee.getProfession() + "'" + ", POINT_ID = "
				+ newEmployee.getPointID() + " where EMPLOYEE_ID = " + id + ";");
	}

	@Override
	public void deleteRow(String id) throws SQLException {
		Database.update("delete from employees where EMPLOYEE_ID = " + id);
	}

	@Override
	public Entity getRow(String id) throws SQLException {
		Employee employee;
		ResultSet resultSet = Database
				.getResultSet("Select * from employees where EMPLOYEE_ID=" + id
						+ ";");
		employee = new Employee(resultSet.getString("EMPLOYEE_ID"),
				resultSet.getString("PASSPORT"), resultSet.getString("NAME"),
				resultSet.getString("PROFESSION"),
				resultSet.getString("POINT_ID"));
		return employee;
	}

	public static ResultSet getFormatted(Entity entity) {
		Employee employee = (Employee)entity;
		String firstHalf = "select * from " + name;
		String secondHalf = "";
		if (employee.getEmployeeID().length() != 0){
			secondHalf += " EMPLOYEE_ID = " + employee.getEmployeeID();
		}
		if (employee.getName().length() != 0){
			if (employee.getEmployeeID().length() != 0)
				secondHalf+=" and ";
			secondHalf += " NAME = " + "'" + employee.getName() + "'";
		}
		if (employee.getPassport().length() != 0){
			if (employee.getName().length() != 0)
				secondHalf+=" and ";
			secondHalf += " PASSPORT = " + "'" + employee.getPassport() + "'";
		}
		if (employee.getPointID().length() != 0){
			if (employee.getPassport().length() != 0)
				secondHalf+=" and ";
			secondHalf += " POINT_ID = " + employee.getPointID();
		}
		if (employee.getProfession().length() != 0){
			if (employee.getPointID().length() != 0)
				secondHalf+=" and ";
			secondHalf += " PROFESSION = "+ "'" + employee.getProfession() + "'";
		}
		if (secondHalf.length() != 0){
			firstHalf += " where " + secondHalf + ";";
			System.out.println(firstHalf);
			return Database.getResultSet(firstHalf);
		}
		return Database.getResultSet(firstHalf + ";");
	}

}
