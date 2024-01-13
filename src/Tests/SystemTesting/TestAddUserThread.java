package Tests.SystemTesting;


import Main.GUI.LoginPage;
import Main.Users.Administrator;
import Main.Users.UserStack;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import java.util.HashMap;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextMatchers.hasText;

public class TestAddUserThread extends ApplicationTest{

    private static boolean userAdded = false;
    private static UserStack users = new UserStack();
    @Override
    public void start(Stage stage){
        (new LoginPage()).show(stage);
    }

    @BeforeAll
    public static void createUserInDb() throws Exception{
        Administrator admin = new Administrator("Aldrin", "Cifliku", "Guy_1989", "Juve/123", "acifliku@gmail.com", "+355676105565", 17, 12, 2002);
        users.addUser(admin);
    }

    @AfterAll
    public static void cleanDatabase() throws Exception{
        users.deleteUser("Guy_1989");
        users = new UserStack();
        if(userAdded)
            users.deleteUser("New_1234");
    }

    public void addingInfo(String skipped){
        HashMap<String, String> fields = new HashMap<>();
        fields.put("#nameField", "Max");
        fields.put("#surnameField", "Verstappen");
        fields.put("#emailField", "max@gmail.com");
        fields.put("#phoneField", "+355676105565");
        fields.put("#usernameField", "New_1234");
        fields.put("#passwordField", "Red/1234");
        fields.put("#ssnField", "123-2342-029");
        fields.put("#statusBox", "LIBRARIAN");
        fields.put("#accessBox", "FULL");
        fields.put("#salaryField", "1500");
        for(String key: fields.keySet()){
            if(key.equals(skipped))
                continue;
            else if(key.equals("#statusBox") || key.equals("#accessBox"))
                clickOn(key).clickOn(fields.get(key));
            else
                clickOn(key).write(fields.get(key));
        }
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
    public void testFailedAddUserEmptyFields(){
        //Discovered problem here
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        FxRobot robot = new FxRobot();
        robot.clickOn("#addUser");
        robot.clickOn("#createButton");
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("Please complete all the fields!"));
    }

    @Test
    public void testFailedWrongUsernameAdded(){
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("#addUser");
        addingInfo("#usernameField");
        clickOn("#usernameField").write("WrongUser");
        clickOn("#createButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("Invalid username entered"));
    }

    @Test
    public void testFailedWrongPasswordAdded(){
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("#addUser");
        addingInfo("#passwordField");
        clickOn("#passwordField").write("H/1");
        clickOn("#createButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("Password too short!"));
    }

    @Test
    public void testFailedWrongSsnAdded(){
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("#addUser");
        addingInfo("#ssnField");
        clickOn("#ssnField").write("1234-2311-101");
        clickOn("#createButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("The SSN should be of the form xxx-xxxx-xxx where x-es can be..."));
    }

    @Test
    public void testUserAddedSuccessfully(){
        clickOn("#usernameField").write("Guy_1989");
        clickOn("#passwordField").write("Juve/123");
        clickOn("#loginButton");
        clickOn("#addUser");
        addingInfo("");
        clickOn("#createButton");
        FxRobot robot = new FxRobot();
        Text text = robot.lookup(".dialog-pane .content .text").query();
        verifyThat(text, hasText("New user created successfully"));
        userAdded = true;
    }

}
