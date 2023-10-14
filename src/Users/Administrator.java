package Users;

import Exceptions.InvalidPasswordException;
import Exceptions.InvalidUsernameException;
import Exceptions.UserAlreadyExistsException;

public class Administrator extends User{

	
	public Administrator(String name, String surname, String username, String password, String email, String phone, int day, int month, int year) throws Exception{
		
		super(name, surname, username, password, email, phone, day, month, year, Status.ADMINISTRATOR);
		
	}
	
	//Work underway
	public User createUser(String name, String surname, String username, String password, String email, String phone, int day, int month, int year, String SSN, double salary, Access permission, String userType) throws Exception{
		
		if(userType.equals("LIBRARIAN")){
			Librarian temp = new Librarian(name, surname, username, password, email, phone, day, month ,year, SSN, salary, permission);
			return temp;
		}
		else {
			Manager temp = new Manager(name, surname, username, password, email, phone, day, month ,year, SSN, salary, permission);
			return temp;
		}
		
	}
	
//	public void setPermission(Employee e, String permission) {
//		if(permission.equals("Full"))
//			e.setPermission(Access.FULL);
//		else if(permission.equals("Partial"))
//			e.setPermission(Access.PARTIAL);
//		else
//			e.setPermission(Access.NONE);
//		users.writeUsers();
//	}
//	
//	public void modifyName(Employee e, String newName) {
//		e.setName(newName);
//		users.writeUsers();
//	}
//
//	public void modifySurname(Employee e, String newSurname) {
//		e.setSurname(newSurname);
//		users.writeUsers();
//	}
//	
//	public void modifyBirthday(Employee e, int day, int month, int year) {
//		e.setBirthday(day, month, year);
//		users.writeUsers();
//	}
//	
//	public void modifySSN(Employee e, String SSN) {
//		e.setSSN(SSN);
//		users.writeUsers();
//	}
//	
//	public void modifySalary(Employee e, double salary) {
//		e.setSalary(salary);
//		users.writeUsers();
//	}
	
	//TO DO
	//function to get the total income
	//function to get the performance of employees
}


