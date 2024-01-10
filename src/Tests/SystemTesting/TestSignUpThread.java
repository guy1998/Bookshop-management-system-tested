package Tests.SystemTesting;

import Main.GUI.SignUpPage;
import Main.Users.UserStack;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextMatchers.hasText;

public class TestSignUpThread extends ApplicationTest {

    @Override
    public void start(Stage stage){
        (new SignUpPage()).show(stage);
    }

    //helper method
    public void newUserDeletion(String username) throws Exception{
        (new UserStack()).deleteUser(username);
    }

    @Test
    public void testSignUpWrongProductKey(){
        clickOn("#keyField").write("invalid key");
        clickOn("#signUpButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("Please check and verify your product key before trying again"));
    }

    @Test
    public void testSignUpFieldsEmpty(){
        clickOn("#keyField").write("123456"); //Right product key
        clickOn("#signUpButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("All the fields should be filled!"));
    }

    @Test
    public void testSignUpWrongEmail(){
        //failed this
        clickOn("#nameField").write("Max");
        clickOn("#surnameField").write("Verstappen");
        clickOn("#emailField").write("max@wrong.com");
        clickOn("#phoneField").write("+355676105565");
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
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("Email is invalid"));
    }

    @Test
    public void testSignUpWrongPhone(){
        clickOn("#nameField").write("Max");
        clickOn("#surnameField").write("Verstappen");
        clickOn("#emailField").write("max@gmail.com");
        clickOn("#phoneField").write("+355606105565");
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
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("Phone number should be of format +3556[7-8-9]xxxxxxx"));
    }

    @Test
    public void testSignUpWrongUsername(){
        clickOn("#nameField").write("Max");
        clickOn("#surnameField").write("Verstappen");
        clickOn("#emailField").write("max@gmail.com");
        clickOn("#phoneField").write("+355696105565");
        clickOn("#birthday");
        for(int i=0; i<10; i++)
            type(KeyCode.BACK_SPACE);
        clickOn("#birthday").write("1/9/1999");
        clickOn("#usernameField").write("Max/123");
        clickOn("#passwordField").write("Champ/123");
        clickOn("#retype").write("Champ/123");
        clickOn("#keyField").write("123456");
        clickOn("#signUpButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("Username cannot contain your name or surname"));
    }

    @Test
    public void testSignUpWrongPassword(){
        clickOn("#nameField").write("Max");
        clickOn("#surnameField").write("Verstappen");
        clickOn("#emailField").write("max@gmail.com");
        clickOn("#phoneField").write("+355696105565");
        clickOn("#birthday");
        for(int i=0; i<10; i++)
            type(KeyCode.BACK_SPACE);
        clickOn("#birthday").write("1/9/1999");
        clickOn("#usernameField").write("Super/123");
        clickOn("#passwordField").write("Max/123");
        clickOn("#retype").write("Max/123");
        clickOn("#keyField").write("123456");
        clickOn("#signUpButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("Password too short!"));
    }

    @Test
    public void testSignUpWrongRetype(){
        //Failed this
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
        clickOn("#retype").write("Max/123");
        clickOn("#keyField").write("123456");
        clickOn("#signUpButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("Re-Type the password correctly!"));
    }

    @Test
    public void testSignUpSuccessfulSignUp() throws Exception{
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
        newUserDeletion("Super/123");
    }

}
