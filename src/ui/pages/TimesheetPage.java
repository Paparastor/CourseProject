package ui.pages;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.controllers.TimesheetPageController;

public class TimesheetPage extends JFrame {

	private TimesheetPageController controller;

	private final static int WIDTH = 270;
	private final static int HEIGHT = 250;
	private final static int FIELD_SIZE = 10;

	private int mode;

	private static final long serialVersionUID = 1L;

	private String timesheetID;
	private MainPage mainPage;

	private JTextField timesheetIDField;
	private JComboBox<String> employeeIDField;
	private JTextField dateField;
	private JTextField planField;

	public int getMode() {
		return mode;
	}

	public MainPage getMainPage() {
		return mainPage;
	}

	public String getTimesheetID() {
		return timesheetID;
	}

	public JTextField getTimesheetIDField() {
		return timesheetIDField;
	}

	public JComboBox<String> getEmployeeIDField() {
		return employeeIDField;
	}

	public JTextField getDateField() {
		return dateField;
	}

	public JTextField getPlanField() {
		return planField;
	}

	public TimesheetPage(String timesheetID, MainPage mainPage, int mode) {

		this.timesheetID = timesheetID;
		this.mainPage = mainPage;
		this.mode = mode;

		// Setting the controller
		controller = new TimesheetPageController(this);

		// Setting parameters of the frame
		this.setName("TimesheetEditPage");
		this.setTitle("Edit Timesheet");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// Timesheet ID field
		JPanel timesheetIDPanel = new JPanel();
		timesheetIDPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		timesheetIDPanel.add(new JLabel("Timesheet ID:"));
		timesheetIDField = new JTextField(FIELD_SIZE);
		timesheetIDPanel.add(timesheetIDField);
		this.add(timesheetIDPanel);

		// Employee ID field
		JPanel employeeIDPanel = new JPanel();
		employeeIDPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		employeeIDPanel.add(new JLabel("Employee:"));
		employeeIDField = new JComboBox<String>();
		employeeIDPanel.add(employeeIDField);
		this.add(employeeIDPanel);

		// Date field
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		datePanel.add(new JLabel("Date:"));
		dateField = new JTextField(FIELD_SIZE);
		datePanel.add(dateField);
		this.add(datePanel);

		// Plan field
		JPanel planPanel = new JPanel();
		planPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		planPanel.add(new JLabel("Plan:"));
		planField = new JTextField(FIELD_SIZE);
		planPanel.add(planField);
		this.add(planPanel);

		// Filling all fields
		controller.fillAll();

		// OK button
		JButton ok = new JButton("OK");
		ok.addActionListener(controller);
		this.add(ok);

		// Close button
		JButton close = new JButton("Close");
		close.addActionListener(controller);
		this.add(close);

	}
}
