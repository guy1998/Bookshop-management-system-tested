package Users;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Products.Bill;
import Products.Book;
import Products.BookStock;
import Utilis.CompDate;

public class Librarian extends Employee{
	
	
	private final File billFolder;
	private final File billDat;
	private ArrayList<Bill> bills;
	
public Librarian(String name, String surname, String username, String password, String email, String phone, int day, int month, int year, String SSN, double salary, Access permission) throws Exception{
		
		super(name, surname, username, password, email, phone, day, month, year, Status.LIBRARIAN, SSN, salary, permission);
		billDat = new File( this.getUserId() + ".dat");
		bills = new ArrayList<>();
		if(!billDat.exists())
			writeBills();
		else
			bills = readBills();
		billFolder = new File(this.getUserId());
		if(!billFolder.exists())
			System.out.println(billFolder.mkdir());
		
	}	

public void writeBills() {
	try {
		FileOutputStream out = new FileOutputStream(billDat);
		ObjectOutputStream outOb = new ObjectOutputStream(out);
		outOb.writeObject(bills);
		outOb.close();
		out.close();
	}catch (FileNotFoundException e) {
		System.err.println("File not Found!!!");
	} catch (IOException e) {
		System.err.println(e.getMessage());
	}
}

public ArrayList<Bill> readBills() {
	try {
		FileInputStream in = new FileInputStream(billDat);
		ObjectInputStream inOb = new ObjectInputStream(in);
		bills = (ArrayList<Bill>) inOb.readObject();
		in.close();
		inOb.close();
	}catch (FileNotFoundException e) {
		System.err.println("File not Found!!!");
	} catch (ClassNotFoundException e) {
		System.err.println("Class not Found!!!");
	} catch (IOException e) {
		System.err.println(e.getMessage());
	}
	
	return bills;
}

//To be tested
	public int nrOfBills() {
		int cnt = 0;
		bills = readBills();
		for(Bill bill : bills) {
			cnt++;
		}
		
		return cnt;
	}

	//To be tested
	public int nrOfBills(CompDate specificDate) {
		
		int cnt = 0;
		bills = readBills();
		for(Bill bill : bills) {
			if(bill.getDateIssued().compareTo(specificDate) == 0)
				cnt++;
		}
		
		return cnt;
	}
	
	//To be tested
	public int nrOfBills(CompDate start, CompDate end) {
		
		int cnt = 0;
		bills = readBills();
		for(Bill bill : bills) {
			if(bill.getDateIssued().compareTo(start) >= 0 && bill.getDateIssued().compareTo(end) <= 0)
				cnt++;
		}
		
		return cnt;
	}

	//To be tested
	public int nrOfBooks(CompDate specificDate) {
		int cnt = 0;
		bills = readBills();
		for(Bill bill : bills) {
			if(bill.getDateIssued().compareTo(specificDate) == 0)
				for(Book x:bill.getBooksSold()) {
					cnt += x.getNumber();
				}
					
		}
		
		return cnt;
	}
	
	//To be tested
	public int nrOfBooks(CompDate start, CompDate end) {
		
		int cnt = 0;
		bills = readBills();
		for(Bill bill : bills) {
			if(bill.getDateIssued().compareTo(start) >= 0 && bill.getDateIssued().compareTo(end) <= 0)
				for(Book x:bill.getBooksSold())
					cnt += x.getNumber();
		}
		
		return cnt;
	}

	//To be tested
	public double moneyMade(CompDate specificDate) {
		double amount = 0;
		
		bills = readBills();
		for(Bill bill : bills) {
			if(bill.getDateIssued().compareTo(specificDate) == 0)
				for(Book x:bill.getBooksSold()) {
					amount += x.getNumber() * x.getSellingPrice();
				}
					
		}
		
		return amount;
	}

	//To be tested
	public double moneyMade(CompDate start, CompDate end) {
		
		double amount = 0;
		
		bills = readBills();
		for(Bill bill : bills) {
			if(bill.getDateIssued().compareTo(start) >= 0 && bill.getDateIssued().compareTo(end) <= 0)
				for(Book x:bill.getBooksSold())
					amount += x.getNumber() * x.getSellingPrice();
		}
		
		return amount;
	}

	//To be tested
	public double createBill(ArrayList<Book> books) throws Exception{
		
		bills = readBills();
		Bill newBill = new Bill(books);
		newBill.printFormat(billFolder + "/[Bill" + this.nrOfBills() + "].txt");
		bills.add(newBill);
		writeBills();
		
		return newBill.getTotalAmount();
	}
			
}
