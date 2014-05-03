package ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.Database;
import entities.Employee;
import entities.Point;

public class MainPageController implements ActionListener,
		ListSelectionListener {

	public final static String NAME_EMPLOYEES = "employees";
	public final static String NAME_POINTS = "points";
	public final static String NAME_TIMESHEETS = "timesheets";

	public final static String ACTION_ADD = "ADD";
	public final static String ACTION_ADD_EMPLOYEE = "New Employee";
	public final static String ACTION_ADD_POINT = "New Point";

	public final static String ACTION_EDIT = "Edit";

	private MainPage mainPage;

	public MainPageController(MainPage mainPage) {

		this.mainPage = mainPage;

		// Database initialization
		Database.initializeDatabase();
	}

	public void updateTables() {
		mainPage.getEmployees().setModel(
				new MyTableModel(Database.Employees.getAll()));
		mainPage.getPoints().setModel(
				new MyTableModel(Database.Points.getAll()));
		mainPage.getTimesheets().setModel(
				new MyTableModel(Database.Timesheets.getAll()));
	}
	
	private String getSelectedTab(){
		JTabbedPane p = mainPage.getTablePane();
		JScrollPane c = (JScrollPane) p.getSelectedComponent();
		JViewport viewport = c.getViewport(); 
		JTable myTable = (JTable)viewport.getView();
		return myTable.getName();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == ACTION_ADD) {
			switch(getSelectedTab()){
			case ACTION_ADD_EMPLOYEE:
				new ActionEvent(this,(int) System.currentTimeMillis(),ACTION_ADD_EMPLOYEE);
				break;
			case ACTION_ADD_POINT:
				new ActionEvent(this,(int) System.currentTimeMillis(),ACTION_ADD_POINT);
				break;
			}
		}
		if (e.getActionCommand() == ACTION_ADD_EMPLOYEE) {
			try {
				Employee employee = new Employee("NULL", "0", "empty", "empty",
						"0");
				Database.Employees.addEmployee(employee);

				MyTableModel m = new MyTableModel(Database.Employees.getAll());

				Integer index = m.getRowCount();
				mainPage.getController().updateTables();

				String neededID = (String) mainPage.getEmployees().getModel()
						.getValueAt(index - 1, 0);

				EmployeeEditPage emp = new EmployeeEditPage(
						neededID.toString(), mainPage, true);
				emp.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand() == ACTION_ADD_POINT) {
			try {
				Point point = new Point("NULL", "0", "empty");
				Database.Points.addPoint(point);

				MyTableModel m = new MyTableModel(Database.Points.getAll());

				Integer index = m.getRowCount();
				mainPage.getController().updateTables();

				String neededID = (String) mainPage.getPoints().getModel()
						.getValueAt(index - 1, 0);

				PointEditPage emp = new PointEditPage(neededID.toString(),
						mainPage, true);
				emp.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand() == ACTION_EDIT) {
			
			switch (getSelectedTab()) {
			case NAME_EMPLOYEES:
				String empModel = new MyTableModel(Database.Employees.getAll())
						.getValueAt(mainPage.getEmployees().getSelectedRow(), 0)
						.toString();
				EmployeeEditPage em = new EmployeeEditPage(empModel, mainPage, false);
				em.setVisible(true);
				break;
			case NAME_POINTS:
				String ptsModel = new MyTableModel(Database.Points.getAll())
						.getValueAt(mainPage.getPoints().getSelectedRow(), 0)
						.toString();
				PointEditPage pt = new PointEditPage(ptsModel, mainPage, false);
				pt.setVisible(true);
				break;
			}
			System.out.println();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
//		if (!arg0.getValueIsAdjusting()) { // Preventing a double event firing
//			String a = new MyTableModel(Database.Employees.getAll())
//					.getValueAt(mainPage.getEmployees().getSelectedRow(), 0)
//					.toString();
//			EmployeeEditPage e = new EmployeeEditPage(a, mainPage, false);
//			e.setVisible(true);
//		}
	}

}
