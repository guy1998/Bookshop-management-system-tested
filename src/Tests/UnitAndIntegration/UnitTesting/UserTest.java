package Tests.UnitAndIntegration.UnitTesting;

import Main.Backend.Exceptions.InvalidEmail;
import Main.Backend.Exceptions.InvalidPasswordException;
import Main.Backend.Exceptions.InvalidPhoneNumberException;
import Main.Backend.Exceptions.InvalidUsernameException;
import Main.Backend.Users.User;
import Mocks.UserModelMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;
    @BeforeEach
    void setUp(){
        try {
            user = new UserModelMock("Name","Surname","Usertame69.","Password69.","email@gmail.com","+355693074065",15,8,2003);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void userCreationValid(){
        assertDoesNotThrow(() -> UserModelMock.createUser(user));
    }
    @Test
    void userCreationEmptyNameTest(){
        user.setName("");
        assertThrows(InvalidUsernameException.class,() -> UserModelMock.createUser(user),"All the fields should be filled: ");
    }
    @Test
    void userCreationEmptySurnameTest(){
        user.setSurname("");
        assertThrows(InvalidUsernameException.class,() -> UserModelMock.createUser(user));
    }
    @Test
    void userCreationEmptyUsernameTest(){
        user.setUserName("");
        assertThrows(InvalidUsernameException.class,() -> UserModelMock.createUser(user));
    }
    @Test
    void userCreationEmptyPasswordTest(){
        user.setPassword("");
        assertThrows(InvalidUsernameException.class,() -> UserModelMock.createUser(user));
    }
    @ParameterizedTest
    @CsvSource({
            "Name",
            "Surname"
    })
    void userCreationUsernameContainsTest(String userName){
        user.setUserName(userName);
        assertThrows(InvalidUsernameException.class,() -> UserModelMock.createUser(user),"Username cannot contain your name or surname");
    }
    @Test
    void userCreationPasswordLengthTest(){
        user.setPassword("passwor");
        assertThrows(InvalidPasswordException.class,() -> UserModelMock.createUser(user),"Password too short!");
    }
    @Test
    void userCreationPasswordContainsTest(){
        user.setPassword(user.getUsername());
        assertThrows(InvalidPasswordException.class,() -> UserModelMock.createUser(user),"Password too short!");
    }
    @ParameterizedTest
    @CsvSource({
            "Usertame69",
    })
    void userCreationUsernameRegexInvalidTest(String userName){
        user.setUserName(userName);
        assertThrows(InvalidUsernameException.class,() -> UserModelMock.createUser(user),"Username must contain at least: a lowercase, an uppercase, a number and one of [/,_,.]!");

    }
    @ParameterizedTest
    @CsvSource({
            "Usertame69.",
    })
    void userCreationUsernameRegexValidTest(String userName){
        user.setUserName(userName);
        assertDoesNotThrow(() -> UserModelMock.createUser(user));
    }
    @ParameterizedTest
    @CsvSource({
            "Password.",
    })
    void userCreationPasswordRegexInvalidTest(String password){
        user.setPassword(password);
        assertThrows(InvalidPasswordException.class,() -> UserModelMock.createUser(user),"Password must contain at least: a lowercase, an uppercase, a number and one of [/,_,.]!");
    }
    @ParameterizedTest
    @CsvSource({
            "Password69.",
    })
    void userCreationPasswordRegexValidTest(String password){
        user.setPassword(password);
        assertDoesNotThrow(() -> UserModelMock.createUser(user));
    }
    @ParameterizedTest
    @CsvSource({
            "+1555797",
    })
    void userCreationPhoneRegexInvalidTest(String phone){
        user.setPhone(phone);
        assertThrows(InvalidPhoneNumberException.class,() -> UserModelMock.createUser(user));
    }
    @ParameterizedTest
    @CsvSource({
            "+355693074065",
    })
    void userCreationPhoneRegexValidTest(String phone){
        user.setPhone(phone);
        assertDoesNotThrow(() -> UserModelMock.createUser(user));
    }
    @ParameterizedTest
    @CsvSource({
            "name@live.com",
    })
    void userCreationEmailRegexInvalidTest(String email){
        user.setEmail(email);
        assertThrows(InvalidEmail.class,() -> UserModelMock.createUser(user));
    }
    @ParameterizedTest
    @CsvSource({
            "name@gmail.com",
    })
    void userCreationEmailRegexValidTest(String email){
        user.setEmail(email);
        assertDoesNotThrow(() -> UserModelMock.createUser(user));
    }
    @Test
    void testEqualsValid(){
        User user1,user2;
        try {
            user1 = UserModelMock.createUser(user);
            user.setName("0k");
            user2 = UserModelMock.createUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(user1, user2);
    }
    @Test
    void testEqualsInvalid(){
//        User user1,user2;
//        Object obj = new HashSet<Exception>();
//        try {
//            user1 = UserModelMock.createUser(user);
//            user.setName("0k");
//            user2 = UserModelMock.createUser(user);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        assertNotEquals(user1, user2);
//        assertNotEquals(user1, obj);
    }
}