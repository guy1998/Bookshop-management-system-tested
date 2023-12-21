package Tests.UnitTesting;

import Main.Products.CategoryWriter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TestCategoryWriter {

    @TempDir
    private static File tempFolder;
    private File tempFile;
    @BeforeEach
    public void createDataset() throws IOException{
        tempFile = new File(tempFolder, "categories.txt");
        FileWriter fileWriter = new FileWriter(tempFile.getPath(), true);
        fileWriter.append("Fantasy\n");
        fileWriter.append("History\n");
        fileWriter.append("Romance\n");
        fileWriter.append("Drama\n");
        fileWriter.close();
    }
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
    public void testCategoryExistWithTrueCase() {
        assertTrue((new CategoryWriter(tempFile.getPath())).categoryExists("Fantasy"));
    }

    @Test
    public void testCategoryExistsWithFalseCase() {
        assertFalse((new CategoryWriter(tempFile.getPath())).categoryExists("Comedy"));
    }

    @Test
    public void testCategoryExistsWithEmptyFile() {
        File emptyFile = new File(tempFolder, "empty.txt");
        assertFalse((new CategoryWriter(emptyFile.getPath())).categoryExists("Comedy"));
    }

    @Test
    public void testAddCategoryWithEmptyFile() throws FileNotFoundException {
        File emptyFile = new File(tempFolder, "empty.txt");
        (new CategoryWriter(emptyFile.getPath())).addCategory("Fantasy");
        Scanner input = new Scanner(emptyFile);
        while(input.hasNext()){
            assertEquals("Fantasy", input.nextLine());
        }
        input.close();
    }

    @Test
    public void testAddCategoryWithNonEmptyFile() throws FileNotFoundException {
        String [] categories = new String []{"Fantasy", "History", "Romance", "Drama", "Science"};
        (new CategoryWriter(tempFile.getPath())).addCategory("Science");
        Scanner input = new Scanner(tempFile);
        for(String s:categories){
            String temp = input.nextLine();
            System.out.println(temp);
            assertEquals(s, temp);
        }
        input.close();
    }
}
