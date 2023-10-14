package Exceptions;

public class NonExistantUserException extends Exception{

	public NonExistantUserException() {
		System.out.println();
	}
	
	@Override 
	public String getMessage() {
		return "This user does not exist!";
	}
	
}
