package Main.Products;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class TransactionControl implements Serializable{

	private ArrayList<Transaction> transactions;
	private final File tFile;
	
	public TransactionControl() {
		tFile = new File("Transactions.dat");
		transactions = new ArrayList<>();
		if(!tFile.exists())
			writeTransactions();
		else
			readTransactions();
	}
	
	public void writeTransactions() {
		
		try {
			FileOutputStream out = new FileOutputStream(tFile);
			ObjectOutputStream obOut = new ObjectOutputStream(out);
			obOut.writeObject(transactions);
			obOut.close();
			out.close();
		}catch(FileNotFoundException e) {
			System.out.println("Nuk ekziston file");
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void readTransactions() {
		
		try {
			FileInputStream in = new FileInputStream(tFile);
			ObjectInputStream obIn = new ObjectInputStream(in);
			transactions = (ArrayList<Transaction>)obIn.readObject();
			obIn.close();
			in.close();
		}catch (FileNotFoundException e) {
			System.err.println("File not Found!!!");
		} catch (ClassNotFoundException e) {
			System.err.println("Class not Found!!!");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	public ArrayList<Transaction> getTransactions(){
		return transactions;
	}
	
	
}
