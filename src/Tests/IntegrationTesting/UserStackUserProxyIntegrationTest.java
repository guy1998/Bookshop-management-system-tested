package Tests.IntegrationTesting;

import Main.Exceptions.*;
import Main.Users.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserStackUserProxyIntegrationTest {

    @TempDir
    private static File tempFolder;

    private File tempFile;
    private UserStack users;
    private ArrayList<User> myUsers = new ArrayList<>();

    @BeforeEach
    public void createDataset() {
        try {
            myUsers.add(new Administrator("John", "Doe", "Guy_1989", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
            myUsers.add(new Librarian("Max", "Verstappen", "Ver_123", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002, "123-4356-5673", 2100, Access.FULL));
            myUsers.add(new Manager("Lewis", "Hamilton", "Ham_4404", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002, "123-4356-5673", 2000, Access.FULL));
            tempFile = new File(tempFolder, "testUser.dat");
            FileOutputStream out = new FileOutputStream(tempFile);
            ObjectOutputStream obOut = new ObjectOutputStream(out);
            obOut.writeObject(myUsers);
            obOut.close();
            out.close();
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }

    @AfterEach
    void cleanup() {
        File[] files = tempFolder.listFiles();
        if (files != null) {
            for (File f : files) {
                System.out.println(f.delete());
            }
        }
    }

    @AfterAll
    public static void deleteDirectory() {
        System.out.println(tempFolder.delete());
    }

    public ArrayList<User> auxiliaryReader(File file) throws Exception {
        FileInputStream in = new FileInputStream(file);
        ObjectInputStream inOb = new ObjectInputStream(in);
        ArrayList<User> new_users = (ArrayList<User>) inOb.readObject();
        in.close();
        inOb.close();
        return new_users;
    }

    @Test
    public void testIntegrationAddUserWriteUsers() throws Exception {
        User existent_user = new Administrator("John", "Doe", "Guy_1989", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002);
        User non_existent_user = new Administrator("John", "Doe", "Guy_1999", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002);
        users = new UserStack(new UserProxy(tempFile.getPath()));
        assertAll("Integration between addUser and writeUsers", () -> {
            Throwable exception = assertThrows(UserAlreadyExistsException.class, () -> users.addUser(existent_user));
            assertEquals("It seems that this user exists in the system.", exception.getMessage());
            ArrayList<User> new_users = auxiliaryReader(new File(tempFile.getPath()));
            assertEquals(new_users.size(), myUsers.size()); //making sure the file did not change
        }, () -> {
            users.addUser(non_existent_user);
            ArrayList<User> new_users = auxiliaryReader(new File(tempFile.getPath()));
            assertEquals(1, new_users.size() - myUsers.size());
            assertEquals(non_existent_user, new_users.get(new_users.size() - 1));
        });
    }

    @Test
    public void testIntegrationDeleteUserWriteUsers() throws Exception {
        User existent_user = new Administrator("John", "Doe", "Guy_1989", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002);
        User non_existent_user = new Administrator("John", "Doe", "Guy_1999", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002);
        users = new UserStack(new UserProxy(tempFile.getPath()));
        assertAll("Integration between deleteUser and writeUsers", () -> {
            Throwable exception = assertThrows(NonExistantUserException.class, () -> users.deleteUser(non_existent_user.getUsername()));
            assertEquals("This user does not exist!", exception.getMessage());
            ArrayList<User> new_users = auxiliaryReader(new File(tempFile.getPath()));
            assertEquals(new_users.size(), myUsers.size());
        }, () -> {
            users.deleteUser(existent_user.getUsername());
            ArrayList<User> new_users = auxiliaryReader(new File(tempFile.getPath()));
            assertEquals(-1, new_users.size() - myUsers.size());
            assertNotEquals(existent_user, new_users.get(0));
            assertNotEquals(existent_user, new_users.get(1));
        });
    }

    @ParameterizedTest
    @CsvSource({
            "'Very/123', 'New password cannot be the same as old password'",
            "'a', 'Password too short!'",
            "'Guy_1989', 'Password cannot be the same or contain the username'",
            "'John/123', 'Password should not contain the name or surname!'",
            "'Hello1234', 'Password must contain at least: a lowercase, an uppercase, a number and one of [/,_,.]!'",
    })
    public void testIntegrationModifyPasswordWriteUsersFirstScenario(String newPassword, String message) throws Exception {
        users = new UserStack(new UserProxy(tempFile.getPath()));
        Throwable exception = assertThrows(InvalidPasswordException.class, () -> users.modifyPassword(users.findUser("Guy_1989"), newPassword));
        assertEquals(message, exception.getMessage());
        ArrayList<User> new_users = auxiliaryReader(tempFile);
        assertEquals(new_users.get(0).getPassword(), myUsers.get(0).getPassword()); //I know the user i am changing is first
    }

    @Test
    public void testIntegrationModifyPasswordWriteUsersSecondScenario() throws Exception {
        users = new UserStack(new UserProxy(tempFile.getPath()));
        users.modifyPassword(users.findUser("Guy_1989"), "Hello/1234");
        ArrayList<User> new_users = auxiliaryReader(tempFile);
        assertNotEquals(new_users.get(0).getPassword(), myUsers.get(0).getPassword());
    }

    @ParameterizedTest
    @CsvSource({
            "'a', 'Username too short!'",
            "'John/1234', 'Username cannot contain your name or surname'",
            "'Hello1234', 'Username must contain at least: a lowercase, an uppercase, a number and one of [/,_,.]!'"
    })
    public void testIntegrationModifyUsernameWriteUsersFirstScenario(String newUsername, String message) throws Exception{
        users = new UserStack(new UserProxy(tempFile.getPath()));
        Throwable exception = assertThrows(InvalidUsernameException.class, () -> users.modifyUsername(users.findUser("Guy_1989"), newUsername));
        assertEquals(message, exception.getMessage());
        ArrayList<User> new_users = auxiliaryReader(tempFile);
        assertEquals(new_users.get(0).getUsername(), myUsers.get(0).getUsername());
    }

    @Test
    public void testIntegrationModifyUsernameWriteUsersSecondScenario() throws Exception {
        users = new UserStack(new UserProxy(tempFile.getPath()));
        users.modifyUsername(users.findUser("Guy_1989"), "Hello/1234");
        ArrayList<User> new_users = auxiliaryReader(tempFile);
        assertNotEquals(new_users.get(0).getUsername(), myUsers.get(0).getUsername());
    }

    @Test
    public void testIntegrationModifyEmailWriteUsers() throws Exception{
        users = new UserStack(new UserProxy(tempFile.getPath()));
        Throwable exception = assertThrows(InvalidEmail.class, ()->users.modifyEmail(users.findUser("Guy_1989"), "acifliku@kot.com"));
        assertEquals("Email is invalid", exception.getMessage());
        users.modifyEmail(users.findUser("Guy_1989"), "acifliku@gmail.com");
        ArrayList<User> new_users = auxiliaryReader(tempFile);
        assertEquals("acifliku@gmail.com", new_users.get(0).getEmail());
        assertNotEquals(myUsers.get(0).getEmail(), new_users.get(0).getEmail());
    }

    @Test
    public void testIntegrationModifyPhoneWriteUsers() throws Exception{
        users = new UserStack(new UserProxy(tempFile.getPath()));
        Throwable exception = assertThrows(NonExistantUserException.class, ()->users.modifyName(users.findUser("Guy_1989"), "+041356901922"));
        assertEquals("Phone number should be of format +3556[7-8-9]xxxxxxx", exception.getMessage());
        users.modifyPhone(users.findUser("Guy_1989"), "+355676105567");
        ArrayList<User> new_users = auxiliaryReader(tempFile);
        assertEquals("+355676105567", new_users.get(0).getPhone());
        assertNotEquals(myUsers.get(0).getPhone(), new_users.get(0).getPhone());
    }

    @Test
    public void testIntegrationModifyNameWriteUsers() throws Exception{
        users = new UserStack(new UserProxy(tempFile.getPath()));
        users.modifyName(users.findUser("Guy_1989"), "Joe");
        ArrayList<User> new_users = auxiliaryReader(tempFile);
        assertEquals("Joe", new_users.get(0).getName());
        assertNotEquals(myUsers.get(0).getName(), new_users.get(0).getName());
    }

    @Test
    public void testIntegrationModifySurnameWriteUsers() throws Exception {
        users = new UserStack(new UserProxy(tempFile.getPath()));
        users.modifySurname(users.findUser("Guy_1989"), "Doberman");
        ArrayList<User> new_users = auxiliaryReader(tempFile);
        assertEquals("Doberman", new_users.get(0).getSurname());
        assertNotEquals(myUsers.get(0).getSurname(), new_users.get(0).getSurname());
    }

    @Test
    public void testIntegrationModifyPermissionWriteUsers() throws Exception{
        users = new UserStack(new UserProxy(tempFile.getPath()));
        users.modifyPermission(users.findUser("Ver_123"), Access.PARTIAL);
        ArrayList<User> new_users = auxiliaryReader(tempFile);
        assertEquals(Access.PARTIAL, ((Employee)new_users.get(1)).getPermission());
        assertNotEquals(((Employee)myUsers.get(1)).getPermission(), ((Employee)new_users.get(1)).getPermission());
    }

    @Test
    public void testIntegrationModifySSNWriteUsers() throws Exception {
        users = new UserStack(new UserProxy(tempFile.getPath()));
        users.modifySSN(users.findUser("Ver_123"), "123-4356-5674");
        ArrayList<User> new_users = auxiliaryReader(tempFile);
        assertEquals("123-4356-5674", ((Employee)new_users.get(1)).getSSN());
        assertNotEquals(((Employee)myUsers.get(1)).getSSN(), ((Employee)new_users.get(1)).getSSN());
    }

    @Test
    public void testIntegrationModifySalaryWriteUsers() throws Exception{
        users = new UserStack(new UserProxy(tempFile.getPath()));
        users.modifySalary(users.findUser("Ver_123"), 2000.0);
        ArrayList<User> new_users = auxiliaryReader(tempFile);
        assertEquals(2000.0, ((Employee)new_users.get(1)).getSalary());
        assertNotEquals(((Employee)myUsers.get(1)).getSalary(), ((Employee)new_users.get(1)).getSalary());
    }

}
