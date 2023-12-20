package Main.GUI;

import java.io.FileInputStream;

import Main.Products.Book;
import Main.Products.BookStock;
import Main.Users.Librarian;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SearchView {

	private BookStock stock;
	private Librarian lib;
	
	public SearchView(Librarian lib) {
		stock = new BookStock();
		this.lib = lib;
	}
	
	public void show(Stage primaryStage) {
		
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
				(new LibrarianView(lib)).show(primaryStage);
		});
		Label profile = new Label("Search");
		profile.setStyle("-fx-background-color: purple;");
		profile.setTextFill(Color.WHITE);
		profile.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		profile.setPrefWidth(300);
		HBox help = new HBox();
		help.setStyle("-fx-background-color: purple;");
		help.setPrefWidth(300);
		help.setSpacing(10);
		help.setAlignment(Pos.CENTER_RIGHT);
		try {
			input = new FileInputStream("Images/magnifier.png");
		}catch(java.io.FileNotFoundException e1) {
			System.out.println("Image at search view");
		}
		ImageView searchIcon = new ImageView(new Image(input));
		searchIcon.setFitHeight(20);
		searchIcon.setFitWidth(20);
		searchIcon.setOnMouseEntered(e->{
			searchIcon.setCursor(Cursor.HAND);
		});
		TextField searchBox = new TextField();
		searchBox.setPromptText("Search...");
		searchBox.setPrefSize(200, 20);
		searchBox.setFocusTraversable(false);
		searchBox.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-radius: 10px");
		help.getChildren().addAll(searchIcon, searchBox);
		tape.getChildren().addAll(back, profile, help);
		
		Label searchSth = new Label("Search something");
		searchSth.setStyle("-fx-background-color: white;");
		searchSth.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		searchSth.setPrefSize(800, 480);
		searchSth.setAlignment(Pos.CENTER);
		searchSth.setTextFill(Color.PURPLE);
		
		Label noRes = new Label("No result");
		noRes.setStyle("-fx-background-color: white;");
		noRes.setPrefSize(800, 480);
		noRes.setAlignment(Pos.CENTER);
		noRes.setTextFill(Color.PURPLE);
		noRes.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		
		content.setTop(tape);
		content.setCenter(searchSth);
		
		Scene scene = new Scene(content, 800, 550);
		scene.setOnKeyPressed(e->{
			Book temp = null;
			if(e.getCode().equals(KeyCode.ENTER)) {
				temp = stock.findBook(searchBox.getText());
				System.out.println("Enter pressed");
			}
			
			if(temp != null) {
				BookPane pane = new BookPane(temp);
				pane.fillContent();
				content.setCenter(pane);
			}else {
				content.setCenter(noRes);
			}
				
		});
		
		searchIcon.setOnMouseClicked(e->{
			Book temp = stock.findBook(searchBox.getText());
			if(temp != null) {
				BookPane pane = new BookPane(temp);
				pane.fillContent();
				content.setCenter(pane);
			}else {
				content.setCenter(noRes);
			}
		});
		
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Search");
		primaryStage.show();
	}
	
}

class BookPane extends VBox{
	
	private Book showable;
	
	public BookPane(Book showable) {
		this.showable = showable;
	}
	
	public void fillContent() {
		
		this.setStyle("-fx-background-color: white;");
		this.setAlignment(Pos.CENTER);
		this.setSpacing(100);
		
		VBox bookInfo = new VBox();
		bookInfo.setStyle("-fx-background-color: white;");
		bookInfo.setAlignment(Pos.CENTER);
		bookInfo.setSpacing(40);
		
		Label title = new Label("Title: " + showable.getTitle());
		title.setTextFill(Color.PURPLE);
		title.setAlignment(Pos.CENTER_LEFT);
		title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		title.setStyle("-fx-background-color: white;");
		
		Label genre = new Label("Genre: " + showable.getCategory());
		genre.setTextFill(Color.PURPLE);
		genre.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		genre.setStyle("-fx-background-color: white;");
		genre.setAlignment(Pos.CENTER_LEFT);
		
		Label author = new Label("Author: " + showable.getAuthorProperty());
		author.setTextFill(Color.PURPLE);
		author.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		author.setStyle("-fx-background-color: white;");
		author.setAlignment(Pos.CENTER_LEFT);
		
		Label isbn = new Label("ISBN: " + showable.getISBN());
		isbn.setTextFill(Color.PURPLE);
		isbn.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		isbn.setStyle("-fx-background-color: white;");
		isbn.setAlignment(Pos.CENTER_LEFT);
		
		Label price = new Label("Buy now for the price of: " + showable.getSellingPrice() + "$");
		price.setTextFill(Color.PURPLE);
		price.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		price.setStyle("-fx-background-color: white;");
		
		Label out = new Label("Currently out of stock!!");
		out.setTextFill(Color.PURPLE);
		out.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		out.setStyle("-fx-background-color: white;");
		
		bookInfo.getChildren().addAll(title, genre, author, isbn);

		if(showable.getNumber() == 0)
			this.getChildren().addAll(bookInfo, out);
		else
			this.getChildren().addAll(bookInfo, price);
	}
	
}

