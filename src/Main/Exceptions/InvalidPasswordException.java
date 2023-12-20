package Main.Exceptions;

public class InvalidPasswordException extends Exception{

	String message = "The password entered is invalid";
	
	public InvalidPasswordException() {
		System.out.println(this.getMessage());
	}
	
	public InvalidPasswordException(String message) {
		this.message = message;
		System.out.println(this.getMessage());
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
}
