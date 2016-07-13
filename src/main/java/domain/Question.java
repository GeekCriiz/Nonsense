package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Question extends DomainEntity{

	// Constructors -----------------------------------------------------------

	public Question() {
		super();
	}

	// Attributes ---------------------------------------------------------
	private String category; 
	private String description; 
	private String answer; 
	
	
	@SafeHtml
	@NotBlank
	@Pattern(regexp = "^DEFINITION|CHARACTER_|DATE|FILM|ACRONYM$")
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@SafeHtml
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@SafeHtml
	@NotBlank
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	// Relationships ---------------------------------------------------------------
	private Collection<AnswerPlayer> answerPlayers;

	@Valid
	@NotNull
	@OneToMany(mappedBy="question")
	public Collection<AnswerPlayer> getAnswerPlayers() {
		return answerPlayers;
	}
	public void setAnswerPlayers(Collection<AnswerPlayer> answerPlayers) {
		this.answerPlayers = answerPlayers;
	}
}
