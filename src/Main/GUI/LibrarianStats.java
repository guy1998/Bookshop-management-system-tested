package Main.GUI;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import Main.Users.*;
import Main.Utilis.CompDate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LibrarianStats {
	
	private User current;
	private Librarian librarian;
	private int days;
	
	public LibrarianStats(User current, Librarian librarian) {
		this.current = current;
		this.librarian = librarian;
	}
	
	public void show(Stage primaryStage) {
		
		SimpleIntegerProperty booksSold = new SimpleIntegerProperty();
		SimpleDoubleProperty moneyMade = new SimpleDoubleProperty();
		SimpleIntegerProperty billsIssued = new SimpleIntegerProperty();
		booksSold.setValue(librarian.nrOfBooks(new CompDate(Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR)));
		moneyMade.setValue(librarian.moneyMade(new CompDate(Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR)));
		billsIssued.setValue(librarian.nrOfBills(new CompDate(Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR)));
		
		BorderPane content = new BorderPane();
		
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
			if(current.getStatus() == Status.ADMINISTRATOR)
				(new EmployeeView((Administrator)current)).show(primaryStage);
			else if(current.getStatus() == Status.MANAGER)
				(new UsersView(current)).show(primaryStage);
			
		});
		Label profile = new Label(librarian.getName() + " " + librarian.getSurname());
		profile.setStyle("-fx-background-color: purple;");
		profile.setTextFill(Color.WHITE);
		profile.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		HBox menuBox = new HBox();
		menuBox.setStyle("-fx-background-color: purple;");
		menuBox.setAlignment(Pos.BOTTOM_RIGHT);
		menuBox.setPrefWidth(300);
		MenuBar mb = new MenuBar();
		mb.setStyle("-fx-background-color: purple;");
		Menu filter = new Menu("Data filter");
		RadioMenuItem specific = new RadioMenuItem("Specific date");
		RadioMenuItem interval = new RadioMenuItem("Interval of days");
		ToggleGroup group = new ToggleGroup();
		Menu edit = new Menu("Edit");
		MenuItem editItem = new MenuItem("Edit");
		edit.getItems().add(editItem);
		editItem.setOnAction(e->{
			(new UserEdit((Administrator)current, librarian)).show(primaryStage);
		});
		if(current.getStatus().equals(Status.ADMINISTRATOR))
			mb.getMenus().add(edit);
		menuBox.getChildren().add(mb);
		
		group.getToggles().addAll(specific, interval);
		filter.getItems().addAll(specific, interval);
		mb.getMenus().add(filter);
		tape.getChildren().addAll(back, profile, menuBox);
		
		DatePicker specificDate = new DatePicker(LocalDate.now());
		specificDate.setFocusTraversable(false);
		HBox dateBox = new HBox();
		dateBox.setStyle("-fx-background-color: purple;");
		dateBox.setPrefSize(800, 60);
		dateBox.setAlignment(Pos.CENTER_LEFT);
		dateBox.setPadding(new Insets(20));
		dateBox.setSpacing(40);
		Label specificLabel = new Label("Choose a specific date:");
		specificLabel.setTextFill(Color.WHITE);
		specificLabel.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 24));
		dateBox.getChildren().addAll(specificLabel, specificDate);
		
		DatePicker start = new DatePicker(LocalDate.now());
		start.setFocusTraversable(false);
		DatePicker end = new DatePicker(LocalDate.now());
		end.setFocusTraversable(false);
		HBox dateBox2 = new HBox();
		dateBox2.setStyle("-fx-background-color: purple;");
		dateBox2.setPrefSize(800, 70);
		dateBox2.setAlignment(Pos.CENTER_LEFT);
		dateBox2.setPadding(new Insets(20));
		dateBox2.setSpacing(40);
		Label intervalLabel = new Label("Choose an interval of days: ");
		intervalLabel.setTextFill(Color.WHITE);
		intervalLabel.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 17));
		dateBox2.getChildren().addAll(intervalLabel, start, end);
		
		Label filterPicker = new Label("Please pick the filter for the statistics!");
		filterPicker.setStyle("-fx-background-color: purple;");
		filterPicker.setTextFill(Color.WHITE);
		filterPicker.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
		filterPicker.setPrefSize(800, 60);
		filterPicker.setAlignment(Pos.CENTER);
		filterPicker.setPadding(new Insets(20));
		
		content.setBottom(filterPicker);
		content.setTop(tape);
		
		interval.setOnAction(e->{
			content.setBottom(dateBox2);
			LocalDate temp = start.getValue();
			LocalDate temp2 = end.getValue();
			days = (int)ChronoUnit.DAYS.between(temp, temp2);
			System.out.println(days);
			booksSold.setValue(librarian.nrOfBooks(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear()), 
					new CompDate(temp2.getDayOfMonth(), temp2.getMonthValue(), temp2.getYear())));
			moneyMade.setValue(librarian.moneyMade(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear()), 
					new CompDate(temp2.getDayOfMonth(), temp2.getMonthValue(), temp2.getYear())));
			billsIssued.setValue(librarian.nrOfBills(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear()), 
					new CompDate(temp2.getDayOfMonth(), temp2.getMonthValue(), temp2.getYear())));
		});
		specific.setOnAction(e->{
			content.setBottom(dateBox);
			days = 0;
			System.out.println(days);
			LocalDate temp = specificDate.getValue();
			booksSold.setValue(librarian.nrOfBooks(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear())));
			moneyMade.setValue(librarian.moneyMade(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear())));
			billsIssued.setValue(librarian.nrOfBills(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear())));
		});
		
		VBox vSpace1 = new VBox();
		vSpace1.setPadding(new Insets(10));
		vSpace1.setSpacing(20);
		vSpace1.setAlignment(Pos.CENTER);
		StackPane nrOfBooks = new StackPane();
		Circle outer = new Circle(90);
		outer.setFill(Color.RED);
		Circle inner = new Circle(70);
		inner.setFill(Color.WHITE);
		Label numberBooks = new Label("" + booksSold.getValue());
		numberBooks.setTextFill(Color.PURPLE);
		numberBooks.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
		nrOfBooks.getChildren().add(outer);
		nrOfBooks.getChildren().add(inner);
		nrOfBooks.getChildren().add(numberBooks);
		Label booksSoldLabel = new Label("Books sold");
		booksSoldLabel.setTextFill(Color.PURPLE);
		booksSoldLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
		booksSoldLabel.setAlignment(Pos.CENTER);
		vSpace1.getChildren().addAll(nrOfBooks, booksSoldLabel);
		
		VBox vSpace2 = new VBox();
		vSpace2.setPadding(new Insets(10));
		vSpace2.setSpacing(20);
		vSpace2.setAlignment(Pos.CENTER);
		Circle outer2 = new Circle(90);
		outer2.setFill(Color.RED);
		Circle inner2 = new Circle(70);
		inner2.setFill(Color.WHITE);
		StackPane nrOfBills = new StackPane();
		Label numberBills = new Label("" + billsIssued.getValue());
		numberBills.setTextFill(Color.PURPLE);
		numberBills.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
		nrOfBills.getChildren().add(outer2);
		nrOfBills.getChildren().add(inner2);
		nrOfBills.getChildren().add(numberBills);
		Label billsLabel = new Label("Total of bills");
		billsLabel.setTextFill(Color.PURPLE);
		billsLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
		billsLabel.setAlignment(Pos.CENTER);
		vSpace2.getChildren().addAll(nrOfBills, billsLabel);
		
		VBox vSpace3 = new VBox();
		vSpace3.setPadding(new Insets(10));
		vSpace3.setSpacing(20);
		vSpace3.setAlignment(Pos.CENTER);
		Circle outer3 = new Circle(90);
		outer3.setFill(Color.RED);
		Circle inner3 = new Circle(70);
		inner3.setFill(Color.WHITE);
		StackPane money = new StackPane();
		Label moneyNumber = new Label(moneyMade.getValue() + "$");
		moneyNumber.setTextFill(Color.PURPLE);
		moneyNumber.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		money.getChildren().add(outer3);
		money.getChildren().add(inner3);
		money.getChildren().add(moneyNumber);
		Label moneyLabel = new Label("Money made");
		moneyLabel.setTextFill(Color.PURPLE);
		moneyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
		moneyLabel.setAlignment(Pos.CENTER);
		vSpace3.getChildren().addAll(money, moneyLabel);
		
		HBox mainField = new HBox();
		mainField.setStyle("-fx-background-color: white;");
		mainField.setSpacing(40);
		mainField.setPadding(new Insets(20));
		mainField.setAlignment(Pos.CENTER);
		mainField.getChildren().addAll(vSpace1, vSpace2, vSpace3);
		
		specificDate.setOnAction(e->{
			LocalDate temp = specificDate.getValue();
			days = 0;
			booksSold.setValue(librarian.nrOfBooks(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear())));
			moneyMade.setValue(librarian.moneyMade(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear())));
			billsIssued.setValue(librarian.nrOfBills(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear())));
		});
		
		start.setOnAction(e->{
			LocalDate temp = start.getValue();
			LocalDate temp2 = end.getValue();
			days = (int)ChronoUnit.DAYS.between(temp, temp2);
			booksSold.setValue(librarian.nrOfBooks(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear()), 
					new CompDate(temp2.getDayOfMonth(), temp2.getMonthValue(), temp2.getYear())));
			moneyMade.setValue(librarian.moneyMade(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear()), 
					new CompDate(temp2.getDayOfMonth(), temp2.getMonthValue(), temp2.getYear())));
			billsIssued.setValue(librarian.nrOfBills(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear()), 
					new CompDate(temp2.getDayOfMonth(), temp2.getMonthValue(), temp2.getYear())));
		});
		end.setOnAction(e->{
			LocalDate temp = start.getValue();
			LocalDate temp2 = end.getValue();
			days = (int)ChronoUnit.DAYS.between(temp, temp2);
			booksSold.setValue(librarian.nrOfBooks(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear()), 
					new CompDate(temp2.getDayOfMonth(), temp2.getMonthValue(), temp2.getYear())));
			moneyMade.setValue(librarian.moneyMade(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear()), 
					new CompDate(temp2.getDayOfMonth(), temp2.getMonthValue(), temp2.getYear())));
			billsIssued.setValue(librarian.nrOfBills(new CompDate(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear()), 
					new CompDate(temp2.getDayOfMonth(), temp2.getMonthValue(), temp2.getYear())));
		});
		
		booksSold.addListener(ov -> {
			numberBooks.setText(booksSold.getValue() + "");
			if(days == 0) {
				if(booksSold.getValue() > 10) {
					outer.setFill(Color.GREEN);
				}else if(booksSold.getValue() > 5) {
					outer.setFill(Color.ORANGE);
				}else {
					outer.setFill(Color.RED);
				}
			}else {
				if(booksSold.getValue()/(days + 1) > 10) {
					outer.setFill(Color.GREEN);
				}else if(booksSold.getValue()/(days + 1) > 5) {
					outer.setFill(Color.ORANGE);
				}else {
					outer.setFill(Color.RED);
				}
			}
				
		});
		billsIssued.addListener(ov->{
			numberBills.setText(billsIssued.getValue() + "");
			if(days == 0) {
				if(billsIssued.getValue() > 5) {
					outer2.setFill(Color.GREEN);
				}else if(billsIssued.getValue() > 2) {
					outer2.setFill(Color.ORANGE);
				}else {
					outer2.setFill(Color.RED);
				}
			}else {
				if(billsIssued.getValue()/(days + 1.0) > 10) {
					outer2.setFill(Color.GREEN);
				}else if(billsIssued.getValue()/(days + 1.0) > 5) {
					outer2.setFill(Color.ORANGE);
				}else {
					outer2.setFill(Color.RED);
				}
			}
		});
		moneyMade.addListener(ov->{
			moneyNumber.setText(moneyMade.getValue() + "$");
			if(days == 0) {
				if(moneyMade.getValue() > 10) {
					outer3.setFill(Color.GREEN);
				}else if(moneyMade.getValue() > 5) {
					outer3.setFill(Color.ORANGE);
				}else {
					outer3.setFill(Color.RED);
				}
			}else {
				System.out.println("Days");
				if(moneyMade.getValue()/(days + 1.0) > 10) {
					outer.setFill(Color.GREEN);
				}else if(moneyMade.getValue()/(days + 1) > 5) {
					outer.setFill(Color.ORANGE);
				}else {
					outer.setFill(Color.RED);
				}
			}
		});
		
		content.setCenter(mainField);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Librarian stats");
		primaryStage.show();
	}
}
