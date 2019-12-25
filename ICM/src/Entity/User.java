package Entity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;

import javafx.scene.image.Image;
/**
 * The <code>Member</code> entity, represents a Member in the database.
 */
public class User {

	private String id;

    private String phone;
    private String email;

    private boolean isConnected;
    private Role role;
    private String password;
    private String username;
    
    
    public User() {
    	
    }
    
	

    public User(String id, String phone, String email, boolean isConnected, Role role, String password,
		String username) {
	super();
	this.id = id;
	this.phone = phone;
	this.email = email;
	this.isConnected = isConnected;
	this.role = role;
	this.password = password;
	this.username = username;
}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
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



	public boolean isConnected() {
		return isConnected;
	}



	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}



	public Role getRole() {
		return role;
	}



	public void setPermission(Role role) {
		this.role = role;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}


	public enum Role {
	Studint, Secturer, Employee;
    }
}