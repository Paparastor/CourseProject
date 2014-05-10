package ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import database.Database;

import ui.MyTableModel;
import ui.pages.DialogPage;

public class DialogPageController implements ActionListener {

	public final static String PARAMETER_POINT = "POINT_ID";
	public final static String PARAMETER_EMPLOYEE = "NAME";
	public final static String PARAMETER_DATE = "DATE";

	private DialogPage page;
	private String parameter;

	public DialogPageController(DialogPage page, String parameter) {
		this.page = page;
		this.parameter = parameter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			String query;
			if (parameter == PARAMETER_POINT) {
				query = "select points.POINT_ID,points.POST,employees.NAME, employees.PROFESSION from points,employees " +
						" where employees.POINT_ID=points.POINT_ID " +
						" and points."+parameter + "="+ page.getParameterTextField().getText() +
						" order by employees.PROFESSION" + ";";
				System.out.println(query);
				page.getMainPage()
						.getCommon()
						.setModel(
								new MyTableModel(
										Database.getResultSet(query)));
			}
			if (parameter == PARAMETER_EMPLOYEE) {
				query = "select employees.EMPLOYEE_ID,employees.NAME,timesheets.DATE,timesheets.PLAN_PERCENTAGE from employees,timesheets " +
						" where employees.EMPLOYEE_ID=timesheets.EMPLOYEE_ID " +
						" and employees."+parameter + "="+ "'"+page.getParameterTextField().getText() +"'"+ ";";
				System.out.println(query);
				page.getMainPage()
						.getCommon()
						.setModel(
								new MyTableModel(
										Database.getResultSet(query)));
			}
			if (parameter == PARAMETER_DATE) {
				query = "select * from timesheets " +
						" where timesheets."+parameter + "="+ "'"+page.getParameterTextField().getText() +"'"+ ";";
				System.out.println(query);
				page.getMainPage()
						.getCommon()
						.setModel(
								new MyTableModel(
										Database.getResultSet(query)));
			}
		}
		page.dispose();
	}
}
