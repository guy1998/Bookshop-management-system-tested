package Mocks;

import Main.Backend.Products.Bill;
import Main.Backend.Products.Book;
import Main.Backend.Utilis.CompDate;

import java.util.ArrayList;

public class BillModelMock extends Bill {
    public BillModelMock(ArrayList<Book> booksSold, CompDate date) {
        super(booksSold);
        this.dateIssued = date;
    }
}
