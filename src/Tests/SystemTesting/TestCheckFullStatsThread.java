package Tests.SystemTesting;

import Main.GUI.LoginPage;
import Main.Products.Author;
import Main.Products.Book;
import Main.Products.Transaction;
import Main.Products.TransactionControl;
import Main.Users.Access;
import Main.Users.Administrator;
import Main.Users.Librarian;
import Main.Users.UserStack;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextMatchers.hasText;

public class TestCheckFullStatsThread extends ApplicationTest {

    private static UserStack users = new UserStack();

    @Override
    public void start(Stage primaryStage){
        (new LoginPage()).show(primaryStage);
    }

    public static ArrayList<Book> createBooks(int q1, int q2) throws Exception{
        ArrayList<Book> billBooks = new ArrayList<>();
        Book book1 = new Book("132-2141-421", "Harry Potter", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author("Joanne", "K", "Rowling"));
        Book book2 = new Book("132-2141-421", "Book with 2 authors", "Fantasy", 12.5, 11.5, 13.5, 3, 12, 1991, new Author[]{new Author("Author", "1"), new Author("Author", "2")});
        book1.setNumber(q1);
        book2.setNumber(q2);
        billBooks.add(book1);
        billBooks.add(book2);
        return billBooks;
    }

    @BeforeAll
    public static void createStatistics() throws Exception{
        Administrator admin = new Administrator("Aldrin", "Cifliku", "Guy_1989", "Juve/123", "acifliku@gmail.com", "+355676105565", 17, 12, 2002);
        users.addUser(admin);
        Librarian max = new Librarian("Max", "Verstappen", "Super/123", "Red/1234", "max@gmail.com", "+355676105565", 1, 1, 1999, "123-1019-231", 1500.0, Access.FULL);
        users.addUser(max);
        ArrayList<Book> billBooks = createBooks(100, 30);
        TransactionControl transactions = new TransactionControl();
        transactions.getTransactions().add(new Transaction(billBooks, true));
        transactions.writeTransactions();
        billBooks = createBooks(40, 80);
        transactions.getTransactions().add(new Transaction(billBooks, true));
        transactions.writeTransactions();
    }

    @AfterAll
    public static void destroyStatistics() throws Exception{
        File binaryBillFolder = new File("Database/Bills/BinaryBill/"+users.findUser("Super/123").getUserId() + ".dat");
        File textBillFolder = new File("Database/Bills/TextBill/"+users.findUser("Super/123").getUserId());
        File adminMsg = new File("Database/Messages/" + users.findUser("Guy_1989").getUserId()+".msg");
        File libMsg = new File("Database/Messages/" + users.findUser("Super/123").getUserId()+".msg");
        File transactionFile = new File("Database/Transaction.dat");
        System.out.println(binaryBillFolder.delete());
        System.out.println(textBillFolder.delete());
        System.out.println(adminMsg.delete());
        System.out.println(libMsg.delete());
        System.out.println(transactionFile.delete());
        users.deleteUser("Guy_1989");
        users.deleteUser("Super/123");
    }

    @Test
    public void testFailedLoginUserNotExist(){
        clickOn("#usernameField").write("WrongUser");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("This user does not exist!"));
    }

    @Test
    public void testFailedPasswordNoMatch(){
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/124");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("The password entered does not match"));
    }

    @Test
    public void testCheckStatistics() throws Exception{
        //failed this test
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("#finance");
        FxRobot robot = new FxRobot();
        Label text = robot.lookup("#income").query();
        verifyThat(text, LabeledMatchers.hasText("Incomes: 3375.0"));
        assertEquals(Color.GREEN, text.getBackground().getFills().get(0).getFill());
        Label spentLabel = robot.lookup("#spent").query();
        verifyThat(spentLabel, LabeledMatchers.hasText("Expenditure: 0.0"));
        assertEquals(Color.RED, spentLabel.getBackground().getFills().get(0).getFill());
        Label salaryLabel = robot.lookup("#salaries").query();
        verifyThat(salaryLabel, LabeledMatchers.hasText("Salaries: -1500.0"));
        assertEquals(Color.RED, salaryLabel.getBackground().getFills().get(0).getFill());
        Label total = robot.lookup("#total").query();
        verifyThat(total, LabeledMatchers.hasText("Total: 1875.0"));
        assertEquals(Color.GREEN, total.getBackground().getFills().get(0).getFill());
    }

}
