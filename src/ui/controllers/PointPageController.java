package ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ui.MyTableModel;
import ui.pages.MainPage;
import ui.pages.PointPage;

import database.Database;
import database.tables.Points;
import entities.Point;

public class PointPageController implements ActionListener {

	private PointPage page;

	private Point point;

	public PointPageController(PointPage page) {

		this.page = page;

		if (page.getMode() != MainPageController.MODE_FILTER) {
			try {
				point = (Point) Database.getPoints().getRow(page.getPointID());
			} catch (NumberFormatException e) {
				System.out.println("Error: something with numbers...");
			} catch (SQLException e) {
				System.out.println("Error: unable to read point");
			}
		} else
			point = new Point("", "", "");
	}

	public void fillAll() {
		page.getPointIDField().setText(point.getPointID());
		page.getSalaryTextField().setText(point.getSalary());
		page.getPostTextField().setText(point.getPost());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			Point newPoint = new Point(page.getPointIDField().getText(), page
					.getSalaryTextField().getText(), page.getPostTextField()
					.getText());
			if (page.getMode() == MainPageController.MODE_FILTER) {
				page.getMainPage()
						.getPoints()
						.setModel(
								new MyTableModel(Points
										.getFormatted(newPoint)));
				page.dispose();
			} else {
				try {
					Database.getPoints().editRow(point.getPointID(), newPoint);
					MainPage m = page.getMainPage();
					MainPageController c = m.getController();
					try {
						c.updateTables();
					} catch (Exception ex) {
					} // I don't know where exception occurs!11
					page.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(page,
							"Sorry, there's something wrong.");
				}
			}

		} else if (e.getActionCommand() == "Close") {
			if (page.getMode() == MainPageController.MODE_ADD) {
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
