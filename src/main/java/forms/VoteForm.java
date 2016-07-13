package forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class VoteForm {

	private int answerPlayerId;
	private int gameId;

	@NotNull
	@Min(0)
	public int getAnswerPlayerId() {
		return answerPlayerId;
	}
	public void setAnswerPlayerId(int answerPlayerId) {
		this.answerPlayerId = answerPlayerId;
	}
	
	@NotNull
	@Min(0)
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
}