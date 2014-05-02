package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import database.Database;

import entities.Employee;

public class EmployeeEditPage extends JFrame {
	
	private EmployeeEditController controller;

	private final static int WIDTH = 250;
	private final static int HEIGHT = 250;
	private final static int FIELD_SIZE = 10;

	private static final long serialVersionUID = 1L;
	
	private String employeeID;
	
	private JTextField idTextField;
	private JTextField passportTextField;
	private JTextField nameTextField;
	private JTextField professionTextField;
	private JTextField pointIDTextField;
	
	public JTextField getIdTextField() {
		return idTextField;
	}
	
	public String getEmployeeID(){
		return employeeID;
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

	public EmployeeEditPage(String employeeID) {
		
		this.employeeID = employeeID;
		
		// Setting the controller
		controller = new EmployeeEditController(this);

		// Setting parameters of the frame
		this.setName("EmployeeEditPage");
		this.setTitle("Edit Employee");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// ID field
		JPanel id = new JPanel();
		id.setLayout(new FlowLayout(FlowLayout.RIGHT));
		id.add(new JLabel("ID:"));
		idTextField = new JTextField(FIELD_SIZE);
		id.add(idTextField);
		this.add(id);

		// Passport field
		JPanel passport = new JPanel();
		passport.setLayout(new FlowLayout(FlowLayout.RIGHT));
		passport.add(new JLabel("Passport:"));
		passportTextField = new JTextField(FIELD_SIZE);
		passport.add(passportTextField);
		this.add(passport);

		// Name field
		JPanel name = new JPanel();
		name.setLayout(new FlowLayout(FlowLayout.RIGHT));
		name.add(new JLabel("Name:"));
		nameTextField = new JTextField(FIELD_SIZE);
		name.add(nameTextField);
		this.add(name);

		// Profession field
		JPanel profession = new JPanel();
		profession.setLayout(new FlowLayout(FlowLayout.RIGHT));
		profession.add(new JLabel("Profession:"));
		professionTextField = new JTextField(FIELD_SIZE);
		profession.add(professionTextField);
		this.add(profession);

		// PointID field
		JPanel pointID = new JPanel();
		pointID.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pointID.add(new JLabel("Point ID:"));
		pointIDTextField = new JTextField(FIELD_SIZE);
		pointID.add(pointIDTextField);
		this.add(pointID);

		// Filling all fields
		controller.fillAll();

		// OK button
		final JFrame j = this;
		JButton ok = new JButton("OK");
		ok.addActionListener(controller);
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
