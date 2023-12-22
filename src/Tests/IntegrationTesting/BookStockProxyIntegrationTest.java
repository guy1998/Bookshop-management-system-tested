package Tests.IntegrationTesting;

import Main.Exceptions.BookExistsException;
import Main.Exceptions.UserAlreadyExistsException;
import Main.Products.Author;
import Main.Products.Book;
import Main.Products.BookProxy;
import Main.Products.BookStock;
import Main.Users.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

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
            myBooks.add(new Book("132-2141-421", "Book with 2 authors", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author[]{new Author("Author", "1"), new Author("Author", "2")}));
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
                f.delete();
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
        assertAll("Integration between addUser and writeUsers", ()->{
            Throwable exception = assertThrows(BookExistsException.class, ()->books.addBook(existentBook));
            assertEquals("There exists a book with this ISBN", exception.getMessage());
        }, ()->{
            books.addBook(nonExistentBook);
            ArrayList<Book> newBooks = auxiliaryReader(new File(tempFile.getPath()));
            assertEquals(1, newBooks.size() - myBooks.size());
            assertEquals(nonExistentBook, newBooks.get(newBooks.size() - 1));
        });
    }

}
