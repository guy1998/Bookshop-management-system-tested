package GUI;

import java.io.FileInputStream;
import java.time.LocalDate;

import Exceptions.InvalidEmployeeInfo;
import Exceptions.InvalidPasswordException;
import Exceptions.InvalidUsernameException;
import Exceptions.UserAlreadyExistsException;
import Users.Access;
import Users.Administrator;
import Users.Status;
import Users.User;
import Users.UserStack;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CreateUserView {

	private Administrator admin;

	public CreateUserView(Administrator admin) {
		this.admin = admin;
	}
	
	public void show(Stage primaryStage) {
		
		BorderPane content = new BorderPane();
		content.setStyle("-fx-background-color: purple;");
		
		//getting all the icons for the page
		FileInputStream input = null;
		ImageView [] icons = new ImageView[3];
		for(int i=0; i<3; i++) {
			try {
				if(i == 0)
					input = new FileInputStream("Images/left-arrow.png");
				else if(i == 1)
					input = new FileInputStream("Images/user(1).png");
				else
					input = new FileInputStream("Images/key.png");
			}catch(java.io.FileNotFoundException e) {
				System.out.println("Check the images folder");
			}
			icons[i] = new ImageView(new Image(input));
			if(i == 0) {
				icons[i].setFitHeight(50);
				icons[i].setFitWidth(50);
			}else {
				icons[i].setFitHeight(25);
				icons[i].setFitWidth(25);
			}
		}
		
		HBox tape = new HBox();
		tape.setStyle("-fx-background-color: purple;");
		tape.setAlignment(Pos.CENTER_LEFT);
		tape.setPrefHeight(70);
		tape.setSpacing(10);
		tape.setPrefWidth(550);
		Button back = new Button();
		back.setGraphic(icons[0]);
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
		Label create = new Label("Create User");
		create.setStyle("-fx-background-color: purple;");
		create.setTextFill(Color.WHITE);
		create.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		
		tape.getChildren().addAll(back, create);
		
		HBox pageContent = new HBox();
		pageContent.setSpacing(100);
		pageContent.setAlignment(Pos.CENTER);
		pageContent.setStyle("-fx-backgroun-color: purple;");
		
		// ---- //
		VBox generalInformation = new VBox();
		generalInformation.setSpacing(12);
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
		nameField.setPrefWidth(200);
		nameField.setPrefHeight(40);
		nameField.setPromptText("Employee name here..");
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
		surnameField.setPrefWidth(200);
		surnameField.setPrefHeight(40);
		surnameField.setPromptText("Employee surname here..");
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
		birthday.setStyle("-fx-background-color: white;");
		birthday.setFocusTraversable(false);
		birthday.setValue(LocalDate.now());
		birthday.setPrefSize(300, 30);
		birthdayFinal.getChildren().addAll(birthdayLabel, birthday);
		
		generalInformation.getChildren().addAll(generalLabel, name, surname, email, phone, birthdayFinal);
		
		VBox credentials = new VBox();
		credentials.setSpacing(20);
		credentials.setMinHeight(300);
		credentials.setStyle("-fx-background-color: purple;");
		credentials.setAlignment(Pos.CENTER);
		
		Label credentialLabel = new Label("Credentials");
		credentialLabel.setAlignment(Pos.TOP_CENTER);
		credentialLabel.setTextAlignment(TextAlignment.CENTER);
		credentialLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		credentialLabel.setTextFill(Color.WHITE);
		
		
		TextField usernameField = new TextField();
		usernameField.setPrefWidth(200);
		usernameField.setPrefHeight(40);
		usernameField.setPromptText("username");
		usernameField.setFocusTraversable(false);
		usernameField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
		usernameField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
		HBox userPane = new HBox();//pane that holds the user field and icon
		userPane.setSpacing(10);
		userPane.setAlignment(Pos.CENTER);
		userPane.getChildren().add(icons[1]);
		userPane.getChildren().add(usernameField);
		
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefHeight(40);
		passwordField.setPrefWidth(200);
		passwordField.setPromptText("password");
		passwordField.setFocusTraversable(false);
		passwordField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
		passwordField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
		HBox passwordPane = new HBox();//pane that holds the password field and icon
		passwordPane.setSpacing(10);
		passwordPane.setAlignment(Pos.CENTER);
		passwordPane.getChildren().addAll(icons[2], passwordField);
		
		
		Line hLine = new Line(550, 350, 800, 350);
		hLine.setStyle("-fx-stroke: white");
		hLine.setStrokeWidth(3);
		
		VBox empInfo = new VBox();
		empInfo.setSpacing(10);
		empInfo.setStyle("-fx-background-color: purple;");
		empInfo.setAlignment(Pos.CENTER_LEFT);
		Label empLabel = new Label("Employee Info");
		empLabel.setAlignment(Pos.TOP_CENTER);
		empLabel.setTextAlignment(TextAlignment.CENTER);
		empLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		empLabel.setTextFill(Color.WHITE);
		VBox ssn = new VBox();
		ssn.setSpacing(1);
		ssn.setStyle("-fx-backgroun-color: purple;");
		ssn.setAlignment(Pos.CENTER_LEFT);
		Label ssnLabel = new Label("SSN: ");
		ssnLabel.setAlignment(Pos.TOP_LEFT);
		ssnLabel.setTextAlignment(TextAlignment.LEFT);
		ssnLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
		ssnLabel.setTextFill(Color.WHITE);
		TextField ssnField = new TextField();
		ssnField.setPrefWidth(200);
		ssnField.setPrefHeight(30);
		ssnField.setPromptText("XXX-XXXX-XXXX");
		ssnField.setFocusTraversable(false);
		ssnField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
		ssnField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
		ssn.getChildren().addAll(ssnLabel, ssnField);
		VBox salary = new VBox();
		salary.setSpacing(1);
		salary.setStyle("-fx-backgroun-color: purple;");
		salary.setAlignment(Pos.CENTER_LEFT);
		Label salaryLabel = new Label("Salary: ");
		salaryLabel.setAlignment(Pos.TOP_LEFT);
		salaryLabel.setTextAlignment(TextAlignment.LEFT);
		salaryLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
		salaryLabel.setTextFill(Color.WHITE);
		TextField salaryField = new TextField();
		salaryField.setPrefWidth(200);
		salaryField.setPrefHeight(30);
		salaryField.setPromptText("Salary..");
		salaryField.setFocusTraversable(false);
		salaryField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12));
		salaryField.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px;");
		salary.getChildren().addAll(salaryLabel, salaryField);
		HBox combo = new HBox();
		combo.setStyle("-fx-background-color: purple;");
		combo.setSpacing(20);
		combo.setAlignment(Pos.CENTER);
		ComboBox statusBox = new ComboBox(FXCollections.observableArrayList("MANAGER", "LIBRARIAN"));
		statusBox.setPromptText("Status");
		statusBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15");
		ComboBox accessBox = new ComboBox(FXCollections.observableArrayList("FULL", "PARTIAL", "NONE"));
		accessBox.setPromptText("Access");
		accessBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15");
		combo.getChildren().addAll(statusBox, accessBox);
		
		empInfo.getChildren().addAll(empLabel, combo, ssn, salary);
		
		credentials.getChildren().addAll(credentialLabel, userPane, passwordPane, hLine, empInfo);
		
		Line vLine = new Line(600, 250, 600, 570);
		vLine.setStyle("-fx-stroke: white;");
		vLine.setStrokeWidth(3);
		
		pageContent.getChildren().addAll(generalInformation, vLine, credentials);
		
		Button createB = new Button("Create");
		createB.setStyle("-fx-background-color: purple; -fx-text-fill: white; -fx-border-color: white;");
		createB.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));
		createB.setAlignment(Pos.CENTER);
		createB.setPrefHeight(40);
		createB.setPrefWidth(800);
		createB.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				createB.setStyle("-fx-background-color: darkorchid; -fx-text-fill: white; -fx-border-color: white;");
				createB.setCursor(Cursor.HAND);
			}
		});
		createB.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				createB.setStyle("-fx-background-color: purple; -fx-text-fill: white; -fx-border-color: white;");
			}
		});
		createB.setOnAction(e->{
			
		    Access aTemp = null;
		    
		    if(statusBox.getValue() == null) {
		    	Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Error");
		        alert.setHeaderText("Please choose employee status");					        
		        alert.showAndWait();
		        return;
		    }
		    
		    if(accessBox.getValue() == null) {
		    	Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Error");
		        alert.setHeaderText("Please choose employee access");					        
		        alert.showAndWait();
		        return;
		    }
		    else if(accessBox.getValue().toString().equals("FULL"))
		    	aTemp = Access.FULL;
		    else if(accessBox.getValue().toString().equals("PARTIAL"))
		    	aTemp = Access.PARTIAL;
		    else if(accessBox.getValue().toString().equals("NONE"))
		    	aTemp = Access.NONE;
		    
			
		    try {
		    LocalDate ld = birthday.getValue();
		    if(!ssnField.getText().matches("\\d{3}-\\d{4}-\\d{4}"))
					throw new InvalidEmployeeInfo("The SSN should be of the form xxx-xxxx-xxxx where x-es can be any digit but cannot be other characters.");
		    
			User temp = admin.createUser(nameField.getText(), surnameField.getText(), usernameField.getText(), passwordField.getText(), emailField.getText(), phoneField.getText(),
					ld.getDayOfMonth(), ld.getMonthValue(), ld.getYear(), ssnField.getText(), Double.parseDouble(salaryField.getText()), 
					aTemp, statusBox.getValue().toString());
			UserStack users = new UserStack();
			users.addUser(temp);
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Succes");
	        alert.setHeaderText("New user created successfully");
	        alert.showAndWait();
	        (new AdminView(admin)).show(primaryStage);
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
			} catch (NumberFormatException e1) {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Error");
		        alert.setHeaderText("Please enter only NUMBERS int the wage field");					        
		        alert.showAndWait();
			} catch (UserAlreadyExistsException e1) {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Error");
		        alert.setHeaderText(e1.getMessage());					        
		        alert.showAndWait();
			}catch(InvalidEmployeeInfo e1){
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Error");
		        alert.setHeaderText("Invalid employee info");
		        alert.setContentText(e1.getMessage());
		        alert.showAndWait();
			}catch (Exception e3) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(e3.getMessage());
				alert.showAndWait();
			}
		});
		
		content.setTop(tape);
		content.setCenter(pageContent);
		content.setBottom(createB);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Create User");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
}
