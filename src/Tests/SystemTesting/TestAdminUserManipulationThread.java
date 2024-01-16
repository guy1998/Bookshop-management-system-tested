package Tests.SystemTesting;

import Main.Exceptions.InvalidBookInfo;
import Main.GUI.LoginPage;
import Main.Products.Author;
import Main.Products.Book;
import Main.Products.BookStock;
import Main.Users.Access;
import Main.Users.Administrator;
import Main.Users.Manager;
import Main.Users.UserStack;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAdminUserManipulationThread extends ApplicationTest {
    private static final UserStack users = new UserStack();
    private static final BookStock stock = new BookStock();
    private static final ArrayList<Book> books = createBooks(100, 0);

    @Override
    public void start(Stage primaryStage) {
        (new LoginPage()).show(primaryStage);
    }

    public static void sleep(int seconds) {
        try {
            for (int i = 0; i < seconds; i++) {
                Thread.sleep(1000);
                System.out.println(i + 1);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Book> createBooks(int q1, int q2) {
        ArrayList<Book> billBooks = new ArrayList<>();
        Book book1, book2;
        try {
            book1 = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
            book2 = new Book("132-2141-420", "Book with 2 authors", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Author", "1"), new Author("Author", "2"));
        } catch (InvalidBookInfo e) {
            throw new RuntimeException(e);
        }
        book1.setNumber(q1 - 1);
        book2.setNumber(q2 - 1);
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
        for (Book book : books)
            stock.addBook(book);
    }

    //@BeforeAll
    @AfterAll
    public static void removeUsers() throws Exception {
        File booksFile = new File("Database/products.dat");
        File usersFile = new File("Database/user.dat");
        System.out.println(booksFile.delete());
        System.out.println(usersFile.delete());
        System.out.println(booksFile.createNewFile());
        System.out.println(usersFile.createNewFile());
    }

    @Test
    public void editName() {
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        clickOn("#manage");
        clickOn("#Super/123");
        clickOn("#0");
        clickOn("Name...").write("Min");
        clickOn("Confirm");
        clickOn("Save");
        Button btt = robot.lookup("#Super/123").query();
        assertEquals("Min Verstappen, MANAGER", btt.getText());
        clickOn("#Super/123");
        clickOn("#0");
        clickOn("Name...").write("Max");
        clickOn("Confirm");
        clickOn("Save");
    }
    @Test
    public void editSurname() {
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        clickOn("#manage");
        clickOn("#Super/123");
        clickOn("#1");
        clickOn("Surname...").write("Min");
        clickOn("Confirm");
        clickOn("Save");
        Button btt = robot.lookup("#Super/123").query();
        assertEquals("Max Min, MANAGER", btt.getText());
        clickOn("#Super/123");
        clickOn("#1");
        clickOn("Surname...").write("Verstappen");
        clickOn("Confirm");
        clickOn("Save");
    }
    @Test
    public void deleteUser() {
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        clickOn("#manage");
        clickOn("#Super/123");
        clickOn("Delete");
        clickOn("OK");
        VBox btt = robot.lookup("#bg").query();
        assertEquals(0, btt.getChildren().size());
    }
}
