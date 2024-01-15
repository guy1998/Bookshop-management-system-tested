package Main.GUI;

import java.io.FileInputStream;

import Main.Exceptions.InvalidEmail;
import Main.Exceptions.InvalidPasswordException;
import Main.Exceptions.InvalidPhoneNumberException;
import Main.Exceptions.InvalidUsernameException;
import Main.Users.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ProfileView {
	
	private User user;
	private UserStack userstack;
	private SimpleStringProperty tempName;
	private SimpleStringProperty tempSurname;
	private SimpleStringProperty tempUsername;
	private SimpleStringProperty tempEmail;
	private SimpleStringProperty tempPhone;

	public ProfileView(User user) {
		
		this.user = user;
		this.userstack = new UserStack();
		tempName = user.getNameProperty();
		tempSurname = user.getSurnameProperty();
		tempUsername = user.getUsernameProperty();
		tempEmail = user.getEmailProperty();
		tempPhone = user.getPhoneProperty();
		
	}
	
	public void show(Stage primaryStage) {
		
		System.out.println(user);
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
			if(user.getStatus() == Status.ADMINISTRATOR)
				(new AdminView((Administrator)user)).show(primaryStage);
			else if(user.getStatus() == Status.MANAGER)
				(new ManagerView((Manager)user, false)).show(primaryStage);
			else
				(new LibrarianView((Librarian)user)).show(primaryStage);
		});
		Label profile = new Label("MyProfile");
		profile.setStyle("-fx-background-color: purple;");
		profile.setTextFill(Color.WHITE);
		profile.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		VBox total = new VBox();
		total.setStyle("-fx-background-color: purple;");
		total.setAlignment(Pos.CENTER);
		total.setSpacing(20);
		GridPane data = new GridPane();
		data.setAlignment(Pos.CENTER);
		Label [] labels = new Label[6];
		String [] s = {
				"Name: " + user.getName(),
				"Surname: " + user.getSurname(),
				"Username: " + user.getUsername(),
				"E-mail: " + user.getEmail(),
				"Phone Number: " + user.getPhone(),
				"Birthday: " + user.getBirthday()
		};
		String [] ids = {"name", "surname", "username", "email", "phone", "birthday"};
		Button [] btns = new Button[6];
		for(int i=0; i<6; i++) {
			labels[i] = new Label(s[i]);
			labels[i].setId(ids[i]);
			labels[i].setStyle("-fx-background-color: purple;");
			labels[i].setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
			labels[i].setTextFill(Color.WHITE);
			labels[i].setAlignment(Pos.CENTER);
			GridPane.setMargin(labels[i], new Insets(10));
			
			
				if(i == 5) {
					btns[i] = new Button("Change password");
					btns[i].setId("passwordButton");
				}else {
					btns[i] = new Button("Edit");
					btns[i].setId(ids[i] + "Button");
				}
				btns[i].setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-radius:10px;");
				if(i == 5)
					btns[i].setPrefSize(200, 40);
				else {
					btns[i].setPrefSize(70, 40);
					GridPane.setMargin(btns[i], new Insets(10));
				}
				btns[i].setAlignment(Pos.CENTER);
				btns[i].setTextFill(Color.PURPLE);
				btns[i].setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
			
			if(i <= 4) {
				data.add(labels[i], 0, i);
				data.add(btns[i], 1, i);
			}
			
		}
		for(Button b:btns) {
			b.setOnMouseEntered(e->{
				b.setCursor(Cursor.HAND);
				b.setStyle("-fx-background-color: gainsboro; -fx-background-radius: 10px; -fx-border-radius:10px;");
			});
			b.setOnMouseExited(e->{
				b.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-radius:10px;");
			});
		}
		
		
		total.getChildren().addAll(data, btns[5]);
		
		StackPane editPrompt = new StackPane();
		editPrompt.setStyle("-fx-background-color: purple;");
		Button [] confirm = new Button[6];
		for(int i=0; i<6; i++) {
			confirm[i] = new Button("Confirm");
			if(i < 5)
				confirm[i].setId(ids[i] + "Confirm");
			else
				confirm[i].setId("passwordConfirm");
			confirm[i].setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-radius:10px;");
			confirm[i].setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
			confirm[i].setPrefSize(230, 40);
			confirm[i].setTextFill(Color.PURPLE);
		}
		for(Button c : confirm) {
			c.setOnMouseEntered(e->{
				c.setCursor(Cursor.HAND);
				c.setStyle("-fx-background-color: gainsboro; -fx-background-radius: 10px; -fx-border-radius:10px;");
			});
			c.setOnMouseExited(e->{
				c.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-radius:10px;");
			});
		}

		VBox editName = new VBox();
		editName.setAlignment(Pos.CENTER);
		editName.setStyle("-fx-background-color: purple;");
		editName.setSpacing(5);
		Label name = new Label("Edit name: ");
		name.setStyle("-fx-background-color: purple;");
		name.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		name.setTextFill(Color.WHITE);
		TextField nameField = new TextField();
		nameField.setId("nameField");
		nameField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		nameField.setPromptText("Name...");
		nameField.setPrefSize(230, 40);
		editName.getChildren().addAll(name, nameField, confirm[0]);
		VBox editSurname = new VBox();
		editSurname.setAlignment(Pos.CENTER);
		editSurname.setStyle("-fx-background-color: purple;");
		editSurname.setSpacing(5);
		Label surname = new Label("Edit surname: ");
		surname.setStyle("-fx-background-color: purple;");
		surname.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		surname.setTextFill(Color.WHITE);
		TextField surnameField = new TextField();
		surnameField.setId("surnameField");
		surnameField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		surnameField.setPromptText("Surname...");
		surnameField.setPrefSize(230, 40);
		editSurname.getChildren().addAll(surname, surnameField, confirm[1]);
		VBox editUsername = new VBox();
		editUsername.setAlignment(Pos.CENTER);
		editUsername.setStyle("-fx-background-color: purple;");
		editUsername.setSpacing(5);
		Label username = new Label("Edit username: ");
		username.setStyle("-fx-background-color: purple;");
		username.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		username.setTextFill(Color.WHITE);
		TextField usernameField = new TextField();
		usernameField.setId("usernameField");
		usernameField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		usernameField.setPromptText("username...");
		usernameField.setPrefSize(230, 40);
		editUsername.getChildren().addAll(username, usernameField, confirm[2]);
		VBox editEmail = new VBox();
		editEmail.setAlignment(Pos.CENTER);
		editEmail.setStyle("-fx-background-color: purple;");
		editEmail.setSpacing(5);
		Label email = new Label("Edit email: ");
		email.setStyle("-fx-background-color: purple;");
		email.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		email.setTextFill(Color.WHITE);
		TextField emailField = new TextField();
		emailField.setId("emailField");
		emailField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		emailField.setPromptText("email...");
		emailField.setPrefSize(230, 40);
		editEmail.getChildren().addAll(email, emailField, confirm[3]);
		VBox editPhone = new VBox();
		editPhone.setAlignment(Pos.CENTER);
		editPhone.setStyle("-fx-background-color: purple;");
		editPhone.setSpacing(5);
		Label phone = new Label("Edit phone number: ");
		phone.setStyle("-fx-background-color: purple;");
		phone.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		phone.setTextFill(Color.WHITE);
		TextField phoneField = new TextField();
		phoneField.setId("phoneField");
		phoneField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		phoneField.setPromptText("phone...");
		phoneField.setPrefSize(230, 40);
		editPhone.getChildren().addAll(phone, phoneField, confirm[4]);
		VBox editPassword = new VBox();
		editPassword.setAlignment(Pos.CENTER);
		editPassword.setStyle("-fx-background-color: purple;");
		editPassword.setSpacing(5);
		Label password = new Label("Edit password: ");
		password.setStyle("-fx-background-color: purple;");
		password.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		password.setTextFill(Color.WHITE);
		PasswordField passwordField = new PasswordField();
		passwordField.setId("passwordField");
		passwordField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		passwordField.setPromptText("old password...");
		passwordField.setPrefSize(230, 40);
		PasswordField npField = new PasswordField();
		npField.setId("npField");
		npField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		npField.setPromptText("new password...");
		npField.setPrefSize(230, 40);
		editPassword.getChildren().addAll(password, passwordField, npField, confirm[5]);
		Label nth = new Label("Nothing to edit right now");
		nth.setAlignment(Pos.CENTER);
		nth.setStyle("-fx-background-color: purple;");
		nth.setTextFill(Color.WHITE);
		nth.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
		editPrompt.getChildren().add(nth);
		
		
		btns[0].setOnAction(e->{
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(editName);
		});
		btns[1].setOnAction(e->{
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(editSurname);
		});
		btns[2].setOnAction(e->{
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(editUsername);
		});
		btns[3].setOnAction(e->{
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(editEmail);
		});
		btns[4].setOnAction(e->{
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(editPhone);
		});
		btns[5].setOnAction(e->{
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(editPassword);
		});
		tempName.addListener(ov->{
			labels[0].setText("Name: " + user.getName());
		});
		tempSurname.addListener(ov->{
			labels[1].setText("Surname: " + user.getSurname());
		});
		tempUsername.addListener(ov->{
			labels[2].setText("Username: " + user.getUsername());
		});
		tempEmail.addListener(e->{
			labels[3].setText("E-mail: " + user.getEmail());
		});
		tempPhone.addListener(e->{
			labels[4].setText("Phone number: " + user.getPhone());
		});
		confirm[0].setOnAction(e->{
			userstack.modifyName(user, nameField.getText());
			user.setName(nameField.getText());
			tempName.setValue(user.getName());
			nameField.clear();
			tempName.addListener(ov->{
			labels[0].setText("Name: " + user.getName());
		});
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(nth);
		});
		confirm[1].setOnAction(e->{
			userstack.modifySurname(user, surnameField.getText());
			user.setSurname(surnameField.getText());
			tempSurname.setValue(user.getSurname());
			surnameField.clear();
			tempSurname.addListener(ov->{
			labels[1].setText("Surname: " + user.getSurname());
			});
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(nth);
		});
		confirm[2].setOnAction(e->{
			try {
			userstack.modifyUsername(user, usernameField.getText());
			user.setUserName(usernameField.getText());
			tempUsername.setValue(user.getUsername());
			usernameField.clear();
			tempUsername.addListener(ov->{
				labels[2].setText("Username: " + user.getUsername());
			});
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(nth);
			}catch(InvalidUsernameException e1) {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Error");
		        alert.setHeaderText("Invalid username entered");
		        alert.setContentText(e1.getMessage());
		        alert.showAndWait();
			}
		});
		confirm[3].setOnAction(e->{
			try{
				userstack.modifyEmail(user, emailField.getText());
				user.setEmail(emailField.getText());
				tempEmail.setValue(user.getEmail());
				emailField.clear();
				tempEmail.addListener(ov->{
				labels[3].setText("Email: " + user.getEmail());
				});
				editPrompt.getChildren().clear();
				editPrompt.getChildren().add(nth);
			}catch(InvalidEmail e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error with email");
				alert.setContentText(e1.getMessage());
				alert.showAndWait();
			}
		});
		confirm[4].setOnAction(e->{
			try {
				userstack.modifyPhone(user, phoneField.getText());
				user.setPhone(phoneField.getText());
				tempPhone.setValue(user.getPhone());
				phoneField.clear();
				tempPhone.addListener(ov->{
				labels[4].setText("Phone number: " + user.getPhone());
				});
				editPrompt.getChildren().clear();
				editPrompt.getChildren().add(nth);
			}catch(InvalidPhoneNumberException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error with the phone number");
				alert.setContentText(e1.getMessage()
				);
				alert.showAndWait();
			}
		});
		confirm[5].setOnAction(e->{
			try {
				if(!passwordField.getText().equals(user.getPassword()))
					throw new InvalidPasswordException("Your current password does not match");
			userstack.modifyPassword(user, npField.getText());
			passwordField.clear();
			npField.clear();
			Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Success");
	        alert.setHeaderText("Password changed successfully");
			alert.setContentText("Password changed successfully");
	        alert.showAndWait();
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(nth);
			}catch(InvalidPasswordException e1) {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Error");
		        alert.setHeaderText("Invalid Password entered");
		        alert.setContentText(e1.getMessage());
		        alert.showAndWait();
			}
		});
		
		Line vLine = new Line(400, 30, 400, 450);
		vLine.setStroke(Color.WHITE);
		vLine.setStrokeWidth(5);
	
		
		HBox mainField = new HBox();
		mainField.setStyle("-fx-background-color: purple;");
		mainField.setSpacing(30);
		mainField.setAlignment(Pos.CENTER);
		mainField.getChildren().addAll(total, vLine, editPrompt);
		
		tape.getChildren().addAll(back, profile);
		
		content.setTop(tape);
		content.setCenter(mainField);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Profile");
		primaryStage.show();
		
	}
	
}
