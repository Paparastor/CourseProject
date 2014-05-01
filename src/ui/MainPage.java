package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.Database;

public class MainPage extends JFrame {

	private static final long serialVersionUID = 1L;

	private final static int WIDTH = 500;
	private final static int HEIGHT = 600;

	// Controller
	private Controller controller;

	// Menu
	private JPanel menuPanel;
	private JMenuBar menuBar;

	// Tables
	private JTable employees;
	private JTable points;
	private JTable timesheets;
	private JTabbedPane tablePane;

	private void updateTables() {
		employees.setModel(new MyTableModel(Database.Employees.getAll()));
		points.setModel(new MyTableModel(Database.Points.getAll()));
		timesheets.setModel(new MyTableModel(Database.Timesheets.getAll()));
	}

	public MainPage() {

		controller = new Controller(this);

		// Main frame creating
		this.setName("MainPage");
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Database initialization
		Database.initializeDatabase();

		// Menu bar creation
		menuBar = new JMenuBar();

		// 'File' item creation
		JMenu file = new JMenu("File");
		menuBar.add(file);

		// 'Edit' item creation
		JMenu edit = new JMenu("Edit");
		JMenu addMenu = new JMenu("Add...");

		JMenuItem emp = new JMenuItem("New Employee");
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
		menuPanel.setLayout(new FlowLayout());
		menuPanel.add(menuBar);
		this.add(menuPanel);

		// Table creation
		employees = new JTable(new MyTableModel(Database.Employees.getAll()));
		employees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employees.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {

						if (!arg0.getValueIsAdjusting()) {
							String a = new MyTableModel(
									Database.Employees.getAll()).getValueAt(
									employees.getSelectedRow(), 0).toString();
							EmployeeEditPage e = new EmployeeEditPage(a);
						}
					}
				});

		points = new JTable(new MyTableModel(Database.Points.getAll()));
		points.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		timesheets = new JTable(new MyTableModel(Database.Timesheets.getAll()));
		timesheets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tablePane = new JTabbedPane();
		tablePane.add("Employees", new JScrollPane(employees));
		tablePane.add("Points", new JScrollPane(points));
		tablePane.add("Timesheets", new JScrollPane(timesheets));
		this.add(tablePane);

		// Database closing
		// Database.close();

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainPage();
			}
		});
	}

}
