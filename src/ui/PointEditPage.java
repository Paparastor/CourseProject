package ui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PointEditPage extends JFrame {

	private PointEditController controller;

	private final static int WIDTH = 250;
	private final static int HEIGHT = 250;
	private final static int FIELD_SIZE = 10;

	public static final boolean MODE_EDIT = false;
	public static final boolean MODE_ADD = true;

	private boolean mode;

	private static final long serialVersionUID = 1L;

	private String pointID;
	private MainPage mainPage;

	private JTextField pointIDField;
	private JTextField salaryField;
	private JTextField postField;

	public boolean getMode() {
		return mode;
	}

	public MainPage getMainPage() {
		return mainPage;
	}

	public String getPointID() {
		return pointID;
	}

	public JTextField getPointIDField() {
		return pointIDField;
	}

	public JTextField getSalaryTextField() {
		return salaryField;
	}

	public JTextField getPostTextField() {
		return postField;
	}

	public PointEditPage(String pointID, MainPage mainPage, boolean mode) {

		this.pointID = pointID;
		this.mainPage = mainPage;
		this.mode = mode;

		// Setting the controller
		controller = new PointEditController(this);

		// Setting parameters of the frame
		this.setName("PointEditPage");
		this.setTitle("Edit Point");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// Point ID field
		JPanel pointIDPanel = new JPanel();
		pointIDPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pointIDPanel.add(new JLabel("Point ID:"));
		pointIDField = new JTextField(FIELD_SIZE);
		pointIDPanel.add(pointIDField);
		this.add(pointIDPanel);

		// Salary field
		JPanel salaryPanel = new JPanel();
		salaryPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		salaryPanel.add(new JLabel("Salary:"));
		salaryField = new JTextField(FIELD_SIZE);
		salaryPanel.add(salaryField);
		this.add(salaryPanel);

		// Post field
		JPanel postPanel = new JPanel();
		postPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		postPanel.add(new JLabel("Post:"));
		postField = new JTextField(FIELD_SIZE);
		postPanel.add(postField);
		this.add(postPanel);

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
