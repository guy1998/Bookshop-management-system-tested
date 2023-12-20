package Main.GUI;

import java.util.ArrayList;

import Main.Products.Author;
import Main.Products.Book;
import Main.Products.BookStock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AddAuthor {

	Book book;
	BookStock stock;
	
	public AddAuthor(Book book) {
		this.book = book;
		stock = new BookStock();
	}
	
	public void show() {
		
		ArrayList<Author> authors = new ArrayList<>();
		Stage secondaryStage = new Stage();
		
		VBox content = new VBox();
		content.setStyle("-fx-background-color: purple;");
		content.setPadding(new Insets(10));
		content.setSpacing(10);
		content.setAlignment(Pos.CENTER);
		
		Label author = new Label("Edit author");
		author.setStyle("-fx-background-color: purple;");
		author.setTextFill(Color.WHITE);
		author.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		
		TextField name = new TextField();
		name.setStyle("-fx-background-color: white; -fx-border-radius: 15px; -fx-background-radius: 15px;");
		name.setPromptText("name...");
		name.setPrefSize(200, 40);
		TextField middlename = new TextField();
		middlename.setStyle("-fx-background-color: white; -fx-border-radius: 15px; -fx-background-radius: 15px;");
		middlename.setPromptText("middlename...");
		middlename.setPrefSize(200, 40);
		TextField surname = new TextField();
		surname.setStyle("-fx-background-color: white; -fx-border-radius: 15px; -fx-background-radius: 15px;");
		surname.setPromptText("surname...");
		surname.setPrefSize(200, 40);
		VBox fieldsPane = new VBox();
		fieldsPane.setStyle("-fx-background-color: purple;");
		fieldsPane.setSpacing(2);
		fieldsPane.getChildren().addAll(name, middlename, surname);
		
		Button save = new Button("Save");
		save.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-radius: 10px;");
		save.setTextFill(Color.PURPLE);
		save.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		save.setOnMouseEntered(e->{
			save.setStyle("-fx-background-color: gainsboro; -fx-background-radius: 10px; -fx-border-radius: 10px;");
			save.setCursor(Cursor.HAND);
		});
		save.setOnMouseExited(e->{
			save.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-radius: 10px;");
		});
		save.setOnAction(e->{
			if(authors.size() != 0) {
				Author[]temp = new Author[authors.size()];
				for(int i=0; i<authors.size(); i++)
					temp[i] = authors.get(i);
				stock.modifyAuthors(book, temp);
			}
			secondaryStage.close();
		});
		
		Button addAuthor = new Button("Add author");
		addAuthor.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-radius: 10px;");
		addAuthor.setTextFill(Color.PURPLE);
		addAuthor.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		addAuthor.setOnMouseEntered(e->{
			addAuthor.setStyle("-fx-background-color: gainsboro; -fx-background-radius: 10px; -fx-border-radius: 10px;");
			addAuthor.setCursor(Cursor.HAND);
		});
		addAuthor.setOnMouseExited(e->{
			addAuthor.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-radius: 10px;");
		});
		addAuthor.setOnAction(e->{
			authors.add(new Author(name.getText(), middlename.getText(), surname.getText()));
			name.clear();
			middlename.clear();
			surname.clear();
		});
		HBox buttonsPane = new HBox();
		buttonsPane.setStyle("-fx-background-color: purple;");
		buttonsPane.setSpacing(5);
		buttonsPane.setAlignment(Pos.CENTER);
		buttonsPane.getChildren().addAll(save, addAuthor);
		
		content.getChildren().addAll(author, fieldsPane, buttonsPane);
		
		
		Scene scene = new Scene(content, 400, 400);
		secondaryStage.setScene(scene);
		secondaryStage.setResizable(false);
		secondaryStage.show();
	}
	
}
