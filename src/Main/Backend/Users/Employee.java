package Main.Backend.Users;

public class Employee extends User{
	
	private String SSN;
	private double salary;
	private Access permission;
	
	public Employee(String name, String surname, String username, String password, String email, String phone, int day, int month, int year, Status status, String SSN, double salary, Access permission) throws Exception{
		
		super(name, surname, username, password, email, phone, day, month, year, status);
		this.SSN = SSN;
		this.salary = salary;
		this.permission = permission;
		
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public Access getPermission() {
		return permission;
	}
	
	public void setPermission(Access permission) {
		this.permission = permission;
	}
}
