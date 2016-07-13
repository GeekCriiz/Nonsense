package forms;

import javax.validation.constraints.NotNull;

public class AlertForm {

	private int gameId;
	private int playerId;


	@NotNull
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	@NotNull
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	

}