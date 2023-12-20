package Main.Products;
import java.io.Serializable;
import java.util.ArrayList;

public class Author implements Serializable{
	
	private String name;
	private String middleName;
	private String surname;
	private ArrayList<Book> books = new ArrayList<>();

	public Author(String name, String surname)
	{
		this.name = name;
		this.surname = surname;
		this.middleName = "";
	}

	public Author(String name, String middleName, String surname)
	{
		this.name = name;
		this.middleName = middleName;
		this.surname = surname;
	}

	public void addBook(Book book)
	{
		books.add(book);
	}


	@Override
	public String toString(){

		if(!middleName.equals(""))
			return this.name + " " + this.middleName + " " + this.surname;

		return this.name + " " + this.surname;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Author && this.name.equals(((Author)o).getName()) && this.surname.equals(((Author)o).getSurname()) && this.middleName.equals(((Author)o).getMiddleName()))
			return true;
		
		return false;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getNumberOfBooks() {
		return books.size();
	}

}