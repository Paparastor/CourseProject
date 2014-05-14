package ui.pages;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import ui.controllers.PointFilterPageController;
import ui.controllers.SearchPageController;

public class PointFilterPage extends JFrame {

	private PointFilterPageController controller;

	private final static int WIDTH = 250;
	private final static int HEIGHT = 170;
	private final static int FIELD_SIZE = 10;

	private static final long serialVersionUID = 1L;

	private JTextField minimalSalaryTextField;
	private JTextField maximalSalaryTextField;
	private JComboBox<String> postBox;

	private MainPage mainPage;

	public PointFilterPageController getController() {
		return controller;
	}

	public MainPage getMainPage() {
		return mainPage;
	}

	public JTextField getMinimalSalaryTextField() {
		return minimalSalaryTextField;
	}

	public JTextField getMaximalSalaryTextField() {
		return maximalSalaryTextField;
	}

	public JComboBox<String> getPostBox() {
		return postBox;
	}

	public PointFilterPage(MainPage mainPage) {

		this.mainPage = mainPage;

		// Setting parameters of the frame
		this.setName("EmployeeEditPage");
		this.setSize(WIDTH, HEIGHT);
		// this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// Search parameter field
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		panel.add(new JLabel("Minimal salary:"));
		minimalSalaryTextField = new JTextField(FIELD_SIZE);
		panel.add(minimalSalaryTextField);

		panel.add(new JLabel("Maximal salary:"));
		maximalSalaryTextField = new JTextField(FIELD_SIZE);
		panel.add(maximalSalaryTextField);

		panel.add(new JLabel("Post:"));
		postBox = new JComboBox<String>();
		panel.add(postBox);
		this.add(panel);

		// Setting the controller
		controller = new PointFilterPageController(this);

		// Buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// OK button
		JButton ok = new JButton("OK");
		ok.addActionListener(controller);
		buttonPanel.add(ok);

		// Close button
		JButton close = new JButton("Close");
		close.addActionListener(controller);
		buttonPanel.add(close);

		this.add(buttonPanel);

	}
}
