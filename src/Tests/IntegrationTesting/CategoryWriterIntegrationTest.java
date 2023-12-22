package Tests.IntegrationTesting;

import Main.Products.Book;
import Main.Products.BookStock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.util.ArrayList;

public class CategoryWriterIntegrationTest {

    @TempDir
    private static File tempFolder;

    private File tempFile;

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

}
