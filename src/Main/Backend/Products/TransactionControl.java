package Main.Backend.Products;

import java.io.Serializable;
import java.util.ArrayList;

public class TransactionControl implements Serializable{
	private ArrayList<Transaction> transactions;
	private final TransactionWriter writer;
	
	public TransactionControl() {
		writer = new TransactionWriter("Database/Transaction.dat");
		readTransactions();
	}
	
	public void writeTransactions() {
		writer.writeTransactions(transactions);
	}
	
	public void readTransactions() {
		transactions = writer.readTransactions();
	}
	
	public ArrayList<Transaction> getTransactions(){
		return transactions;
	}
	
	
}
