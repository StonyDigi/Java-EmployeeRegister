package hu.webler.model;

public class EmployeeCategory {

	private int id;
	private String name;
	private boolean status;
	private boolean deleted;
	
	public EmployeeCategory(int id, String name, boolean status, boolean deleted) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.deleted = deleted;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isStatus() {
		return status;
	}

	public boolean isDeleted() {
		return deleted;
	}
	
	public String toString() {
		return "id: "+this.id +", "+this.name + ", "+this.status + ", "+this.deleted;
	}
}
