package ui.pages;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import ui.controllers.MainPageController;

public class MainPage extends JFrame {

	public final static boolean MODE_ADMINISTRATIVE = true;
	public final static boolean MODE_MANAGER = false;
	
	private static final long serialVersionUID = 1L;

	private final static int WIDTH = 600;
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
	private JTable common;
	private JTabbedPane tablePane;

	// Fields
	private JComboBox<String> queriesBox;
	private JButton executeQueryButton;

	// Menu items
	private JMenuItem addEmployeeItem;
	private JMenuItem addPointItem;
	private JMenuItem addTimesheetItem;

	private String currentPointsQuery;
	private String currentEmployeesQuery;
	private String currentTimesheetsQuery;

	public String getCurrentPointsQuery() {
		return currentPointsQuery;
	}

	public String getCurrentEmployeesQuery() {
		return currentEmployeesQuery;
	}

	public String getCurrentTimesheetsQuery() {
		return currentTimesheetsQuery;
	}

	public void setCurrentPointsQuery(String currentPointsQuery) {
		this.currentPointsQuery = currentPointsQuery;
	}

	public void setCurrentEmployeesQuery(String currentEmployeesQuery) {
		this.currentEmployeesQuery = currentEmployeesQuery;
	}

	public void setCurrentTimesheetsQuery(String currentTimesheetsQuery) {
		this.currentTimesheetsQuery = currentTimesheetsQuery;
	}

	public JButton getExecuteQueryButton() {
		return executeQueryButton;
	}

	public JTable getCommon() {
		return common;
	}

	public JComboBox<String> getQueriesBox() {
		return queriesBox;
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

	public MainPage(boolean mode) {

		controller = new MainPageController(this);

		// Main frame creating
		this.setName("MainPage");
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// Menu bar creation
		menuBar = new JMenuBar();

		// 'Edit' item creation
		JMenu edit = new JMenu("Entity");
		JMenu addMenu = new JMenu("Add...");

		addEmployeeItem = new JMenuItem(MainPageController.ACTION_ADD_EMPLOYEE);
		addEmployeeItem.addActionListener(controller);
		addMenu.add(addEmployeeItem);

		addPointItem = new JMenuItem(MainPageController.ACTION_ADD_POINT);
		addPointItem.addActionListener(controller);
		addMenu.add(addPointItem);

		addTimesheetItem = new JMenuItem("New Timesheet");
		addTimesheetItem.addActionListener(controller);
		addMenu.add(addTimesheetItem);

		edit.add(addMenu);
		menuBar.add(edit);

		// 'Query' item creation
		JMenu report = new JMenu("Report");

		JMenuItem administrationRecomendations = new JMenuItem(
				"Administration Recomendations");
		administrationRecomendations
				.setActionCommand(MainPageController.ACTION_REPORT_AUTOMATIATION);
		administrationRecomendations.addActionListener(controller);
		report.add(administrationRecomendations);

		JMenuItem entityReport = new JMenuItem("Selected entity info");
		entityReport.setActionCommand(MainPageController.ACTION_REPORT_ENTITY);
		entityReport.addActionListener(controller);
		report.add(entityReport);

		menuBar.add(report);

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

		JButton filterButton = new JButton("Filter");
		filterButton.setActionCommand(MainPageController.ACTION_FILTER);
		filterButton.addActionListener(controller);
		buttonPanel.add(filterButton);

		JButton findButton = new JButton("Find");
		findButton.setActionCommand(MainPageController.ACTION_FIND);
		findButton.addActionListener(controller);
		buttonPanel.add(findButton);

		JButton reportButton = new JButton("Report");
		reportButton.setActionCommand(MainPageController.ACTION_REPORT);
		reportButton.addActionListener(controller);
		buttonPanel.add(reportButton);

		this.add(buttonPanel);

		// Table creation
		employees = new JTable();
		employees.setName(MainPageController.NAME_EMPLOYEES);
		employees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employees.getTableHeader().addMouseListener(controller);

		JTableHeader header = employees.getTableHeader();
		header.setUpdateTableInRealTime(true);
		//header.addMouseListener(tableModel.new ColumnListener(table));
		header.setReorderingAllowed(true);

		points = new JTable();
		points.setName(MainPageController.NAME_POINTS);
		points.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		points.getTableHeader().addMouseListener(controller);

		timesheets = new JTable();
		timesheets.setName(MainPageController.NAME_TIMESHEETS);
		timesheets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		timesheets.getTableHeader().addMouseListener(controller);

		common = new JTable();
		common.setName(MainPageController.NAME_COMMON);
		common.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		controller.updateTables();

		tablePane = new JTabbedPane();

		// 'Employees' pane item
		JPanel e = new JPanel();
		e.setLayout(new BoxLayout(e, BoxLayout.Y_AXIS));
		e.add(new JScrollPane(employees));

		// 'Points' pane item
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(new JScrollPane(points));

		// 'Timesheets' pane item
		JPanel t = new JPanel();
		t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
		t.add(new JScrollPane(timesheets));

		// 'Common' pane item
		JPanel c = new JPanel();
		queriesBox = new JComboBox<String>();
		queriesBox.setEditable(false);
		queriesBox.addActionListener(controller);
		queriesBox.addItem(MainPageController.QUERY_BLANK);
		queriesBox.addItem(MainPageController.QUERY_EMPLOYEES_ON_POINT);
		queriesBox.addItem(MainPageController.QUERY_TIMESHEETS_ON_EMPLOYEE);
		queriesBox.addItem(MainPageController.QUERY_TIMESHEETS_ON_DATE);

		executeQueryButton = new JButton("Execute");
		executeQueryButton.setActionCommand(MainPageController.ACTION_EXECUTE);
		executeQueryButton.addActionListener(controller);

		c.add(queriesBox);
		c.add(executeQueryButton);
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		c.add(new JScrollPane(common));

		tablePane.add("Employees", e);
		tablePane.add("Points", p);
		tablePane.add("Timesheets", t);
		tablePane.add("Common", c);
		this.add(tablePane);
		
		if (!mode){
			administrationRecomendations.setEnabled(false);
			addButton.setEnabled(false);
			addMenu.setEnabled(false);
			tablePane.remove(3);
		}

	}

//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				new MainPage();
//			}
//		});
//	}

}
