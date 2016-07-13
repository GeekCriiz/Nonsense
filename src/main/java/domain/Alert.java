package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Alert extends DomainEntity{

	// Constructors -----------------------------------------------------------

	public Alert() {
		super();
	}

	// Attributes ---------------------------------------------------------
	private Date timer;
	private int voted;
	private int yes;
	
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getTimer() {
		return timer;
	}
	public void setTimer(Date timer) {
		this.timer = timer;
	}
	
	@NotNull
	@Min(0)
	@Max(8)
	public int getVoted() {
		return voted;
	}
	public void setVoted(int voted) {
		this.voted = voted;
	}
	
	@NotNull
	@Min(0)
	@Max(8)
	public int getYes() {
		return yes;
	}
	public void setYes(int yes) {
		this.yes = yes;
	}
	
	
	// Relationships ---------------------------------------------------------------
	private Game game;
	private Player player;
		
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
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
}
