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
import com.itextpdf.text.pdf.PdfWriter;

import database.Database;

import java.io.File;
import java.util.Date;

import entities.*;

public class ReportCreator {
	
	private final static String FILE_EMPLOYEE_INFO = "EmployeeReport.pdf";
	private final static String FILE_TABLE = "TableReport.pdf";
	
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 30,
			Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	private static Font plainText = new Font(Font.FontFamily.TIMES_ROMAN, 20,
			Font.NORMAL);

	public ReportCreator() {

	}

	public static void createReport(Document document) {
		try {
			PdfWriter.getInstance(document, new FileOutputStream(FILE));
			document.open();
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getEmployeeInfo(Employee employee)
			throws DocumentException {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(FILE_EMPLOYEE_INFO));
			document.open();

			Paragraph preface = new Paragraph();
			try {
				PdfWriter.getInstance(document, new FileOutputStream(FILE_EMPLOYEE_INFO));
				document.open();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Lets write a big header
			preface.add(new Paragraph("Report", catFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph("Information about company's employee.", plainText));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph("Name:   " + employee.getName(), plainText));
			preface.add(new Paragraph("Passport:   " + employee.getPassport(),
					plainText));
			preface.add(new Paragraph(
					"Profession:   " + employee.getProfession(), plainText));
			
			Point p = (Point)Database.getPoints().getRow(employee.getPointID());
			
			preface.add(new Paragraph(
					"Post:   " + p.getPost(), plainText));
			preface.add(new Paragraph(
					"Salary:   " + p.getSalary(), plainText));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(new Date().toString(),plainText));
			document.add(preface);
			File file = new File(FILE_EMPLOYEE_INFO);
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getTableDoc(Employee employee)
			throws DocumentException {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(FILE_TABLE));
			document.open();

			Paragraph preface = new Paragraph();
			try {
				PdfWriter.getInstance(document, new FileOutputStream(FILE_TABLE));
				document.open();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Lets write a big header
			preface.add(new Paragraph("Report", catFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph("Table test.", plainText));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			
			
			
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(" ", subFont));
			preface.add(new Paragraph(new Date().toString(),plainText));
			document.add(preface);
			File file = new File(FILE_TABLE);
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
