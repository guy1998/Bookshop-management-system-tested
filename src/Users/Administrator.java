package Users;

import Exceptions.InvalidPasswordException;
import Exceptions.InvalidUsernameException;
import Exceptions.UserAlreadyExistsException;

public class Administrator extends User {


	public Administrator(String name, String surname, String username, String password, String email, String phone, int day, int month, int year) throws Exception {

		super(name, surname, username, password, email, phone, day, month, year, Status.ADMINISTRATOR);

	}

	public User createUser(String name, String surname, String username, String password, String email, String phone, int day, int month, int year, String SSN, double salary, Access permission, String userType) throws Exception {

		if (userType.equals("LIBRARIAN")) {
			return new Librarian(name, surname, username, password, email, phone, day, month, year, SSN, salary, permission);
		} else {
			return new Manager(name, surname, username, password, email, phone, day, month, year, SSN, salary, permission);
		}

	}
}


