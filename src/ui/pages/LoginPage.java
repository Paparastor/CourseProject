package ui.pages;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ui.controllers.SearchPageController;

public class LoginPage extends JFrame implements ActionListener {

	private final static int WIDTH = 250;
	private final static int HEIGHT = 100;
	private final static int FIELD_SIZE = 10;

	private static final long serialVersionUID = 1L;

	private JTextField searchTextField;

	private MainPage mainPage;


	public JTextField getSearchTextField() {
		return searchTextField;
	}

	public MainPage getMainPage() {
		return mainPage;
	}

	public LoginPage() {

		// Setting parameters of the frame
		this.setName("EmployeeEditPage");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setVisible(true);

		// OK button
		JButton ok = new JButton("Administrative Mode");
		ok.addActionListener(this);
		this.add(ok);

		// Close button
		JButton close = new JButton("Manager Mode");
		close.addActionListener(this);
		this.add(close);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand()=="Administrative Mode"){
			MainPage page = new MainPage(MainPage.MODE_ADMINISTRATIVE);
			page.setVisible(true);
			this.setVisible(false);
		}
		if (e.getActionCommand()=="Manager Mode"){
			MainPage page = new MainPage(MainPage.MODE_MANAGER);
			page.setVisible(true);
			this.setVisible(false);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginPage();
			}
		});
	}
}
