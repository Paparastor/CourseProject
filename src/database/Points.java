package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Entity;
import entities.Point;

public class Points extends DBTable {
	
	public static final String ORDER_BY_SALARY_ASC = "Order by salary (ascend)";
	public static final String ORDER_BY_SALARY_DESC = "Order by salary (descend)";

	public Points(String creationQuery, String name) {
		super(creationQuery, name);
	}

	@Override
	public void addRow(Entity entity) throws SQLException {
		Point point = (Point)entity;
		Database.update("insert into points values(" + point.getPointID() + ","
				+ point.getSalary() + "," + "\"" + point.getPost() + "\""
				+ ");");
	}

	@Override
	public void editRow(String id, Entity newEntity) throws SQLException {
		Point newPoint = (Point)newEntity;
		Database.update("update points set " + "POINT_ID = " + newPoint.getPointID()
				+ ", SALARY = " + newPoint.getSalary() + ", POST = " + "'"
				+ newPoint.getPost() + "'" + " where POINT_ID = " + id
				+ ";");
	}

	@Override
	public void deleteRow(String id) throws SQLException {
		Database.update("delete from points where POINT_ID = " + id);
	}

	@Override
	public Entity getRow(String id) throws SQLException {
		Point point;
		ResultSet resultSet = Database.getResultSet("Select * from points where POINT_ID="
				+ id + ";");
		point = new Point(resultSet.getString("POINT_ID"),
				resultSet.getString("SALARY"), resultSet.getString("POST"));
		return point;
	}

	@Override
	public ResultSet getAllFormatted(String orderField) {
		switch (orderField) {
		case ORDER_BY_SALARY_ASC:
			return Database.getResultSet("select * from " + super.getName() +" order by SALARY asc;");
		case ORDER_BY_SALARY_DESC:
			return Database.getResultSet("select * from " + super.getName() +" order by SALARY desc;");
		case ORDER_BY_ID:
			return Database.getPoints().getAll();
		}
		return null;
	}

}
