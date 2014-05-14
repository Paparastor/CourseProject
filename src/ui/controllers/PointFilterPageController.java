package ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import database.Database;

import ui.MyTableModel;
import ui.pages.PointFilterPage;

public class PointFilterPageController implements ActionListener {

	private PointFilterPage page;

	public PointFilterPageController(PointFilterPage page) {
		this.page = page;
		MyTableModel model = new MyTableModel(
				Database.getResultSet("select POST from points;"));
		page.getPostBox().addItem("-");
		for (int i = 0; i < model.getRowCount(); i++) {
			page.getPostBox().addItem((String) model.getValueAt(i, 0));
		}
	}

	// OK and Cancel options handling
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			String minimalSalary = page.getMinimalSalaryTextField().getText();
			String maximalSalary = page.getMaximalSalaryTextField().getText();
			String post = (String)page.getPostBox().getSelectedItem();
			String query = "select * from points where ";
			if (minimalSalary.length() != 0) {
				query += " SALARY > " + minimalSalary;
			} else
				query += " SALARY>=0 ";
			if (maximalSalary.length() != 0) {
				query += " and SALARY<=" + maximalSalary;
			}
			if (post != "-") {
				query += " and POST LIKE '" + post + "'";
			}
			query +=";";
			page.getMainPage().getPoints()
					.setModel(new MyTableModel(Database.getResultSet(query)));
			page.getMainPage().getController().setCurrentPointsQuery(query);
		
		}
		if (e.getActionCommand() == "Close") {
			page.dispose();
		}

	}

}