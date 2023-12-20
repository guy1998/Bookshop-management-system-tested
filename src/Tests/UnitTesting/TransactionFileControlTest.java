package Tests.UnitTesting;

import Main.Exceptions.InvalidBookInfo;
import Main.Products.Book;
import Main.Products.Transaction;
import Main.Products.TransactionWriter;
import Mocks.BookModelMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionFileControlTest {
    @TempDir
    static File tempFolder;
    static TransactionWriter writer;
    static HashMap<Integer, BookModelMock> groupOfBooks;
    static File tempFile;
    static ArrayList<Transaction> transactions;
    @BeforeAll
    static void setUp(){
        groupOfBooks =new HashMap<>();
        try {
            groupOfBooks.put(1,new BookModelMock("Book1",1));
            groupOfBooks.put(2,new BookModelMock("Book2",2));
            groupOfBooks.put(3,new BookModelMock("Book3",3));
            groupOfBooks.put(4,new BookModelMock("Book4",4));
        } catch (InvalidBookInfo e) {
            throw new RuntimeException(e);
        }
    }
    @BeforeEach
    void caseSetup(){
        transactions = new ArrayList<>();
        tempFile = new File(tempFolder, "TestBooks.dat");
        System.out.println(tempFolder.getPath());
        writer = new TransactionWriter(tempFolder.getPath()+"/TestBooks.dat");
    }
    @Test
    void readTransactionsTest1Transaction(){
        ArrayList<Book> books = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        transactions.add(new Transaction(books,true));
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            objectOutputStream.writeObject(transactions);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("They searched all of Mordor and they STILL didn't find the file");
        } catch (IOException e) {
            throw new RuntimeException("It worked on my PC, idk about why it doesn't work for you");
        }
        transactions = writer.readTransactions();
        assertEquals(1,transactions.size());
        assertEquals(5,transactions.get(0).getBooks().size());
        assertTrue(transactions.get(0).isOutgoing());
    }
    @Test
    void readTransactionsTest2Transaction(){
        ArrayList<Book> books = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        transactions.add(new Transaction(books,true));
        books = new ArrayList<>();
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(1));
        transactions.add(new Transaction(books,false));
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            objectOutputStream.writeObject(transactions);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("They searched all of Mordor and they STILL didn't find the file");
        } catch (IOException e) {
            throw new RuntimeException("It worked on my PC, idk about why it doesn't work for you");
        }
        transactions = writer.readTransactions();
        assertEquals(2,transactions.size());
        assertEquals(5,transactions.get(0).getBooks().size());
        assertTrue(transactions.get(0).isOutgoing());
        assertEquals(4,transactions.get(1).getBooks().size());
        assertFalse(transactions.get(1).isOutgoing());
    }
    @Test
    void readTransactionsTest0Transaction(){
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            objectOutputStream.writeObject(transactions);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("They searched all of Mordor and they STILL didn't find the file");
        } catch (IOException e) {
            throw new RuntimeException("It worked on my PC, idk about why it doesn't work for you");
        }
        transactions = writer.readTransactions();
        assertEquals(0,transactions.size());
    }
    @Test
    void writeTransactionsTest1Transaction(){
        ArrayList<Book> books = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        transactions.add(new Transaction(books,true));
        writer.writeTransactions(transactions);
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(tempFile))){
            transactions = (ArrayList<Transaction>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("They searched all of Mordor and they STILL didn't find the file");
        } catch (IOException e) {
            throw new RuntimeException("It worked on my PC, idk about why it doesn't work for you");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("It's probably a cluster since I can't find this class");
        }
        assertEquals(1,transactions.size());
        assertEquals(5,transactions.get(0).getBooks().size());
        assertTrue(transactions.get(0).isOutgoing());
    }
    @Test
    void writeTransactionsTest2Transaction(){
        ArrayList<Book> books = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        transactions.add(new Transaction(books,true));
        books = new ArrayList<>();
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(1));
        transactions.add(new Transaction(books,false));
        writer.writeTransactions(transactions);
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(tempFile))){
            transactions = (ArrayList<Transaction>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("They searched all of Mordor and they STILL didn't find the file");
        } catch (IOException e) {
            throw new RuntimeException("It worked on my PC, idk about why it doesn't work for you");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("It's probably a cluster since I can't find this class");
        }
        assertEquals(2,transactions.size());
        assertEquals(5,transactions.get(0).getBooks().size());
        assertTrue(transactions.get(0).isOutgoing());
        assertEquals(4,transactions.get(1).getBooks().size());
        assertFalse(transactions.get(1).isOutgoing());
    }
    @Test
    void writeTransactionsTest0Transaction(){
        writer.writeTransactions(transactions);
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(tempFile))){
            transactions = (ArrayList<Transaction>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("They searched all of Mordor and they STILL didn't find the file");
        } catch (IOException e) {
            throw new RuntimeException("It worked on my PC, idk about why it doesn't work for you");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("It's probably a cluster since I can't find this class");
        }
        assertEquals(0,transactions.size());
    }
}
