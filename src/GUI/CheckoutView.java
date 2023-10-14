package GUI;

import java.io.FileInputStream;
import java.util.ArrayList;

import Notification.Message;
import Products.Book;
import Products.BookStock;
import Users.Librarian;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CheckoutView {
	
	private BookStock stock;
	private Librarian lib;
	private ArrayList<Book> billBooks;
	
	public CheckoutView(Librarian lib, ArrayList<Book> billBooks) {
		this.lib = lib;
		stock = new BookStock();
		this.billBooks = billBooks;
	}
	
	public void show(Stage primaryStage) {
		
		
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
			System.out.println("Checkout view image does not exist");
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
			for(Book book : billBooks)
				stock.modifyQuantity(book, book.getNumber());
			(new ChooseItems(lib)).show(primaryStage);
		});
		Label create = new Label("Checkout");
		create.setStyle("-fx-background-color: purple;");
		create.setTextFill(Color.WHITE);
		create.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		tape.getChildren().addAll(back, create);
		
		ScrollPane sp = new ScrollPane();
		sp.setStyle("-fx-background-color: purple;");
		sp.setFocusTraversable(false);
		sp.setPrefWidth(800);
		VBox background = new VBox();
		background.setStyle("-fx-background-color: purple;");
		background.setPrefWidth(783);
		background.setMinHeight(420);
		background.setSpacing(5);
		if(billBooks.isEmpty()) {
			Label temp = new Label("No books");
			temp.setPrefHeight(400);
			temp.setPrefWidth(783);
			temp.setAlignment(Pos.CENTER);
			background.getChildren().add(temp);}
		else {
			for(Book book : billBooks) {
				Label bookL = book.createLabel();
				bookL.setText(bookL.getText() + ", Total amount: " + book.getSellingPrice() * book.getNumber() + "$");
				bookL.setMinHeight(60);
				bookL.setPrefWidth(663);
				bookL.setStyle("-fx-background-color: purple;");
				bookL.setWrapText(true);
				bookL.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));
				bookL.setTextFill(Color.WHITE);
				Button minus = new Button();
				try {
					input = new FileInputStream("Images/minus.png");
				}catch(java.io.FileNotFoundException e) {
					
				}
				ImageView min = new ImageView(new Image(input));
				min.setFitHeight(20);
				min.setFitWidth(20);
				minus.setGraphic(min);
				minus.setMinHeight(60);
				minus.setPrefWidth(40);
				minus.setStyle("-fx-background-color: purple;");
				minus.setOnMouseEntered(e-> {
					minus.setCursor(Cursor.HAND);
					minus.setStyle("-fx-background-color: darkorchid;");
				});
				minus.setOnMouseExited(e->{
					minus.setStyle("-fx-background-color: purple;");
				});
				Button remove = new Button();
				try {
					input = new FileInputStream("Images/close.png");
				}catch(java.io.FileNotFoundException e) {
					
				}
				ImageView cross = new ImageView(new Image(input));
				cross.setFitHeight(20);
				cross.setFitWidth(20);
				remove.setGraphic(cross);
				remove.setMinHeight(60);
				remove.setPrefWidth(40);
				remove.setStyle("-fx-background-color: purple;");
				remove.setOnMouseEntered(e-> {
					remove.setCursor(Cursor.HAND);
					remove.setStyle("-fx-background-color: darkorchid;");
				});
				remove.setOnMouseExited(e->{
					remove.setStyle("-fx-background-color: purple;");
				});
				Button add = new Button();
				try {
					input = new FileInputStream("Images/plus.png");
				}catch(java.io.FileNotFoundException e) {
					
				}
				ImageView plus = new ImageView(new Image(input));
				plus.setFitHeight(20);
				plus.setFitWidth(20);
				add.setGraphic(plus);
				add.setMinHeight(60);
				add.setPrefWidth(40);
				add.setStyle("-fx-background-color: purple;");
				add.setOnMouseEntered(e-> {
					add.setCursor(Cursor.HAND);
					add.setStyle("-fx-background-color: darkorchid;");
				});
				add.setOnMouseExited(e->{
					add.setStyle("-fx-background-color: purple;");
				});
				
				HBox optionButtons = new HBox();
				optionButtons.setStyle("-fx-background-color: purple;");
				optionButtons.setSpacing(0);
				optionButtons.setMinHeight(60);
				optionButtons.setPrefWidth(120);
				optionButtons.getChildren().addAll(minus, add, remove);
				
				HBox bookBox = new HBox();
				bookBox.setSpacing(0);
				bookBox.setMinHeight(60);
				bookBox.setPrefWidth(783);
				bookBox.setStyle("-fx-background-color: purple;");
				bookBox.getChildren().addAll(bookL, optionButtons);
				background.getChildren().add(bookBox);
				
				minus.setOnAction(e->{
					stock.modifyQuantity(book, 1);
					billBooks.get(billBooks.indexOf(book)).addNumber(-1);
					if(book.getNumber() > 0)
						bookL.setText(book.toString() + "\nCopies: " + book.getNumber() + ", Total amount: " + book.getSellingPrice() * book.getNumber() + "$");
					else {
						background.getChildren().remove(bookBox);
						billBooks.remove(billBooks.indexOf(book));
					}
				});
				
				remove.setOnAction(e->{
					stock.modifyQuantity(book, book.getNumber());
					background.getChildren().remove(bookBox);
					billBooks.remove(billBooks.indexOf(book));
				});
				
				add.setOnAction(e->{
					if(stock.findBook(book).getNumber() == 0) {
						Alert alert = new Alert(AlertType.INFORMATION);
				        alert.setTitle("Stock info");
				        alert.setHeaderText("There are no copies left");
				        alert.setContentText("You cannot sell this book as there are no copies of it left");
				        alert.showAndWait();
					}else {
						stock.modifyQuantity(book, -1);
						billBooks.get(billBooks.indexOf(book)).addNumber(1);
						bookL.setText(book.toString() + "\nCopies: " + book.getNumber() + ", Total amount: " + book.getSellingPrice() * book.getNumber() + "$");
					}
				});
			}
		}
		sp.setContent(background);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		Button check = new Button("Go to payment");
		check.setPrefSize(800, 60);
		check.setStyle("-fx-background-color: purple;");
		check.setTextFill(Color.WHITE);
		check.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 17));
		check.setOnMouseEntered(e->{
			check.setStyle("-fx-background-color: darkorchid;");
			check.setCursor(Cursor.HAND);
		});
		check.setOnMouseExited(e->{
			check.setStyle("-fx-background-color: purple;");
		});
		check.setOnAction(e->{
			Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Please Confirm");
	        alert.setHeaderText("Once you go to the payment section you cannot cancel!");
	        alert.setContentText("Do you want to proceed?");
	        alert.showAndWait();
	        if(alert.getResult().equals(ButtonType.OK))
				(new PaymentView(lib, billBooks)).show(primaryStage);
		});
		
		content.setTop(tape);
		content.setCenter(sp);
		content.setBottom(check);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setTitle("Checkout");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}
