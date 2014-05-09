package ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.*;

import ui.MyTableModel;
import ui.pages.EmployeePage;
import ui.pages.MainPage;
import ui.pages.PointPage;
import ui.pages.TimesheetPage;

import database.Database;
import database.tables.DBTable;
import entities.Employee;
import entities.Entity;
import entities.Point;
import entities.Timesheet;

public class MainPageController extends MouseAdapter implements ActionListener {

	// Tables names
	public final static String NAME_EMPLOYEES = "employees";
	public final static String NAME_POINTS = "points";
	public final static String NAME_TIMESHEETS = "timesheets";

	// Sorting actions for a different tables
	public final static String SORT_EMPLOYEES = "employees sorting";
	public final static String SORT_POINTS = "points sorting";
	public final static String SORT_TIMESHEETS = "timesheets sorting";

	// General actions
	public final static String ACTION_ADD = "ADD";
	public final static String ACTION_DELETE = "DELETE";
	public final static String ACTION_EDIT = "EDIT";
	public final static String ACTION_FILTER = "FILTER";
	public final static String ACTION_FIND = "FIND";

	// Specific actions for a different tables
	public final static String ACTION_ADD_EMPLOYEE = "New Employee";
	public final static String ACTION_ADD_POINT = "New Point";
	public final static String ACTION_ADD_TIMESHEET = "New Timesheet";

	public static final int MODE_EDIT = 0;
	public static final int MODE_ADD = 1;
	public static final int MODE_FILTER = 2;

	// Reference to a main page
	private MainPage mainPage;

	public MainPageController(MainPage mainPage) {

		this.mainPage = mainPage;

		// Database initialization
		Database.initializeDatabase();
	}

	// Updating all the tables
	public void updateTables() {
		mainPage.getEmployees().setModel(
				new MyTableModel(Database.getEmployees().getAll()));
		mainPage.getPoints().setModel(
				new MyTableModel(Database.getPoints().getAll()));
		mainPage.getTimesheets().setModel(
				new MyTableModel(Database.getTimesheets().getAll()));
	}

	// Getting currently selected tab
	private String getSelectedTab() {
		JTabbedPane p = mainPage.getTablePane();
		JPanel pan = (JPanel) p.getSelectedComponent();
		JScrollPane c = (JScrollPane) pan.getComponent(0);
		JViewport viewport = c.getViewport();
		JTable myTable = (JTable) viewport.getView();
		return myTable.getName();
	}

	// Getting entities index
	private String getNeededID(Entity entity, DBTable table)
			throws SQLException {

		MyTableModel m = new MyTableModel(table.getAll());

		Integer index = m.getRowCount();
		mainPage.getController().updateTables();

		String neededID = (String) m.getValueAt(index - 1, 0);
		return neededID;
	}

	// Sorting action
	@Override
	public void mouseClicked(MouseEvent e) {
		JTable table;
		DBTable dbTable;
		switch (getSelectedTab()) {
		case NAME_EMPLOYEES:
			table = mainPage.getEmployees();
			dbTable = Database.getEmployees();
			break;
		case NAME_POINTS:
			table = mainPage.getPoints();
			dbTable = Database.getPoints();
			break;
		case NAME_TIMESHEETS:
			table = mainPage.getTimesheets();
			dbTable = Database.getTimesheets();
			break;
		default:
			table = null;
			dbTable = null;
			break;
		}
		int col = table.columnAtPoint(e.getPoint());
		String name = table.getColumnName(col);
		table.setModel(new MyTableModel(dbTable.getAll(name)));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// General addition action
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
		// Specific addition action for 'employees'
		else if (e.getActionCommand() == ACTION_ADD_EMPLOYEE) {
			try {
				Employee employee = new Employee();
				Database.getEmployees().addRow(employee);
				DBTable table = Database.getEmployees();

				EmployeePage emp = new EmployeePage(
						getNeededID(employee, table), mainPage, MODE_ADD);
				emp.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		// Specific addition action for 'points'
		else if (e.getActionCommand() == ACTION_ADD_POINT) {
			try {
				Point point = new Point();
				Database.getPoints().addRow(point);
				DBTable table = Database.getPoints();

				PointPage emp = new PointPage(getNeededID(point, table),
						mainPage, MODE_ADD);
				emp.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		// Specific addition action for 'timesheets'
		else if (e.getActionCommand() == ACTION_ADD_TIMESHEET) {
			try {
				Timesheet timesheet = new Timesheet();
				Database.getTimesheets().addRow(timesheet);
				DBTable table = Database.getTimesheets();

				TimesheetPage tm = new TimesheetPage(getNeededID(timesheet,
						table), mainPage, MODE_ADD);
				tm.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		// General action for editing
		else if (e.getActionCommand() == ACTION_EDIT) {
			// Editing specification
			switch (getSelectedTab()) {
			case NAME_EMPLOYEES:
				String empModel = new MyTableModel(Database.getEmployees()
						.getAll()).getValueAt(
						mainPage.getEmployees().getSelectedRow(), 0).toString();
				EmployeePage em = new EmployeePage(empModel, mainPage,
						MODE_EDIT);
				em.setVisible(true);
				break;
			case NAME_POINTS:
				String ptsModel = new MyTableModel(Database.getPoints()
						.getAll()).getValueAt(
						mainPage.getPoints().getSelectedRow(), 0).toString();
				PointPage pt = new PointPage(ptsModel, mainPage, MODE_EDIT);
				pt.setVisible(true);
				break;
			case NAME_TIMESHEETS:
				String tsModel = new MyTableModel(Database.getTimesheets()
						.getAll()).getValueAt(
						mainPage.getTimesheets().getSelectedRow(), 0)
						.toString();
				TimesheetPage ts = new TimesheetPage(tsModel, mainPage,
						MODE_EDIT);
				ts.setVisible(true);
				break;
			}
			System.out.println();
		}
		// General delete action
		else if (e.getActionCommand() == ACTION_DELETE) {
			// Deletion specification
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
		// General filter action
		else if (e.getActionCommand() == ACTION_FILTER) {
			// Deletion specification
			switch (getSelectedTab()) {
			case NAME_EMPLOYEES:
				EmployeePage emp = new EmployeePage("",mainPage,MODE_FILTER);
				emp.setVisible(true);
				break;
			case NAME_POINTS:
				PointPage pt = new PointPage("",mainPage,MODE_FILTER);
				pt.setVisible(true);
				break;
			case NAME_TIMESHEETS:
				TimesheetPage ts = new TimesheetPage("",mainPage,MODE_FILTER);
				ts.setVisible(true);
				break;
			}
			updateTables();
		}
	}
}