package Tests.UnitTesting;

import Main.Exceptions.InvalidBookInfo;
import Main.Products.Bill;
import Main.Products.Book;
import Main.Users.BillWriter;
import Mocks.BookModelMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibrarianFileTest {
    @TempDir
    static File tempFolder;
    static BillWriter writer;
    static HashMap<Integer,BookModelMock> groupOfBooks;
    static File tempFile;
    @BeforeAll
    static void setUp(){
        groupOfBooks=new HashMap<>();
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
        tempFile = new File(tempFolder, "TestBooks.dat");
        System.out.println(tempFolder.getPath());
        writer = new BillWriter(tempFolder.getPath()+"/TestBooks.dat");
    }

    @AfterEach
    void cleanup() {
        tempFolder.delete();
    }

    @Test
    void testGetBooks1Bill() {
        ArrayList<Bill> bills = new ArrayList<>();
        ArrayList<Book> books;
        ArrayList<Bill> billList;
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            books = new ArrayList<>();
            books.add(groupOfBooks.get(1));
            books.add(groupOfBooks.get(2));
            books.add(groupOfBooks.get(3));
            books.add(groupOfBooks.get(4));
            books.add(groupOfBooks.get(2));
            books.add(groupOfBooks.get(3));
            bills.add(new Bill(books));
            objectOutputStream.writeObject(bills);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File does not exist");
        } catch (IOException e) {
            throw new RuntimeException("Problems with your PC mate");
        }
        billList = writer.readBills();
        assertEquals(1, billList.size());
        assertEquals(15, billList.get(0).getTotalAmount());
        assertEquals(6,billList.get(0).getBooksSold().size());
    }
    @Test
    void testGetBooks2Bill() {
        ArrayList<Bill> bills = new ArrayList<>();
        ArrayList<Book> books;
        ArrayList<Bill> billList;
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            books = new ArrayList<>();
            books.add(groupOfBooks.get(1));
            books.add(groupOfBooks.get(2));
            books.add(groupOfBooks.get(3));
            books.add(groupOfBooks.get(4));
            books.add(groupOfBooks.get(2));
            books.add(groupOfBooks.get(3));
            bills.add(new Bill(books));
            books = new ArrayList<>();
            books.add(groupOfBooks.get(1));
            books.add(groupOfBooks.get(2));
            books.add(groupOfBooks.get(2));
            books.add(groupOfBooks.get(3));
            bills.add(new Bill(books));
            objectOutputStream.writeObject(bills);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File does not exist");
        } catch (IOException e) {
            throw new RuntimeException("Problems with your PC mate");
        }
        billList = writer.readBills();
        assertEquals(2, billList.size());
        assertEquals(15, billList.get(0).getTotalAmount());
        assertEquals(6,billList.get(0).getBooksSold().size());
        assertEquals(8, billList.get(1).getTotalAmount());
        assertEquals(4,billList.get(1).getBooksSold().size());
    }
    @Test
    void testGetBooks0Bill() {
        ArrayList<Bill> bills = new ArrayList<>();
        ArrayList<Bill> billList;
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            objectOutputStream.writeObject(bills);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File does not exist");
        } catch (IOException e) {
            throw new RuntimeException("Problems with your PC mate");
        }
        billList = writer.readBills();
        assertEquals(0, billList.size());
    }
    @Test
    void testWriteBooks1Bill() {
        ArrayList<Bill> bills = new ArrayList<>();
        ArrayList<Book> books;
        ArrayList<Bill> billList;
        books = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        bills.add(new Bill(books));
        writer.writeBills(bills);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(tempFile))) {
            billList = (ArrayList<Bill>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File does not exist");
        } catch (IOException e) {
            throw new RuntimeException("Problems with your PC mate");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("This class is unreal");
        }
        assertEquals(1,billList.size());
        assertEquals(15, billList.get(0).getTotalAmount());
        assertEquals(6,billList.get(0).getBooksSold().size());
    }
    @Test
    void testWriteBooks2Bill() {
        ArrayList<Bill> bills = new ArrayList<>();
        ArrayList<Book> books;
        ArrayList<Bill> billList;
        books = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        bills.add(new Bill(books));
        books = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        bills.add(new Bill(books));
        writer.writeBills(bills);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(tempFile))) {
            billList = (ArrayList<Bill>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File does not exist");
        } catch (IOException e) {
            throw new RuntimeException("Problems with your PC mate");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("This class is unreal");
        }
        assertEquals(2, billList.size());
        assertEquals(15, billList.get(0).getTotalAmount());
        assertEquals(6,billList.get(0).getBooksSold().size());
        assertEquals(8, billList.get(1).getTotalAmount());
        assertEquals(4,billList.get(1).getBooksSold().size());
    }
    @Test
    void testWriteBooks0Bill() {
        ArrayList<Bill> bills = new ArrayList<>();
        ArrayList<Bill> billList;
        writer.writeBills(bills);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(tempFile))) {
            billList = (ArrayList<Bill>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File does not exist");
        } catch (IOException e) {
            throw new RuntimeException("Problems with your PC mate");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("This class is unreal");
        }
        assertEquals(0, billList.size());
    }
}
