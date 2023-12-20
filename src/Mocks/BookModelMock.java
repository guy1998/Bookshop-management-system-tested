package Mocks;

import Main.Exceptions.InvalidBookInfo;
import Main.Products.Author;
import Main.Products.Book;

public class BookModelMock extends Book {
    public BookModelMock(String title, double sellingPrice) throws InvalidBookInfo {
        super("111-1111-111", title, "Category", 0, 0, sellingPrice, 20, 4, 1984, new Author("Gordon","Ramsey"));
        this.setNumber(1);
    }
}
