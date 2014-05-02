package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.Database;
import entities.Employee;

public class MainPageController implements ActionListener,
		ListSelectionListener {

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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "New Employee") {
			try {
				Employee employee = new Employee("NULL", "0","empty","empty","0");
				Database.Employees.addEmployee(employee);
				
				MyTableModel m = new MyTableModel(Database.Employees.getAll());
				
				Integer index = m.getRowCount();
				mainPage.getController().updateTables();
				
				String neededID =  (String) mainPage.getEmployees().getModel().getValueAt(index-1,0);
				
				EmployeeEditPage emp = new EmployeeEditPage(neededID.toString(), mainPage, true);
				emp.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (!arg0.getValueIsAdjusting()) { 										// Preventing a double event firing
			String a = new MyTableModel(Database.Employees.getAll())
					.getValueAt(mainPage.getEmployees().getSelectedRow(), 0)
					.toString();
			EmployeeEditPage e = new EmployeeEditPage(a, mainPage, false);
			e.setVisible(true);
		}
	}

}
