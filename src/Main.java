import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import GUI.*;
import Products.Author;
import Products.Bill;
import Products.Book;
import Products.BookStock;
import Users.*;
import Utilis.CompDate;


public class Main extends Application{
	
	
	public static void main(String [] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FileInputStream input = null;
		try {
		input = new FileInputStream("Images/bookstore.png");
		}catch (java.io.FileNotFoundException e) {
			System.out.println("No file bookstore.png");
		}
		primaryStage.getIcons().add(new Image(input));

		(new LoginPage()).show(primaryStage);
	}

}
