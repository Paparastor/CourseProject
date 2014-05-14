package ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.*;

import com.itextpdf.text.DocumentException;

import ui.MyTableModel;
import ui.pages.DialogPage;
import ui.pages.EmployeeFilterPage;
import ui.pages.EmployeePage;
import ui.pages.MainPage;
import ui.pages.PointFilterPage;
import ui.pages.PointPage;
import ui.pages.SearchPage;
import ui.pages.TimesheetFilterPage;
import ui.pages.TimesheetPage;

import database.Database;
import database.reports.ReportCreator;
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
	public final static String NAME_COMMON = "common";

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
	public final static String ACTION_REPORT = "REPORT";
	public final static String ACTION_COMBOBOX_CHANGED = "comboBoxChanged";
	public final static String ACTION_EXECUTE = "EXECUTE";

	// Reports action
	public final static String ACTION_REPORT_AUTOMATIATION = "auto";
	public final static String ACTION_REPORT_ENTITY = "entity report";

	// Specific actions for a different tables
	public final static String ACTION_ADD_EMPLOYEE = "New Employee";
	public final static String ACTION_ADD_POINT = "New Point";
	public final static String ACTION_ADD_TIMESHEET = "New Timesheet";

	// Modes
	public static final int MODE_EDIT = 0;
	public static final int MODE_ADD = 1;
	public static final int MODE_FILTER = 2;

	// Manually written queries
	public final static String QUERY_EMPLOYEES_ON_POINT = "Get employees of selected point";
	public final static String QUERY_TIMESHEETS_ON_EMPLOYEE = "Get timesheets of selected employee";
	public final static String QUERY_TIMESHEETS_ON_DATE = "Get timesheets of selected date";
	public final static String QUERY_BLANK = "";

	private String currentPointsQuery;
	private boolean currentPointsState;

	private String currentEmployeesQuery;
	private boolean currentEmployeesState;

	private String currentTimesheetsQuery;
	private boolean currentTimesheetsState;

	public String getcurrentpointsquery() {
		return currentPointsQuery;
	}

	public String getCurrentEmployeesQuery() {
		return currentEmployeesQuery;
	}

	public String getCurrentTimesheetsQuery() {
		return currentTimesheetsQuery;
	}

	public String getCurrentPointsQuery() {
		return currentPointsQuery;
	}

	public void setCurrentPointsQuery(String currentPointsQuery) {
		this.currentPointsQuery = currentPointsQuery;
	}

	public void setCurrentEmployeesQuery(String currentEmployeesQuery) {
		this.currentEmployeesQuery = currentEmployeesQuery;
	}

	public void setCurrentTimesheetsQuery(String currentTimesheetsQuery) {
		this.currentTimesheetsQuery = currentTimesheetsQuery;
	}

	// Reference to a main page
	private MainPage mainPage;

	public MainPageController(MainPage mainPage) {

		this.mainPage = mainPage;

		// Database initialization
		Database.initializeDatabase();
	}

	// Updating all the tables
	public void updateTables() {
		currentEmployeesQuery = "select * from employees;";
		currentEmployeesState = true;

		currentPointsQuery = "select * from points;";
		currentPointsState = true;

		currentTimesheetsQuery = "select * from timesheets;";
		currentTimesheetsState = true;

		mainPage.getEmployees().setModel(
				new MyTableModel(Database.getResultSet(currentEmployeesQuery)));
		mainPage.getPoints().setModel(
				new MyTableModel(Database.getResultSet(currentPointsQuery)));
		mainPage.getTimesheets()
				.setModel(
						new MyTableModel(Database
								.getResultSet(currentTimesheetsQuery)));
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
		int col;
		String name;
		String newQuery;
		String state;
		switch (getSelectedTab()) {
		case NAME_EMPLOYEES:
			col = mainPage.getEmployees().columnAtPoint(e.getPoint());
			name = mainPage.getEmployees().getColumnName(col);
			if (currentEmployeesState) {
				state = " asc";
				currentEmployeesState = false;
			} else {
				state = " desc";
				currentEmployeesState = true;
			}
			newQuery = currentEmployeesQuery.substring(0,
					currentEmployeesQuery.length() - 1)
					+ " order by " + name + state + ";";
			System.out.println(newQuery);
			mainPage.getEmployees().setModel(
					new MyTableModel(Database.getResultSet(newQuery)));
			break;
		case NAME_POINTS:
			col = mainPage.getPoints().columnAtPoint(e.getPoint());
			name = mainPage.getPoints().getColumnName(col);
			if (currentPointsState) {
				state = " asc";
				currentPointsState = false;
			} else {
				state = " desc";
				currentPointsState = true;
			}
			newQuery = currentPointsQuery.substring(0,
					currentPointsQuery.length() - 1)
					+ " order by " + name + state + ";";
			mainPage.getPoints().setModel(
					new MyTableModel(Database.getResultSet(newQuery)));
			break;
		case NAME_TIMESHEETS:
			col = mainPage.getTimesheets().columnAtPoint(e.getPoint());
			name = mainPage.getTimesheets().getColumnName(col);
			if (currentTimesheetsState) {
				state = " asc";
				currentTimesheetsState = false;
			} else {
				state = " desc";
				currentTimesheetsState = true;
			}
			newQuery = currentTimesheetsQuery.substring(0,
					currentTimesheetsQuery.length() - 1)
					+ " order by "
					+ name + state
					+ ";";
			mainPage.getTimesheets().setModel(
					new MyTableModel(Database.getResultSet(newQuery)));
			break;
		}
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
				String empModel = (String) mainPage.getEmployees().getValueAt(
						mainPage.getEmployees().getSelectedRow(), 0);
				EmployeePage em = new EmployeePage(empModel, mainPage,
						MODE_EDIT);
				em.setVisible(true);
				break;
			case NAME_POINTS:
				String ptsModel = (String) mainPage.getPoints().getValueAt(
						mainPage.getPoints().getSelectedRow(), 0);
				PointPage pt = new PointPage(ptsModel, mainPage, MODE_EDIT);
				pt.setVisible(true);
				break;
			case NAME_TIMESHEETS:
				String tsModel = (String) mainPage.getTimesheets().getValueAt(
						mainPage.getTimesheets().getSelectedRow(), 0);
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
				String emp = (String) mainPage.getEmployees().getValueAt(
						mainPage.getEmployees().getSelectedRow(), 0);
				try {
					Database.getEmployees().deleteRow(emp);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case NAME_POINTS:
				String pts = (String) mainPage.getPoints().getValueAt(
						mainPage.getPoints().getSelectedRow(), 0);
				try {
					Database.getPoints().deleteRow(pts);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case NAME_TIMESHEETS:
				String ts = (String) mainPage.getTimesheets().getValueAt(
						mainPage.getTimesheets().getSelectedRow(), 0);
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
//				EmployeePage emp = new EmployeePage("", mainPage, MODE_FILTER);
//				emp.setVisible(true);
				EmployeeFilterPage emp = new EmployeeFilterPage(mainPage);
				emp.setVisible(true);
				break;
			case NAME_POINTS:
				// PointPage pt = new PointPage("", mainPage, MODE_FILTER);
				// pt.setVisible(true);
				PointFilterPage pt = new PointFilterPage(mainPage);
				pt.setVisible(true);
				break;
			case NAME_TIMESHEETS:
//				TimesheetPage ts = new TimesheetPage("", mainPage, MODE_FILTER);
//				ts.setVisible(true);
				TimesheetFilterPage ts = new TimesheetFilterPage(mainPage);
				ts.setVisible(true);
				break;
			}
			updateTables();
		}
		// General find action
		else if (e.getActionCommand() == ACTION_FIND) {
			SearchPage p;
			switch (getSelectedTab()) {
			case NAME_EMPLOYEES:
				p = new SearchPage(mainPage, mainPage.getEmployees());
				p.setVisible(true);
				break;
			case NAME_POINTS:
				p = new SearchPage(mainPage, mainPage.getPoints());
				p.setVisible(true);
				break;
			case NAME_TIMESHEETS:
				p = new SearchPage(mainPage, mainPage.getTimesheets());
				p.setVisible(true);
				break;
			}

		} else if (e.getActionCommand() == ACTION_COMBOBOX_CHANGED) {
			JComboBox<String> b = mainPage.getQueriesBox();
			String value = (String) b.getSelectedItem();
			if (value == QUERY_EMPLOYEES_ON_POINT
					|| value == QUERY_TIMESHEETS_ON_DATE
					|| value == QUERY_TIMESHEETS_ON_EMPLOYEE) {
				mainPage.getQueriesBox().setEditable(false);

			} else {
				mainPage.getQueriesBox().setEditable(true);
			}
		}
		// Query execution
		else if (e.getActionCommand() == ACTION_EXECUTE) {

			JComboBox<String> b = mainPage.getQueriesBox();
			String value = (String) b.getSelectedItem();
			DialogPage p;
			if (value == QUERY_EMPLOYEES_ON_POINT) {
				p = new DialogPage(mainPage,
						DialogPageController.PARAMETER_POINT);
				p.setVisible(true);
			} else if (value == QUERY_TIMESHEETS_ON_DATE) {
				p = new DialogPage(mainPage,
						DialogPageController.PARAMETER_DATE);
				p.setVisible(true);
			} else if (value == QUERY_TIMESHEETS_ON_EMPLOYEE) {
				p = new DialogPage(mainPage,
						DialogPageController.PARAMETER_EMPLOYEE);
				p.setVisible(true);
			} else
				mainPage.getCommon().setModel(
						new MyTableModel(Database.getResultSet(value)));
		} else if (e.getActionCommand() == ACTION_REPORT) {
			switch (getSelectedTab()) {
			case NAME_EMPLOYEES:
				try {
					Employee employee = (Employee) Database.getEmployees()
							.getRow((String) mainPage.getEmployees()
									.getValueAt(
											mainPage.getEmployees()
													.getSelectedRow(), 0));
					try {
						ReportCreator.getEmployeeInfo(employee);
					} catch (DocumentException e1) {
						e1.printStackTrace();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case NAME_POINTS:
				try {
					Point point = (Point) Database.getPoints().getRow(
							(String) mainPage.getPoints().getValueAt(
									mainPage.getPoints().getSelectedRow(), 0));
					try {
						ReportCreator.getPointInfo(point);
					} catch (DocumentException e1) {
						e1.printStackTrace();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case NAME_TIMESHEETS:
				try {
					Timesheet timesheet = (Timesheet) Database.getTimesheets()
							.getRow((String) mainPage.getTimesheets()
									.getValueAt(
											mainPage.getTimesheets()
													.getSelectedRow(), 0));
					try {
						ReportCreator.getTimesheetInfo(timesheet);
					} catch (DocumentException e1) {
						e1.printStackTrace();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			}
		} else if (e.getActionCommand() == ACTION_REPORT_AUTOMATIATION) {
			try {
				ReportCreator.generateAdministrationRecomendations();
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
		} else if (e.getActionCommand() == ACTION_REPORT_ENTITY) {
			this.actionPerformed(new ActionEvent(this,
					ActionEvent.ACTION_PERFORMED, ACTION_REPORT));
		}

	}
}
