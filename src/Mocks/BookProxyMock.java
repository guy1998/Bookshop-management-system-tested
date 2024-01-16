package Mocks;

import Main.Backend.Products.Book;
import Main.Backend.Products.BookDb;

import java.util.ArrayList;

public class BookProxyMock implements BookDb {

    private ArrayList<Book> books;

    public BookProxyMock(ArrayList<Book> books){
        this.books = books;
    }
    @Override
    public void writeBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public ArrayList<Book> readBooks() {
        return books;
    }
}
