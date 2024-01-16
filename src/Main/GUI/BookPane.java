package Main.GUI;

import Main.Backend.Products.Book;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class BookPane extends VBox {

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
		title.setId("title");
        title.setTextFill(Color.PURPLE);
        title.setAlignment(Pos.CENTER_LEFT);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
        title.setStyle("-fx-background-color: white;");

        Label genre = new Label("Genre: " + showable.getCategory());
		genre.setId("genre");
        genre.setTextFill(Color.PURPLE);
        genre.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
        genre.setStyle("-fx-background-color: white;");
        genre.setAlignment(Pos.CENTER_LEFT);

        Label author = new Label("Author: " + showable.getAuthorProperty());
		author.setId("author");
        author.setTextFill(Color.PURPLE);
        author.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
        author.setStyle("-fx-background-color: white;");
        author.setAlignment(Pos.CENTER_LEFT);

        Label isbn = new Label("ISBN: " + showable.getISBN());
		isbn.setId("isbn");
        isbn.setTextFill(Color.PURPLE);
        isbn.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
        isbn.setStyle("-fx-background-color: white;");
        isbn.setAlignment(Pos.CENTER_LEFT);

        Label price = new Label("Buy now for the price of: " + showable.getSellingPrice() + "$");
		price.setId("price");
        price.setTextFill(Color.PURPLE);
        price.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
        price.setStyle("-fx-background-color: white;");

        Label out = new Label("Currently out of stock!!");
		out.setId("out");
        out.setTextFill(Color.PURPLE);
        out.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
        out.setStyle("-fx-background-color: white;");

        bookInfo.getChildren().addAll(title, genre, author, isbn);

        if (showable.getNumber() == 0)
            this.getChildren().addAll(bookInfo, out);
        else
            this.getChildren().addAll(bookInfo, price);
    }

}
