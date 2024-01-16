package Main.Backend.Users;

import Main.Backend.Products.Bill;

import java.io.*;
import java.util.ArrayList;

public class BillWriter implements Serializable{

    private final File billDat;

    public BillWriter(String filePath){
        billDat = new File( filePath);
        if(!billDat.exists()){
            try {
                System.out.println(billDat.createNewFile());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            writeBills(new ArrayList<>());}
    }

    public void writeBills(ArrayList<Bill> bills) {
        try {
            FileOutputStream out = new FileOutputStream(billDat);
            ObjectOutputStream outOb = new ObjectOutputStream(out);
            outOb.writeObject(bills);
            outOb.close();
            out.close();
        }catch (FileNotFoundException e) {
            System.err.println("File not Found!!!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<Bill> readBills() {
        ArrayList<Bill> bills = null;
        try {
            FileInputStream in = new FileInputStream(billDat);
            ObjectInputStream inOb = new ObjectInputStream(in);
            bills = (ArrayList<Bill>) inOb.readObject();
            in.close();
            inOb.close();
        }catch (FileNotFoundException e) {
            System.err.println("File not Found!!!");
        } catch (ClassNotFoundException e) {
            System.err.println("Class not Found!!!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return bills;
    }
}
