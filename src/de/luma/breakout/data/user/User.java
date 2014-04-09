package de.luma.breakout.data.user;

public class User {
	
	private static User user = null;

	private String username;
	private String password;	
	
	public User() {
		super();
	}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		if (username == null) {
			return "";
		}
		return username;
	}
	
	public String getPassword() {
		if (password == null) {
			return "";
		}
		return password;
	}	
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static User findByUsername(String username) {
		if (user == null) {
			user = new User("luma", "lülü");
		}
		return user;
	}
	
}