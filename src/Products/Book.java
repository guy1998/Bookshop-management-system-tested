package Products;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import Exceptions.InvalidBookInfo;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;

public class Book implements Cloneable, Serializable{
	
	private String ISBN;
	private String title;
	private Author [] authors;
	private String category;
	private double originalPrice;
	private double sellingPrice;
	private double purchasePrice;
	private Date datePublished;
	private int number;
	private transient SimpleStringProperty isbnProperty;
	private transient SimpleStringProperty titleProperty;
	private transient SimpleStringProperty authorProperty;
	private transient SimpleStringProperty categoryProperty;
	private transient SimpleDoubleProperty priceProperty;
	private transient SimpleDoubleProperty purchase;
	private transient SimpleDoubleProperty original;
	private transient SimpleIntegerProperty numberProperty;


	public Book(String ISBN, String title, String category, double purchasePrice, double originalPrice, double sellingPrice, int day, int month, int year, Author ... authors) throws InvalidBookInfo{
		
		if(!checkIsbnValidity(ISBN))
			throw new InvalidBookInfo("Book ISBN must be of the format xxx-xxxx-xxx");
		
		this.ISBN = ISBN;
		this.title = title;
		this.category = category;
		this.purchasePrice = purchasePrice;
		this.originalPrice = originalPrice;
		this.sellingPrice = sellingPrice;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		datePublished = cal.getTime();
		this.authors = authors;
	}

	public static boolean checkIsbnValidity(String ISBN){
		return ISBN.matches("\\d{3}-\\d{4}-\\d{3}");
	}
	
	public String getISBN(){return ISBN;}

	public String getTitle(){return title;}

	public String getCategory(){return category;}

	public double getSellingPrice(){return sellingPrice;}

	public double getOriginalPrice(){return originalPrice;}

	public double getPurchasePrice(){return purchasePrice;}
	
	public String getDatePublished() {return datePublished.toString();}
	
	public int getNumber() {return number;}

	public void setISBN(String ISBN) {this.ISBN = ISBN;}
	
	public void setTitle(String title) {
		this.title = title;
		this.setTitleProperty();
	}
	
	public void setCategory(String category) {this.category = category;}
	
	public void setSellingPrice(double sellingPrice) {this.sellingPrice = sellingPrice;}
	
	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	public void setAuthors(Author [] authors) {
		this.authors = authors;
	}
	
	public void setOriginalPrice(double originalPrice) {this.originalPrice = originalPrice;}
	
	public void setNumber(int number) {this.number = number;}
	
	public void setDatePublished(int day, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		this.datePublished = cal.getTime();
	}
	
	public void addNumber(int number) {
		this.number += number;
	}

	@Override
	public String toString()
	{
		StringBuilder authorsName = new StringBuilder();

		//since there may be more than 1 author for a specific book
		for(Author x : this.authors) {
			authorsName.append(x.toString());
			authorsName.append(", ");
		}

		return "\"" + this.title + "\" by " + authorsName + " Genre: " + category;
	}

	@Override
	public boolean equals(Object o) {
		
		if(o instanceof Book)
		{
			if(this.ISBN.equals(((Book)o).getISBN()))
				return true;
		}
		
		return false;
	}

	//To be tested
	@Override
	public Object clone() {
		try {
			Book temp = (Book)super.clone();
			temp.datePublished = (Date)datePublished.clone();
			temp.authors = (Author[])authors.clone();
			return temp;
		}catch(CloneNotSupportedException e) {
			return null;
		}
	}

	public String getIsbnProperty() {
		return isbnProperty.get();
	}

	public String getTitleProperty() {
		return titleProperty.get();
	}
	
	public SimpleStringProperty getTitleProperty(boolean check) {
		
		return titleProperty;
	}

	public String getAuthorProperty() {
		return authorProperty.get();
	}

	public String getCategoryProperty() {
		return categoryProperty.get();
	}

	public double getPriceProperty() {
		return priceProperty.get();
	}

	public void setIsbnProperty() {
		this.isbnProperty = new SimpleStringProperty(this.ISBN);
	}

	public void setTitleProperty() {
		this.titleProperty = new SimpleStringProperty(this.title);
	}

	public void setAuthorProperty() {
		StringBuilder authorsName = new StringBuilder();

		//since there may be more than 1 author for a specific book
		int i = 0;
		for(Author x : this.authors) {
			authorsName.append(x.toString());
			if (i++ != this.authors.length - 1)
				authorsName.append(", ");
		}
		this.authorProperty = new SimpleStringProperty(authorsName.toString());
	}

	public void setCategoryProperty() {
		this.categoryProperty = new SimpleStringProperty(this.category);
	}

	public void setPriceProperty() {
		this.priceProperty = new SimpleDoubleProperty(this.sellingPrice);
	}
	
	public void setPurchase() {
		this.purchase = new SimpleDoubleProperty(this.purchasePrice);
	}
	
	public double getPurchase() {
		return purchase.get();
	}
	
	public void setOriginal() {
		this.original = new SimpleDoubleProperty(this.originalPrice);
	}
	
	public double getOriginal() {
		return original.get();
	}
	
	public void setNumberProperty() {
		this.numberProperty = new SimpleIntegerProperty(this.number);
	}

	public int getNumberProperty() {
		return this.numberProperty.get();
	}
	
	public Label createLabel() {
		
		Label temp = new Label(this.toString() + "\nCopies:" + this.getNumber());
		return temp;
	}
}
