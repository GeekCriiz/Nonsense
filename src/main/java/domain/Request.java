package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity{

	// Constructors -----------------------------------------------------------

	public Request() {
		super();
	}

	// Attributes ---------------------------------------------------------
	private String status;
	
	@NotBlank
	@SafeHtml
	@Pattern(regexp = "^PENDING|ACCEPTED|REJECTED$")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
