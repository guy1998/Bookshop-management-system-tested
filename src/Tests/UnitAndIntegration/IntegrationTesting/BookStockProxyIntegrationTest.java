package Tests.UnitAndIntegration.IntegrationTesting;

import Main.Backend.Exceptions.BookExistsException;
import Main.Backend.Exceptions.InvalidBookInfo;
import Main.Backend.Products.Author;
import Main.Backend.Products.Book;
import Main.Backend.Products.BookProxy;
import Main.Backend.Products.BookStock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookStockProxyIntegrationTest {
    @TempDir
    private static File tempFolder;

    private File tempFile;
    private BookStock books;
    private ArrayList<Book> myBooks = new ArrayList<>();

    @BeforeEach
    public void createDataset(){
        try{
            myBooks.add(new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling")));
            myBooks.add(new Book("132-2141-431", "Book with 2 authors", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author[]{new Author("Author", "1"), new Author("Author", "2")}));
            tempFile = new File(tempFolder, "testBooks.dat");
            FileOutputStream out = new FileOutputStream(tempFile);
            ObjectOutputStream obOut = new ObjectOutputStream(out);
            obOut.writeObject(myBooks);
            obOut.close();
            out.close();
        }catch(Exception e){
            System.out.println("Something went wrong!");
        }
    }
    @AfterEach
    void cleanup() {
        File[] files = tempFolder.listFiles();
        if(files!=null) {
            for(File f: files) {
                System.out.println(f.delete());
            }
        }
    }

    @AfterAll
    public static void deleteDirectory(){
        System.out.println(tempFolder.delete());
    }

    public ArrayList<Book> auxiliaryReader(File file) throws Exception{
        FileInputStream in = new FileInputStream(file);
        ObjectInputStream inOb = new ObjectInputStream(in);
        ArrayList<Book> newBooks = (ArrayList<Book>) inOb.readObject();
        in.close();
        inOb.close();
        return newBooks;
    }

    @Test
    public void testIntegrationAddBooksWriteBooks() throws Exception{
        Book existentBook = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        Book nonExistentBook = new Book("134-2141-425", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        books = new BookStock(new BookProxy(tempFile.getPath()));
        assertAll("Integration between addBooks and writeBooks", ()->{
            Throwable exception = assertThrows(BookExistsException.class, ()->books.addBook(existentBook));
            assertEquals("There exists a book with this ISBN", exception.getMessage());
        }, ()->{
            books.addBook(nonExistentBook);
            ArrayList<Book> newBooks = auxiliaryReader(new File(tempFile.getPath()));
            assertEquals(1, newBooks.size() - myBooks.size());
            assertEquals(nonExistentBook, newBooks.get(newBooks.size() - 1));
        });
    }

    @Test
    public void testIntegrationDeleteBooksWriteBooks() throws Exception {
        Book existentBook = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        books = new BookStock(new BookProxy(tempFile.getPath()));
        assertAll("Integration between deleteBooks and writeBooks",() -> {
            books.delete(existentBook);
            ArrayList<Book> newBooks = auxiliaryReader(new File(tempFile.getPath()));
            assertEquals(-1, newBooks.size() - myBooks.size());
            assertNotEquals(existentBook, newBooks.get(0));
        });
    }

    @Test
    public void testIntegrationModifyTitleWriteTitle() throws Exception{
        Book book = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        books = new BookStock(new BookProxy(tempFile.getPath()));
        books.modifyTitle(book, "Harry Potter 1");
        ArrayList<Book> newBook = auxiliaryReader(tempFile);
        assertEquals("Harry Potter 1", newBook.get(0).getTitle());
        assertNotEquals(myBooks.get(0).getTitle(), newBook.get(0).getTitle());
    }

    @Test
    public void testIntegrationModifyCategoryWriteCategory() throws Exception{
        Book book = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        books = new BookStock(new BookProxy(tempFile.getPath()));
        books.modifyCategory(book, "Fiction");
        ArrayList<Book> newBook = auxiliaryReader(tempFile);
        assertEquals("Fiction", newBook.get(0).getCategory());
        assertNotEquals(myBooks.get(0).getCategory(), newBook.get(0).getCategory());
    }

    @ParameterizedTest
    @CsvSource({
            "''", //Empty ISBN
            "'132-214-421'", //3 chars entered where 4 are needed
            "'132-21441-421'", //5 chars entered where 4 are needed
            "'1312142-421'", //Dashes missing
            "'13-2142-421'", //2 chars entered where 3 are needed (1st position)
            "'132-2142-41'", //2 chars entered where 3 are needed (2nd position)
            "'132-2142-4124'", //4 chars entered where 3 are needed
            "'132-214a-421'", //non numeric elements are entered
    })
    public void testIntegrationModifyISBNWriteISBNNotCorrect(String ISBN) throws Exception{
        books = new BookStock(new BookProxy(tempFile.getPath()));
        Throwable exception = assertThrows(InvalidBookInfo.class, () -> books.modifyISBN(books.findBook("Harry Potter"), ISBN));
        assertEquals("Book ISBN must be of the format xxx-xxxx-xxx", exception.getMessage());
        ArrayList<Book> newBook = auxiliaryReader(tempFile);
        assertNotEquals(ISBN, newBook.get(0).getISBN());
        assertEquals(myBooks.get(0).getISBN(), newBook.get(0).getISBN());
    }

    @Test
    public void testIntegrationModifyISBNWriteISBN() throws Exception{
        Book book = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        books = new BookStock(new BookProxy(tempFile.getPath()));
        books.modifyISBN(book, "132-2141-425");
        ArrayList<Book> newBook = auxiliaryReader(tempFile);
        assertEquals("132-2141-425", newBook.get(0).getISBN());
        assertNotEquals(myBooks.get(0).getISBN(), newBook.get(0).getISBN());
    }

    @Test
    public void testIntegrationModifyOriginalPriceWriteBook() throws Exception{
        Book book = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        books = new BookStock(new BookProxy(tempFile.getPath()));
        books.modifyOriginalPrice(book, 12.2);
        ArrayList<Book> newBook = auxiliaryReader(tempFile);
        assertEquals(12.2, newBook.get(0).getOriginalPrice());
        assertNotEquals(myBooks.get(0).getOriginalPrice(), newBook.get(0).getOriginalPrice());
    }

    @Test
    public void testIntegrationModifyPurchasePriceWriteBook() throws Exception{
        Book book = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        books = new BookStock(new BookProxy(tempFile.getPath()));
        books.modifyPurchasePrice(book, 14.2);
        ArrayList<Book> newBook = auxiliaryReader(tempFile);
        assertEquals(14.2, newBook.get(0).getPurchasePrice());
        assertNotEquals(myBooks.get(0).getPurchasePrice(), newBook.get(0).getPurchasePrice());
    }

    @Test
    public void testIntegrationModifySellingPriceWriteBook() throws Exception {
        Book book = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        books = new BookStock(new BookProxy(tempFile.getPath()));
        books.modifySellingPrice(book, 16.2);
        ArrayList<Book> newBook = auxiliaryReader(tempFile);
        assertEquals(16.2, newBook.get(0).getSellingPrice());
        assertNotEquals(myBooks.get(0).getSellingPrice(), newBook.get(0).getSellingPrice());
    }

    @Test
    public void testIntegrationModifyAuthorsWriteBook() throws Exception {
        Book book = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        Author author = new Author("Joanne", "Rowling");
        Author[] authors = {author};
        books = new BookStock(new BookProxy(tempFile.getPath()));
        books.modifyAuthors(book, authors);
        ArrayList<Book> newBook = auxiliaryReader(tempFile);
        assertEquals(author, newBook.get(0).getAuthors()[0]);
        assertNotEquals(myBooks.get(0).getAuthors(), newBook.get(0).getAuthors());
    }

    @Test
    public void testIntegrationModifyQuantityWriteBook() throws Exception {
        Book book = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        books = new BookStock(new BookProxy(tempFile.getPath()));
        books.modifyQuantity(book, 5);
        ArrayList<Book> newBook = auxiliaryReader(tempFile);
        assertEquals(5, newBook.get(0).getNumber());
        assertNotEquals(myBooks.get(0).getNumber(), newBook.get(0).getNumber());
    }

}
