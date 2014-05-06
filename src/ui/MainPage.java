package ui;

import java.awt.*;
import javax.swing.*;

import database.Employees;
import database.Points;
import database.Timesheets;

public class MainPage extends JFrame {

	private static final long serialVersionUID = 1L;

	private final static int WIDTH = 530;
	private final static int HEIGHT = 570;

	// Controller
	private MainPageController controller;

	// Menu
	private JPanel menuPanel;
	private JMenuBar menuBar;

	// Tables
	private JTable employees;
	private JTable points;
	private JTable timesheets;
	private JTabbedPane tablePane;

	// Menu items
	private JMenuItem addEmployeeItem;
	private JMenuItem addPointItem;
	private JMenuItem addTimesheetItem;

	// ComboBoxes
	private JComboBox<String> employeesBox;
	private JComboBox<String> pointsBox;
	private JComboBox<String> timesheetsBox;
	

	public JComboBox<String> getEmployeesBox() {
		return employeesBox;
	}

	public JComboBox<String> getPointsBox() {
		return pointsBox;
	}

	public JComboBox<String> getTimesheetsBox() {
		return timesheetsBox;
	}

	public JMenuItem getAddEmployeeItem() {
		return addEmployeeItem;
	}

	public JMenuItem getAddPointItem() {
		return addPointItem;
	}

	public JMenuItem getAddTimesheetItem() {
		return addTimesheetItem;
	}

	public MainPageController getController() {
		return controller;
	}

	public JTable getEmployees() {
		return employees;
	}

	public JTable getPoints() {
		return points;
	}

	public JTable getTimesheets() {
		return timesheets;
	}

	public JTabbedPane getTablePane() {
		return tablePane;
	}

	public MainPage() {

		controller = new MainPageController(this);

		// Main frame creating
		this.setName("MainPage");
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// Menu bar creation
		menuBar = new JMenuBar();

		// 'File' item creation
		JMenu file = new JMenu("File");
		menuBar.add(file);

		// 'Edit' item creation
		JMenu edit = new JMenu("Edit");
		JMenu addMenu = new JMenu("Add...");

		addEmployeeItem = new JMenuItem(MainPageController.ACTION_ADD_EMPLOYEE);
		addEmployeeItem.addActionListener(controller);
		addMenu.add(addEmployeeItem);

		addPointItem = new JMenuItem(MainPageController.ACTION_ADD_POINT);
		addPointItem.addActionListener(controller);
		addMenu.add(addPointItem);

		addTimesheetItem = new JMenuItem("New Timesheet");
		addMenu.add(addTimesheetItem);

		edit.add(addMenu);
		menuBar.add(edit);

		// 'Query' item creation
		JMenu query = new JMenu("Query");
		menuBar.add(query);

		// 'Format' item creation
		JMenu format = new JMenu("Format");
		menuBar.add(format);

		// 'Help' item creation
		JMenu help = new JMenu("Help");
		menuBar.add(help);

		menuPanel = new JPanel();
		menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		menuPanel.add(menuBar);
		this.add(menuPanel);

		// Buttons panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton addButton = new JButton("Add");
		addButton.setActionCommand(MainPageController.ACTION_ADD);
		addButton.addActionListener(controller);
		buttonPanel.add(addButton);

		JButton editButton = new JButton("Edit");
		editButton.setActionCommand(MainPageController.ACTION_EDIT);
		editButton.addActionListener(controller);
		buttonPanel.add(editButton);

		JButton deleteButton = new JButton("Delete");
		deleteButton.setActionCommand(MainPageController.ACTION_DELETE);
		deleteButton.addActionListener(controller);
		buttonPanel.add(deleteButton);

		this.add(buttonPanel);

		// Table creation
		employees = new JTable();
		employees.setName(MainPageController.NAME_EMPLOYEES);
		employees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		points = new JTable();
		points.setName(MainPageController.NAME_POINTS);
		points.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		timesheets = new JTable();
		timesheets.setName(MainPageController.NAME_TIMESHEETS);
		timesheets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		controller.updateTables();

		tablePane = new JTabbedPane();

		// 'Employees' comboBox
		JPanel e = new JPanel();
		e.setLayout(new BoxLayout(e, BoxLayout.Y_AXIS));
		employeesBox = new JComboBox<String>(new String[] {
				Employees.ORDER_BY_ID, Employees.ORDER_BY_NAME,
				Employees.ORDER_BY_POINT });
		employeesBox.setActionCommand(MainPageController.SORT_EMPLOYEES);
		employeesBox.addActionListener(controller);
		e.add(employeesBox);
		e.add(new JScrollPane(employees));

		// 'Points' comboBox
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		pointsBox = new JComboBox<String>(new String[] { Points.ORDER_BY_ID,
				Points.ORDER_BY_SALARY_ASC, Points.ORDER_BY_SALARY_DESC });
		pointsBox.setActionCommand(MainPageController.SORT_POINTS);
		pointsBox.addActionListener(controller);
		p.add(pointsBox);
		p.add(new JScrollPane(points));

		// 'Timesheets' comboBox
		JPanel t = new JPanel();
		t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
		timesheetsBox = new JComboBox<String>(new String[] {
				Timesheets.ORDER_BY_ID, Timesheets.ORDER_BY_DATE, Timesheets.ORDER_BY_PLAN});
		timesheetsBox.setActionCommand(MainPageController.SORT_TIMESHEETS);
		timesheetsBox.addActionListener(controller);
		t.add(timesheetsBox);
		t.add(new JScrollPane(timesheets));

		tablePane.add("Employees", e);
		tablePane.add("Points", p);
		tablePane.add("Timesheets", t);
		this.add(tablePane);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainPage();
			}
		});
	}

}
