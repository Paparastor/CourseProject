package entities;

public class Employee implements IEntity {

	private String employeeID;
	private String passport;
	private String name;
	private String profession;
	private int pointID;
	
	public Employee(String employeeID, String passport, String name,
			String profession, int pointID) {
		super();
		this.employeeID = employeeID;
		this.passport = passport;
		this.name = name;
		this.profession = profession;
		this.pointID = pointID;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public int getPointID() {
		return pointID;
	}

	public void setPointID(int pointID) {
		this.pointID = pointID;
	}
	
}
