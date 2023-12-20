package Main.Exceptions;

public class UserAlreadyExistsException extends Exception{

	public UserAlreadyExistsException() {
		System.out.println(this.getMessage());
	}
	
	@Override
	public String getMessage() {
		return "It seems that this user exists in the system.";
	}
	
}
