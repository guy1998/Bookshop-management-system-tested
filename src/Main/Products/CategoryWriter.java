package Main.Products;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

public class CategoryWriter {

    private final File file;
    public CategoryWriter(String filePath){
        file = new File(filePath);
    }
    public boolean categoryExists(String category) {

        Scanner input = null;
        try {
            input = new Scanner(file);
        }catch(FileNotFoundException e) {
            System.out.println("Categories is not a file");
        }

        while(input != null && input.hasNext()) {
            if(input.next().equals(category))
                return true;
        }

        return false;
    }

    public void addCategory(String newCategory) {
        FileOutputStream out;
        try {
            out = new FileOutputStream(file, true);
            out.write(("\n"+newCategory).getBytes(), 0, ("\n"+newCategory).length());
            out.close();
        }catch(Exception e) {
            System.out.println("It won't happen");
        }

    }

}
