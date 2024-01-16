package Main.Backend.Users;

public class Manager extends Employee{

	
	public Manager(String name, String surname, String username, String password, String email, String phone, int day, int month, int year, String SSN, double salary, Access permission) throws Exception {
		
		super(name, surname, username, password, email, phone, day, month, year, Status.MANAGER, SSN, salary, permission);
		
	}
	
	
	
}
