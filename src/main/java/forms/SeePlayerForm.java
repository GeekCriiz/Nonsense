package forms;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class SeePlayerForm {

	public SeePlayerForm(){
		
	}
	
	//Attributes -------------------------------------------
	private String surname;
	private String name;
	private String nickname;
	
		
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
	
	@NotBlank
	@SafeHtml	
	@Column(unique=true)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	
}
