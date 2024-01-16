package Tests.SystemTesting;

import Main.Backend.Exceptions.InvalidBookInfo;
import Main.GUI.LoginPage;
import Main.Backend.Products.Author;
import Main.Backend.Products.Book;
import Main.Backend.Products.BookStock;
import Main.Backend.Users.Access;
import Main.Backend.Users.Administrator;
import Main.Backend.Users.Manager;
import Main.Backend.Users.UserStack;
import Tests.Utils.CacheOperations;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestManagerAddBookThread extends ApplicationTest {

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
        book1.setNumber(q1-1);
        book2.setNumber(q2-1);
        billBooks.add(book1);
        billBooks.add(book2);
        return billBooks;
    }

    @BeforeAll
    public static void createUsersAndBooks() throws Exception {
        CacheOperations.clearCache();
        Administrator admin = new Administrator("Aldrin", "Cifliku", "Guy_1989", "Juve/123", "acifliku@gmail.com", "+355676105565", 17, 12, 2002);
        users.addUser(admin);
        Manager max = new Manager("Max", "Verstappen", "Super/123", "Red/1234", "max@gmail.com", "+355676105565", 1, 1, 1999, "123-1019-231", 1500.0, Access.FULL);
        users.addUser(max);
        for(Book book: books)
            stock.addBook(book);
    }

    //@BeforeAll
    @AfterAll
    public static void removeUsers() throws Exception {
        File booksFile = new File("Database/products.dat");
        File transactionFile = new File("Database/Transaction.dat");
        System.out.println(transactionFile.delete());
        System.out.println(booksFile.delete());
        System.out.println(booksFile.createNewFile());
        users.deleteUser("Guy_1989");
        users.deleteUser("Super/123");
    }
    @Test
    public void addBookSuccessful() {
        clickOn("#usernameField").write("Super/123");
        clickOn("#passwordField").write("Red/1234");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        assertEquals("These books are running low!!!\n" +
                     "\n" +
                     "These books are missing in stock!!!\n" +
                     "\"Book with 2 authors\" by Author 1, Author 2, Genre: Fantasy", text.getText());
        clickOn("OK");
        clickOn("Book Stock");
        clickOn("Add Book");
        clickOn("book title...").write("Lord of the Rings");
        clickOn("ISBN...").write("111-1111-111");
        clickOn("selling price...").write("20.5");
        clickOn("purchase price...").write("15.0");
        clickOn("original price...").write("10.99");
        clickOn("author's name...").write("James");
        clickOn("author's middle name...").write("R.R");
        clickOn("author's surname...").write("Tolkien");
        clickOn("Category");
        clickOn("Fantasy");
        clickOn("Add");
        DialogPane pane = robot.lookup(".dialog-pane").query();
        assertEquals("New book was created successfully", pane.getHeaderText());
        clickOn("OK");
        clickOn("Cancel");
        TableView<Book> txt = robot.lookup("#books").query();
        assertEquals("Lord of the Rings", txt.getColumns().get(0).getCellObservableValue(txt.getItems().size()-1).getValue());
        assertEquals("James R.R Tolkien", txt.getColumns().get(1).getCellObservableValue(txt.getItems().size()-1).getValue());
        assertEquals("Fantasy", txt.getColumns().get(2).getCellObservableValue(txt.getItems().size()-1).getValue());
        clickOn("Information");
        clickOn("Detailed");
        txt = robot.lookup("#books").query();
        assertEquals("111-1111-111", txt.getColumns().get(1).getCellObservableValue(txt.getItems().size()-1).getValue());
        assertEquals(10.99, txt.getColumns().get(3).getCellObservableValue(txt.getItems().size()-1).getValue());
        assertEquals(15.0, txt.getColumns().get(2).getCellObservableValue(txt.getItems().size()-1).getValue());
        assertEquals(20.5, txt.getColumns().get(4).getCellObservableValue(txt.getItems().size()-1).getValue());
}

    @Test
    public void addBookWrongISBN() {
        clickOn("#usernameField").write("Super/123");
        clickOn("#passwordField").write("Red/1234");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        assertEquals("These books are running low!!!\n" +
                     "\n" +
                     "These books are missing in stock!!!\n" +
                     "\"Book with 2 authors\" by Author 1, Author 2, Genre: Fantasy", text.getText());
        clickOn("OK");
        clickOn("Book Stock");
        clickOn("Add Book");
        clickOn("book title...").write("Lord of the Rings");
        clickOn("ISBN...").write("132-2141-421");
        clickOn("selling price...").write("20.5");
        clickOn("purchase price...").write("15.0");
        clickOn("original price...").write("10.99");
        clickOn("author's name...").write("James");
        clickOn("author's middle name...").write("R.R");
        clickOn("author's surname...").write("Tolkien");
        clickOn("Category");
        clickOn("Fantasy");
        clickOn("Add");
        text = robot.lookup(".dialog-pane .content .text").query();
        assertEquals("There exists a book with this ISBN", text.getText());
        clickOn("OK");
    }

    @Test
    public void addBookWrongPrice() {
        clickOn("#usernameField").write("Super/123");
        clickOn("#passwordField").write("Red/1234");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        assertEquals("These books are running low!!!\n" +
                     "\n" +
                     "These books are missing in stock!!!\n" +
                     "\"Book with 2 authors\" by Author 1, Author 2, Genre: Fantasy", text.getText());
        clickOn("OK");
        clickOn("Book Stock");
        clickOn("Add Book");
        clickOn("book title...").write("Lord of the Rings");
        clickOn("ISBN...").write("132-2141-421");
        clickOn("selling price...").write("aaa");
        clickOn("purchase price...").write("15.0");
        clickOn("original price...").write("10.99");
        clickOn("author's name...").write("James");
        clickOn("author's middle name...").write("R.R");
        clickOn("author's surname...").write("Tolkien");
        clickOn("Category");
        clickOn("Fantasy");
        clickOn("Add");
        text = robot.lookup(".dialog-pane .content .text").query();
        assertEquals("Please enter only decimal numbers in price fields!!", text.getText());
        clickOn("OK");
    }
    @Test
    public void addBookWNoAuthor() {
        clickOn("#usernameField").write("Super/123");
        clickOn("#passwordField").write("Red/1234");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        assertEquals("These books are running low!!!\n" +
                     "\n" +
                     "These books are missing in stock!!!\n" +
                     "\"Book with 2 authors\" by Author 1, Author 2, Genre: Fantasy", text.getText());
        clickOn("OK");
        clickOn("Book Stock");
        clickOn("Add Book");
        clickOn("book title...").write("Lord of the Rings");
        clickOn("ISBN...").write("132-2141-421");
        clickOn("selling price...").write("20.5");
        clickOn("purchase price...").write("15.0");
        clickOn("original price...").write("10.99");
        clickOn("Category");
        clickOn("Fantasy");
        clickOn("Add");
        text = robot.lookup(".dialog-pane .content .text").query();
        assertEquals("You must enter a name and surname", text.getText());
        clickOn("OK");
    }
    @Test
    public void addBookNoTitle() {
        clickOn("#usernameField").write("Super/123");
        clickOn("#passwordField").write("Red/1234");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        assertEquals("These books are running low!!!\n" +
                     "\n" +
                     "These books are missing in stock!!!\n" +
                     "\"Book with 2 authors\" by Author 1, Author 2, Genre: Fantasy", text.getText());
        clickOn("OK");
        clickOn("Book Stock");
        clickOn("Add Book");
        clickOn("ISBN...").write("111-1111-111");
        clickOn("selling price...").write("20.5");
        clickOn("purchase price...").write("15.0");
        clickOn("original price...").write("10.99");
        clickOn("author's name...").write("James");
        clickOn("author's middle name...").write("R.R");
        clickOn("author's surname...").write("Tolkien");
        clickOn("Category");
        clickOn("Fantasy");
        clickOn("Add");
        text = robot.lookup(".dialog-pane .content .text").query();
        assertEquals("All fields must be complete in this section.", text.getText());
        clickOn("OK");
    }
}