package entities;

public class Point extends Entity {
	
	private String pointID;
	private String salary;
	private String post;
	
	public Point(String pointID, String salary, String post) {
		super();
		this.pointID = pointID;
		this.salary = salary;
		this.post = post;
	}
	
	public Point(){
		this("NULL", "0", "empty");
	}

	public String getPointID() {
		return pointID;
	}

	public void setPointID(String pointID) {
		this.pointID = pointID;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
		
}
