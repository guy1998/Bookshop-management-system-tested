package Main.Exceptions;

public class InvalidPhoneNumberException extends Exception{

	public InvalidPhoneNumberException() {
		
	}
	
	@Override
	public String getMessage() {
		return "Phone number should be of format +3556[7-8-9]xxxxxxx";
	}
	
}
