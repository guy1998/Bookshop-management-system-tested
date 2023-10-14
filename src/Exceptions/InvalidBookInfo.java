package Exceptions;

public class InvalidBookInfo extends Exception{

	String message = "Book information is invalid!";
	
	public InvalidBookInfo() {
		
	}
	
	public InvalidBookInfo(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
