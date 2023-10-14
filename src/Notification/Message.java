package Notification;
import java.io.Serializable;
import java.util.Date;
import Users.*;
import javafx.scene.control.Label;


public class Message implements Serializable{

	private boolean read;
	private String header;
	private String text;
	private Date timeWritten;
	private User user;
	
	public Message () {
		this.read = false;
		this.header = "Welcome to BookShop Managment System";
		this.text = "The text about the features";
		this.timeWritten = new Date();
	}
	public Message(User user) {
		this.header = "(no header)";
		this.user = user;
		this.read = false;	
		timeWritten = new Date();
	}
	
	public Message(User user, String text) {
		this.header = "(no header)";
		this.user = user;
		this.text = text;
		this.read = false;
		timeWritten = new Date();
	}
	
	public Message(User user, String header, String text) {
		this.user = user;
		this.header = header;
		this.text = text;
		this.read = false;
		timeWritten = new Date();
	}
	
	public boolean isRead() {
		return read;
	}
	
	public void markAsRead() {
		this.read = true;
	}
	
	public void markAsUnread() {
		this.read = false;
	}
	
	public String getHeader() {
		return header;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return header + "\nFrom: " + user.getName() + " " + user.getSurname() + " " + timeWritten.toString();
	}
	
	public Label createLabel() {
		if(user != null)
			return new Label(this.toString());
		else
			return new Label(header + "\nFrom: System" + " " + timeWritten.toString());
			
	}
	
}
