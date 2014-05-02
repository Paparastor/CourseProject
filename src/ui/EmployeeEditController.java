package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.Database;
import entities.Employee;

public class EmployeeEditController implements ActionListener {

	private EmployeeEditPage page;

	private Employee employee;

	public EmployeeEditController(EmployeeEditPage page) {

		this.page = page;

		try {
			employee = Database.Employees.getEmployee(page.getEmployeeID());
		} catch (NumberFormatException e) {
			System.out.println("Error: something with numbers...");
		} catch (SQLException e) {
			System.out.println("Error: unable to read employee");
		}
	}

	public void fillAll() {
		page.getIdTextField().setText(employee.getEmployeeID());
		page.getPassportTextField().setText(employee.getPassport());
		page.getNameTextField().setText(employee.getName());
		page.getProfessionTextField().setText(employee.getProfession());
		page.getPointIDTextField().setText(employee.getPointID());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			Employee newEmployee = new Employee(
					page.getIdTextField().getText(), page
							.getPassportTextField().getText(), page
							.getNameTextField().getText(), page
							.getProfessionTextField().getText(), page
							.getPointIDTextField().getText());
			try {
				Database.Employees.editEmployee(employee.getEmployeeID(),
						newEmployee);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(page, "Sorry, there's something wrong.");
				MainPage m = page.getMainPage();
				MainPageController c = m.getController();
				try {										
					c.updateTables();
					page.dispose();
				} catch (Exception ex) {}			// I don't know where exception occurs!11
			}
			
		} else if (e.getActionCommand() == "Close") {
			page.dispose();
		}
	}

}
