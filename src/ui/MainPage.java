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
		//JMenu file = new JMenu("File");
		//menuBar.add(file);

		// 'Edit' item creation
		JMenu edit = new JMenu("Entity");
		JMenu addMenu = new JMenu("Add...");
		JMenu findMenu = new JMenu("Find...");

		addEmployeeItem = new JMenuItem(MainPageController.ACTION_ADD_EMPLOYEE);
		addEmployeeItem.addActionListener(controller);
		addMenu.add(addEmployeeItem);

		addPointItem = new JMenuItem(MainPageController.ACTION_ADD_POINT);
		addPointItem.addActionListener(controller);
		addMenu.add(addPointItem);

		addTimesheetItem = new JMenuItem("New Timesheet");
		addMenu.add(addTimesheetItem);

		edit.add(addMenu);
		edit.add(findMenu);
		menuBar.add(edit);

		// 'Query' item creation
		JMenu query = new JMenu("Query");
		menuBar.add(query);

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
		employees.getTableHeader().addMouseListener(controller);

		points = new JTable();
		points.setName(MainPageController.NAME_POINTS);
		points.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		points.getTableHeader().addMouseListener(controller);

		timesheets = new JTable();
		timesheets.setName(MainPageController.NAME_TIMESHEETS);
		timesheets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		timesheets.getTableHeader().addMouseListener(controller);

		controller.updateTables();

		tablePane = new JTabbedPane();

		// 'Employees' comboBox
		JPanel e = new JPanel();
		e.setLayout(new BoxLayout(e, BoxLayout.Y_AXIS));
		e.add(new JScrollPane(employees));

		// 'Points' comboBox
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(new JScrollPane(points));

		// 'Timesheets' comboBox
		JPanel t = new JPanel();
		t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
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
