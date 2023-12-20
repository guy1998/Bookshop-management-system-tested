package Main.Exceptions;

public class BookExistsException extends Exception{

	@Override
	public String getMessage() {
		return "There exists a book with this ISBN";
	}
	
}
