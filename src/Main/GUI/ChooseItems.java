package Main.GUI;

import java.io.FileInputStream;
import java.util.ArrayList;

import Main.Products.Book;
import Main.Products.BookStock;
import Main.Users.Librarian;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

public class ChooseItems {

	private BookStock stock;
	private Librarian lib;
	private ArrayList<Book> billBooks;
	
	public ChooseItems(Librarian lib) {
		this.lib = lib;
		stock = new BookStock();
		billBooks = new ArrayList<>();
	}
	
	public void show(Stage primaryStage) {
		
		ArrayList<Book> books = stock.getProductList1();
		
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
			(new LibrarianView(lib)).show(primaryStage);
		});
		Label create = new Label("Choose items");
		create.setStyle("-fx-background-color: purple;");
		create.setTextFill(Color.WHITE);
		create.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		tape.getChildren().addAll(back, create);
		
		ScrollPane sp = new ScrollPane();
		sp.setStyle("-fx-background-color: purple;");
		sp.setFocusTraversable(false);
		sp.setPrefWidth(800);
		VBox background = new VBox();
		background.setStyle("-fx-background-color: lightgrey;");
		background.setPrefWidth(783);
		background.setMinHeight(420);
		if(books.isEmpty()) {
			Label temp = new Label("No books");
			temp.setPrefHeight(400);
			temp.setPrefWidth(783);
			temp.setAlignment(Pos.CENTER);
			background.getChildren().add(temp);}
		else {
			for(Book book : books) {
				Label bookL = book.createLabel();
				bookL.setMinHeight(60);
				bookL.setPrefWidth(783);
				if(book.getNumber() == 0)
					bookL.setStyle("-fx-background-color: lightslategrey; -fx-border-color: lightgrey;");
				else
					bookL.setStyle("-fx-background-color: white; -fx-border-color: lightgrey;");
				bookL.setOnMouseClicked(e->{
					if(book.getNumber() > 0){
						stock.modifyQuantity(book, -1);
						Book clone = (Book)book.clone();
						if(billBooks.contains(clone)) {
							billBooks.get(billBooks.indexOf(clone)).addNumber(1);
						}else {
							clone.setNumber(1);
							billBooks.add(clone);
						}
						bookL.setText(book.toString() + "\nCopies left: " + book.getNumber());
						if(book.getNumber() == 0)
							bookL.setStyle("-fx-background-color: lightslategrey; -fx-border-color: lightgrey;");
					}else {
						Alert alert = new Alert(AlertType.INFORMATION);
				        alert.setTitle("Stock info");
				        alert.setHeaderText("There are no copies left");
				        alert.setContentText("You cannot sell this book as there are no copies of it left");
				        alert.showAndWait();
					}
				});
				bookL.setOnMouseEntered(e-> {
					bookL.setCursor(Cursor.HAND);
					if(book.getNumber() == 0)
						bookL.setStyle("-fx-background-color: lightslategrey; -fx-border-color: lightgrey;");
					else
						bookL.setStyle("-fx-background-color: white; -fx-border-color: black;");
				});
				bookL.setOnMouseExited(e->{
					if(book.getNumber() == 0)
						bookL.setStyle("-fx-background-color: lightslategrey; -fx-border-color: lightgrey;");
					else
						bookL.setStyle("-fx-background-color: white; -fx-border-color: lightgrey;");
				});
				bookL.setWrapText(true);
				bookL.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));
				bookL.setTextFill(Color.PURPLE);
				background.getChildren().add(bookL);
			}
		}
		sp.setContent(background);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		Button check = new Button("Go to checkout");
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
			(new CheckoutView(lib, billBooks)).show(primaryStage);
		});
		content.setTop(tape);
		content.setCenter(sp);
		content.setBottom(check);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setTitle("Choose item");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
}
