package Products;

import Utilis.CompDate;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Bill implements Serializable{

	private double totalAmount = 0;
	protected CompDate dateIssued;
	private ArrayList<Book> booksSold;

	public Bill(ArrayList<Book> booksSold) {
		
		this.booksSold = booksSold;
		Calendar cal = Calendar.getInstance();
		this.dateIssued = new CompDate(cal.get(Calendar.DAY_OF_MONTH), (cal.get(Calendar.MONTH) + 1), cal.get(Calendar.YEAR));
		for(Book x:booksSold)
			totalAmount += x.getSellingPrice() * x.getNumber();
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public CompDate getDateIssued() {
		return dateIssued;
	}

	public ArrayList<Book> getBooksSold() {
		return booksSold;
	}

	public void printFormat(String filePath) throws FileNotFoundException {
		
		PrintWriter bill = new PrintWriter(filePath);
		bill.println("\t\t\tFederal Ministry of Finance\t\t\t");
		bill.println();
		bill.println();
		bill.println("\t----------------------------------------------------\t");
		
		for(Book x : booksSold)
			bill.println(x.toString() + "\t\tPrice: +" + x.getSellingPrice() + "$ X " + x.getNumber() + "\n");
		
		bill.println("Taxes: \t\t\t\t\t\t\t\t\tx10%");
		bill.printf("Total: \t\t\t\t\t\t\t\t\t%.2f$\n\n", totalAmount);
		bill.println("Bill issue time: " + (new Date()).toString());
		
		bill.close();
	}
	
	
	
}
