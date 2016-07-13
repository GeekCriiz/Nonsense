package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Score extends DomainEntity{

	// Constructors -----------------------------------------------------------

	public Score() {
		super();
	}

	// Attributes ---------------------------------------------------------
	private int current;
	private int last;
	private int definition;
	private int date;
	private int film;
	private int acronym;
	private int character_;
	
	@NotNull
	@Min(0)
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	
	@NotNull
	@Min(0)
	public int getLast() {
		return last;
	}
	public void setLast(int last) {
		this.last = last;
	}
	
	@NotNull
	@Min(0)
	public int getDefinition() {
		return definition;
	}
	public void setDefinition(int definition) {
		this.definition = definition;
	}
	
	@NotNull
	@Min(0)
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	
	@NotNull
	@Min(0)
	public int getFilm() {
		return film;
	}
	public void setFilm(int film) {
		this.film = film;
	}
	
	@NotNull
	@Min(0)
	public int getAcronym() {
		return acronym;
	}
	public void setAcronym(int acronym) {
		this.acronym = acronym;
	}
	
	@NotNull
	@Min(0)
	public int getCharacter_() {
		return character_;
	}
	public void setCharacter_(int character_) {
		this.character_ = character_;
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
