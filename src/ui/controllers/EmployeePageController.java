package ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ui.MyTableModel;
import ui.pages.EmployeePage;
import ui.pages.MainPage;

import database.Database;
import database.tables.Employees;
import entities.Employee;
import entities.Point;

public class EmployeePageController implements ActionListener {

	private EmployeePage page;

	private Employee employee;

	private MyTableModel model;

	public EmployeePageController(EmployeePage page) {

		this.page = page;

		model = new MyTableModel(Database.points.getAll());

		if (page.getMode() != MainPageController.MODE_FILTER) {
			try {
				employee = (Employee) Database.getEmployees().getRow(
						page.getEmployeeID());
			} catch (NumberFormatException e) {
				System.out.println("Error: something with numbers...");
			} catch (SQLException e) {
				System.out.println("Error: unable to read employee");
			}
		} else
			employee = new Employee("", "", "", "", "");
	}

	public void fillAll() {
		page.getIdTextField().setText(employee.getEmployeeID());
		page.getPassportTextField().setText(employee.getPassport());
		page.getNameTextField().setText(employee.getName());
		page.getProfessionTextField().setText(employee.getProfession());
		for (int i = 0; i < model.getRowCount(); i++) {
			page.getPointIDTextField().addItem((String) model.getValueAt(i, 2));
		}
		if (page.getMode() == MainPageController.MODE_EDIT) {
			Point point = null;
			try {
				point = (Point) Database.points.getRow(employee.getPointID());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			page.getPointIDTextField().setSelectedItem(point.getPost());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			Employee newEmployee = new Employee(
					page.getIdTextField().getText(), page
							.getPassportTextField().getText(), page
							.getNameTextField().getText(), page
							.getProfessionTextField().getText(),
					(String) model.getValueAt(page.getPointIDTextField()
							.getSelectedIndex(), 0));
			if (page.getMode() == MainPageController.MODE_FILTER) {
				page.getMainPage()
						.getEmployees()
						.setModel(
								new MyTableModel(Employees
										.getFormatted(newEmployee)));
				page.dispose();
			} else {
				try {
					Database.getEmployees().editRow(employee.getEmployeeID(),
							newEmployee);
					MainPage m = page.getMainPage();
					MainPageController c = m.getController();
					try {
						c.updateTables();
						page.dispose();
					} catch (Exception ex) {
					} // I don't know where exception occurs!11
					page.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(page,
							"Sorry, there's something wrong.");
				}
			}

		} else if (e.getActionCommand() == "Close") {
			if (page.getMode() == MainPageController.MODE_ADD) {
				MyTableModel m = new MyTableModel(Database.getEmployees()
						.getAll());
				Integer index = m.getRowCount();
				String neededID = (String) m.getValueAt(index - 1, 0);
				try {
					Database.getEmployees().deleteRow(neededID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			page.dispose();
			page.getMainPage().getController().updateTables();
		}
	}

}
