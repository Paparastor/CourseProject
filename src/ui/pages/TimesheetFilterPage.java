package ui.pages;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.controllers.EmployeeFilterPageController;
import ui.controllers.TimesheetFilterPageController;

public class TimesheetFilterPage extends JFrame {

	private TimesheetFilterPageController controller;

	private final static int WIDTH = 250;
	private final static int HEIGHT = 250;
	private final static int FIELD_SIZE = 10;

	private static final long serialVersionUID = 1L;

	private JTextField minimalDateTextField;
	private JTextField maximalDateTextField;
	private JComboBox<String> professionBox;

	private MainPage mainPage;

	public TimesheetFilterPageController getController() {
		return controller;
	}

	public MainPage getMainPage() {
		return mainPage;
	}

	public JTextField getMinimalSalaryTextField() {
		return minimalDateTextField;
	}

	public JTextField getMaximalSalaryTextField() {
		return maximalDateTextField;
	}

	public JComboBox<String> getPostBox() {
		return professionBox;
	}

	public TimesheetFilterPage(MainPage mainPage) {

		this.mainPage = mainPage;

		// Setting parameters of the frame
		this.setName("EmployeeEditPage");
		this.setSize(WIDTH, HEIGHT);
		// this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// Search parameter field
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		panel.add(new JLabel("Minimal Plan:"));
		minimalDateTextField = new JTextField(FIELD_SIZE);
		panel.add(minimalDateTextField);

		panel.add(new JLabel("Maximal Plan:"));
		maximalDateTextField = new JTextField(FIELD_SIZE);
		panel.add(maximalDateTextField);

		// Setting the controller
		controller = new TimesheetFilterPageController(this);

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

		this.add(panel);
		this.add(buttonPanel);

	}
}
