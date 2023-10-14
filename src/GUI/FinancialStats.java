package GUI;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import Products.Book;
import Products.Transaction;
import Products.TransactionControl;
import Users.Administrator;
import Users.Employee;
import Users.Librarian;
import Users.Manager;
import Users.Status;
import Users.User;
import Users.UserStack;
import Utilis.CompDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class FinancialStats {

	private Administrator admin;
	private TransactionControl tCtrl; 
	private UserStack employees;
	
	public FinancialStats(Administrator admin) {
		this.admin = admin;
		tCtrl = new TransactionControl();
		employees = new UserStack();
	}
	
	public void show(Stage primaryStage) {
		
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
			(new AdminView((Administrator)admin)).show(primaryStage);
		});
		Label profile = new Label("Finances");
		profile.setStyle("-fx-background-color: purple;");
		profile.setTextFill(Color.WHITE);
		profile.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
		tape.getChildren().addAll(back, profile);
		
		double [] array = values();
		double total = 0;
		for(double v : array)
			total += v;
		
		Label income = new Label("Incomes: " + array[0]);
		income.setStyle("-fx-background-color: green; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		income.setPrefSize(300, 70);
		income.setTextFill(Color.WHITE);
		income.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		income.setAlignment(Pos.CENTER_LEFT);
		income.setPadding(new Insets(10));
		
		Label spent = new Label("Expenditure: " + array[1]);
		spent.setStyle("-fx-background-color: red; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		spent.setPrefSize(300, 70);
		spent.setTextFill(Color.WHITE);
		spent.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		spent.setAlignment(Pos.CENTER_LEFT);
		spent.setPadding(new Insets(10));
		
		Label salaries = new Label("Salaries: " + array[2]);
		salaries.setStyle("-fx-background-color: red; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		salaries.setPrefSize(300, 70);
		salaries.setTextFill(Color.WHITE);
		salaries.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		salaries.setAlignment(Pos.CENTER_LEFT);
		salaries.setPadding(new Insets(10));
		
		Label totalL = new Label("Total: " + total);
		if(total > 0)
			totalL.setStyle("-fx-background-color: green; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		else
			totalL.setStyle("-fx-background-color: red; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		totalL.setPrefSize(300, 70);
		totalL.setTextFill(Color.WHITE);
		totalL.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
		totalL.setAlignment(Pos.CENTER_LEFT);
		totalL.setPadding(new Insets(10));
		VBox labels = new VBox();
		labels.setStyle("-fx-background-color: white;");
		labels.setSpacing(20);
		labels.setAlignment(Pos.CENTER);
		labels.getChildren().addAll(income, spent, salaries, totalL);
		
		NumberAxis yAxis = new NumberAxis();
		CategoryAxis xAxis = new CategoryAxis();
		BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
		ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();
		for(User u : employees.readUsers()) {
			if(u instanceof Employee)
				data.add(new Data(u.getName(), ((Employee)u).getSalary()));
		}
		XYChart.Series<String, Number> ser = new XYChart.Series<>();
		ser.getData().addAll(data);
		ser.setName("Salaries");
		bc.setTitle("Salaries chart");
		bc.getData().add(ser);
		
		HBox mainField = new HBox();
		mainField.setPadding(new Insets(20));
		mainField.setSpacing(50);
		mainField.setPrefSize(800, 480);
		mainField.setStyle("-fx-background-color: white;");
		mainField.setAlignment(Pos.CENTER);
		mainField.getChildren().addAll(labels, bc);
		
		content.setTop(tape);
		content.setCenter(mainField);
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Finances");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public double [] values() {
		
		//the numbers Mason what do they mean
		double [] numbers = new double[3];
		//Mason: so the index 0 is the incomes from books sold
		// index 1 is the money spent buying books
		//index 2 is the money spent on salaries
		
		for(Transaction t : tCtrl.getTransactions()){
			if(t.getTransactionDate().compareTo(new CompDate(1, LocalDate.now().getMonthValue(), LocalDate.now().getYear())) >= 0 &&
					t.getTransactionDate().compareTo(new CompDate(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear())) <= 0) {
				if(t.isOutgoing()) {
					for(Book b : t.getBooks())
						numbers[0] += b.getNumber() * b.getSellingPrice();
				}else {
					for(Book b : t.getBooks())
						numbers[1] -= b.getNumber() * b.getPurchasePrice();
				}
			}
			
		}
		
		ArrayList<User> allUsers = employees.readUsers();
		for(User u : allUsers) {
			if(u instanceof Employee)
				numbers[2] -= ((Employee)u).getSalary();
		}
		
		return numbers;
	}
	
}
