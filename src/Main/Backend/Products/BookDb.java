package Main.Backend.Products;

import java.util.ArrayList;

public interface BookDb {

    void writeBooks(ArrayList<Book> books);
    ArrayList<Book> readBooks();

}
