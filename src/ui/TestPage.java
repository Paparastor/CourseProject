package ui;

import java.awt.*;
import javax.swing.*;

import database.Database;

public class TestPage {

	private final static int WIDTH = 500;
	private final static int HEIGHT = 500;

	// Main frame
	private JFrame mainFrame;

	// Menu
	private JPanel menuPanel;
	private JMenuBar menuBar;

	// Table switcher
	private JPanel switchPanel;

	// Table
	private JTable table;
	private JScrollPane tablePane;

	public TestPage() {

		// Main frame creating
		mainFrame = new JFrame("TestPage");
		mainFrame.setSize(WIDTH, HEIGHT);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Database initialization
		Database.initializeDatabase();

		// Menu bar creation
		menuBar = new JMenuBar();
		//
		JMenu file = new JMenu("File");
		file.add(new JMenuItem("New..."));
		menuBar.add(file);
		//
		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);
		//
		JMenu query = new JMenu("Query");
		menuBar.add(query);
		//
		JMenu format = new JMenu("Edit");
		menuBar.add(format);
		//
		JMenu help = new JMenu("Help");
		menuBar.add(help);
		//
		menuPanel = new JPanel();
		menuPanel.setLayout(new FlowLayout());
		menuPanel.add(menuBar);
		mainFrame.add(menuPanel);

		// Switch
		switchPanel = new JPanel();
		switchPanel.setLayout(new FlowLayout());
		ButtonGroup bg = new ButtonGroup();
		JRadioButton emp = new JRadioButton("employees",true);
		switchPanel.add(emp);
		bg.add(emp);
		JRadioButton pts = new JRadioButton("points");
		switchPanel.add(pts);
		bg.add(pts);
		JRadioButton tst = new JRadioButton("timesheets");
		switchPanel.add(tst);
		bg.add(tst);
		mainFrame.add(switchPanel);


		// Table creation
		MyTableModel m = new MyTableModel(
				Database.getResultSet("select * from employees;"));
		table = new JTable(m);
		tablePane = new JScrollPane(table);
		mainFrame.add(tablePane);

		// Database closing
		Database.close();

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TestPage();
			}
		});
	}

}
