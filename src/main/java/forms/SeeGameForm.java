package forms;

import java.util.Collection;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import domain.Player;
import domain.Score;

public class SeeGameForm {

	private int roundNumber;
	private Collection<Score> scores;
	private Collection<Player> players;
	
	@NotNull
	@Min(0)
	@Max(8)
	public int getRoundNumber() {
		return roundNumber;
	}
	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}
	
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
	@ManyToMany
	public Collection<Player> getPlayers() {
		return players;
	}
	public void setPlayers(Collection<Player> players) {
		this.players = players;
	}
	
}