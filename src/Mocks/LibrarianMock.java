package Mocks;

import Main.Products.Bill;
import Main.Products.Book;
import Main.Users.Access;
import Main.Users.BillWriter;
import Main.Users.Librarian;
import Main.Utilis.CompDate;

import java.util.ArrayList;
import java.util.HashMap;

public class LibrarianMock extends Librarian {
    HashMap<Integer,BookModelMock> groupOfBooks;
    int[][] groupForBills;
    CompDate[] dates;
    boolean forFile;
    public LibrarianMock(String pathName, boolean forFile) throws Exception {
        super("Name","Surname","Usertame69.","Password69.","email@gmail.com","+355693074065",15,8,2003, "0000000", 25000, Access.FULL);
        writer = new BillWriter(pathName);
        this.forFile = forFile;
        groupOfBooks=new HashMap<>();
        groupOfBooks.put(1,new BookModelMock("Book1",1000));
        groupOfBooks.put(2,new BookModelMock("Book2",2000));
        groupOfBooks.put(3,new BookModelMock("Book3",3000));
        groupOfBooks.put(4,new BookModelMock("Book4",4000));
    }
    public LibrarianMock(String pathName) throws Exception {
        this(pathName,false);
    }
    public void translate(String workStr){
        String[] rows = workStr.split(" ");
        int[][] result = new int[rows.length/2][];
        CompDate[] deez = new CompDate[rows.length/2];
        for(int i=0;i<result.length;i++){
            result[i] = new int[rows[2*i].length()];
            for(int j=0;j<rows[2*i].length();j++){
                result[i][j] = rows[2*i].charAt(j) - 48;
            }
            String[] dateVars = rows[2*i+1].split("/");
            deez[i] = new CompDate(Integer.parseInt(dateVars[0]),Integer.parseInt(dateVars[1]),Integer.parseInt(dateVars[2]));
        }
        groupForBills = result;
        dates = deez;
    }

    @Override
    public ArrayList<Bill> readBills(){
        if(forFile) return super.readBills();
       ArrayList<Bill> result = new ArrayList<>();
       for(int i=0;i<groupForBills.length;i++){
           ArrayList<Book> list = new ArrayList<>();
           for(int el:groupForBills[i]){
               list.add(groupOfBooks.get(el));
           }
           Bill bill = new BillModelMock(list,dates[i]);
           result.add(bill);
       }
       return result;
    }

    public CompDate dateCon(String date) {
        String[] res = date.split("/");
        return new CompDate(Integer.parseInt(res[0]),Integer.parseInt(res[1]),Integer.parseInt(res[2]));
    }
    @Override
    public double createBill(ArrayList<Book> books) throws Exception{

        bills = readBills();
        Bill newBill = new Bill(books);
        //newBill.printFormat(billFolder + "/[Bill" + this.nrOfBills() + "].txt");
        bills.add(newBill);
        writeBills();

        return newBill.getTotalAmount();
    }
}
