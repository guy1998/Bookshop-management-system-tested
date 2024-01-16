package Main.GUI;

import java.io.FileInputStream;
import java.util.ArrayList;

import Main.Backend.Notification.Message;
import Main.Backend.Users.Administrator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AdminView {

	private Administrator admin;
	private ArrayList<Message> messages;
	
	public AdminView(Administrator admin) {
		this.admin = admin;
		messages = admin.readMessages();
	}
	
	public void show(Stage primaryStage) {
		
		BorderPane content = new BorderPane();
		
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
			(new WriteMessageView(admin)).show(primaryStage);
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
		background.setPrefWidth(283);
		background.setMinHeight(400);
		if(messages.isEmpty()) {
			Label temp = new Label("No messages");
			temp.setPrefHeight(400);
			temp.setPrefWidth(283);
			temp.setAlignment(Pos.CENTER);
			background.getChildren().add(temp);}
		else {
			for(Message message : messages) {
				Label messageL = message.createLabel();
				messageL.setId(""+messages.indexOf(message));
				messageL.setPrefHeight(60);
				messageL.setPrefWidth(283);
				messageL.setOnMouseClicked(e->{
					(new MessageView(admin)).show(primaryStage, message);
					message.markAsRead();
					admin.writeMessages(messages);
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
		
		GridPane buttons = new GridPane();
		ImageView [] buttonIcons = new ImageView[4];
		Button [] btns = new Button[4];
		Tooltip [] tooltips = new Tooltip[4];
		for(int i=0; i<4; i++) {
			try {
				if(i==0)
					input = new FileInputStream("Images/suitcase.png");
				else if(i==1)
					input = new FileInputStream("Images/add-friend.png");
				else if(i==2)
					input = new FileInputStream("Images/book.png");
				else
					input = new FileInputStream("Images/dollar.png");
			}catch(java.io.FileNotFoundException e) {
				System.out.println("Photos do not exist");
			}
			Image img = new Image(input);
			buttonIcons[i] = new ImageView(img);
			buttonIcons[i].setFitHeight(70);
			buttonIcons[i].setFitWidth(70);
			btns[i] = new Button();
			btns[i].setGraphic(buttonIcons[i]);
			btns[i].setStyle("-fx-background-color: purple; -fx-background-radius: 10; -fx-background-radius: 10;");
			btns[i].setPrefSize(200, 200);
			btns[i].setAlignment(Pos.CENTER);
			if(i == 0) {
				tooltips[i] = new Tooltip("Manage employees");
				btns[i].setId("manage");
			}else if(i == 1) {
				btns[i].setId("addUser");
				tooltips[i] = new Tooltip("Add user");
			}else if(i == 2) {
				btns[i].setId("bookInfo");
				tooltips[i] = new Tooltip("Books info");
			}else {
				btns[i].setId("finance");
				tooltips[i] = new Tooltip("Financial");
			}
			tooltips[i].setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-background-color: thistle");
			btns[i].setTooltip(tooltips[i]);
			
		}		
		btns[0].setOnMouseEntered(e->{
			btns[0].setCursor(Cursor.HAND);
			btns[0].setStyle("-fx-background-color: darkorchid; -fx-background-radius: 10; -fx-background-radius: 10;");
		});
		btns[1].setOnMouseEntered(e->{
			btns[1].setCursor(Cursor.HAND);
			btns[1].setStyle("-fx-background-color: darkorchid; -fx-background-radius: 10; -fx-background-radius: 10;");
		});
		btns[1].setOnAction(e->{
			(new CreateUserView(admin)).show(primaryStage);
		});
		btns[2].setOnMouseEntered(e->{
			btns[2].setCursor(Cursor.HAND);
			btns[2].setStyle("-fx-background-color: darkorchid; -fx-background-radius: 10; -fx-background-radius: 10;");
		});
		btns[2].setOnAction(e->{
			(new BookShop(admin)).show(primaryStage);
		});
		btns[3].setOnMouseEntered(e->{
			btns[3].setCursor(Cursor.HAND);
			btns[3].setStyle("-fx-background-color: darkorchid; -fx-background-radius: 10; -fx-background-radius: 10;");
		});
		btns[0].setOnMouseExited(e->{			
			btns[0].setStyle("-fx-background-color: purple; -fx-background-radius: 10; -fx-background-radius: 10;");
		});
		btns[0].setOnAction(e->{
			(new EmployeeView(admin)).show(primaryStage);
		});
		btns[1].setOnMouseExited(e->{			
			btns[1].setStyle("-fx-background-color: purple; -fx-background-radius: 10; -fx-background-radius: 10;");
		});
		btns[2].setOnMouseExited(e->{			
			btns[2].setStyle("-fx-background-color: purple; -fx-background-radius: 10; -fx-background-radius: 10;");
		});
		btns[3].setOnMouseExited(e->{			
			btns[3].setStyle("-fx-background-color: purple; -fx-background-radius: 10; -fx-background-radius: 10;");
		});
		btns[3].setOnAction(e->{
			(new FinancialStats(admin)).show(primaryStage);
		});
		buttons.add(btns[0], 0, 0);
		GridPane.setMargin(btns[0], new Insets(10));
		buttons.add(btns[1], 1, 0);
		GridPane.setMargin(btns[1], new Insets(10));
		buttons.add(btns[2], 0, 1);
		GridPane.setMargin(btns[2], new Insets(10));
		buttons.add(btns[3], 1, 1);
		GridPane.setMargin(btns[3], new Insets(10));
		
		HBox tape = new HBox();
		tape.setSpacing(100);
		tape.setStyle("-fx-background-color: purple");
		tape.setAlignment(Pos.BOTTOM_CENTER);
		tape.setMinHeight(100);
		Label hello = new Label("Hello " + admin.getName()+ "!");
		hello.setId("hello");
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
		Image img1 = new Image(inputStream1);
		Image img2 = new Image(inputStream2);
		ImageView profile = new ImageView(img1);
		ImageView out = new ImageView(img2);
		profile.setId("profile");
		profile.setFitHeight(25);
		profile.setFitWidth(25);
		out.setFitHeight(25);
		out.setFitWidth(25);
		out.setOnMouseEntered(e->{
			out.setCursor(Cursor.HAND);
		});
		out.setOnMouseClicked(e->{
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
			(new ProfileView(admin)).show(primaryStage);
		});
		icons.getChildren().addAll(profile, out);
		tape.getChildren().addAll(hello, icons);
		
		HBox mainField = new HBox();
		mainField.setStyle("-fx-background-color: white;");
		mainField.getChildren().addAll(buttons, inbox);
		mainField.setPadding(new Insets(20));
		mainField.setSpacing(40);
		
		content.setTop(tape);
		content.setCenter(mainField);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Admin view");
		primaryStage.show();
	}
	
}
