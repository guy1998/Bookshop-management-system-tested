package Tests;

import Exceptions.InvalidBookInfo;
import Products.Author;
import Products.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestBook {

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
    public void checkIsbnValidityWrongISBN(String ISBN){
        assertFalse(Book.checkIsbnValidity(ISBN));
    }

    @Test
    public void checkIsbnValidityRightIsbn(){
        assertTrue(Book.checkIsbnValidity("132-2141-421"));
    }

    @Test
    public void testToString(){
        assertAll("String representation testing", ()->{
            Book book = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
            assertEquals("\"Harry Potter\" by Joanne K Rowling, Genre: Fantasy", book.toString());
        }, ()->{
            Book book = new Book("132-2141-421", "Book with 2 authors", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author[]{new Author("Author", "1"), new Author("Author", "2")});
            assertEquals("\"Book with 2 authors\" by Author 1, Author 2, Genre: Fantasy", book.toString());
        });
    }

    @Test
    public void testEquals() {
        try {
            Book book = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
            assertFalse(book.equals(new Author("Joanne", "K", "Rowling")));
            assertFalse(book.equals(new Book("111-1111-111", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"))));
            assertTrue(book.equals(new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"))));
        } catch (InvalidBookInfo e) {
            System.out.println(e.getMessage());;
        }
    }

    @Test
    public void testClone(){
        try{
            Book book = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
            Book clone = (Book)book.clone();
            assertEquals(book, clone);
            assertFalse(book.getDate() == clone.getDate());
            assertFalse(book.getAuthors() == clone.getAuthors());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
