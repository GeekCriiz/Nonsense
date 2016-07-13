package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Game extends DomainEntity{

	// Constructors -----------------------------------------------------------

	public Game() {
		super();
	}

	// Attributes ---------------------------------------------------------
	private int playersNumber;
	private int catNumber;
	private boolean custom;
	private int roundNumber;
	
	@NotNull
	@Min(3)
	@Max(8)
	public int getPlayersNumber() {
		return playersNumber;
	}
	public void setPlayersNumber(int playersNumber) {
		this.playersNumber = playersNumber;
	}
	
	@NotNull
	@Min(1)
	@Max(5)
	public int getCatNumber() {
		return catNumber;
	}
	public void setCatNumber(int catNumber) {
		this.catNumber = catNumber;
	}
	
	@NotNull
	@Min(0)
	@Max(8)
	public int getRoundNumber() {
		return roundNumber;
	}
	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}
	
	public boolean isCustom() {
		return custom;
	}
	public void setCustom(boolean custom) {
		this.custom = custom;
	}
	
	// Relationships ---------------------------------------------------------------
	private Collection<Score> scores;
	private Collection<Request> requests;
	private Collection<Player> players;
	private Collection<Alert> alerts;
	private Collection<Round> rounds;

	@Valid
	@NotNull
	@OneToMany(mappedBy="game")
	public Collection<Score> getScores() {
		return scores;
	}
	public void setScores(Collection<Score> scores) {
		this.scores = scores;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="game")
	public Collection<Alert> getAlerts() {
		return alerts;
	}
	public void setAlerts(Collection<Alert> alerts) {
		this.alerts = alerts;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="game", cascade={CascadeType.ALL})
	public Collection<Round> getRounds() {
		return rounds;
	}
	public void setRounds(Collection<Round> rounds) {
		this.rounds = rounds;
	}
	
	@Valid
	@NotNull
	@ManyToMany
	public Collection<Player> getPlayers() {
		return players;
	}
	public void setPlayers(Collection<Player> players) {
		this.players = players;
	}
	
	@Valid
	@OneToMany(mappedBy="game")
	public Collection<Request> getRequests() {
		return requests;
	}
	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}
}
