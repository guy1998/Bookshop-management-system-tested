package Main.Exceptions;

public class InvalidUsernameException extends Exception{

	private String message = "This username is not valid";
	
	public InvalidUsernameException() {
		System.out.println(this.getMessage());
	}
	
	public InvalidUsernameException(String message) {
		this.message = message;
		System.out.println(this.getMessage());
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
