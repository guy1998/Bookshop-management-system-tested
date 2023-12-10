package Mocks;

import Exceptions.InvalidBookInfo;
import Products.Author;
import Products.Book;

public class BookModelMock extends Book {
    public BookModelMock(String title, double sellingPrice) throws InvalidBookInfo {
        super("111-1111-111", title, "Category", 0, 0, sellingPrice, 20, 4, 1984, new Author("Gordon","Ramsey"));
        this.setNumber(1);
    }
}
