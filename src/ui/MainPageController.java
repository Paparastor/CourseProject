package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import database.DBTable;
import database.Database;
import entities.Employee;
import entities.Entity;
import entities.Point;
import entities.Timesheet;

public class MainPageController implements ActionListener {

	public final static String NAME_EMPLOYEES = "employees";
	public final static String NAME_POINTS = "points";
	public final static String NAME_TIMESHEETS = "timesheets";

	public final static String SORT_EMPLOYEES = "employees sorting";
	public final static String SORT_POINTS = "points sorting";
	public final static String SORT_TIMESHEETS = "timesheets sorting";

	public final static String ACTION_ADD = "ADD";
	public final static String ACTION_DELETE = "DELETE";

	public final static String ACTION_ADD_EMPLOYEE = "New Employee";
	public final static String ACTION_ADD_POINT = "New Point";
	public final static String ACTION_ADD_TIMESHEET = "New Timesheet";

	public final static String ACTION_EDIT = "Edit";

	private MainPage mainPage;

	public MainPageController(MainPage mainPage) {

		this.mainPage = mainPage;

		// Database initialization
		Database.initializeDatabase();
	}

	public void updateTables() {
		mainPage.getEmployees().setModel(
				new MyTableModel(Database.getEmployees().getAll()));
		mainPage.getPoints().setModel(
				new MyTableModel(Database.getPoints().getAll()));
		mainPage.getTimesheets().setModel(
				new MyTableModel(Database.getTimesheets().getAll()));
	}

	private String getSelectedTab() {
		JTabbedPane p = mainPage.getTablePane();
		JPanel pan = (JPanel) p.getSelectedComponent();
		JScrollPane c = (JScrollPane) pan.getComponent(1);
		JViewport viewport = c.getViewport();
		JTable myTable = (JTable) viewport.getView();
		return myTable.getName();
	}

	private String getNeededID(Entity entity, DBTable table)
			throws SQLException {

		MyTableModel m = new MyTableModel(table.getAll());

		Integer index = m.getRowCount();
		mainPage.getController().updateTables();

		String neededID = (String) m.getValueAt(index - 1, 0);
		return neededID;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == ACTION_ADD) {
			switch (getSelectedTab()) {
			case NAME_EMPLOYEES:
				this.actionPerformed(new ActionEvent(this,
						ActionEvent.ACTION_PERFORMED, ACTION_ADD_EMPLOYEE));
				break;
			case NAME_POINTS:
				this.actionPerformed(new ActionEvent(this,
						ActionEvent.ACTION_PERFORMED, ACTION_ADD_POINT));
				break;
			case NAME_TIMESHEETS:
				this.actionPerformed(new ActionEvent(this,
						ActionEvent.ACTION_PERFORMED, ACTION_ADD_TIMESHEET));
				break;
			}
		}
		else if (e.getActionCommand() == ACTION_ADD_EMPLOYEE) {
			try {
				Employee employee = new Employee();
				Database.getEmployees().addRow(employee);
				DBTable table = Database.getEmployees();

				EmployeeEditPage emp = new EmployeeEditPage(getNeededID(
						employee, table), mainPage, true);
				emp.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand() == ACTION_ADD_POINT) {
			try {
				Point point = new Point();
				Database.getPoints().addRow(point);
				DBTable table = Database.getPoints();

				PointEditPage emp = new PointEditPage(
						getNeededID(point, table), mainPage, true);
				emp.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand() == ACTION_ADD_TIMESHEET) {
			try {
				Timesheet timesheet = new Timesheet();
				Database.getTimesheets().addRow(timesheet);
				DBTable table = Database.getTimesheets();

				TimesheetEditPage tm = new TimesheetEditPage(getNeededID(
						timesheet, table), mainPage, true);
				tm.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand() == ACTION_EDIT) {

			switch (getSelectedTab()) {
			case NAME_EMPLOYEES:
				String empModel = new MyTableModel(Database.getEmployees()
						.getAll()).getValueAt(
						mainPage.getEmployees().getSelectedRow(), 0).toString();
				EmployeeEditPage em = new EmployeeEditPage(empModel, mainPage,
						false);
				em.setVisible(true);
				break;
			case NAME_POINTS:
				String ptsModel = new MyTableModel(Database.getPoints()
						.getAll()).getValueAt(
						mainPage.getPoints().getSelectedRow(), 0).toString();
				PointEditPage pt = new PointEditPage(ptsModel, mainPage, false);
				pt.setVisible(true);
				break;
			case NAME_TIMESHEETS:
				String tsModel = new MyTableModel(Database.getTimesheets()
						.getAll()).getValueAt(
						mainPage.getTimesheets().getSelectedRow(), 0)
						.toString();
				TimesheetEditPage ts = new TimesheetEditPage(tsModel, mainPage,
						false);
				ts.setVisible(true);
				break;
			}
			System.out.println();
		}
		else if (e.getActionCommand() == ACTION_DELETE) {

			switch (getSelectedTab()) {
			case NAME_EMPLOYEES:
				String emp = new MyTableModel(Database.getEmployees().getAll())
						.getValueAt(mainPage.getEmployees().getSelectedRow(), 0)
						.toString();
				try {
					Database.getEmployees().deleteRow(emp);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case NAME_POINTS:
				String pts = new MyTableModel(Database.getPoints().getAll())
						.getValueAt(mainPage.getPoints().getSelectedRow(), 0)
						.toString();
				try {
					Database.getPoints().deleteRow(pts);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case NAME_TIMESHEETS:
				String ts = new MyTableModel(Database.getTimesheets().getAll())
						.getValueAt(mainPage.getTimesheets().getSelectedRow(),
								0).toString();
				try {
					Database.getTimesheets().deleteRow(ts);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			}
			updateTables();
		}
		else if (e.getActionCommand() == SORT_EMPLOYEES) {
			mainPage.getEmployees().setModel(
					new MyTableModel(Database.getEmployees().getAllFormatted(
							mainPage.getEmployeesBox().getSelectedItem()
									.toString())));
		}
		else if (e.getActionCommand() == SORT_POINTS) {
			mainPage.getPoints().setModel(
					new MyTableModel(Database.getPoints().getAllFormatted(
							mainPage.getPointsBox().getSelectedItem()
									.toString())));
		}
		else if (e.getActionCommand() == SORT_TIMESHEETS) {
			mainPage.getTimesheets().setModel(
					new MyTableModel(Database.getTimesheets().getAllFormatted(
							mainPage.getTimesheetsBox().getSelectedItem()
									.toString())));
		}
		
	}
}
