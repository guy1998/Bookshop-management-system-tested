package Main.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import Main.Backend.Exceptions.BookExistsException;
import Main.Backend.Exceptions.InvalidBookInfo;
import Main.Backend.Products.Author;
import Main.Backend.Products.Book;
import Main.Backend.Products.BookStock;
import Main.Backend.Products.Transaction;
import Main.Backend.Products.TransactionControl;
import Main.Backend.Users.Administrator;
import Main.Backend.Users.Manager;
import Main.Backend.Users.Status;
import Main.Backend.Users.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class AddBookView {
	
	private User user;
	private boolean categoryAdded;
	
	public AddBookView(User user) {
		this.user = user;
	}
	
	public AddBookView(User user, boolean categoryAdded) {
		this.user = user;
		this.categoryAdded = categoryAdded;
	}

	public void show(Stage primaryStage) {
		
		ArrayList<Author> authors = new ArrayList<>();
		BorderPane content = new BorderPane();
		
		HBox header = new HBox();
		header.setStyle("-fx-background-color: purple;");
		header.setAlignment(Pos.BOTTOM_LEFT);
		header.setPadding(new Insets(20));
		header.setPrefHeight(40);
		Label newMess = new Label("Add Book");
		newMess.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		newMess.setTextFill(Color.WHITE);
		header.getChildren().add(newMess);
		
		HBox mainField = new HBox();
		mainField.setStyle("-fx-background-color: purple;");
		mainField.setAlignment(Pos.CENTER);
		mainField.setSpacing(60);
		mainField.setPadding(new Insets(30));
		mainField.setPrefSize(800,500);
		
		VBox bookInfo = new VBox();
		bookInfo.setStyle("-fx-background-color: purple;");
		bookInfo.setSpacing(10);
		bookInfo.setAlignment(Pos.TOP_CENTER);
		GridPane fields = new GridPane();
		fields.setStyle("-fx-background-color: purple;");
		Label book = new Label("Book Information");
		book.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		book.setTextFill(Color.WHITE);
		VBox titleBox = new VBox();
		titleBox.setSpacing(2);
		titleBox.setStyle("-fx-background-color: purple;");
		titleBox.setAlignment(Pos.CENTER_LEFT);
		Label title = new Label("Title:");
		title.setTextFill(Color.WHITE);
		title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
		TextField titleField = new TextField();
		titleField.setFocusTraversable(false);
		titleField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		titleField.setPromptText("book title...");
		titleField.setPrefSize(200, 40);
		titleField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 13));
		titleBox.getChildren().addAll(title, titleField);
		VBox isbnBox = new VBox();
		isbnBox.setSpacing(2);
		isbnBox.setStyle("-fx-background-color: purple;");
		isbnBox.setAlignment(Pos.CENTER_LEFT);
		Label isbn = new Label("ISBN:");
		isbn.setTextFill(Color.WHITE);
		isbn.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
		TextField isbnField = new TextField();
		isbnField.setFocusTraversable(false);
		isbnField.setPromptText("ISBN...");
		isbnField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		isbnField.setPrefSize(200, 40);
		isbnField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 13));
		isbnBox.getChildren().addAll(isbn, isbnField);
		VBox categoryBox = new VBox();
		categoryBox.setSpacing(2);
		categoryBox.setStyle("-fx-background-color: purple;");
		categoryBox.setAlignment(Pos.CENTER_LEFT);
		Label category = new Label("Category:");
		category.setTextFill(Color.WHITE);
		category.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
		ArrayList<String> categories = new ArrayList<>();
		File cFile = new File("Database/Categories.txt");
		Scanner cIn = null;
		try {
			cIn = new Scanner(cFile);
		}catch(java.io.FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		while(cIn != null && cIn.hasNext()) {
			categories.add(cIn.next());
		}
		if(cIn != null) {
			cIn.close();
		}

		categories.add("New category");
		ComboBox<String> categoryCb = new ComboBox<>(FXCollections.observableArrayList(categories));
		categoryCb.setPromptText("Category");
		if(categoryAdded)
			categoryCb.setValue(categories.get(categories.size() - 2));
		categoryCb.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		categoryCb.setPrefSize(200, 40);
		categoryCb.setOnAction(e->{
			if(categoryCb.getValue().equals("New category")) {
				(new AddCategory(user)).show(primaryStage);
			}
		});
		categoryBox.getChildren().addAll(category, categoryCb);
		VBox sellingBox = new VBox();
		sellingBox.setSpacing(2);
		sellingBox.setStyle("-fx-background-color: purple;");
		sellingBox.setAlignment(Pos.CENTER_LEFT);
		Label selling = new Label("Selling price:");
		selling.setTextFill(Color.WHITE);
		selling.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
		TextField sellingField = new TextField();
		sellingField.setFocusTraversable(false);
		sellingField.setPromptText("selling price...");
		sellingField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		sellingField.setPrefSize(200, 40);
		sellingField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 13));
		sellingBox.getChildren().addAll(selling, sellingField);
		VBox purchaseBox = new VBox();
		purchaseBox.setSpacing(2);
		purchaseBox.setStyle("-fx-background-color: purple;");
		purchaseBox.setAlignment(Pos.CENTER_LEFT);
		Label purchase = new Label("Purchase price:");
		purchase.setTextFill(Color.WHITE);
		purchase.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
		TextField purchaseField = new TextField();
		purchaseField.setFocusTraversable(false);
		purchaseField.setPromptText("purchase price...");
		purchaseField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		purchaseField.setPrefSize(200, 40);
		purchaseField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 13));
		purchaseBox.getChildren().addAll(purchase, purchaseField);
		VBox originalBox = new VBox();
		originalBox.setSpacing(2);
		originalBox.setStyle("-fx-background-color: purple;");
		originalBox.setAlignment(Pos.CENTER_LEFT);
		Label original = new Label("Original price:");
		original.setTextFill(Color.WHITE);
		original.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
		TextField originalField = new TextField();
		originalField.setFocusTraversable(false);
		originalField.setPromptText("original price...");
		originalField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		originalField.setPrefSize(200, 40);
		originalField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 13));
		originalBox.getChildren().addAll(original, originalField);
		fields.add(titleBox, 0, 0);
		fields.add(isbnBox, 0, 1);
		fields.add(categoryBox, 0, 2);
		fields.add(originalBox, 1, 0);
		fields.add(purchaseBox, 1, 1);
		fields.add(sellingBox, 1, 2);
		GridPane.setMargin(titleBox, new Insets(10));
		GridPane.setMargin(isbnBox, new Insets(10));
		GridPane.setMargin(categoryBox, new Insets(10));
		GridPane.setMargin(originalBox, new Insets(10));
		GridPane.setMargin(purchaseBox, new Insets(10));
		GridPane.setMargin(sellingBox, new Insets(10));
		
		VBox pubDateFinal = new VBox();
		pubDateFinal.setSpacing(1);
		pubDateFinal.setStyle("-fx-background-color: purple;");
		pubDateFinal.setAlignment(Pos.CENTER);
		Label pubDateLabel = new Label("Publishing date: ");
		pubDateLabel.setTextAlignment(TextAlignment.LEFT);
		pubDateLabel.setTextFill(Color.WHITE);
		pubDateLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
		DatePicker pubDate = new DatePicker();
		pubDate.setValue(LocalDate.now());
		pubDate.setPrefSize(200, 40);
		pubDate.setFocusTraversable(false);
		pubDateFinal.getChildren().addAll(pubDateLabel, pubDate);
		
		bookInfo.getChildren().addAll(book, fields, pubDateFinal);
		
		VBox authorInfo = new VBox();
		authorInfo.setStyle("-fx-background-color: purple;");
		authorInfo.setSpacing(20);
		authorInfo.setAlignment(Pos.TOP_CENTER);
		Label author = new Label("Author Information");
		author.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		author.setTextFill(Color.WHITE);
		VBox nameBox = new VBox();
		nameBox.setSpacing(2);
		nameBox.setStyle("-fx-background-color: purple;");
		nameBox.setAlignment(Pos.CENTER_LEFT);
		Label name = new Label("Name:");
		name.setTextFill(Color.WHITE);
		name.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
		TextField nameField = new TextField();
		nameField.setFocusTraversable(false);
		nameField.setPromptText("author's name...");
		nameField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		nameField.setPrefSize(200, 40);
		nameField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 13));
		nameBox.getChildren().addAll(name, nameField);
		VBox middleBox = new VBox();
		middleBox.setSpacing(2);
		middleBox.setStyle("-fx-background-color: purple;");
		middleBox.setAlignment(Pos.CENTER_LEFT);
		Label middle = new Label("Middle name:");
		middle.setTextFill(Color.WHITE);
		middle.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
		TextField middleField = new TextField();
		middleField.setFocusTraversable(false);
		middleField.setPromptText("author's middle name...");
		middleField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		middleField.setPrefSize(200, 40);
		middleField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 13));
		middleBox.getChildren().addAll(middle, middleField);
		VBox surnameBox = new VBox();
		surnameBox.setSpacing(2);
		surnameBox.setStyle("-fx-background-color: purple;");
		surnameBox.setAlignment(Pos.CENTER_LEFT);
		Label surname = new Label("Surname:");
		surname.setTextFill(Color.WHITE);
		surname.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
		TextField surnameField = new TextField();
		surnameField.setFocusTraversable(false);
		surnameField.setPromptText("author's surname...");
		surnameField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		surnameField.setPrefSize(200, 40);
		surnameField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 13));
		surnameBox.getChildren().addAll(surname, surnameField);
		Button other = new Button("Other author");
		other.setPrefSize(200, 40);
		other.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		other.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		other.setTextFill(Color.PURPLE);
		other.setAlignment(Pos.CENTER);
		FileInputStream input = null;
		try {
			input = new FileInputStream("Images/plus.png");
		}catch(java.io.FileNotFoundException e) {
			System.out.println("Check img for add author");
		}
		ImageView img = new ImageView(new Image(input));
		img.setFitHeight(10);
		img.setFitWidth(10);
		other.setGraphic(img);
		other.setOnMouseEntered(e->{
			other.setStyle("-fx-background-color: gainsboro; -fx-background-radius: 15px; -fx-border-radius: 15px;");
			other.setCursor(Cursor.HAND);
		});
		other.setOnMouseExited(e->{
			other.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		});
		other.setOnAction(e->{
			if(nameField.getText().equals("") || surnameField.getText().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Incomplete fields in author information section");
		        alert.setContentText("You must enter a name and surname");
		        alert.showAndWait();
			}else {
				authors.add(new Author(nameField.getText(), middleField.getText(), surnameField.getText()));
				nameField.clear();
				middleField.clear();
				surnameField.clear();
			}
		});
		authorInfo.getChildren().addAll(author, nameBox, middleBox, surnameBox, other);
		
		mainField.getChildren().addAll(bookInfo, authorInfo);
		
		HBox footer = new HBox();
		footer.setSpacing(0);
		footer.setPrefWidth(550);
		footer.setPrefHeight(40);
		Button add = new Button("Add");
		add.setPrefSize(400, 40);
		add.setStyle("-fx-background-color: purple; -fx-border-color: white;");
		add.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		add.setAlignment(Pos.CENTER);
		add.setTextFill(Color.WHITE);
		add.setOnMouseEntered(e->{
			add.setCursor(Cursor.HAND);
			add.setStyle("-fx-background-color: darkorchid; -fx-border-color: white;");
		});
		add.setOnMouseExited(e->{
			add.setStyle("-fx-background-color: purple; -fx-border-color: white;");
		});
		add.setOnAction(e->{
			if(titleField.getText().equals("") || isbnField.getText().equals("") || sellingField.getText().equals("") ||
					originalField.getText().equals("") || purchaseField.getText().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Incomplete fields in book information section");
		        alert.setContentText("All fields must be complete in this section.");
		        alert.showAndWait();
			}else if((nameField.getText().equals("") || surnameField.getText().equals("")) && authors.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Incomplete fields in author information section");
		        alert.setContentText("You must enter a name and surname");
		        alert.showAndWait();
			}
			else {
				try {
				LocalDate ld = pubDate.getValue();	
					
				BookStock stock = new BookStock();
				if(authors.size() == 0)
					authors.add(new Author(nameField.getText(), middleField.getText(), surnameField.getText()));
				else if(!nameField.getText().equals("") && !surnameField.getText().equals(""))
					authors.add(new Author(nameField.getText(), middleField.getText(), surnameField.getText()));
				
				Author [] temp = new Author[authors.size()];
				for(int i=0; i<authors.size(); i++)
					temp[i] = authors.get(i);
				
				Book tempBook = new Book(isbnField.getText(), titleField.getText(), categoryCb.getValue(), Double.parseDouble(purchaseField.getText()),
							Double.parseDouble(originalField.getText()), Double.parseDouble(sellingField.getText()), ld.getDayOfMonth(), ld.getMonthValue(), ld.getYear(), temp);
				stock.addBook(tempBook);
				stock.writeProducts();
				TransactionControl transactions = new TransactionControl();
				transactions.getTransactions().add(new Transaction((Book)tempBook.clone(), false));
				transactions.writeTransactions();
				Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Success");
			    alert.setHeaderText("New book was created successfully");
			    alert.showAndWait();
			    titleField.clear();
			    isbnField.clear();
			    categoryCb.setValue("");
			    purchaseField.clear();
			    originalField.clear();
			    sellingField.clear();
			    pubDate.setValue(LocalDate.now());
			    nameField.clear();
			    middleField.clear();
			    surnameField.clear();
			    authors.clear();
				}catch(NumberFormatException e1) {
					Alert alert = new Alert(AlertType.ERROR);
				    alert.setTitle("Error");
				    alert.setHeaderText("Non numerical values entered");
				    alert.setContentText("Please enter only decimal numbers in price fields!!");
				    alert.showAndWait();
				}catch(InvalidBookInfo e2) {
					Alert alert = new Alert(AlertType.ERROR);
				    alert.setTitle("Error");
				    alert.setHeaderText("Invalid book information");
				    alert.setContentText(e2.getMessage());
				    alert.showAndWait();
				}catch(BookExistsException e3) {
					Alert alert = new Alert(AlertType.ERROR);
				    alert.setTitle("Error");
				    alert.setContentText(e3.getMessage());
				    alert.showAndWait();
				}
				
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
			if(user.getStatus().equals(Status.MANAGER))
				(new BookShop((Manager)user)).show(primaryStage);
			else
				(new BookShop((Administrator)user)).show(primaryStage);
		});
		footer.getChildren().addAll(add, cancel);
		
		content.setCenter(mainField);
		content.setTop(header);
		content.setBottom(footer);
		
		Scene scene = new Scene(content, 800, 550);
		
		primaryStage.setTitle("Add Book");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
}
