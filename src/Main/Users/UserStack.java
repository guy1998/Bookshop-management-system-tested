package Main.Users;
import java.io.Serializable;
import java.util.ArrayList;

import Main.Exceptions.InvalidEmail;
import Main.Exceptions.InvalidPasswordException;
import Main.Exceptions.InvalidPhoneNumberException;
import Main.Exceptions.InvalidUsernameException;
import Main.Exceptions.NonExistantUserException;
import Main.Exceptions.UserAlreadyExistsException;

public class UserStack implements Serializable{

	private ArrayList<User> users;
	private final UserDb proxy;
	public UserStack() {
		proxy = new UserProxy("Database/user.dat");
		users = new ArrayList<User>();
        users = proxy.readUsers();
	}

	public UserStack(UserDb proxy){
		this.proxy = proxy;
		users = new ArrayList<User>();
		users = proxy.readUsers();
	}
	
	public UserStack(User user) {

		proxy = new UserProxy("Database/user.dat");
		users = new ArrayList<>();
		users.add(user);
		proxy.writeUsers(users);
	
	}

	public ArrayList<User> readUsers(){
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
	
	public void addUser(User user) throws UserAlreadyExistsException{

		if(users.contains(user))
			throw new UserAlreadyExistsException();

		users.add(user);
		proxy.writeUsers(users);
	}

	public void deleteUser(String username) throws NonExistantUserException{
		User temp = this.findUser(username);
		users.remove(this.findUser(username));
		proxy.writeUsers(users);
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
		else if(password.contains(user.getName()) || password.contains(user.getSurname()))
			throw new InvalidPasswordException("Password should not contain the name or surname!");
		else if(!password.matches("^(?=[a-zA-Z/._]*\\d)(?=[\\dA-Z/._]*[a-z])(?=[a-z\\d/._]*[A-Z])(?=[a-zA-Z\\d]*[/._])[a-zA-Z\\d/_]{8,}$"))
			throw new InvalidPasswordException("Password must contain at least: a lowercase, an uppercase, a number and one of [/,_,.]!");
		
		this.findUser(user).setPassword(password);
		proxy.writeUsers(users);
		
	}
	
	public void modifyUsername(User user, String username) throws InvalidUsernameException{
		if (username.length() < 8)
			throw new InvalidUsernameException("Username too short!");
		else if(username.contains(user.getName()) || username.contains(user.getSurname()))
			throw new InvalidUsernameException("Username cannot contain your name or surname");
		else if(!username.matches("^(?=[a-zA-Z/._]*\\d)(?=[\\dA-Z/._]*[a-z])(?=[a-z\\d/._]*[A-Z])(?=[a-zA-Z\\d]*[/._])[a-zA-Z\\d/._]{4,}$"))
			throw new InvalidUsernameException("Username must contain at least: a lowercase, an uppercase, a number and one of [/,_,.]!");
		
		this.findUser(user).setUserName(username);
		proxy.writeUsers(users);
	}
	
	public void modifyName(User user, String name) {
		System.out.println(this.findUser(user));
		this.findUser(user).setName(name);
		proxy.writeUsers(users);
	}
	
	public void modifySurname(User user, String surname) {
		this.findUser(user).setSurname(surname);
		proxy.writeUsers(users);
	}
	
	public void modifyPhone(User user, String phone) throws InvalidPhoneNumberException{
		if(!phone.matches("\\+3556[789]\\d{7}"))
			throw new InvalidPhoneNumberException();
		this.findUser(user).setPhone(phone);
		proxy.writeUsers(users);
	}
	
	public void modifyEmail(User user, String email) throws InvalidEmail{
		if(!email.matches("\\w+@gmail.com"))
			throw new InvalidEmail();
		this.findUser(user).setEmail(email);
		proxy.writeUsers(users);
	}
	
	public void modifySSN(User user, String SSN) {
		((Employee)this.findUser(user)).setSSN(SSN);
		proxy.writeUsers(users);
	}
	
	public void modifySalary(User user, Double salary) {
		((Employee)this.findUser(user)).setSalary(salary);
		proxy.writeUsers(users);
	}
	
	public void modifyPermission(User user, Access permission) {
		((Employee)this.findUser(user)).setPermission(permission);
		proxy.writeUsers(users);
	}
}
