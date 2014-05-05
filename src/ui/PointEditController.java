package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.Database;
import entities.Point;

public class PointEditController implements ActionListener {
	
	private PointEditPage page;

	private Point point;

	public PointEditController(PointEditPage page) {

		this.page = page;

		try {
			point = (Point)Database.getPoints().getRow(page.getPointID());
		} catch (NumberFormatException e) {
			System.out.println("Error: something with numbers...");
		} catch (SQLException e) {
			System.out.println("Error: unable to read point");
		}
	}

	public void fillAll() {
		page.getPointIDField().setText(point.getPointID());
		page.getSalaryTextField().setText(point.getSalary());
		page.getPostTextField().setText(point.getPost());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			Point newPoint = new Point(
					page.getPointIDField().getText(),
					page.getSalaryTextField().getText(),
					page.getPostTextField().getText());
			try {
				Database.getPoints().editRow(point.getPointID(),
						newPoint);
				MainPage m = page.getMainPage();
				MainPageController c = m.getController();
				try {
					c.updateTables();
				} catch (Exception ex) {
				} 							// I don't know where exception occurs!11
				page.dispose();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(page,
						"Sorry, there's something wrong.");
			} 

		} else if (e.getActionCommand() == "Close") {
			if (page.getMode()) {
				MyTableModel m = new MyTableModel(Database.getPoints().getAll());
				Integer index = m.getRowCount();
				String neededID = (String) m.getValueAt(index - 1, 0);
				try {
					Database.getPoints().deleteRow(neededID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			page.dispose();
			page.getMainPage().getController().updateTables();
		}
	}
}
