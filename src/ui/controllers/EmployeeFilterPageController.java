package ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.MyTableModel;
import ui.pages.EmployeeFilterPage;
import database.Database;

public class EmployeeFilterPageController implements ActionListener {

	private EmployeeFilterPage page;

	public EmployeeFilterPageController(EmployeeFilterPage page) {
		this.page = page;
		MyTableModel model = new MyTableModel(
				Database.getResultSet("select distinct PROFESSION from employees;"));
		page.getPostBox().addItem("-");
		for (int i = 0; i < model.getRowCount(); i++) {
			page.getPostBox().addItem((String) model.getValueAt(i, 0));
		}
	}

	// OK and Cancel options handling
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			String minimalPointID = page.getMinimalSalaryTextField().getText();
			String maximalPointID = page.getMaximalSalaryTextField().getText();
			String profession = (String)page.getPostBox().getSelectedItem();
			String query = "select * from employees where ";
			if (minimalPointID.length() != 0) {
				query += " POINT_ID > " + minimalPointID;
			} else
				query += " POINT_ID>=0 ";
			if (maximalPointID.length() != 0) {
				query += " and POINT_ID<=" + maximalPointID;
			}
			if (profession != "-") {
				query += " and PROFESSION LIKE '" + profession + "'";
			}
			query +=";";
			page.getMainPage().getEmployees()
					.setModel(new MyTableModel(Database.getResultSet(query)));
			page.getMainPage().getController().setCurrentEmployeesQuery(query);
		
		}
		if (e.getActionCommand() == "Close") {
			page.dispose();
		}

	}
}
