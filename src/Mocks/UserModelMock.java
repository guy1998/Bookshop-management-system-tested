package Mocks;

import Main.Backend.Users.Status;
import Main.Backend.Users.User;

public class UserModelMock extends User {
    public UserModelMock(String name, String surname, String username, String password, String email, String phone, int day, int month, int year) throws Exception {
        super(name, surname, username, password, email, phone, day, month, year, Status.LIBRARIAN);
    }
    public static User createUser(User user) throws Exception {
        return new UserModelMock(user.getName(), user.getSurname(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(), 15,8,2023);
    }
}
