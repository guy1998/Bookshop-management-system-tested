package Tests.UnitTesting;

import Main.Notification.Message;
import Main.Users.Administrator;
import Main.Users.MessageWriter;
import Main.Users.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;

public class TestMessageWriter {

    @TempDir
    private static File tempFolder;
    private final User testUser = new Administrator("John", "Doe", "Guy_1989", "Very/123", "acifliku6@gmail.com", "+355676105565", 17, 12, 2002);

    public TestMessageWriter() throws Exception {
    }

    @AfterEach
    void cleanup() {
        File[] files = tempFolder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
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
    public void testReadMessagesNonExistentFile(){
        MessageWriter testWriter = new MessageWriter(tempFolder.getPath() + "Obviously non existent path");
        ArrayList<Message> messages = testWriter.readMessages();
        assertEquals(1, messages.size());
        assertTrue((new File(tempFolder.getPath() + "Obviously non existent path")).exists());
    }

    @Test
    public void testReadMessagesEmptyFile(){
        File tempFile = new File(tempFolder, "testMessages.dat");
        MessageWriter testWriter = new MessageWriter(tempFile.getPath());
        ArrayList<Message> messages = testWriter.readMessages();
        assertEquals(1, messages.size());
    }

    @Test
    public void testReadMessagesNonEmptyFile() throws IOException {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(testUser, "Message for test 1", "This message was sent as a test number 1"));
        messages.add(new Message(testUser, "Message for test 2", "This message was sent as a test number 2"));
        File tempFile = new File(tempFolder, "testMessages.dat");
        FileOutputStream out = new FileOutputStream(tempFile);
        ObjectOutputStream obOut = new ObjectOutputStream(out);
        obOut.writeObject(messages);
        obOut.close();
        out.close();
        MessageWriter testWriter = new MessageWriter(tempFile.getPath());
        ArrayList<Message> messagesRead = testWriter.readMessages();
        assertEquals(messages.size(), messagesRead.size());
        assertEquals(messages.get(0).getHeader(), messagesRead.get(0).getHeader());
        assertEquals(messages.get(0).getText(), messagesRead.get(0).getText());
        assertEquals(messages.get(1).getHeader(), messagesRead.get(1).getHeader());
        assertEquals(messages.get(1).getText(), messagesRead.get(1).getText());
    }

    @Test
    public void testWriteMessagesParam() throws IOException {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(testUser, "Message for test 1", "This message was sent as a test number 1"));
        messages.add(new Message(testUser, "Message for test 2", "This message was sent as a test number 2"));
        File tempFile = new File(tempFolder, "testMessages.dat");
        FileOutputStream out = new FileOutputStream(tempFile);
        ObjectOutputStream obOut = new ObjectOutputStream(out);
        obOut.writeObject(messages);
        obOut.close();
        out.close();
        ArrayList<Message> alteredMessages = new ArrayList<>();
        alteredMessages.add(new Message(testUser, "Altered message", "This message was sent as a test with an altered message"));
        MessageWriter testWriter = new MessageWriter(tempFile.getPath());
        testWriter.writeMessages(alteredMessages);
        assertNotEquals(messages.size(), testWriter.readMessages().size());
        assertNotEquals(messages.get(0).getHeader(), testWriter.readMessages().get(0).getHeader());
        assertNotEquals(messages.get(0).getText(), testWriter.readMessages().get(0).getText());
    }

}
