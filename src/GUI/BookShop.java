package GUI;

import java.io.FileInputStream;
import java.util.ArrayList;

import Products.Book;
import Products.BookStock;
import Users.Administrator;
import Users.Manager;
import Users.Status;
import Users.User;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class BookShop {

	User user;
	BookStock stock;
	
	public BookShop(User user) {
		stock = new BookStock();
		this.user = user;
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
			if(user.getStatus().equals(Status.MANAGER))
				(new ManagerView((Manager)user, false)).show(primaryStage);
			else
				(new AdminView((Administrator)user)).show(primaryStage);
		});
		Label bookStock = new Label("Book Stock");
		bookStock.setStyle("-fx-background-color: purple;");
		bookStock.setTextFill(Color.WHITE);
		bookStock.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		
		MenuBar mb = new MenuBar();
		mb.setStyle("-fx-background-color: purple;");
		Menu sort = new Menu("Information");
		sort.setStyle("-fx-background-color: purple;");
		RadioMenuItem asc = new RadioMenuItem("Detailed");
		RadioMenuItem desc = new RadioMenuItem("Simple");
		ToggleGroup sorting = new ToggleGroup();
		sorting.getToggles().addAll(asc, desc);
		sort.getItems().addAll(asc, desc);
		Menu update = new Menu("Update");
		update.setStyle("-fx-background-color: purple;");
		ArrayList<MenuItem> items= new ArrayList<>();
		for(Book book: stock.getProductList1()) {
			MenuItem temp = new MenuItem(book.getTitle());
			temp.setOnAction(e->{
				System.out.println(temp.getText());
				(new EditBook(stock.findBook(temp.getText()), user)).show(primaryStage);
			});
			items.add(temp);
		}
		update.getItems().addAll(items);
		mb.getMenus().addAll(update, sort);
		HBox menuBar = new HBox();
		menuBar.setStyle("-fx-background-color: purple;");
		menuBar.setAlignment(Pos.BOTTOM_RIGHT);
		menuBar.setPrefWidth(400);
		menuBar.getChildren().addAll(mb);
		
		//simple table
		TableView<Book> table = new TableView<>();
		table.setEditable(true);
		ObservableList<Book> books = FXCollections.observableArrayList(stock.getProductList1());
		System.out.println(books);
		table.setItems(books);
		TableColumn title = new TableColumn("Title");
		title.setMinWidth(267);
		title.setCellValueFactory(new PropertyValueFactory<Book, String>("titleProperty"));
		TableColumn author = new TableColumn("Author");
		author.setMinWidth(266);
		author.setCellValueFactory(new PropertyValueFactory<Book, String>("authorProperty"));
		TableColumn category = new TableColumn("Category");
		category.setMinWidth(266);
		category.setCellValueFactory(new PropertyValueFactory<Book, String>("categoryProperty"));
		table.getColumns().addAll(title, author, category);
		
		//detailed table
		TableView<Book> detailedTable = new TableView<>();
		detailedTable.setItems(books);
		TableColumn titleDet = new TableColumn("Title");
		titleDet.setMinWidth(300);
		titleDet.setCellValueFactory(new PropertyValueFactory<Book, String>("titleProperty"));
		TableColumn isbn = new TableColumn("ISBN");
		isbn.setMinWidth(70);
		isbn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbnProperty"));
		TableColumn purchasePrice = new TableColumn("Purchased for:");
		purchasePrice.setMinWidth(140);
		purchasePrice.setCellValueFactory(new PropertyValueFactory<Book, Double>("purchase"));
		TableColumn originalPrice = new TableColumn("Original price");
		originalPrice.setMinWidth(140);
		originalPrice.setCellValueFactory(new PropertyValueFactory<Book, Double>("original"));
		TableColumn sellingPrice = new TableColumn("Selling price");
		sellingPrice.setMinWidth(140);
		sellingPrice.setCellValueFactory(new PropertyValueFactory<Book, Double>("priceProperty"));
		TableColumn number = new TableColumn("Quantity");
		number.setCellValueFactory(new PropertyValueFactory<Book, Integer>("numberProperty"));
		number.setMinWidth(70);
		detailedTable.getColumns().addAll(titleDet, isbn, purchasePrice, originalPrice, sellingPrice, number);
		
		Button addBook = new Button("Add Book");
		addBook.setStyle("-fx-background-color: purple;");
		addBook.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
		addBook.setPrefSize(800, 50);
		addBook.setTextFill(Color.WHITE);
		addBook.setOnMouseEntered(e->{
			addBook.setCursor(Cursor.HAND);
			addBook.setStyle("-fx-background-color: darkorchid;");
		});
		addBook.setOnMouseExited(e->{
			addBook.setStyle("-fx-background-color: purple;");
		});
		addBook.setOnAction(e->{
			(new AddBookView(user)).show(primaryStage);
		});
		
		tape.getChildren().addAll(back, bookStock, menuBar);
		
		content.setTop(tape);
		content.setBottom(addBook);
		content.setCenter(table);
		
		asc.setOnAction(e->{
			content.setCenter(detailedTable);
		});
		
		desc.setOnAction(e->{
			content.setCenter(table);
		});
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Book Stock");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
}
