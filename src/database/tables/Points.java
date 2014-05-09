package database.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;

import entities.Entity;
import entities.Point;

public class Points extends DBTable {
	
	public static final String name = "points";

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

	public static ResultSet getFormatted(Entity entity) {
		Point point = (Point)entity;
		String firstHalf = "select * from " + name;
		String secondHalf = "";
		if (point.getPointID().length() != 0){
			secondHalf += " POINT_ID = " + point.getPointID();
		}
		if (point.getPost().length() != 0){
			if (point.getPointID().length() != 0)
				secondHalf+=" and ";
			secondHalf += " POST = " + "'" + point.getPost() + "'";
		}
		if (point.getSalary().length() != 0){
			if (point.getPost().length() != 0)
				secondHalf+=" and ";
			secondHalf += " SALARY = " +  point.getSalary();
		}
		if (secondHalf.length() != 0){
			firstHalf += " where " + secondHalf + ";";
			System.out.println(firstHalf);
			return Database.getResultSet(firstHalf);
		}
		return Database.getResultSet(firstHalf + ";");
	}

}
