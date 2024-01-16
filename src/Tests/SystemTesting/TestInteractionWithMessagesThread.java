package Tests.SystemTesting;

import Main.GUI.LoginPage;
import Main.Backend.Notification.Message;
import Main.Backend.Users.Access;
import Main.Backend.Users.Administrator;
import Main.Backend.Users.Librarian;
import Main.Backend.Users.UserStack;
import Tests.Utils.CacheOperations;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
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

public class TestInteractionWithMessagesThread extends ApplicationTest {

    private static UserStack users = new UserStack();
    @Override
    public void start(Stage primaryStage){
        (new LoginPage()).show(primaryStage);
    }

    @BeforeAll
    public static void createUsersAndMessages() throws Exception{
        CacheOperations.clearCache();
        Administrator admin = new Administrator("Aldrin", "Cifliku", "Guy_1989", "Juve/123", "acifliku@gmail.com", "+355676105565", 17, 12, 2002);
        users.addUser(admin);
        Librarian max = new Librarian("Max", "Verstappen", "Super/123", "Red/1234", "max@gmail.com", "+355676105565", 1, 1, 1999, "123-1019-231", 1500.0, Access.FULL);
        users.addUser(max);
        ArrayList<Message> messages = admin.readMessages();
        messages.add(new Message(max, "Hello there my dear admin!"));
        admin.writeMessages(messages);
    }

    @AfterAll
    public static void removeUsers() throws Exception{
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

    //helper
    public boolean verifyAdded() throws Exception{
        ArrayList<Message> messages = users.findUser("Super/123").readMessages();
        Message message = messages.get(messages.size()-1);
        return message.getText().equals("Hello my dear librarian");
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
    public void testReadAndDeleteDefaultMessage() throws Exception{
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("#" + 0);
        FxRobot robot = new FxRobot();
        Label text = robot.lookup("#messageHeader").query();
        verifyThat(text, LabeledMatchers.hasText("Welcome to BookShop Management System"));
        Label text2 = robot.lookup("#messageText").query();
        verifyThat(text2, LabeledMatchers.hasText("The text about the features"));
        while(true) {
            try {
                clickOn("#delete");
                Text alert = robot.lookup(".dialog-pane .content .text").query();
                verifyThat(alert, hasText("System messages cannot be deleted!"));
                break;
            }catch (Exception e){
                continue;
            }
        }
    }

    @Test
    public void testNewMessageReply() throws Exception{
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("#" + 1);
        FxRobot robot = new FxRobot();
        Label text = robot.lookup("#messageHeader").query();
        verifyThat(text, LabeledMatchers.hasText("(no header)"));
        Label text2 = robot.lookup("#messageText").query();
        verifyThat(text2, LabeledMatchers.hasText("Hello there my dear admin!"));
        clickOn("#reply");
        clickOn("#to").write("Super/178");
        clickOn("#messageBody").write("Hello my dear librarian");
        clickOn("#send");
        Text alert = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(alert, hasText("This user does not exist!"));
        clickOn("OK");
        clickOn("#to");
        for (int i=0; i<9; i++)
            type(KeyCode.BACK_SPACE);
        clickOn("#to").write("Super/123");
        clickOn("#send");
        alert = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(alert, hasText("Message sent successfully to Max Verstappen"));
        assertTrue(verifyAdded());
    }

    @Test
    public void testMarkAsUnread(){
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("#" + 0);
        clickOn("#markunread");
        clickOn("#back");
        FxRobot robot = new FxRobot();
        Label text = robot.lookup("#0").query();
        assertEquals(Color.DARKORCHID, text.getBackground().getFills().get(0).getFill());
    }
}
