package Main.Users;

import Main.Products.Bill;
import Main.Products.Book;
import Main.Utilis.CompDate;

import java.io.*;
import java.util.ArrayList;

public class Librarian extends Employee{
	

	private final File billFolder;
	protected ArrayList<Bill> bills;
	protected BillWriter writer;
	
public Librarian(String name, String surname, String username, String password, String email, String phone, int day, int month, int year, String SSN, double salary, Access permission) throws Exception{
		super(name, surname, username, password, email, phone, day, month, year, Status.LIBRARIAN, SSN, salary, permission);
		writer = new BillWriter("../Database/Bills/BinaryBill/" +this.getUserId() + ".dat");
		bills = new ArrayList<>();
		billFolder = new File("../../../Database/Bills/TextBill/" + this.getUserId());
		if(!billFolder.exists())
			System.out.println(billFolder.mkdirs());
	}	

public void writeBills() {
	writer.writeBills(bills);
}

public ArrayList<Bill> readBills() {
	bills = writer.readBills();
	return bills;
}

	public int nrOfBills() {
		int cnt = 0;
		bills = readBills();
		for(Bill bill : bills) {
			cnt++;
		}

		return cnt;
	}

	public int nrOfBills(CompDate specificDate) {
		
		int cnt = 0;
		bills = readBills();
		for(Bill bill : bills) {
			if(bill.getDateIssued().compareTo(specificDate) == 0)
				cnt++;
		}
		
		return cnt;
	}

	public int nrOfBills(CompDate start, CompDate end) {
		
		int cnt = 0;
		bills = readBills();
		for(Bill bill : bills) {
			if(bill.getDateIssued().compareTo(start) >= 0 && bill.getDateIssued().compareTo(end) <= 0)
				cnt++;
		}
		
		return cnt;
	}

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

	public double moneyMade(CompDate start, CompDate end) {
		
		double amount = 0;
		
		bills = readBills();
		for(Bill bill : bills) {
			if(bill.getDateIssued().compareTo(start) >= 0 && bill.getDateIssued().compareTo(end) <= 0)
				for (Book x : bill.getBooksSold())
					amount += x.getNumber() * x.getSellingPrice();
		}
		
		return amount;
	}

	public double createBill(ArrayList<Book> books) throws Exception{
		
		bills = readBills();
		Bill newBill = new Bill(books);
		newBill.printFormat(billFolder + "/[Bill" + this.nrOfBills() + "].txt");
		bills.add(newBill);
		writeBills();
		
		return newBill.getTotalAmount();
	}
			
}
