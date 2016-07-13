package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Orderr extends DomainEntity{

	// Constructors -----------------------------------------------------------

	public Orderr() {
		super();
	}

	// Attributes ---------------------------------------------------------
	private boolean answered;
	private int qNumber;
	
	@NotNull
	public boolean isAnswered() {
		return answered;
	}
	public void setAnswered(boolean answered) {
		this.answered = answered;
	}
	
	@NotNull
	@Min(1)
	@Max(5)
	public int getQNumber() {
		return qNumber;
	}
	public void setQNumber(int qNumber) {
		this.qNumber = qNumber;
	}
	
	
	// Relationships ---------------------------------------------------------------
	private Round round;
	private Question question;
	private Collection<AnswerPlayer> answerPlayers;
		
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Round getRound() {
		return round;
	}
	public void setRound(Round round) {
		this.round = round;
	}
	
	@Valid
	@ManyToOne(optional=true)
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="orderr")
	public Collection<AnswerPlayer> getAnswerPlayers() {
		return answerPlayers;
	}
	public void setAnswerPlayers(Collection<AnswerPlayer> answerPlayers) {
		this.answerPlayers = answerPlayers;
	}
	
	
}
