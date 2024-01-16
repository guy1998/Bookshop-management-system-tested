package Main.Backend.Users;

import Main.Backend.Notification.Message;

import java.io.*;
import java.util.ArrayList;

public class MessageWriter implements Serializable{

    private final File msg;
    private ArrayList<Message> messages;

    public MessageWriter(String filePath){
        msg = new File(filePath);
        messages = new ArrayList<>();
        if(!msg.exists()) {
            messages.add(new Message());
            writeMessages(messages);
        }
        else
            readMessages();
    }
    public void writeMessages(ArrayList<Message> moreMsg) {
        try {
            FileOutputStream out = new FileOutputStream(msg);
            ObjectOutputStream obOut = new ObjectOutputStream(out);
            this.messages = moreMsg;
            obOut.writeObject(messages);
            obOut.close();
            out.close();
        }catch (FileNotFoundException e) {
            System.err.println("File not Found!!!");
        }catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<Message> readMessages(){
        try {
            FileInputStream in = new FileInputStream(msg);
            ObjectInputStream inOb = new ObjectInputStream(in);
            messages = (ArrayList<Message>) inOb.readObject();
            in.close();
            inOb.close();
        }catch (FileNotFoundException e) {
            System.err.println("File not Found!!!");
        } catch (ClassNotFoundException e) {
            System.err.println("Class not Found!!!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return messages;
    }


}
