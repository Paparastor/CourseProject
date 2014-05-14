package ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.MyTableModel;
import ui.pages.EmployeeFilterPage;
import ui.pages.TimesheetFilterPage;
import database.Database;

public class TimesheetFilterPageController implements ActionListener {

	private TimesheetFilterPage page;

	public TimesheetFilterPageController(TimesheetFilterPage page) {
		this.page = page;
	}

	// OK and Cancel options handling
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			String minimalPointID = page.getMinimalSalaryTextField().getText();
			String maximalPointID = page.getMaximalSalaryTextField().getText();
			String query = "select * from timesheets where ";
			if (minimalPointID.length() != 0) {
				query += " PLAN_PERCENTAGE > "  +minimalPointID ;
			} else
				query += " PLAN_PERCENTAGE>=0 ";
			if (maximalPointID.length() != 0) {
				query += " and PLAN_PERCENTAGE<="  + maximalPointID ;
			}
			query +=";";
			System.out.println(query);
			page.getMainPage().getTimesheets()
					.setModel(new MyTableModel(Database.getResultSet(query)));
			page.getMainPage().getController().setCurrentTimesheetsQuery(query);
		
		}
		if (e.getActionCommand() == "Close") {
			page.dispose();
		}

	}
}
