package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Round extends DomainEntity{

	// Constructors -----------------------------------------------------------

	public Round() {
		super();
	}

	// Attributes ---------------------------------------------------------
	private boolean ended;
	private int qNumber;
	private int roundNumber;

	public boolean isEnded() {
		return ended;
	}
	public void setEnded(boolean ended) {
		this.ended = ended;
	}
	
	@NotNull
	@Min(0)
	@Max(5)
	public int getQNumber() {
		return qNumber;
	}
	public void setQNumber(int qNumber) {
		this.qNumber = qNumber;
	}

	@NotNull
	@Min(1)
	@Max(8)
	public int getRoundNumber() {
		return roundNumber;
	}
	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	// Relationships ---------------------------------------------------------------
	private Game game;
	private Player speaker;
	private Collection<Orderr> orderrs;

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Player getSpeaker() {
		return speaker;
	}
	public void setSpeaker(Player speaker) {
		this.speaker = speaker;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="round",cascade={CascadeType.ALL})
	public Collection<Orderr> getOrderrs() {
		return orderrs;
	}
	public void setOrderrs(Collection<Orderr> orderrs) {
		this.orderrs = orderrs;
	}
	
}
