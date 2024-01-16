package Main.GUI;

import javafx.stage.Stage;

import java.io.FileInputStream;

import Main.Backend.Exceptions.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import Main.Backend.Users.*;

public class LoginPage {
	
	public void show(Stage primaryStage){
		
		//this is the pane that holds the entire content
		VBox pane = new VBox();
		pane.spacingProperty().bind(pane.heightProperty().divide(20));
		
		Label loginLabel = new Label("Library Managment System");
		loginLabel.setAlignment(Pos.CENTER);
		loginLabel.setTextAlignment(TextAlignment.CENTER);
		loginLabel.setTextFill(Color.WHITE);
		loginLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));		
		
		FileInputStream inputStream = null;
		try {
		inputStream = new FileInputStream("Images/user(1).png");
		}catch(Exception a) {
			System.out.println("This should never happen but anyway");
		}
		Image userImg = new Image(inputStream);
		ImageView userIcon = new ImageView(userImg);
		userIcon.setFitHeight(20);
		userIcon.setFitWidth(20);
		TextField usernameField = new TextField();
		usernameField.setPrefWidth(200);
		usernameField.setPrefHeight(40);
		usernameField.setPromptText("username");
		usernameField.setFocusTraversable(false);
		usernameField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
		usernameField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
		usernameField.setId("usernameField");
		HBox userPane = new HBox();//pane that holds the user field and icon
		userPane.spacingProperty().bind(userPane.widthProperty().divide(70));
		userPane.setAlignment(Pos.CENTER);
		userPane.getChildren().add(userIcon);
		userPane.getChildren().add(usernameField);
		
		FileInputStream inputStream2 = null;
		try {
		inputStream2 = new FileInputStream("Images/key.png");
		}catch(Exception a) {
			System.out.println("This should never happen but anyway");
		}
		Image passImg = new Image(inputStream2);
		ImageView passIcon = new ImageView(passImg);
		passIcon.setFitHeight(20);
		passIcon.setFitWidth(20);
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefHeight(40);
		passwordField.setPrefWidth(200);
		passwordField.setPromptText("password");
		passwordField.setFocusTraversable(false);
		passwordField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
		passwordField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
		passwordField.setId("passwordField");
		HBox passwordPane = new HBox();//pane that holds the password field and icon
		passwordPane.spacingProperty().bind(passwordPane.widthProperty().divide(70));
		passwordPane.setAlignment(Pos.CENTER);
		passwordPane.getChildren().addAll(passIcon, passwordField);
		
		VBox temp = new VBox();//pane that holds both userpane and passpane
		temp.spacingProperty().bind(pane.heightProperty().divide(50));
		temp.getChildren().addAll(userPane, passwordPane);
		temp.setAlignment(Pos.CENTER);
		
		VBox buttonsPane = new VBox();//pane that holds the login and reset button
		buttonsPane.setSpacing(10);
		buttonsPane.setAlignment(Pos.CENTER);
		Button loginButton = new Button("LogIn");
		loginButton.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15; -fx-text-fill: purple;");
		loginButton.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));
		loginButton.setPrefHeight(40);
		loginButton.setPrefWidth(200);
		loginButton.setFocusTraversable(false);
		loginButton.setId("loginButton");
		loginButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				loginButton.setStyle("-fx-background-color: gainsboro; -fx-border-radius:15; -fx-background-radius:15; -fx-text-fill: purple;");
				loginButton.setCursor(Cursor.HAND);
			}
		});
		loginButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				loginButton.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15; -fx-text-fill: purple;");
			}
		});
		loginButton.setOnAction(e->{
			UserStack users = new UserStack();
						
			try {
				User us = users.findUser(usernameField.getText());
				System.out.println(us);
				if(!us.getPassword().equals(passwordField.getText())) 
					throw new InvalidPasswordException("The password entered does not match");
				
				if(us.getStatus().equals(Status.ADMINISTRATOR))
					(new AdminView((Administrator)us)).show(primaryStage);
				else if(us.getStatus().equals(Status.LIBRARIAN))
					(new LibrarianView((Librarian)us)).show(primaryStage);
				else
					(new ManagerView((Manager)us, true)).show(primaryStage);
			}catch(NonExistantUserException e1) {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Error");
		        alert.setHeaderText("User does not exist");
		        alert.setContentText(e1.getMessage());
		        alert.showAndWait();
			}catch(InvalidPasswordException e2) {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Error");
		        alert.setHeaderText("Incorrect password");
		        alert.setContentText(e2.getMessage());
		        alert.showAndWait();
			}
			
		});
		
		Label forgot = new Label("Forgot password?");
		forgot.setTextFill(Color.WHITE);
		forgot.setFont(Font.font("Verdana", FontWeight.LIGHT, FontPosture.REGULAR, 12));
		forgot.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				forgot.setUnderline(true);
				forgot.setCursor(Cursor.HAND);
			}
		});
		forgot.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				forgot.setUnderline(false);
			}
		});

		buttonsPane.getChildren().addAll(forgot, loginButton);
		
		VBox loginPane = new VBox();//pane that holds temp and buttonspane
		loginPane.setSpacing(25);
		loginPane.getChildren().addAll(temp, buttonsPane);
		loginPane.setAlignment(Pos.CENTER);
		
		VBox signUpPane = new VBox();//pane that holds the sign-up message and button
		signUpPane.spacingProperty().bind(signUpPane.widthProperty().divide(3.6));
		signUpPane.setAlignment(Pos.CENTER);
		signUpPane.setStyle("-fx-background-color: purple;");
		Label signUpLabel = new Label("If you are a new \nadministrator you can sign-up \nnow using the product key!");
		signUpLabel.setAlignment(Pos.CENTER);
		signUpLabel.setTextAlignment(TextAlignment.CENTER);
		signUpLabel.setTextFill(Color.WHITE);
		signUpLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 15));
		Button signUpButton = new Button("Sign-Up");
		signUpButton.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15; -fx-text-fill: purple;");
		signUpButton.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));
		signUpButton.setAlignment(Pos.BOTTOM_CENTER);
		signUpButton.setPrefHeight(40);
		signUpButton.setPrefWidth(300);
		signUpButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				signUpButton.setStyle("-fx-background-color: gainsboro; -fx-border-radius:15; -fx-background-radius:15; -fx-text-fill: purple;");
				signUpButton.setCursor(Cursor.HAND);
			}
		});
		signUpButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				signUpButton.setStyle("-fx-background-color: white; -fx-border-radius:15; -fx-background-radius:15; -fx-text-fill: purple;");
			}
		});
		signUpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
				(new SignUpPage()).show(primaryStage);
				}catch(Exception a) {
					System.out.println("Something went wrong " + a.getMessage());
				}
			}
		});
		signUpPane.getChildren().addAll(signUpLabel, signUpButton);
				
		pane.setAlignment(Pos.CENTER);
		pane.setStyle("-fx-background-color: purple;");
						
		HBox supreme = new HBox();//pane that holds login pane, middle line, and sign-up pane
		supreme.setSpacing(0);
		supreme.setStyle("-fx-background-color: purple;");
		supreme.setAlignment(Pos.CENTER);
		supreme.spacingProperty().bind(supreme.widthProperty().divide(10));
		
		Line middleLine = new Line(1000, 250, 1000, 670);
		middleLine.setStyle("-fx-stroke: white;");
		middleLine.setStrokeWidth(3);
		supreme.getChildren().addAll(loginPane, middleLine, signUpPane);
				
		pane.setAlignment(Pos.CENTER);
		pane.setStyle("-fx-background-color: purple;");
		pane.getChildren().addAll(loginLabel, supreme);
		
		Scene scene = new Scene(pane, 800, 550);
		
		scene.setOnKeyPressed(e->{
			if(e.getCode().equals(KeyCode.ENTER)) {
				UserStack users = new UserStack();
				
				try {
					User us = users.findUser(usernameField.getText());
					System.out.println(us);
					if(!us.getPassword().equals(passwordField.getText())) 
						throw new InvalidPasswordException("The password entered does not match");
					
					if(us.getStatus().equals(Status.ADMINISTRATOR))
						(new AdminView((Administrator)us)).show(primaryStage);
					else if(us.getStatus().equals(Status.LIBRARIAN))
						(new LibrarianView((Librarian)us)).show(primaryStage);
					else
						(new ManagerView((Manager)us, true)).show(primaryStage);
				}catch(NonExistantUserException e1) {
					Alert alert = new Alert(AlertType.WARNING);
			        alert.setTitle("Error");
			        alert.setHeaderText("User does not exist");
			        alert.setContentText(e1.getMessage());
			        alert.showAndWait();
				}catch(InvalidPasswordException e2) {
					Alert alert = new Alert(AlertType.WARNING);
			        alert.setTitle("Error");
			        alert.setHeaderText("Incorrect password");
			        alert.setContentText(e2.getMessage());
			        alert.showAndWait();
				}
			}
		});
		
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

}



