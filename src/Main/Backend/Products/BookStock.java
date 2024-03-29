package Main.Backend.Products;
import java.io.Serializable;
import java.util.ArrayList;

import Main.Backend.Exceptions.BookExistsException;
import Main.Backend.Exceptions.InvalidBookInfo;

public class BookStock implements Serializable{
	
	private ArrayList<Book> productList;
	public BookDb proxy;
	public BookStock(){
		proxy = new BookProxy("Database/products.dat");
		productList = proxy.readBooks();
	}
	public BookStock(BookDb proxy) {
		this.proxy = proxy;
		productList = proxy.readBooks();
	}
	
	public void writeProducts() {
		proxy.writeBooks(productList);
	}
	
	//To be tested
	public Book findBook(String title) {
		
		for(Book book:productList)
			if(book.getTitle().equalsIgnoreCase(title))
				return book;
		
		return null;
	}
	
	//To be tested
	public Book findBook(Book b) {
		
		for(Book book:productList)
			if(book.equals(b))
				return book;
		
		return null;
	}

	//To be tested
	public boolean empty() {
		
		for(Book b:productList)
			if(b.getNumber() != 0)
				return false;
		
		return true; 
	}
	
	public boolean exists(String ISBN) {
		for(Book b:productList) {
			if(b.getISBN().equals(ISBN))
				return true;
		}
		
		return false;
	}
	
	public void addBook(Book book) throws BookExistsException{
		
		if(!productList.contains(book)) {
			productList.add(book);
			productList.get(productList.indexOf(book)).addNumber(1);
			writeProducts();
		}else throw new BookExistsException();
			
	}
	
	//To be tested
	public ArrayList<Book> runningLow(){
		
		ArrayList<Book> temp = new ArrayList<>();
		
		for(int i=0; i<productList.size(); i++) {
			
			if(productList.get(i).getNumber() < 5)
				temp.add(productList.get(i));
			
		}
		
		return temp;
	}

	public ArrayList<Book> getProductList1(){
		return productList;
	}
	
	public Book delete(Book book) {
		Book temp =  productList.remove(productList.indexOf(book));
		writeProducts();
		return temp;
	}
	
	public void modifyTitle(Book book, String title) {
		this.findBook(book).setTitle(title);
		writeProducts();
	}
	
	public void modifyISBN(Book book, String ISBN) throws InvalidBookInfo{
		
		if(!ISBN.matches("\\d{3}-\\d{4}-\\d{3}"))
			throw new InvalidBookInfo("Book ISBN must be of the format xxx-xxxx-xxx");
		
		this.findBook(book).setISBN(ISBN);
		writeProducts();
	}
	
	public void modifyCategory(Book book, String category) {
		this.findBook(book).setCategory(category);
		writeProducts();
	}
	
	public void modifyOriginalPrice(Book book, double originalPrice) {
		this.findBook(book).setOriginalPrice(originalPrice);
		writeProducts();
	}
	
	public void modifyPurchasePrice(Book book, double purchasePrice) {
		this.findBook(book).setPurchasePrice(purchasePrice);
		writeProducts();
	}
	
	public void modifySellingPrice(Book book, double sellingPrice) {
		this.findBook(book).setSellingPrice(sellingPrice);
		writeProducts();
	}
	
	public void modifyAuthors(Book book, Author[] authors) {
		this.findBook(book).setAuthors(authors);
		writeProducts();
	}
	
	public void modifyQuantity(Book book, int Number) {
		this.findBook(book).addNumber(Number);
		writeProducts();
	}
	//To be tested
	public boolean categoryExists(String category) {
		CategoryWriter c_writer = new CategoryWriter("Database/Categories.txt");
		return c_writer.categoryExists(category);
	}
	
	public void addCategory(String newCategory) {
		CategoryWriter c_writer = new CategoryWriter("Database/Categories.txt");
		c_writer.addCategory(newCategory);
	}
			
}
