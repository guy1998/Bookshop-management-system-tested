package Main.GUI;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Main.Products.Book;
import Main.Products.Transaction;
import Main.Products.TransactionControl;
import Main.Users.Librarian;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PaymentView {

	private Librarian lib;
	private ArrayList<Book> billBooks;
	
	public PaymentView(Librarian lib, ArrayList<Book> billBooks) {
		
		this.lib = lib;
		this.billBooks = billBooks;
		
	}
	
	public void show(Stage primaryStage) {
		
		DecimalFormat df = new DecimalFormat("#.00"); 
		double totalAmount = 0;
		for(Book book : billBooks) {
			totalAmount += book.getNumber() * book.getSellingPrice();
		}
		final double ta = totalAmount;
		BorderPane content = new BorderPane();
		
		HBox tape = new HBox();
		tape.setStyle("-fx-background-color: purple;");
		tape.setAlignment(Pos.CENTER_LEFT);
		tape.setPrefHeight(70);
		tape.setSpacing(10);
		tape.setPrefWidth(550);
		tape.setPadding(new Insets(20));
		Label transaction = new Label("New transaction");
		transaction.setStyle("-fx-background-color: purple;");
		transaction.setTextFill(Color.WHITE);
		transaction.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		tape.getChildren().addAll(transaction);
		
		HBox mainField = new HBox();
		mainField.setStyle("-fx-background-color: white;");
		mainField.setPadding(new Insets(20));
		mainField.setAlignment(Pos.CENTER);
		mainField.setSpacing(30);
		VBox writings = new VBox();
		writings.setStyle("-fx-background-color: white;");
		writings.setSpacing(15);
		writings.setAlignment(Pos.CENTER_LEFT);
		Label total = new Label("Total: " + totalAmount);
		total.setTextFill(Color.PURPLE);
		total.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
		total.setWrapText(true);
		total.setStyle("-fx-background-color: white;");
		Label afterTax = new Label("After taxes: " + df.format(totalAmount * 1.1));
		afterTax.setTextFill(Color.PURPLE);
		afterTax.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
		afterTax.setWrapText(true);
		afterTax.setStyle("-fx-background-color: white;");
		writings.getChildren().addAll(total, afterTax);
		VBox change = new VBox();
		change.setStyle("-fx-background-color: white;");
		change.setSpacing(20);
		change.setAlignment(Pos.CENTER);
		VBox calculator = new VBox();
		calculator.setSpacing(2);
		calculator.setStyle("-fx-background-color: white;");
		calculator.setAlignment(Pos.CENTER);
		TextField amount = new TextField();
		amount.setPromptText("Amount paid...");
		amount.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: purple;");
		amount.setPrefSize(200, 40);
		Button calculate = new Button("Calculate change");
		calculate.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		calculate.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: purple;");
		calculate.setTextFill(Color.PURPLE);
		calculate.setOnMouseEntered(e->{
			calculate.setStyle("-fx-background-color: gainsboro; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: purple;");
			calculate.setCursor(Cursor.HAND);
		});
		calculate.setOnMouseExited(e->{
			calculate.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: purple;");
		});
		calculator.getChildren().addAll(amount, calculate);
		Label showing = new Label();
		showing.setStyle("-fx-background-color: white;");
		showing.setTextFill(Color.PURPLE);
		showing.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		showing.setWrapText(true);
		change.getChildren().addAll(calculator, showing);
		calculate.setOnAction(e->{
			if(Double.parseDouble(amount.getText()) < 0) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Invalid value");
		        alert.setHeaderText("Cannot enter negative numbers");
		        alert.setContentText("Nobody can pay a negative amount of money (it would be cool if we could though) so please enter a correct amount!");
		        alert.showAndWait();
			}else {
				double changeVal = Double.parseDouble(amount.getText()) - ta * 1.1;
				if(changeVal < 0)
					showing.setText("It seems that buyer is " + df.format((-1*changeVal)) + "$ short");
				else if(changeVal > 0)
					showing.setText("The change is: " + df.format(changeVal));
				else
					showing.setText("Precise amount. Thank you!!!");
			}
		});
		Line vLine = new Line(400, 150, 400, 500);
		vLine.setStroke(Color.PURPLE);
		vLine.setStrokeWidth(10);
		mainField.getChildren().addAll(writings, vLine, change);
		
		Button save = new Button("Save transaction");
		save.setPrefSize(800, 60);
		save.setStyle("-fx-background-color: purple;");
		save.setTextFill(Color.WHITE);
		save.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 17));
		save.setOnMouseEntered(e->{
			save.setStyle("-fx-background-color: darkorchid;");
			save.setCursor(Cursor.HAND);
		});
		save.setOnMouseExited(e->{
			save.setStyle("-fx-background-color: purple;");
		});
		save.setOnAction(e->{
			try {
			lib.createBill(billBooks);
			TransactionControl transactions = new TransactionControl();
			transactions.getTransactions().add(new Transaction(billBooks, true));
			transactions.writeTransactions();
			(new LibrarianView(lib)).show(primaryStage);
			}catch(Exception e1){
				System.out.println("Sfunksionoi");
				System.out.println(e1.getMessage());
			}
		});
		
		content.setTop(tape);
		content.setCenter(mainField);
		content.setBottom(save);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setTitle("Transaction");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	
}
