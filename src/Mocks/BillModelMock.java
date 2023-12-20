package Mocks;

import Main.Products.Bill;
import Main.Products.Book;
import Main.Utilis.CompDate;

import java.util.ArrayList;

public class BillModelMock extends Bill {
    public BillModelMock(ArrayList<Book> booksSold, CompDate date) {
        super(booksSold);
        this.dateIssued = date;
    }
}
