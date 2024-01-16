package Tests.SystemTesting;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import Main.GUI.LoginPage;
import Main.GUI.ManagerView;
import Main.Users.*;
import javafx.application.Application;
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

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextMatchers.hasText;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChangeUserDataThreadManager extends ApplicationTest {
    private static UserStack users = new UserStack();

    @Override
    public void start(Stage primaryStage){
        (new LoginPage()).show(primaryStage);
    }

    @BeforeAll
    public static void addUser() throws Exception{
        Manager manager = new Manager("Aldrin", "Cifliku", "Guy_1989", "Juve/123", "acifliku@gmail.com", "+355676105565", 17, 12, 2002, "123-1234-918", 1500.0, Access.FULL);
        users.addUser(manager);
    }
    @AfterAll
    public static void deleteUser() throws Exception{
        File messages = new File("Database/Messages/" + users.findUser("Guy_1989").getUserId() +".msg");
        users.deleteUser("Guy_1989");
        System.out.println(messages.delete());
    }

    //This two tests simply test the outcome of logging in with wrong info
    @Test
    @Order(0)
    public void testFailedLoginUserNotExist(){
        clickOn("#usernameField").write("WrongUser");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("This user does not exist!"));
    }

    @Test
    @Order(1)
    public void testFailedPasswordNoMatch(){
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/124");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("The password entered does not match"));
    }

    //This test checks all scenarios where the editing should be refused due to wrong info entered.
    @Test
    @Order(2)
    public void testWrongEditInfo() throws Exception{
        //successful login here
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("OK");
        FxRobot robot = new FxRobot();
        clickOn("#profile"); //going to profile menu or view
        //testing invalid username
        clickOn("#usernameButton");
        clickOn("#usernameField").write("Aldrin/123");
        clickOn("#usernameConfirm");
        Text usernamePrompt = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(usernamePrompt, hasText("Username cannot contain your name or surname"));
        clickOn("OK");
        //testing invalid email
        clickOn("#emailButton");
        clickOn("#emailField").write("email@invalid.com");
        clickOn("#emailConfirm");
        Text emailPrompt = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(emailPrompt, hasText("Email is invalid"));
        clickOn("OK");
        //testing invalid phone number
        clickOn("#phoneButton");
        clickOn("#phoneField").write("+3333333");
        clickOn("#phoneConfirm");
        Text phonePrompt = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(phonePrompt, hasText("Phone number should be of format +3556[7-8-9]xxxxxxx"));
        clickOn("OK");
        //checking that no changes were done underneath
        users = new UserStack();
        User user = users.findUser("Guy_1989");//this would throw an error if changes have occurred
        assertEquals("acifliku@gmail.com", user.getEmail());
        assertEquals("+355676105565", user.getPhone());
    }

    @Test
    @Order(4)
    public void testChangePassword() throws Exception{
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("OK");
        FxRobot robot = new FxRobot();
        clickOn("#profile");
        clickOn("#passwordButton");
        //Test the case where the old password is entered wrong
        clickOn("#passwordField").write("Juve/124");
        clickOn("#npField").write("123");
        clickOn("#passwordConfirm");
        Text passwordPrompt = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(passwordPrompt, hasText("Your current password does not match"));
        clickOn("OK");
        //Test the case where the new password is invalid
        clickOn("#passwordField");
        type(KeyCode.BACK_SPACE);
        clickOn("#passwordField").write("Juve/123");
        clickOn("#passwordConfirm");
        passwordPrompt = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(passwordPrompt, hasText("Password too short!"));
        clickOn("OK");
        //verify no changes underneath
        User user = users.findUser("Guy_1989");
        assertEquals("Juve/123", user.getPassword());
        //Test the case where password is changed successfully
        clickOn("#npField");
        for (int i=0; i<3; i++)
            type(KeyCode.BACK_SPACE);
        clickOn("#npField").write("Juve/1234");
        clickOn("#passwordConfirm");
        passwordPrompt = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(passwordPrompt, hasText("Password changed successfully"));
        clickOn("OK");
        //verify change
        users = new UserStack();
        user = users.findUser("Guy_1989");
        assertEquals("Juve/1234", user.getPassword());
    }

    //This is supposed to test if the text in the view is changed as well as the db
    @Test
    @Order(3)
    public void changeInfoAndView() throws Exception{
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("OK");
        clickOn("#profile");
        FxRobot robot = new FxRobot();
        clickOn("#nameButton");
        clickOn("#nameField").write("Aldrin12");
        clickOn("#nameConfirm");
        Label total = robot.lookup("#name").query();
        verifyThat(total, LabeledMatchers.hasText("Name: Aldrin12"));
        users = new UserStack(); //simply updates the version of db
        User user = users.findUser("Guy_1989");
        assertEquals("Aldrin12", user.getName());
    }
}
