package ui.pages;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import ui.MyTableModel;
import ui.controllers.EmployeeFilterPageController;
import ui.controllers.PointFilterPageController;
import database.Database;

public class EmployeeFilterPage extends JFrame {

	private EmployeeFilterPageController controller;

	private final static int WIDTH = 250;
	private final static int HEIGHT = 250;
	private final static int FIELD_SIZE = 10;

	private static final long serialVersionUID = 1L;

	private JTextField minimalPointIDTextField;
	private JTextField maximalPointIDTextField;
	private JComboBox<String> professionBox;

	private MainPage mainPage;

	public EmployeeFilterPageController getController() {
		return controller;
	}

	public MainPage getMainPage() {
		return mainPage;
	}

	public JTextField getMinimalSalaryTextField() {
		return minimalPointIDTextField;
	}

	public JTextField getMaximalSalaryTextField() {
		return maximalPointIDTextField;
	}

	public JComboBox<String> getPostBox() {
		return professionBox;
	}

	public EmployeeFilterPage(MainPage mainPage) {

		this.mainPage = mainPage;

		// Setting parameters of the frame
		this.setName("EmployeeEditPage");
		this.setSize(WIDTH, HEIGHT);
		// this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// Search parameter field
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		panel.add(new JLabel("Minimal Point ID:"));
		minimalPointIDTextField = new JTextField(FIELD_SIZE);
		panel.add(minimalPointIDTextField);

		panel.add(new JLabel("Maximal Point ID:"));
		maximalPointIDTextField = new JTextField(FIELD_SIZE);
		panel.add(maximalPointIDTextField);

		panel.add(new JLabel("Profession:"));
		professionBox = new JComboBox<String>();
		panel.add(professionBox);
		this.add(panel);

		// Setting the controller
		controller = new EmployeeFilterPageController(this);

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
