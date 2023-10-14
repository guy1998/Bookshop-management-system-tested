package GUI;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import Users.Administrator;
import Users.Status;

import java.util.ArrayList;

import Exceptions.NonExistantUserException;
import Notification.Message;
import Users.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class WriteMessageView {
	
	private User current;
	
	public WriteMessageView(User current) {
		this.current = current;
	}

	public void show(Stage primaryStage) {
		
		BorderPane content = new BorderPane();

		HBox header = new HBox();
		header.setStyle("-fx-background-color: purple;");
		header.setAlignment(Pos.BOTTOM_LEFT);
		header.setPadding(new Insets(20));
		header.setPrefHeight(70);
		Label newMess = new Label("New Message");
		newMess.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		newMess.setTextFill(Color.WHITE);
		header.getChildren().add(newMess);
		
		VBox body = new VBox();
		body.setSpacing(0);
		body.setStyle("-fx-background-color: purple;");
		body.setPrefWidth(780);
		body.setMinHeight(380);
		TextField to = new TextField();
		to.setFocusTraversable(false);
		to.setFont(Font.font("Verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 13));
		to.setPrefWidth(780);
		to.setPrefHeight(60);
		to.setPromptText("Send to...");
		to.setStyle("-fx-background-color: white; -fx-border-color: white;");
		TextArea messageBody = new TextArea() ;
		messageBody.setFocusTraversable(false);
		messageBody.setWrapText(true);
		messageBody.setPrefWidth(780);
		messageBody.setMinHeight(380);
		//messageBody.setAlignment(Pos.TOP_LEFT);
		messageBody.setFont(Font.font("Verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 15));
		messageBody.setStyle("-fx-control-inner-background: purple; -fx-text-fill: white;");
		messageBody.setPromptText("Write your message here...");
		body.getChildren().addAll(to, messageBody);
		
		
		HBox footer = new HBox();
		footer.setSpacing(0);
		footer.setPrefWidth(550);
		footer.setPrefHeight(40);
		Button send = new Button("Send");
		send.setPrefSize(400, 40);
		send.setStyle("-fx-background-color: purple; -fx-border-color: white;");
		send.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		send.setAlignment(Pos.CENTER);
		send.setTextFill(Color.WHITE);
		send.setOnMouseEntered(e->{
			send.setCursor(Cursor.HAND);
			send.setStyle("-fx-background-color: darkorchid; -fx-border-color: white;");
		});
		send.setOnMouseExited(e->{
			send.setStyle("-fx-background-color: purple; -fx-border-color: white;");
		});
		send.setOnAction(e->{
			try {
				User tempUser = (new UserStack()).findUser(to.getText());
				Message temp = new Message(current, "Message from " + current.getName(), messageBody.getText());
				ArrayList<Message> messages = tempUser.readMessages();
				messages.add(temp);
				tempUser.writeMessages(messages);
				Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Success");
		        alert.setHeaderText("Message sent successfully to " + tempUser.getName() + " " + tempUser.getSurname());
		        alert.showAndWait();
		        if(current.getStatus() == Status.ADMINISTRATOR)
					(new AdminView((Administrator)current)).show(primaryStage);
				else if(current.getStatus() == Status.MANAGER)
					(new ManagerView((Manager)current, false)).show(primaryStage);
				else
					(new LibrarianView((Librarian)current)).show(primaryStage);
			}catch(NonExistantUserException e1) {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Error");
		        alert.setHeaderText("User you are trying to write to does not exist");
		        alert.setContentText(e1.getMessage());
		        alert.showAndWait();
			}
		});
		Button cancel = new Button("Cancel");
		cancel.setPrefSize(400, 40);
		cancel.setStyle("-fx-background-color: purple; -fx-border-color: white;");
		cancel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		cancel.setAlignment(Pos.CENTER);
		cancel.setTextFill(Color.WHITE); 
		cancel.setOnMouseEntered(e->{
			cancel.setCursor(Cursor.HAND);
			cancel.setStyle("-fx-background-color: darkorchid; -fx-border-color: white;");
		});
		cancel.setOnMouseExited(e->{
			cancel.setStyle("-fx-background-color: purple; -fx-border-color: white;");
		});
		cancel.setOnAction(e->{
			if(current.getStatus() == Status.ADMINISTRATOR)
				(new AdminView((Administrator)current)).show(primaryStage);
			else if(current.getStatus() == Status.MANAGER)
				(new ManagerView((Manager)current, false)).show(primaryStage);
			else
				(new LibrarianView((Librarian)current)).show(primaryStage);
		});
		footer.getChildren().addAll(send, cancel);
		
		content.setTop(header);
		content.setCenter(body);
		content.setBottom(footer);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("New Message");
		primaryStage.show();
		
		
		
		
	}
	
	
}