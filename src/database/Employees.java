package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.*;

public class Employees extends DBTable {

	public static final String ORDER_BY_NAME = "Order by names";
	public static final String ORDER_BY_POINT = "Order by points";
	
	public static final String FILTER_BY_ = "Order by points";

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
	public ResultSet getAllFormatted(String orderField) {
		switch (orderField) {
		case ORDER_BY_NAME:
			return Database.getResultSet("select * from " + super.getName() +" order by NAME;");
		case ORDER_BY_POINT:
			return Database.getResultSet("select * from employees order by POINT_ID;");
		case ORDER_BY_ID:
			return Database.getEmployees().getAll();
		}
		return null;
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

}
