package Mocks;

import Products.Bill;
import Products.Book;
import Users.Access;
import Users.Librarian;
import Utilis.CompDate;

import java.util.ArrayList;
import java.util.HashMap;

public class LibrarianMock extends Librarian {
    HashMap<Integer,BookModelMock> groupOfBooks;
    int[][] groupForBills;
    CompDate[] dates;
    public LibrarianMock() throws Exception {
        super("Name","Surname","Usertame69.","Password69.","email@gmail.com","+355693074065",15,8,2003, "0000000", 25000, Access.FULL);
        groupOfBooks=new HashMap<>();
        groupOfBooks.put(1,new BookModelMock("Book1",1000));
        groupOfBooks.put(2,new BookModelMock("Book2",2000));
        groupOfBooks.put(3,new BookModelMock("Book3",3000));
        groupOfBooks.put(4,new BookModelMock("Book4",4000));
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
}
