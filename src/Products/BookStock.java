package Products;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import Exceptions.BookExistsException;
import Exceptions.InvalidBookInfo;

public class BookStock implements Serializable{
	
	private ArrayList<Book> productList;
	private final File products;
	
	public BookStock() {
		products = new File("ProductList.dat");
		productList = new ArrayList<>();
		if(!products.exists())
			writeProducts();
		else 
			readProducts();
		
	}
	
	public void writeProducts() {
		try {
			FileOutputStream out = new FileOutputStream(products);
			ObjectOutputStream outOb = new ObjectOutputStream(out);
			outOb.writeObject(productList);
			outOb.close();
			out.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void readProducts() {
		try {
			FileInputStream in = new FileInputStream(products);
			ObjectInputStream inOb = new ObjectInputStream(in);
			productList = (ArrayList<Book>)inOb.readObject();
			setProperties();
			inOb.close();
			in.close();
		}catch (FileNotFoundException e) {
			System.err.println("File not Found!!!");
		} catch (ClassNotFoundException e) {
			System.err.println("Class not Found!!!");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void setProperties() {
		for(Book x: productList) {
			x.setAuthorProperty();
			x.setCategoryProperty();
			x.setIsbnProperty();
			x.setTitleProperty();
			x.setPriceProperty();
			x.setNumberProperty();
			x.setPurchase();
			x.setOriginal();
		}
	}
	
	public Book findBook(String title) {
		
		for(Book book:productList)
			if(book.getTitle().equalsIgnoreCase(title))
				return book;
		
		return null;
	}
	
	public Book findBook(Book b) {
		
		for(Book book:productList)
			if(book.equals(b))
				return book;
		
		return null;
	}
	
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
	
	public ArrayList<Book> runningLow(){
		
		ArrayList<Book> temp = new ArrayList<>();
		
		for(int i=0; i<productList.size(); i++) {
			
			if(productList.get(i).getNumber() < 5)
				temp.add(productList.get(i));
			
		}
		
		return temp;
	}
	
	public String getProductList() {
		String products = "";
		for(int i=0; i<productList.size(); i++)
			products += productList.get(i).toString() + "\n";
		
		return products;
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
		
		for(Book x : productList)
			if(x.equals(book)) {
				x.setTitle(title);
				x.setTitleProperty();
			}
		writeProducts();
	}
	
	public void modifyISBN(Book book, String ISBN) throws InvalidBookInfo{
		
		if(!ISBN.matches("\\d{3}-\\d{4}-\\d{3}"))
			throw new InvalidBookInfo("Book ISBN must be of the format xxx-xxxx-xxx");
		
		this.findBook(book).setISBN(ISBN);
		writeProducts();
	}
	
	public void modifyCategory(Book book, String categroy) {
		this.findBook(book).setCategory(categroy);
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
	
	public boolean categoryExists(String category) {
		
		File file = new File("Categories.txt");
		Scanner input = null;
		try {
			input = new Scanner(file);
		}catch(FileNotFoundException e) {
			System.out.println("Categories is not a file");
		}
		
		while(input.hasNext()) {
			if(input.next().equals(category))
				return true;
		}
		
		return false;
	}
	
	public void addCategory(String newCategory) {
		File file = new File("Categories.txt");
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file, true);
			out.write(("\n"+newCategory).getBytes(), 0, ("\n"+newCategory).length());
		}catch(Exception e) {
			System.out.println("Please dont happen");
		}
				
	}
			
}
