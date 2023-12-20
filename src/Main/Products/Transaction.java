package Main.Products;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import Main.Utilis.CompDate;

public class Transaction implements Serializable{

	private ArrayList<Book> books;
	private CompDate transactionDate;
	private boolean outgoing;
	
	public Transaction(ArrayList<Book> books, boolean outgoing) {
		this.books = books;
		this.outgoing = outgoing;
		transactionDate = new CompDate(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
	}
	
	public Transaction(Book book, boolean outgoing) {
		this.books = new ArrayList<Book>();
		this.books.add(book);
		this.outgoing = outgoing;
		transactionDate = new CompDate(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
	}

	public ArrayList<Book> getBooks() {
		return books;
	}

	public CompDate getTransactionDate() {
		return transactionDate;
	}

	public boolean isOutgoing() {
		return outgoing;
	}
	
}
