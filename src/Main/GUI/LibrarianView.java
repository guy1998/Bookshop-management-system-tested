package Main.GUI;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;

import Main.Backend.Notification.Message;
import Main.Backend.Users.Access;
import Main.Backend.Users.Librarian;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LibrarianView {
	
	private Librarian lib;
	private ArrayList <Message> messages;
	
	public LibrarianView (Librarian lib){
		this.lib = lib;
		messages = lib.readMessages();
	}
	
	public void show(Stage primaryStage) {
		
		BorderPane content = new BorderPane();
		
		HBox tape = new HBox();
		tape.setStyle("-fx-background-color: purple;");
		tape.setAlignment(Pos.BOTTOM_CENTER);
		tape.setSpacing(100);
		tape.setMinHeight(100);
		Label hello = new Label("Hello " + lib.getName() + "!");
		hello.setId("hello");
		hello.setTextFill(Color.WHITE);
		hello.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		ImageView [] icons = new ImageView[4];
		HBox options = new HBox();
		options.setPrefWidth(400);
		options.setSpacing(10);
		options.setAlignment(Pos.BOTTOM_RIGHT);
		options.setPadding(new Insets(17));
		options.setStyle("-fx-background-color: purple;");
		Button [] buttons = new Button[2];
		FileInputStream input = null;
		for(int i=0; i<4; i++) {
			try {
				if(i == 0)
					input = new FileInputStream("Images/user(1).png");
				else if(i == 1)
					input = new FileInputStream("Images/logout.png");
				else if(i == 2)
					input = new FileInputStream("Images/magnifier.png");
				else 
					input = new FileInputStream("Images/shopping-cart.png");
			}catch(java.io.FileNotFoundException e) {
				System.out.println("No such photo!");
			}
			
			icons[i] = new ImageView(new Image(input));
			if(i == 2) {
				icons[i].setFitHeight(25);
				icons[i].setFitWidth(25);
				buttons[0] = new Button("Search book");
				buttons[0].setAlignment(Pos.CENTER_LEFT);
				buttons[0].setGraphic(icons[i]);
				buttons[0].setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: purple");
				buttons[0].setTextFill(Color.WHITE);
				buttons[0].setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
				buttons[0].setPrefSize(350, 80);
			}else if(i == 3) {
				icons[i].setFitHeight(100);
				icons[i].setFitWidth(100);
				buttons[1] = new Button();
				buttons[1].setId("cart");
				buttons[1].setAlignment(Pos.CENTER);
				buttons[1].setGraphic(icons[i]);
				if(lib.getPermission().equals(Access.FULL))
					buttons[1].setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: purple");
				else 
					buttons[1].setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: grey");
				buttons[1].setPrefSize(350, 200);
			}else {
				icons[i].setFitHeight(25);
				icons[i].setFitWidth(25);
				options.getChildren().add(icons[i]);
			}
		}
		tape.getChildren().addAll(hello, options);
		icons[1].setOnMouseEntered(e->{
			icons[1].setCursor(Cursor.HAND);
		});
		icons[1].setOnMouseClicked(e->{
			Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Loging out?");
	        alert.setHeaderText("Are you sure you want to logout?");
	        alert.showAndWait();
	        if(alert.getResult() == ButtonType.OK)
	        	(new LoginPage()).show(primaryStage);
		});
		icons[0].setId("profile");
		icons[0].setOnMouseEntered(e->{
			if(!lib.getPermission().equals(Access.NONE))
				icons[0].setCursor(Cursor.HAND);
		});
		icons[0].setOnMouseClicked(e->{
			if(!lib.getPermission().equals(Access.NONE))
				(new ProfileView(lib)).show(primaryStage);
		});
		
		buttons[0].setOnMouseEntered(e->{
			buttons[0].setCursor(Cursor.HAND);
			buttons[0].setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: darkorchid");
		});
		buttons[0].setOnMouseExited(e->{
			buttons[0].setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: purple");
		});
		buttons[0].setOnAction(e->{
			(new SearchView(lib)).show(primaryStage);
		});
		
		buttons[1].setOnMouseEntered(e->{
			if(lib.getPermission().equals(Access.FULL)) {
				buttons[1].setCursor(Cursor.HAND);
				buttons[1].setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: darkorchid");
			}
		});
		buttons[1].setOnMouseExited(e->{
			if(lib.getPermission().equals(Access.FULL))
				buttons[1].setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: purple");
		});
		buttons[1].setOnAction(e->{
			if(lib.getPermission().equals(Access.FULL))
				(new ChooseItems(lib)).show(primaryStage);
			else {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Access denied");
		        alert.setHeaderText("It seems the administrator has denied you access to this menu.");
		        alert.showAndWait();
			}
		});
		VBox buttonBox = new VBox();
		buttonBox.setSpacing(30);
		buttonBox.setPadding(new Insets(10));
		buttonBox.setStyle("-fx-background-color: white;");
		buttonBox.getChildren().addAll(buttons);
		
		BorderPane inbox = new BorderPane();
		inbox.setFocusTraversable(false);
		inbox.setPrefHeight(270);
		inbox.setPrefWidth(320);
		Button compose = new Button("Compose");
		compose.setPrefSize(inbox.getPrefWidth(), 30);
		compose.setStyle("-fx-background-color: purple; -fx-background-radius: 10; -fx-border-radius: 10;");
		compose.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		compose.setAlignment(Pos.CENTER);
		compose.setTextFill(Color.WHITE);		
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
			(new WriteMessageView(lib)).show(primaryStage);
		});
		Label inboxLabel = new Label("Inbox:");
		inboxLabel.setStyle("-fx-background-color: white;");
		inboxLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		inboxLabel.setTextFill(Color.PURPLE);
		inboxLabel.setTextAlignment(TextAlignment.LEFT);
		inboxLabel.setAlignment(Pos.CENTER_LEFT);
		ScrollPane sp = new ScrollPane();
		sp.setStyle("-fx-background-color: purple;");
		sp.setFocusTraversable(false);
		sp.setPrefWidth(320);
		VBox background = new VBox();
		background.setStyle("-fx-background-color: purple;");
		background.setPrefWidth(302);
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
				messageL.setPrefWidth(302);
				messageL.setOnMouseClicked(e->{
					(new MessageView(lib)).show(primaryStage, message);
					message.markAsRead();
					lib.writeMessages();
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
		
		HBox mainField = new HBox();
		mainField.setStyle("-fx-background-color: white;");
		mainField.getChildren().addAll(buttonBox, inbox);
		mainField.setPadding(new Insets(20));
		mainField.setSpacing(70);
		
		content.setTop(tape);
		content.setCenter(mainField);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Librarian View");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
