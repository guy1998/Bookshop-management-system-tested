package Main.Backend.Users;

import Main.Backend.Exceptions.InvalidEmail;
import Main.Backend.Exceptions.InvalidPasswordException;
import Main.Backend.Exceptions.InvalidPhoneNumberException;
import Main.Backend.Exceptions.InvalidUsernameException;
import Main.Backend.Notification.Message;
import Main.Backend.Utilis.CompDate;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public abstract class User implements Serializable{

	private String username;
	private String name;
	private String password;
	private String surname;
	private String email;
	private String phone;
	private CompDate birthday;
	private Status status;
	private final String userId;
	private transient SimpleStringProperty nameProperty;
	private transient SimpleStringProperty surnameProperty;
	private transient SimpleStringProperty usernameProperty;
	private transient SimpleStringProperty emailProperty;
	private transient SimpleStringProperty phoneProperty;
	private final MessageWriter writer;

	protected User(String name, String surname, String username, String password, String email, String phone, int day, int month, int year, Status status) throws Exception{
		
		if(username.isEmpty() || name.isEmpty() || surname.isEmpty() || password.isEmpty())
			throw new InvalidUsernameException("All the fields should be filled!");
		
		this.name = name;
		this.surname = surname;
		
		if(username.contains(this.name) || username.contains(this.surname))
			throw new InvalidUsernameException("Username cannot contain your name or surname");
		else if(!username.matches("^(?=[a-zA-Z/._]*\\d)(?=[\\dA-Z/._]*[a-z])(?=[a-z\\d/._]*[A-Z])(?=[a-zA-Z\\d]*[/._])[a-zA-Z\\d/._]{4,}$"))
			throw new InvalidUsernameException("Username must contain at least: a lowercase, an uppercase, a number and one of [/,_,.]!");
		else if(password.length() < 8)
			throw new InvalidPasswordException("Password too short!");
		else if(password.contains(username))
			throw new InvalidPasswordException("Password cannot be the same or contain the username");
		else if(!password.matches("^(?=[a-zA-Z/._]*\\d)(?=[\\dA-Z/._]*[a-z])(?=[a-z\\d/._]*[A-Z])(?=[a-zA-Z\\d]*[/._])[a-zA-Z\\d/._]{8,}$"))
			throw new InvalidPasswordException("Password must contain at least: a lowercase, an uppercase, a number and one of [/,_,.]!");
		else if(!phone.matches("\\+3556[789]\\d{7}"))
			throw new InvalidPhoneNumberException();
		else if(!email.matches("\\w+@gmail.com"))
			throw new InvalidEmail();
		
		this.username = username;
		this.password = password;
		this.status = status;
		this.email = email;
		this.phone = phone;
		birthday = new CompDate(day, month, year);
		this.userId = UUID.randomUUID().toString();
		writer = new MessageWriter("Database/Messages/" + this.userId + ".msg");
		
	}

	public String getName() {return name;}
	
	public void setName(String name) {
		this.name = name;
		this.setNameProperty();
	}
	
	public String getSurname() {return surname;}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getUsername() {return username;}
	
	public Status getStatus() {return status;}
	
	public String getBirthday() {return birthday.toString();}
	
	public void setBirthday(int day, int month, int year) {
		birthday = new CompDate(day, month, year);
	}
	
	public String getPassword() {return password;}
	
	public void setUserName(String username){
		this.username = username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return name + " " + surname + ", " + status;
	}
	
	public String getUserId() {
		return userId;
	}

	@Override
	public boolean equals (Object o) {
		
		if(o instanceof User && ((User)o).getUsername().equals(this.username))
			return true;
		
		return false;
	}

	public SimpleStringProperty getNameProperty() {
		return nameProperty;
	}

	public SimpleStringProperty getSurnameProperty() {
		return surnameProperty;
	}

	public SimpleStringProperty getUsernameProperty() {
		return usernameProperty;
	}

	public SimpleStringProperty getEmailProperty() {
		return emailProperty;
	}

	public SimpleStringProperty getPhoneProperty() {
		return phoneProperty;
	}

	public void setNameProperty() {
		this.nameProperty = new SimpleStringProperty(this.name);
	}

	public void setSurnameProperty() {
		this.surnameProperty = new SimpleStringProperty(this.surname);
	}

	public void setUsernameProperty() {
		this.usernameProperty = new SimpleStringProperty(this.username);
	}

	public void setEmailProperty() {
		this.emailProperty = new SimpleStringProperty(this.email);
	}

	public void setPhoneProperty() {
		this.phoneProperty = new SimpleStringProperty(this.phone);
	}

	public void writeMessages() {
		writer.writeMessages(readMessages());
	}

	public void writeMessages(ArrayList<Message> moreMsg) {
		writer.writeMessages(moreMsg);
	}

	public ArrayList<Message> readMessages(){
		return writer.readMessages();
	}

	
}
