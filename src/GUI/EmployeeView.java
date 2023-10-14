package GUI;

import java.io.FileInputStream;
import java.util.ArrayList;

import Users.Administrator;
import Users.Librarian;
import Users.Manager;
import Users.Status;
import Users.User;
import Users.UserStack;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EmployeeView {

	private Administrator admin;
	private UserStack users;
	
	public EmployeeView(Administrator admin) {
		this.admin = admin;
		this.users = new UserStack();
	}
	
	public void show(Stage primaryStage) {
		
		BorderPane content = new BorderPane();
		content.setStyle("-fx-background-color: purple;");
		
		FileInputStream input = null;
		try {
			input = new FileInputStream("Images/left-arrow.png");
		}catch (java.io.FileNotFoundException e)  {
			System.out.println("No such pic in Images");
		}
		ImageView backIcon = new ImageView(new Image(input));
		backIcon.setFitHeight(50);
		backIcon.setFitWidth(50);
		HBox tape = new HBox();
		tape.setStyle("-fx-background-color: purple;");
		tape.setAlignment(Pos.CENTER_LEFT);
		tape.setPrefHeight(70);
		tape.setSpacing(10);
		tape.setPrefWidth(550);
		Button back = new Button();
		back.setGraphic(backIcon);
		back.setPrefSize(70, 70);
		back.setStyle("-fx-background-color: purple;");
		back.setAlignment(Pos.CENTER);
		back.setOnMouseEntered(e->{
			back.setCursor(Cursor.HAND);
			back.setStyle("-fx-background-color: darkorchid;");
		});
		back.setOnMouseExited(e->{
			back.setStyle("-fx-background-color: purple;");
		});
		back.setOnAction(e->{
				(new AdminView(admin)).show(primaryStage);
		});
		Label profile = new Label("Employees");
		profile.setStyle("-fx-background-color: purple;");
		profile.setTextFill(Color.WHITE);
		profile.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		tape.getChildren().addAll(back, profile);
		
		ScrollPane sp = new ScrollPane();
		sp.setStyle("-fx-background-color: purple;");
		sp.setFocusTraversable(false);
		sp.setPrefWidth(800);
		VBox background = new VBox();
		background.setStyle("-fx-background-color: lightgrey;");
		background.setPrefWidth(783);
		background.setMinHeight(480);
		ArrayList<User> allUser = new ArrayList<>();
		allUser.addAll(users.filterByStatus(Status.LIBRARIAN));
		allUser.addAll(users.filterByStatus(Status.MANAGER));
		for(User user:allUser) {
			Button temp = new Button(user.toString());
			temp.setMinHeight(60);
			temp.setPrefWidth(783);
			temp.setAlignment(Pos.CENTER);
			temp.setStyle("-fx-background-color: white; -fx-border-color: lightgrey; -fx-border-radius: 15px; -fx-background-radius: 15px;");
			temp.setOnMouseEntered(e->{
				temp.setCursor(Cursor.HAND);
				temp.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-radius: 15px; -fx-background-radius: 15px;");
			});
			temp.setOnMouseExited(e->{
				temp.setStyle("-fx-background-color: white; -fx-border-color: lightgrey; -fx-border-radius: 15px; -fx-background-radius: 15px;");
			});
			temp.setOnAction(e->{
				if(user.getStatus().equals(Status.LIBRARIAN))
					(new LibrarianStats(admin, (Librarian)user)).show(primaryStage);
				else
					(new UserEdit(admin, (Manager)user)).show(primaryStage);
			});
			temp.setWrapText(true);
			temp.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
			temp.setTextFill(Color.PURPLE);
			background.getChildren().add(temp);
		}
		sp.setContent(background);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		content.setCenter(sp);
		content.setTop(tape);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Users");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
}
