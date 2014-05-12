package ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ui.MyTableModel;
import ui.pages.MainPage;
import ui.pages.TimesheetPage;

import database.Database;
import database.tables.Timesheets;
import entities.Employee;
import entities.Point;
import entities.Timesheet;

public class TimesheetPageController implements ActionListener {

	private TimesheetPage page;

	private Timesheet timesheet;

	private MyTableModel model;

	public TimesheetPageController(TimesheetPage page) {

		this.page = page;

		model = new MyTableModel(Database.employees.getAll());

		if (page.getMode() != MainPageController.MODE_FILTER) {
			try {
				timesheet = (Timesheet) Database.getTimesheets().getRow(
						page.getTimesheetID());
			} catch (NumberFormatException e) {
				System.out.println("Error: something with numbers...");
			} catch (SQLException e) {
				System.out.println("Error: unable to read timesheet");
			}
		} else
			timesheet = new Timesheet("", "", "", "");
	}

	public void fillAll() {
		page.getTimesheetIDField().setText(timesheet.getTimesheetID());
		for (int i = 0; i < model.getRowCount(); i++) {
			page.getEmployeeIDField().addItem((String) model.getValueAt(i, 2));
		}
		if (page.getMode() == MainPageController.MODE_EDIT) {
			Employee employee = null;
			try {
				employee = (Employee) Database.employees.getRow(timesheet
						.getEmployeeID());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			page.getEmployeeIDField().setSelectedItem(employee.getName());
			page.getDateField().setText(timesheet.getDate());
			page.getPlanField().setText(timesheet.getPlanPercentage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			Timesheet newTimesheet = new Timesheet(page.getTimesheetIDField()
					.getText(), (String) model.getValueAt(page
					.getEmployeeIDField().getSelectedIndex(), 0), page
					.getDateField().getText(), page.getPlanField().getText());
			if (page.getMode() == MainPageController.MODE_FILTER) {
				page.getMainPage()
						.getTimesheets()
						.setModel(
								new MyTableModel(Timesheets
										.getFormatted(newTimesheet)));
				page.dispose();
			} else {
				try {
					Database.getTimesheets().editRow(
							timesheet.getTimesheetID(), newTimesheet);
					MainPage m = page.getMainPage();
					MainPageController c = m.getController();
					try {
						c.updateTables();
					} catch (Exception ex) {
					} // I don't know where exception occurs!11
					page.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(page,
							"Sorry, there's something wrong.");
					e1.printStackTrace();
				}
			}

		} else if (e.getActionCommand() == "Close") {
			if (page.getMode() == MainPageController.MODE_ADD) {
				MyTableModel m = new MyTableModel(Database.getTimesheets()
						.getAll());
				Integer index = m.getRowCount();
				String neededID = (String) m.getValueAt(index - 1, 0);
				try {
					Database.getTimesheets().deleteRow(neededID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			page.dispose();
			page.getMainPage().getController().updateTables();
		}
	}
}
