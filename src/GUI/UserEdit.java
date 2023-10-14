package GUI;

import java.io.File;
import java.io.FileInputStream;

import Users.Access;
import Users.Administrator;
import Users.Employee;
import Users.Librarian;
import Users.Manager;
import Users.Status;
import Users.User;
import Users.UserStack;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UserEdit {
	
	private Administrator admin;
	private User editable;
	
	public UserEdit(Administrator admin, User editable) {
		this.admin = admin;
		this.editable = editable;
	}
	
	public void show(Stage primaryStage) {
		
		SimpleStringProperty nameProp = editable.getNameProperty();
		SimpleStringProperty surnameProp = editable.getSurnameProperty();
		SimpleStringProperty emailProp = editable.getEmailProperty();
		SimpleStringProperty phoneProp = editable.getPhoneProperty();
		SimpleStringProperty ssnProp = new SimpleStringProperty(((Employee)editable).getSSN());
		SimpleDoubleProperty salaryProp = new SimpleDoubleProperty(((Employee)editable).getSalary());
		
		BorderPane content = new BorderPane();
		
		HBox tape = new HBox();
		tape.setStyle("-fx-background-color: purple;");
		tape.setAlignment(Pos.CENTER_LEFT);
		tape.setPrefHeight(70);
		tape.setSpacing(10);
		tape.setPrefWidth(550);
		FileInputStream input = null;
		try {
			input = new FileInputStream("Images/left-arrow.png");
		}catch(java.io.FileNotFoundException e) {
			System.out.println("Check images for bookshop");
		}
		ImageView backIcon = new ImageView(new Image(input));
		backIcon.setFitHeight(50);
		backIcon.setFitWidth(50);
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
			if(editable.getStatus().equals(Status.LIBRARIAN))
				(new LibrarianStats(admin, (Librarian)editable)).show(primaryStage);
			else 
				(new EmployeeView(admin)).show(primaryStage);
		});
		Label editUser = new Label("Edit User");
		editUser.setStyle("-fx-background-color: purple;");
		editUser.setTextFill(Color.WHITE);
		editUser.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		tape.getChildren().addAll(back, editUser);
		
		HBox mainField = new HBox();
		mainField.setStyle("-fx-background-color: white;");
		mainField.setSpacing(60);
		mainField.setPadding(new Insets(20));
		mainField.setAlignment(Pos.CENTER);
		mainField.setPrefSize(800, 430);
		VBox buttonBox = new VBox();
		buttonBox.setStyle("-fx-background-color: white;");
		buttonBox.setSpacing(10);
		buttonBox.setAlignment(Pos.CENTER);
		Button [] btns = new Button[6];
		for(int i=0; i<6; i++) {
			Button temp;
			if(i == 0)
				temp = new Button("Name: " + editable.getName());
			else if(i == 1)
				temp = new Button("Surname: " + editable.getSurname());
			else if(i == 2)
				temp = new Button("E-mail: " + editable.getEmail());
			else if(i ==3)
				temp = new Button("Phone: " + editable.getPhone());
			else if(i == 4)
				temp = new Button("SSN: " + ((Employee)editable).getSSN());
			else 
				temp = new Button("Salary: " + ((Employee)editable).getSalary());
			temp.setStyle("-fx-background-color: white;");
			temp.setPrefSize(300, 30);
			FileInputStream in = null;
			try {
				in = new FileInputStream("Images/edit.png");
			}catch(java.io.FileNotFoundException e) {
				System.out.println("edit img not found");
			}
			ImageView icon = new ImageView(new Image(in));
			icon.setFitHeight(30);
			icon.setFitWidth(30);
			temp.setOnMouseEntered(e->{
				temp.setStyle("-fx-background-color: gainsboro;");
				temp.setCursor(Cursor.HAND);
			});
			temp.setOnMouseExited(e->{
				temp.setStyle("-fx-background-color: white;");
			});
			temp.setGraphic(icon);
			temp.setAlignment(Pos.CENTER_LEFT);
			temp.setTextFill(Color.PURPLE);
			temp.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
			btns[i] = temp;
		}
		Access [] access = new Access[3];
		access[0] = Access.FULL;
		access[1] = Access.PARTIAL;
		access[2] = Access.NONE;
		ComboBox<Access> cb = new ComboBox<>(FXCollections.observableArrayList(access));
		cb.setStyle("-fx-background-color: white; -fx-border-color: purple; -fx-border-radius: 15px; -fx-background-radius: 15px;");
		cb.setPrefSize(300, 30);
		cb.setValue(((Employee)editable).getPermission());
		buttonBox.getChildren().addAll(btns);
		buttonBox.getChildren().add(cb);
		
		StackPane editPrompt = new StackPane();
		editPrompt.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-border-radius: 20px;");
		editPrompt.setPrefSize(400, 300);
		editPrompt.setPadding(new Insets(20));
		Button [] confirm = new Button[6];
		for(int i=0; i<6; i++) {
			confirm[i] = new Button("Confirm");
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
		surnameField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		surnameField.setPromptText("Surname...");
		surnameField.setPrefSize(230, 40);
		editSurname.getChildren().addAll(surname, surnameField, confirm[1]);
		VBox editSsn = new VBox();
		editSsn.setAlignment(Pos.CENTER);
		editSsn.setStyle("-fx-background-color: purple;");
		editSsn.setSpacing(5);
		Label ssn = new Label("Edit ssn: ");
		ssn.setStyle("-fx-background-color: purple;");
		ssn.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		ssn.setTextFill(Color.WHITE);
		TextField ssnField = new TextField();
		ssnField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		ssnField.setPromptText("ssn...");
		ssnField.setPrefSize(230, 40);
		editSsn.getChildren().addAll(ssn, ssnField, confirm[2]);
		VBox editEmail = new VBox();
		editEmail.setAlignment(Pos.CENTER);
		editEmail.setStyle("-fx-background-color: purple;");
		editEmail.setSpacing(5);
		Label email = new Label("Edit email: ");
		email.setStyle("-fx-background-color: purple;");
		email.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		email.setTextFill(Color.WHITE);
		TextField emailField = new TextField();
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
		phoneField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		phoneField.setPromptText("phone...");
		phoneField.setPrefSize(230, 40);
		editPhone.getChildren().addAll(phone, phoneField, confirm[4]);
		VBox editSalary = new VBox();
		editSalary.setAlignment(Pos.CENTER);
		editSalary.setStyle("-fx-background-color: purple;");
		editSalary.setSpacing(5);
		Label salary = new Label("Edit salary: ");
		salary.setStyle("-fx-background-color: purple;");
		salary.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		salary.setTextFill(Color.WHITE);
		TextField salaryField = new TextField();
		salaryField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		salaryField.setPromptText("salary...");
		salaryField.setPrefSize(230, 40);
		editSalary.getChildren().addAll(salary, salaryField, confirm[5]);
		Label nth = new Label("Nothing to edit right now");
		nth.setAlignment(Pos.CENTER);
		nth.setStyle("-fx-background-color: purple;");
		nth.setTextFill(Color.WHITE);
		nth.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
		editPrompt.getChildren().add(nth);
		
		nameProp.addListener(ov->{
			btns[0].setText("Name: " + nameProp.getValue());
		});
		surnameProp.addListener(ov->{
			btns[1].setText("Surname: " + surnameProp.getValue());
		});
		emailProp.addListener(ov->{
			btns[2].setText("E-mail: " + emailProp.getValue());
		});
		phoneProp.addListener(ov->{
			btns[3].setText("Phone: " + phoneProp.getValue());
		});
		ssnProp.addListener(ov->{
			btns[4].setText("SSN: " + ssnProp.getValue());
		});
		salaryProp.addListener(ov->{
			btns[5].setText("Salary: " + salaryProp.getValue());
		});
		
		btns[0].setOnAction(e->{
			editPrompt.getChildren().clear();
			editPrompt.getChildren().addAll(editName);
		});
		btns[1].setOnAction(e->{
			editPrompt.getChildren().clear();
			editPrompt.getChildren().addAll(editSurname);
		});
		btns[2].setOnAction(e->{
			editPrompt.getChildren().clear();
			editPrompt.getChildren().addAll(editEmail);
		});
		btns[3].setOnAction(e->{
			editPrompt.getChildren().clear();
			editPrompt.getChildren().addAll(editPhone);
		});
		btns[4].setOnAction(e->{
			editPrompt.getChildren().clear();
			editPrompt.getChildren().addAll(editSsn);
		});
		btns[5].setOnAction(e->{
			editPrompt.getChildren().clear();
			editPrompt.getChildren().addAll(editSalary);
		});
		
		confirm[0].setOnAction(e->{
			nameProp.setValue(nameField.getText());
			nameField.clear();
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(nth);
		});
		confirm[1].setOnAction(e->{
			surnameProp.setValue(surnameField.getText());
			surnameField.clear();
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(nth);
		});
		confirm[2].setOnAction(e->{
			ssnProp.setValue(ssnField.getText());
			ssnField.clear();
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(nth);
		});
		confirm[3].setOnAction(e->{
			emailProp.setValue(emailField.getText());
			emailField.clear();
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(nth);
		});
		confirm[4].setOnAction(e->{
			phoneProp.setValue(phoneField.getText());
			phoneField.clear();
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(nth);
		});
		confirm[5].setOnAction(e->{
			salaryProp.setValue(Double.parseDouble(salaryField.getText()));
			salaryField.clear();
			editPrompt.getChildren().clear();
			editPrompt.getChildren().add(nth);
		});
		
		Button save = new Button("Save");
		save.setStyle("-fx-background-color: purple;");
		save.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
		save.setPrefSize(400, 50);
		save.setTextFill(Color.WHITE);
		save.setOnMouseEntered(e->{
			save.setCursor(Cursor.HAND);
			save.setStyle("-fx-background-color: darkorchid;");
		});
		save.setOnMouseExited(e->{
			save.setStyle("-fx-background-color: purple;");
		});
		save.setOnAction(e->{
			UserStack users = new UserStack();
			try {
				users.modifyName(editable, nameProp.getValue());
				editable.setName(nameProp.getValue());
				users.modifySurname(editable, surnameProp.getValue());
				editable.setSurname(surnameProp.getValue());
				users.modifyEmail(editable, emailProp.getValue());
				editable.setEmail(emailProp.getValue());
				users.modifyPhone(editable, phoneProp.getValue());
				editable.setPhone(phoneProp.getValue());
				users.modifySSN(editable, ssnProp.getValue());
				((Employee)editable).setSSN(ssnProp.getValue());
				users.modifySalary(editable, salaryProp.getValue());
				((Employee)editable).setSalary(salaryProp.getValue());
				users.modifyPermission(editable, cb.getValue());
				((Employee)editable).setPermission(cb.getValue());
				if(editable.getStatus().equals(Status.LIBRARIAN))
					(new LibrarianStats(admin, (Librarian)editable)).show(primaryStage);
				else
					(new EmployeeView(admin)).show(primaryStage);
			}catch(Exception e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(e1.getMessage());
				alert.showAndWait();
			}
		});
		Button delete = new Button("Delete");
		delete.setStyle("-fx-background-color: purple;");
		delete.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
		delete.setPrefSize(400, 50);
		delete.setTextFill(Color.WHITE);
		delete.setOnMouseEntered(e->{
			delete.setCursor(Cursor.HAND);
			delete.setStyle("-fx-background-color: darkorchid;");
		});
		delete.setOnMouseExited(e->{
			delete.setStyle("-fx-background-color: purple;");
		});
		delete.setOnAction(e->{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Warning");
			alert.setHeaderText("You are about to delete this user");
			alert.setContentText("Do you want to proceed?");
			alert.showAndWait();
	        if(alert.getResult() == ButtonType.OK) {
	        	UserStack users = new UserStack();
	        	try {
	        		File mesagges = new File(editable.getUserId() + ".msg");
	        		mesagges.delete();
	        		File bills = new File(editable.getUserId() + ".dat");
	        		if(bills.exists())
	        			bills.delete();
	        		File txtBills = new File(editable.getUserId());
	        		if(txtBills.exists())
	        			txtBills.delete();
	        		users.deleteUser(editable.getUsername());
	        	}catch(Exception e1) {
	        		System.out.println("Should never happen");
	        	}
	        	(new EmployeeView(admin)).show(primaryStage);
	        }
		});
		HBox buttonsPane = new HBox();
		buttonsPane.setPrefSize(800, 50);
		buttonsPane.setStyle("-fx-background-color: purple;");
		buttonsPane.setSpacing(0);
		buttonsPane.getChildren().addAll(save, delete);
		
		mainField.getChildren().addAll(buttonBox, editPrompt);
		
		content.setTop(tape);
		content.setCenter(mainField);
		content.setBottom(buttonsPane);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Edit user");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

}
