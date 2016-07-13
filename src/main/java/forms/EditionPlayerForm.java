package forms;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class EditionPlayerForm {

	public EditionPlayerForm(){
		
	}
	
	//Attributes -------------------------------------------
	private int id;
	private String surname;
	private String name;
	private String email;
	private String nickname;
	private String password;
	private String repeatPassword;
	
		
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@SafeHtml
	@NotBlank
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@SafeHtml
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	@SafeHtml	
	@Column(unique=true)
	@Size(min = 5, max = 16)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	
	@Size(min = 5, max = 32)
	@NotBlank
	@SafeHtml
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Size(min = 5, max = 32)
	@NotBlank
	@SafeHtml
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
}
