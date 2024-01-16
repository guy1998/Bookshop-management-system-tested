package Main.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import Main.Backend.Products.Book;
import Main.Backend.Products.Transaction;
import Main.Backend.Products.TransactionControl;
import Main.Backend.Users.Manager;
import Main.Backend.Utilis.CompDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
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

public class BookStatistics {
	
	private Manager current;
	
	public BookStatistics(Manager current) {
		this.current = current;
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
			(new ManagerView(current, false)).show(primaryStage);
		});
		Label profile = new Label("Books statistics");
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
		RadioMenuItem daily = new RadioMenuItem("Daily");
		RadioMenuItem monthly = new RadioMenuItem("Monthly");
		RadioMenuItem total = new RadioMenuItem("Total");
		ToggleGroup group = new ToggleGroup();
		menuBox.getChildren().add(mb);
		
		group.getToggles().addAll(daily, monthly, total);
		filter.getItems().addAll(daily, monthly, total);
		mb.getMenus().add(filter);
		tape.getChildren().addAll(back, profile, menuBox);
		
		content.setTop(tape);
		dailyStatistics ds = new dailyStatistics(new CompDate(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()), 
				new CompDate(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
		ds.fillContent();
		content.setCenter(ds);

		daily.setOnAction(e->{
			content.setCenter(ds);
		});
		
		monthly.setOnAction(e->{
			dailyStatistics monthlyDs = new dailyStatistics(new CompDate(1, LocalDate.now().getMonthValue(), LocalDate.now().getYear()), 
					new CompDate(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
			monthlyDs.fillContent();
			content.setCenter(monthlyDs);
		});
		
		total.setOnAction(e->{
			dailyStatistics totalDs = new dailyStatistics(new CompDate(1, 1, 2023), 
					new CompDate(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
			totalDs.fillContent();
			content.setCenter(totalDs);
		});
		
		Scene scene = new Scene(content, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Books Statistics");
		primaryStage.show();
		
	}
	
}

class dailyStatistics extends HBox{
	
	private CompDate date1;
	private CompDate date2;
	
	public dailyStatistics(CompDate date1, CompDate date2) {
		this.date1 = date1;
		this.date2 = date2;
	}
	
	public void fillContent() {
		
		this.setPadding(new Insets(20));
		this.setSpacing(50);
		this.setPrefSize(800, 480);
		this.setStyle("-fx-background-color: white;");
		this.setAlignment(Pos.CENTER);
		
		ArrayList<Book> sold = new ArrayList<Book>();
		ArrayList<Book> bought = new ArrayList<Book>();
		
		TransactionControl ctrl = new TransactionControl();
		ArrayList<Transaction> transactions = ctrl.getTransactions();
		
		for(Transaction t:transactions) {
			if(t.getTransactionDate().compareTo(date1) >= 0 && t.getTransactionDate().compareTo(date2) <= 0) {
				if(t.isOutgoing()) 
					sold.addAll(t.getBooks());
				else 
					bought.addAll(t.getBooks());
			}
		}
		System.out.println(sold);
		System.out.println(bought);
		
		ArrayList<String> categories = new ArrayList<>();
		try {
			File file = new File("Categories.txt");
			Scanner in = new Scanner(file);
			while(in.hasNext()) {
				categories.add(in.next());
			}
		}catch(Exception e) {
			System.out.println("The categories were not found!");
		}
		System.out.println(categories);
		int [] categoryNumber = new int[categories.size()];
		
		Book mostSold = null;
		if(!sold.isEmpty()) {
			mostSold = sold.get(0);
			for(Book b:sold) {
				if(b.getNumber() > mostSold.getNumber())
					mostSold = b;
				categoryNumber[categories.indexOf(b.getCategory())]+=b.getNumber();
			}
		}
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		for(int i=0; i<categories.size(); i++)
			pieChartData.add(new Data(categories.get(i), categoryNumber[i]));
		PieChart chart = new PieChart(pieChartData);
		chart.setTitle("Books sold by categories");
		chart.setPrefSize(400, 400);
		
		int soldCnt = 0;
		int boughtCnt = 0;
		
		for(Book b:sold)
			soldCnt += b.getNumber();
		
		for(Book b:bought)
			boughtCnt += b.getNumber();
		
		VBox writingStats = new VBox();
		writingStats.setStyle("-fx-background-color: white;");
		writingStats.setSpacing(10);
		writingStats.setAlignment(Pos.CENTER);
		Label mostSoldLabel;
		if(mostSold != null)
			mostSoldLabel =  new Label("Most sold: " + mostSold.getTitle());
		else
			 mostSoldLabel = new Label("Most sold: No books sold");
		mostSoldLabel.setStyle("-fx-background-color: purple; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		mostSoldLabel.setTextFill(Color.WHITE);
		mostSoldLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
		mostSoldLabel.setWrapText(true);
		mostSoldLabel.setPrefSize(300, 60);
		mostSoldLabel.setAlignment(Pos.CENTER);
		Label totalSoldLabel = new Label("Books sold: " + soldCnt);
		totalSoldLabel.setStyle("-fx-background-color: purple; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		totalSoldLabel.setTextFill(Color.WHITE);
		totalSoldLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
		totalSoldLabel.setWrapText(true);
		totalSoldLabel.setPrefSize(300, 60);
		totalSoldLabel.setAlignment(Pos.CENTER);
		Label totalboughtLabel = new Label("Books bought: " + boughtCnt);
		totalboughtLabel.setStyle("-fx-background-color: purple; -fx-background-radius: 15px; -fx-border-radius: 15px;");
		totalboughtLabel.setTextFill(Color.WHITE);
		totalboughtLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
		totalboughtLabel.setWrapText(true);
		totalboughtLabel.setPrefSize(300, 60);
		totalboughtLabel.setAlignment(Pos.CENTER);
		writingStats.getChildren().addAll(mostSoldLabel, totalSoldLabel, totalboughtLabel);
		
		if(sold.isEmpty()) {
			mostSoldLabel.setPrefSize(500, 60);
			totalSoldLabel.setPrefSize(500, 60);
			totalboughtLabel.setPrefSize(500, 60);
			this.getChildren().add(writingStats);
		}
		else
			this.getChildren().addAll(chart, writingStats);
		
		
	}
	
}
