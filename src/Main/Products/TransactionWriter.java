package Main.Products;

import java.io.*;
import java.util.ArrayList;

public class TransactionWriter {
    private final ArrayList<Transaction> transactions;
    private final File tFile;
    public TransactionWriter(String pathName) {
        tFile = new File(pathName);
        transactions = new ArrayList<>();
        if(!tFile.exists())
            writeTransactions();
        else
            readTransactions();
    }

    public void writeTransactions() {

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tFile))) {
            objectOutputStream.writeObject(transactions);
        }catch(FileNotFoundException e) {
            System.out.println("Nuk ekziston file");
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }

    }
    public ArrayList<Transaction> readTransactions(){

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(tFile))){
            return (ArrayList<Transaction>) objectInputStream.readObject();
        }catch (FileNotFoundException e) {
            System.err.println("File not Found!!!");
        } catch (ClassNotFoundException e) {
            System.err.println("Class not Found!!!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return new ArrayList<>();//idk it wouldnt work w/o it
    }
}
