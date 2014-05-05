package entities;

public class Timesheet extends Entity {
	
	private String timesheetID;
	private String employeeID;
	private String date;
	private String planPercentage;
	
	public Timesheet(String timesheetID, String employeeID, String date,
			String planPercentage) {
		super();
		this.timesheetID = timesheetID;
		this.employeeID = employeeID;
		this.date = date;
		this.planPercentage = planPercentage;
	}
	
	public Timesheet(){
		this("NULL","0","empty","0");
	}

	public String getTimesheetID() {
		return timesheetID;
	}

	public void setTimesheetID(String timesheetID) {
		this.timesheetID = timesheetID;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPlanPercentage() {
		return planPercentage;
	}

	public void setPlanPercentage(String planPercentage) {
		this.planPercentage = planPercentage;
	}
	
}
