package Products;

import java.io.*;
import java.util.ArrayList;

public class BookProxy implements BookDb{

    private final File products;

    public BookProxy(String filePath){
        products = new File(filePath);
        if(!products.exists())
            writeBooks(new ArrayList<Book>());
    }

    @Override
    public void writeBooks(ArrayList<Book> books) {
        try {
            FileOutputStream out = new FileOutputStream(products);
            ObjectOutputStream outOb = new ObjectOutputStream(out);
            outOb.writeObject(books);
            outOb.close();
            out.close();
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Book> readBooks() {
        ArrayList<Book> productList = new ArrayList<>();
        try {
            FileInputStream in = new FileInputStream(products);
            ObjectInputStream inOb = new ObjectInputStream(in);
            productList = (ArrayList<Book>)inOb.readObject();
            setProperties(productList);
            inOb.close();
            in.close();
        }catch (FileNotFoundException e) {
            System.err.println("File not Found!!!");
        } catch (ClassNotFoundException e) {
            System.err.println("Class not Found!!!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return productList;
    }

    public void setProperties(ArrayList<Book> books) {
        for(Book x: books) {
            x.setAuthorProperty();
            x.setCategoryProperty();
            x.setIsbnProperty();
            x.setTitleProperty();
            x.setPriceProperty();
            x.setNumberProperty();
            x.setPurchase();
            x.setOriginal();
        }
    }
}
