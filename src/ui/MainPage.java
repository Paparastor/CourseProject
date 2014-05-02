package ui;

import java.awt.*;
import javax.swing.*;

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

		JMenuItem emp = new JMenuItem("New Employee");
		emp.addActionListener(controller);
		addMenu.add(emp);

		JMenuItem poi = new JMenuItem("New Point");
		addMenu.add(poi);

		JMenuItem tim = new JMenuItem("New Timesheet");
		addMenu.add(tim);

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
		buttonPanel.add(addButton);
		
		JButton deleteButton = new JButton("Delete");
		buttonPanel.add(deleteButton);
		
		this.add(buttonPanel);
		
		// Table creation
		employees = new JTable();
		employees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employees.getSelectionModel().addListSelectionListener(controller);

		points = new JTable();
		points.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		timesheets = new JTable();
		timesheets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		controller.updateTables();

		tablePane = new JTabbedPane();
		tablePane.add("Employees", new JScrollPane(employees));
		tablePane.add("Points", new JScrollPane(points));
		tablePane.add("Timesheets", new JScrollPane(timesheets));
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
