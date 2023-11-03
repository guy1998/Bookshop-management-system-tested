package Users;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import Exceptions.InvalidEmail;
import Exceptions.InvalidPasswordException;
import Exceptions.InvalidPhoneNumberException;
import Exceptions.InvalidUsernameException;
import Exceptions.NonExistantUserException;
import Exceptions.UserAlreadyExistsException;

public class UserStack implements Serializable{

	private ArrayList<User> users;
	private final File userList;
	
	public UserStack() {
		
		userList = new File("user.dat");
		users = new ArrayList<User>();
		if(!userList.exists())
			writeUsers();
		else
			users = readUsers();
		
	}
	
	public UserStack(User user) {
		
		userList = new File("user.dat");
		users = new ArrayList<>();
		users.add(user);
		writeUsers();
	
	}
	
	public void writeUsers() {
		
		try {
			FileOutputStream out = new FileOutputStream(userList);
			ObjectOutputStream outOb = new ObjectOutputStream(out);
			outOb.writeObject(users);
			outOb.close();
			out.close();
		}catch (FileNotFoundException e) {
			System.err.println("File not Found!!!");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	public ArrayList<User> readUsers() {
		
		try {
			FileInputStream in = new FileInputStream(userList);
			ObjectInputStream inOb = new ObjectInputStream(in);
			users = (ArrayList<User>) inOb.readObject();
			setProperties();
			in.close();
			inOb.close();
		}catch (FileNotFoundException e) {
			System.err.println("File not Found!!!");
		} catch (ClassNotFoundException e) {
			System.err.println("Class not Found!!!");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		
		return users;
	}
	
	public User findUser(User user) {
		for(int i=0; i<users.size(); i++)
			if(user.equals(users.get(i)))
				return users.get(i);
		
		return null;
	}
	
	public User findUser(String username) throws NonExistantUserException{
		
		for(int i=0; i<users.size(); i++)
			if(users.get(i).getUsername().equals(username))
				return users.get(i);
		
		throw new NonExistantUserException();
	}
	
	public boolean exists(String username) {
		
		for(int i=0; i<users.size(); i++)
			if(users.get(i).getUsername().equals(username))
				return true;
		
		return false;
	}
	
	public void addUser(User user) throws UserAlreadyExistsException{
		
		if(users.contains(user))
			throw new UserAlreadyExistsException();
		
		users.add(user);
		writeUsers();
	}
	
	public void deleteUser(String username) throws NonExistantUserException{
		User temp = this.findUser(username);
		users.remove(this.findUser(username));
		writeUsers();
	}
	
	public void setProperties() {
		for(User u:users) {
			u.setEmailProperty();
			u.setNameProperty();
			u.setPhoneProperty();
			u.setSurnameProperty();
			u.setUsernameProperty();
		}
	}
	
	public ArrayList<User> filterByStatus(Status status) {
		
		ArrayList<User> temp = new ArrayList<>();
		
		for(int i=0; i<users.size(); i++)
			if(users.get(i).getStatus().equals(status))
				temp.add(users.get(i));
				
		return temp;
	}
	
	public void modifyPassword(User user, String password) throws InvalidPasswordException{
		
		if(password.equals(user.getPassword()))
			throw new InvalidPasswordException("New password cannot be the same as old password");
		else if(password.length() < 8)
			throw new InvalidPasswordException("Password too short!");
		else if(password.contains(user.getUsername()))
			throw new InvalidPasswordException("Password cannot be the same or contain the username");
		else if(!password.matches("^(?=[a-zA-Z/._]*\\d)(?=[\\dA-Z/._]*[a-z])(?=[a-z\\d/._]*[A-Z])(?=[a-zA-Z\\d]*[/._])[a-zA-Z\\d/_]{8,}$"))
			throw new InvalidPasswordException("Password must contain at least: a lowercase, an uppercase, a number and one of [/,_,.]!");
		
		this.findUser(user).setPassword(password);
		writeUsers();
		
	}
	
	public void modifyUsername(User user, String username) throws InvalidUsernameException{
		if(username.contains(user.getName()) || username.contains(user.getSurname()))
			throw new InvalidUsernameException("Username cannot contain your name or surname");
		else if(!username.matches("^(?=[a-zA-Z/._]*\\d)(?=[\\dA-Z/._]*[a-z])(?=[a-z\\d/._]*[A-Z])(?=[a-zA-Z\\d]*[/._])[a-zA-Z\\d/._]{4,}$"))
			throw new InvalidUsernameException("Username must contain at least: a lowercase, an uppercase, a number and one of [/,_,.]!");
		
		this.findUser(user).setUserName(username);
		writeUsers();
	}
	
	public void modifyName(User user, String name) {
		System.out.println(this.findUser(user));
		this.findUser(user).setName(name);
		writeUsers();
	}
	
	public void modifySurname(User user, String surname) {
		this.findUser(user).setSurname(surname);
		writeUsers();
	}
	
	public void modifyPhone(User user, String phone) throws InvalidPhoneNumberException{
		if(!phone.matches("\\+3556[789]\\d{7}"))
			throw new InvalidPhoneNumberException();
		this.findUser(user).setPhone(phone);
		writeUsers();
	}
	
	public void modifyEmail(User user, String email) throws InvalidEmail{
		if(!email.matches("\\w+@gmail.com"))
			throw new InvalidEmail();
		this.findUser(user).setEmail(email);
		writeUsers();
	}
	
	public void modifyBirthday(User user, int day, int month, int year) {
		this.findUser(user).setBirthday(day, month, year);
		writeUsers();
	}
	
	public void modifySSN(User user, String SSN) {
		((Employee)this.findUser(user)).setSSN(SSN);
		writeUsers();
	}
	
	public void modifySalary(User user, Double salary) {
		((Employee)this.findUser(user)).setSalary(salary);
		writeUsers();
	}
	
	public void modifyPermission(User user, Access permission) {
		((Employee)this.findUser(user)).setPermission(permission);
		writeUsers();
	}
}
