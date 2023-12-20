package Main.Exceptions;

public class InvalidEmail extends Exception{

	public InvalidEmail() {
		
	}
	
	@Override
	public String getMessage() {
		return "Email is invalid";
	}
	
}
