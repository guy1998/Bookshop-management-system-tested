import Main.GUI.LoginPage;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;

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
