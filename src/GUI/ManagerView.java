package GUI;
import Users.*;

import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;

import Exceptions.InvalidPasswordException;
import Notification.Message;
import Products.Author;
import Products.Book;
import Products.BookStock;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ManagerView {

	private User currentManager;
	private BookStock stock;
	private ArrayList<Message> messages;
	private boolean loging;
	
	public ManagerView() {
		stock = new BookStock();
	}
	
	public ManagerView(User currentManager, boolean loging) {
		this.currentManager = currentManager;
		stock = new BookStock();
		messages = currentManager.readMessages();
		this.loging = loging;
	}
	
	public void show(Stage primaryStage) {
	
		HBox tape = new HBox();
		tape.setSpacing(100);
		tape.setStyle("-fx-background-color: purple");
		tape.setAlignment(Pos.BOTTOM_CENTER);
		tape.setMinHeight(100);
		
		Label hello = new Label("Hello " + currentManager.getName()+ "!");
		hello.setTextFill(Color.WHITE);
		hello.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		HBox icons = new HBox();
		icons.setStyle("-fx-background-color: purple");
		icons.setAlignment(Pos.BOTTOM_RIGHT);
		icons.setPadding(new Insets(10));
		icons.setMinWidth(400);
		icons.setSpacing(10);
		FileInputStream inputStream1 = null, inputStream2 = null;
		try {
			inputStream1 = new FileInputStream("Images/user(1).png");
			inputStream2 = new FileInputStream("Images/logout.png");
		}catch(java.io.FileNotFoundException e) {
			System.out.println("Please check the images folder and place the images");
		}
		
		ImageView profile = new ImageView(new Image(inputStream1));
		ImageView logout = new ImageView(new Image(inputStream2));
		logout.setOnMouseEntered(e->{
			logout.setCursor(Cursor.HAND);
		});
		logout.setOnMouseClicked(e->{
			Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Loging out?");
	        alert.setHeaderText("Are you sure you want to logout?");
	        alert.showAndWait();
	        if(alert.getResult() == ButtonType.OK)
	        	(new LoginPage()).show(primaryStage);
		});
		profile.setOnMouseEntered(e->{
			profile.setCursor(Cursor.HAND);
		});
		profile.setOnMouseClicked(e->{
			(new ProfileView(currentManager)).show(primaryStage);
		});
		profile.setFitHeight(25);
		profile.setFitWidth(25);
		logout.setFitHeight(25);
		logout.setFitWidth(25);
		icons.getChildren().addAll(profile, logout);
		
		tape.getChildren().addAll(hello, icons);
		
		VBox buttons = new VBox();
		buttons.setSpacing(30);
		buttons.setStyle("-fx-background-color: white;");
		buttons.setAlignment(Pos.CENTER);
		
		FileInputStream stockInput = null;
		try {
			stockInput = new FileInputStream("Images/book.png");
		}catch(java.io.FileNotFoundException e) {
			System.out.println("Check the images folder and add staff.png");
		}
		Image stockImg = new Image(stockInput);
		ImageView stockIcon = new ImageView(stockImg);
		stockIcon.setFitHeight(30);
		stockIcon.setFitWidth(30);
		Button bookStock = new Button("Book Stock");
		bookStock.setAlignment(Pos.CENTER_LEFT);
		bookStock.setGraphic(stockIcon);
		if(((Manager)currentManager).getPermission().equals(Access.FULL)) 
			bookStock.setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: purple");
		else 
			bookStock.setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: grey");
		bookStock.setTextFill(Color.WHITE);
		bookStock.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 25));
		bookStock.setPrefSize(350, 80);
		bookStock.setOnMouseEntered(e->{
			if(((Manager)currentManager).getPermission().equals(Access.FULL)) {
				bookStock.setStyle("-fx-background-color: darkorchid; -fx-border-radius:20; -fx-background-radius:20");
				bookStock.setCursor(Cursor.HAND);
			}
		});
		bookStock.setOnMouseExited(e->{
			if(((Manager)currentManager).getPermission().equals(Access.FULL)) 
				bookStock.setStyle("-fx-background-color: purple; -fx-border-radius:20; -fx-background-radius:20");
		});
		bookStock.setOnAction(e->{
			if(((Manager)currentManager).getPermission().equals(Access.FULL)) 
				(new BookShop(currentManager)).show(primaryStage);
			else {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Access denied");
		        alert.setHeaderText("It seems the administrator has denied you access to this menu.");
		        alert.showAndWait();
			}
		});
		FileInputStream staffInput = null;
		try {
			staffInput = new FileInputStream("Images/staff.png");
		}catch(java.io.FileNotFoundException e) {
			System.out.println("Check the images folder and add staff.png");
		}
		Image staffImg = new Image(staffInput);
		ImageView staffIcon = new ImageView(staffImg);
		staffIcon.setFitHeight(30);
		staffIcon.setFitWidth(30);
		Button staff = new Button("Staff Managment");
		if(!((Manager)currentManager).getPermission().equals(Access.NONE)) 
			staff.setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: purple");
		else
			staff.setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: grey;");
		staff.setAlignment(Pos.CENTER_LEFT);
		staff.setTextFill(Color.WHITE);
		staff.setPrefSize(350, 80);
		staff.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 25));
		staff.setGraphic(staffIcon);
		staff.setOnMouseEntered(e->{
			if(!((Manager)currentManager).getPermission().equals(Access.NONE)) {
				staff.setStyle("-fx-background-color: darkorchid; -fx-border-radius:20; -fx-background-radius:20");
				staff.setCursor(Cursor.HAND);
			}
		});
		staff.setOnMouseExited(e->{
			if(!((Manager)currentManager).getPermission().equals(Access.NONE)) 
				staff.setStyle("-fx-background-color: purple; -fx-border-radius:20; -fx-background-radius:20");
		});
		staff.setOnAction(e->{
			if(!((Manager)currentManager).getPermission().equals(Access.NONE)) 
				(new UsersView(currentManager)).show(primaryStage);
			else {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Access denied");
		        alert.setHeaderText("It seems the administrator has denied you access to this menu.");
		        alert.showAndWait();
			}
		});
		FileInputStream statsInput = null;
		try {
			statsInput = new FileInputStream("Images/trend.png");
		}catch(java.io.FileNotFoundException e) {
			System.out.println("Check the images folder and add staff.png");
		}
		Image statsImg = new Image(statsInput);
		ImageView statsIcon = new ImageView(statsImg);
		statsIcon.setFitHeight(30);
		statsIcon.setFitWidth(30);
		Button stats = new Button("Statistics");
		stats.setAlignment(Pos.CENTER_LEFT);
		stats.setGraphic(statsIcon);
		if(!((Manager)currentManager).getPermission().equals(Access.NONE)) 
			stats.setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: purple");
		else 
			stats.setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: grey");
		stats.setTextFill(Color.WHITE);
		stats.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 25));
		stats.setPrefSize(350, 80);
		stats.setOnMouseEntered(e->{
			if(!((Manager)currentManager).getPermission().equals(Access.NONE)) {
				stats.setStyle("-fx-background-color: darkorchid; -fx-border-radius:20; -fx-background-radius:20");
				stats.setCursor(Cursor.HAND);
			}
		});
		stats.setOnMouseExited(e->{
			if(!((Manager)currentManager).getPermission().equals(Access.NONE)) 
				stats.setStyle("-fx-background-color: purple; -fx-border-radius:20; -fx-background-radius:20");
		});
		stats.setOnAction(e->{
			if(!((Manager)currentManager).getPermission().equals(Access.NONE)) 
				(new BookStatistics((Manager)currentManager)).show(primaryStage);
		});
		
		buttons.getChildren().addAll(bookStock, staff, stats);
		
		BorderPane inbox = new BorderPane();
		inbox.setFocusTraversable(false);
		inbox.setPrefHeight(270);
		inbox.setPrefWidth(320);
		Label inboxLabel = new Label("Inbox:");
		inboxLabel.setStyle("-fx-background-color: white;");
		inboxLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		inboxLabel.setTextFill(Color.PURPLE);
		inboxLabel.setTextAlignment(TextAlignment.LEFT);
		inboxLabel.setAlignment(Pos.CENTER_LEFT);
		Button compose = new Button("Compose");
		compose.setPrefSize(inbox.getPrefWidth(), 30);
		compose.setStyle("-fx-background-color: purple; -fx-background-radius: 10; -fx-border-radius: 10;");
		compose.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		compose.setAlignment(Pos.CENTER);
		compose.setTextFill(Color.WHITE);
		FileInputStream input = null;
		
		try {
			input = new FileInputStream("Images/plus.png");
		}catch(Exception e) {
			System.out.println("Check images folder");
		}
		ImageView compIcon = new ImageView(new Image(input));
		compIcon.setFitHeight(20);
		compIcon.setFitWidth(20);
		compose.setGraphic(compIcon);
		compose.setOnMouseEntered(e->{
			compose.setCursor(Cursor.HAND);
			compose.setStyle("-fx-background-color: darkorchid; -fx-background-radius: 10; -fx-border-radius: 10;");
		});
		compose.setOnMouseExited(e->{
			compose.setStyle("-fx-background-color: purple; -fx-background-radius: 10; -fx-border-radius: 10;");
		});
		compose.setOnAction(e->{
			System.out.println("U kry");
			(new WriteMessageView(currentManager)).show(primaryStage);
		});
		ScrollPane sp = new ScrollPane();
		sp.setStyle("-fx-background-color: purple;");
		sp.setFocusTraversable(false);
		sp.setPrefWidth(320);
		VBox background = new VBox();
		background.setStyle("-fx-background-color: purple;");
		background.setPrefWidth(297);
		background.setMinHeight(400);
		if(messages.isEmpty()) {
			Label temp = new Label("No messages");
			temp.setPrefHeight(400);
			temp.setPrefWidth(285);
			temp.setAlignment(Pos.CENTER);
			background.getChildren().add(temp);}
		else {
			for(Message message : messages) {
				Label messageL = message.createLabel();
				messageL.setPrefHeight(60);
				messageL.setPrefWidth(298);
				messageL.setOnMouseClicked(e->{
					(new MessageView(currentManager)).show(primaryStage, message);
					message.markAsRead();
					currentManager.writeMessages(messages);
				});
				messageL.setOnMouseEntered(e-> {
					messageL.setCursor(Cursor.HAND);
					if(message.isRead())
						messageL.setStyle("-fx-background-color: purple; -fx-border-color: lightslategrey;");
					else
						messageL.setStyle("-fx-background-color: darkorchid; -fx-border-color: lightslategrey;");
				});
				messageL.setOnMouseExited(e->{
					if(!message.isRead())
						messageL.setStyle("-fx-background-color: darkorchid; -fx-border-color: whitesmoke;");
					else
						messageL.setStyle("-fx-background-color: purple; -fx-border-color: whitesmoke;");
				});
				messageL.setWrapText(true);
				if(message.isRead())
					messageL.setStyle("-fx-background-color: purple; -fx-border-color: whitesmoke;");
				else
					messageL.setStyle("-fx-background-color: darkorchid; -fx-border-color: whitesmoke;");
				messageL.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 13));
				messageL.setTextFill(Color.WHITE);
				background.getChildren().add(messageL);
			}
		}
		sp.setContent(background);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		inbox.setTop(inboxLabel);
		inbox.setCenter(sp);
		inbox.setBottom(compose);
		BorderPane.setMargin(compose, new Insets(5));

		
		HBox content = new HBox();
		content.setSpacing(100);
		content.setAlignment(Pos.CENTER_LEFT);
		content.setPadding(new Insets(20));
		content.getChildren().addAll(buttons, inbox);
		
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: white;");
		pane.setTop(tape);
		pane.setCenter(content);
		
		Scene scene = new Scene(pane, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Manager");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		if(!stock.runningLow().isEmpty() && loging)
		 {
			StringBuilder low = new StringBuilder("These books are running low!!!\n");
			StringBuilder none = new StringBuilder("These books are missing in stock!!!\n");
			
			for(int i=0; i<stock.runningLow().size(); i++) {
				if(stock.runningLow().get(i).getNumber() == 0) {
					none.append(stock.runningLow().get(i).toString());
					none.append("\n");
				}
				else {
					low.append(stock.runningLow().get(i).toString());
					low.append("\n");
				}
			}
			low.append("\n");
			
			Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Stock info");
	        alert.setHeaderText("Important stock information");
	        if(none.toString().equals("These books are missing in stock!!!\n"))
	        	alert.setContentText(low.toString());
	        else
	        	alert.setContentText(low.toString() + none);
	        alert.showAndWait();
	        }else if(stock.empty() && loging) {
	        	Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Stock info");
		        alert.setHeaderText("Empty Stock");
		        alert.setContentText("Your stock seems to be empty. Try adding some books.");
		        alert.showAndWait();
	        }
		
		}
	
}
