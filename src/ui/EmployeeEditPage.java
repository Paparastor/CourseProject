package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import database.Database;

import entities.Employee;

public class EmployeeEditPage extends JFrame {

	private final static int WIDTH = 250;
	private final static int HEIGHT = 250;
	private final static int FIELD_SIZE = 10;

	private JTextField idTextField;
	private JTextField passportTextField;
	private JTextField nameTextField;
	private JTextField professionTextField;
	private JTextField pointIDTextField;

	public JTextField getIdTextField() {
		return idTextField;
	}

	public JTextField getPassportTextField() {
		return passportTextField;
	}

	public JTextField getNameTextField() {
		return nameTextField;
	}

	public JTextField getProfessionTextField() {
		return professionTextField;
	}

	public JTextField getPointIDTextField() {
		return pointIDTextField;
	}

	public Employee getEmployee() {
		return employee;
	}

	private Employee employee;

	private static final long serialVersionUID = 1L;

	public EmployeeEditPage(String employeeID) {

		try {
			employee = Database.Employees.getEmployee(employeeID);
		} catch (NumberFormatException e) {
			System.out.println("Error: something with numbers...");
		} catch (SQLException e) {
			System.out.println("Error: unable to read employee");
		}

		this.setName("EmployeeEditPage");
		this.setTitle("Edit Employee");
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// ID
		JPanel id = new JPanel();
		id.setLayout(new FlowLayout(FlowLayout.RIGHT));
		id.add(new JLabel("ID:"));
		idTextField = new JTextField(employee.getEmployeeID(), FIELD_SIZE);
		id.add(idTextField);
		this.add(id);

		// Passport
		JPanel passport = new JPanel();
		passport.setLayout(new FlowLayout(FlowLayout.RIGHT));
		passport.add(new JLabel("Passport:"));
		passportTextField = new JTextField(employee.getPassport(), FIELD_SIZE);
		passport.add(passportTextField);
		this.add(passport);

		// Name
		JPanel name = new JPanel();
		name.setLayout(new FlowLayout(FlowLayout.RIGHT));
		name.add(new JLabel("Name:"));
		nameTextField = new JTextField(employee.getName(), FIELD_SIZE);
		name.add(nameTextField);
		this.add(name);

		// Profession
		JPanel profession = new JPanel();
		profession.setLayout(new FlowLayout(FlowLayout.RIGHT));
		profession.add(new JLabel("Profession:"));
		professionTextField = new JTextField(employee.getProfession(),
				FIELD_SIZE);
		profession.add(professionTextField);
		this.add(profession);

		// PointID
		JPanel pointID = new JPanel();
		pointID.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pointID.add(new JLabel("Point ID:"));
		pointIDTextField = new JTextField(employee.getPointID(), FIELD_SIZE);
		pointID.add(pointIDTextField);
		this.add(pointID);

		final JFrame j = this;

		// OK button
		JButton ok = new JButton("OK");
		this.add(ok);

		// Close button
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				j.dispose();
			}
		});
		this.add(close);

	}

}
