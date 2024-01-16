package Tests.UnitAndIntegration.UnitTesting;

import Main.Backend.Users.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestUserProxy {
    @TempDir
    private static File tempFolder;


    @AfterEach
    void cleanup() {
        File[] files = tempFolder.listFiles();
        if(files!=null) {
            for(File f: files) {
                System.out.println(f.delete());
            }
        }
    }

    @AfterAll
    public static void deleteDirectory(){
        System.out.println(tempFolder.delete());
    }

    @Test
    public void testReadUsersNonExistentFile(){
        UserProxy userProxy = new UserProxy(tempFolder.getPath() + "Obviously non existent path");
        ArrayList<User> users = userProxy.readUsers();
        Assertions.assertEquals(0, users.size());
    }

    @Test
    public void testReadUsersEmptyFile(){
        File tempFile = new File(tempFolder, "testUsers.dat");
        UserProxy userProxy = new UserProxy(tempFile.getPath());
        ArrayList<User> users = userProxy.readUsers();
        Assertions.assertEquals(0, users.size());
    }

    @Test
    public void testReadUsersNonEmptyFile() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        users.add(new Manager("Charles", "Leclerc", "Ferrari16.", "Champion24.", "chleclerc@gmail.com", "+355693074065", 15, 8, 2023, "409-52-2002", 1200, Access.PARTIAL));
        File tempFile = new File(tempFolder, "testUsers.dat");
        FileOutputStream out = new FileOutputStream(tempFile);
        ObjectOutputStream obOut = new ObjectOutputStream(out);
        obOut.writeObject(users);
        obOut.close();
        out.close();
        UserProxy userProxy = new UserProxy(tempFile.getPath());
        ArrayList<User> usersRead = userProxy.readUsers();
        Assertions.assertEquals(users.size(), usersRead.size());
        Assertions.assertEquals(users.get(0).getUsername(), usersRead.get(0).getUsername());

    }

    @Test
    public void testWriteUsers() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        users.add(new Manager("Charles", "Leclerc", "Ferrari16.", "Champion24.", "chleclerc@gmail.com", "+355693074065", 15, 8, 2023, "409-52-2002", 1200, Access.PARTIAL));
        users.add(new Manager("Charles", "Leclerc", "fERRARI16_.", "Champion24.", "chleclerc@gmail.com", "+355693074065", 15, 8, 2023, "409-52-2002", 1200, Access.PARTIAL));
        File tempFile = new File(tempFolder, "testManager.dat");
        FileOutputStream out = new FileOutputStream(tempFile);
        ObjectOutputStream obOut = new ObjectOutputStream(out);
        obOut.writeObject(users);
        obOut.close();
        out.close();
        ArrayList<User> alteredUsers = new ArrayList<>();
        alteredUsers.add(new Manager("Charles", "Leclerc", "Altered123.", "Champion24.", "chleclerc@gmail.com", "+355693074065", 15, 8, 2023, "409-52-2002", 1200, Access.PARTIAL));
        UserProxy userProxy = new UserProxy(tempFile.getPath());
        userProxy.writeUsers(alteredUsers);
        assertNotEquals(users.size(), userProxy.readUsers().size());
        Assertions.assertNotEquals(users.get(0).getUsername(), alteredUsers.get(0).getUsername());
    }

}
