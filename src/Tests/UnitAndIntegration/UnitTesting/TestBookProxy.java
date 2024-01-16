package Tests.UnitAndIntegration.UnitTesting;

import Main.Backend.Exceptions.InvalidBookInfo;
import Main.Backend.Products.Author;
import Main.Backend.Products.Book;
import Main.Backend.Products.BookProxy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestBookProxy {
    @TempDir
    private static File tempFolder;


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

    @Test
    public void testReadBooksNonExistentFile(){
        BookProxy bookProxy = new BookProxy(tempFolder.getPath() + "Obviously non existent path");
        ArrayList<Book> books = bookProxy.readBooks();
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testReadBooksEmptyFile(){
        File tempFile = new File(tempFolder, "testBooks.dat");
        BookProxy bookProxy = new BookProxy(tempFile.getPath());
        ArrayList<Book> books = bookProxy.readBooks();
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testReadBooksNonEmptyFile() throws IOException, InvalidBookInfo {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling")));
        books.add(new Book("132-2141-421", "Book with 2 authors", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author[]{new Author("Author", "1"), new Author("Author", "2")}));
        File tempFile = new File(tempFolder, "testBooks.dat");
        FileOutputStream out = new FileOutputStream(tempFile);
        ObjectOutputStream obOut = new ObjectOutputStream(out);
        obOut.writeObject(books);
        obOut.close();
        out.close();
        BookProxy bookProxy = new BookProxy(tempFile.getPath());
        ArrayList<Book> booksRead = bookProxy.readBooks();
        Assertions.assertEquals(books.size(), booksRead.size());
        Assertions.assertEquals(books.get(0).getISBN(), booksRead.get(0).getISBN());
        Assertions.assertEquals(books.get(1).getISBN(), booksRead.get(1).getISBN());

    }

    @Test
    public void testWriteBooks() throws IOException, InvalidBookInfo {
        ArrayList<Book> books = new ArrayList<>();
        books.add((new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"))));
        books.add(new Book("132-2141-421", "Book with 2 authors", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author[]{new Author("Author", "1"), new Author("Author", "2")}));
        File tempFile = new File(tempFolder, "testBooks.dat");
        FileOutputStream out = new FileOutputStream(tempFile);
        ObjectOutputStream obOut = new ObjectOutputStream(out);
        obOut.writeObject(books);
        obOut.close();
        out.close();
        ArrayList<Book> alteredBooks = new ArrayList<>();
        alteredBooks.add(new Book("111-1111-111", "Altered Book", "Altered", 1, 1, 1, 1, 1, 1111, new Author("Altered", "0")));
        BookProxy bookProxy = new BookProxy(tempFile.getPath());
        bookProxy.writeBooks(alteredBooks);
        assertNotEquals(books.size(), bookProxy.readBooks().size());
        Assertions.assertNotEquals(books.get(0).getISBN(), alteredBooks.get(0).getISBN());
    }

}
