package Main.GUI;

import java.util.ArrayList;

import Main.Products.Book;
import Main.Products.BookStock;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SellingQuantityView {

	private Book book;
	private BookStock stock;
	private ArrayList<Book> billBooks;
	
	public SellingQuantityView(Book book, ArrayList<Book> billBooks) {
		this.book = book;
		stock = new BookStock();
		this.billBooks = billBooks;
	}
	
	public void show() {
		
		Stage secondaryStage = new Stage();
		
		VBox content = new VBox();
		content.setStyle("-fx-background-color: purple;");
		content.setSpacing(10);
		content.setPadding(new Insets(20));
		content.setAlignment(Pos.CENTER);
		
		Label label = book.createLabel();
		label.setStyle("-fx-background-color: purple;");
		label.setTextFill(Color.WHITE);
		label.setFont(Font.font("Verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 15));
		
		String [] quantities = new String[book.getNumber()];
		for(int i=0; i < book.getNumber(); i++) {
			quantities[i] = (i+1) + "";
		}
		
		ComboBox cb = new ComboBox(FXCollections.observableArrayList(quantities));
		cb.setStyle("-fx-background-color: white; -fx-border-radius: 10px; -fx-background-radius: 10px;");
		cb.setPromptText("Quantity");
		
		Button add = new Button("Add");
		add.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-radius: 10px;");
		add.setTextFill(Color.PURPLE);
		add.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		add.setOnMouseEntered(e->{
			add.setStyle("-fx-background-color: gainsboro; -fx-background-radius: 10px; -fx-border-radius: 10px;");
			add.setCursor(Cursor.HAND);
		});
		add.setOnMouseExited(e->{
			add.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-radius: 10px;");
		});
		add.setOnAction(e->{
			Book clone = (Book)book.clone();
			System.out.println(cb.getValue().toString());
			clone.setNumber(Integer.parseInt(cb.getValue().toString()));
			billBooks.add(clone);
		});
		
		content.getChildren().addAll(label, cb, add);
		
		Scene scene = new Scene(content, 600, 200);
		secondaryStage.setTitle("Selling quantity");
		secondaryStage.setScene(scene);
		secondaryStage.setResizable(false);
		secondaryStage.show();
		
	}
	
}
