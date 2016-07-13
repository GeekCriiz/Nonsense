package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Player extends Actor{

	// Constructors -----------------------------------------------------------

	public Player() {
		super();
	}

	// Attributes ---------------------------------------------------------
	
	private int negatives;
	private int bans;
	private boolean blocked;
	private String favouriteCat;
	private double averageScore;
	private int played;
	private int won;
	private int abandoned;
	private Date banExpirationDate;
	
	@NotNull
	@Min(0)
	public int getNegatives() {
		return negatives;
	}
	public void setNegatives(int negatives) {
		this.negatives = negatives;
	}
	
	@NotNull
	@Range(min=0, max=4)
	public int getBans() {
		return bans;
	}
	public void setBans(int bans) {
		this.bans = bans;
	}
	
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	@SafeHtml
	@NotNull
	public String getFavouriteCat() {
		return favouriteCat;
	}
	public void setFavouriteCat(String favouriteCat) {
		this.favouriteCat = favouriteCat;
	}
	
	@NotNull
	@Min(0)
	public double getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}
	
	@NotNull
	@Min(0)
	public int getPlayed() {
		return played;
	}
	public void setPlayed(int played) {
		this.played = played;
	}
	
	@NotNull
	@Min(0)
	public int getWon() {
		return won;
	}
	public void setWon(int won) {
		this.won = won;
	}
	
	@NotNull
	@Min(0)
	public int getAbandoned() {
		return abandoned;
	}
	public void setAbandoned(int abandoned) {
		this.abandoned = abandoned;
	}
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getBanExpirationDate() {
		return banExpirationDate;
	}
	public void setBanExpirationDate(Date banExpirationDate) {
		this.banExpirationDate = banExpirationDate;
	}
	
	// Relationships ---------------------------------------------------------------

	private Collection<Player> followers;
	private Collection<Player> following;
	private Collection<Request> requests;
	private Collection<Game> games;
	
	@Valid
	@NotNull
	@ManyToMany(mappedBy="following")
	public Collection<Player> getFollowers() {
		return followers;
	}
	public void setFollowers(Collection<Player> followers) {
		this.followers = followers;
	}
	
	@Valid
	@NotNull
	@ManyToMany
	public Collection<Player> getFollowing() {
		return following;
	}
	public void setFollowing(Collection<Player> following) {
		this.following = following;
	}
	
	@Valid
	@NotNull
	@ManyToMany(mappedBy="players")
	public Collection<Game> getGames() {
		return games;
	}
	public void setGames(Collection<Game> games) {
		this.games = games;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="player")
	public Collection<Request> getRequests() {
		return requests;
	}
	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}
	
}
