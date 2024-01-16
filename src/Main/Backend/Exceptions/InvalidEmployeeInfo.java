package Main.Backend.Exceptions;

public class InvalidEmployeeInfo extends Exception{

	private String message;
	
	public InvalidEmployeeInfo() {
		this("You have entered some invalid information for your employee. Please chck again!");
	}
	
	public InvalidEmployeeInfo(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
