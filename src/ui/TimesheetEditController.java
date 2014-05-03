package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.Database;
import entities.Timesheet;

public class TimesheetEditController implements ActionListener  {
	
	private TimesheetEditPage page;

	private Timesheet timesheet;

	public TimesheetEditController(TimesheetEditPage page) {

		this.page = page;

		try {
			timesheet = Database.Timesheets.getTimesheet(page.getTimesheetID());
		} catch (NumberFormatException e) {
			System.out.println("Error: something with numbers...");
		} catch (SQLException e) {
			System.out.println("Error: unable to read timesheet");
		}
	}

	public void fillAll() {
		page.getTimesheetIDField().setText(timesheet.getTimesheetID());
		page.getEmployeeIDField().setText(timesheet.getEmployeeID());
		page.getDateField().setText(timesheet.getDate());
		page.getPlanField().setText(timesheet.getPlanPercentage());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			Timesheet newTimesheet = new Timesheet(page.getTimesheetIDField().getText(),page.getEmployeeIDField().getText(),page.getDateField().getText(),
					page.getPlanField().getText());
			try {
				Database.Timesheets.editTimesheet(timesheet.getTimesheetID(), newTimesheet);
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

		} else if (e.getActionCommand() == "Close") {
			if (page.getMode()) {
				MyTableModel m = new MyTableModel(Database.Timesheets.getAll());
				Integer index = m.getRowCount();
				String neededID = (String) m.getValueAt(index - 1, 0);
				try {
					Database.Timesheets.deleteTimesheet(neededID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			page.dispose();
			page.getMainPage().getController().updateTables();
		}
	}
}
