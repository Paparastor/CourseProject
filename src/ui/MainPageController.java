package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.Database;

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

	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (!arg0.getValueIsAdjusting()) { 										// Preventing a double event firing
			String a = new MyTableModel(Database.Employees.getAll())
					.getValueAt(mainPage.getEmployees().getSelectedRow(), 0)
					.toString();
			EmployeeEditPage e = new EmployeeEditPage(a);
			e.setVisible(true);
		}
	}

}
