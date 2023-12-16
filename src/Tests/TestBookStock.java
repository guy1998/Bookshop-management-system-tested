package Tests;

import Exceptions.InvalidBookInfo;
import Mocks.BookProxyMock;
import Products.Author;
import Products.Book;
import Products.BookStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestBookStock {

    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<Book> runningLow = new ArrayList<>();

    @BeforeEach
    public void createData(){
        try {
            Book book1 = new Book("132-2141-421", "Harry Potter", "Fantasy", 13.5, 14.5, 15.5, 1, 1, 1991, new Author("Joanne", "K", "Rowling"));
            Book book2 = new Book("132-2241-421","Hobbit",  "Fantasy", 13.5, 14.5, 15.5, 1, 1, 1991, new Author("John", "Ronald", "Tolkien"));
            Book book3 = new Book("132-2341-421","Alice in the Wonderland",  "Fantasy", 13.5, 14.5, 15.5, 1, 1, 1991, new Author("Lewis", "Carol"));
            book1.addNumber(6);
            book2.addNumber(3);
            book3.addNumber(8);
            books.add(book1);
            books.add(book2);
            books.add(book3);
            runningLow.add(book2);
        } catch (InvalidBookInfo e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testRunningLowWithNoBooks(){
        ArrayList<Book> dataset = new ArrayList<>();
        BookStock stock = new BookStock(new BookProxyMock(dataset));
        assertEquals(new ArrayList<Book>(), stock.runningLow());
    }

    @Test
    public void testRunningLowWithDataButNoBookRunningLow(){
        books.remove(1);
        BookStock stock = new BookStock(new BookProxyMock(books));
        assertEquals(new ArrayList<Book>(), stock.runningLow());
    }

    @Test
    public void testRunningLowWithOneBookRunningLow(){
        BookStock stock = new BookStock(new BookProxyMock(books));
        assertEquals(runningLow, stock.runningLow());
    }

    @Test
    public void testRunningLowWithAllBooksRunningLow(){
        books.get(0).addNumber(-3);
        books.get(2).addNumber(-4);
        runningLow.add(0, books.get(0));
        runningLow.add(books.get(2));
        BookStock stock = new BookStock(new BookProxyMock(books));
        assertEquals(runningLow, stock.runningLow());
    }

    @Test
    public void testRunningLowWithOnlyOneBook(){
        books.remove(0);
        books.remove(1);
        BookStock stock = new BookStock(new BookProxyMock(books));
        assertEquals(runningLow, stock.runningLow());
    }

    @Test
    public void testFindBookByTitle(){
        assertAll("Test for finding book by title", ()->{
            BookStock stock = new BookStock(new BookProxyMock(books));
            assertNull(stock.findBook("Does not exist"));
        }, ()->{
            BookStock stock = new BookStock(new BookProxyMock(books));
            assertEquals(new Book("132-2141-421", "Harry Potter", "Fantasy", 13.5, 14.5, 15.5, 1, 1, 1991, new Author("Joanne", "K", "Rowling")), stock.findBook("Harry Potter"));
        }, ()->{
            BookStock stock = new BookStock(new BookProxyMock(new ArrayList<Book>()));
            assertNull(stock.findBook("Hellooo"));
        });
    }
    
    @Test
    public void testFindBookByReference(){
        assertAll("Tests for finding book by reference", ()->{
            Book nonExistantBook = new Book("111-2141-421", "Does not exist", "Fantasy", 13.5, 14.5, 15.5, 1, 1, 1991, new Author("Joanne", "K", "Rowling"));
            BookStock stock = new BookStock(new BookProxyMock(books));
            assertNull(stock.findBook(nonExistantBook));
        }, ()->{
            Book existantBook = new Book("132-2141-421", "Harry Potter", "Fantasy", 13.5, 14.5, 15.5, 1, 1, 1991, new Author("Joanne", "K", "Rowling"));
            BookStock stock = new BookStock(new BookProxyMock(books));
            assertEquals(new Book("132-2141-421", "Harry Potter", "Fantasy", 13.5, 14.5, 15.5, 1, 1, 1991, new Author("Joanne", "K", "Rowling")), stock.findBook(existantBook));
        }, ()->{
            BookStock stock = new BookStock(new BookProxyMock(new ArrayList<>()));
            assertNull(stock.findBook(new Book("132-2141-421", "Harry Potter", "Fantasy", 13.5, 14.5, 15.5, 1, 1, 1991, new Author("Joanne", "K", "Rowling"))));
        });
    }

    @Test
    public void testExist(){
        assertAll("Tests for exist method", ()->{
            BookStock stock = new BookStock(new BookProxyMock(books));
            assertTrue(stock.exists("132-2141-421"));
        }, ()->{
            BookStock stock = new BookStock(new BookProxyMock(books));
            assertFalse(stock.exists("111-2141-421"));
        }, ()->{
            BookStock stock = new BookStock(new BookProxyMock(new ArrayList<>()));
            assertFalse(stock.exists("111-2141-421"));
        });
    }

    @Test
    public void testEmpty(){
        assertAll("Tests for empty method", ()->{
            assertTrue((new BookStock(new BookProxyMock(new ArrayList<>()))).empty());
        }, ()->{
            assertFalse((new BookStock(new BookProxyMock(books))).empty());
        }, ()->{
            books.get(0).addNumber(-(books.get(0).getNumber()));
            books.get(1).addNumber(-(books.get(1).getNumber()));
            books.get(2).addNumber(-(books.get(2).getNumber()));
            assertTrue((new BookStock(new BookProxyMock(books))).empty());
        });
    }

}
