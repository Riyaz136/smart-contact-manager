package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserForm {

	
	@NotBlank(message = "Username is required")
	@Size( min =3 ,message = "Min 3 character")
	private  String name;	
	
	@Email(message = "Invalid is required")
	@NotBlank(message = "email is required")
	//@pattern we can user to pass our pattern 
	private String email;
	
	@NotBlank(message = "password is  required")
	@Size( min =3 ,message = "Min 3 character")
	private String password;
	
	@NotBlank(message = " About is required")
	private String about;
	
	@Size(min=8, max=12 , message = "Invalid Phone Number")
	private String phoneNumber;
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	public UserForm(String name, String email, String password, String about, String phoneNumber) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.phoneNumber = phoneNumber;
	}
	
	public UserForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "UserForm [name=" + name + ", email=" + email + ", password=" + password + ", about=" + about
				+ ", phoneNumber=" + phoneNumber + "]";
	}

}
