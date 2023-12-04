package Tests;

import Exceptions.*;
import Mocks.UserProxyMock;
import Users.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;

public class TestUserStack {

    private UserStack users;
    private ArrayList<User> myUsers = new ArrayList<>();
    @BeforeEach
    public void createDataset(){
        users = new UserStack(new UserProxyMock(myUsers));
        try{
            users.addUser(new Administrator("John", "Doe", "Guy_1989", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
            users.addUser(new Librarian("Max", "Verstappen", "Ver_123", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002, "123-4356-5673", 2000, Access.FULL));
            users.addUser(new Manager("Lewis", "Hamilton", "Ham_4404", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002, "123-4356-5673", 2000, Access.FULL));
        }catch(Exception e){
            System.out.println("Something went wrong!");
        }
    }

    @Test
    public void testFindUserByUsername(){
        assertAll("Finding by username", ()->{
            User expected = new Librarian("Max", "Verstappen", "Ver_123", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002, "123-4356-5673", 2000, Access.FULL);
            assertEquals(expected, users.findUser("Ver_123"));
        }, ()->{
            Throwable exception = assertThrows(NonExistantUserException.class, ()->users.findUser("Checo_123"));
            assertEquals("This user does not exist!", exception.getMessage());
        });
    }

    @Test
    public void testFindUserByReference(){
        assertAll("Finding by reference", ()->{
            User expected = new Librarian("Max", "Verstappen", "Ver_123", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002, "123-4356-5673", 2000, Access.FULL);
            assertEquals(expected, users.findUser(expected));
        }, ()->{
            User misleading = new Librarian("Max", "Verstappen", "Checo_123", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002, "123-4356-5673", 2000, Access.FULL);
            assertNull(users.findUser(misleading));
        });
    }

    @Test
    public void testFilterByStatusAllTheSame(){
        ArrayList<User> expected = new ArrayList<>();
        try{
            users.addUser(new Administrator("John", "Doe", "Guy_1992", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
            users.addUser(new Administrator("John", "Doe", "Guy_1990", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
            users.addUser(new Administrator("John", "Doe", "Guy_1991", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
            users.deleteUser("Ver_123");
            users.deleteUser("Ham_4404");
            expected.add(new Administrator("John", "Doe", "Guy_1989", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
            expected.add(new Administrator("John", "Doe", "Guy_1992", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
            expected.add(new Administrator("John", "Doe", "Guy_1990", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
            expected.add(new Administrator("John", "Doe", "Guy_1991", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
        }catch (Exception e){
            System.out.println("Something went wrong");
        }
        assertEquals(expected, users.filterByStatus(Status.ADMINISTRATOR));
    }

    @Test
    public void testFilterByStatusAllDifferent(){
        ArrayList<User> expected = new ArrayList<>();
        try{
            expected.add(new Librarian("Max", "Verstappen", "Ver_123", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002, "123-4356-5673", 2000, Access.FULL));
        }catch (Exception e){
            System.out.println("Something went wrong!");
        }
        assertEquals(expected, users.filterByStatus(Status.LIBRARIAN));
    }

    @Test
    public void testFilterByStatusAllDifferentNoResult(){
        ArrayList<User> expected = new ArrayList<>();
        try{
            users.deleteUser("Ver_123");
        }catch (Exception e){
            System.out.println("Something went wrong!");
        }
        assertEquals(expected, users.filterByStatus(Status.LIBRARIAN));
    }

    @Test
    public  void testFilterByStatusAllSameNoResult(){
        ArrayList<User> expected = new ArrayList<>();
        try{
            users.addUser(new Administrator("John", "Doe", "Guy_1992", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
            users.addUser(new Administrator("John", "Doe", "Guy_1990", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
            users.addUser(new Administrator("John", "Doe", "Guy_1991", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
            users.deleteUser("Ver_123");
            users.deleteUser("Ham_4404");
        }catch (Exception e){
            System.out.println("Something went wrong");
        }
        assertEquals(expected, users.filterByStatus(Status.LIBRARIAN));
    }

    @Test
    public void testAddUserErrorHandling(){
        Throwable exception = assertThrows(UserAlreadyExistsException.class, ()->{
            users.addUser(new Administrator("John", "Doe", "Guy_1989", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002));
        });
        assertEquals("It seems that this user exists in the system.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            //Testing based on error messages as equivalence classes
            "'Very/123', 'New password cannot be the same as old password'",
            "'a', 'Password too short!'",
            "'Guy_1989', 'Password cannot be the same or contain the username'",
            "'John/123', 'Password should not contain the name or surname!'",
            "'Hello1234', 'Password must contain at least: a lowercase, an uppercase, a number and one of [/,_,.]!'"
    })
    public void testModifyPassword(String password, String message){
        try{
            User temp = new Administrator("John", "Doe", "Guy_1989", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002);
            Throwable exception = assertThrows(InvalidPasswordException.class, ()->users.modifyPassword(temp, password));
            assertEquals(message, exception.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvSource({
        "'a', 'Username too short!'",
        "'John/1234', 'Username cannot contain your name or surname'",
        "'Hello1234', 'Username must contain at least: a lowercase, an uppercase, a number and one of [/,_,.]!'"
    })
    public void testModifyUsername(String username, String message){
        try{
            User temp = new Administrator("John", "Doe", "Guy_1989", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002);
            Throwable exception = assertThrows(InvalidUsernameException.class, ()->users.modifyUsername(temp, username));
            assertEquals(message, exception.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testModifyPhone(){
        try {
            User temp = new Administrator("John", "Doe", "Guy_1989", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002);
            Throwable exception = assertThrows(InvalidPhoneNumberException.class, ()->users.modifyPhone(temp, "+355796105565"));
            assertEquals("Phone number should be of format +3556[7-8-9]xxxxxxx", exception.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testModifyEmail(){
        try {
            User temp = new Administrator("John", "Doe", "Guy_1989", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002);
            Throwable exception = assertThrows(InvalidEmail.class, ()->users.modifyEmail(temp, "acifliku@outlook.com"));
            assertEquals("Email is invalid", exception.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
