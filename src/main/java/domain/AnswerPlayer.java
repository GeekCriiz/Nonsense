package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.Vote;
import domain.Player;
import domain.Question;

@Entity
@Access(AccessType.PROPERTY)
public class AnswerPlayer extends DomainEntity{

	// Constructors -----------------------------------------------------------

	public AnswerPlayer() {
		super();
	}

	// Attributes ---------------------------------------------------------

	private String answerPlayer;
	private boolean perfect;
	private boolean correct;

	@SafeHtml
	@NotBlank
	public String getAnswerPlayer() {
		return answerPlayer;
	}
	public void setAnswerPlayer(String answerPlayer) {
		this.answerPlayer = answerPlayer;
	}
	
	public boolean isPerfect() {
		return perfect;
	}
	public void setPerfect(boolean perfect) {
		this.perfect = perfect;
	}
	
	public boolean isCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	// Relationships ---------------------------------------------------------------

	private Collection<Vote> votes;
	private Player player;
	private Question question;
	private Orderr orderr;
	
	@Valid
	@OneToMany(mappedBy="answerPlayer")
	public Collection<Vote> getVotes() {
		return votes;
	}
	public void setVotes(Collection<Vote> votes) {
		this.votes = votes;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Orderr getOrderr() {
		return orderr;
	}
	public void setOrderr(Orderr orderr) {
		this.orderr = orderr;
	}
}
