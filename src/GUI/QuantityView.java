package GUI;

import java.util.ArrayList;

import Products.Book;
import Products.BookStock;
import Products.Transaction;
import Products.TransactionControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class QuantityView {
	
	private Book book;
	private BookStock stock;
	private TransactionControl transactions;
	
	public QuantityView(Book book) {
		this.book = book;
		stock = new BookStock();
		transactions = new TransactionControl();
	}
	
	public void show() {
		
		Stage secondaryStage = new Stage();
		
		VBox content = new VBox();
		content.setStyle("-fx-background-color: purple;");
		content.setSpacing(10);
		content.setPadding(new Insets(20));
		content.setAlignment(Pos.CENTER);
		
		TextField nr = new TextField();
		nr.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-raidus: 15px;");
		nr.setPromptText("quantity...");
		nr.setPrefSize(200, 40);
		
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
			try {
			if(Integer.parseInt(nr.getText()) <= 0)
				throw new NumberFormatException();
			stock.modifyQuantity(book, Integer.parseInt(nr.getText()));
			Book clone = (Book)book.clone();
			clone.setNumber(Integer.parseInt(nr.getText()));
			transactions.getTransactions().add(new Transaction(clone, false));
			transactions.writeTransactions();
			secondaryStage.close();
			}catch(NumberFormatException e1) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Invalid value entered");
		        alert.setContentText("Please enter only positive integers!!");
		        alert.showAndWait();
			}
		});
		
		content.getChildren().addAll(nr, add);
		
		Scene scene = new Scene(content, 400, 400);
		secondaryStage.setTitle("Quantity");
		secondaryStage.setScene(scene);
		secondaryStage.setResizable(false);
		secondaryStage.show();
		
	}

}
