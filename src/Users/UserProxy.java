package Users;

import java.io.*;
import java.util.ArrayList;

public class UserProxy implements UserDb{

    private final File userList;

    public UserProxy(){
        userList = new File("user.dat");
        if(!userList.exists())
			writeUsers(new ArrayList<User>());
    }

    public void writeUsers(ArrayList<User> users) {

        try{
            FileOutputStream out = new FileOutputStream(userList);
            ObjectOutputStream outOb = new ObjectOutputStream(out);
            outOb.writeObject(users);
            outOb.close();
            out.close();
        }catch (FileNotFoundException e) {
            System.err.println("File not Found!!!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public ArrayList<User> readUsers() {

        ArrayList<User> users = new ArrayList<>();
        try {
            FileInputStream in = new FileInputStream(userList);
            ObjectInputStream inOb = new ObjectInputStream(in);
            users = (ArrayList<User>) inOb.readObject();
            setProperties(users);
            in.close();
            inOb.close();
        }catch (FileNotFoundException e) {
            System.err.println("File not Found!!!");
        } catch (ClassNotFoundException e) {
            System.err.println("Class not Found!!!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


        return users;
    }

    public void setProperties(ArrayList<User> users) {
        for(User u:users) {
            u.setEmailProperty();
            u.setNameProperty();
            u.setPhoneProperty();
            u.setSurnameProperty();
            u.setUsernameProperty();
        }
    }

}
