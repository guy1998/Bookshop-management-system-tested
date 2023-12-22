package Tests.UnitTesting;

import Main.Exceptions.InvalidBookInfo;
import Main.Products.Bill;
import Main.Products.Book;
import Mocks.BookModelMock;
import Mocks.LibrarianMock;
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
    static LibrarianMock lib;
    static HashMap<Integer, BookModelMock> groupOfBooks;
    static File tempFile;
    @BeforeAll
    static void setUp() {
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
    void caseSetup() throws Exception {
        tempFile = new File(tempFolder, "TestBooks.dat");
        lib = new LibrarianMock(tempFile.getPath(),true);
        System.out.println(tempFile.createNewFile());
        try(PrintWriter printWriter = new PrintWriter(new FileWriter(tempFile.getPath(),false),false)){
            printWriter.flush();
        }
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))){
            objectOutputStream.writeObject(new ArrayList<Bill>());
        }
    }

    @AfterEach
    void cleanup() {
        System.out.println(tempFolder.delete());
    }

    @Test
    void testCreate1Bill(){
        ArrayList<Bill> bills;
        double x;
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(tempFile))){
            ArrayList<Book> books = new ArrayList<>();
            books.add(groupOfBooks.get(1));
            books.add(groupOfBooks.get(2));
            books.add(groupOfBooks.get(4));
            x = lib.createBill(books);
            assertEquals(7,x);
            bills = (ArrayList<Bill>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Not a real File sorryyyy");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Something");
        }
        assertEquals(1,bills.size());
        assertEquals(3,bills.get(0).getBooksSold().size());

    }
    @Test
    void testCreate2Bill(){
        ArrayList<Bill> bills;
        ArrayList<Book> books;
        double x;
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(tempFile))){
            books = new ArrayList<>();
            books.add(groupOfBooks.get(1));
            books.add(groupOfBooks.get(2));
            books.add(groupOfBooks.get(4));
            x = lib.createBill(books);
            assertEquals(7,x);
            books = new ArrayList<>();
            books.add(groupOfBooks.get(1));
            books.add(groupOfBooks.get(3));
            books.add(groupOfBooks.get(3));
            books.add(groupOfBooks.get(1));
            x = lib.createBill(books);
            assertEquals(8,x);
            bills = (ArrayList<Bill>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Not a real File sorryyyy");
        } catch (IOException e) {
            throw new RuntimeException("Idk how this can go this wrong but hey you can see me gg");
        } catch (Exception e) {
            throw new RuntimeException("Something");
        }
        assertEquals(2,bills.size());
        assertEquals(3,bills.get(0).getBooksSold().size());

    }
    @Test
    void testCreate0Bill(){
        ArrayList<Bill> bills;
        double x;
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(tempFile))){
            ArrayList<Book> books = new ArrayList<>();
            x = lib.createBill(books);
            assertEquals(0,x);
            bills = (ArrayList<Bill>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Not a real File sorryyyy");
        } catch (IOException e) {
            throw new RuntimeException("Idk how this can go this wrong but hey you can see me gg");
        } catch (Exception e) {
            throw new RuntimeException("Something");
        }
        assertEquals(1,bills.size());
        assertEquals(0,bills.get(0).getBooksSold().size());

    }
}
