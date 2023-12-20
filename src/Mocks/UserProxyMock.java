package Mocks;

import Main.Users.User;
import Main.Users.UserDb;

import java.util.ArrayList;

public class UserProxyMock implements UserDb {

    private ArrayList<User> dbUsers;

    public UserProxyMock(ArrayList<User> users){
        dbUsers = users;
    }

    @Override
    public void writeUsers(ArrayList<User> users) {
        dbUsers = users;
    }

    @Override
    public ArrayList<User> readUsers() {
        return dbUsers;
    }
}
