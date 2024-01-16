package Tests.SystemTesting;

import Main.GUI.LoginPage;
import Main.Backend.Products.Author;
import Main.Backend.Products.Book;
import Main.Backend.Products.BookStock;
import Main.Backend.Products.TransactionControl;
import Main.Backend.Users.Access;
import Main.Backend.Users.Librarian;
import Main.Backend.Users.UserStack;
import Tests.Utils.CacheOperations;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextMatchers.hasText;

public class SellBooksThread extends ApplicationTest {

    private static UserStack users = new UserStack();
    private static BookStock stock = new BookStock();

    @Override
    public void start(Stage primaryStage){
        (new LoginPage()).show(primaryStage);
    }

    //helper method that creates 3 books in db
    public static void createBooks() throws Exception {
        Book book1 = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        Book book2 = new Book("132-2141-423", "Book with 2 authors", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author[]{new Author("Author", "1"), new Author("Author", "2")});
        Book book3 = new Book("122-2211-111", "Book", "Thriller", 13.5, 12.5, 14.5, 5, 11, 2000, new Author("Name", "Surname"));
        book1.setNumber(2);
        book2.setNumber(5);
        book3.setNumber(-1);
        stock.addBook(book1);
        stock.addBook(book2);
        stock.addBook(book3);
    }

    public static void deleteBooks(){
        stock.delete(stock.findBook("Harry Potter"));
        stock.delete(stock.findBook("Book with 2 authors"));
        stock.delete(stock.findBook("Book"));
    }

    @BeforeAll
    public static void addUser() throws Exception{
        CacheOperations.clearCache();
        Librarian librarian = new Librarian("Aldrin", "Cifliku", "Guy_1989", "Juve/123", "acifliku@gmail.com", "+355676105565", 17, 12, 2002, "123-1234-918", 1500.0, Access.FULL);
        users.addUser(librarian);
        createBooks();
    }

    @AfterAll
    public static void deleteUser() throws Exception {
        File messages = new File("Database/Messages/" + users.findUser("Guy_1989").getUserId() + ".msg");
        File binaryBill = new File("Database/Bills/BinaryBill/" + users.findUser("Guy_1989").getUserId() + ".dat");
        File billFolder = new File("Database/Bills/TextBill/" + users.findUser("Guy_1989").getUserId());
        File transactionFile = new File("Database/Transaction.dat");
        users.deleteUser("Guy_1989");
        System.out.println(binaryBill.delete());
        System.out.println(messages.delete());
        System.out.println(transactionFile.delete());
        if (billFolder.exists() && billFolder.isDirectory()) {
            for (File f : billFolder.listFiles())
                System.out.println(f.delete());
        }
        System.out.println(billFolder.delete());
        deleteBooks();
    }

    @Test
    public void testSellBooks() throws Exception{
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        clickOn("#cart");
        //verify change in number
        clickOn("#132-2141-421");
        Label label = robot.lookup("#132-2141-421").query();
        verifyThat(label, LabeledMatchers.hasText("\"Harry Potter\" by Joanne K Rowling, Genre: Fantasy\nCopies left: 2"));
        //verify correct format of books without copies
        Label noStockBook = robot.lookup("#122-2211-111").query();
        verifyThat(noStockBook, LabeledMatchers.hasText("\"Book\" by Name Surname, Genre: Thriller\nCopies:0"));
        assertEquals(Color.LIGHTSLATEGRAY, noStockBook.getBackground().getFills().get(0).getFill());
        //verify prompt when trying to sell book with no copies
        clickOn("#122-2211-111");
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("You cannot sell this book as there are no copies of it left"));
        clickOn("OK");
        clickOn("#check");
        label = robot.lookup("#132-2141-421").query();
        verifyThat(label, LabeledMatchers.hasText("\"Harry Potter\" by Joanne K Rowling, Genre: Fantasy\nCopies:1, Total amount: 13.5$"));
        clickOn("#plus132-2141-421");//add some copies of this book at checkout
        label = robot.lookup("#132-2141-421").query();
        verifyThat(label, LabeledMatchers.hasText("\"Harry Potter\" by Joanne K Rowling, Genre: Fantasy\nCopies: 2, Total amount: 27.0$"));
        clickOn("#pay");
        clickOn("OK");
        clickOn("#saveTransaction");
        //verify we reached the home page
        Label hello = robot.lookup("#hello").query();
        verifyThat(hello, LabeledMatchers.hasText("Hello Aldrin!"));
        //verify transaction was saved
        ArrayList<Book> booksSold = new ArrayList<>();
        booksSold.add(new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling")));
        TransactionControl transactions = new TransactionControl();
        assertEquals(booksSold, transactions.getTransactions().get(0).getBooks());
    }
}
