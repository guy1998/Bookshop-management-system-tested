package Main.GUI;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import Main.Backend.Products.BookStock;
import Main.Backend.Users.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AddCategory {
	
	private User user;
	
	public AddCategory(User user) {
		this.user = user;
	}

	public void show(Stage primaryStage) {
		
		
		VBox content = new VBox();
		content.setStyle("-fx-background-color: purple;");
		content.setSpacing(10);
		content.setAlignment(Pos.CENTER);
		content.setPadding(new Insets(10));
		
		Label newCat = new Label("New Category");
		newCat.setTextFill(Color.WHITE);
		newCat.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 24));
		newCat.setAlignment(Pos.CENTER);
		
		TextField catField = new TextField();
		catField.setPrefSize(200, 40);
		catField.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		catField.setPromptText("new category...");
		catField.setFocusTraversable(false);
		
		Button add = new Button("Add");
		add.setStyle("-fx-background-color: white; -fx-border-radius: 15px; -fx-background-radius: 15px;");
		add.setTextFill(Color.PURPLE);
		add.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 24));
		add.setAlignment(Pos.CENTER);
		add.setPrefSize(200, 30);
		add.setOnMouseEntered(e->{
			add.setStyle("-fx-background-color: gainsboro; -fx-border-radius: 15px; -fx-background-radius: 15px;");
			add.setCursor(Cursor.HAND);
		});
		add.setOnMouseExited(e->{
			add.setStyle("-fx-background-color: white; -fx-border-radius: 15px; -fx-background-radius: 15px;");
		});
		add.setOnAction(e->{
			BookStock stock = new BookStock();
			if(!catField.getText().equals("")) {
				if(stock.categoryExists(catField.getText())) {
					Alert alert = new Alert(AlertType.WARNING);
			        alert.setTitle("Warning");
			        alert.setHeaderText("Category Exists");
			        alert.showAndWait();
				}else {
					stock.addCategory(catField.getText());
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("New book category was created successfully");
					alert.showAndWait();
					(new AddBookView(user, true)).show(primaryStage);
				}
			}else {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Warning");
		        alert.setHeaderText("You have not added any category yet");
		        alert.showAndWait();
			}
		});
		Button cancel = new Button("Cancel");
		cancel.setStyle("-fx-background-color: white; -fx-border-radius: 15px; -fx-background-radius: 15px;");
		cancel.setTextFill(Color.PURPLE);
		cancel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 24));
		cancel.setAlignment(Pos.CENTER);
		cancel.setPrefSize(200, 30);
		cancel.setOnMouseEntered(e->{
			cancel.setStyle("-fx-background-color: gainsboro; -fx-border-radius: 15px; -fx-background-radius: 15px;");
			cancel.setCursor(Cursor.HAND);
		});
		cancel.setOnMouseExited(e->{
			cancel.setStyle("-fx-background-color: white; -fx-border-radius: 15px; -fx-background-radius: 15px;");
		});
		cancel.setOnAction(e->{
			(new AddBookView(user)).show(primaryStage);
		});
		HBox buttonsPane = new HBox();
		buttonsPane.setStyle("-fx-background-color: purple;");
		buttonsPane.setSpacing(20);
		buttonsPane.setAlignment(Pos.CENTER);
		buttonsPane.getChildren().addAll(add, cancel);
		
		content.getChildren().addAll(newCat, catField, buttonsPane);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setTitle("Add category");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
}
