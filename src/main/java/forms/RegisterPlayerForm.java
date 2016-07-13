package forms;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class RegisterPlayerForm {
	
	//Attributes---------------------------------------
	private String name;
	private String surname;
	private String nickname;
	private String email;
	private String username;
	private String password;
	private String repeatPassword;
	
	
	
	@NotBlank
	@SafeHtml
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	@SafeHtml	
	@Column(unique=true)
	@Size(min = 5, max = 16)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@NotBlank
	@SafeHtml
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@SafeHtml
	@Email
	@NotBlank
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
		
	@NotBlank
	@Size(min = 5, max = 16)
	@SafeHtml
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml
	public String getRepeatPassword() {
		return repeatPassword;
	}
	
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
	
}
