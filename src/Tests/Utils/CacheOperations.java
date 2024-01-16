package Tests.Utils;

import java.io.File;

public class CacheOperations {

    public static void clearCache(){
        clearMessages();
        clearBinaryBill();
        clearTextBill();
        clearOthers();
    }

    private static void clearOthers(){
        File users = new File("Database/user.dat");
        File products = new File("Database/products.dat");
        File transactions = new File("Database/transactions");
        if(users.exists())
            users.delete();
        if(products.exists())
            products.delete();
        if (transactions.exists())
            transactions.delete();
    }

    private static void clearTextBill(){
        File textBillFolder = new File("Database/Bills/TextBill");
        if (textBillFolder.exists()) {
            for (File f : textBillFolder.listFiles())
                f.delete();
        }
    }

    private static void clearBinaryBill(){
        File  binaryBillFolder = new File("Database/Bills/BinaryBill");
        if (binaryBillFolder.exists()) {
            for (File f : binaryBillFolder.listFiles())
                f.delete();
        }
    }

    private static void clearMessages(){
        File messagesFolder = new File("Database/Messages");
        if (messagesFolder.exists()) {
            for (File f : messagesFolder.listFiles())
                f.delete();
        }
    }

}
