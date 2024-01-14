package Main.GUI;

import java.io.FileInputStream;
import java.util.ArrayList;

import Main.Users.*;

import Main.Notification.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MessageView {
	
	private User current;
	
	public MessageView(User current) {
		this.current = current;
	}
	
	public void show(Stage primaryStage, Message message) {
		
		BorderPane root = new BorderPane();
		
		ScrollPane sp = new ScrollPane();
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setPrefSize(790, 250);
		VBox messageBody = new VBox();
		messageBody.setStyle("-fx-background-color: lavender; -fx-background-radius: 25px; -fx-border-radius: 25px;");
		messageBody.setFocusTraversable(false);
		messageBody.setPadding(new Insets(20));
		messageBody.setPrefWidth(783.5);
		messageBody.setMinHeight(500);
		messageBody.setAlignment(Pos.TOP_LEFT);
		Label messageText = new Label(message.getText());
		messageText.setId("messageText");
		messageText.setTextAlignment(TextAlignment.JUSTIFY);
		messageText.setWrapText(true);
		messageText.setFont(Font.font("Verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 17));
		messageText.setTextFill(Color.BLACK);
		messageText.setStyle("-fx-background-color: lavender");
		messageBody.getChildren().add(messageText);
		sp.setContent(messageBody);
		sp.setFocusTraversable(false);
		root.setCenter(sp);
		
		StackPane replyPane = new StackPane();
		replyPane.setStyle("-fx-background-color: transparent;");
		replyPane.setPrefHeight(60);
		Button reply = new Button("Reply");
		reply.setId("reply");
		reply.setStyle("-fx-background-color: lavender; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		reply.setPrefWidth(800);
		reply.setPrefHeight(60);
		reply.setTextFill(Color.MEDIUMPURPLE);
		reply.setFocusTraversable(false);
		reply.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		reply.setAlignment(Pos.CENTER);
		reply.setOnMouseEntered(e->{
			reply.setCursor(Cursor.HAND);
			reply.setStyle("-fx-background-color: thistle; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		});
		reply.setOnMouseExited(e->{
			reply.setStyle("-fx-background-color: lavender; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		});
		reply.setOnAction(e->{
			(new WriteMessageView(current)).show(primaryStage);
		});
		replyPane.getChildren().add(reply);
		root.setBottom(replyPane);
		
		HBox header = new HBox();
		FileInputStream input;
		ImageView [] icons = new ImageView[3];
		try {
			for(int i=0; i<3; i++) {
				if(i==0)
					input = new FileInputStream("Images/left-arrow.png");
				else if(i==1)
					input = new FileInputStream("Images/garbage.png");
				else
					input = new FileInputStream("Images/unread.png");
				Image img = new Image(input);
				icons[i] = new ImageView(img);
				icons[i].setFitHeight(50);
				icons[i].setFitWidth(50);
				
			}
		}catch(java.io.FileNotFoundException e) {
			System.out.println("This should never happen");
		}
		GridPane optionsBar = new GridPane();
		optionsBar.setStyle("-fx-background-color: thistle;");
		optionsBar.setAlignment(Pos.CENTER);
		optionsBar.add(icons[0], 0, 0);
		optionsBar.add(icons[1], 1, 0);
		optionsBar.add(icons[2], 2, 0);
		icons[0].setId("back");
		icons[0].setOnMouseEntered(e->{
			icons[0].setCursor(Cursor.HAND);
		});
		icons[0].setOnMouseClicked(e->{
			if(current.getStatus().equals(Status.ADMINISTRATOR))
				(new AdminView((Administrator)current)).show(primaryStage);
			else if(current.getStatus().equals(Status.LIBRARIAN))
				(new LibrarianView((Librarian)current)).show(primaryStage);
			else
				(new ManagerView((Manager)current, false)).show(primaryStage);
		});
		icons[1].setId("delete");
		icons[1].setOnMouseEntered(e->{
			icons[1].setCursor(Cursor.HAND);
		});
		icons[1].setOnMouseClicked(e->{
			if(message.getHeader().equals("Welcome to BookShop Management System")) {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("This message was sent to you by the system.");
		        alert.setHeaderText("You cannot delete this message!");
				alert.setContentText("System messages cannot be deleted!");
		        alert.showAndWait();
			}else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
		        alert.setTitle("This message will be deleted permanently!!");
		        alert.setHeaderText("Are you sure you want to proceed?");
		        alert.showAndWait();
		        if(alert.getResult().equals(ButtonType.OK)) {
		        	ArrayList<Message> messages = current.readMessages();
		        	for(int i=0; i<messages.size(); i++)
		        		if(messages.get(i).getText().equals(message.getText()) && messages.get(i).getHeader().equals(message.getHeader())) {
		        			messages.remove(i);
							break;
		        		}
		        	for(Message m:messages)
		        		System.out.println(m.getHeader());
					current.writeMessages(messages);
					if(current.getStatus().equals(Status.ADMINISTRATOR))
						(new AdminView((Administrator)current)).show(primaryStage);
					else if(current.getStatus().equals(Status.LIBRARIAN))
						(new LibrarianView((Librarian)current)).show(primaryStage);
					else
						(new ManagerView((Manager)current, false)).show(primaryStage);
		        }	
			}
			
		});
		icons[2].setId("markunread");
		icons[2].setOnMouseEntered(e->{
			icons[2].setCursor(Cursor.HAND);
		});
		icons[2].setOnMouseClicked(e->{
			ArrayList<Message> messages = current.readMessages();
			for(int i=0; i<messages.size(); i++)
        		if(messages.get(i).getText().equals(message.getText()) && messages.get(i).getHeader().equals(message.getHeader())) {
        			messages.get(i).markAsUnread();
        		}
			current.writeMessages(messages);
		});
		header.setStyle("-fx-background-color: thistle;");
		header.setPrefWidth(780);
		header.setMinHeight(70);
		header.setPadding(new Insets(20));
		Label headerLabel = new Label(message.getHeader());
		headerLabel.setId("messageHeader");
		headerLabel.setWrapText(true);
		headerLabel.setStyle("-fx-background-color: thistle");
		headerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		headerLabel.setTextFill(Color.BLACK);
		header.setSpacing(250);
		header.setAlignment(Pos.CENTER_LEFT);
		header.getChildren().addAll(headerLabel, optionsBar);
		root.setTop(header);
		
		Scene scene = new Scene(root, 800, 550);
		primaryStage.setTitle("Message View");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
}