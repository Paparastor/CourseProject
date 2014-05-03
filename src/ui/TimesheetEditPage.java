package ui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TimesheetEditPage extends JFrame {

	private TimesheetEditController controller;

	private final static int WIDTH = 250;
	private final static int HEIGHT = 250;
	private final static int FIELD_SIZE = 10;

	public static final boolean MODE_EDIT = false;
	public static final boolean MODE_ADD = true;

	private boolean mode;

	private static final long serialVersionUID = 1L;

	private String timesheetID;
	private MainPage mainPage;

	private JTextField timesheetIDField;
	private JTextField employeeIDField;
	private JTextField dateField;
	private JTextField planField;

	public boolean getMode() {
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

	public JTextField getEmployeeIDField() {
		return employeeIDField;
	}

	public JTextField getDateField() {
		return dateField;
	}

	public JTextField getPlanField() {
		return planField;
	}

	public TimesheetEditPage(String timesheetID, MainPage mainPage, boolean mode) {

		this.timesheetID = timesheetID;
		this.mainPage = mainPage;
		this.mode = mode;

		// Setting the controller
		controller = new TimesheetEditController(this);

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
		employeeIDPanel.add(new JLabel("Employee ID:"));
		employeeIDField = new JTextField(FIELD_SIZE);
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
