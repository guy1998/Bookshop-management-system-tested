package Main.GUI;

import java.io.FileInputStream;

import Main.Exceptions.InvalidBookInfo;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import Main.Products.Book;
import Main.Products.BookStock;
import Main.Users.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EditBook {

	private Book editable;
	private SimpleStringProperty tempTitle = new SimpleStringProperty();
	private SimpleStringProperty tempISBN = new SimpleStringProperty();
	private SimpleStringProperty tempCategory = new SimpleStringProperty();
	private SimpleDoubleProperty tempOriginal = new SimpleDoubleProperty();
	private SimpleDoubleProperty tempPurchase = new SimpleDoubleProperty();
	private SimpleDoubleProperty tempSelling = new SimpleDoubleProperty();
	
	private User user;
	private BookStock stock;
	
	public EditBook(Book editable, User user) {
		this.editable = editable;
		this.user = user;
		stock = new BookStock();
		this.tempTitle.setValue(editable.getTitle());
		this.tempISBN.setValue(editable.getISBN());
		this.tempCategory.setValue(editable.getCategoryProperty());
		this.tempPurchase.setValue(editable.getPurchase());
		this.tempOriginal.setValue(editable.getOriginal());
		this.tempSelling.setValue(editable.getPriceProperty());
	}
	
	public void show(Stage primaryStage) {
		BorderPane content = new BorderPane();
		
		HBox tape = new HBox();
		tape.setStyle("-fx-background-color: purple;");
		tape.setAlignment(Pos.CENTER_LEFT);
		tape.setPrefHeight(100);
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
		back.setPrefSize(70, 100);
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
			(new BookShop(user)).show(primaryStage);
		});
		Label title = new Label(editable.getTitle());
		title.setPrefWidth(630);
		title.setWrapText(true);
		title.setStyle("-fx-background-color: purple;");
		title.setTextFill(Color.WHITE);
		title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		MenuBar options = new MenuBar();
		options.setStyle("-fx-background-color: purple");
		Menu more = new Menu("More");
		more.setStyle("-fx-background-color: purple;");
		MenuItem editAuthor = new MenuItem("Edit Author");
		editAuthor.setOnAction(e->{
			(new AddAuthor(editable)).show();
		});
		MenuItem delete = new MenuItem("Delete this item");
		delete.setOnAction(e->{
				if(editable.getNumber() > 0) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
		        alert.setTitle("Warning");
		        alert.setHeaderText("You are about to delete a book that is still on stock");
		        alert.setContentText("Do you want to proceed?");
		        alert.showAndWait();
		        if(alert.getResult() == ButtonType.OK) {
		        	Book temp = stock.delete(editable);
		        	Alert confirmation = new Alert(AlertType.INFORMATION);
			        confirmation.setTitle("Deleted successfully");
			        confirmation.setHeaderText("The following book was deleted:");
			        confirmation.setContentText(temp.toString());
			        confirmation.showAndWait();
			        (new BookShop(user)).show(primaryStage);
		        }
			}else {
				Book temp = stock.delete(editable);
	        	Alert confirmation = new Alert(AlertType.INFORMATION);
		        confirmation.setTitle("Deleted successfully");
		        confirmation.setHeaderText("The following book was deleted:");
		        confirmation.setContentText(temp.toString());
		        confirmation.showAndWait();
		        (new BookShop(user)).show(primaryStage);
			}
				
		});
		more.getItems().addAll(editAuthor, delete);
		options.getMenus().add(more);
		tape.getChildren().addAll(back, title, options);
		
		VBox properties = new VBox();
		properties.setStyle("-fx-background-color: purple;");
		properties.setSpacing(15);
		properties.setAlignment(Pos.CENTER_LEFT);
		Button [] btns = new Button[12];
		for(int i=0; i<12; i++) {
			Button temp;
			if(i == 0)
				temp = new Button("Title: " + editable.getTitle());
			else if(i == 1)
				temp = new Button("ISBN: " + editable.getISBN());
			else if(i == 2)
				temp = new Button("Category: " + editable.getCategory());
			else if(i == 3)
				temp = new Button("Original price: " + editable.getOriginalPrice());
			else if(i == 4)
				temp = new Button("Purchase price: " + editable.getPurchasePrice());
			else if(i == 5)
				temp = new Button("Selling price: " + editable.getSellingPrice());
			else 
				temp = new Button("Confirm");
			
			temp.setAlignment(Pos.CENTER_LEFT);
			temp.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
			if(i > 5) {
				temp.setPrefSize(230, 40);
				temp.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10px;");
				temp.setTextFill(Color.PURPLE);
				temp.setOnMouseEntered(e->{
					temp.setCursor(Cursor.HAND);
					temp.setStyle("-fx-background-color: gainsboro; -fx-border-radius: 10; -fx-background-radius: 10px;");
				});
				temp.setOnMouseExited(e->{
					temp.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10px;");
				});
			}
			else {
				
			temp.setStyle("-fx-background-color: purple;");
			temp.setMinWidth(400);
			temp.setTextFill(Color.WHITE);
			temp.setOnMouseEntered(e->{
				temp.setCursor(Cursor.HAND);
				temp.setStyle("-fx-background-color: darkorchid;");
			});
			temp.setOnMouseExited(e->{
				temp.setStyle("-fx-background-color: purple;");
			});
			
			}
			
			btns[i] = temp;
		}
		
		for(int i=0; i<6; i++)
			properties.getChildren().add(btns[i]);
			
		StackPane edit = new StackPane();
		edit.setStyle("-fx-background-color: purple");
		VBox editTitle = new VBox();
		editTitle.setAlignment(Pos.CENTER);
		editTitle.setStyle("-fx-background-color: purple;");
		editTitle.setSpacing(5);
		Label titleL = new Label("Edit title: ");
		titleL.setStyle("-fx-background-color: purple;");
		titleL.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		titleL.setTextFill(Color.WHITE);
		TextField titleField = new TextField();
		titleField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		titleField.setPromptText("title...");
		titleField.setPrefSize(230, 40);
		editTitle.getChildren().addAll(titleL, titleField, btns[6]);
		VBox editISBN = new VBox();
		editISBN.setAlignment(Pos.CENTER);
		editISBN.setStyle("-fx-background-color: purple;");
		editISBN.setSpacing(5);
		Label ISBNL = new Label("Edit ISBN: ");
		ISBNL.setStyle("-fx-background-color: purple;");
		ISBNL.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		ISBNL.setTextFill(Color.WHITE);
		TextField ISBNField = new TextField();
		ISBNField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		ISBNField.setPromptText("ISBN...");
		ISBNField.setPrefSize(230, 40);
		editISBN.getChildren().addAll(ISBNL, ISBNField, btns[7]);
		VBox editcategory = new VBox();
		editcategory.setAlignment(Pos.CENTER);
		editcategory.setStyle("-fx-background-color: purple;");
		editcategory.setSpacing(5);
		Label categoryL = new Label("Edit category: ");
		categoryL.setStyle("-fx-background-color: purple;");
		categoryL.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		categoryL.setTextFill(Color.WHITE);
		TextField categoryField = new TextField();
		categoryField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		categoryField.setPromptText("category...");
		categoryField.setPrefSize(230, 40);
		editcategory.getChildren().addAll(categoryL, categoryField, btns[8]);
		VBox editOriginal = new VBox();
		editOriginal.setAlignment(Pos.CENTER);
		editOriginal.setStyle("-fx-background-color: purple;");
		editOriginal.setSpacing(5);
		Label originalL = new Label("Edit original price: ");
		originalL.setStyle("-fx-background-color: purple;");
		originalL.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		originalL.setTextFill(Color.WHITE);
		TextField originalField = new TextField();
		originalField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		originalField.setPromptText("original price...");
		originalField.setPrefSize(230, 40);
		editOriginal.getChildren().addAll(originalL, originalField, btns[9]);
		VBox editPurchase = new VBox();
		editPurchase.setAlignment(Pos.CENTER);
		editPurchase.setStyle("-fx-background-color: purple;");
		editPurchase.setSpacing(5);
		Label purchaseL = new Label("Edit purchase price: ");
		purchaseL.setStyle("-fx-background-color: purple;");
		purchaseL.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		purchaseL.setTextFill(Color.WHITE);
		TextField purchaseField = new TextField();
		purchaseField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		purchaseField.setPromptText("purchase price...");
		purchaseField.setPrefSize(230, 40);
		editPurchase.getChildren().addAll(purchaseL, purchaseField, btns[10]);
		VBox editSelling = new VBox();
		editSelling.setAlignment(Pos.CENTER);
		editSelling.setStyle("-fx-background-color: purple;");
		editSelling.setSpacing(5);
		Label sellingL = new Label("Edit selling price: ");
		sellingL.setStyle("-fx-background-color: purple;");
		sellingL.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		sellingL.setTextFill(Color.WHITE);
		TextField sellingField = new TextField();
		sellingField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
		sellingField.setPromptText("selling price...");
		sellingField.setPrefSize(230, 40);
		editSelling.getChildren().addAll(sellingL, sellingField, btns[11]);
		Label nth = new Label("Nothing to edit right now");
		nth.setAlignment(Pos.CENTER);
		nth.setStyle("-fx-background-color: purple;");
		nth.setTextFill(Color.WHITE);
		nth.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
		edit.getChildren().add(nth);
		btns[0].setOnAction(e->{
			edit.getChildren().clear();
			edit.getChildren().add(editTitle);
		});
		btns[1].setOnAction(e->{
			edit.getChildren().clear();
			edit.getChildren().add(editISBN);
		});
		btns[2].setOnAction(e->{
			edit.getChildren().clear();
			edit.getChildren().add(editcategory);
		});
		btns[3].setOnAction(e->{
			edit.getChildren().clear();
			edit.getChildren().add(editOriginal);
		});
		btns[4].setOnAction(e->{
			edit.getChildren().clear();
			edit.getChildren().add(editPurchase);
		});
		btns[5].setOnAction(e->{
			edit.getChildren().clear();
			edit.getChildren().add(editSelling);
		});
		
		tempTitle.addListener(ov->{
			btns[0].setText("Title: " + tempTitle.getValue());
		});
		tempISBN.addListener(ov->{
			btns[1].setText("ISBN: " + tempISBN.getValue());
		});
		tempCategory.addListener(ov->{
			btns[2].setText("Category: " + tempCategory.getValue());
		});
		tempOriginal.addListener(ov->{
			btns[3].setText("Original price: " + tempOriginal.getValue());
		});
		tempPurchase.addListener(ov->{
			btns[4].setText("Purchase price: " + tempPurchase.getValue());
		});
		tempSelling.addListener(ov->{
			btns[5].setText("Selling price: " + tempSelling.getValue());
		});
		
		btns[6].setOnAction(e->{
			stock.modifyTitle(editable, titleField.getText());
			tempTitle.setValue(titleField.getText());
			tempTitle.addListener(ov->{
				btns[0].setText("Title: " + editable.getTitle());
			});
			edit.getChildren().clear();
			edit.getChildren().add(nth);
		});
		btns[7].setOnAction(e->{
			try {
			stock.modifyISBN(editable, ISBNField.getText());
			tempISBN.setValue(ISBNField.getText());
			editable.setISBN(ISBNField.getText());
			tempISBN.addListener(ov->{
				btns[1].setText("ISBN: " + editable.getISBN());
			});
			edit.getChildren().clear();
			edit.getChildren().add(nth);
			}catch(InvalidBookInfo e1) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Invalid book information");
		        alert.setContentText(e1.getMessage());
		        alert.showAndWait();
			}
		});
		btns[8].setOnAction(e->{
			stock.modifyCategory(editable, categoryField.getText());
			tempCategory.setValue(categoryField.getText());
			tempCategory.addListener(ov->{
				btns[2].setText("Category: " + editable.getCategory());
			});
			edit.getChildren().clear();
			edit.getChildren().add(nth);
		});
		btns[9].setOnAction(e->{
			stock.modifyOriginalPrice(editable, Double.parseDouble(originalField.getText()));
			tempOriginal.setValue(Double.parseDouble(originalField.getText()));
			tempOriginal.addListener(ov->{
				btns[3].setText("Original price: " + editable.getOriginalPrice());
			});
			edit.getChildren().clear();
			edit.getChildren().add(nth);
		});
		btns[10].setOnAction(e->{
			stock.modifyPurchasePrice(editable, Double.parseDouble(purchaseField.getText()));
			tempPurchase.setValue( Double.parseDouble(purchaseField.getText()));
			tempPurchase.addListener(ov->{
				btns[4].setText("Purchase price: " + editable.getPurchasePrice());
			});
			edit.getChildren().clear();
			edit.getChildren().add(nth);
		});
		btns[11].setOnAction(e->{
			stock.modifySellingPrice(editable, Double.parseDouble(sellingField.getText()));
			tempSelling.setValue( Double.parseDouble(sellingField.getText()));
			tempSelling.addListener(ov->{
				btns[5].setText("Selling price: " + editable.getSellingPrice());
			});
			edit.getChildren().clear();
			edit.getChildren().add(nth);
		});
		
		Line vLine = new Line(400, 300, 400, 640);
		vLine.setStroke(Color.WHITE);
		vLine.setStrokeWidth(7);
		
		HBox mainField = new HBox();
		mainField.setStyle("-fx-background-color: purple");
		mainField.setSpacing(40);
		mainField.setPadding(new Insets(20));
		mainField.getChildren().addAll(properties, vLine, edit);
		
		Button addMore = new Button("Add more of this item in stock");
		addMore.setStyle("-fx-background-color: purple; -fx-border-color: white;");
		addMore.setPrefSize(800, 60);
		addMore.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
		addMore.setTextFill(Color.WHITE);
		addMore.setOnMouseEntered(e->{
			addMore.setCursor(Cursor.HAND);
			addMore.setStyle("-fx-background-color: darkorchid; -fx-border-color: white;");
		});
		addMore.setOnMouseExited(e->{
			addMore.setStyle("-fx-background-color: purple; -fx-border-color: white;");
		});
		addMore.setOnAction(e->{
			(new QuantityView(editable)).show();
		});
		
		content.setTop(tape);
		content.setCenter(mainField);
		content.setBottom(addMore);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setTitle("Edit Book");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
}
