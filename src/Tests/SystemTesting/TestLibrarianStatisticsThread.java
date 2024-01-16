package Tests.SystemTesting;

import Main.GUI.LoginPage;
import Main.Backend.Products.Author;
import Main.Backend.Products.Book;
import Main.Backend.Users.Access;
import Main.Backend.Users.Administrator;
import Main.Backend.Users.Librarian;
import Main.Backend.Users.UserStack;
import Tests.Utils.CacheOperations;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
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

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextMatchers.hasText;

public class TestLibrarianStatisticsThread extends ApplicationTest {


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
    public static void addStatistics() throws Exception{
        CacheOperations.clearCache();
        Administrator admin = new Administrator("Aldrin", "Cifliku", "Guy_1989", "Juve/123", "acifliku@gmail.com", "+355676105565", 17, 12, 2002);
        users.addUser(admin);
        Librarian max = new Librarian("Max", "Verstappen", "Super/123", "Red/1234", "max@gmail.com", "+355676105565", 1, 1, 1999, "123-1019-231", 1500.0, Access.FULL);
        users.addUser(max);
        ArrayList<Book> billBooks = createBooks(2, 3);
        max.createBill(billBooks);
        billBooks = createBooks(4, 1);
        max.createBill(billBooks);
    }

    @AfterAll
    public static void removeStatistics() throws Exception{
        File binaryBillFolder = new File("Database/Bills/BinaryBill/"+users.findUser("Super/123").getUserId() + ".dat");
        File textBillFolder = new File("Database/Bills/TextBill/"+users.findUser("Super/123").getUserId());
        File adminMsg = new File("Database/Messages/" + users.findUser("Guy_1989").getUserId()+".msg");
        File libMsg = new File("Database/Messages/" + users.findUser("Super/123").getUserId()+".msg");
        System.out.println(binaryBillFolder.delete());
        System.out.println(textBillFolder.delete());
        System.out.println(adminMsg.delete());
        System.out.println(libMsg.delete());
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
    public void testManageEmployeeNoDateChosen(){
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("#manage");
        clickOn("#Super/123");
        FxRobot robot = new FxRobot();
        Label text = robot.lookup("#nrOfBooks").query();
        verifyThat(text, LabeledMatchers.hasText("0"));
        Label text2 = robot.lookup("#numberOfBills").query();
        verifyThat(text2, LabeledMatchers.hasText("0"));
        Label text3 = robot.lookup("#moneyNumber").query();
        verifyThat(text3, LabeledMatchers.hasText("0.0$"));
    }

    @Test
    public void testManageEmployeeStatsWithSingleDate(){
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("#manage");
        clickOn("#Super/123");
        clickOn("#filter");
        clickOn("#specific");
        FxRobot robot = new FxRobot();
        Label text = robot.lookup("#nrOfBooks").query();
        verifyThat(text, LabeledMatchers.hasText("10"));
        Label text2 = robot.lookup("#numberOfBills").query();
        verifyThat(text2, LabeledMatchers.hasText("2"));
        Label text3 = robot.lookup("#moneyNumber").query();
        verifyThat(text3, LabeledMatchers.hasText("135.0$"));
        clickOn("#specificPicker");
        for(int i=0; i<10; i++)
            type(KeyCode.BACK_SPACE);
        clickOn("#specificPicker").write("1/1/2001");
        type(KeyCode.ENTER);
        text = robot.lookup("#nrOfBooks").query();
        verifyThat(text, LabeledMatchers.hasText("0"));
        text2 = robot.lookup("#numberOfBills").query();
        verifyThat(text2, LabeledMatchers.hasText("0"));
        text3 = robot.lookup("#moneyNumber").query();
        verifyThat(text3, LabeledMatchers.hasText("0.0$"));
    }

    @Test
    public void testManageEmployeeStatsWithInterval(){
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("#manage");
        clickOn("#Super/123");
        clickOn("#filter");
        clickOn("#interval");
        FxRobot robot = new FxRobot();
        Label text = robot.lookup("#nrOfBooks").query();
        verifyThat(text, LabeledMatchers.hasText("10"));
        Label text2 = robot.lookup("#numberOfBills").query();
        verifyThat(text2, LabeledMatchers.hasText("2"));
        Label text3 = robot.lookup("#moneyNumber").query();
        verifyThat(text3, LabeledMatchers.hasText("135.0$"));
        clickOn("#startPicker");
        for(int i=0; i<10; i++)
            type(KeyCode.BACK_SPACE);
        clickOn("#startPicker").write("1/1/2001");
        clickOn("#endPicker");
        for(int i=0; i<10; i++)
            type(KeyCode.BACK_SPACE);
        clickOn("#endPicker").write("1/1/2001");
        type(KeyCode.ENTER);
        text = robot.lookup("#nrOfBooks").query();
        verifyThat(text, LabeledMatchers.hasText("0"));
        text2 = robot.lookup("#numberOfBills").query();
        verifyThat(text2, LabeledMatchers.hasText("0"));
        text3 = robot.lookup("#moneyNumber").query();
        verifyThat(text3, LabeledMatchers.hasText("0.0$"));
    }

}
