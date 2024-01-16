package Tests.UnitAndIntegration.IntegrationTesting;

import Main.Backend.Exceptions.InvalidBookInfo;
import Main.Backend.Products.Bill;
import Main.Backend.Products.Book;
import Main.Backend.Utilis.CompDate;
import Mocks.BillModelMock;
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

public class LibrarianBillWriterIntegrationTest {
    @TempDir
    static File tempFolder;
    static LibrarianMock lib;
    static HashMap<Integer,Book> groupOfBooks;
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
    void billWriterCreateBillTest() throws Exception {
        ArrayList<Bill> bills;
        double x;
        ArrayList<Book> books = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(4));
        x = lib.createBill(books);
        assertEquals(7,x);
        bills = lib.readBills();
        assertEquals(1,bills.size());
        assertEquals(3,bills.get(0).getBooksSold().size());
    }
    @Test
    void readBillsNrOfBills0ArgTest() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Bill> bills = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(4));
        bills.add(new Bill(books));
        books = new ArrayList<>();
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(1));
        bills.add(new Bill(books));
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))){
            objectOutputStream.writeObject(bills);
        }
        assertEquals(2, lib.nrOfBills());
    }
    @Test
    void readBillsNrOfBills1ArgTest() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Bill> bills = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(4));
        bills.add(new BillModelMock(books, new CompDate(1,10,2004)));
        books = new ArrayList<>();
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(1));
        bills.add(new BillModelMock(books, new CompDate(15,8,2008)));
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))){
            objectOutputStream.writeObject(bills);
        }
        assertEquals(1, lib.nrOfBills(new CompDate(1,10,2004)));
        assertEquals(1, lib.nrOfBills(new CompDate(15,8,2008)));
        assertEquals(0, lib.nrOfBills(new CompDate(15,8,2003)));
    }
    @Test
    void readBillsNrOfBills2ArgTest() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Bill> bills = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(4));
        bills.add(new BillModelMock(books, new CompDate(1,10,2004)));
        books = new ArrayList<>();
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(1));
        bills.add(new BillModelMock(books, new CompDate(15,8,2008)));
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))){
            objectOutputStream.writeObject(bills);
        }
        assertEquals(2, lib.nrOfBills(new CompDate(1,1,2000),new CompDate(31,12,2050)));
        assertEquals(1, lib.nrOfBills(new CompDate(15,8,2003),new CompDate(15,8,2005)));
        assertEquals(1, lib.nrOfBills(new CompDate(15,8,2007),new CompDate(15,8,2010)));
        assertEquals(0, lib.nrOfBills(new CompDate(15,8,2005),new CompDate(15,8,2007)));

    }
    @Test
    void readBillsMoneyMade1ArgTest() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Bill> bills = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(4));
        bills.add(new BillModelMock(books, new CompDate(1,10,2004)));
        books = new ArrayList<>();
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(1));
        bills.add(new BillModelMock(books, new CompDate(15,8,2008)));
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))){
            objectOutputStream.writeObject(bills);
        }
        assertEquals(10, lib.moneyMade(new CompDate(1,10,2004)));
        assertEquals(9, lib.moneyMade(new CompDate(15,8,2008)));
        assertEquals(0, lib.moneyMade(new CompDate(15,8,2003)));

    }
    @Test
    void readBillsMoneyMade2ArgTest() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Bill> bills = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(4));
        bills.add(new BillModelMock(books, new CompDate(1,10,2004)));
        books = new ArrayList<>();
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(1));
        bills.add(new BillModelMock(books, new CompDate(15,8,2008)));
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))){
            objectOutputStream.writeObject(bills);
        }
        assertEquals(19, lib.moneyMade(new CompDate(1,1,2000),new CompDate(31,12,2050)));
        assertEquals(10, lib.moneyMade(new CompDate(15,8,2003),new CompDate(15,8,2005)));
        assertEquals(9, lib.moneyMade(new CompDate(15,8,2007),new CompDate(15,8,2010)));
        assertEquals(0, lib.moneyMade(new CompDate(15,8,2005),new CompDate(15,8,2007)));

    }
    @Test
    void readBillsNrOfBooks1ArgTest() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Bill> bills = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(4));
        bills.add(new BillModelMock(books, new CompDate(1,10,2004)));
        books = new ArrayList<>();
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(1));
        bills.add(new BillModelMock(books, new CompDate(15,8,2008)));
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))){
            objectOutputStream.writeObject(bills);
        }
        assertEquals(4, lib.nrOfBooks(new CompDate(1,10,2004)));
        assertEquals(4, lib.nrOfBooks(new CompDate(15,8,2008)));
        assertEquals(0, lib.nrOfBooks(new CompDate(15,8,2003)));

    }
    @Test
    void readBillsNrOfBooks2ArgTest() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Bill> bills = new ArrayList<>();
        books.add(groupOfBooks.get(1));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(3));
        books.add(groupOfBooks.get(4));
        bills.add(new BillModelMock(books, new CompDate(1,10,2004)));
        books = new ArrayList<>();
        books.add(groupOfBooks.get(4));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(2));
        books.add(groupOfBooks.get(1));
        bills.add(new BillModelMock(books, new CompDate(15,8,2008)));
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile))){
            objectOutputStream.writeObject(bills);
        }
        assertEquals(8, lib.nrOfBooks(new CompDate(1,1,2000),new CompDate(31,12,2050)));
        assertEquals(4, lib.nrOfBooks(new CompDate(15,8,2003),new CompDate(15,8,2005)));
        assertEquals(4, lib.nrOfBooks(new CompDate(15,8,2007),new CompDate(15,8,2010)));
        assertEquals(0, lib.nrOfBooks(new CompDate(15,8,2005),new CompDate(15,8,2007)));

    }
}
