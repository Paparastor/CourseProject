package database.reports;

import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ui.MyTableModel;

import entities.*;

public class ReportCreator {

	private final static String FOLDER_PATH = "reports/";
	
	private final static String FILE_EMPLOYEE_INFO = "Employee Info Report ";
	private final static String FILE_POINT_INFO = "Point Info Report ";
	private final static String FILE_TIMESHEET_INFO = "Timesheet Info Report ";

	private final static String FILE_EMPLOYEES_TIMESHEETS = "Employee's Timesheets Report ";
	private final static String FILE_EMPLOYEES_FINANCES = "Employee's Finances Report ";
	private final static String FILE_FACILITY_ADMINISTRATION = "Facility Administration Recomendations";

	private static Font headerText = new Font(Font.FontFamily.TIMES_ROMAN, 30,
			Font.BOLD);
	private static Font subHeaderText = new Font(Font.FontFamily.TIMES_ROMAN,
			25, Font.BOLDITALIC);

	private static Font plainText = new Font(Font.FontFamily.TIMES_ROMAN, 20,
			Font.NORMAL);
	private static Font plainUnderlinedText = new Font(
			Font.FontFamily.TIMES_ROMAN, 20, Font.UNDERLINE);
	private static Font plainBoldText = new Font(Font.FontFamily.TIMES_ROMAN,
			20, Font.BOLD);

	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 20,
			Font.NORMAL, BaseColor.RED);
	private static Font yellowFont = new Font(Font.FontFamily.TIMES_ROMAN, 20,
			Font.NORMAL, BaseColor.YELLOW);
	private static Font greenFont = new Font(Font.FontFamily.TIMES_ROMAN, 20,
			Font.NORMAL, BaseColor.GREEN);
	private static Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 20,
			Font.NORMAL, BaseColor.BLUE);

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
					FOLDER_PATH + FILE_EMPLOYEE_INFO + new Date().toString() + ".pdf"));
			document.open();

			Paragraph paragraph = new Paragraph();

			// Lets write a big header
			paragraph.add(new Paragraph("Report", headerText));
			addEmptyLine(paragraph, 4);
			paragraph.add(new Paragraph(
					"Information about company's employee.", plainText));
			addEmptyLine(paragraph, 2);
			paragraph.add(new Paragraph("Name:   " + employee.getName(),
					plainText));
			paragraph.add(new Paragraph(
					"Passport:   " + employee.getPassport(), plainText));
			paragraph.add(new Paragraph("Profession:   "
					+ employee.getProfession(), plainText));

			Point p = (Point) Database.getPoints()
					.getRow(employee.getPointID());

			paragraph.add(new Paragraph("Post:   " + p.getPost(), plainText));
			paragraph
					.add(new Paragraph("Salary:   " + p.getSalary(), plainText));
			addEmptyLine(paragraph, 3);
			paragraph.add(new Paragraph("Date: " + new Date().toString(),
					plainText));
			document.add(paragraph);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Point info
	public static void getPointInfo(Point point) throws DocumentException {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(
					FOLDER_PATH + FILE_POINT_INFO + new Date().toString() + ".pdf"));
			document.open();

			Paragraph paragraph = new Paragraph();

			paragraph.add(new Paragraph("Report", headerText));

			addEmptyLine(paragraph, 4);

			paragraph.add(new Paragraph(
					"Information about company's working point.", plainText));
			addEmptyLine(paragraph, 2);
			paragraph.add(new Paragraph("ID:   " + point.getPointID(),
					plainText));
			paragraph
					.add(new Paragraph("Post:   " + point.getPost(), plainText));
			paragraph.add(new Paragraph("Salary:   " + point.getSalary(),
					plainText));

			addEmptyLine(paragraph, 3);

			paragraph.add(new Paragraph("Date: " + new Date().toString(),
					plainText));

			document.add(paragraph);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Timesheet info
	public static void getTimesheetInfo(Timesheet timesheet)
			throws DocumentException {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(
					FOLDER_PATH + FILE_TIMESHEET_INFO + new Date().toString() + ".pdf"));
			document.open();

			Paragraph paragraph = new Paragraph();

			paragraph.add(new Paragraph("Report", headerText));

			addEmptyLine(paragraph, 4);

			paragraph.add(new Paragraph("Information about timesheet: ",
					plainText));

			addEmptyLine(paragraph, 2);

			paragraph.add(new Paragraph("ID:   " + timesheet.getTimesheetID(),
					plainText));
			paragraph.add(new Paragraph("Date:   " + timesheet.getDate(),
					plainText));
			Employee employee = (Employee) Database.getEmployees().getRow(
					timesheet.getEmployeeID());
			paragraph.add(new Paragraph("Employee:   " + employee.getName(),
					plainText));
			paragraph.add(new Paragraph("Plan percentage:   "
					+ timesheet.getPlanPercentage() + "%", plainText));

			addEmptyLine(paragraph, 3);

			paragraph.add(new Paragraph("Date: " + new Date().toString(),
					plainText));

			document.add(paragraph);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Info about work of certain employee
	public static void getEmployeesTimesheets(Employee employee)
			throws DocumentException {
		Document document = new Document();
		try {
			PdfWriter
					.getInstance(document, new FileOutputStream(
							FOLDER_PATH + FILE_EMPLOYEES_TIMESHEETS + new Date().toString()
									+ ".pdf"));
			document.open();

			Paragraph paragraph = new Paragraph();

			paragraph.add(new Paragraph("Report", headerText));

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

			paragraph.add(new Paragraph("Date: " + new Date().toString(),
					plainText));
			document.add(paragraph);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Certain employee's finances info
	public static void getEmployeesFinances(Employee employee)
			throws DocumentException {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(
					FOLDER_PATH + FILE_EMPLOYEES_FINANCES + new Date().toString() + ".pdf"));
			document.open();

			Paragraph paragraph = new Paragraph();

			paragraph.add(new Paragraph("Report", headerText));

			addEmptyLine(paragraph, 4);

			paragraph.add(new Paragraph("The finances of worker "
					+ employee.getName() + ": ", plainText));

			addEmptyLine(paragraph, 2);

			String query = "select distinct timesheets.TIMESHEET_ID,timesheets.DATE,timesheets.PLAN_PERCENTAGE,(select distinct SALARY from points where POINT_ID="
					+ employee.getPointID()
					+ ") as SALARY,"
					+ " timesheets.PLAN_PERCENTAGE * (select distinct SALARY from points where POINT_ID="
					+ employee.getPointID()
					+ ")/100 as 'Payment' "
					+ " from timesheets,points,employees "
					+ " where timesheets.EMPLOYEE_ID="
					+ employee.getEmployeeID()
					+ " group by timesheets.TIMESHEET_ID;";
			PdfPTable table = getPdfTable(query);
			MyTableModel model = new MyTableModel(Database.getResultSet(query));
			int sum = 0;
			for (int i = 0; i < model.getRowCount(); i++) {

				sum += Integer.parseInt(model.getValueAt(i, 4).toString());
			}
			paragraph.add(table);

			addEmptyLine(paragraph, 1);

			paragraph.add(new Paragraph("Total payment for this worker: " + sum
					+ ".", plainText));

			addEmptyLine(paragraph, 2);

			paragraph.add(new Paragraph("Date: " + new Date().toString(),
					plainText));
			document.add(paragraph);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Certain employee's recommendations
	public static Paragraph getEmployeesRecomendations(Employee employee)
			throws SQLException {
		Paragraph paragraph = new Paragraph();

		paragraph.add(new Paragraph("Employee " + employee.getName(),
				plainUnderlinedText));

		addEmptyLine(paragraph, 1);

		String query = "select * from timesheets where timesheets.EMPLOYEE_ID="
				+ employee.getEmployeeID() + ";";

		MyTableModel model = new MyTableModel(Database.getResultSet(query));

		int sum = 0;

		for (int i = 0; i < model.getRowCount(); i++) {
			sum += Integer.parseInt(model.getValueAt(i, 3).toString());
		}

		if (model.getRowCount() != 0) {
			sum /= model.getRowCount();
		}

		double factor = 1.0;

		if (sum <= 75) {
			paragraph
					.add(new Paragraph("Efficiency level: Very Low ", redFont));
			factor = 0.75;
		} else if (sum < 100) {
			paragraph.add(new Paragraph("Efficiency level: Low ", yellowFont));
			factor = 0.85;
		} else if (sum == 100) {
			paragraph
					.add(new Paragraph("Efficiency level: Normal ", greenFont));
		} else if (sum > 100) {
			paragraph.add(new Paragraph("Efficiency level: High ", blueFont));
			factor = 1.25;
		}

		paragraph.add(new Paragraph("Average employees efficiency: " + sum
				+ "%", plainText));

		addEmptyLine(paragraph, 1);

		Point point = (Point) Database.points.getRow(employee.getPointID());

		paragraph.add(new Paragraph("Current employee's salary : "
				+ Double.parseDouble(point.getSalary()), plainText));
		paragraph.add(new Paragraph("Recommended salary factor: " + factor,
				plainText));
		paragraph.add(new Paragraph(
				"Employee's salary with recommended factor: "
						+ (factor * Double.parseDouble(point.getSalary())),
				plainBoldText));

		addEmptyLine(paragraph, 10);

		return paragraph;
	}

	// Certain point's recommendations
	public static Paragraph getPointsRecomendations(Point point)
			throws SQLException {
		Paragraph paragraph = new Paragraph();

		paragraph.add(new Paragraph("Point " + point.getPost() + " (ID "
				+ point.getPointID() + ")", plainUnderlinedText));

		addEmptyLine(paragraph, 1);

		paragraph.add(new Paragraph("Point's salary: " + point.getSalary(),
				plainText));

		String query = "select * from employees where employees.POINT_ID="
				+ point.getPointID() + ";";

		MyTableModel model = new MyTableModel(Database.getResultSet(query));

		int sum = 0;

		for (int i = 0; i < model.getRowCount(); i++) {
			String empID = (String) model.getValueAt(i, 0);
			String q = "select * from timesheets where timesheets.EMPLOYEE_ID="
					+ empID + ";";

			MyTableModel m = new MyTableModel(Database.getResultSet(q));

			int s = 0;

			for (int j = 0; j < m.getRowCount(); j++) {
				s += Integer.parseInt(m.getValueAt(j, 3).toString());
			}
			if (m.getRowCount() != 0) {
				s /= m.getRowCount();
			}

			sum += s;
		}

		if (model.getRowCount() != 0) {
			sum /= model.getRowCount();
		}

		paragraph.add(new Paragraph("Employees count: " + model.getRowCount(),
				plainText));
		paragraph.add(new Paragraph("Average point's efficiency: " + sum + "%",
				plainText));
		if (sum <= 75) {
			paragraph.add(new Paragraph("Profitability: Very Low ", redFont));
		} else if (sum < 100) {
			paragraph.add(new Paragraph("Profitability: Low ", yellowFont));
		} else if (sum == 100) {
			paragraph.add(new Paragraph("Profitability: Normal ", greenFont));
		} else if (sum > 100) {
			paragraph.add(new Paragraph("Profitability: High ", blueFont));
		}

		addEmptyLine(paragraph, 10);

		return paragraph;
	}

	// Facility administration recomendations
	public static void generateAdministrationRecomendations()
			throws DocumentException {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(
					FOLDER_PATH + FILE_FACILITY_ADMINISTRATION + new Date().toString()
							+ ".pdf"));
			document.open();

			Paragraph paragraph = new Paragraph();

			paragraph.add(new Paragraph(
					"Facility administration recomendations", headerText));

			addEmptyLine(paragraph, 6);

			// Employees recomendations
			paragraph.add(new Paragraph("Employees recomendations:",
					subHeaderText));

			addEmptyLine(paragraph, 1);

			MyTableModel modelEmployees = new MyTableModel(
					Database.employees.getAll());
			for (int i = 0; i < modelEmployees.getRowCount(); i++) {
				Employee employee = (Employee) Database.employees
						.getRow((String) modelEmployees.getValueAt(i, 0));
				paragraph.add(getEmployeesRecomendations(employee));
			}

			addEmptyLine(paragraph, 8);

			// Points recomendations
			paragraph
					.add(new Paragraph("Points recomendations:", subHeaderText));

			addEmptyLine(paragraph, 1);

			MyTableModel modelPoints = new MyTableModel(
					Database.points.getAll());
			for (int i = 0; i < modelPoints.getRowCount(); i++) {
				Point point = (Point) Database.points
						.getRow((String) modelPoints.getValueAt(i, 0));
				paragraph.add(getPointsRecomendations(point));
			}

			addEmptyLine(paragraph, 15);

			// Points recomendations
			paragraph.add(new Paragraph("Summary", subHeaderText));

			addEmptyLine(paragraph, 1);

			MyTableModel employeesCountModel = new MyTableModel(
					Database.getResultSet("select count(*) from employees;"));
			paragraph.add(new Paragraph("Employees count: "
					+ employeesCountModel.getValueAt(0, 0), plainText));

			MyTableModel pointsCountModel = new MyTableModel(
					Database.getResultSet("select count(*) from points;"));
			paragraph.add(new Paragraph("Points count: "
					+ pointsCountModel.getValueAt(0, 0), plainText));

			MyTableModel mostEfficientEmployeeModel = new MyTableModel(
			 Database.getResultSet("select NAME from employees where EMPLOYEE_ID in "
			 +
			 "(select EMPLOYEE_ID from timesheets group by EMPLOYEE_ID" +
			 " having sum(PLAN_PERCENTAGE) in " +
			 "(select max(PLAN_PERCENTAGE) from " +
			 "(select sum(PLAN_PERCENTAGE) as PLAN_PERCENTAGE from timesheets group by TIMESHEET_ID)))"));
			
			addEmptyLine(paragraph, 1);
			
			paragraph.add(new Paragraph("Most efficient employees: ", plainUnderlinedText));
			
			for (int i=0 ; i<mostEfficientEmployeeModel.getRowCount();i++){
				paragraph.add(new Paragraph((String) mostEfficientEmployeeModel.getValueAt(i, 0), plainText));
			}

			addEmptyLine(paragraph, 1);

			MyTableModel generalEfficiencyModel = new MyTableModel(
					Database.getResultSet("select avg(PLAN_PERCENTAGE) from timesheets;"));
			paragraph
					.add(new Paragraph("General facility efficiency: "
							+ generalEfficiencyModel.getValueAt(0, 0) + "%",
							plainText));

			double efficiency = Double
					.parseDouble((String) generalEfficiencyModel.getValueAt(0,
							0));

			if (efficiency <= 75) {
				paragraph
						.add(new Paragraph("Profitability: Very Low ", redFont));
			} else if (efficiency < 100) {
				paragraph.add(new Paragraph("Profitability: Low ", yellowFont));
			} else if (efficiency == 100) {
				paragraph
						.add(new Paragraph("Profitability: Normal ", greenFont));
			} else if (efficiency > 100) {
				paragraph.add(new Paragraph("Profitability: High ", blueFont));
			}

			addEmptyLine(paragraph, 6);

			paragraph.add(new Paragraph("Date: " + new Date().toString(),
					plainText));

			document.add(paragraph);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
