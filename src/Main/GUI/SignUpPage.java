package Main.GUI;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.time.LocalDate;

import Main.Exceptions.InvalidPasswordException;
import Main.Exceptions.InvalidUsernameException;
import Main.Users.Administrator;
import Main.Users.UserStack;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
import javafx.scene.input.MouseEvent;



public class SignUpPage {
	
	public void show(Stage primaryStage){
		
				VBox finalPane = new VBox();
				finalPane.setSpacing(50);
				finalPane.setStyle("-fx-background-color: purple;");
				finalPane.setAlignment(Pos.CENTER);
				
				Label signUp = new Label("Sign-Up");
				signUp.setAlignment(Pos.CENTER);
				signUp.setTextAlignment(TextAlignment.CENTER);
				signUp.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
				signUp.setTextFill(Color.WHITE);
				
				// ~~~~~~~~~~~~ //
				HBox pageContent = new HBox();
				pageContent.setSpacing(50);
				pageContent.setAlignment(Pos.CENTER);
				pageContent.setStyle("-fx-backgroun-color: purple;");
				
				// ---- //
				VBox generalInformation = new VBox();
				generalInformation.setSpacing(8);
				generalInformation.setStyle("-fx-backgroun-color: purple;");
				generalInformation.setAlignment(Pos.CENTER);
				
				Label generalLabel = new Label("General Information");
				generalLabel.setAlignment(Pos.CENTER);
				generalLabel.setTextAlignment(TextAlignment.CENTER);
				generalLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
				generalLabel.setTextFill(Color.WHITE);
				
				VBox name = new VBox();
				name.setSpacing(1);
				name.setStyle("-fx-backgroun-color: purple;");
				name.setAlignment(Pos.CENTER_LEFT);
				Label nameLabel = new Label("Name: ");
				nameLabel.setAlignment(Pos.TOP_LEFT);
				nameLabel.setTextAlignment(TextAlignment.LEFT);
				nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
				nameLabel.setTextFill(Color.WHITE);
				TextField nameField = new TextField();
				nameField.setId("nameField");
				nameField.setPrefWidth(200);
				nameField.setPrefHeight(40);
				nameField.setPromptText("Your name here..");
				nameField.setFocusTraversable(false);
				nameField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
				nameField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
				name.getChildren().addAll(nameLabel, nameField);
				
				VBox surname = new VBox();
				surname.setSpacing(1);
				surname.setStyle("-fx-backgroun-color: purple;");
				surname.setAlignment(Pos.CENTER_LEFT);
				Label surnameLabel = new Label("Surname: ");
				surnameLabel.setAlignment(Pos.TOP_LEFT);
				surnameLabel.setTextAlignment(TextAlignment.LEFT);
				surnameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
				surnameLabel.setTextFill(Color.WHITE);
				TextField surnameField = new TextField();
				surnameField.setId("surnameField");
				surnameField.setPrefWidth(200);
				surnameField.setPrefHeight(40);
				surnameField.setPromptText("Your surname here..");
				surnameField.setFocusTraversable(false);
				surnameField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
				surnameField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
				surname.getChildren().addAll(surnameLabel, surnameField);
			
				VBox email = new VBox();
				email.setSpacing(1);
				email.setStyle("-fx-backgroun-color: purple;");
				email.setAlignment(Pos.CENTER_LEFT);
				Label emailLabel = new Label("E-mail: ");
				emailLabel.setAlignment(Pos.TOP_LEFT);
				emailLabel.setTextAlignment(TextAlignment.LEFT);
				emailLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
				emailLabel.setTextFill(Color.WHITE);
				TextField emailField = new TextField();
				emailField.setId("emailField");
				emailField.setPrefWidth(200);
				emailField.setPrefHeight(40);
				emailField.setPromptText("random@gmail.com");
				emailField.setFocusTraversable(false);
				emailField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
				emailField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
				email.getChildren().addAll(emailLabel, emailField);
				
				VBox phone = new VBox();
				phone.setSpacing(1);
				phone.setStyle("-fx-background-color: purple;");
				phone.setAlignment(Pos.CENTER_LEFT);
				Label phoneLabel = new Label("Phone-Number: ");
				phoneLabel.setAlignment(Pos.TOP_LEFT);
				phoneLabel.setTextAlignment(TextAlignment.LEFT);
				phoneLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
				phoneLabel.setTextFill(Color.WHITE);
				TextField phoneField = new TextField();
				phoneField.setId("phoneField");
				phoneField.setPrefWidth(200);
				phoneField.setPrefHeight(40);
				phoneField.setPromptText("+3556xxxxxxx");
				phoneField.setFocusTraversable(false);
				phoneField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
				phoneField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
				phone.getChildren().addAll(phoneLabel, phoneField);
				
				VBox birthdayFinal = new VBox();
				birthdayFinal.setSpacing(1);
				birthdayFinal.setStyle("-fx-background-color: purple;");
				birthdayFinal.setAlignment(Pos.CENTER_LEFT);
				Label birthdayLabel = new Label("Birthday: ");
				birthdayLabel.setTextAlignment(TextAlignment.LEFT);
				birthdayLabel.setTextFill(Color.WHITE);
				birthdayLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
				DatePicker birthday = new DatePicker();
				birthday.setId("birthday");
				birthday.setStyle("-fx-background-color: white;");
				birthday.setFocusTraversable(false);
				birthday.setValue(LocalDate.now());
				birthday.setPrefSize(300, 30);
				birthdayFinal.getChildren().addAll(birthdayLabel, birthday);
				
				generalInformation.getChildren().addAll(generalLabel, name, surname, email, phone, birthdayFinal);
				// ------ //
				
			    // ------ //
				VBox credentials = new VBox();
				credentials.setSpacing(20);
				credentials.setStyle("-fx-background-color: purple;");
				credentials.setAlignment(Pos.CENTER);
				
				Label credentialLabel = new Label("Credentials");
				credentialLabel.setAlignment(Pos.CENTER);
				credentialLabel.setTextAlignment(TextAlignment.CENTER);
				credentialLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
				credentialLabel.setTextFill(Color.WHITE);
				
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
				usernameField.setId("usernameField");
				usernameField.setPrefWidth(200);
				usernameField.setPrefHeight(40);
				usernameField.setPromptText("username");
				usernameField.setFocusTraversable(false);
				usernameField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
				usernameField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
				HBox userPane = new HBox();//pane that holds the user field and icon
				userPane.setSpacing(10);
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
				passwordField.setId("passwordField");
				passwordField.setPrefHeight(40);
				passwordField.setPrefWidth(200);
				passwordField.setPromptText("password");
				passwordField.setFocusTraversable(false);
				passwordField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
				passwordField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
				FileInputStream inputStream3 = null;
				try {
					inputStream3 = new FileInputStream("Images/approved.png");
				}catch(java.io.FileNotFoundException e) {
					System.out.println("Check the images folder and add approved.png");
				}
				Image retypeImg = new Image(inputStream3);
				ImageView retypeIcon = new ImageView(retypeImg);
				retypeIcon.setFitHeight(20);
				retypeIcon.setFitWidth(20);
				PasswordField retype = new PasswordField();
				retype.setId("retype");
				retype.setPrefHeight(40);
				retype.setPrefWidth(200);
				retype.setPromptText("re-type password");
				retype.setFocusTraversable(false);
				retype.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
				retype.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
				HBox passwordPane = new HBox();//pane that holds the password field and icon
				passwordPane.setSpacing(10);
				passwordPane.setAlignment(Pos.CENTER);
				passwordPane.getChildren().addAll(passIcon, passwordField);
				HBox retypePane = new HBox();
				retypePane.setSpacing(10);
				retypePane.setAlignment(Pos.CENTER);
				retypePane.getChildren().addAll(retypeIcon, retype);
				VBox totalPass = new VBox();
				totalPass.setSpacing(3);
				totalPass.setAlignment(Pos.CENTER);
				totalPass.getChildren().addAll(passwordPane, retypePane);
				
				Line hLine = new Line(550, 350, 800, 350);
				hLine.setStyle("-fx-stroke: white");
				hLine.setStrokeWidth(3);
				
				VBox productKey = new VBox();
				productKey.setSpacing(1);
				productKey.setStyle("-fx-background-color: purple;");
				productKey.setAlignment(Pos.CENTER_LEFT);
				Label keyLabel = new Label("Enter the product key: ");
				keyLabel.setAlignment(Pos.TOP_LEFT);
				keyLabel.setTextAlignment(TextAlignment.LEFT);
				keyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
				keyLabel.setTextFill(Color.WHITE);
				TextField keyField = new TextField();
				keyField.setId("keyField");
				keyField.setPrefWidth(200);
				keyField.setPrefHeight(40);
				keyField.setPromptText("XXXXXX");
				keyField.setFocusTraversable(false);
				keyField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
				keyField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
				productKey.getChildren().addAll(keyLabel, keyField);
				
				credentials.getChildren().addAll(credentialLabel, userPane, totalPass, hLine, productKey);
				// ------ //
				
				Line vLine = new Line(600, 250, 600, 570);
				vLine.setStyle("-fx-stroke: white;");
				vLine.setStrokeWidth(3);
				
				pageContent.getChildren().addAll(generalInformation, vLine, credentials);
				// ~~~~~~~~~ //
				
				Button signUpButton = new Button("Sign-Up");
				signUpButton.setId("signUpButton");
				signUpButton.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15; -fx-text-fill: purple;");
				signUpButton.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));
				signUpButton.setAlignment(Pos.CENTER);
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
				signUpButton.setOnAction(e -> {
					
					if(keyField.getText().equals("123456")) {
						try {
							LocalDate temp = birthday.getValue();
							Administrator newAdmin = new Administrator(nameField.getText(), surnameField.getText(), usernameField.getText(), passwordField.getText(),
									emailField.getText(), phoneField.getText(), temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear());
							UserStack users = new UserStack();
							users.addUser(newAdmin);
							newAdmin = (Administrator) users.findUser(newAdmin.getUsername());
							(new AdminView(newAdmin)).show(primaryStage);
						}catch(InvalidUsernameException e1) {
							Alert alert = new Alert(AlertType.WARNING);
					        alert.setTitle("Error");
					        alert.setHeaderText("Invalid username entered");
					        alert.setContentText(e1.getMessage());
					        alert.showAndWait();
						}catch(InvalidPasswordException e2) {
							Alert alert = new Alert(AlertType.WARNING);
					        alert.setTitle("Error");
					        alert.setHeaderText("Invalid password entered");
					        alert.setContentText(e2.getMessage());
					        alert.showAndWait();
						}catch (Exception e1) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("Invalid information passed!");
							alert.setContentText(e1.getMessage());
							alert.showAndWait();
						}
					}else {
						Alert alert = new Alert(AlertType.WARNING);
				        alert.setTitle("Key mismatch");
				        alert.setHeaderText("The product key you entered is not valid!!");
				        alert.setContentText("Please check and verify your product key before trying again");
				        alert.showAndWait();
					}
					
				});
				
				Button cancelButton = new Button("Cancel");
				cancelButton.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15; -fx-text-fill: purple;");
				cancelButton.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));
				cancelButton.setAlignment(Pos.CENTER);
				cancelButton.setPrefHeight(40);
				cancelButton.setPrefWidth(300);
				cancelButton.setOnMouseEntered(e->{
					cancelButton.setStyle("-fx-background-color: gainsboro; -fx-border-radius:15; -fx-background-radius:15; -fx-text-fill: purple;");
					cancelButton.setCursor(Cursor.HAND);
				});
				cancelButton.setOnMouseExited(e->{
					cancelButton.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15; -fx-text-fill: purple;");
				});
				cancelButton.setOnAction(e->{
					(new LoginPage()).show(primaryStage);
				});
				HBox buttonsPane = new HBox();
				buttonsPane.setStyle("-fx-background-color: purple;");
				buttonsPane.setSpacing(20);
				buttonsPane.getChildren().addAll(signUpButton, cancelButton);
				buttonsPane.setAlignment(Pos.CENTER);
				
				finalPane.getChildren().addAll(signUp, pageContent, buttonsPane);
				
				Scene scene = new Scene(finalPane, 800, 550);
				primaryStage.setTitle("Sign-Up");
				primaryStage.setScene(scene);
				primaryStage.show();
				
	}

}
