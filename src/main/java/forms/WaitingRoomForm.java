package forms;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class WaitingRoomForm {
	
	private int playersNumber;
	private List<Tupla> players;
	private String host;
	private int gameId;
	
	@NotNull
	@Min(0)
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	@NotNull
	public List<Tupla> getPlayers() {
		return players;
	}
	public void setPlayers(List<Tupla> players) {
		this.players = players;
	}
	
	@NotNull
	@Min(0)
	public int getPlayersNumber() {
		return playersNumber;
	}
	public void setPlayersNumber(int playersNumber) {
		this.playersNumber = playersNumber;
	}
	
	@NotBlank
	@SafeHtml
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
}