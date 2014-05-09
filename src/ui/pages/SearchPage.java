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
import javax.swing.JTable;
import javax.swing.JTextField;

import ui.controllers.SearchPageController;

public class SearchPage extends JFrame {

	private SearchPageController controller;

	private final static int WIDTH = 250;
	private final static int HEIGHT = 170;
	private final static int FIELD_SIZE = 10;

	private static final long serialVersionUID = 1L;

	private JList<String> list;

	private JTextField searchTextField;

	private MainPage mainPage;

	public SearchPageController getController() {
		return controller;
	}

	public JList<String> getList() {
		return list;
	}

	public JTextField getSearchTextField() {
		return searchTextField;
	}

	public MainPage getMainPage() {
		return mainPage;
	}

	public SearchPage(MainPage mainPage, JTable table) {

		this.mainPage = mainPage;

		// Setting the controller
		controller = new SearchPageController(this, table);

		// Setting parameters of the frame
		this.setName("EmployeeEditPage");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// Search parameter field
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		searchPanel.add(new JLabel("Search field:"));
		searchTextField = new JTextField(FIELD_SIZE);
		searchTextField.getDocument().addDocumentListener(controller);
		searchPanel.add(searchTextField);
		this.add(searchPanel);

		// List
		list = new JList<String>(new DefaultListModel<String>());
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(200, 80));
		this.add(listScroller);

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
