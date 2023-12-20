package Main.Products;

import java.io.Serializable;
import java.util.ArrayList;

public class TransactionControl implements Serializable{
	private ArrayList<Transaction> transactions;
	private final TransactionWriter writer;
	
	public TransactionControl() {
		writer = new TransactionWriter("Transaction.dat");
		transactions = new ArrayList<>();
	}
	
	public void writeTransactions() {
		writer.writeTransactions();
	}
	
	public void readTransactions() {
		transactions = writer.readTransactions();
	}
	
	public ArrayList<Transaction> getTransactions(){
		return transactions;
	}
	
	
}
