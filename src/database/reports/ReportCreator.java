package database.reports;

import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import database.Database;

import java.sql.ResultSet;
import java.util.Date;

import ui.MyTableModel;

import entities.*;

public class ReportCreator {

	private final static String FILE_EMPLOYEE_INFO = "Employee Info Report ";
	private final static String FILE_EMPLOYEES_TIMESHEETS = "Employee's Timesheets Report ";
	private final static String FILE_EMPLOYEES_FINANCES = "Employee's Finances Report ";

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 30,
			Font.BOLD);
	private static Font plainText = new Font(Font.FontFamily.TIMES_ROMAN, 20,
			Font.NORMAL);

	private static PdfPTable getPdfTable(String query) {
		ResultSet t = Database.getResultSet(query);
		MyTableModel model = new MyTableModel(t);
		PdfPTable table = new PdfPTable(model.getColumnCount());
		for (int i = 0; i < model.getColumnCount(); i++) {
			table.addCell(model.getColumnName(i));
		}
		for (int i = 0; i < model.getRowCount(); i++) {
			for (int j = 0; j < model.getColumnCount(); j++) {
				table.addCell((String) model.getValueAt(i, j));
			}
		}
		return table;
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	// Employee info
	public static void getEmployeeInfo(Employee employee)
			throws DocumentException {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(
					FILE_EMPLOYEE_INFO + new Date().toString() + ".pdf"));
			document.open();

			Paragraph paragraph = new Paragraph();

			// Lets write a big header
			paragraph.add(new Paragraph("Report", catFont));
			addEmptyLine(paragraph, 4);
			paragraph.add(new Paragraph("Information about company's employee.",
					plainText));
			addEmptyLine(paragraph, 2);
			paragraph.add(new Paragraph("Name:   " + employee.getName(),
					plainText));
			paragraph.add(new Paragraph("Passport:   " + employee.getPassport(),
					plainText));
			paragraph.add(new Paragraph("Profession:   "
					+ employee.getProfession(), plainText));

			Point p = (Point) Database.getPoints()
					.getRow(employee.getPointID());

			paragraph.add(new Paragraph("Post:   " + p.getPost(), plainText));
			paragraph.add(new Paragraph("Salary:   " + p.getSalary(), plainText));
			addEmptyLine(paragraph, 3);
			paragraph.add(new Paragraph("Date: " + new Date().toString(), plainText));
			document.add(paragraph);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Info about work of certain employee
	public static void getTableDoc(Employee employee) throws DocumentException {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(FILE_EMPLOYEES_TIMESHEETS + new Date().toString() + ".pdf"));
			document.open();

			Paragraph paragraph = new Paragraph();
			
			paragraph.add(new Paragraph("Report", catFont));
			
			addEmptyLine(paragraph, 4);
			
			paragraph.add(new Paragraph("Timesheets of worker "
					+ employee.getName() + ":", plainText));
			
			addEmptyLine(paragraph, 2);

			String query = "select * from timesheets where timesheets.EMPLOYEE_ID="
					+ employee.getEmployeeID() + ";";
			PdfPTable table = getPdfTable(query);
			MyTableModel model = new MyTableModel(Database.getResultSet(query));
			int sum = 0;
			for (int i = 0; i < model.getRowCount(); i++) {

				sum += Integer.parseInt(model.getValueAt(i, 3).toString());
			}
			paragraph.add(table);
			
			addEmptyLine(paragraph, 1);
			
			paragraph.add(new Paragraph(
					"Average plan percentage for this worker: " + sum
							/ model.getRowCount() + ".", plainText));

			addEmptyLine(paragraph, 2);

			paragraph.add(new Paragraph("Date: " + new Date().toString(), plainText));
			document.add(paragraph);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getEmployeesFinances(Employee employee, String startDate, String endDate) throws DocumentException {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(FILE_EMPLOYEES_FINANCES + new Date().toString() + ".pdf"));
			document.open();

			Paragraph paragraph = new Paragraph();
			
			paragraph.add(new Paragraph("Report", catFont));
			
			addEmptyLine(paragraph, 4);
			
			paragraph.add(new Paragraph("Finances of worker "
					+ employee.getName() + " on period since "+ startDate + " untill " + endDate +": ", plainText));
			
			addEmptyLine(paragraph, 2);

			String query = "select * from timesheets where timesheets.EMPLOYEE_ID="
					+ employee.getEmployeeID() + ";";
			PdfPTable table = getPdfTable(query);
			MyTableModel model = new MyTableModel(Database.getResultSet(query));
			int sum = 0;
			for (int i = 0; i < model.getRowCount(); i++) {

				sum += Integer.parseInt(model.getValueAt(i, 3).toString());
			}
			paragraph.add(table);
			
			addEmptyLine(paragraph, 1);
			
			paragraph.add(new Paragraph(
					"Average plan percentage for this worker: " + sum
							/ model.getRowCount() + ".", plainText));

			addEmptyLine(paragraph, 2);

			paragraph.add(new Paragraph("Date: " + new Date().toString(), plainText));
			document.add(paragraph);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
