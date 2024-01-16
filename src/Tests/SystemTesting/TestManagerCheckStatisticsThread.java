package Tests.SystemTesting;

import Main.Exceptions.InvalidBookInfo;
import Main.GUI.LoginPage;
import Main.Products.Author;
import Main.Products.Book;
import Main.Products.BookStock;
import Main.Users.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestManagerCheckStatisticsThread extends ApplicationTest {

    private static final UserStack users = new UserStack();
    private static final BookStock stock = new BookStock();
    private static final ArrayList<Book> books = createBooks(100,0);

    @Override
    public void start(Stage primaryStage) {
        (new LoginPage()).show(primaryStage);
    }

    public static void sleep(int seconds){
        try {
            for(int i=0;i<seconds;i++) {
                Thread.sleep(1000);
                System.out.println(i + 1);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<Book> createBooks(int q1, int q2){
        ArrayList<Book> billBooks = new ArrayList<>();
        Book book1, book2;
        try {
            book1 = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
            book2 = new Book("132-2141-420", "Book with 2 authors", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Author", "1"), new Author("Author", "2"));
        } catch (InvalidBookInfo e) {
            throw new RuntimeException(e);
        }
        book1.setNumber(q1);
        book2.setNumber(q2);
        billBooks.add(book1);
        billBooks.add(book2);
        return billBooks;
    }

    @BeforeAll
    public static void createUsersAndBooks() throws Exception {
        Administrator admin = new Administrator("Aldrin", "Cifliku", "Guy_1989", "Juve/123", "acifliku@gmail.com", "+355676105565", 17, 12, 2002);
        users.addUser(admin);
        Manager max = new Manager("Max", "Verstappen", "Super/123", "Red/1234", "max@gmail.com", "+355676105565", 1, 1, 1999, "123-1019-231", 1500.0, Access.FULL);
        users.addUser(max);
        Librarian min = new Librarian("Min", "Verstappen", "Supra/123", "Red/1234", "min@gmail.com", "+355676105565", 1, 1, 1999, "123-1019-234", 1500.0, Access.FULL);
        users.addUser(min);
        for(Book book: books)
            stock.addBook(book);
        ArrayList<Book> billBooks = createBooks(2, 3);
        min.createBill(billBooks);
        billBooks = createBooks(4, 1);
        min.createBill(billBooks);
    }

    //@BeforeAll
    @AfterAll
    public static void removeUsers() throws Exception {
        File booksFile = new File("Database/products.dat");
        File[] bills = (new File("Database/Bills/BinaryBill")).listFiles();
        System.out.println(booksFile.delete());
        for(File bill: bills)
            System.out.println(bill.delete());
        System.out.println(booksFile.createNewFile());
        users.deleteUser("Guy_1989");
        users.deleteUser("Super/123");
        users.deleteUser("Supra/123");
    }
    @Test
    public void checkStatsInterval() {
        clickOn("#usernameField").write("Super/123");
        clickOn("#passwordField").write("Red/1234");
        clickOn("#loginButton");
        clickOn("OK");
        clickOn("Staff Managment");
        clickOn("#Supra/123");
        clickOn("Data filter");
        clickOn("Interval of days");
        FxRobot robot = new FxRobot();
        Label txt = robot.lookup("#nrOfBooks").query();
        assertEquals("10",txt.getText());
        txt = robot.lookup("#numberOfBills").query();
        assertEquals("2",txt.getText());
        txt = robot.lookup("#moneyNumber").query();
        assertEquals("135.0$",txt.getText());

    }
    @Test
    public void checkStatsSpecificDate() {
        clickOn("#usernameField").write("Super/123");
        clickOn("#passwordField").write("Red/1234");
        clickOn("#loginButton");
        clickOn("OK");
        clickOn("Staff Managment");
        clickOn("#Supra/123");
        clickOn("Data filter");
        clickOn("#specific");
        FxRobot robot = new FxRobot();
        Label txt = robot.lookup("#nrOfBooks").query();
        assertEquals("10",txt.getText());
        txt = robot.lookup("#numberOfBills").query();
        assertEquals("2",txt.getText());
        txt = robot.lookup("#moneyNumber").query();
        assertEquals("135.0$",txt.getText());

    }
    @Test
    public void checkStatsBeginning() {
        clickOn("#usernameField").write("Super/123");
        clickOn("#passwordField").write("Red/1234");
        clickOn("#loginButton");
        clickOn("OK");
        clickOn("Staff Managment");
        clickOn("#Supra/123");
        FxRobot robot = new FxRobot();
        Label txt = robot.lookup("#nrOfBooks").query();
        assertEquals("0",txt.getText());
        txt = robot.lookup("#numberOfBills").query();
        assertEquals("0",txt.getText());
        txt = robot.lookup("#moneyNumber").query();
        assertEquals("0.0$",txt.getText());

    }
}