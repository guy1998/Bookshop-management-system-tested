package Tests.SystemTesting;

import Main.GUI.LoginPage;
import Main.GUI.SignUpPage;
import Main.Users.Administrator;
import Main.Users.User;
import Main.Users.UserStack;
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

public class TestChangeUserDataAfterSignUp extends ApplicationTest {
    private static UserStack users = new UserStack();

    @Override
    public void start(Stage primaryStage){
        (new SignUpPage()).show(primaryStage);
    }

    @AfterAll
    public static void deleteUser() throws Exception{
        File messages = new File("Database/Messages/" + users.findUser("Super/123").getUserId() +".msg");
        users.deleteUser("Super/123");
        System.out.println(messages.delete());
    }

    //This thread is concerned more with how the transition occurs from sign up to changing data so it will not be as
    //thorough as the one where the user already exists
    //Testing only one fail scenario since it has already been tested
    @Test
    public void testThreadFailsInSignUp(){
        clickOn("#keyField").write("invalid key");
        clickOn("#signUpButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("Please check and verify your product key before trying again"));
    }

    //avoiding tests about data validity since they have already been tested
    @Test
    public void userSignsUpAndGoesToChangeData() throws Exception{
        clickOn("#nameField").write("Max");
        clickOn("#surnameField").write("Verstappen");
        clickOn("#emailField").write("max@gmail.com");
        clickOn("#phoneField").write("+355696105565");
        clickOn("#birthday");
        for(int i=0; i<10; i++)
            type(KeyCode.BACK_SPACE);
        clickOn("#birthday").write("1/9/1999");
        clickOn("#usernameField").write("Super/123");
        clickOn("#passwordField").write("Champ/123");
        clickOn("#retype").write("Champ/123");
        clickOn("#keyField").write("123456");
        clickOn("#signUpButton");
        FxRobot robot = new FxRobot();
        Label text = robot.lookup("#hello").query();
        verifyThat(text, LabeledMatchers.hasText("Hello Max!"));
        users = new UserStack();
        User temp = users.findUser("Super/123"); //it will throw an error if db is not updated
        clickOn("#profile");
        clickOn("#nameButton");
        clickOn("#nameField").write("Max12");
        clickOn("#nameConfirm");
        Label total = robot.lookup("#name").query();
        verifyThat(total, LabeledMatchers.hasText("Name: Max12"));
        users = new UserStack(); //simply updates the version of db
        User user = users.findUser("Super/123");
        assertEquals("Max12", user.getName());
    }
}
