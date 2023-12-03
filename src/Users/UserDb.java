package Users;

import java.util.ArrayList;

public interface UserDb {

    void writeUsers(ArrayList<User> users);
    ArrayList<User> readUsers();

}
