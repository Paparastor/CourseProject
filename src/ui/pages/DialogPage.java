package ui.pages;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ui.controllers.DialogPageController;

public class DialogPage extends JFrame {

	private DialogPageController controller;

	private final static int WIDTH = 250;
	private final static int HEIGHT = 170;
	private final static int FIELD_SIZE = 10;

	private static final long serialVersionUID = 1L;

	private JTextField parameterField;
	private JList<String> list;

	private MainPage mainPage;
	
	public JList<String> getList(){
		return list;
	}

	public DialogPageController getController() {
		return controller;
	}

	public JTextField getParameterTextField() {
		return parameterField;
	}

	public MainPage getMainPage() {
		return mainPage;
	}

	public DialogPage(MainPage mainPage, String parameterName) {

		this.mainPage = mainPage;

		// Setting the controller
		controller = new DialogPageController(this, parameterName);

		// Setting parameters of the frame
		this.setName("DialogPage");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// Search parameter field
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		searchPanel.add(new JLabel(parameterName + ":"));
		parameterField = new JTextField(FIELD_SIZE);
		
		searchPanel.add(parameterField);
		this.add(searchPanel);

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
