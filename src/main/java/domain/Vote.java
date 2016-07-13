package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


import domain.Vote;
import domain.Player;

@Entity
@Access(AccessType.PROPERTY)
public class Vote extends DomainEntity{

	// Constructors -----------------------------------------------------------

	public Vote() {
		super();
	}

	// Attributes ---------------------------------------------------------

	// Relationships ---------------------------------------------------------------

	private Player player;
	private AnswerPlayer answerPlayer;
	
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
	public AnswerPlayer getAnswerPlayer() {
		return answerPlayer;
	}
	public void setAnswerPlayer(AnswerPlayer answerPlayer) {
		this.answerPlayer = answerPlayer;
	}
}
